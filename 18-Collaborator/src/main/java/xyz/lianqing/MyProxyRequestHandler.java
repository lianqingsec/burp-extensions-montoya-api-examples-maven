/*
 * 版权所有 (c) 2023. PortSwigger Ltd. 保留所有权利。
 *
 * 本代码可用于扩展 Burp Suite 社区版和专业版的功能，
 * 但须遵守这些产品的使用许可条款。
 */

package xyz.lianqing;

import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;

/**
 * 代理请求处理器类
 * 处理代理请求并添加 Collaborator 载荷
 * 
 * 主要功能：
 * 1. 处理代理请求
 * 2. 生成 Collaborator 载荷
 * 3. 修改请求参数
 * 
 * 使用场景：
 * - 测试 SSRF 漏洞
 * - 检测外部系统交互
 * - 监控网络请求
 */
public class MyProxyRequestHandler implements ProxyRequestHandler
{
    /**
     * Collaborator 客户端实例
     * 用于生成载荷
     */
    private final CollaboratorClient collaboratorClient;

    /**
     * 构造函数
     * 初始化代理请求处理器
     * 
     * @param collaboratorClient Collaborator 客户端实例
     */
    public MyProxyRequestHandler(CollaboratorClient collaboratorClient)
    {
        this.collaboratorClient = collaboratorClient;
    }

    /**
     * 处理接收到的代理请求
     * 添加 Collaborator 载荷到请求中
     * 
     * @param interceptedRequest 拦截的请求
     * @return 继续处理请求的操作
     */
    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest)
    {
        // 生成 Collaborator 客户端的有效载荷
        String payload = collaboratorClient.generatePayload().toString();

        // 创建一个新的 HTTP 请求，添加 host 参数
        HttpRequest newRequest = interceptedRequest.withParameter(HttpParameter.urlParameter("host", payload));
        // 测试地址: https://portswigger-labs.net/ssrf-dns.php

        return ProxyRequestReceivedAction.continueWith(newRequest);
    }

    /**
     * 处理将要发送的代理请求
     * 目前直接传递请求，不做特殊处理
     * 
     * @param interceptedRequest 拦截的请求
     * @return 继续发送请求的操作
     */
    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest)
    {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }
}
