package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 自定义扫描插入点扩展
 * 实现了 BurpExtension 接口，用于在 Burp Suite 中添加自定义的扫描插入点
 * 
 * 主要功能：
 * 1. 提供自定义的扫描插入点提供者
 * 2. 支持对特定参数进行扫描插入点处理
 * 3. 允许用户自定义扫描插入点的位置和行为
 */
public class CustomScanInsertionPoints implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 设置扩展名称并注册自定义的扫描插入点提供者
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("自定义扫描插入点");

        // 注册自定义的扫描插入点提供者
        api.scanner().registerInsertionPointProvider(new MyInsertionPointProvider(api));
    }
}