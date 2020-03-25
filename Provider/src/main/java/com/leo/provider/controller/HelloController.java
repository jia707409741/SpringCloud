package com.leo.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/25
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello"+port;
    }
}
