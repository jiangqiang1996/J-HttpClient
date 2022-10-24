package top.jiangqiang.httpclient.utils;

import lombok.extern.slf4j.Slf4j;
import top.jiangqiang.httpclient.constants.CommonConstants;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:40
 */
@Slf4j
public class SocketUtils {
    public static byte[] readToByte(Socket socket) {
        return readToString(socket).getBytes(StandardCharsets.UTF_8);
    }

    public static String readToString(Socket socket) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                // 一次读一行
                stringBuilder.append(line).append(CommonConstants.CRLF);
            }
            log.debug("响应报文：\n{}", stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);//读取数据超时
        } finally {
            if (!socket.isInputShutdown()) {
                try {
                    socket.shutdownInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket.isInputShutdown() && socket.isOutputShutdown()) {
                CommonUtils.close(socket);
            }
        }
    }


    public static void writeString(Socket socket, String data) {
        try {
            log.debug("请求报文：\n{}", data);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.ISO_8859_1);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);//socket write error 建立连接之后，很久没有传输数据
        } finally {
            if (!socket.isOutputShutdown()) {
                try {
                    socket.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket.isInputShutdown() && socket.isOutputShutdown()) {
                CommonUtils.close(socket);
            }
        }
    }
}
