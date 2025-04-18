/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.ExtensionUnloadingHandler;

/**
 * 扩展卸载处理器类
 * 用于处理扩展卸载时的清理工作
 * 
 * 主要功能：
 * 1. 在扩展卸载时执行清理操作
 * 2. 记录扩展卸载日志
 * 3. 管理扩展的生命周期
 * 
 * 使用场景：
 * - 扩展卸载时的资源释放
 * - 扩展状态的管理
 * - 扩展日志的记录
 */
public class MyExtensionUnloadingHandler implements ExtensionUnloadingHandler
{
    /**
     * Montoya API 接口实例
     * 用于访问 Burp Suite 的功能
     */
    private final MontoyaApi api;

    /**
     * 构造函数
     * 初始化扩展卸载处理器
     * 
     * @param api Montoya API 接口实例
     */
    public MyExtensionUnloadingHandler(MontoyaApi api)
    {
        this.api = api;
    }

    /**
     * 扩展卸载处理方法
     * 在扩展被卸载时自动调用
     * 用于执行清理操作和记录日志
     */
    @Override
    public void extensionUnloaded()
    {
        // 记录扩展卸载日志
        api.logging().logToOutput("扩展已卸载。");
    }
}
