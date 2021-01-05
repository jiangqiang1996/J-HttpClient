package xin.jiangqiang.entity.request;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.constants.HttpRequestHeaderType;
import xin.jiangqiang.entity.common.Cookie;
import xin.jiangqiang.entity.request.body.RequestBody;
import xin.jiangqiang.entity.request.body.impl.RequestFormBody;
import xin.jiangqiang.entity.request.body.impl.RequestFormDataBody;
import xin.jiangqiang.entity.request.body.impl.RequestJSONBody;
import xin.jiangqiang.enums.HttpStructure;
import xin.jiangqiang.enums.RequestMethod;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author jiangqiang
 * @date 2021/1/3 14:09
 */
@ToString
@Getter
public class RequestEntity {
    private final RequestLine requestLine = new RequestLine();
    private final RequestHeader requestHeader = new RequestHeader();
    private final RequestBody requestBody;

    /**
     * 设置请求参数
     *
     * @param requestBody 请求体
     */
    public RequestEntity(RequestBody requestBody) {

        if (requestBody instanceof RequestFormBody) {
            addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_X_WWW_FORM_URLENCODED);
        } else if (requestBody instanceof RequestJSONBody) {
            addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_JSON);
        } else if (requestBody instanceof RequestFormDataBody) {
            addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_FORM_DATA);
        } else {
            addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_X_WWW_FORM_URLENCODED);//默认就表单类型
        }
        this.requestBody = requestBody;
    }

    /**
     * 拼接报文字符串
     * 返回规范的请求报文信息
     *
     * @return 返回报文字符串
     */
    public String builderToString() {
        String requestStr;
        String bodyStr = null;
        if (requestBody != null) {
            bodyStr = requestBody.builder(requestHeader.getHeader(HttpRequestHeaderType.CONTENT_TYPE));
            addHeader(HttpRequestHeaderType.CONTENT_LENGTH, String.valueOf(bodyStr.getBytes(StandardCharsets.UTF_8).length));
        } else {
            addHeader(HttpRequestHeaderType.CONTENT_LENGTH, "0");
        }
        requestStr = requestLine.builder() + requestHeader.builder();
        if (StringUtils.isNotEmpty(bodyStr)) {
            requestStr += bodyStr;
        }
        return requestStr;
    }

    /**
     * 不传参数则返回全部
     * 根据选项返回指定部分报文
     *
     * @return
     */
    public String formatToString(HttpStructure... httpStructures) {
        List<HttpStructure> httpStructures1 = new ArrayList<>(3);
        Collections.addAll(httpStructures1, httpStructures);
        if (httpStructures1.size() == 0) {
            httpStructures1.add(HttpStructure.LINE);
            httpStructures1.add(HttpStructure.HEAD);
            httpStructures1.add(HttpStructure.BODY);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (httpStructures1.contains(HttpStructure.LINE)) {
            stringBuilder.append(requestLine.builder());
        }
        if (httpStructures1.contains(HttpStructure.HEAD)) {
            stringBuilder.append(requestHeader.builder());
        }
        if (httpStructures1.contains(HttpStructure.BODY)) {
            if (requestBody != null) {
                stringBuilder.append(requestBody.builder(requestHeader.getHeader(HttpRequestHeaderType.CONTENT_TYPE)));
            }
        }
        return stringBuilder.toString();
    }

    public byte[] builderToByte() {
        return builderToString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public RequestEntity setUrl(String url) {
        requestHeader.setHost(url);
        requestLine.setUrl(url);
        return this;
    }

    public RequestEntity setMethod(RequestMethod method) {
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

    public String getHeader(String name) {
        return this.requestHeader.getHeader(name);
    }

    public Map<String, String> getHeaders() {
        return this.requestHeader.getHeaders();
    }

    public RequestEntity addCookie(Cookie cookie) {
        requestHeader.addCookie(cookie);
        return this;
    }

    public RequestEntity addCookies(List<Cookie> cookies) {
        requestHeader.addCookies(cookies);
        return this;
    }
}
