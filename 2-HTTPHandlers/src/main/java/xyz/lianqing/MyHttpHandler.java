package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.logging.Logging;

import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;
import static burp.api.montoya.http.message.params.HttpParameter.urlParameter;

/**
 * 自定义 HTTP 处理器实现类
 * 
 * 这个类实现了 HttpHandler 接口，用于处理所有通过 Burp 的 HTTP 请求和响应。
 * 主要功能包括：
 * 1. 处理发送前的请求
 * 2. 处理接收到的响应
 * 3. 修改请求和响应的内容
 * 4. 添加注释和高亮标记
 */
class MyHttpHandler implements HttpHandler {
    private final Logging logging;

    /**
     * 构造函数
     * 
     * @param api Montoya API 接口，用于获取日志记录器
     */
    public MyHttpHandler(MontoyaApi api) {
        this.logging = api.logging();
    }

    /**
     * 处理即将发送的 HTTP 请求
     * 
     * @param requestToBeSent 即将发送的 HTTP 请求
     * @return 处理后的请求动作，包含修改后的请求和注释
     */
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
        // 获取请求的当前注释
        Annotations annotations = requestToBeSent.annotations();

        // 如果请求是 POST 方法，记录请求体并添加注释
        if (isPost(requestToBeSent)) {
            // 添加注释说明这是一个 POST 请求
            annotations = annotations.withNotes("这是一个 POST 请求");
            // 将请求体内容记录到输出日志
            logging.logToOutput(requestToBeSent.bodyToString());
        }

        // 修改请求，添加 URL 参数
        HttpRequest modifiedRequest = requestToBeSent.withAddedParameters(urlParameter("foo", "bar"));

        // 返回修改后的请求和更新后的注释
        return continueWith(modifiedRequest, annotations);
    }

    /**
     * 处理接收到的 HTTP 响应
     * 
     * @param responseReceived 接收到的 HTTP 响应
     * @return 处理后的响应动作，包含修改后的响应和注释
     */
    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
        // 获取响应的当前注释
        Annotations annotations = responseReceived.annotations();
        
        // 如果请求中包含 Content-Length 头，将响应标记为蓝色高亮
        if (responseHasContentLengthHeader(responseReceived)) {
            annotations = annotations.withHighlightColor(HighlightColor.BLUE);
        }

        // 返回响应和更新后的注释
        return continueWith(responseReceived, annotations);
    }

    /**
     * 检查请求是否为 POST 方法
     * 
     * @param httpRequestToBeSent 要检查的 HTTP 请求
     * @return 如果是 POST 请求返回 true，否则返回 false
     */
    private static boolean isPost(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("POST");
    }

    /**
     * 检查响应对应的请求是否包含 Content-Length 头
     * 
     * @param httpResponseReceived 要检查的 HTTP 响应
     * @return 如果请求包含 Content-Length 头返回 true，否则返回 false
     */
    private static boolean responseHasContentLengthHeader(HttpResponseReceived httpResponseReceived) {
        return httpResponseReceived.initiatingRequest().headers().stream()
                .anyMatch(header -> header.name().equalsIgnoreCase("Content-Length"));
    }
}
