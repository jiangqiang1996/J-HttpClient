package xin.jiangqiang;

import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.RequestEntity;
import xin.jiangqiang.entity.RequestLine;
import xin.jiangqiang.net.Sender;

import java.nio.charset.StandardCharsets;

/**
 * @author jiangqiang
 * @date 2021/1/3 12:49
 */
public class TestCallBack implements CallBack<byte[]> {
    public static void main(String[] args) {
        String url = "http://www.baidu.com/";
        Sender sender = new Sender();
        sender.setCallBack(new TestCallBack());
        RequestEntity requestEntity = new RequestEntity();
        sender.setRequestEntity(requestEntity);
        byte[] bytes = sender.send(url);
        if (bytes != null) {//异步请求时数组为null
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }
    }

    @Override
    public void process(byte[] bytes) {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}
