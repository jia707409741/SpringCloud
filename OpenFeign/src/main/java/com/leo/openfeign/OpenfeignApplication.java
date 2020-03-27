package com.leo.openfeign;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OpenfeignApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(OpenfeignApplication.class, args);
    }

    @Bean
    Logger.Level logLevel()
    {
        return Logger.Level.FULL;
    }
}
