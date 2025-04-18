# Burp Suite Montoya API 示例项目

这是一个使用 Maven 构建的 Burp Suite Montoya API 示例项目集合。该项目是基于 [PortSwigger 官方示例项目](https://github.com/PortSwigger/burp-extensions-montoya-api-examples) 进行改写的，主要做了以下改进：

1. 将构建工具从 Gradle 切换为 Maven
2. 为所有代码示例添加了详细的中文注释
3. 优化了项目结构和模块组织
4. 保持了与官方示例相同的功能实现

该项目包含了 18 个不同的示例扩展，展示了 Burp Suite 扩展开发的各种功能和最佳实践。

## 环境要求

- Java 17 或更高版本
- Maven 3.6 或更高版本
- Burp Suite Professional 2023.3 或更高版本

## 构建说明

1. 克隆项目到本地：
```bash
git clone https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven.git
```

2. 进入项目目录：
```bash
cd burp-extensions-montoya-api-examples-maven
```

3. 构建所有模块：
```bash
mvn clean package
```

4. 构建后的 JAR 文件将位于各个模块的 `target` 目录下。

## 示例扩展列表

| 模块名                         | 扩展 | 描述                                          |
|-----------------------------|------|---------------------------------------------|
| 1-HelloWorld                | [Hello World](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/1-HelloWorld) | 演示如何在 Burp 的各个位置打印输出                        |
| 2-HTTPHandlers              | [HTTP 处理器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/2-HTTPHandlers) | 演示如何对通过 Burp 任何工具的请求执行各种操作                  |
| 3-ProxyHandlers             | [代理处理器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/3-ProxyHandlers) | 演示如何对通过代理的请求执行各种操作                          |
| 4-EventListeners            | [事件监听器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/4-EventListeners) | 演示如何注册各种运行时事件的处理程序，并在每个事件发生时打印消息            |
| 5-TrafficRedirector         | [流量重定向器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/5-TrafficRedirector) | 演示如何将所有出站请求从一个主机重定向到另一个主机                       |
| 6-CustomLogger              | [自定义日志记录器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/6-CustomLogger) | 演示如何在 Burp 的 UI 中添加新标签页，显示所有 Burp 工具的 HTTP 流量日志 |
| 7-CustomRequestEditorTab    | [自定义请求编辑器标签页](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/7-CustomRequestEditorTab) | 演示如何在 Burp 的 HTTP 消息编辑器中添加新标签页，以处理数据序列化格式       |
| 8-CustomScanInsertionPoints | [自定义扫描插入点](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/8-CustomScanInsertionPoints) | 演示如何为主动扫描提供自定义攻击插入点                             |
| 9-CustomScanChecks          | [自定义扫描检查](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/9-CustomScanChecks) | 演示如何实现自定义检查以扩展 Burp 的被动和主动扫描检查功能                |
| 10-CustomSessionTokens      | [自定义会话令牌](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/10-CustomSessionTokens) | 演示如何处理 Burp 通常无法理解的自定义会话令牌                    |
| 11-IntruderPayloads         | [Intruder 载荷](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/11-IntruderPayloads) | 演示如何提供自定义 Intruder 载荷和载荷处理                      |
| 12-Multi-API                | [多 API](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/12-Multi-API) | 演示如何在一个扩展中同时使用 Montoya API 和旧版 Wiener API     |
| 13-Persistence              | [持久化](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/13-Persistence) | 演示如何将数据保存和加载到项目文件中                            |
| 14-WebSocketHandlers        | [WebSocket 处理器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/14-WebSocketHandlers) | 演示如何对通过 Burp 任何工具的 WebSocket 消息执行各种操作         |
| 15-ProxyWebSocketHandlers   | [代理 WebSocket 处理器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/15-ProxyWebSocketHandlers) | 演示如何对通过代理的 WebSocket 消息执行各种操作                 |
| 16-MenuBar                  | [菜单栏](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/16-MenuBar) | 演示如何注册带有操作项的顶级菜单栏                               |
| 17-ContextMenu              | [上下文菜单](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/17-ContextMenu) | 演示如何注册新的上下文菜单项以打印请求和响应                          |
| 18-Collaborator             | [协作器](https://github.com/lianqingsec/burp-extensions-montoya-api-examples-maven/tree/master/18-Collaborator) | 演示如何使用协作器和持久化功能                               |