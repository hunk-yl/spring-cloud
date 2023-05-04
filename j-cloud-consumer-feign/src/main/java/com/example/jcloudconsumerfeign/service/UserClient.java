package com.example.jcloudconsumerfeign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName UserClient
 * @Description TODO
 * @Author yanglin
 * @Date 2023/4/24 14:03
 * @Version 1.0
 **/
@FeignClient("PROVIDER-USER")
public interface UserClient {

    /**
     * @Author yanglin
     * @Description feign中没有原生的@GetMapping/@PostMapping/@DeleteMapping/@PutMapping,要制定需要用method进行
     * 接口上方用requestmapping指定是服务提供者的哪个controller提供服务
     * @Date 2023/4/24 14:07
     * @Param
     * @return
     **/

    @RequestMapping(value = "/user/sayHello", method = RequestMethod.GET)
    public String sayHello();

    @RequestMapping(value = "/user/sayHi", method = RequestMethod.GET)
    public String sayHi();

    @RequestMapping(value = "/user/sayHaha", method = RequestMethod.GET)
    public String sayHaha();
}

