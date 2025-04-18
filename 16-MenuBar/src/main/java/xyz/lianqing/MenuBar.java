package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.menu.BasicMenuItem;
import burp.api.montoya.ui.menu.Menu;
import burp.api.montoya.ui.menu.MenuItem;

/**
 * 菜单栏扩展类
 * 演示如何在 Burp Suite 中添加自定义菜单项
 * 
 * 主要功能：
 * 1. 创建自定义菜单栏
 * 2. 添加菜单项及其动作
 * 3. 注册扩展卸载处理器
 * 
 * 使用场景：
 * - 添加自定义功能入口
 * - 提供扩展管理功能
 * - 实现用户交互界面
 */
public class MenuBar implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和注册菜单项
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("添加菜单栏");
        // 记录扩展加载日志
        api.logging().logToOutput("扩展已加载。");

        // 创建"触发关键警报"菜单项
        BasicMenuItem alertEventItem = BasicMenuItem.basicMenuItem("触发关键警报")
                .withAction(() -> api.logging().raiseCriticalEvent("来自扩展的警报"));

        // 创建"卸载扩展"菜单项
        BasicMenuItem basicMenuItem = MenuItem.basicMenuItem("卸载扩展");
        MenuItem unloadExtensionItem = basicMenuItem.withAction(() -> api.extension().unload());

        // 创建菜单栏并添加菜单项
        Menu menu = Menu.menu("菜单栏").withMenuItems(alertEventItem, unloadExtensionItem);

        // 注册菜单栏
        api.userInterface().menuBar().registerMenu(menu);

        // 注册扩展卸载处理器
        api.extension().registerUnloadingHandler(new MyExtensionUnloadingHandler(api));
    }
}
