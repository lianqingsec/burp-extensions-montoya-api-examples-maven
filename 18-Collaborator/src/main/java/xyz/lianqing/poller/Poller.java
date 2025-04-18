/*
 * 版权 (c) 2023. PortSwigger Ltd. 保留所有权利。
 *
 * 本代码可用于扩展 Burp Suite Community Edition 和 Burp Suite Professional 的功能，
 * 但须遵守这些产品的许可条款。
 */

package xyz.lianqing.poller;

import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.collaborator.SecretKey;
import burp.api.montoya.logging.Logging;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Collaborator 轮询器类
 * 用于定期检查 Collaborator 客户端的交互
 * 
 * 主要功能：
 * 1. 定期轮询 Collaborator 客户端
 * 2. 处理新发现的交互
 * 3. 管理轮询线程的生命周期
 * 
 * 使用场景：
 * - 自动化检测外部交互
 * - 实时监控 Collaborator 活动
 * - 处理异步交互事件
 */
public class Poller
{
    /**
     * Collaborator 客户端实例
     * 用于获取交互信息
     */
    private final CollaboratorClient collaboratorClient;

    /**
     * 轮询间隔时间
     * 控制检查新交互的频率
     */
    private final Duration pollInterval;

    /**
     * 交互处理器列表
     * 用于处理发现的交互
     */
    private final List<InteractionHandler> interactionHandlers;

    /**
     * 调度执行器服务
     * 用于管理轮询任务
     */
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 构造函数
     * 初始化轮询器实例
     * 
     * @param collaboratorClient Collaborator 客户端实例
     * @param pollInterval 轮询间隔时间
     */
    public Poller(CollaboratorClient collaboratorClient, Duration pollInterval)
    {
        this.collaboratorClient = collaboratorClient;
        this.pollInterval = pollInterval;
        this.interactionHandlers = new ArrayList<>();
    }

    /**
     * 注册交互处理器
     * 添加新的交互处理器到列表中
     * 
     * @param interactionHandler 交互处理器实例
     */
    public void registerInteractionHandler(InteractionHandler interactionHandler)
    {
        interactionHandlers.add(interactionHandler);
    }

    /**
     * 启动轮询器
     * 开始定期检查新交互
     */
    public void start()
    {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::poll, 0, pollInterval.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 停止轮询器
     * 关闭调度执行器服务
     */
    public void shutdown()
    {
        if (scheduledExecutorService != null)
        {
            scheduledExecutorService.shutdown();
        }
    }

    /**
     * 轮询方法
     * 检查新交互并通知处理器
     */
    private void poll()
    {
        List<Interaction> interactions = collaboratorClient.getAllInteractions();

        for (Interaction interaction : interactions)
        {
            for (InteractionHandler interactionHandler : interactionHandlers)
            {
                interactionHandler.handleInteraction(interaction);
            }
        }
    }
}
