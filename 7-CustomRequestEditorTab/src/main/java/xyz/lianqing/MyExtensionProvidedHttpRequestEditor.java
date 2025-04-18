package xyz.lianqing;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.RawEditor;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.EditorMode;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpRequestEditor;
import burp.api.montoya.utilities.Base64EncodingOptions;
import burp.api.montoya.utilities.Base64Utils;
import burp.api.montoya.utilities.URLUtils;

import java.awt.*;
import java.util.Optional;

import static burp.api.montoya.core.ByteArray.byteArray;

/**
 * 自定义 HTTP 请求编辑器实现类
 * 提供对 HTTP 请求的编辑功能，包括请求内容的修改和显示
 * 
 * 主要功能：
 * 1. 显示和编辑 HTTP 请求内容
 * 2. 支持请求内容的修改和保存
 * 3. 提供请求内容的获取和设置方法
 */
class MyExtensionProvidedHttpRequestEditor implements ExtensionProvidedHttpRequestEditor
{
    private final RawEditor requestEditor;
    private final Base64Utils base64Utils;
    private final URLUtils urlUtils;
    private HttpRequestResponse requestResponse;
    private final MontoyaApi api;

    private ParsedHttpParameter parsedHttpParameter;

    /**
     * 构造函数
     * 
     * @param api Burp API 接口实例
     * @param creationContext 编辑器创建上下文
     */
    MyExtensionProvidedHttpRequestEditor(MontoyaApi api, EditorCreationContext creationContext)
    {
        this.api = api;
        base64Utils = api.utilities().base64Utils();
        urlUtils = api.utilities().urlUtils();

        if (creationContext.editorMode() == EditorMode.READ_ONLY)
        {
            requestEditor = api.userInterface().createRawEditor(EditorOptions.READ_ONLY);
        }
        else {
            requestEditor = api.userInterface().createRawEditor();
        }
    }

    /**
     * 获取当前编辑的请求内容
     * 
     * @return 当前请求的字节数组表示
     */
    @Override
    public HttpRequest getRequest()
    {
        HttpRequest request;

        if (requestEditor.isModified())
        {
            // reserialize data
            String base64Encoded = base64Utils.encodeToString(requestEditor.getContents(), Base64EncodingOptions.URL);
            String encodedData = urlUtils.encode(base64Encoded);

            request = requestResponse.request().withUpdatedParameters(HttpParameter.parameter(parsedHttpParameter.name(), encodedData, parsedHttpParameter.type()));
        }
        else
        {
            request = requestResponse.request();
        }

        return request;
    }

    /**
     * 设置要编辑的请求内容
     * 
     * @param request 要编辑的请求内容
     */
    @Override
    public void setRequestResponse(HttpRequestResponse requestResponse)
    {
        this.requestResponse = requestResponse;

        String urlDecoded = urlUtils.decode(parsedHttpParameter.value());

        ByteArray output;

        try
        {
            output = base64Utils.decode(urlDecoded);
        }
        catch (Exception e)
        {
            output = byteArray(urlDecoded);
        }

        this.requestEditor.setContents(output);
    }

    /**
     * 判断请求内容是否被修改
     * 
     * @return 如果请求内容被修改则返回 true，否则返回 false
     */
    @Override
    public boolean isModified()
    {
        return requestEditor.isModified();
    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse requestResponse)
    {
        Optional<ParsedHttpParameter> dataParam = requestResponse.request().parameters().stream().filter(p -> p.name().equals("data")).findFirst();

        dataParam.ifPresent(httpParameter -> parsedHttpParameter = httpParameter);

        return dataParam.isPresent();
    }

    @Override
    public String caption()
    {
        return "Serialized input";
    }

    @Override
    public Component uiComponent()
    {
        return requestEditor.uiComponent();
    }

    @Override
    public Selection selectedData()
    {
        return requestEditor.selection().isPresent() ? requestEditor.selection().get() : null;
    }
}