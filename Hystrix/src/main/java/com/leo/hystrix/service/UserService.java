package com.leo.hystrix.service;

import com.leo.common.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
@Service
public class UserService
{
    @Autowired
    RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "200")})
    public Future<User> getUsersById(Integer id)
    {
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids)
    {
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class, StringUtils.join(ids, ','));
        return Arrays.asList(users);
    }
}
