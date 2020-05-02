package com.example.zipkin02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController
{
    public static final Log log= LogFactory.getLog(HelloController.class);

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public void hello(){
        String s = restTemplate.getForObject("http://localhost:8080/hello?name={1}", String.class, "leo");
        log.info(s);
    }
}
