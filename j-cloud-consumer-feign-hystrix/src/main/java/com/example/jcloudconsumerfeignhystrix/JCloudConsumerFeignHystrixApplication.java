package com.example.jcloudconsumerfeignhystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringCloudApplication
@EnableEurekaClient // eureka客户端
@EnableFeignClients // 断路器客户端
@EnableHystrixDashboard // 开启仪表盘
public class JCloudConsumerFeignHystrixApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(JCloudConsumerFeignHystrixApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        log.info("\n-----------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Local:\t\thttp://localhost:" + port + path + "/\n\t" +
                "External:\thttp://" + ip + ":" + port + path +"/\n\t" +
                "swagger-ui:\t\thttp://" + ip + ":" + port + path + "/doc.html/\n\t" +
                "HystrixDashboard:\t\thttp://" + ip + ":" + port + path + "/hystrix\n" +
                "---------------------------------------------------");
    }

}
