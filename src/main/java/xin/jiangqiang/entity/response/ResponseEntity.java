package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.entity.common.Cookie;

import java.util.List;
import java.util.Map;

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
}
