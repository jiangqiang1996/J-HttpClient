package xin.jiangqiang.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.entity.response.ResponseEntity;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static xin.jiangqiang.net.MessageParser.parseResp;

/**
 * 报文发送测试工具类
 *
 * @author jiangqiang
 * @date 2021/1/7 14:52
 */
@Slf4j
public class MessageSendTestUtils {
    private final String url;
    private Socket socket = null;
    private Integer port;

    public MessageSendTestUtils(String url) {
        this.url = url;
    }

    public void init() {
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
                port = 443;
            } else {
                port = 80;
            }
        }
    }

    /**
     * 报文测试工具类
     *
     * @param message
     * @return
     */
    public ResponseEntity sendAndGetRespnse(String message) {
        init();
        List<String> ips = NetUtils.getIpsByName(url);//获取ip
        try {
            socket = new Socket(ips.get(0), port);
            //发送报文
            SocketUtils.writeString(socket, message);
            //读取报文
            byte[] response = SocketUtils.readToByte(socket);
            //解析报文
            ResponseEntity responseEntity = parseResp(response);
            return responseEntity;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
