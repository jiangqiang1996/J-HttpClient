package xin.jiangqiang.entity.request.body.impl;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.entity.request.body.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 提交json参数,不需要设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestJSONBody implements RequestBody {
    private final Map<String, String> map = new HashMap<>();

    @Override
    public String builder(String contentType) {
        return JSON.toJSONString(map);
    }

    public RequestJSONBody addAllParams(Map<String, String> params) {
        this.map.putAll(params);
        return this;
    }

    public RequestJSONBody addParam(String name, String value) {
        this.map.put(name, value);
        return this;
    }

    public RequestJSONBody removeParam(String name) {
        this.map.remove(name);
        return this;
    }

    public RequestJSONBody removeAllParams() {
        this.map.clear();
        return this;
    }
}
