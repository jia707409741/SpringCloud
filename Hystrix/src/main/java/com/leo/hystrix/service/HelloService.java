package com.leo.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
@Service
public class HelloService
{
    @Autowired
    RestTemplate restTemplate;

    /**
     * 我们将调用provider里的hello接口，但是这个调用可能会失败，所以我们要在这个方法上加上
     *
     * @HystrixCommand注解，配置里面fallbackMethod的属性，表示调用该方法失败时，会调用临时
     * 的一个替代方法。
     */
    @HystrixCommand(fallbackMethod = "error")//服务降级
    public String hello()
    {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){
        return new AsyncResult<String>(){
            @Override
            public String invoke()
            {
                return restTemplate.getForObject("http://provider/hello",String.class);
            }
        };
    }

    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult //这个注解表示该方法的请求结果会被缓存起来，缓存key就是参数，value就是方法的返回值
    public String hello3(String name){
        return restTemplate.getForObject("http://provider/hello2?name={1}",String.class,name);
    }
    /**
     * @description：
     * 名字要和fallbackMethod返回值一致，方法返回值也要一致。总不能上面失败的方法返回是String类型
     * 你下面定义的临时调用方法返回Integer类型，这显然是不行的。
     * @since v1.0.0
     * author Leo
     * date 2020/3/26
     */
    public String error(Throwable t)
    {
        return "error"+t.getMessage();
    }

    public String error2(String name)
    {
        return "error"+name;
    }
}
