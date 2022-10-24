package top.jiangqiang.httpclient.entity.response;

import lombok.ToString;
import top.jiangqiang.httpclient.constants.CommonConstants;
import top.jiangqiang.httpclient.entity.common.Cookie;
import top.jiangqiang.httpclient.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangqiang
 * @date 2021/1/3 16:40
 */
@ToString
public class ResponseHeader {
    private final Map<String, String> map = new HashMap<>();//不存储cookie
    private final List<Cookie> cookies = new ArrayList<>();
    private final List<String> cookieStrings = new ArrayList<>();//存储cookie字符串

    public String builder() {
        StringBuilder stringBuilder = HttpUtils.mapToHttpStringBuilder(map);
        stringBuilder.append(builderCookieToString());
        stringBuilder.append(CommonConstants.CRLF);
        return stringBuilder.toString();
    }

    private StringBuilder builderCookieToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : cookieStrings) {
            stringBuilder.append("Set-Cookie:").append(CommonConstants.BLANKSPACE).append(string).append(CommonConstants.CRLF);
        }
        return stringBuilder;
    }

    public void putAll(Map<String, String> map) {
        map.remove("Set-Cookie");//不存储cookie
        this.map.putAll(map);
    }

    public void put(String name, String value) {
        if (!"Set-Cookie".equals(name)) {//不存储cookie
            this.map.put(name, value);
        }
    }

    public void addAllCookies(List<String> cookieStrings) {
        for (String str : cookieStrings) {
            cookies.add(Cookie.getInstance(str));
        }
        this.cookieStrings.addAll(cookieStrings);
    }

    public void addCookie(String cookieString) {
        cookies.add(Cookie.getInstance(cookieString));
        cookieStrings.add(cookieString);
    }

    public List<Cookie> getCookies() {
        return this.cookies;
    }

    public Cookie getCookie(String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public Map<String, String> getHeaders() {
        return this.map;
    }

    public String getHeader(String name) {
        return this.map.get(name);
    }
}
