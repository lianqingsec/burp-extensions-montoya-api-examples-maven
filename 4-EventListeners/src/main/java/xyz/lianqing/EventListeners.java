package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.Extension;
import burp.api.montoya.extension.ExtensionUnloadingHandler;
import burp.api.montoya.http.Http;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.proxy.Proxy;
import burp.api.montoya.scanner.Scanner;
import burp.api.montoya.scanner.audit.AuditIssueHandler;
import burp.api.montoya.scanner.audit.issues.AuditIssue;

/**
 * 事件监听器示例扩展
 * 该类实现了 BurpExtension 接口，Burp 会自动检测并加载
 * 展示了如何注册各种事件处理器，包括：
 * 1. HTTP 请求/响应处理器
 * 2. 代理请求/响应处理器
 * 3. 扫描问题处理器
 * 4. 扩展卸载处理器
 */
public class EventListeners implements BurpExtension {
    private Logging logging;

    @Override
    public void initialize(MontoyaApi api) {
        // 获取日志记录器
        logging = api.logging();

        // 获取各个 API 接口实例
        Http http = api.http();          // HTTP 相关操作
        Proxy proxy = api.proxy();       // 代理相关操作
        Extension extension = api.extension();  // 扩展相关操作
        Scanner scanner = api.scanner();  // 扫描相关操作

        // 设置扩展名称
        extension.setName("事件监听器示例");

        // 注册 HTTP 处理器，用于处理所有 HTTP 请求和响应
        http.registerHttpHandler(new MyHttpHandler(api));

        // 注册代理处理器，用于处理经过代理的请求和响应
        proxy.registerRequestHandler(new MyProxyRequestHandler(api));
        proxy.registerResponseHandler(new MyProxyResponseHandler(api));

        // 注册扫描问题处理器，用于处理扫描过程中发现的问题
        scanner.registerAuditIssueHandler(new MyAuditIssueListenerHandler());

        // 注册扩展卸载处理器，用于在扩展被卸载时执行清理操作
        extension.registerUnloadingHandler(new MyExtensionUnloadHandler());
    }

    /**
     * 扫描问题处理器
     * 当扫描器发现新的问题时会被调用
     */
    private class MyAuditIssueListenerHandler implements AuditIssueHandler {
        @Override
        public void handleNewAuditIssue(AuditIssue auditIssue) {
            logging.logToOutput("发现新的扫描问题: " + auditIssue.name());
        }
    }

    /**
     * 扩展卸载处理器
     * 当扩展被卸载时会被调用，用于执行清理操作
     */
    private class MyExtensionUnloadHandler implements ExtensionUnloadingHandler {
        @Override
        public void extensionUnloaded() {
            logging.logToOutput("扩展已被卸载");
        }
    }
}