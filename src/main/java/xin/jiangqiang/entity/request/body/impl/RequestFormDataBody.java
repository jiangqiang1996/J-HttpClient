package xin.jiangqiang.entity.request.body.impl;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交复杂参数(文件),必须设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestFormDataBody {
    private final List<Byte> bytes = new ArrayList<>();

    public String builder(String contentType) {//todo 尚未实现提交文件参数
        return "";
    }


}
