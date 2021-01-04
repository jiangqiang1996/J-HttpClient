package xin.jiangqiang.utils;

import xin.jiangqiang.constants.CommonConstants;

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
}
