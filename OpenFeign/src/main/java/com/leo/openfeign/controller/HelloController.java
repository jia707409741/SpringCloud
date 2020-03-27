package com.leo.openfeign.controller;

import com.leo.common.User;
import com.leo.openfeign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Autowired
    HelloService helloService;

//    @GetMapping("/hello")
//    public String hello(){
//        return helloService.hello();
//    }

    @GetMapping("/hello")
    public String hello2() throws UnsupportedEncodingException
    {
        String s = helloService.hello2("leo");
        System.out.println(s);
        User user = new User();
        user.setId(1);
        user.setUsername("leo");
        user.setPassword("123");
        User user1 = helloService.addUser2(user);
        System.out.println(user1);
        helloService.deleteUser2(1);
        helloService.getUserByName(URLEncoder.encode("leo","UTF-8"));
        return helloService.hello();
    }
}