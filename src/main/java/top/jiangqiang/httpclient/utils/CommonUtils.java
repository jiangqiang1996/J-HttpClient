package top.jiangqiang.httpclient.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 公共工具类
 *
 * @author jiangqiang
 * @date 2021/1/3 11:50
 */
public class CommonUtils {
    /**
     * 关闭资源
     *
     * @param objects
     */
    public static void close(Closeable... objects) {
        for (Closeable closeable : objects) {
            if (!Objects.isNull(closeable)) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用一种字符集解码后,再用另一种字符集编码
     *
     * @param str           需要编解码的字符串
     * @param decodeCharset 解码字符集
     * @param encodeCharset 编码字符集
     * @return
     */
    public static String charsetConvert(String str, Charset decodeCharset, Charset encodeCharset) {
        return new String(str.getBytes(decodeCharset), encodeCharset);
    }

    /**
     * 默认将UTF-8转换为ISO8859-1
     *
     * @param str
     * @return
     */
    public static String charsetConvert(String str) {
        return charsetConvert(str, StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);
    }

}
