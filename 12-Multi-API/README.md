# 多 API 示例扩展
============================

###### 演示如何在同一个扩展中同时使用 Montoya API 和旧版 Extender API

---

## 扩展功能说明

本示例展示了如何在同一个扩展中同时使用新旧两个版本的 API，主要功能包括：

1. 初始化顺序演示：
   - `registerExtenderCallbacks()` 方法首先被调用
     - 注册扩展名称
     - 将 `http://test.url` 添加到范围内
   - `initialize()` 方法随后被调用
     - 注册新的套件标签
     - 注册扩展名称（会覆盖 `registerExtenderCallbacks()` 设置的名称）
     - 检查 `http://test.url` 是否在范围内，并将结果写入仪表板

2. 自定义套件标签：
   - 包含一个功能按钮
   - 点击按钮时，会同时使用新旧两个 API 打印扩展文件名
   - 展示新旧 API 的混合使用方式

## 技术实现

1. API 优先级：
   - Montoya API 的优先级高于旧版 Extender API
   - 当两个 API 设置相同的属性时，Montoya API 的设置会覆盖旧版 API 的设置

2. 初始化流程：
   - 首先调用旧版 API 的 `registerExtenderCallbacks()`
   - 然后调用 Montoya API 的 `initialize()`
   - 这种顺序确保了向后兼容性

3. 范围管理：
   - 使用旧版 API 添加 URL 到范围
   - 使用 Montoya API 检查 URL 是否在范围内
   - 展示了新旧 API 在范围管理上的互操作性

## 使用场景

1. 迁移场景：
   - 帮助开发者从旧版 API 迁移到 Montoya API
   - 展示新旧 API 的对应关系
   - 演示混合使用的最佳实践

2. 功能扩展：
   - 在保持旧功能的同时添加新功能
   - 利用新 API 的特性增强扩展功能
   - 实现平滑过渡

## 注意事项

1. 开发建议：
   - 优先使用 Montoya API 的新功能
   - 仅在必要时使用旧版 API
   - 注意 API 的优先级关系

2. 兼容性考虑：
   - 确保扩展在不同版本的 Burp Suite 中都能正常工作
   - 合理处理新旧 API 的差异
   - 提供适当的错误处理机制

3. 性能优化：
   - 避免重复调用新旧 API 的相同功能
   - 合理管理资源使用
   - 优化初始化流程

## 扩展工作流程

本扩展的工作流程如下：

1. `registerExtenderCallbacks()` 方法被调用：
   - 注册扩展名称
   - 将 `http://test.url` 添加到范围内

2. `initialize()` 方法被调用：
   - 注册新的套件标签
   - 注册扩展名称（会覆盖 `registerExtenderCallbacks()` 设置的名称）
   - 检查 `http://test.url` 是否在范围内，并将结果写入仪表板

3. 新的套件标签包含一个按钮：
   - 当按钮被点击时，会同时使用 Wiener API 和 Montoya API 打印扩展文件名到输出