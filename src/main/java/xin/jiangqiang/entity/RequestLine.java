package xin.jiangqiang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xin.jiangqiang.enums.Constants;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.utils.RegExpUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private String version = "HTTP/1.1";

    public RequestLine(String url) {
        this.url = url;
    }

    public String builder() {
        String tmpUrl = convertUrl(url);
        return this.method.getName() + Constants.BLANKSPACE.getValue() + tmpUrl + Constants.BLANKSPACE.getValue() + this.version + Constants.CRLF.getValue();
    }

    private static String convertUrl(String str) {
        String tmpStr = "/";
        if (RegExpUtil.isMatch(str, "^(http|https)(://).*(/)")) {
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
