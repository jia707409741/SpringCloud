package com.leo.openfeign.service;

import com.leo.common.User;
import leo.study.feign.IUserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
//@FeignClient(value = "provider",fallback = HelloServiceFallBack.class)//绑定服务
@FeignClient(value = "provider",fallbackFactory = FallBackFactory.class)//绑定服务
public interface HelloService extends IUserService
{
    /**
     * author Leo
     * date 2020/3/27

    @GetMapping("/hello")
    String hello();//定义一个方法名

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);

    @PostMapping("/user2")
    User addUser(@RequestBody User user);

    @DeleteMapping("/deleteUser2/{id}")
    void deleteUserById(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name);
     */
}
