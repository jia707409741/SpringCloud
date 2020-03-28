package cloud.study.resiliencespring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
@Service
//@Retry(name = "retryA") //表示要使用重试策略
@CircuitBreaker(name = "cbA", fallbackMethod = "error")
public class HelloService
{
    @Autowired
    RestTemplate restTemplate;

    public String hello()
    {
        for (int i = 0; i < 5; i++)
        {
            restTemplate.getForObject("http://localhost:8003/hello", String.class);
        }
        return "success";
    }

    public String error(Throwable t)
    {
        return "error";
    }
}
