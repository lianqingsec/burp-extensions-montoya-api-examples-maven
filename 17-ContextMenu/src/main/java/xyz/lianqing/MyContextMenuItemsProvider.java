/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 上下文菜单项提供者类
 * 用于为特定工具提供自定义上下文菜单项
 * 
 * 主要功能：
 * 1. 为代理、目标和日志工具提供菜单项
 * 2. 处理请求和响应的打印功能
 * 3. 管理菜单项的显示和操作
 * 
 * 使用场景：
 * - 在特定工具中显示自定义菜单
 * - 提供请求和响应的快速查看
 * - 实现自定义操作功能
 */
public class MyContextMenuItemsProvider implements ContextMenuItemsProvider
{
    /**
     * Montoya API 接口实例
     * 用于访问 Burp Suite 的功能
     */
    private final MontoyaApi api;

    /**
     * 构造函数
     * 初始化上下文菜单项提供者
     * 
     * @param api Montoya API 接口实例
     */
    public MyContextMenuItemsProvider(MontoyaApi api)
    {
        this.api = api;
    }

    /**
     * 提供菜单项方法
     * 根据上下文菜单事件提供相应的菜单项
     * 
     * @param event 上下文菜单事件对象
     * @return 菜单项组件列表
     */
    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event)
    {
        // 检查事件是否来自代理、目标或日志工具
        if (event.isFromTool(ToolType.PROXY, ToolType.TARGET, ToolType.LOGGER))
        {
            List<Component> menuItemList = new ArrayList<>();

            // 创建打印请求和响应的菜单项
            JMenuItem retrieveRequestItem = new JMenuItem("打印请求");
            JMenuItem retrieveResponseItem = new JMenuItem("打印响应");

            // 获取当前选中的请求响应对象
            HttpRequestResponse requestResponse = event.messageEditorRequestResponse().isPresent() 
                ? event.messageEditorRequestResponse().get().requestResponse() 
                : event.selectedRequestResponses().get(0);

            // 为请求菜单项添加点击事件
            retrieveRequestItem.addActionListener(l -> 
                api.logging().logToOutput("请求内容：\r\n" + requestResponse.request().toString()));
            menuItemList.add(retrieveRequestItem);

            // 如果存在响应，添加响应菜单项
            if (requestResponse.response() != null)
            {
                retrieveResponseItem.addActionListener(l -> 
                    api.logging().logToOutput("响应内容：\r\n" + requestResponse.response().toString()));
                menuItemList.add(retrieveResponseItem);
            }

            return menuItemList;
        }

        return null;
    }
}
