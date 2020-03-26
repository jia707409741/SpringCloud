package com.leo.hystrix.common;

import com.leo.common.User;
import com.leo.hystrix.service.UserService;
import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Integer>
{
    private UserService userService;
    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id)
    {
        //定义延迟
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    //返回请求参数
    @Override
    public Integer getRequestArgument()
    {
        return id;
    }

    //请求合并方法
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection)
    {
        ArrayList<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User, Integer> request : collection)
        {
            ids.add(request.getArgument());
        }
        return new UserBatchCommand(ids,userService);
    }

    //请求结果分发
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection)
    {
        int count=0;
        for (CollapsedRequest<User, Integer> request : collection)
        {
            request.setResponse(users.get(count++));
        }
    }
}
