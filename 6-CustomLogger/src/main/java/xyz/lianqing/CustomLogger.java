package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;

import javax.swing.*;
import java.awt.*;

import static burp.api.montoya.ui.editor.EditorOptions.READ_ONLY;

/**
 * 自定义日志记录器扩展
 * 实现了 BurpExtension 接口，Burp 会自动检测并加载
 * 
 * 主要功能：
 * 1. 记录所有 HTTP 请求和响应
 * 2. 提供自定义的日志查看界面
 * 3. 支持请求和响应的详细查看
 * 4. 显示请求来源工具和 URL 信息
 */
public class CustomLogger implements BurpExtension {
    private MontoyaApi api;

    /**
     * 扩展初始化方法
     * 设置扩展名称、创建日志表格模型并注册 HTTP 处理器
     * 
     * @param api Burp API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        // 设置扩展名称
        api.extension().setName("自定义日志记录器");

        // 创建日志表格模型
        MyTableModel tableModel = new MyTableModel();
        
        // 注册自定义标签页和 HTTP 处理器
        api.userInterface().registerSuiteTab("自定义日志记录器", constructLoggerTab(tableModel));
        api.http().registerHttpHandler(new MyHttpHandler(tableModel));
    }

    /**
     * 构建日志记录器标签页
     * 创建包含请求/响应查看器和日志表格的界面
     * 
     * @param tableModel 日志表格数据模型
     * @return 构建好的界面组件
     */
    private Component constructLoggerTab(MyTableModel tableModel) {
        // 创建主分割面板，垂直分割
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // 创建请求/响应查看器标签页
        JTabbedPane tabs = new JTabbedPane();

        // 获取用户界面实例
        UserInterface userInterface = api.userInterface();

        // 创建只读的请求和响应查看器
        HttpRequestEditor requestViewer = userInterface.createHttpRequestEditor(READ_ONLY);
        HttpResponseEditor responseViewer = userInterface.createHttpResponseEditor(READ_ONLY);

        // 添加请求和响应标签页
        tabs.addTab("请求", requestViewer.uiComponent());
        tabs.addTab("响应", responseViewer.uiComponent());

        // 将标签页添加到分割面板右侧
        splitPane.setRightComponent(tabs);

        // 创建日志表格
        JTable table = new JTable(tableModel) {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                // 当选择表格行时，显示对应的请求和响应
                HttpResponseReceived responseReceived = tableModel.get(rowIndex);
                requestViewer.setRequest(responseReceived.initiatingRequest());
                responseViewer.setResponse(responseReceived);

                super.changeSelection(rowIndex, columnIndex, toggle, extend);
            }
        };

        // 为表格添加滚动条
        JScrollPane scrollPane = new JScrollPane(table);

        // 将表格添加到分割面板左侧
        splitPane.setLeftComponent(scrollPane);

        return splitPane;
    }
}
