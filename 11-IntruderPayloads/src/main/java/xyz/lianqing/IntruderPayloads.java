package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 自定义 Intruder 载荷扩展
 * 实现了 BurpExtension 接口，用于在 Burp Suite 中添加自定义的 Intruder 载荷功能
 * 
 * 主要功能：
 * 1. 提供自定义的载荷生成器
 * 2. 支持自定义的载荷处理器
 * 3. 扩展 Intruder 工具的载荷处理能力
 */
public class IntruderPayloads implements BurpExtension
{
    /**
     * 扩展初始化方法
     * 设置扩展名称并注册自定义的载荷生成器和处理器
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    @Override
    public void initialize(MontoyaApi api)
    {
        // 设置扩展名称
        api.extension().setName("Intruder 载荷扩展");

        // 注册自定义的载荷生成器提供者
        api.intruder().registerPayloadGeneratorProvider(new MyPayloadGeneratorProvider());
        
        // 注册自定义的载荷处理器
        api.intruder().registerPayloadProcessor(new MyPayloadProcessor(api));
    }
}
