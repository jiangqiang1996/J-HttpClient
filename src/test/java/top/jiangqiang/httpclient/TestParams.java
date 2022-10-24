package top.jiangqiang.httpclient;

import lombok.extern.slf4j.Slf4j;
import top.jiangqiang.httpclient.constants.HttpHeaderValue;
import top.jiangqiang.httpclient.entity.common.Cookie;
import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestBodyDefault;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;
import top.jiangqiang.httpclient.constants.HttpRequestHeaderType;
import top.jiangqiang.httpclient.enums.HttpStructure;
import top.jiangqiang.httpclient.enums.RequestMethod;
import top.jiangqiang.httpclient.interceptor.AbstractInterceptor;
import top.jiangqiang.httpclient.net.Sender;

/**
 * @author jiangqiang
 * @date 2021/1/4 9:50
 */
@Slf4j
public class TestParams {
    public static void main(String[] args) {
        String url = "http://blog.jiangqiang.xin/api/admin/login";
        Sender sender = new Sender();
        RequestBodyDefault requestBody = new RequestBodyDefault();

        requestBody.addParam("username", "蒋樯");
        requestBody.addParam("password", "19961226qwe+++");
        RequestEntity requestEntity = new RequestEntity(requestBody);
        requestEntity.addCookie(Cookie.getInstance(" BAIDUID=6C16CEF00A5C374087B2AC031537516C:FG=1; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com "));
        requestEntity.addCookie(Cookie.getInstance(" BIDUPSID=6C16CEF00A5C374087B2AC031537516C; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com"));
        requestEntity.addCookie(Cookie.getInstance("PSTM=1609831367; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com"));
        requestEntity.addCookie(Cookie.getInstance(" BAIDUID=6C16CEF00A5C37404A7809E41BEF0D4E:FG=1; max-age=31536000; expires=Wed, 05-Jan-22 07:22:47 GMT; domain=.baidu.com; path=/; version=1; comment=bd"));
        requestEntity.addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_JSON);
        requestEntity.setMethod(RequestMethod.POST);
        sender.setInterceptor(new AbstractInterceptor() {
            @Override
            public void beforeRequest(RequestEntity requestEntity) {
                super.beforeRequest(requestEntity);
                log.info("2");
                log.info("请求报文：\n{}", requestEntity.formatToString(HttpStructure.LINE, HttpStructure.HEAD, HttpStructure.BODY));
            }

            @Override
            public void afterRequest(ResponseEntity responseEntity) {
                super.afterRequest(responseEntity);
                log.info("3");
                log.info("响应报文：\n{}", responseEntity.formatToString(HttpStructure.LINE, HttpStructure.HEAD));
            }
        });
        sender.setRequestEntity(requestEntity);
        ResponseEntity responseEntity = sender.send(url);
//        log.info(responseEntity.toString());
    }
}
