/*
 * 版权所有 (c) 2023. PortSwigger Ltd. 保留所有权利。
 *
 * 本代码可用于扩展 Burp Suite 社区版和 Burp Suite 专业版的功能，
 * 但须遵守这些产品的许可条款。
 */

package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.proxy.ProxyHttpRequestResponse;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;
import example.collaborator.poller.InteractionHandler;

import java.util.List;

/**
 * 交互处理器实现类
 * 处理 Collaborator 交互并创建审计问题
 * 
 * 主要功能：
 * 1. 处理 Collaborator 交互事件
 * 2. 记录交互信息
 * 3. 创建审计问题
 * 
 * 使用场景：
 * - 检测潜在的安全问题
 * - 记录安全事件
 * - 生成审计报告
 */
public class MyInteractionHandler implements InteractionHandler
{
    /**
     * Burp Suite API 实例
     * 用于访问 Burp 功能
     */
    private final MontoyaApi api;

    /**
     * 交互日志记录器
     * 用于记录交互信息
     */
    private final InteractionLogger interactionLogger;

    /**
     * 构造函数
     * 初始化交互处理器
     * 
     * @param api Burp Suite API 实例
     * @param interactionLogger 交互日志记录器
     */
    public MyInteractionHandler(MontoyaApi api, InteractionLogger interactionLogger)
    {
        this.api = api;
        this.interactionLogger = interactionLogger;
    }

    /**
     * 处理交互方法
     * 当发现新的 Collaborator 交互时被调用
     * 
     * @param interaction 交互实例
     */
    @Override
    public void handleInteraction(Interaction interaction)
    {
        // 记录交互信息
        interactionLogger.logInteraction(interaction);

        // 获取与交互 ID 匹配的代理 HTTP 请求响应列表
        List<ProxyHttpRequestResponse> proxyHttpRequestResponseList = api.proxy().history(requestResponse -> 
            requestResponse.finalRequest().toString().contains(interaction.id().toString()));

        // 遍历每个代理 HTTP 请求响应，并将其添加到站点地图中
        proxyHttpRequestResponseList.forEach(item -> api.siteMap().add(
                AuditIssue.auditIssue(
                        "Collaborator 示例问题",
                        "这是一个示例问题描述，用于演示如何使用 Collaborator 进行交互。",
                        "这是一个示例问题背景信息，解释了问题的潜在影响。",
                        item.finalRequest().url(),
                        AuditIssueSeverity.HIGH,
                        AuditIssueConfidence.CERTAIN,
                        "这是一个示例问题补救措施，提供了如何解决该问题的建议。",
                        "这是一个示例问题详细信息，包含了更多关于问题的技术细节。",
                        AuditIssueSeverity.HIGH,
                        HttpRequestResponse.httpRequestResponse(item.finalRequest(), item.originalResponse())
                )
        ));
    }
}
