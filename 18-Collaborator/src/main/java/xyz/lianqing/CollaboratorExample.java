/*
 * 版权所有 (c) 2023. PortSwigger Ltd. 保留所有权利。
 *
 * 本代码可用于扩展 Burp Suite 社区版和 Burp Suite 专业版的功能，
 * 但须遵守这些产品的许可条款。
 */

package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.SecretKey;
import burp.api.montoya.persistence.PersistedObject;
import xyz.lianqing.poller.Poller;

import java.time.Duration;

/**
 * Collaborator 示例扩展类
 * 演示如何使用 Burp Suite 的 Collaborator 功能
 * 
 * 主要功能：
 * 1. 创建和管理 Collaborator 客户端
 * 2. 处理代理请求和响应
 * 3. 监控和记录交互
 * 
 * 使用场景：
 * - 检测外部系统交互
 * - 测试 SSRF 漏洞
 * - 监控网络请求
 */
public class CollaboratorExample implements BurpExtension
{
    /**
     * Montoya API 接口实例
     * 用于访问 Burp Suite 的功能
     */
    private MontoyaApi api;

    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和初始化 Collaborator 功能
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 初始化 MontoyaApi 实例
        this.api = api;

        // 设置扩展名称
        api.extension().setName("Collaborator 示例扩展");

        // 创建或恢复 Collaborator 客户端
        CollaboratorClient collaboratorClient = createCollaboratorClient(api.persistence().extensionData());

        // 记录存储的交互
        InteractionLogger interactionLogger = new InteractionLogger(api);
        interactionLogger.logInteractions(collaboratorClient.getAllInteractions());

        // 注册代理请求处理器
        api.proxy().registerRequestHandler(new MyProxyRequestHandler(collaboratorClient));

        // 定期轮询 CollaboratorClient 以检索任何新交互
        Poller collaboratorPoller = new Poller(collaboratorClient, Duration.ofSeconds(10));
        collaboratorPoller.registerInteractionHandler(new MyInteractionHandler(api, interactionLogger));
        collaboratorPoller.start();

        // 注册扩展卸载处理器
        api.extension().registerUnloadingHandler(() ->
        {
            // 停止轮询 CollaboratorClient
            collaboratorPoller.shutdown();

            // 记录扩展卸载信息
            api.logging().logToOutput("扩展正在卸载...");
        });
    }

    /**
     * 创建 Collaborator 客户端
     * 从持久化数据中恢复或创建新的客户端
     * 
     * @param persistedData 持久化数据对象
     * @return Collaborator 客户端实例
     */
    private CollaboratorClient createCollaboratorClient(PersistedObject persistedData)
    {
        CollaboratorClient collaboratorClient;

        // 从持久化数据中获取现有的 Collaborator 密钥
        String existingCollaboratorKey = persistedData.getString("persisted_collaborator");

        if (existingCollaboratorKey != null)
        {
            // 使用现有密钥恢复 Collaborator 客户端
            api.logging().logToOutput("使用密钥创建 Collaborator 客户端。");
            collaboratorClient = api.collaborator().restoreClient(SecretKey.secretKey(existingCollaboratorKey));
        }
        else
        {
            // 未找到现有的 Collaborator 客户端，创建新客户端
            api.logging().logToOutput("未找到现有的 Collaborator 客户端。正在创建新客户端...");
            collaboratorClient = api.collaborator().createClient();

            // 保存 Collaborator 客户端的密钥以便后续使用
            api.logging().logToOutput("保存 Collaborator 密钥。");
            persistedData.setString("persisted_collaborator", collaboratorClient.getSecretKey().toString());
        }

        return collaboratorClient;
    }
}
