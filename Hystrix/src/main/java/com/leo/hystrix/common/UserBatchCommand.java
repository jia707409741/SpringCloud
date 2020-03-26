package com.leo.hystrix.common;

import com.leo.common.User;
import com.leo.hystrix.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
public class UserBatchCommand extends HystrixCommand<List<User>>
{
    private List<Integer> ids;
    private UserService userService;

    @Override
    protected List<User> run() throws Exception
    {
        return userService.getUserByIds(ids);
    }

    public UserBatchCommand(List<Integer> ids, UserService userService)
    {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("batchKey")));
        this.ids = ids;
        this.userService = userService;
    }
}
