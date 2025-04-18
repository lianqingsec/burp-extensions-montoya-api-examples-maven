package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 自定义扫描检查扩展
 * 实现了 BurpExtension 接口，用于在 Burp Suite 中添加自定义的扫描检查功能
 * 
 * 主要功能：
 * 1. 提供自定义的扫描检查实现
 * 2. 支持主动和被动扫描检查
 * 3. 检测特定的安全漏洞和敏感信息泄露
 */
public class CustomScanChecks implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 设置扩展名称并注册自定义的扫描检查实现
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("自定义扫描检查");

        // 注册自定义的扫描检查实现
        api.scanner().registerScanCheck(new MyScanCheck(api));
    }
}
