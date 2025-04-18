package xyz.lianqing;

import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.proxy.websocket.*;

import static burp.api.montoya.websocket.Direction.CLIENT_TO_SERVER;

/**
 * 代理 WebSocket 消息处理器类
 * 用于处理通过 Burp Proxy 的 WebSocket 消息
 * 
 * 主要功能：
 * 1. 处理接收到的文本消息
 * 2. 处理待发送的文本消息
 * 3. 处理二进制消息
 * 4. 对敏感信息进行标记和拦截
 * 
 * 使用场景：
 * - 代理 WebSocket 消息的安全分析
 * - 敏感信息的检测和标记
 * - WebSocket 通信的监控和拦截
 */
class MyProxyWebSocketMessageHandler implements ProxyMessageHandler {

    /**
     * 处理接收到的文本消息
     * 检查消息中是否包含敏感信息，并进行相应处理
     * 
     * @param interceptedTextMessage 被拦截的文本消息
     * @return 处理后的消息动作
     */
    @Override
    public TextMessageReceivedAction handleTextMessageReceived(InterceptedTextMessage interceptedTextMessage) {
        // 如果消息包含用户名，使用红色高亮标记
        if (interceptedTextMessage.payload().contains("username")) {
            interceptedTextMessage.annotations().setHighlightColor(HighlightColor.RED);
        }

        // 如果消息来自客户端且包含密码，拦截该消息
        if (interceptedTextMessage.direction() == CLIENT_TO_SERVER && 
            interceptedTextMessage.payload().contains("password")) {
            return TextMessageReceivedAction.intercept(interceptedTextMessage);
        }

        // 对于其他消息，直接继续传递
        return TextMessageReceivedAction.continueWith(interceptedTextMessage);
    }

    /**
     * 处理待发送的文本消息
     * 目前直接传递消息，不做特殊处理
     * 
     * @param interceptedTextMessage 被拦截的文本消息
     * @return 处理后的消息动作
     */
    @Override
    public TextMessageToBeSentAction handleTextMessageToBeSent(InterceptedTextMessage interceptedTextMessage) {
        return TextMessageToBeSentAction.continueWith(interceptedTextMessage);
    }

    /**
     * 处理接收到的二进制消息
     * 目前直接传递消息，不做特殊处理
     * 
     * @param interceptedBinaryMessage 被拦截的二进制消息
     * @return 处理后的消息动作
     */
    @Override
    public BinaryMessageReceivedAction handleBinaryMessageReceived(InterceptedBinaryMessage interceptedBinaryMessage) {
        return BinaryMessageReceivedAction.continueWith(interceptedBinaryMessage);
    }

    /**
     * 处理待发送的二进制消息
     * 目前直接传递消息，不做特殊处理
     * 
     * @param interceptedBinaryMessage 被拦截的二进制消息
     * @return 处理后的消息动作
     */
    @Override
    public BinaryMessageToBeSentAction handleBinaryMessageToBeSent(InterceptedBinaryMessage interceptedBinaryMessage) {
        return BinaryMessageToBeSentAction.continueWith(interceptedBinaryMessage);
    }
}
