package xin.jiangqiang.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.CommonConstants;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.utils.NetUtils;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLine {
    private RequestMethod method = RequestMethod.GET;
    private String url;//完整URL，build时创建请求所需部分
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
        String hostContainPort = NetUtils.getHostContainPort(url);
        int length = hostContainPort.length();
        if (url.startsWith("https://")) {
            length = length + 8;
        } else if (url.startsWith("http://")) {
            length = length + 7;
        }
        if (url.length() == length) {//长度相同，返回/
            return "/";
        } else {
            return url.substring(length);
        }
    }

}
