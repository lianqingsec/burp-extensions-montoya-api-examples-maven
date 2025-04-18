package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 代理 WebSocket 处理器示例扩展类
 * 演示如何使用 Burp Suite 的代理 WebSocket 处理功能
 * 
 * 主要功能：
 * 1. 注册代理 WebSocket 创建处理器
 * 2. 设置扩展名称
 * 3. 初始化代理 WebSocket 处理链
 * 
 * 使用场景：
 * - 代理 WebSocket 通信的安全测试
 * - WebSocket 消息的代理监控和修改
 * - 自动化 WebSocket 代理测试
 */
public class ProxyWebSocketHandlerExample implements BurpExtension {

    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和注册处理器
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称
        api.extension().setName("代理 WebSocket 处理器示例");

        // 创建代理 WebSocket 创建处理器实例
        MyProxyWebSocketCreationHandler exampleWebSocketCreationHandler = new MyProxyWebSocketCreationHandler();
        
        // 向 Burp 注册代理 WebSocket 创建处理器
        api.proxy().registerWebSocketCreationHandler(exampleWebSocketCreationHandler);
    }
}
