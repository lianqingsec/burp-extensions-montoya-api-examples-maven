package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 上下文菜单扩展类
 * 演示如何在 Burp Suite 中添加自定义上下文菜单项
 * 
 * 主要功能：
 * 1. 注册上下文菜单项提供者
 * 2. 设置扩展名称
 * 3. 初始化上下文菜单功能
 * 
 * 使用场景：
 * - 为特定工具添加右键菜单
 * - 提供快捷操作功能
 * - 增强用户交互体验
 */
public class ContextMenu implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和注册上下文菜单
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("上下文菜单扩展");

        // 注册上下文菜单项提供者
        api.userInterface().registerContextMenuItemsProvider(new MyContextMenuItemsProvider(api));
    }
}
