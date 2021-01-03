package xin.jiangqiang.callback;

/**
 * @author jiangqiang
 * @date 2021/1/3 12:00
 */
public interface CallBack<T> {
    void process(T t);//todo 后期直接传入response对象
}
