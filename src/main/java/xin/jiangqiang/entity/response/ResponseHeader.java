package xin.jiangqiang.entity.response;

import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.constants.CommonConstants;
import xin.jiangqiang.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangqiang
 * @date 2021/1/3 16:40
 */
@ToString
public class ResponseHeader {
    @Getter
    private final Map<String, String> map = new HashMap<>();

    public String builder() {
        StringBuilder stringBuilder = HttpUtils.mapToHttpStringBuilder(map);
        stringBuilder.append(CommonConstants.CRLF);
        return stringBuilder.toString();
    }

    public void putAll(Map<String, String> map) {
        this.map.putAll(map);
    }

    public void put(String name, String value) {
        this.map.put(name, value);
    }

}
