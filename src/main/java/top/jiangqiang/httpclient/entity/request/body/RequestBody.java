package top.jiangqiang.httpclient.entity.request.body;

/**
 * @author jiangqiang
 * @date 2021/1/4 17:04
 */
public interface RequestBody {
    /**
     * 生成报文 ISO8859-1编码
     *
     * @param contentType
     * @return
     */
    String builder(String contentType);
}
