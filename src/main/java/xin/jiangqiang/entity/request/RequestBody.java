package xin.jiangqiang.entity.request;

import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.enums.Constants;
import xin.jiangqiang.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestBody {
    private final Map<String, String> map = new HashMap<>();

    public String builder() {
        StringBuilder stringBuilder = HttpUtils.mapToHttpStringBuilder(map);
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
