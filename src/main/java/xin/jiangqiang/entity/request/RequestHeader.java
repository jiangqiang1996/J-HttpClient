package xin.jiangqiang.entity.request;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.CommonConstants;
import xin.jiangqiang.constants.HttpRequestHeaderType;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.entity.common.Cookie;
import xin.jiangqiang.utils.HttpUtils;
import xin.jiangqiang.utils.RegExpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:40
 */
@ToString
@NoArgsConstructor
public class RequestHeader {
    private final Map<String, String> map = new HashMap<>();
    private final List<Cookie> cookies = new ArrayList<>();

    {
        put(HttpRequestHeaderType.USER_AGENT, CommonConstants.PROJECT_VERSION);
        put(HttpRequestHeaderType.CONNECTION, HttpHeaderValue.CLOSE);
        put(HttpRequestHeaderType.ACCEPT, "*/*");
    }

    public String builder() {
        StringBuilder stringBuilder = HttpUtils.mapToHttpStringBuilder(map);
        if (cookies.size() != 0) {//有cookie
            stringBuilder.append("Cookie").append(CommonConstants.COLON).append(CommonConstants.BLANKSPACE);
            for (Cookie cookie : cookies) {
                stringBuilder.append(cookie.getName()).append("=")
                        .append(cookie.getValue()).append(";")
                        .append(CommonConstants.BLANKSPACE);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除最后的分号
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除最后的空格
            stringBuilder.append(CommonConstants.CRLF);
        }
        stringBuilder.append(CommonConstants.CRLF);
        return stringBuilder.toString();
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addCookies(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
    }

    public void putAll(Map<String, String> map) {
        if (StringUtils.isNotEmpty(map.get("cookie"))) {
            throw new RuntimeException("不允许使用putAll方法设置cookie");
        }
        this.map.putAll(map);

    }

    public void put(String name, String value) {
        if (StringUtils.isNotEmpty(map.get("cookie"))) {
            throw new RuntimeException("不允许使用put方法设置cookie");
        }
        this.map.put(name, value);
    }

    public String getHeader(String name) {
        return this.map.get(name);
    }

    public Map<String, String> getHeaders() {
        return this.map;
    }

    public void remove(String name) {
        this.map.remove(name);
    }

    public void removeAll() {
        this.map.clear();
    }

    public void setHost(String url) {
        if (RegExpUtils.isMatch(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*.*")) {
            Pattern r = Pattern.compile("^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*");
            // 现在创建 matcher 对象
            Matcher matcher = r.matcher(url);
            if (matcher.find()) {
                String group = matcher.group();
                String host = null;
                if (group.startsWith("https://")) {
                    host = group.substring(8);
                } else if (group.startsWith("http")) {
                    host = group.substring(7);
                }
                this.map.put(HttpRequestHeaderType.HOST, host);
            } else {
                throw new RuntimeException("URL无效");
            }
        } else {
            throw new RuntimeException("URL无效");
        }
    }

}
