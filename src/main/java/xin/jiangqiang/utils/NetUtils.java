package xin.jiangqiang.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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

}
