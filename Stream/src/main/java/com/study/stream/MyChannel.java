package com.study.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @description: 消息通道接口
 * @author: Leo
 * @createDate: 2020/4/21
 * @version: 1.0
 */
public interface MyChannel
{
    String INPUT="leo-INPUT";
    String OUTPUT="leo-OUTPUT";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();
}
