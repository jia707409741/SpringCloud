package com.leo.openfeign.service;

import com.leo.common.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
@Component //添加该注解
@RequestMapping("/leo") //防止请求地址重复
public class HelloServiceFallBack implements HelloService
{
    @Override
    public String hello()
    {
        return "error1";
    }

    @Override
    public String hello2(String name)
    {
        return "error2";
    }

    @Override
    public User addUser2(User user)
    {
        return null;
    }

    @Override
    public void deleteUser2(Integer id)
    {

    }

    @Override
    public void getUserByName(String name) throws UnsupportedEncodingException
    {

    }
}

