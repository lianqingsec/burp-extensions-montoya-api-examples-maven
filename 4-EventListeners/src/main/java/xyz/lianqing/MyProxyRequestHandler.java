package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;

/**
 * 代理请求处理器
 * 实现了 ProxyRequestHandler 接口，用于处理经过代理的请求
 * 可以用于：
 * 1. 拦截和修改代理请求
 * 2. 记录代理请求信息
 * 3. 分析代理请求数据
 */
public class MyProxyRequestHandler implements ProxyRequestHandler
{
    private final Logging logging;

    public MyProxyRequestHandler(MontoyaApi api)
    {
        logging = api.logging();
    }

    /**
     * 处理初始拦截到的代理请求
     * @param interceptedRequest 被拦截的请求
     * @return 处理后的请求操作，这里直接继续发送原始请求
     */
    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        // 记录初始拦截到的请求信息
        logging.logToOutput("初始拦截到的代理请求发送到 " + interceptedRequest.httpService());

        return ProxyRequestReceivedAction.continueWith(interceptedRequest);
    }

    /**
     * 处理即将发送的代理请求
     * @param interceptedRequest 被拦截的请求
     * @return 处理后的请求操作，这里直接继续发送原始请求
     */
    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        // 记录最终发送的请求信息
        logging.logToOutput("最终拦截到的代理请求发送到 " + interceptedRequest.httpService());

        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }
}
