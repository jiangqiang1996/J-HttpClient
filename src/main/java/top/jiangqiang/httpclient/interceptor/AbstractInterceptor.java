package top.jiangqiang.httpclient.interceptor;

import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:19
 */
public abstract class AbstractInterceptor implements Interceptor {

    @Override
    public void beforeRequest(RequestEntity requestEntity) {

    }

    @Override
    public void afterRequest(ResponseEntity responseEntity) {

    }
}
