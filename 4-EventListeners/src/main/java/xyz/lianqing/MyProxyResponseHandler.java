package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.proxy.http.InterceptedResponse;
import burp.api.montoya.proxy.http.ProxyResponseHandler;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;

/**
 * 代理响应处理器
 * 实现了 ProxyResponseHandler 接口，用于处理经过代理的响应
 * 可以用于：
 * 1. 拦截和修改代理响应
 * 2. 记录代理响应信息
 * 3. 分析代理响应数据
 */
public class MyProxyResponseHandler implements ProxyResponseHandler
{
    private final Logging logging;

    public MyProxyResponseHandler(MontoyaApi api)
    {
        logging = api.logging();
    }

    /**
     * 处理初始拦截到的代理响应
     * @param interceptedResponse 被拦截的响应
     * @return 处理后的响应操作，这里直接继续使用原始响应
     */
    @Override
    public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
        // 记录初始拦截到的响应信息
        logging.logToOutput("初始拦截到的代理响应来自 " + interceptedResponse.initiatingRequest().httpService());

        return ProxyResponseReceivedAction.continueWith(interceptedResponse);
    }

    /**
     * 处理即将发送的代理响应
     * @param interceptedResponse 被拦截的响应
     * @return 处理后的响应操作，这里直接继续使用原始响应
     */
    @Override
    public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
        // 记录最终发送的响应信息
        logging.logToOutput("最终拦截到的代理响应来自 " + interceptedResponse.initiatingRequest().httpService());

        return ProxyResponseToBeSentAction.continueWith(interceptedResponse);
    }
}
