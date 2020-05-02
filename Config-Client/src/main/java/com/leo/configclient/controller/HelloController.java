package com.leo.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/29
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Value("${leo}")
    String leo;

    @GetMapping("/hello")
    public String hello(){
        return leo;
    }
}
