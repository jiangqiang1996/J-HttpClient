package top.jiangqiang.httpclient;

import lombok.extern.slf4j.Slf4j;
import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.request.body.impl.RequestFormDataBody;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;
import top.jiangqiang.httpclient.enums.HttpStructure;
import top.jiangqiang.httpclient.enums.RequestMethod;
import top.jiangqiang.httpclient.interceptor.AbstractInterceptor;
import top.jiangqiang.httpclient.net.Sender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangqiang
 * @date 2021/1/6 14:35
 */
@Slf4j
public class TestUpload {
    public static void main(String[] args) {
        String url = "http://localhost:8080/upload";
        Sender sender = new Sender();
        RequestFormDataBody requestBody = new RequestFormDataBody();

        requestBody.addParam("name", "随心");
        requestBody.addParam("single_file", new File("D:\\Documents\\file/456.png"));
        List<File> files = new ArrayList<>();
        files.add(new File("D:\\Documents\\file/asd.txt"));
        files.add(new File("D:\\Documents\\file/dasd.txt"));
        files.add(new File("D:\\Documents\\file/111"));
        files.add(new File("D:\\Documents\\file/aaa"));
        requestBody.addParam("batch_files", files);

        RequestEntity requestEntity = new RequestEntity(requestBody);
        requestEntity.setMethod(RequestMethod.POST);
        sender.setInterceptor(new AbstractInterceptor() {
            @Override
            public void beforeRequest(RequestEntity requestEntity) {
                super.beforeRequest(requestEntity);
                log.info("请求报文：\n{}", requestEntity.formatToString(HttpStructure.LINE, HttpStructure.HEAD, HttpStructure.BODY));
            }

            @Override
            public void afterRequest(ResponseEntity responseEntity) {
                super.afterRequest(responseEntity);
                log.info("响应报文：\n{}", responseEntity.formatToString(HttpStructure.LINE, HttpStructure.HEAD));
            }
        });
        sender.setRequestEntity(requestEntity);
        ResponseEntity responseEntity = sender.send(url);
    }
}
