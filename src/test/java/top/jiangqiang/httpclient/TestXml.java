package top.jiangqiang.httpclient;

import lombok.extern.slf4j.Slf4j;
import top.jiangqiang.httpclient.constants.HttpHeaderValue;
import top.jiangqiang.httpclient.constants.HttpRequestHeaderType;
import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.request.body.impl.CommonRequestBody;
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
public class TestXml {
    public static void main(String[] args) {
        String url = "http://localhost:8080/xml";
        Sender sender = new Sender();
        String str = "";
        CommonRequestBody requestBody = new CommonRequestBody("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<message> \n" +
                "  <orderlist> \n" +
                "    <order> \n" +
                "      <lotterytype>2004</lotterytype>  \n" +
                "      <phase>201409209</phase>  \n" +
                "      <orderid>zy2014090234322</orderid>  \n" +
                "      <playtype>200401</playtype>  \n" +
                "      <betcode>00,02,03,04,05,06,07^</betcode>  \n" +
                "      <multiple>1</multiple>  \n" +
                "      <amount>2</amount>  \n" +
                "      <add>0</add>  \n" +
                "      <endtime>结束时间</endtime> \n" +
                "    </order> \n" +
                "  </orderlist> \n" +
                "</message>");

        RequestEntity requestEntity = new RequestEntity(requestBody);
        requestEntity.addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_XML);
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
