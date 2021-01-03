package xin.jiangqiang.entity;

import xin.jiangqiang.enums.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
public class RequestBody {
    private final Map<String, String> map = new HashMap<>();

    public String builder() {
        Set<Map.Entry<String, String>> entries = this.map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append(entry.getKey().trim()).append(Constants.COLON.getValue()).append(Constants.BLANKSPACE.getValue()).append(entry.getValue());
        }
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
