package xin.jiangqiang.entity.request;

import lombok.*;
import xin.jiangqiang.enums.Constants;
import xin.jiangqiang.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:40
 */
@ToString
@NoArgsConstructor
public class RequestHeader {
    @Getter
    private final Map<String, String> map = new HashMap<>();

    public String builder() {
        StringBuilder stringBuilder = HttpUtils.mapToHttpStringBuilder(map);
        stringBuilder.append("Connection:").append(Constants.BLANKSPACE.getValue()).append("close").append(Constants.CRLF.getValue()).append(Constants.CRLF.getValue());
        return stringBuilder.toString();
    }

    public void putAll(Map<String, String> map) {
        this.map.putAll(map);
    }

    public void put(String name, String value) {
        this.map.put(name, value);
    }

    public void remove(String name) {
        this.map.remove(name);
    }

    public void removeAll() {
        this.map.clear();
    }
}
