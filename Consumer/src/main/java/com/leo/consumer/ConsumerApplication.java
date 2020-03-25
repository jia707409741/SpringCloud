package com.leo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplateOne()
    {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced //开启负载均衡，默认算法为轮询
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
