package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:16
 */
@AllArgsConstructor
@ToString(exclude = "content")
@Getter
public class ResponseEntity {
    private final ResponseLine responseLine;
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;
    private final byte[] content;//整个响应报文对应的字节数组,包括行和头

    public String builderToString() {
        String requestStr = responseLine.builder() + responseHeader.builder() + responseBody.builder();
        return requestStr;
    }

}
