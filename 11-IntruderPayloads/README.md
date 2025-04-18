# 自定义 Intruder 载荷示例扩展
============================

###### 提供自定义的 Intruder 载荷和载荷处理功能

---

## 扩展功能说明

本示例展示了如何使用扩展来实现以下功能：
- 生成自定义的 Intruder 载荷
- 对 Intruder 载荷（包括内置载荷）应用自定义处理

当扩展注册为 Intruder 载荷提供者时，用户可以在 Intruder 界面中选择该扩展作为攻击的载荷源。当扩展注册为载荷处理器时，用户可以创建载荷处理规则，并选择扩展的处理器作为规则的操作。

### 主要特性

- 提供自定义的载荷生成器
- 支持自定义的载荷处理器
- 支持序列化数据的处理
- 自动处理 Base64 和 URL 编码

### 技术实现

1. 载荷生成器：
   - 注册新的 `PayloadGeneratorProvider`，返回新的 `PayloadGenerator`
   - `PayloadGenerator` 实现以下功能：
     - 包含要使用的载荷列表
     - 遍历载荷列表，直到没有可用载荷

2. 载荷处理器：
   - 注册新的 `PayloadProcessor`
   - `PayloadProcessor` 实现以下功能：
     - 解码载荷的基础值
     - 解析解码数据中 `input` 字符串的位置
     - 使用新的载荷重建序列化数据

### 使用场景

- 测试命令注入漏洞
- 测试 XSS 漏洞
- 处理序列化的输入数据
- 自动化安全测试

### 注意事项

- 需要正确配置载荷生成器
- 确保载荷处理器的正确性
- 注意序列化数据的格式
- 建议在测试环境中充分验证

### 扩展配置

1. 安装扩展：
   - 将编译后的 JAR 文件加载到 Burp Suite
   - 确保扩展已正确启用

2. 使用方式：
   - 在 Intruder 工具中选择自定义载荷源
   - 配置载荷处理规则
   - 执行攻击测试

3. 调试建议：
   - 使用 Burp Suite 的日志功能
   - 检查载荷的生成和处理
   - 验证序列化数据的正确性

### 开发说明

1. 扩展结构：
   - `IntruderPayloads`：扩展入口类
   - `MyPayloadGenerator`：载荷生成器实现类
   - `MyPayloadGeneratorProvider`：载荷生成器提供者实现类
   - `MyPayloadProcessor`：载荷处理器实现类

2. 关键接口：
   - `PayloadGenerator`：载荷生成器接口
   - `PayloadGeneratorProvider`：载荷生成器提供者接口
   - `PayloadProcessor`：载荷处理器接口

3. 开发建议：
   - 遵循 Burp Suite 的扩展开发规范
   - 合理处理异常情况
   - 优化载荷处理性能
   - 提供清晰的错误信息