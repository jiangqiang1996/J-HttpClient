package xin.jiangqiang;

import xin.jiangqiang.constants.HttpHeaderValue;
import xin.jiangqiang.entity.request.body.RequestBody;
import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.request.body.impl.RequestBodyDefault;
import xin.jiangqiang.entity.request.body.impl.RequestFormBody;
import xin.jiangqiang.entity.request.body.impl.RequestJSONBody;
import xin.jiangqiang.entity.response.ResponseEntity;
import xin.jiangqiang.constants.HttpRequestHeaderType;
import xin.jiangqiang.enums.RequestMethod;
import xin.jiangqiang.net.Sender;

/**
 * @author jiangqiang
 * @date 2021/1/4 9:50
 */
public class TestParams {
    public static void main(String[] args) {
        String url = "http://blog.jiangqiang.xin/api/admin/login";
        Sender sender = new Sender();
        RequestBodyDefault requestBody = new RequestBodyDefault();

        requestBody.addParam("username", "蒋樯");
        requestBody.addParam("password", "19961226qwe+++");
        RequestEntity requestEntity = new RequestEntity(requestBody);

        requestEntity.addHeader(HttpRequestHeaderType.CONTENT_TYPE, HttpHeaderValue.CONTENTTYPE_JSON);
        requestEntity.setMethod(RequestMethod.POST);
//        requestEntity.addHeader(HttpHeaderType.Content_Length.getValue(), "63");
        sender.setRequestEntity(requestEntity);
        ResponseEntity responseEntity = sender.send(url);
//        System.out.println(responseEntity);

    }
}
