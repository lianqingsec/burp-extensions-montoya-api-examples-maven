HTTP 处理器示例扩展
============================

###### 演示如何对通过 Burp 任何工具的请求执行各种操作

---

## 项目简介

这是一个 HTTP 处理器示例扩展，展示了如何在 Burp Suite 中处理 HTTP 请求和响应。通过这个扩展，你可以学习到如何拦截、修改和标记 HTTP 流量。

## 功能特点

本扩展实现了以下功能：

1. 请求处理
   - 自动检测 POST 请求
   - 记录 POST 请求的请求体内容
   - 为 POST 请求添加注释标记
   - 自动为所有请求添加 URL 参数（foo=bar）

2. 响应处理
   - 检测请求中的 Content-Length 头
   - 为包含 Content-Length 头的响应添加蓝色高亮标记

## 技术实现

扩展通过以下方式工作：

1. 注册 HTTP 处理器
   - 使用 `api.http().registerHttpHandler()` 注册自定义处理器
   - 处理器会拦截所有通过 Burp 的 HTTP 流量

2. 处理发送的请求
   - 检查请求方法是否为 POST
   - 如果是 POST 请求：
     * 将请求体内容记录到输出日志
     * 添加注释说明这是一个 POST 请求
   - 为所有请求添加 URL 参数

3. 处理接收的响应
   - 检查原始请求是否包含 Content-Length 头
   - 如果包含，为响应添加蓝色高亮标记

## 使用场景

这个扩展适用于以下场景：
1. 需要监控特定类型请求（如 POST 请求）的内容
2. 需要自动修改所有请求（如添加参数）
3. 需要根据请求特征标记响应（如高亮显示）

## 开发参考

本示例使用了以下 Burp API：
- `HttpHandler` 接口：处理 HTTP 请求和响应
- `Annotations` 类：管理请求和响应的注释
- `HighlightColor` 枚举：定义高亮颜色
- `HttpParameter` 类：处理 URL 参数

这个示例是学习 Burp HTTP 处理器开发的良好起点，展示了如何拦截和修改 HTTP 流量。