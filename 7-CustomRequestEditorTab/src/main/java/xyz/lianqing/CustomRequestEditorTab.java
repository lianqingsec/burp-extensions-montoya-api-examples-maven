package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 自定义请求编辑器标签页扩展
 * 实现了 BurpExtension 接口，用于在 Burp Suite 中添加自定义的请求编辑器
 * 
 * 主要功能：
 * 1. 提供序列化输入编辑器
 * 2. 支持对请求中的特定参数进行特殊处理
 * 3. 允许用户以更友好的方式查看和编辑序列化数据
 */
public class CustomRequestEditorTab implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 设置扩展名称并注册自定义的 HTTP 请求编辑器提供者
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("序列化输入编辑器");

        // 注册自定义的 HTTP 请求编辑器提供者
        api.userInterface().registerHttpRequestEditorProvider(new MyHttpRequestEditorProvider(api));
    }
}
