package com.study.stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/4/21
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Autowired
    MyChannel myChannel;

    @GetMapping("/hello")
    public void hello(){
        myChannel.output().send(MessageBuilder.withPayload("hello,controller").build());
    }
}
