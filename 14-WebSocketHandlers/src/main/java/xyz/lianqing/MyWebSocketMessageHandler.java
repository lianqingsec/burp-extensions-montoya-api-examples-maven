package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.websocket.*;

import static burp.api.montoya.websocket.Direction.CLIENT_TO_SERVER;

/**
 * WebSocket 消息处理器类
 * 用于处理 WebSocket 连接中的消息
 * 
 * 主要功能：
 * 1. 处理文本消息，检测敏感信息
 * 2. 处理二进制消息
 * 3. 对包含敏感信息的消息进行 Base64 编码
 * 
 * 使用场景：
 * - 安全测试中的消息拦截和修改
 * - 敏感信息的检测和标记
 * - WebSocket 通信的监控和分析
 */
class MyWebSocketMessageHandler implements MessageHandler {

    /**
     * Montoya API 实例
     * 用于访问 Burp Suite 的功能
     */
    private final MontoyaApi api;

    /**
     * 构造函数
     * 初始化 WebSocket 消息处理器
     * 
     * @param api Montoya API 接口实例
     */
    public MyWebSocketMessageHandler(MontoyaApi api) {
        this.api = api;
    }

    /**
     * 处理文本消息
     * 检查消息中是否包含敏感信息，并进行相应处理
     * 
     * @param textMessage 要处理的文本消息
     * @return 处理后的消息动作
     */
    @Override
    public TextMessageAction handleTextMessage(TextMessage textMessage) {
        // 检查消息方向是否为客户端到服务器，且包含密码字段
        if (textMessage.direction() == CLIENT_TO_SERVER && textMessage.payload().contains("password")) {
            // 对包含密码的消息进行 Base64 编码
            String base64EncodedPayload = api.utilities().base64Utils().encodeToString(textMessage.payload());

            // 返回编码后的消息
            return TextMessageAction.continueWith(base64EncodedPayload);
        }

        // 对于其他消息，直接继续传递
        return TextMessageAction.continueWith(textMessage);
    }

    /**
     * 处理二进制消息
     * 目前直接传递二进制消息，不做特殊处理
     * 
     * @param binaryMessage 要处理的二进制消息
     * @return 处理后的消息动作
     */
    @Override
    public BinaryMessageAction handleBinaryMessage(BinaryMessage binaryMessage) {
        // 直接传递二进制消息
        return BinaryMessageAction.continueWith(binaryMessage);
    }
}
