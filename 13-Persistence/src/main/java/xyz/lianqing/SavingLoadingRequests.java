package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.persistence.PersistedObject;

import static burp.api.montoya.http.message.requests.HttpRequest.httpRequest;
import static burp.api.montoya.http.message.requests.HttpRequest.httpRequestFromUrl;
import static xyz.lianqing.PersistenceExample.STARTUP_COUNT_KEY;

/**
 * HTTP 请求保存和加载类
 * 演示如何持久化存储和加载 HTTP 请求
 * <p>
 * 主要功能：
 * 1. 保存不同类型的 HTTP 请求
 * 2. 从持久化存储中加载请求
 * 3. 将请求发送到 Repeater 工具
 */
public class SavingLoadingRequests {
    /**
     * 简单请求键名
     * 用于在持久化存储中标识简单 HTTP 请求的键
     */
    public static final String SIMPLE_REQUEST_KEY = "simpleRequest";

    /**
     * 带头部请求键名
     * 用于在持久化存储中标识带自定义头部的 HTTP 请求的键
     */
    public static final String REQUEST_WITH_HEADERS_KEY = "requestWithHeaders";

    /**
     * URL 请求键名
     * 用于在持久化存储中标识从 URL 创建的 HTTP 请求的键
     */
    public static final String REQUEST_FROM_URL_KEY = "requestFromUrl";

    private final MontoyaApi api;
    private final PersistedObject myExtensionData;

    /**
     * 构造函数
     * 初始化 API 和持久化数据对象
     *
     * @param api Montoya API 接口实例
     */
    public SavingLoadingRequests(MontoyaApi api) {
        this.api = api;
        this.myExtensionData = api.persistence().extensionData();
    }

    /**
     * 运行示例
     * 展示 HTTP 请求的保存和加载功能
     */
    public void runExample() {
        // 检查是否已保存示例请求
        if (!checkForRequests()) {
            api.logging().raiseInfoEvent("没有保存的请求，正在创建请求");
            createAndSaveExampleRequests();
        }

        // 将示例请求发送到 Repeater 工具，并在标签名中包含启动次数
        sendExampleRequestsToRepeaterWithStartupCount();
    }

    /**
     * 检查请求是否存在
     * 检查持久化存储中是否已保存所有示例请求
     *
     * @return 如果所有请求都已保存则返回 true，否则返回 false
     */
    private boolean checkForRequests() {
        // 我们可以获取每种数据类型保存的键列表
        return myExtensionData.httpRequestKeys().contains(SIMPLE_REQUEST_KEY) &&
                myExtensionData.httpRequestKeys().contains(REQUEST_WITH_HEADERS_KEY) &&
                myExtensionData.httpRequestKeys().contains(REQUEST_FROM_URL_KEY);
    }

    /**
     * 创建并保存示例请求
     * 构建不同类型的 HTTP 请求并保存到持久化存储
     */
    private void createAndSaveExampleRequests() {
        // 创建简单请求
        HttpRequest simpleRequest = httpRequest("GET / HTTP1.0\r\n\r\n");
        // 创建带自定义头部的请求
        HttpRequest requestWithHeaders = httpRequest("GET / HTTP1.1\r\nHost: localhost\r\nMyHeader: Example\r\n\r\n");
        // 从 URL 创建请求
        HttpRequest requestFromUrl = httpRequestFromUrl("http://localhost");

        // 将每个请求保存到其对应的键
        myExtensionData.setHttpRequest(SIMPLE_REQUEST_KEY, simpleRequest);
        myExtensionData.setHttpRequest(REQUEST_WITH_HEADERS_KEY, requestWithHeaders);
        myExtensionData.setHttpRequest(REQUEST_FROM_URL_KEY, requestFromUrl);
    }

    /**
     * 将示例请求发送到 Repeater 工具
     * 在标签名中包含启动次数
     */
    private void sendExampleRequestsToRepeaterWithStartupCount() {
        // 从持久化存储中加载请求
        HttpRequest simpleRequest = myExtensionData.getHttpRequest(SIMPLE_REQUEST_KEY);
        HttpRequest requestWithHeaders = myExtensionData.getHttpRequest(REQUEST_WITH_HEADERS_KEY);
        HttpRequest requestFromUrl = myExtensionData.getHttpRequest(REQUEST_FROM_URL_KEY);

        // 获取启动次数
        Integer startupCount = myExtensionData.getInteger(STARTUP_COUNT_KEY);

        // 将请求发送到 Repeater 工具，并在标签名中包含启动次数
        api.repeater().sendToRepeater(simpleRequest, "简单请求 " + startupCount);
        api.repeater().sendToRepeater(requestWithHeaders, "带头部请求 " + startupCount);
        api.repeater().sendToRepeater(requestFromUrl, "URL 请求 " + startupCount);
    }
}
