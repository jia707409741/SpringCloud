package com.leo.openfeign.service;

import com.leo.common.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
@Component
public class FallBackFactory implements FallbackFactory<HelloService>
{
    @Override
    public HelloService create(Throwable throwable)
    {
        return new HelloService()
        {
            @Override
            public String hello()
            {
                return "error--->1";
            }

            @Override
            public String hello2(String name)
            {
                return "error--->2";
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
        };
    }
}
