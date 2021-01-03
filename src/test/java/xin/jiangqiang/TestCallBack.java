package xin.jiangqiang;

import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.net.Sender;

import java.nio.charset.StandardCharsets;

/**
 * @author jiangqiang
 * @date 2021/1/3 12:49
 */
public class TestCallBack implements CallBack<byte[]> {
    public static void main(String[] args) {
        String url = "http://www.baidu.com/";
        Sender sender = new Sender(url);
        sender.setCallBack(new TestCallBack());
        sender.send();
    }

    @Override
    public void process(byte[] bytes) {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}
