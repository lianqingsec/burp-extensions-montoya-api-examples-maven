package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 代理处理器示例扩展
 * <p>
 * 这是一个演示如何使用 Burp 的代理处理器的示例扩展。
 * Burp 会自动检测并加载任何继承 BurpExtension 的类。
 * <p>
 * 本示例展示了如何：
 * 1. 注册自定义的代理请求处理器
 * 2. 注册自定义的代理响应处理器
 * 3. 拦截和处理通过代理的 HTTP 流量
 */
public class ProxyHandlerExample implements BurpExtension {
    /**
     * 扩展初始化方法
     *
     * @param api Montoya API 接口，提供了访问 Burp 功能的入口
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称，这将在 Burp 的扩展列表中显示
        api.extension().setName("代理处理器示例");

        // 向 Burp 注册代理请求和响应处理器
        // MyProxyHttpRequestHandler 将处理所有通过代理的请求
        // MyProxyHttpResponseHandler 将处理所有通过代理的响应
        api.proxy().registerRequestHandler(new MyProxyHttpRequestHandler());
        api.proxy().registerResponseHandler(new MyProxyHttpResponseHandler());
    }
}
