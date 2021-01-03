package xin.jiangqiang.utils;

import xin.jiangqiang.enums.Constants;

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
            stringBuilder.append(entry.getKey().trim()).append(Constants.COLON.getValue()).append(Constants.BLANKSPACE.getValue()).append(entry.getValue());
        }
        return stringBuilder;
    }
}
