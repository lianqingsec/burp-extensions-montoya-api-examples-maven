package xyz.lianqing;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * 流量重定向器扩展
 * 实现了 BurpExtension 接口，Burp 会自动检测并加载
 * 
 * 主要功能：
 * 1. 将指定源主机的流量重定向到目标主机
 * 2. 自动更新请求中的 Host 头部
 * 3. 保持原始端口和协议（HTTP/HTTPS）不变
 * 
 * 使用场景：
 * 1. 测试环境流量重定向
 * 2. 开发环境调试
 * 3. 负载均衡测试
 */
public class TrafficRedirector implements BurpExtension {
    /**
     * 源主机名，需要重定向的原始主机
     * 示例：host1.example.org
     */
    static final String HOST_FROM = "host1.example.org";
    
    /**
     * 目标主机名，重定向后的目标主机
     * 示例：host2.example.org
     */
    static final String HOST_TO = "host2.example.org";

    /**
     * 扩展初始化方法
     * 设置扩展名称并注册 HTTP 处理器
     * 
     * @param api Burp API 接口实例
     */
    @Override
    public void initialize(MontoyaApi api) {
        // 设置扩展名称
        api.extension().setName("流量重定向器");

        // 注册 HTTP 处理器，用于处理所有 HTTP 请求
        api.http().registerHttpHandler(new MyHttpHandler());
    }
}
