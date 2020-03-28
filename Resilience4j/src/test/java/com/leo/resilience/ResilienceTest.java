package com.leo.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
public class ResilienceTest
{
    @Test
    public void test1()
    {
        //注意，创建普通的maven工程，他的模块自带的jdk版本是5，你需要改成8
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比,一旦超过，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在达到设置时间之后，断路器会进入到一个半打开状态！
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于半打开状态时，环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreakerRegistry of = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb = of.circuitBreaker("leo", config);
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb, () -> "hello ResilienceTest");
        Try<String> map = Try.of(supplier)
                .map(v -> v + " hello");
        System.out.println(map.isSuccess());
        System.out.println(map.get());
    }

    @Test
    public void test2()
    {
        //注意，创建普通的maven工程，他的模块自带的jdk版本是5，你需要改成8
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比,一旦超过，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在达到设置时间之后，断路器会进入到一个半打开状态！
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于半打开状态时，环形缓冲区的大小
//                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreakerRegistry of = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb = of.circuitBreaker("leo");
        System.out.println(cb.getState());
        cb.onError(0, new RuntimeException());
        System.out.println(cb.getState());
        cb.onError(0, new RuntimeException());
        System.out.println(cb.getState());
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb, () -> "hello ResilienceTest");
        Try<String> map = Try.of(supplier)
                .map(v -> v + " hello");
        System.out.println(map.isSuccess());
        System.out.println(map.get());
    }

    @Test
    public void test3()
    {
        RateLimiterConfig config = RateLimiterConfig.custom()
                //阈值刷新时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                //阈值刷新频次
                .limitForPeriod(2)
                //冷却时间
                .timeoutDuration(Duration.ofMillis(1000))
                .build();
        RateLimiter limiter = RateLimiter.of("leo", config);

        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(limiter, () ->
        {
            System.out.println(new Date());
        });
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t->{
                    System.out.println(t.getMessage());
                });
    }

    @Test
    public void test04(){
        RetryConfig config = RetryConfig.custom()
                //重试次数
                .maxAttempts(5)
                //间隔时间
                .waitDuration(Duration.ofMillis(500))
                //抛出哪个异常就进行重试
                .retryExceptions(RuntimeException.class)
                .build();
        Retry of = Retry.of("leo", config);
        Retry.decorateRunnable(of, new Runnable()
        {
            int count=0;
            @Override
            public void run()
            {
                if(count++ <3){
                    throw new RuntimeException();
                }
            }
        }).run();
    }
}
