package xyz.lianqing;

import burp.api.montoya.intruder.AttackConfiguration;
import burp.api.montoya.intruder.PayloadGenerator;
import burp.api.montoya.intruder.PayloadGeneratorProvider;

/**
 * 自定义载荷生成器提供者实现类
 * 实现了 PayloadGeneratorProvider 接口，用于提供自定义的载荷生成器
 * 
 * 主要功能：
 * 1. 提供载荷生成器的显示名称
 * 2. 根据攻击配置创建载荷生成器
 * 3. 管理载荷生成器的生命周期
 */
public class MyPayloadGeneratorProvider implements PayloadGeneratorProvider
{
    /**
     * 获取显示名称
     * 
     * @return 载荷生成器的显示名称
     */
    @Override
    public String displayName()
    {
        return "自定义载荷生成器";
    }

    /**
     * 提供载荷生成器
     * 
     * @param attackConfiguration 攻击配置
     * @return 载荷生成器实例
     */
    @Override
    public PayloadGenerator providePayloadGenerator(AttackConfiguration attackConfiguration)
    {
        return new MyPayloadGenerator();
    }
}
