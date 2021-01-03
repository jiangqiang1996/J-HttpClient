package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.enums.Constants;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangqiang
 * @date 2021/1/3 16:40
 */
@Getter
@AllArgsConstructor
public class ResponseBody {
    private final byte[] content;//响应正文对应的字节数组

    public String builder() {
        return new String(content, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "content=" + new String(content, StandardCharsets.UTF_8) +
                '}';
    }
}
