package top.jiangqiang.httpclient.entity.request;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import top.jiangqiang.httpclient.entity.common.Cookie;
import top.jiangqiang.httpclient.entity.request.body.RequestBody;
import top.jiangqiang.httpclient.constants.HttpHeaderValue;
import top.jiangqiang.httpclient.constants.HttpRequestHeaderType;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestFormBody;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestFormDataBody;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestJSONBody;
import top.jiangqiang.httpclient.enums.HttpStructure;
import top.jiangqiang.httpclient.enums.RequestMethod;
import top.jiangqiang.httpclient.utils.CommonUtils;
import top.jiangqiang.httpclient.utils.RegExpUtils;

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
            String id = ((RequestFormDataBody) requestBody).getId();
            addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_FORM_DATA + ";  boundary=--------------------------" + id);
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
        String contentType = requestHeader.getHeader(HttpRequestHeaderType.CONTENT_TYPE);
        Boolean hasBody = requestLine.getMethod().getHasBody();
        if (requestBody != null) {
            bodyStr = requestBody.builder(contentType);
            if (contentType.equals(HttpHeaderValue.CONTENTTYPE_JSON)) {
                addHeader(HttpRequestHeaderType.CONTENT_LENGTH, hasBody ? String.valueOf(bodyStr.getBytes(StandardCharsets.UTF_8).length) : "0");
            } else {
                addHeader(HttpRequestHeaderType.CONTENT_LENGTH, hasBody ? String.valueOf(bodyStr.getBytes(StandardCharsets.ISO_8859_1).length) : "0");
            }
        } else {
            addHeader(HttpRequestHeaderType.CONTENT_LENGTH, "0");
        }
        if (getVersion().equals(HttpHeaderValue.VERSION)) {//HTTP1.1 默认
            addHeader(HttpRequestHeaderType.CONNECTION, HttpHeaderValue.KEEP_ALIVE);
        } else {
            addHeader(HttpRequestHeaderType.CONNECTION, HttpHeaderValue.CLOSE);
        }
        String oldUrl = requestLine.getUrl();
        if (!hasBody) {//如果没有body,则参数拼接在URL上
            String newUrl = oldUrl;
            //协议,域名部分,端口号,斜杠或者斜杠加任意字符,问号,键值对
            if (RegExpUtils.isMatch(newUrl, "^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*((:)\\d{1,5})+(/|/[^/?]*)?(\\?)[^?=]*(=)[^?=]*")) {
                newUrl += ("&" + bodyStr);
            } else {
                newUrl = newUrl + "?" + bodyStr;
            }
            requestLine.setUrl(newUrl);
        }
        requestStr = requestLine.builder() + requestHeader.builder();
        if (StringUtils.isNotEmpty(bodyStr)) {
            if (hasBody) {
                requestStr += bodyStr;
            }
        }
        requestLine.setUrl(oldUrl);
        if (contentType.startsWith(HttpHeaderValue.CONTENTTYPE_FORM_DATA)) {//对文件提交单独编码
            return requestStr;
        } else {
            return CommonUtils.charsetConvert(requestStr);
        }
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
        Boolean hasBody = requestLine.getMethod().getHasBody();
        String oldUrl = requestLine.getUrl();
        String bodyStr = "";
        if (requestBody != null) {
            bodyStr = requestBody.builder(requestHeader.getHeader(HttpRequestHeaderType.CONTENT_TYPE));
        }
        addHeader(HttpRequestHeaderType.CONTENT_LENGTH, hasBody ? String.valueOf(bodyStr.getBytes(StandardCharsets.UTF_8).length) : "0");
        if (httpStructures1.contains(HttpStructure.LINE)) {
            if (hasBody) {
                stringBuilder.append(requestLine.builder());
            } else {//如果没有body,则参数拼接在URL上
                String newUrl = oldUrl;
                //协议,域名部分,端口号,斜杠或者斜杠加任意字符,问号,键值对
                if (RegExpUtils.isMatch(newUrl, "^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*((:)\\d{1,5})+(/|/[^/?]*)?(\\?)[^?=]*(=)[^?=]*")) {
                    newUrl = newUrl + "&" + bodyStr;
                } else {
                    newUrl = newUrl + "?" + bodyStr;
                }
                requestLine.setUrl(newUrl);
                stringBuilder.append(requestLine.builder());
                requestLine.setUrl(oldUrl);
            }
        }
        if (getVersion().equals(HttpHeaderValue.VERSION)) {//HTTP1.1 默认
            addHeader(HttpRequestHeaderType.CONNECTION, HttpHeaderValue.KEEP_ALIVE);
        } else {
            addHeader(HttpRequestHeaderType.CONNECTION, HttpHeaderValue.CLOSE);
        }
        if (httpStructures1.contains(HttpStructure.HEAD)) {
            stringBuilder.append(requestHeader.builder());
        }
        if (httpStructures1.contains(HttpStructure.BODY)) {
            if (requestBody != null && hasBody) {
                stringBuilder.append(bodyStr);
            }
        }
        return stringBuilder.toString();
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

    public String getVersion() {
        return requestLine.getVersion();
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
