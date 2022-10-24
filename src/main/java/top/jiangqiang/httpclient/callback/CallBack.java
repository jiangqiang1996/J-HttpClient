package top.jiangqiang.httpclient.callback;

/**
 * @author jiangqiang
 * @date 2021/1/3 12:00
 */
public interface CallBack<T> {
    void process(T t);
}
