package xyz.lianqing;

import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;

import static burp.api.montoya.core.HighlightColor.RED;
import static burp.api.montoya.http.message.ContentType.JSON;

/**
 * 代理请求处理器实现类
 * <p>
 * 这个类实现了 ProxyRequestHandler 接口，用于处理通过 Burp 代理的 HTTP 请求。
 * 主要功能包括：
 * 1. 拦截和处理接收到的请求
 * 2. 处理即将发送的请求
 * 3. 根据请求特征执行不同的操作
 */
class MyProxyHttpRequestHandler implements ProxyRequestHandler {
    /**
     * 处理接收到的代理请求
     *
     * @param interceptedRequest 被拦截的 HTTP 请求
     * @return 处理后的请求动作，决定请求的后续处理方式
     */
    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        // 如果是 POST 请求，直接丢弃
        if (interceptedRequest.method().equals("POST")) {
            return ProxyRequestReceivedAction.drop();
        }

        // 如果 URL 中包含 "foo"，不拦截该请求，直接放行
        if (interceptedRequest.url().contains("foo")) {
            return ProxyRequestReceivedAction.doNotIntercept(interceptedRequest);
        }

        // 如果请求内容类型为 JSON，添加红色高亮并遵循 Burp 的拦截规则
        if (interceptedRequest.contentType() == JSON) {
            return ProxyRequestReceivedAction.continueWith(interceptedRequest, interceptedRequest.annotations().withHighlightColor(RED));
        }

        // 对于其他所有请求，进行拦截等待用户处理
        return ProxyRequestReceivedAction.intercept(interceptedRequest);
    }

    /**
     * 处理即将发送的代理请求
     *
     * @param interceptedRequest 被拦截的 HTTP 请求
     * @return 处理后的请求动作，决定请求的发送方式
     */
    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        // 对用户修改后的请求不做任何处理，直接发送
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }
}
