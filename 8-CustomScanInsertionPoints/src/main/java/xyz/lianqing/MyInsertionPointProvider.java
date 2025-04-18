package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPointProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 自定义插入点提供者实现类
 * 实现了 AuditInsertionPointProvider 接口，用于提供自定义的审计插入点
 * 
 * 主要功能：
 * 1. 检测请求中的特定参数
 * 2. 为符合条件的参数创建审计插入点
 * 3. 管理插入点的创建和提供
 */
class MyInsertionPointProvider implements AuditInsertionPointProvider {
    private final MontoyaApi api;  // Burp API 接口实例

    /**
     * 构造函数
     * 
     * @param api Burp API 接口实例
     */
    MyInsertionPointProvider(MontoyaApi api) {
        this.api = api;
    }

    /**
     * 提供插入点列表
     * 根据请求参数创建相应的审计插入点
     * 
     * @param baseHttpRequestResponse 基础 HTTP 请求响应
     * @return 审计插入点列表
     */
    @Override
    public List<AuditInsertionPoint> provideInsertionPoints(HttpRequestResponse baseHttpRequestResponse) {
        // 获取请求中的所有参数
        List<ParsedHttpParameter> parameters = baseHttpRequestResponse.request().parameters();

        // 过滤出名为 "data" 的参数，并为每个参数创建审计插入点
        return parameters.stream()
                .filter(p -> p.name().equals("data"))
                .map(p -> new MyAuditInsertionPoint(api, baseHttpRequestResponse, p))
                .collect(toList());
    }
}
