package com.example.zipkin01;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    public static final Log log= LogFactory.getLog(HelloController.class);

    @GetMapping("/hello")
    public String hello(String name){
        log.info("zipkin01 hello!");
        return "hello"+name;
    }
}
