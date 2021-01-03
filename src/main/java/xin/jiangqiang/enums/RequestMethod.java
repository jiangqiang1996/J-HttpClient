package xin.jiangqiang.enums;

import lombok.Getter;

/**
 * @author jiangqiang
 * @date 2021/1/3 9:50
 */
@Getter
public enum RequestMethod {
    GET("GET"), POST("POST");

    private String name;

    RequestMethod(String name) {
        this.name = name;
    }
}
