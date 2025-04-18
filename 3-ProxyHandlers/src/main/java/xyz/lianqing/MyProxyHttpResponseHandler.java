package xyz.lianqing;

import burp.api.montoya.proxy.http.InterceptedResponse;
import burp.api.montoya.proxy.http.ProxyResponseHandler;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;

import static burp.api.montoya.core.HighlightColor.BLUE;

/**
 * 代理响应处理器实现类
 * <p>
 * 这个类实现了 ProxyResponseHandler 接口，用于处理通过 Burp 代理的 HTTP 响应。
 * 主要功能包括：
 * 1. 处理接收到的响应
 * 2. 处理即将发送的响应
 * 3. 根据响应内容执行不同的操作
 */
class MyProxyHttpResponseHandler implements ProxyResponseHandler {
    /**
     * 处理接收到的代理响应
     *
     * @param interceptedResponse 被拦截的 HTTP 响应
     * @return 处理后的响应动作，决定响应的后续处理方式
     */
    @Override
    public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
        // 检查响应体中是否包含 "username" 字符串
        // 如果包含，添加蓝色高亮标记并继续处理
        if (interceptedResponse.bodyToString().contains("username")) {
            return ProxyResponseReceivedAction.continueWith(
                    interceptedResponse,
                    interceptedResponse.annotations().withHighlightColor(BLUE)
            );
        }

        // 对于其他响应，不做特殊处理，直接继续
        return ProxyResponseReceivedAction.continueWith(interceptedResponse);
    }

    /**
     * 处理即将发送的代理响应
     *
     * @param interceptedResponse 被拦截的 HTTP 响应
     * @return 处理后的响应动作，决定响应的发送方式
     */
    @Override
    public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
        // 对即将发送的响应不做任何处理，直接发送
        return ProxyResponseToBeSentAction.continueWith(interceptedResponse);
    }
}
