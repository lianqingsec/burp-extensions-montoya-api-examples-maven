package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.logging.Logging;

/**
 * HTTP 请求和响应处理器
 * 实现了 HttpHandler 接口，用于处理所有 HTTP 请求和响应
 * 可以用于：
 * 1. 记录所有 HTTP 请求和响应
 * 2. 修改请求和响应内容
 * 3. 分析请求和响应数据
 */
public class MyHttpHandler implements HttpHandler
{
    private final Logging logging;

    public MyHttpHandler(MontoyaApi api)
    {
        logging = api.logging();
    }

    /**
     * 处理即将发送的 HTTP 请求
     * @param httpRequestToBeSent 即将发送的 HTTP 请求
     * @return 处理后的请求操作，这里直接继续发送原始请求
     */
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent httpRequestToBeSent) {
        // 记录请求信息，包括目标服务和来源工具
        logging.logToOutput("HTTP 请求发送到 " + httpRequestToBeSent.httpService() + " [" + httpRequestToBeSent.toolSource().toolType().toolName() + "]");

        return RequestToBeSentAction.continueWith(httpRequestToBeSent);
    }

    /**
     * 处理接收到的 HTTP 响应
     * @param httpResponseReceived 接收到的 HTTP 响应
     * @return 处理后的响应操作，这里直接继续使用原始响应
     */
    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
        // 记录响应信息，包括来源服务和来源工具
        logging.logToOutput("HTTP 响应来自 " + httpResponseReceived.initiatingRequest().httpService() + " [" + httpResponseReceived.toolSource().toolType().toolName() + "]");

        return ResponseReceivedAction.continueWith(httpResponseReceived);
    }
}