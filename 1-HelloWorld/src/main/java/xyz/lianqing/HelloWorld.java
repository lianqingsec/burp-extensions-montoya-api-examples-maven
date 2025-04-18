package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

/**
 * HelloWorld 扩展示例
 * <p>
 * 这是一个简单的 Burp 扩展示例，展示了如何在 Burp 中使用日志功能。
 * Burp 会自动检测并加载任何继承 BurpExtension 的类。
 */
public class HelloWorld implements BurpExtension {
    /**
     * 扩展初始化方法
     *
     * @param api Montoya API 接口，提供了访问 Burp 功能的入口
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称，这将在 Burp 的扩展列表中显示
        api.extension().setName("Hello world extension");

        // 获取日志记录器实例
        Logging logging = api.logging();

        // 向输出流写入消息，这些消息会显示在 Burp 的输出面板中
        logging.logToOutput("Hello output.");

        // 向错误流写入消息，这些消息会显示在 Burp 的错误面板中
        logging.logToError("Hello error.");

        // 向 Burp 的警报标签页写入不同级别的消息
        logging.raiseInfoEvent("Hello info event.");      // 信息级别消息
        logging.raiseDebugEvent("Hello debug event.");    // 调试级别消息
        logging.raiseErrorEvent("Hello error event.");    // 错误级别消息
        logging.raiseCriticalEvent("Hello critical event."); // 严重错误级别消息

        // 抛出一个异常，这个异常会显示在错误流中
        // 注意：在实际扩展中，应该避免抛出异常，这里仅用于演示
        throw new RuntimeException("Hello exception.");
    }
}
