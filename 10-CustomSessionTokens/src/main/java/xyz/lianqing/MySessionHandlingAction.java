package xyz.lianqing;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.sessions.ActionResult;
import burp.api.montoya.http.sessions.SessionHandlingAction;
import burp.api.montoya.http.sessions.SessionHandlingActionData;

import java.util.List;

import static burp.api.montoya.http.sessions.ActionResult.actionResult;

/**
 * 自定义会话处理动作实现类
 * 实现了 SessionHandlingAction 接口，用于处理会话令牌的提取和更新
 * 
 * 主要功能：
 * 1. 从宏响应中提取会话令牌
 * 2. 更新请求中的会话头信息
 * 3. 支持自定义会话头的处理
 */
public class MySessionHandlingAction implements SessionHandlingAction
{
    /**
     * 获取动作名称
     * 
     * @return 动作名称
     */
    @Override
    public String name()
    {
        return "使用宏中的会话令牌";
    }

    /**
     * 执行会话处理动作
     * 从宏响应中提取会话令牌并更新请求
     * 
     * @param actionData 会话处理动作数据
     * @return 动作执行结果
     */
    @Override
    public ActionResult performAction(SessionHandlingActionData actionData)
    {
        // 创建默认的动作结果
        ActionResult result = actionResult(actionData.request(), actionData.annotations());

        // 获取宏请求响应列表
        List<HttpRequestResponse> macroRequestResponseList = actionData.macroRequestResponses();

        // 如果没有宏响应，直接返回默认结果
        if (macroRequestResponseList.isEmpty())
        {
            return result;
        }

        // 提取最后一个响应的头部信息
        List<HttpHeader> headers = macroRequestResponseList.get(macroRequestResponseList.size()-1).response().headers();

        // 查找会话头
        HttpHeader sessionHeader = findSessionHeader(headers);

        // 如果未找到会话令牌，返回默认结果
        if (sessionHeader == null)
        {
            return result;
        }

        // 创建更新了会话头的 HTTP 请求
        HttpRequest updatedRequest = actionData.request().withUpdatedHeader(sessionHeader);

        return actionResult(updatedRequest, actionData.annotations());
    }

    /**
     * 查找会话头
     * 在响应头中查找特定的会话令牌
     * 
     * @param headers 响应头列表
     * @return 找到的会话头，如果未找到则返回 null
     */
    private HttpHeader findSessionHeader(List<HttpHeader> headers)
    {
        HttpHeader sessionHeader = null;

        // 遍历所有响应头
        for(HttpHeader header : headers)
        {
            // 跳过非 "X-Custom-Session-Id" 的头
            if (!header.name().equals("X-Custom-Session-Id"))
            {
                continue;
            }

            // 获取会话令牌
            sessionHeader = header;
        }

        return sessionHeader;
    }
}
