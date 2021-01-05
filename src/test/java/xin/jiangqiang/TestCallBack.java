package xin.jiangqiang;

import lombok.extern.slf4j.Slf4j;
import xin.jiangqiang.callback.CallBack;
import xin.jiangqiang.entity.common.Cookie;
import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.enums.HttpStructure;
import xin.jiangqiang.interceptor.AbstractInterceptor;
import xin.jiangqiang.net.Sender;

/**
 * @author jiangqiang
 * @date 2021/1/3 12:49
 */
@Slf4j
public class TestCallBack implements CallBack<ResponseEntity> {
    public static void main(String[] args) {
        String url = "http://www.baidu.com/";
        Sender sender = new Sender();
        sender.setCallBack(new TestCallBack());
        RequestEntity requestEntity = new RequestEntity(null);
        requestEntity.addCookie(Cookie.getInstance(" BAIDUID=6C16CEF00A5C374087B2AC031537516C:FG=1; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com "));
        requestEntity.addCookie(Cookie.getInstance(" BIDUPSID=6C16CEF00A5C374087B2AC031537516C; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com"));
        requestEntity.addCookie(Cookie.getInstance("PSTM=1609831367; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com"));
        requestEntity.addCookie(Cookie.getInstance(" BAIDUID=6C16CEF00A5C37404A7809E41BEF0D4E:FG=1; max-age=31536000; expires=Wed, 05-Jan-22 07:22:47 GMT; domain=.baidu.com; path=/; version=1; comment=bd"));

        sender.setRequestEntity(requestEntity);
        log.info("1");
        sender.setInterceptor(new AbstractInterceptor() {
            @Override
            public void beforeRequest(RequestEntity requestEntity) {
                super.beforeRequest(requestEntity);
                log.info("2");
                log.info("请求报文：\n{}", requestEntity.formatToString(HttpStructure.LINE,HttpStructure.HEAD,HttpStructure.BODY));
            }

            @Override
            public void afterRequest(ResponseEntity responseEntity) {
                super.afterRequest(responseEntity);
                log.info("3");
                log.info("响应报文：\n{}", responseEntity.formatToString(HttpStructure.LINE,HttpStructure.HEAD));
            }
        });
        ResponseEntity responseEntity = sender.send(url);
        log.info("4");
        if (responseEntity != null) {//异步请求时为null
            log.info("5");
        }
    }

    @Override
    public void process(ResponseEntity responseEntity) {
        log.info("6");
    }
}
