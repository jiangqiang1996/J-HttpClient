package top.jiangqiang.httpclient.net;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.interceptor.Interceptor;
import top.jiangqiang.httpclient.utils.NetUtils;
import top.jiangqiang.httpclient.utils.RegExpUtils;
import top.jiangqiang.httpclient.utils.SocketUtils;
import top.jiangqiang.httpclient.callback.CallBack;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;

import java.io.*;
import java.net.Socket;
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
        //匹配带端口号的链接^(http|https)(://).*(:)\d{1,5}(/)
        String regex = "^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*(:)\\d{1,5}(/.*)*";
        if (RegExpUtils.isMatch(url, regex)) {
            String tmpStr = null;
            if (url.startsWith("https://")) {
                tmpStr = url.substring(NetUtils.getHost(url).length() + 8 + 1);//需要加一个冒号的长度
            } else if (url.startsWith("http://")) {
                tmpStr = url.substring(NetUtils.getHost(url).length() + 7 + 1);
            }
            if (StringUtils.isEmpty(tmpStr)) {
                throw new RuntimeException("URL不合法");
            }
            String[] split = tmpStr.split("/");
            port = Integer.valueOf(split[0]);
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
        ResponseEntity responseEntity = MessageParser.parseResp(response);
        if (this.interceptor != null) {
            interceptor.afterRequest(responseEntity);
        }
        doCallBack(responseEntity);
        return responseEntity;
    }

}
