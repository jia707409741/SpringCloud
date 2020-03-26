package com.leo.consulconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    LoadBalancerClient client;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public void hello()
    {
        ServiceInstance choose = client.choose("consul-provider");
        System.out.println(choose.getUri());
        System.out.println(choose.getServiceId());
        String s = restTemplate.getForObject(choose.getUri() + "/hello", String.class);
        System.out.println(s);
    }
}
