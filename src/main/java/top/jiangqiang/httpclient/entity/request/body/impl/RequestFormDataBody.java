package top.jiangqiang.httpclient.entity.request.body.impl;

import lombok.Getter;
import lombok.ToString;
import top.jiangqiang.httpclient.entity.request.body.RequestBody;
import top.jiangqiang.httpclient.utils.CommonUtils;
import top.jiangqiang.httpclient.utils.FileUtils;
import top.jiangqiang.httpclient.utils.NetUtils;
import top.jiangqiang.httpclient.constants.CommonConstants;
import top.jiangqiang.httpclient.constants.HttpRequestHeaderType;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 提交复杂参数(文件),必须设置contentType
 *
 * @author jiangqiang
 * @date 2021/1/3 9:49
 */
@ToString
@Getter
public class RequestFormDataBody implements RequestBody {
    private final Map<String, Object> params = new HashMap<>();//Object可以是字符串,可以是File类型,可以是List<File>类型
    private final String id = UUID.randomUUID().toString().replaceAll("-", "");

    public String builder(String contentType) {
        String separator = "----------------------------" + id;//每一个参数结束的分隔符
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            stringBuilder.append(separator).append(CommonConstants.CRLF);
            stringBuilder.append("Content-Disposition").append(CommonConstants.COLON).append(CommonConstants.BLANKSPACE).append("form-data;")
                    .append(CommonConstants.BLANKSPACE).append("name=\"").append(name).append("\"");
            if (value instanceof String) {
                String val = (String) value;
                stringBuilder.append(CommonConstants.CRLF).append(CommonConstants.CRLF).append(CommonUtils.charsetConvert(val)).append(CommonConstants.CRLF);
            } else if (value instanceof File) {//单个文件
                fileToString(stringBuilder, (File) value);
            } else if (value instanceof List<?>) {//列表
                List<?> list = (List<?>) value;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Object next = list.get(i);
                        if (i != 0) {
                            stringBuilder.append(separator).append(CommonConstants.CRLF);
                            stringBuilder.append("Content-Disposition").append(CommonConstants.COLON).append(CommonConstants.BLANKSPACE).append("form-data;")
                                    .append(CommonConstants.BLANKSPACE).append("name=\"").append(name).append("\"");
                        }
                        if (next instanceof File) {//如果是文件
                            File file = (File) next;
                            fileToString(stringBuilder, file);
                        } else {//不是文件
                            throw new RuntimeException("不支持的类型");
                        }
                    }
                } else {
                    stringBuilder.append(CommonConstants.CRLF).append(CommonConstants.CRLF).append(CommonConstants.CRLF);
                }
            } else {
                throw new RuntimeException("不支持的类型");
            }
        }
        stringBuilder.append(separator).append("--").append(CommonConstants.CRLF);
        return stringBuilder.toString();
    }

    public RequestFormDataBody addAllParams(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    private StringBuilder fileToString(StringBuilder stringBuilder, File file) {
        stringBuilder.append(";").append(CommonConstants.BLANKSPACE).append("filename=\"").append(file.getName()).append("\"");
        stringBuilder.append(CommonConstants.CRLF);
        stringBuilder.append(HttpRequestHeaderType.CONTENT_TYPE).append(CommonConstants.COLON).append(CommonConstants.BLANKSPACE)
                .append(NetUtils.getMimeType(file.getName())).append(CommonConstants.CRLF).append(CommonConstants.CRLF);
        stringBuilder.append(new String(FileUtils.fileConvertToByteArray(file), StandardCharsets.ISO_8859_1)).append(CommonConstants.CRLF);
        return stringBuilder;
    }

    public RequestFormDataBody addParam(String name, Object value) {
        this.params.put(name, value);
        return this;
    }

    public RequestFormDataBody removeParam(String name) {
        this.params.remove(name);
        return this;
    }

    public RequestFormDataBody removeAllParams() {
        this.params.clear();
        return this;
    }

}
