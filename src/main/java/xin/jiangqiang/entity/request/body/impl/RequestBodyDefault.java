package xin.jiangqiang.entity.request.body.impl;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.CommonConstants;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.entity.request.body.RequestBody;
import xin.jiangqiang.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持提交表单参数和JSON参数,默认是以表单提交,如果以json提交需要手动设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestBodyDefault implements RequestBody {
    private final Map<String, String> map = new HashMap<>();

    public String builder(String contentType) {
        if (StringUtils.isEmpty(contentType) || HttpHeaderValue.CONTENTTYPE_X_WWW_FORM_URLENCODED.equals(contentType)) {//没有contentType或表单提交
            StringBuilder stringBuilder = HttpUtils.mapToParamUrlEncode(map);
            return stringBuilder.toString();
        } else if (contentType.equals(HttpHeaderValue.CONTENTTYPE_JSON)) {//json提交
            return JSON.toJSONString(map) + CommonConstants.CRLF;
        } else {
            throw new RuntimeException("该类型不支持此请求体对象");
        }
    }

    public RequestBodyDefault addAllParams(Map<String, String> params) {
        this.map.putAll(params);
        return this;
    }

    public RequestBodyDefault addParam(String name, String value) {
        this.map.put(name, value);
        return this;
    }

    public RequestBodyDefault removeParam(String name) {
        this.map.remove(name);
        return this;
    }

    public RequestBodyDefault removeAllParams() {
        this.map.clear();
        return this;
    }
}
