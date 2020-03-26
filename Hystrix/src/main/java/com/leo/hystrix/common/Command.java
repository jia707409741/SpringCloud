package com.leo.hystrix.common;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
public class Command extends HystrixCommand<String>
{
    RestTemplate restTemplate;

    public Command(Setter setter, RestTemplate restTemplate)
    {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception
    {
        return restTemplate.getForObject("http://provider/hello",String.class);
    }

    /**
     * @description：
     * 请求失败的回调
     * @since v1.0.0
     * author Leo
     * date 2020/3/26
     */
    @Override
    protected String getFallback()
    {
        return "error-extends";
    }
}
