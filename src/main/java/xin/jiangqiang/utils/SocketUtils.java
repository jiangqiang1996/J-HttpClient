package xin.jiangqiang.utils;

import lombok.extern.slf4j.Slf4j;
import xin.jiangqiang.enums.Constants;

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
            String line = null;
            while ((line = br.readLine()) != null) {
                // 一次读一行
                stringBuilder.append(line).append(Constants.CRLF.getValue());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            return "";
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
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
