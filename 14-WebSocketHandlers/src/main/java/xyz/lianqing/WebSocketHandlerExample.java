package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * WebSocket 处理器示例扩展类
 * 演示如何使用 Burp Suite 的 WebSocket 处理功能
 * 
 * 主要功能：
 * 1. 注册 WebSocket 创建处理器
 * 2. 设置扩展名称
 * 3. 初始化 WebSocket 处理链
 * 
 * 使用场景：
 * - WebSocket 通信的安全测试
 * - WebSocket 消息的监控和修改
 * - 自动化 WebSocket 测试
 */
public class WebSocketHandlerExample implements BurpExtension {

    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和注册处理器
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称
        api.extension().setName("WebSocket 处理器示例");

        // 创建 WebSocket 创建处理器实例
        MyWebSocketCreatedHandler exampleWebSocketCreationHandler = new MyWebSocketCreatedHandler(api);
        
        // 向 Burp 注册 WebSocket 创建处理器
        api.websockets().registerWebSocketCreatedHandler(exampleWebSocketCreationHandler);
    }
}
