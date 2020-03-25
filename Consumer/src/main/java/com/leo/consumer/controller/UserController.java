package com.leo.consumer.controller;

import com.leo.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/25
 * @version: 1.0
 */
@RestController
public class UserController
{
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    HttpURLConnection conn = null;

    @GetMapping("/hello1")
    public String hello1()
    {
        try
        {
            URL url = new URL("http://localhost:8003/hello");
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200)
            {
                final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final String s = br.readLine();
                br.close();
                return s;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "err";
    }

    @GetMapping("/hello2")
    public String hello2()
    {
        //他返回的是一个list集合，因为你有可能是集群化部署。
        final List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        final ServiceInstance serviceInstance = provider.get(0);
        final String host = serviceInstance.getHost();//host主机名
        final int port = serviceInstance.getPort();//端口名
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");

        final String s = restTemplateOne.getForObject(stringBuffer.toString(), String.class);
        return s;
    }

    @GetMapping("/hello3")
    public String hello3()
    {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    @GetMapping("/hello4")
    public void hello4()
    {
        String s;
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        s = restTemplate.getForObject("http://provider/hello2?name={name}",
                String.class, map);
        System.out.println(s);
        ResponseEntity<String> s1 = restTemplate.getForEntity("http://provider/hello2?name={1}",
                String.class, "leo");
        final String body = s1.getBody();
        System.out.println(body);
        final HttpStatus statusCode = s1.getStatusCode();
        System.out.println(statusCode);
    }

    @GetMapping("/hello5")
    public void hello5()
    {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username","leo");
        map.add("password","123");
        map.add("id",1);
        User user = restTemplate.postForObject("http://provider/user1", map, User.class);
        System.out.println(user);

        user.setId(2);
        user.setUsername("zhbcm");
        user.setPassword("123");
        user = restTemplate.postForObject("http://provider/user2", user, User.class);
        System.out.println(user);
    }

    @GetMapping("/hello6")
    public void hello6(){
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username","leo");
        map.add("password","123");
        map.add("id",1);
        URI user = restTemplate.postForLocation("http://provider/register", map);
        String s = restTemplate.getForObject(user, String.class);
        System.out.println(s);
    }

    @GetMapping("/hello7")
    public void hello7(){
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username","leo");
        map.add("password","123");
        map.add("id",1);
        restTemplate.put("http://provider/updateUser1", map);
        //System.out.println(user);
        User user = new User();
        user.setId(2);
        user.setUsername("zhbcm");
        user.setPassword("123");
        restTemplate.put("http://provider/updateUser2", user);
        //System.out.println(user);
    }

    @GetMapping("/hello8")
    public void hello8(){
       restTemplate.delete("http://provider/deleteUser1?id={1}",1);
       restTemplate.delete("http://provider/deleteUser2/{1}",2);
    }
}
