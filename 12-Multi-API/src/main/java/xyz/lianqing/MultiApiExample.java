package xyz.lianqing;

import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 多 API 示例扩展类
 * 同时实现了 BurpExtension 和 IBurpExtender 接口，用于演示如何在同一个扩展中同时使用新旧两个 API
 * <p>
 * 主要功能：
 * 1. 展示新旧 API 的初始化顺序
 * 2. 演示新旧 API 的优先级关系
 * 3. 实现自定义的套件标签
 * 4. 展示新旧 API 的混合使用
 * <p>
 * 注意事项：
 * - Burp 会自动检测并加载任何继承自 BurpExtension 或 IBurpExtender 的类
 * - 当一个类同时继承 BurpExtension 和 IBurpExtender 时，Burp 将首先注入 IBurpExtender 的实现，然后注入 BurpExtension 的实现
 * - 这意味着 BurpExtension 的优先级高于 IBurpExtender
 */
public class MultiApiExample implements BurpExtension, IBurpExtender {
    // Montoya API 实例，用于访问新版本 API 的功能
    private MontoyaApi montoyaApi;
    // Wiener API 回调实例，用于访问旧版本 API 的功能
    private IBurpExtenderCallbacks wienerApi;

    /**
     * 扩展初始化方法（新 API）
     * 在 IBurpExtender 的 registerExtenderCallbacks 方法之后被调用
     *
     * @param api Montoya API 接口实例，提供新版本 API 的功能
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 保存 Montoya API 实例以供后续使用
        this.montoyaApi = api;

        // 注册一个自定义的套件标签，该标签包含一个按钮，该按钮同时使用了新旧两个 API
        api.userInterface().registerSuiteTab("我的套件标签", new MySuiteTab());

        // 设置扩展名称，这将覆盖 Wiener API 设置的名称
        // 因为 Montoya API 的优先级更高
        api.extension().setName("Montoya 名称");

        // 检查 URL 是否在范围内，Wiener API 已经将其添加到范围内
        if (api.scope().isInScope("http://test.url")) {
            // 使用 Wiener API 发出警报，表示 URL 在范围内
            wienerApi.issueAlert("test.url 在范围内");
        } else {
            // 如果 URL 不在范围内，卸载扩展，这不应该发生
            // 因为 Wiener API 已经将 URL 添加到范围内
            api.extension().unload();
        }
    }

    /**
     * 扩展初始化方法（旧 API）
     * 在 Montoya API 的 initialize 方法之前被调用
     *
     * @param callbacks IBurpExtenderCallbacks 接口实例，提供旧版本 API 的功能
     */
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        // 保存 Wiener API 回调实例以供后续使用
        this.wienerApi = callbacks;

        // 设置扩展名称，这将被 Montoya API 设置的名称覆盖
        // 因为 Montoya API 的优先级更高
        callbacks.setExtensionName("Wiener 名称");

        try {
            // 创建 URL 对象，用于添加到范围
            URL url = new URL("http://test.url");
            // 将 URL 添加到范围，这样后续的检查就会通过
            callbacks.includeInScope(url);
        } catch (MalformedURLException e) {
            // 如果 URL 格式错误，抛出运行时异常
            // 这会导致扩展加载失败
            throw new RuntimeException(e);
        }
    }

    /**
     * 自定义套件标签组件
     * 用于展示如何在同一个组件中同时使用新旧两个 API
     */
    private class MySuiteTab extends JComponent {
        /**
         * 构造函数
         * 初始化套件标签的界面组件
         */
        public MySuiteTab() {
            // 设置布局为垂直排列，使组件从上到下排列
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            // 创建自定义标签内容面板
            JPanel customTabContent = new JPanel();
            // 设置面板名称，用于调试和识别
            customTabContent.setName("自定义标签面板");
            // 设置面板背景颜色为灰色
            customTabContent.setBackground(Color.GRAY);

            // 创建按钮，点击按钮时将同时使用新旧两个 API 记录日志
            JButton button = new JButton("将文件名打印到日志文件");
            // 添加按钮点击事件监听器
            button.addActionListener(e -> {
                // 使用 Montoya API 记录日志，显示扩展文件名
                montoyaApi.logging().logToOutput("Montoya API 用于记录:" + montoyaApi.extension().filename());
                // 使用 Wiener API 记录日志，显示扩展文件名
                wienerApi.printOutput("Wiener API 用于记录:" + wienerApi.getExtensionFilename());
            });

            // 将按钮添加到自定义标签内容面板
            customTabContent.add(button);
            // 将自定义标签内容面板添加到组件
            add(customTabContent);
        }
    }
}
