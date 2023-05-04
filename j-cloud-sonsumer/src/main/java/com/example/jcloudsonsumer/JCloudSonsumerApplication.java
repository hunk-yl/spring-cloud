package com.example.jcloudsonsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JCloudSonsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JCloudSonsumerApplication.class, args);
    }

}
