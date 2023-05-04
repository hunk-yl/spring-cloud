package com.example.jcloudconsumerfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableEurekaClient // 表明这是一个eureka客户端
@EnableFeignClients(basePackages = "com.example.*") // 开始feign
public class JCloudConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(JCloudConsumerFeignApplication.class, args);
    }

}
