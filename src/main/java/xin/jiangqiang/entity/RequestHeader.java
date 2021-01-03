package xin.jiangqiang.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import xin.jiangqiang.enums.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:40
 */
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeader {
    Map<String, String> map = new HashMap<>();

    public String builder() {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append(entry.getKey().trim()).append(Constants.COLON.getValue()).append(Constants.BLANKSPACE.getValue()).append(entry.getValue());
        }
        stringBuilder.append("Connection:").append(Constants.BLANKSPACE.getValue()).append("close").append(Constants.CRLF.getValue()).append(Constants.CRLF.getValue());
        return stringBuilder.toString();
    }
}
