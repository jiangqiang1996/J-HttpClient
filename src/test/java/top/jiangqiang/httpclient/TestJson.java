package top.jiangqiang.httpclient;

import lombok.extern.slf4j.Slf4j;
import top.jiangqiang.httpclient.constants.HttpHeaderValue;
import top.jiangqiang.httpclient.constants.HttpRequestHeaderType;
import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestJSONBody;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;
import top.jiangqiang.httpclient.enums.HttpStructure;
import top.jiangqiang.httpclient.enums.RequestMethod;
import top.jiangqiang.httpclient.interceptor.AbstractInterceptor;
import top.jiangqiang.httpclient.net.Sender;

/**
 * @author jiangqiang
 * @date 2021/1/4 9:50
 */
@Slf4j
public class TestJson {
    public static void main(String[] args) {
        String url = "http://localhost:8080/json";
        Sender sender = new Sender();
        RequestJSONBody requestBody = new RequestJSONBody();

        requestBody.addParam("name", "  随心%%==++                  ");
        requestBody.addParam("age", "  11 ");
        RequestEntity requestEntity = new RequestEntity(requestBody);
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
