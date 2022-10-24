package top.jiangqiang.httpclient.net;

import top.jiangqiang.httpclient.entity.response.ResponseBody;
import top.jiangqiang.httpclient.entity.response.ResponseLine;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;
import top.jiangqiang.httpclient.entity.response.ResponseHeader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangqiang
 * @date 2021/1/7 15:05
 */
public class MessageParser {
    public static ResponseEntity parseResp(byte[] response) {
        String respStr = new String(response, (StandardCharsets.UTF_8));
        String[] tmpStrs = respStr.split("\r\n");
        //解析响应行
        String responseLineStr = tmpStrs[0];
        String[] respLines = responseLineStr.split(" ");
        String desc = null;//有可能没有desc部分
        if (respLines.length < 3) {
            desc = "";
        }
        Integer code = Integer.valueOf(respLines[1]);//断点太久会爆越界异常
        ResponseLine responseLine = new ResponseLine(code, respLines[0], desc);
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
        if (tmpStrings.length >= 2) {
            String body = respStr.substring(tmpStrings[0].length() + 4);//响应正文
            byte[] content = body.getBytes(StandardCharsets.UTF_8);
            responseBody = new ResponseBody(content);
        } else {
            responseBody = new ResponseBody(new byte[0]);
        }
        return new ResponseEntity(responseLine, responseHeader, responseBody, response);
    }
}
