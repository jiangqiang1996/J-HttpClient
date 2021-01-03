package xin.jiangqiang.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

/**
 * 公共工具类
 *
 * @author jiangqiang
 * @date 2021/1/3 11:50
 */
public class CommonUtils {
    /**
     * 关闭资源
     *
     * @param objects
     */
    public static void close(Closeable... objects) {
        for (Closeable closeable : objects) {
            if (!Objects.isNull(closeable)) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
