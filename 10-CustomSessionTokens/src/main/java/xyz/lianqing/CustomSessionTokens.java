package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 自定义会话令牌扩展
 * 实现了 BurpExtension 接口，用于在 Burp Suite 中添加自定义的会话处理功能
 * 
 * 主要功能：
 * 1. 提供自定义的会话处理动作
 * 2. 支持从宏中提取会话令牌
 * 3. 自动更新请求中的会话信息
 * 
 * 注意：Burp Suite 会自动检测并加载任何继承 BurpExtension 的类
 */
public class CustomSessionTokens implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 设置扩展名称并注册自定义的会话处理动作
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("会话令牌示例");

        // 注册自定义的会话处理动作
        api.http().registerSessionHandlingAction(new MySessionHandlingAction());
    }
}
