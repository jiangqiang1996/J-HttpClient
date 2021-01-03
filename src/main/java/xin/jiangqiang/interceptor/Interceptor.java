package xin.jiangqiang.interceptor;

import xin.jiangqiang.entity.request.RequestEntity;
import xin.jiangqiang.entity.response.ResponseEntity;

/**
 * @author jiangqiang
 * @date 2021/1/3 15:14
 */
public interface Interceptor {
    void beforeRequest(RequestEntity requestEntity);

    void afterRequest(ResponseEntity responseEntity);
}
