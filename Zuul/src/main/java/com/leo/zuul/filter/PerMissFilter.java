package com.leo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/28
 * @version: 1.0
 */
@Component
public class PerMissFilter extends ZuulFilter
{
    //过滤器类型
    @Override
    public String filterType()
    {
        return "pre";
    }

    //过滤器优先级
    @Override
    public int filterOrder()
    {
        return 0;
    }

    //是否过滤
    @Override
    public boolean shouldFilter()
    {
        return true;
    }

    //核心过滤逻辑
    @Override
    public Object run() throws ZuulException
    {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();//获取当前请求
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if(!"leo".equals(name)||!"123".equals(password)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.addZuulResponseHeader("content-type","text/html;charset=utf-8");
            ctx.setResponseBody("非法访问！");
        }
        return null;
    }
}
