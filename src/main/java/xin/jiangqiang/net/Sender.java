package xin.jiangqiang.net;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.response.ResponseBody;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.entity.response.ResponseHeader;
import xin.jiangqiang.entity.response.ResponseLine;
import xin.jiangqiang.interceptor.Interceptor;
import xin.jiangqiang.utils.NetUtils;
import xin.jiangqiang.utils.RegExpUtils;
import xin.jiangqiang.utils.SocketUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author jiangqiang
 * @date 2021/1/3 11:17
 */
@Slf4j
@NoArgsConstructor
public class Sender {
    private String url;
    private Integer port;
    @Setter
    @Getter
    private Interceptor interceptor;
    @Setter
    @Getter
    private CallBack<ResponseEntity> callBack;
    @Getter
    @Setter
    RequestEntity requestEntity;

    public Sender(String url) {
        this.url = url;
    }

    public Sender(String url, CallBack<ResponseEntity> callBack) {
        this.url = url;
        this.callBack = callBack;
    }

    /**
     * 根据url设置端口号
     * 如果请求实体没有初始化，则默认初始化
     */
    private void init() {
        if (StringUtils.isEmpty(this.url)) {
            throw new RuntimeException("URL不能为空");
        }
        if (RegExpUtils.isMatch(url, "^(http|https)(://).*(:)\\d{1,5}(/)")) {
            String[] split = url.split("^(http|https)(://)\\.*(:)\\d{1,5}(/)");
            String[] split1 = split[0].substring(0, split[0].length() - 1).split(":");
            port = Integer.valueOf(split1[split1.length - 1]);
        } else {
            if (url.startsWith("https")) {
                this.port = 443;
            } else {
                this.port = 80;
            }
        }
        if (Objects.isNull(requestEntity)) {
            requestEntity = new RequestEntity(null).setUrl(url);
        } else {
            //请求实体需要设置url
            requestEntity.setUrl(url);
        }

    }

    /**
     * 回调函数不为空才执行
     *
     * @param responseEntity
     */
    private void doCallBack(ResponseEntity responseEntity) {
        if (!Objects.isNull(callBack)) {
            callBack.process(responseEntity);
        }
    }

    public final ResponseEntity send(String url) {
        this.url = url;
        return this.send();
    }

    /**
     * 如果回调函数对象不为空,则使用异步
     *
     * @return 异步或报错返回null, 同步返回字节数组
     */
    public final ResponseEntity send() {
        init();
        final Socket socket;
        List<String> ips = NetUtils.getIpsByName(url);//获取ip
        try {
            socket = new Socket(ips.get(0), port);

            if (!Objects.isNull(callBack)) {
                new Thread(() -> {
                    doReqAndResp(socket);
                }).start();
                return null;
            } else {
                return doReqAndResp(socket);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private ResponseEntity doReqAndResp(Socket socket) {
        if (this.interceptor != null) {
            interceptor.beforeRequest(this.requestEntity);
        }
        //发送报文
        SocketUtils.writeString(socket, this.requestEntity.builderToString());
        //读取报文
        byte[] response = SocketUtils.readToByte(socket);
        //解析报文
        ResponseEntity responseEntity = parseResp(response);
        if (this.interceptor != null) {
            interceptor.afterRequest(responseEntity);
        }
        doCallBack(responseEntity);
        return responseEntity;
    }

    private ResponseEntity parseResp(byte[] response) {
        String respStr = new String(response, (StandardCharsets.UTF_8));
        String[] tmpStrs = respStr.split("\r\n");
        //解析响应行
        String[] respLines = tmpStrs[0].split(" ");
        Integer code = Integer.valueOf(respLines[1]);
        ResponseLine responseLine = new ResponseLine(code, respLines[0], respLines[2]);
        //解析响应头
        Map<String, String> headers = new HashMap<>();
        List<String> cookieStrings = new ArrayList<>();
        for (int i = 1; i < tmpStrs.length; i++) {
            String str = tmpStrs[i];
            if ("".equals(str)) {
                break;
            } else {
                String[] keyValues = str.split(":");
                String key = keyValues[0];
                String value = str.substring(keyValues[0].length() + 1).trim();//值可能也包含冒号
                System.out.println(value);
                if ("Set-Cookie".equals(key)) {
                    cookieStrings.add(value);
                } else {
                    headers.put(key, value);
                }
            }
        }
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addAllCookies(cookieStrings);
        responseHeader.putAll(headers);
        //解析响应体
        String[] tmpStrings = respStr.split("\r\n\r\n");
        ResponseBody responseBody = null;
        if (tmpStrings.length == 2) {
            byte[] content = tmpStrings[1].getBytes(StandardCharsets.UTF_8);
            responseBody = new ResponseBody(content);
        }
        return new ResponseEntity(responseLine, responseHeader, responseBody, response);
    }
}
