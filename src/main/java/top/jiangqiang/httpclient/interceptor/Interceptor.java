package top.jiangqiang.httpclient.interceptor;

import top.jiangqiang.httpclient.entity.request.RequestEntity;
import top.jiangqiang.httpclient.entity.response.ResponseEntity;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:14
 */
public interface Interceptor {
    void beforeRequest(RequestEntity requestEntity);

    void afterRequest(ResponseEntity responseEntity);
}
