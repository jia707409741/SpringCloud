package com.study.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/4/21
 * @version: 1.0
 */
//@EnableBinding(Sink.class)表示绑定Sink消息通道
@EnableBinding(Sink.class)
public class MsgReceiver
{
    @StreamListener(Sink.INPUT)
    public void receive(Object payload)
    {
        System.out.println("receive: " + payload);
    }
}
