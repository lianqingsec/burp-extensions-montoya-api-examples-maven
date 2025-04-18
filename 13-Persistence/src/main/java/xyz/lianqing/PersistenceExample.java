package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.PersistedObject;

/**
 * 持久化示例扩展类
 * 演示如何使用 Burp Suite 的持久化功能来保存和加载扩展数据
 * 
 * 主要功能：
 * 1. 保存和加载扩展的启动次数
 * 2. 演示 HTTP 请求的保存和加载
 * 3. 展示请求响应日志的持久化存储
 */
public class PersistenceExample implements BurpExtension {

    /**
     * 启动次数键名
     * 用于在持久化存储中标识启动次数的键
     */
    static final String STARTUP_COUNT_KEY = "Startup Count";

    /**
     * 扩展初始化方法
     * 在扩展加载时被调用，用于设置扩展名称和初始化持久化数据
     * 
     * @param api Montoya API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称，该名称将用于标识扩展的持久化数据
        api.extension().setName("Persistence example extension");

        // 获取扩展的持久化数据对象
        PersistedObject myExtensionData = api.persistence().extensionData();

        // 从项目文件中获取启动次数
        Integer startupCount = myExtensionData.getInteger(STARTUP_COUNT_KEY);

        // 如果值为 null，表示该键在项目文件中不存在
        // 我们也可以使用 myExtensionData.integerKeys().contains(STARTUP_COUNT_KEY) 来检查键是否存在
        if (startupCount == null) {
            startupCount = 0;
        }

        // 在项目文件中设置新的启动次数
        myExtensionData.setInteger(STARTUP_COUNT_KEY, startupCount + 1);

        // 获取更新后的值并创建信息事件
        api.logging().raiseInfoEvent("启动次数为: " + myExtensionData.getInteger(STARTUP_COUNT_KEY));

        // 运行高级示例
        advancedExamples(api);
    }

    /**
     * 运行高级示例
     * 展示更复杂的持久化功能
     * 
     * @param api Montoya API 接口实例
     */
    private static void advancedExamples(MontoyaApi api) {
        // 运行 HTTP 请求保存和加载示例
        SavingLoadingRequests savingLoadingRequests = new SavingLoadingRequests(api);
        savingLoadingRequests.runExample();

        // 运行请求响应日志示例
        RequestResponseLogging requestLogging = new RequestResponseLogging(api);
        requestLogging.runExample();
    }
}
