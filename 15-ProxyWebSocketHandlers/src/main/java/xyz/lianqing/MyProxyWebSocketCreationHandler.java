package xyz.lianqing;

import burp.api.montoya.proxy.websocket.ProxyWebSocketCreation;
import burp.api.montoya.proxy.websocket.ProxyWebSocketCreationHandler;

/**
 * 代理 WebSocket 创建处理器类
 * 用于处理通过 Burp Proxy 的 WebSocket 连接创建事件
 * 
 * 主要功能：
 * 1. 处理代理 WebSocket 连接的创建
 * 2. 注册消息处理器以处理后续的代理 WebSocket 消息
 * 
 * 使用场景：
 * - 代理 WebSocket 通信的监控
 * - 代理 WebSocket 消息的拦截和修改
 * - 安全测试中的 WebSocket 代理分析
 */
class MyProxyWebSocketCreationHandler implements ProxyWebSocketCreationHandler {

    /**
     * 处理代理 WebSocket 创建事件
     * 在代理 WebSocket 连接建立时被调用
     * 
     * @param webSocketCreation 代理 WebSocket 创建事件对象
     */
    @Override
    public void handleWebSocketCreation(ProxyWebSocketCreation webSocketCreation) {
        // 注册代理 WebSocket 消息处理器
        webSocketCreation.proxyWebSocket().registerProxyMessageHandler(new MyProxyWebSocketMessageHandler());
    }
}
