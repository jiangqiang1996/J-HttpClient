package xin.jiangqiang;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangqiang
 * @date 2021/1/8 9:45
 */
@Slf4j
public class TestEncodeAndBecode {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String string = URLEncoder.encode("随 ~!@#$%^&*()心%%=  =++`1234567890-=[]{}\\|;':\"'/.,<>", StandardCharsets.UTF_8);
        log.info(string);
        System.out.println(URLDecoder.decode(string));
    }
}
