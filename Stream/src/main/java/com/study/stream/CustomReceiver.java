package com.study.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @description: 自定义消息收发器
 * @author: Leo
 * @createDate: 2020/4/21
 * @version: 1.0
 */
@EnableBinding(MyChannel.class)
public class CustomReceiver
{
    @StreamListener(MyChannel.INPUT)
    public void receive(Object payload){
        System.out.println("receive "+payload);
    }
}
