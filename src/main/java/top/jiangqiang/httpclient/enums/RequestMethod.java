package top.jiangqiang.httpclient.enums;

import lombok.Getter;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:50
 */
@Getter
public enum RequestMethod {
    GET("GET", false), POST("POST", true),
    HEAD("HEAD", false), OPTIONS("OPTIONS", false),
    PUT("PUT", true), DELETE("DELETE", false),
    TRACE("TRACE", false), CONNECT("CONNECT", false);

    private String name;
    private Boolean hasBody;//是否有body

    RequestMethod(String name, Boolean hasBody) {
        this.name = name;
        this.hasBody = hasBody;
    }
}
