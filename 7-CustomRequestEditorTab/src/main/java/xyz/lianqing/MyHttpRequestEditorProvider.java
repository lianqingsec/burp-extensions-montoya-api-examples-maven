package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpRequestEditor;
import burp.api.montoya.ui.editor.extension.HttpRequestEditorProvider;

/**
 * HTTP 请求编辑器提供者实现类
 * 负责创建和管理自定义的 HTTP 请求编辑器实例
 * 
 * 主要职责：
 * 1. 根据编辑器创建上下文提供合适的编辑器实例
 * 2. 管理编辑器的生命周期
 * 3. 确保编辑器与 Burp API 的正确集成
 */
class MyHttpRequestEditorProvider implements HttpRequestEditorProvider
{
    private final MontoyaApi api;

    /**
     * 构造函数
     * 
     * @param api Burp API 接口实例，用于访问 Burp 的各种功能
     */
    MyHttpRequestEditorProvider(MontoyaApi api)
    {
        this.api = api;
    }

    /**
     * 提供 HTTP 请求编辑器实例
     * 根据创建上下文返回合适的编辑器实现
     * 
     * @param creationContext 编辑器创建上下文，包含创建编辑器所需的信息
     * @return 自定义的 HTTP 请求编辑器实例
     */
    @Override
    public ExtensionProvidedHttpRequestEditor provideHttpRequestEditor(EditorCreationContext creationContext)
    {
        return new MyExtensionProvidedHttpRequestEditor(api, creationContext);
    }
}
