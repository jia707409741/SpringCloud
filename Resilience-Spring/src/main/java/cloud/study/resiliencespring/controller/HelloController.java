package cloud.study.resiliencespring.controller;

import cloud.study.resiliencespring.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
@RestController
public class HelloController
{
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }
}
