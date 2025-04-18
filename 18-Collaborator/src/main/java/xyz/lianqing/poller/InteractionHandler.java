/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package xyz.lianqing.poller;

import burp.api.montoya.collaborator.Interaction;

/**
 * 交互处理器接口
 * 定义处理 Collaborator 交互的方法
 * 
 * 主要功能：
 * 1. 处理 Collaborator 交互事件
 * 2. 提供交互处理的标准接口
 * 3. 支持自定义交互处理逻辑
 * 
 * 使用场景：
 * - 处理外部系统交互
 * - 实现自定义交互逻辑
 * - 扩展 Collaborator 功能
 */
public interface InteractionHandler
{
    /**
     * 处理交互方法
     * 当发现新的 Collaborator 交互时被调用
     * 
     * @param interaction 交互实例
     */
    void handleInteraction(Interaction interaction);
}
