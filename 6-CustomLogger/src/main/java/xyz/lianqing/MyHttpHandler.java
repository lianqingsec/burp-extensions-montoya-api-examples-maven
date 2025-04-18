package xyz.lianqing;

import burp.api.montoya.http.handler.*;

/**
 * HTTP 请求处理器
 * 实现了 HttpHandler 接口，用于处理所有 HTTP 请求和响应
 * 
 * 主要功能：
 * 1. 记录所有 HTTP 请求和响应
 * 2. 将响应信息添加到日志表格中
 * 3. 保持请求和响应的原始内容
 */
public class MyHttpHandler implements HttpHandler
{
    private final MyTableModel tableModel;

    /**
     * 构造函数
     * 
     * @param tableModel 日志表格数据模型，用于存储和显示 HTTP 响应
     */
    public MyHttpHandler(MyTableModel tableModel)
    {
        this.tableModel = tableModel;
    }

    /**
     * 处理即将发送的 HTTP 请求
     * 本示例中不对请求做任何修改
     * 
     * @param requestToBeSent 即将发送的 HTTP 请求
     * @return 继续发送原始请求
     */
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent)
    {
        return RequestToBeSentAction.continueWith(requestToBeSent);
    }

    /**
     * 处理接收到的 HTTP 响应
     * 将响应信息添加到日志表格中
     * 
     * @param responseReceived 接收到的 HTTP 响应
     * @return 继续使用原始响应
     */
    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived)
    {
        // 将响应添加到日志表格中
        tableModel.add(responseReceived);
        return ResponseReceivedAction.continueWith(responseReceived);
    }
}
