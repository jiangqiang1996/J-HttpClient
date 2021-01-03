package xin.jiangqiang.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xin.jiangqiang.enums.RequestMethod;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author jiangqiang
 * @date 2021/1/3 14:09
 */
@Data
@NoArgsConstructor
public class RequestEntity {
    private final RequestLine requestLine = new RequestLine();
    private final RequestHeader requestHeader = new RequestHeader();
    @Setter
    @Getter
    RequestBody requestBody = null;

    /**
     * 设置请求参数
     *
     * @param requestBody 请求体
     */
    public RequestEntity(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public String builderToString() {
        String requestStr = requestLine.builder() + requestHeader.builder();
        if (requestBody != null) {
            requestStr += requestBody.builder();
        }
        return requestStr;
    }

    public byte[] builderToByte() {
        String requestStr = requestLine.builder() + requestHeader.builder();
        if (requestBody != null) {
            requestStr += requestBody.builder();
        }
        return requestStr.getBytes(StandardCharsets.ISO_8859_1);
    }

    public RequestEntity setUrl(String url) {
        requestLine.setUrl(url);
        return this;
    }

    public RequestEntity setUrl(RequestMethod method) {
        requestLine.setMethod(method);
        return this;
    }

    public RequestEntity setVersion(String version) {
        requestLine.setVersion(version);
        return this;
    }

    public RequestEntity addHeader(String name, String value) {
        this.requestHeader.put(name, value);
        return this;
    }

    public RequestEntity addHeaders(Map<String, String> headers) {
        this.requestHeader.putAll(headers);
        return this;
    }

    public RequestEntity removeHeader(String name) {
        this.requestHeader.remove(name);
        return this;
    }

    public RequestEntity removeAllHeader() {
        this.requestHeader.removeAll();
        return this;
    }


    public RequestEntity addParam(String name, String value) {
        this.requestBody.put(name, value);
        return this;
    }

    public RequestEntity addParams(Map<String, String> headers) {
        this.requestBody.putAll(headers);
        return this;
    }

    public RequestEntity removeParam(String name) {
        this.requestBody.remove(name);
        return this;
    }

    public RequestEntity removeAllParam() {
        this.requestBody.removeAll();
        return this;
    }
}
