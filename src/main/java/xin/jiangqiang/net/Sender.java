package xin.jiangqiang.net;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.RequestEntity;
import xin.jiangqiang.entity.RequestHeader;
import xin.jiangqiang.entity.RequestLine;
import xin.jiangqiang.entity.ResponseEntity;
import xin.jiangqiang.interceptor.Interceptor;
import xin.jiangqiang.utils.CommonUtils;
import xin.jiangqiang.utils.NetUtils;
import xin.jiangqiang.utils.RegExpUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author jiangqiang
 * @date 2021/1/3 11:17
 */
@Slf4j
@NoArgsConstructor
public class Sender {
    private String url;
    private Integer port;
    private Interceptor interceptor;
    @Setter
    @Getter
    private CallBack<byte[]> callBack;
    @Getter
    @Setter
    RequestEntity requestEntity;

    public Sender(String url) {
        this.url = url;
    }

    public Sender(String url, CallBack<byte[]> callBack) {
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
        if (RegExpUtil.isMatch(url, "^(http|https)(://).*(:)\\d{1,5}(/)")) {
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
            requestEntity = new RequestEntity().setUrl(url);
        } else {
            //请求实体需要设置url
            requestEntity.setUrl(url);
        }

    }

    /**
     * 回调函数不为空才执行
     *
     * @param bytes todo 后期修改为ResponseEntity对象
     */
    private void doCallBack(byte[] bytes) {
        if (!Objects.isNull(callBack)) {
            callBack.process(bytes);
        }
    }

    private byte[] getResponse(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String str;
            StringBuilder stringBuilder = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
                log.debug(str);
            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.info("报文解析异常");
            throw new RuntimeException("报文解析异常");
        }

    }

    public final byte[] send(String url) {
        this.url = url;
        return this.send();
    }

    /**
     * 如果回调函数对象不为空,则使用异步
     *
     * @return 异步或报错返回null, 同步返回字节数组 todo 后期优化为response对象,包含响应码等属性
     */
    public final byte[] send() {
        init();
        final Socket socket;
        final OutputStreamWriter outputStreamWriter;
        final BufferedWriter bufferedWriter;
        List<String> ips = NetUtils.getIpsByName(url);//获取ip
        try {
            socket = new Socket(ips.get(0), port);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            if (!Objects.isNull(callBack)) {
                new Thread(() -> {
                    doReqAndResp(bufferedWriter, outputStreamWriter, socket);
                }).start();
                return null;
            } else {
                return doReqAndResp(bufferedWriter, outputStreamWriter, socket);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private byte[] doReqAndResp(BufferedWriter bufferedWriter, OutputStreamWriter outputStreamWriter, Socket socket) {
        try {
            if (this.interceptor != null) {
                interceptor.beforeRequest(this.requestEntity);
            }
            bufferedWriter.write(this.requestEntity.builderToString());
            bufferedWriter.flush();
            byte[] response = getResponse(socket.getInputStream());//todo 处理成responseEntity对象
//            ResponseEntity responseEntity = null;
//            if (this.interceptor != null) {
//                interceptor.afterRequest(responseEntity);
//            }
            doCallBack(response);
            return response;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            CommonUtils.close(bufferedWriter, outputStreamWriter, socket);
        }
    }
}
