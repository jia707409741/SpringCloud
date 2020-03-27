package com.leo.provider.controller;

import com.leo.common.User;
import leo.study.feign.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/25
 * @version: 1.0
 */
@RestController
public class HelloController implements IUserService
{
    @Value("${server.port}")
    Integer port;

    @Override
//    @GetMapping("/hello") 因为实现了接口，映射关系随着接口的实现被带过来了
    public String hello()
    {
        return "hello" + port;
    }

    @Override
//    @GetMapping("/hello2")
    public String hello2(String name)
    {
        System.out.println(new Date()+"--->"+name);
        return name;
    }

    @PostMapping("/user1")
    public User addUser1(User user)
    {
        return user;
    }

//    @PostMapping("/user2")
    @Override
    public User addUser2(@RequestBody User user)
    {
        return user;
    }

    @PutMapping("/updateUser1")
    public void updateUser1(User user)
    {
        System.out.println(user);
    }

    @PutMapping("/updateUser2")
    public void updateUser2(@RequestBody User user)
    {
        System.out.println(user);
    }

    @DeleteMapping("/deleteUser1")
    public void deleteUser1(Integer id)
    {
        System.out.println(id);
    }

//    @DeleteMapping("/deleteUser2/{id}")
    @Override
    public void deleteUser2(@PathVariable Integer id)
    {
        System.out.println(id);
    }

//    @GetMapping("/user3")
    @Override
    public void getUserByName(@RequestHeader String name) throws UnsupportedEncodingException
    {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }
}
