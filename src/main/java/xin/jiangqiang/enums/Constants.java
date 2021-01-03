package xin.jiangqiang.enums;

import lombok.Getter;

/**
 * 定义字符串常量
 *
 * @author jiangqiang
 * @date 2021/1/3 9:52
 */
@Getter
public enum Constants {
    BLANKSPACE(" "), CR("\r"), LF("\n"), CRLF("\r\n"),COLON(":");

    private String value;

    Constants(String value) {
        this.value = value;
    }


}
