package com.example.sleuth.service;

import com.example.sleuth.controller.HelloController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService
{
    public static final Log log = LogFactory.getLog(HelloService.class);

    @Async
    public String backgroundFun()
    {
        log.info("backgroundFun");
        return "backgroundFun";
    }

    @Scheduled(cron = "0/5 * * * * ?") //每隔5秒执行一次
    public void scheduled1(){
        log.info("start");
        backgroundFun();
        log.info("end");
    }
}
