package top.jiangqiang.httpclient.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.jiangqiang.httpclient.constants.MimeType;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangqiang
 * @date 2021/1/3 10:33
 */
@Slf4j
public class NetUtils {
    @SneakyThrows
    public static List<String> getIpsByName(String url) {
        url = getHost(url);//去除端口号
        if (url.contains("://")) {
            url = url.substring((url.indexOf("://") + 3));
        }
        String name = url.split("/")[0];
        InetAddress[] addresses = InetAddress.getAllByName(name);
        if (addresses.length == 0) {
            log.error("获取不到url对应的ip地址");
            throw new RuntimeException("UnKnow URL");
        }
        List<String> ipList = new ArrayList<>();
        for (InetAddress address : addresses) {
            ipList.add(address.getHostAddress());
        }
        return ipList;
    }

    /**
     * 获取域名部分，不包括端口号
     *
     * @param url
     * @return
     */
    public static String getHost(String url) {
        if (RegExpUtils.isMatch(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*.*")) {
            String group = RegExpUtils.findMatchString(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*");
            if (StringUtils.isNotEmpty(group)) {
                String host = null;
                if (group.startsWith("https://")) {
                    host = group.substring(8);
                } else if (group.startsWith("http")) {
                    host = group.substring(7);
                }
                return host;
            }
        }
        throw new RuntimeException("URL无效");
    }

    /**
     * 获取域名，包括端口号部分
     *
     * @param url
     * @return
     */
    public static String getHostContainPort(String url) {
        if (RegExpUtils.isMatch(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*.*")) {
            String group = RegExpUtils.findMatchString(url, "^(http|https)(://)([a-zA-Z0-9]*\\.)*[a-zA-Z0-9]*((:)\\d{1,5})+");
            if (StringUtils.isNotEmpty(group)) {
                String host = null;
                if (group.startsWith("https://")) {
                    host = group.substring(8);
                } else if (group.startsWith("http")) {
                    host = group.substring(7);
                }
                return host;
            } else {
                return getHost(url);
            }
        }
        throw new RuntimeException("URL无效");
    }

    public static String getMimeType(String str) {
        String mimeType;
        String suffixName = "";
        if (str.contains(".")) {
            suffixName = str.substring(str.lastIndexOf(".") + 1);
        }
        switch (suffixName) {
            case "txt":
                mimeType = MimeType.TEXT;
                break;
            case "":
            default:
                mimeType = MimeType.OCTET_STREAM;
        }
        return mimeType;
    }
}
