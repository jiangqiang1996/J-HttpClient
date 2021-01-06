package xin.jiangqiang.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.constants.HttpRequestHeaderType;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangqiang
 * @date 2021/1/3 10:33
 */
@Slf4j
public class NetUtils {
    @SneakyThrows
    public static List<String> getIpsByName(String url) {
        url = getHost(url);
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

    public static String getHost(String url) {
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
                return host;
            }
        }
        throw new RuntimeException("URL无效");
    }
}
