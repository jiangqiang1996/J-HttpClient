package xin.jiangqiang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
    public static Boolean isMatch(String str, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
