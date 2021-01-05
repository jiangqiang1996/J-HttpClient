package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.constants.HttpRequestHeaderType;
import xin.jiangqiang.entity.common.Cookie;
import xin.jiangqiang.enums.HttpStructure;

import java.util.*;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:16
 */
@AllArgsConstructor
@ToString(exclude = "content")
@Getter
public class ResponseEntity {
    private final ResponseLine responseLine;
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;
    private final byte[] content;//整个响应报文对应的字节数组,包括行和头

    /**
     * 返回规范的响应报文信息
     *
     * @return
     */
    public String builderToString() {
        String requestStr = responseLine.builder() + responseHeader.builder() + responseBody.builder();
        return requestStr;
    }

    public Integer getResponseCode() {
        return responseLine.getResponseCode();
    }

    public String getVersion() {
        return responseLine.getVersion();
    }

    public String getDesc() {
        return responseLine.getDesc();
    }

    public List<Cookie> getCookies() {
        return responseHeader.getCookies();
    }

    public Cookie getCookie(String name) {
        return responseHeader.getCookie(name);
    }

    public Map<String, String> getHeaders() {
        return responseHeader.getHeaders();
    }

    public String getHeader(String name) {
        return responseHeader.getHeader(name);
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
            stringBuilder.append(responseLine.builder());
        }
        if (httpStructures1.contains(HttpStructure.HEAD)) {
            stringBuilder.append(responseHeader.builder());
        }
        if (httpStructures1.contains(HttpStructure.BODY)) {
            stringBuilder.append(responseBody.builder());
        }
        return stringBuilder.toString();
    }
}
