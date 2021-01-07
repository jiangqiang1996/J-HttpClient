package xin.jiangqiang;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.enums.HttpStructure;
import xin.jiangqiang.utils.MessageSendTestUtils;
import xin.jiangqiang.utils.NetUtils;
import xin.jiangqiang.utils.RegExpUtils;
import xin.jiangqiang.utils.SocketUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static xin.jiangqiang.net.MessageParser.parseResp;

/**
 * @author jiangqiang
 * @date 2021/1/7 14:52
 */
@Slf4j
public class TestMessage {
    public static void main(String[] args) {
        String msg = "POST /upload HTTP/1.1\r\n" +
                "User-Agent: PostmanRuntime/7.26.8\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: c7ac45cb-8719-4990-86a2-d4d6e4b2545d\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Type: multipart/form-data; boundary=--------------------------201694748371316667110105\r\n" +
                "Content-Length: 215\r\n\r\n" +
                "----------------------------201694748371316667110105\r\n" +
                "Content-Disposition: form-data; name=\"head_img\"; filename=\"dasd.txt\"\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                new String(fileConvertToByteArray(new File("D:\\Documents\\file/dasd.txt"))) + "\r\n\r\n" +
                "----------------------------201694748371316667110105--\r\n";
        MessageSendTestUtils sender = new MessageSendTestUtils("http://localhost:8080/upload");
        log.info(msg);
        ResponseEntity responseEntity = sender.sendAndGetRespnse(msg);
        log.info(responseEntity.formatToString(HttpStructure.LINE, HttpStructure.HEAD));
    }

    private static byte[] fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
