package xin.jiangqiang.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import xin.jiangqiang.enums.Constants;
import xin.jiangqiang.enums.RequestMethod;

import java.util.Objects;

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
        return this.version + Constants.BLANKSPACE.getValue() + this.responseCode + Constants.BLANKSPACE.getValue() + this.desc + Constants.CRLF.getValue();
    }
}
