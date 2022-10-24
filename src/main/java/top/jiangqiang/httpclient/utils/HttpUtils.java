package top.jiangqiang.httpclient.utils;

import top.jiangqiang.httpclient.constants.CommonConstants;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 17:51
 */
public class HttpUtils {
    public static StringBuilder mapToHttpStringBuilder(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            String value = entry.getValue();
            stringBuilder.append(entry.getKey().trim()).append(CommonConstants.COLON)
                    .append(CommonConstants.BLANKSPACE).append(value).append(CommonConstants.CRLF);
        }
        return stringBuilder;
    }

    /**
     * map转编码的键值对字符串
     *
     * @param map
     * @return
     */
    public static StringBuilder mapToParamUrlEncode(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(encode(name, StandardCharsets.UTF_8)).append("=")
                    .append(encode(value, StandardCharsets.UTF_8));
            stringBuilder.append("&");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }

    /**
     * 编码
     *
     * @param str
     * @param charset
     * @return
     */
    public static String encode(String str, Charset charset) {
        str = URLEncoder.encode(str, charset);//先编码 再替换
        str = str.replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "\\(")
                .replaceAll("%29", "\\)")
                .replaceAll("%7E", "~")
                .replaceAll("\\+", "%20")
                .replaceAll("\\\\x00", "%00");
        return str;
    }

    /**
     * 解码
     *
     * @param str
     * @param charset
     * @return
     */
    public static String decode(String str, Charset charset) {
        str = str.replaceAll("!", "%21")//先替换,再解码
                .replaceAll("'", "%27")
                .replaceAll("\\(", "%28")
                .replaceAll("\\)", "%29")
                .replaceAll("~", "%7E")
                .replaceAll("%20", "+")
                .replaceAll("%00", "\\\\x00");
        return URLDecoder.decode(str, charset);
    }

}
