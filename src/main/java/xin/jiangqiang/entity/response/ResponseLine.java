package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import xin.jiangqiang.constants.CommonConstants;

/**
 * @author jiangqiang
 * @date 2021/1/3 16:35
 */
@AllArgsConstructor
@Getter
@ToString
public class ResponseLine {
    final private Integer responseCode;
    final private String version;
    final private String desc;

    public String builder() {
        return this.version + CommonConstants.BLANKSPACE + this.responseCode + CommonConstants.BLANKSPACE + this.desc + CommonConstants.CRLF;
    }
}
