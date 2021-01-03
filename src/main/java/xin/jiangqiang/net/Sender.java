package xin.jiangqiang.net;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.RequestHeader;
import xin.jiangqiang.entity.RequestLine;
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
public class Sender {
    final private String url;
    private Integer port;
    @Setter
    @Getter
    private CallBack<byte[]> callBack;

    public Sender(String url) {
        this.url = url;
    }

    public Sender(String url, CallBack<byte[]> callBack) {
        this.url = url;
        this.callBack = callBack;
    }

    /**
     * 根据url设置端口号
     */
    private void setPort() {
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
    }

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

    /**
     * 如果回调函数对象不为空,则使用异步
     *
     * @return 异步或报错返回null, 同步返回字节数组 todo 后期优化为response对象,包含响应码等属性
     */
    public final byte[] send() {
        setPort();
        final Socket socket;
        final OutputStreamWriter outputStreamWriter;
        final BufferedWriter bufferedWriter;
        List<String> ips = NetUtils.getIpsByName(url);//获取ip
        try {
            socket = new Socket(ips.get(0), port);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String requestStr = new RequestLine(url).builder();
            String requestHeader = new RequestHeader().builder();
            if (!Objects.isNull(callBack)) {
                new Thread(() -> {
                    try {
                        bufferedWriter.write(requestStr);//todo 后期优化为一个请求实体对象
                        bufferedWriter.write(requestHeader);//todo 后期优化为一个请求实体对象
                        bufferedWriter.flush();
                        doCallBack(getResponse(socket.getInputStream()));
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    } finally {
                        CommonUtils.close(bufferedWriter, outputStreamWriter);
                        CommonUtils.close(socket);
                    }
                }).start();
                return null;
            } else {
                try {
                    bufferedWriter.write(requestStr);
                    bufferedWriter.write(requestHeader);
                    bufferedWriter.flush();
                    return getResponse(socket.getInputStream());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return null;
                } finally {
                    CommonUtils.close(bufferedWriter, outputStreamWriter);
                    CommonUtils.close(socket);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
