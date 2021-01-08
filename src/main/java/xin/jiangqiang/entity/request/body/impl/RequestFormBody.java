package xin.jiangqiang.entity.request.body.impl;

import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.entity.request.body.RequestBody;
import xin.jiangqiang.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 提交表单参数,不需要设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestFormBody implements RequestBody {
    private final Map<String, String> map = new HashMap<>();

    @Override
    public String builder(String contentType) {
        StringBuilder stringBuilder = HttpUtils.mapToParamUrlEncode(map);
        return stringBuilder.toString();
    }

    public RequestFormBody addAllParams(Map<String, String> params) {
        this.map.putAll(params);
        return this;
    }

    public RequestFormBody addParam(String name, String value) {
        this.map.put(name, value);
        return this;
    }

    public RequestFormBody removeParam(String name) {
        this.map.remove(name);
        return this;
    }

    public RequestFormBody removeAllParams() {
        this.map.clear();
        return this;
    }
}
