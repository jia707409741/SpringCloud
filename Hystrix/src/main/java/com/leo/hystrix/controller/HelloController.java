package com.leo.hystrix.controller;

import com.leo.common.User;
import com.leo.hystrix.common.Command;
import com.leo.hystrix.common.UserCollapseCommand;
import com.leo.hystrix.service.HelloService;
import com.leo.hystrix.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.java2d.pipe.SpanIterator;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Autowired
    HelloService helloService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserService userService;
    @GetMapping("/hello")
    public String hello(){
        int i=1/0;
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public void hello2(){
        Command command = new Command(HystrixCommand.Setter.
                withGroupKey(HystrixCommandGroupKey.Factory.asKey("leo")), restTemplate);
//        String execute = command.execute();//直接执行
//        System.out.println(execute);
        try
        {
            Future<String> queue = command.queue();
            String s = queue.get();
            System.out.println(s);//先入队后执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/hello3")
    public void hello3(){
        Future<String> future = helloService.hello2();
        try
        {
            String s = future.get();
            System.out.println(s);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/hello4")
    public void hello4(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        String s = helloService.hello3("leo");
        s = helloService.hello3("leo");
        ctx.close();
    }

    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException
    {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        UserCollapseCommand cmd1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand cmd2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand cmd3 = new UserCollapseCommand(userService, 97);
        UserCollapseCommand cmd4 = new UserCollapseCommand(userService, 96);
        Future<User> queue1 = cmd1.queue();
        Future<User> queue2 = cmd2.queue();
        Future<User> queue3 = cmd3.queue();
        Future<User> queue4 = cmd4.queue();
        User user1 = queue1.get();
        User user2 = queue2.get();
        User user3 = queue3.get();
        User user4 = queue4.get();
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user4);
        ctx.close();
    }

    @GetMapping("/hello6")
    public void hello6() throws ExecutionException, InterruptedException
    {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> q1 = userService.getUsersById(99);
        Future<User> q2 = userService.getUsersById(98);
        Future<User> q3 = userService.getUsersById(97);
        User user1 = q1.get();
        User user2 = q2.get();
        User user3 = q3.get();
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        Thread.sleep(2000);
        Future<User> q4 = userService.getUsersById(97);
        User user4 = q4.get();
        System.out.println(user4);
        ctx.close();
    }
}
