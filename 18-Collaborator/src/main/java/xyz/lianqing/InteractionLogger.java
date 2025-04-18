/*
 * 版权所有 (c) 2023. PortSwigger Ltd. 保留所有权利。
 *
 * 本代码可用于扩展 Burp Suite 社区版和专业版的功能，
 * 但须遵守这些产品的使用许可条款。
 */

package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.collaborator.Interaction;

import java.util.List;

import static java.lang.String.format;

/**
 * 交互日志记录器类
 * 用于记录和输出 Collaborator 交互信息
 * 
 * 主要功能：
 * 1. 记录交互数量和详细信息
 * 2. 格式化交互日志输出
 * 3. 提供交互信息的可视化
 * 
 * 使用场景：
 * - 监控 Collaborator 活动
 * - 记录交互历史
 * - 调试和问题排查
 */
public class InteractionLogger
{
    /**
     * Burp Suite API 实例
     * 用于访问日志记录功能
     */
    private final MontoyaApi api;

    /**
     * 构造函数
     * 初始化交互日志记录器
     * 
     * @param api Burp Suite API 实例
     */
    public InteractionLogger(MontoyaApi api)
    {
        this.api = api;
    }

    /**
     * 记录所有交互
     * 输出交互总数和详细信息
     * 
     * @param allInteractions 交互列表
     */
    public void logInteractions(List<Interaction> allInteractions)
    {
        // 记录交互总数
        api.logging().logToOutput(allInteractions.size() + " 个未读交互。");

        // 记录每个交互的详细信息
        for (Interaction interaction : allInteractions)
        {
            logInteraction(interaction);
        }
    }

    /**
     * 记录单个交互
     * 输出交互的类型和 ID
     * 
     * @param interaction 交互实例
     */
    public void logInteraction(Interaction interaction)
    {
        // 格式化并输出交互信息
        api.logging().logToOutput(
                format(
                        """
                        交互类型: %s
                        交互 ID: %s
                        """,
                        interaction.type().name(),
                        interaction.id()
                )
        );
    }
}
