package xyz.lianqing;

import burp.api.montoya.intruder.GeneratedPayload;
import burp.api.montoya.intruder.IntruderInsertionPoint;
import burp.api.montoya.intruder.PayloadGenerator;

import java.util.List;

/**
 * 自定义载荷生成器实现类
 * 实现了 PayloadGenerator 接口，用于生成自定义的 Intruder 载荷
 * 
 * 主要功能：
 * 1. 提供预定义的载荷列表
 * 2. 按顺序生成载荷
 * 3. 支持载荷生成结束标记
 */
public class MyPayloadGenerator implements PayloadGenerator
{
    // 预定义的载荷列表
    private static final List<String> PAYLOADS = List.of("|", "<script>alert(1)</script>");
    
    // 当前载荷索引
    private int payloadIndex;

    /**
     * 为插入点生成载荷
     * 
     * @param insertionPoint 插入点
     * @return 生成的载荷
     */
    @Override
    public GeneratedPayload generatePayloadFor(IntruderInsertionPoint insertionPoint)
    {
        // 增加载荷索引
        payloadIndex++;

        // 如果索引超出载荷列表大小，返回结束标记
        if (payloadIndex > PAYLOADS.size())
        {
            return GeneratedPayload.end();
        }

        // 获取当前索引对应的载荷
        String payload = PAYLOADS.get(payloadIndex);

        // 返回生成的载荷
        return GeneratedPayload.payload(payload);
    }
}
