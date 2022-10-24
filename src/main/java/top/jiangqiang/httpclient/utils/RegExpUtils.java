package top.jiangqiang.httpclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
    public static Boolean isMatch(String str, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();//完全匹配才返回true
    }

    /**
     * 获取满足正则表达式的子字符串，找不到返回空字符串
     *
     * @param str
     * @param regEx
     * @return
     */
    public static String findMatchString(String str, String regEx) {
        Pattern r = Pattern.compile(regEx);
        // 现在创建 matcher 对象
        Matcher matcher = r.matcher(str);
        if (matcher.find()) {
            String group = matcher.group();
            return group;
        } else {
            return "";
        }
    }
}
