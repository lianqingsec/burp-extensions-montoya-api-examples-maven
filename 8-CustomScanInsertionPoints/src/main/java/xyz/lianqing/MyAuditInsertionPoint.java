package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.utilities.Utilities;

import java.util.List;

import static burp.api.montoya.http.message.params.HttpParameter.parameter;

/**
 * 自定义审计插入点实现类
 * 实现了 AuditInsertionPoint 接口，用于处理 Base64 编码的输入参数
 * 
 * 主要功能：
 * 1. 解析和处理 Base64 编码的参数值
 * 2. 提取和定位输入字符串
 * 3. 构建包含有效载荷的 HTTP 请求
 */
class MyAuditInsertionPoint implements AuditInsertionPoint
{
    private final String insertionPointPrefix;    // 插入点前缀
    private final String insertionPointSuffix;    // 插入点后缀
    private final HttpRequestResponse requestResponse;  // HTTP 请求响应对象
    private final ParsedHttpParameter parameter;  // 解析后的 HTTP 参数
    private final String baseValue;               // 基础值
    private final Utilities utilities;            // 工具类实例

    /**
     * 构造函数
     * 
     * @param api Burp API 接口实例
     * @param baseHttpRequestResponse 基础 HTTP 请求响应
     * @param parameter 要处理的 HTTP 参数
     */
    MyAuditInsertionPoint(MontoyaApi api, HttpRequestResponse baseHttpRequestResponse, ParsedHttpParameter parameter)
    {
        this.requestResponse = baseHttpRequestResponse;
        this.parameter = parameter;
        this.utilities = api.utilities();

        String paramValue = parameter.value();

        // 对参数值进行 URL 解码和 Base64 解码
        String urlDecoded = utilities.urlUtils().decode(paramValue);
        ByteArray byteData = utilities.base64Utils().decode(urlDecoded);

        String data = byteData.toString();

        // 解析输入字符串在解码数据中的位置
        int start = data.indexOf("input=") + 6;
        int end = data.indexOf("&", start);

        if (end == -1)
        {
            end = data.length();
        }

        baseValue = data.substring(start, end);

        insertionPointPrefix = data.substring(0, start);
        insertionPointSuffix = data.substring(end);
    }

    /**
     * 获取插入点名称
     * 
     * @return 插入点名称
     */
    @Override
    public String name()
    {
        return "Base64 包装的输入";
    }

    /**
     * 获取基础值
     * 
     * @return 基础值
     */
    @Override
    public String baseValue()
    {
        return baseValue;
    }

    /**
     * 构建包含有效载荷的 HTTP 请求
     * 
     * @param payload 要插入的有效载荷
     * @return 更新后的 HTTP 请求
     */
    @Override
    public HttpRequest buildHttpRequestWithPayload(ByteArray payload)
    {
        // 使用指定的有效载荷构建原始数据
        String input = insertionPointPrefix + payload.toString() + insertionPointSuffix;

        // 对数据进行 Base64 编码和 URL 编码
        String updatedParameterValue = utilities.urlUtils().encode(utilities.base64Utils().encodeToString(input));

        HttpParameter updatedParameter = parameter(parameter.name(), updatedParameterValue, parameter.type());

        return requestResponse.request().withUpdatedParameters(updatedParameter);
    }

    /**
     * 获取问题高亮范围
     * 
     * @param payload 有效载荷
     * @return 问题高亮范围列表
     */
    @Override
    public List<Range> issueHighlights(ByteArray payload)
    {
        return null;
    }
}
