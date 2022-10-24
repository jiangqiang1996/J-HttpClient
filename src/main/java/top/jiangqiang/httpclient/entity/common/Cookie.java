package top.jiangqiang.httpclient.entity.common;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * cookie的各项属性建立之后就不能修改
 *
 * @author jiangqiang
 * @date 2021/1/5 9:32
 */
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Cookie implements Serializable {
    @Getter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String value;
    private final String domain;
    private final String path;
    private final String expires;
    private final Long maxAge;
    private final boolean secure;
    private final String comment;
    private final Integer version;

    private Cookie(String name, String value, String domain, String path, String expires, Long maxAge, boolean secure, String comment, Integer version) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.path = path;
        this.expires = expires;
        this.maxAge = maxAge;
        this.secure = secure;
        this.comment = comment;
        this.version = version;
    }

    /**
     * 字段太多，并且大部分字段可能都没用上，所以通过此方法简化创建过程
     *
     * @param str
     * @return
     */
    public static Cookie getInstance(String str) {
        str = str.trim();
        String[] splits = str.split(";");
        String name = "";
        String value = "";
        String domain = "";
        String path = "/";
        String expires = "";
        long maxAge = 0L;
        boolean secure = false;
        String comment = "";
        int version = 0;
        for (int i = 0; i < splits.length; i++) {
            if (StringUtils.isNotEmpty(splits[i].trim())) {
                String string = splits[i].trim();
                String[] tmpArray = string.split("=");
                String tmpKey = tmpArray[0];//等号左边
                String tmpVal = null;
                if (tmpArray[0].length() < string.trim().length()) {
                    tmpVal = string.substring(tmpArray[0].length() + 1).trim();//等号右边
                }
                if (i == 0) {
                    name = tmpKey;
                    value = tmpVal;
                } else {
                    if (tmpKey.equals("max-age")) {
                        maxAge = Long.parseLong(tmpVal);
                    } else if (tmpKey.equals("expires")) {
                        expires = tmpVal;
                    } else if (tmpKey.equals("domain")) {
                        domain = tmpVal;
                    } else if (tmpKey.equals("path")) {
                        path = tmpVal;
                    } else if (tmpKey.equals("version")) {
                        version = Integer.parseInt(tmpVal);
                    } else if (tmpKey.equals("secure")) {
                        if (tmpVal != null) {
                            secure = Boolean.parseBoolean(tmpVal);
                        } else {
                            secure = true;
                        }
                    } else if (tmpKey.equals("comment")) {
                        comment = tmpVal;
                    }
                }
            }
        }
        return new Cookie(name, value, domain, path, expires, maxAge, secure, comment, version);
    }
}
