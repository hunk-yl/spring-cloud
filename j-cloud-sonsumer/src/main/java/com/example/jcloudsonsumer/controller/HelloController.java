package com.example.jcloudsonsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yanglin
 * @Date 2023/4/24 10:12
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Bean
    @LoadBalanced
    public RestTemplate getResttemplate(){
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello(){
        // 指出服务地址 http://{服务提供者应用名称}/{具体的uri}
        String url = "http://provider-user/user/sayHello";

        // 返回值类型和我们的业务返回值一致
        return restTemplate.getForObject(url, String.class);
    }

    @RequestMapping("/hi")
    public String hi(){
        // 指出服务地址 http://{服务提供者应用名名称}/{具体的uri}
        String url="http://provider-user/user-sayHi";

        // 返回值类型和我们的业务返回值一致
        return restTemplate.getForObject(url, String.class);
    }

    public String haha(){
        //指出服务地址 http://{服务提供者应用名称}/{具体的uri}
        String url = "http://provider-user/user/sayHaha";

        // 返回值类型和我们的业务返回值一致
        return restTemplate.getForObject(url, String.class);
    }
}
