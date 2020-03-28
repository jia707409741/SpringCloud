package cloud.study.resiliencespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/28
 * @version: 1.0
 */
@SpringBootApplication
public class ResilienceSpringApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ResilienceSpringApplication.class);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
