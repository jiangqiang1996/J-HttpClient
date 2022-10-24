package top.jiangqiang.httpclient.enums;

import lombok.Getter;

/**
 * @author jiangqiang
 * @date 2021/1/5 16:28
 */
@Getter
public enum HttpStructure {
    LINE("请求行或响应行"), HEAD("请求头或响应头"), BODY("请求体或响应体");
    String desc;//描述

    HttpStructure(String desc) {
        this.desc = desc;
    }
}
