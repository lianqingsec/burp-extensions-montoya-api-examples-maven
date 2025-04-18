package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import burp.api.montoya.utilities.Base64Utils;
import burp.api.montoya.utilities.URLUtils;

import static burp.api.montoya.intruder.PayloadProcessingResult.usePayload;

/**
 * 自定义载荷处理器实现类
 * 实现了 PayloadProcessor 接口，用于处理序列化的输入数据
 * 
 * 主要功能：
 * 1. 处理 Base64 编码的输入数据
 * 2. 支持 URL 编码/解码
 * 3. 自动处理序列化数据的重建
 */
public class MyPayloadProcessor implements PayloadProcessor {
    // 输入参数前缀
    public static final String INPUT_PREFIX = "input=";
    
    // Burp API 接口实例
    private final MontoyaApi api;

    /**
     * 构造函数
     * 
     * @param api Burp API 接口实例
     */
    public MyPayloadProcessor(MontoyaApi api) {
        this.api = api;
    }

    /**
     * 获取显示名称
     * 
     * @return 处理器的显示名称
     */
    @Override
    public String displayName() {
        return "序列化输入包装器";
    }

    /**
     * 处理载荷数据
     * 对序列化的输入数据进行处理
     * 
     * @param payloadData 载荷数据
     * @return 处理结果
     */
    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {
        // 获取工具类实例
        Base64Utils base64Utils = api.utilities().base64Utils();
        URLUtils urlUtils = api.utilities().urlUtils();

        // 解码基础值
        String dataParameter = base64Utils.decode(urlUtils.decode(payloadData.insertionPoint().baseValue())).toString();

        // 解析输入字符串在解码数据中的位置
        String prefix = findPrefix(dataParameter);
        if (prefix == null) {
            return usePayload(payloadData.currentPayload());
        }

        String suffix = findSuffix(dataParameter);

        // 使用新的载荷重建序列化数据
        String rebuiltDataParameter = prefix + payloadData.currentPayload() + suffix;
        ByteArray reserializedDataParameter = urlUtils.encode(base64Utils.encode(rebuiltDataParameter));

        return usePayload(reserializedDataParameter);
    }

    /**
     * 查找前缀
     * 在数据参数中查找输入前缀
     * 
     * @param dataParameter 数据参数
     * @return 前缀，如果未找到则返回 null
     */
    private String findPrefix(String dataParameter) {
        int start = dataParameter.indexOf(INPUT_PREFIX);

        if (start == -1) {
            return null;
        }

        start += INPUT_PREFIX.length();

        return dataParameter.substring(0, start);
    }

    /**
     * 查找后缀
     * 在数据参数中查找输入后缀
     * 
     * @param dataParameter 数据参数
     * @return 后缀
     */
    private String findSuffix(String dataParameter) {
        int start = dataParameter.indexOf(INPUT_PREFIX);

        int end = dataParameter.indexOf("&", start);

        if (end == -1) {
            end = dataParameter.length();
        }

        return dataParameter.substring(end);
    }
}
