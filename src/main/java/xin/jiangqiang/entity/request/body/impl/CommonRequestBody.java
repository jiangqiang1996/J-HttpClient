package xin.jiangqiang.entity.request.body.impl;

import xin.jiangqiang.entity.request.body.RequestBody;

/**
 * 提交XML,HTTP,JAVASCRIPT等字符串内容,需要设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/4 17:22
 */
public class CommonRequestBody implements RequestBody {

    private final String str;

    public CommonRequestBody(String str) {
        this.str = str;
    }

    public String builder(String contentType) {
        return this.str;
    }
}
