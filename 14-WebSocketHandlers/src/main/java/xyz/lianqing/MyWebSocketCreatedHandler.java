package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.websocket.WebSocketCreated;
import burp.api.montoya.websocket.WebSocketCreatedHandler;

/**
 * WebSocket 创建处理器类
 * 用于处理 WebSocket 连接的创建事件
 * 
 * 主要功能：
 * 1. 在 WebSocket 连接创建时发送初始消息
 * 2. 注册消息处理器以处理后续的 WebSocket 消息
 * 
 * 使用场景：
 * - 自动化 WebSocket 通信
 * - 初始化 WebSocket 会话
 * - 设置消息处理链
 */
class MyWebSocketCreatedHandler implements WebSocketCreatedHandler {

    /**
     * Montoya API 实例
     * 用于访问 Burp Suite 的功能
     */
    private final MontoyaApi api;

    /**
     * 构造函数
     * 初始化 WebSocket 创建处理器
     * 
     * @param api Montoya API 接口实例
     */
    MyWebSocketCreatedHandler(MontoyaApi api) {
        this.api = api;
    }

    /**
     * 处理 WebSocket 创建事件
     * 在 WebSocket 连接建立时被调用
     * 
     * @param webSocketCreated WebSocket 创建事件对象
     */
    @Override
    public void handleWebSocketCreated(WebSocketCreated webSocketCreated) {
        // 发送初始消息到 WebSocket 连接
        webSocketCreated.webSocket().sendTextMessage("First Message");

        // 注册消息处理器以处理后续的 WebSocket 消息
        webSocketCreated.webSocket().registerMessageHandler(new MyWebSocketMessageHandler(api));
    }
}
