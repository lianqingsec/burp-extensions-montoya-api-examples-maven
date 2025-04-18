package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * HTTP 处理器示例扩展
 * 
 * 这是一个演示如何使用 Burp 的 HTTP 处理器的示例扩展。
 * Burp 会自动检测并加载任何继承 BurpExtension 的类。
 * 
 * 本示例展示了如何：
 * 1. 注册自定义的 HTTP 请求/响应处理器
 * 2. 在请求发送前和响应接收后进行处理
 * 3. 修改请求和响应的内容
 */
public class HttpHandlerExample implements BurpExtension {
    /**
     * 扩展初始化方法
     * 
     * @param api Montoya API 接口，提供了访问 Burp 功能的入口
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称，这将在 Burp 的扩展列表中显示
        api.extension().setName("HTTP 处理器示例");

        // 向 Burp 注册我们的 HTTP 处理器
        // MyHttpHandler 将处理所有通过 Burp 的 HTTP 请求和响应
        api.http().registerHttpHandler(new MyHttpHandler(api));
    }
}
