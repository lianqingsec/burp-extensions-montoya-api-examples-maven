package xyz.lianqing;

import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.requests.HttpRequest;

import static burp.api.montoya.http.HttpService.httpService;
import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;

/**
 * HTTP 请求处理器
 * 实现了 HttpHandler 接口，用于重定向特定主机的流量
 * 主要功能：
 * 1. 检查请求的目标主机
 * 2. 如果匹配源主机，则重定向到目标主机
 * 3. 更新请求中的 Host 头部
 */
public class MyHttpHandler implements HttpHandler
{
    /**
     * 处理即将发送的 HTTP 请求
     * 如果请求的目标主机匹配源主机，则将其重定向到目标主机
     * 
     * @param httpRequestToBeSent 即将发送的 HTTP 请求
     * @return 处理后的请求操作，如果不需要重定向则继续发送原始请求
     */
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent httpRequestToBeSent) {
        // 获取请求的目标服务信息
        HttpService service = httpRequestToBeSent.httpService();

        // 检查请求的目标主机是否匹配源主机
        if (TrafficRedirector.HOST_FROM.equalsIgnoreCase(service.host())) {
            // 创建新的服务对象，将主机名改为目标主机
            HttpRequest updatedHttpServiceRequest = httpRequestToBeSent.withService(
                httpService(TrafficRedirector.HOST_TO, service.port(), service.secure())
            );
            
            // 更新请求中的 Host 头部
            HttpRequest updatedHostHeaderRequest = updatedHttpServiceRequest.withUpdatedHeader(
                "Host", TrafficRedirector.HOST_TO
            );

            // 返回修改后的请求
            return continueWith(updatedHostHeaderRequest);
        }

        // 如果不需要重定向，则继续发送原始请求
        return continueWith(httpRequestToBeSent);
    }

    /**
     * 处理接收到的 HTTP 响应
     * 本示例中不对响应做任何修改
     * 
     * @param httpResponseReceived 接收到的 HTTP 响应
     * @return 继续使用原始响应
     */
    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
        return ResponseReceivedAction.continueWith(httpResponseReceived);
    }
}