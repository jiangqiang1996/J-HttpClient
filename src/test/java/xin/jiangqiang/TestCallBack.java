package xin.jiangqiang;

import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.interceptor.AbstractInterceptor;
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
        System.out.println(1);
        sender.setInterceptor(new AbstractInterceptor() {
            @Override
            public void beforeRequest(RequestEntity requestEntity) {
                super.beforeRequest(requestEntity);
                System.out.println("_________________________________");
                System.out.println(2);
            }

            @Override
            public void afterRequest(ResponseEntity responseEntity) {
                super.afterRequest(responseEntity);
                System.out.println("_________________________________");
                System.out.println(3);
            }
        });
        byte[] bytes = sender.send(url);
        System.out.println(4);
        if (bytes != null) {//异步请求时数组为null
//            System.out.println(new String(bytes, StandardCharsets.UTF_8));
            System.out.println(5);
        }
    }

    @Override
    public void process(byte[] bytes) {
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        System.out.println(6);
    }
}
