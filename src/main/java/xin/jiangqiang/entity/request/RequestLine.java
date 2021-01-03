package xin.jiangqiang.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.enums.Constants;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.utils.RegExpUtils;

import java.util.Objects;

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
        version = "HTTP/1.1";
    }

    public RequestLine(String url) {
        this.url = url;
    }

    public String builder() {
        String tmpUrl = convertUrl(url);
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("URL不能为空");
        }
        return this.method.getName() + Constants.BLANKSPACE.getValue() + tmpUrl + Constants.BLANKSPACE.getValue() + this.version + Constants.CRLF.getValue();
    }

    private static String convertUrl(String str) {
        String tmpStr = "/";
        if (RegExpUtils.isMatch(str, "^(http|https)(://).*(/)")) {
            String[] split = str.split("^(http|https)(://)\\.*(/)");
            int length = split[0].length();
            String substring = str.substring(length);
            if (substring.length() != 0) {
                tmpStr = str.substring(length);
            }
        }
        return tmpStr;
    }

}
