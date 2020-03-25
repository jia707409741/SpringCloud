## Eureka基本搭建

一、创建一个普通的maven工程（最普通的那种），然后清除里面的src目录。

二、右击工程名，创建SpringBoot工程的子module

​	**添加依赖**

```java
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```
​	**添加配置**

```pro
spring.application.name=eureka
server.port=8001

#表示当前项目不要注册
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

​	**启动类上添加Eureka服务注解**

> @EnableEurekaServer

## Eureka服务注册

右击工程名，添加新的SpringBoot模块`provider`

​	**添加依赖**

```java
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

​	**添加配置**

```properties
spring.application.name=provider
server.port=8003
eureka.client.service-url.defaultZone=http://localhost:8001/eureka
```

## 服务消费

首先在`provider`里添加一个接口

```java
@RestController
public class HelloController
{
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
```

然后我们在创建一个消费者`consumer`，来消费`provider`里的接口

**添加依赖**

```java
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

**添加配置**

```pro
spring.application.name=consumer
server.port=8004
eureka.client.service-url.defaultZone=http://localhost:8001/eureka
```

**调用`provider`代码**

使用HttpURLConnection发起请求，请求地址固定~~

```java
@RestController
public class UserController
{
    HttpURLConnection conn=null;
    @GetMapping("/hello1")
    public String hello() throws MalformedURLException
    {
        try
        {
            URL url = new URL("http://localhost:8003/hello");
            conn= (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode()==200){
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
}
```

**最后浏览器结果**

![1585115007281](Eureka基本搭建.assets/1585115007281.png)

可以看出，这样我们就可以消费服务端提供的接口，但实际上这是写死，是不可能这么写的，否则一个地方要改其他很多地方都要改，所以地址要动态获取。

>旧版本的Eureka需要在服务启动类加上@EnableEurekaClient注解，新版的不需要



**代码改造**

借助Eureka Client的DiscoveryClient，我们可以从Eureka Server中查询到服务的详细信息

```java
    @GetMapping("/hello2")
    public String hello2() throws MalformedURLException
    {
        //他返回的是一个list集合，因为你有可能是集群化部署。
        final List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        final ServiceInstance serviceInstance = provider.get(0);
        final String host = serviceInstance.getHost();//host主机名
        final int port = serviceInstance.getPort();//端口名
        try
        {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("http://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/hello");
            URL url = new URL(stringBuffer.toString());
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
```

> DiscoveryClient查询到的服务列表是一个集合，因为服务在部署过程中，可能是集群化部署。



**集群化部署**

一、改造服务提供者

```java
@RestController
public class HelloController
{
    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello"+port;
    }
}
```

二、打包服务提供者，为了开通不同端口

>  java -jar provider-0.0.1-SNAPSHOT.jar --server.port=1111

三、打开注册中心，看是否成功注册

![1585117022607](Eureka基本搭建.assets/1585117022607.png)

这样的话，DiscoveryClient里的服务实例就不再是一个了，而是两个。

四、手动实现负载均衡

```java
    int count=0;
    @GetMapping("/hello3")
    public String hello3()
    {
        //他返回的是一个list集合，因为你有可能是集群化部署。
        final List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        final ServiceInstance serviceInstance = provider.get((count++) % provider.size());
        final String host = serviceInstance.getHost();//host主机名
        final int port = serviceInstance.getPort();//端口名
        try
        {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("http://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/hello");
            URL url = new URL(stringBuffer.toString());
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
```



访问端口 http://localhost:8004/hello3 ，返回的结果，你会很清楚的看见两个端口来回切换。

