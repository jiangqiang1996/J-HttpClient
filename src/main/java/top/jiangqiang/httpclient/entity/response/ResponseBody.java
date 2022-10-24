package top.jiangqiang.httpclient.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.jiangqiang.httpclient.constants.CommonConstants;

import java.nio.charset.StandardCharsets;

/**
 * @author jiangqiang
 * @date 2021/1/3 16:40
 */
@Getter
@AllArgsConstructor
public class ResponseBody {
    private final byte[] content;//响应正文对应的字节数组

    public String builder() {
        return new String(content, StandardCharsets.UTF_8) + CommonConstants.CRLF;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "content=" + new String(content, StandardCharsets.UTF_8) +
                '}';
    }
}
