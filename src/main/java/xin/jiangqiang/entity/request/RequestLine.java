package xin.jiangqiang.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.CommonConstants;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.utils.RegExpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLine {
    private RequestMethod method = RequestMethod.GET;
    private String url;
    private String version;

    {
        this.method = RequestMethod.GET;
        this.version = HttpHeaderValue.VERSION;
    }

    public RequestLine(String url) {
        this.url = url;
    }

    public String builder() {
        String tmpUrl = convertUrl(url);
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("URL不能为空");
        }
        return this.method.getName() + CommonConstants.BLANKSPACE + tmpUrl + CommonConstants.BLANKSPACE + this.version + CommonConstants.CRLF;
    }

    /**
     * 去掉URL的协议和域名部分
     *
     * @param url
     * @return
     */
    private static String convertUrl(String url) {
        if (RegExpUtils.isMatch(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*.*")) {
            Pattern r = Pattern.compile("^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*");
            // 现在创建 matcher 对象
            Matcher matcher = r.matcher(url);
            if (matcher.find()) {
                String group = matcher.group();
                String substring = url.substring(group.length());
                return StringUtils.isEmpty(substring) ? "/" : substring;
            } else {
                throw new RuntimeException("URL无效");
            }
        } else {
            throw new RuntimeException("URL无效");
        }
    }

}
