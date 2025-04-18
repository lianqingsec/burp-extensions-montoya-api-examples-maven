package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.persistence.PersistedList;
import burp.api.montoya.persistence.PersistedObject;

import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;
import static burp.api.montoya.http.message.HttpRequestResponse.httpRequestResponse;

/**
 * 请求响应日志类
 * 用于演示如何持久化存储 HTTP 请求和响应
 * 
 * 主要功能：
 * 1. 保存最近的 5 个请求响应
 * 2. 自动更新持久化列表
 * 3. 打印请求响应内容到输出日志
 */
public class RequestResponseLogging {
    /**
     * 请求响应列表键名
     * 用于在持久化存储中标识请求响应列表的键
     */
    private static final String REQUEST_RESPONSE_LIST_KEY = "last5";

    private final MontoyaApi api;
    private final PersistedObject myExtensionData;

    /**
     * 构造函数
     * 初始化 API 和持久化数据对象
     * 
     * @param api Montoya API 接口实例
     */
    public RequestResponseLogging(MontoyaApi api) {
        this.api = api;
        this.myExtensionData = api.persistence().extensionData();
    }

    /**
     * 运行示例
     * 展示请求响应的持久化存储功能
     */
    public void runExample() {
        // 确保持久化列表存在
        ensurePersistedListIsPresent();

        // 从项目文件中加载请求响应列表
        PersistedList<HttpRequestResponse> myPersistedList = myExtensionData.getHttpRequestResponseList(REQUEST_RESPONSE_LIST_KEY);
        // 打印列表内容到输出
        printToOutput(myPersistedList);

        // 注册 HTTP 处理器
        api.http().registerHttpHandler(new HttpHandler() {
            @Override
            public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
                return continueWith(requestToBeSent);
            }

            @Override
            public synchronized ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
                // 保持最近 5 个请求响应
                if (myPersistedList.size() >= 5) {
                    myPersistedList.remove(0);
                }

                // 为了节省空间，我们不保存请求和响应的主体内容
                myPersistedList.add(httpRequestResponse(
                    responseReceived.initiatingRequest().withBody(""), 
                    responseReceived.withBody("")
                ));

                return continueWith(responseReceived);
            }
        });
    }

    /**
     * 打印请求响应列表到输出
     * 
     * @param myPersistedList 要打印的请求响应列表
     */
    private void printToOutput(PersistedList<HttpRequestResponse> myPersistedList) {
        // 将加载的列表打印到输出日志
        for (HttpRequestResponse httpRequestResponse : myPersistedList) {
            api.logging().logToOutput(httpRequestResponse.request().toString());
            api.logging().logToOutput("\n========================\n");
            api.logging().logToOutput(httpRequestResponse.response().toString());
            api.logging().logToOutput("\n**************************".repeat(2));
        }
    }

    /**
     * 确保持久化列表存在
     * 如果列表不存在，则创建一个新的空列表
     */
    private void ensurePersistedListIsPresent() {
        // 创建持久化的请求响应列表
        if (myExtensionData.getHttpRequestResponseList(REQUEST_RESPONSE_LIST_KEY) == null) {
            // 创建一个新的空列表并保存到我们的键
            PersistedList<HttpRequestResponse> emptyPersistedList = PersistedList.persistedHttpRequestResponseList();
            myExtensionData.setHttpRequestResponseList(REQUEST_RESPONSE_LIST_KEY, emptyPersistedList);
        }
    }
}
