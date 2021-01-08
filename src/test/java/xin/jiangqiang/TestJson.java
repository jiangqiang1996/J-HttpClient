package xin.jiangqiang;

import lombok.extern.slf4j.Slf4j;
import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.constants.HttpRequestHeaderType;
import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.request.body.impl.RequestBodyDefault;
import xin.jiangqiang.entity.request.body.impl.RequestJSONBody;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.enums.HttpStructure;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.interceptor.AbstractInterceptor;
import xin.jiangqiang.net.Sender;

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
