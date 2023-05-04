package com.example.jcloudconsumerfeign.controller;

import com.example.jcloudconsumerfeign.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yanglin
 * @Date 2023/4/24 13:10
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Autowired
    private UserClient feignClient;

    /**
     * @Author yanglin
     * @Description //此处的mapping是一级controller，调用方法里边绑定了二级的controller，相当于用http完成一次转发
     * @Date 2023/4/24 14:06
     * @Param
     * @return
     **/
    @GetMapping("/hello")
    public String hello(){
        return feignClient.sayHello();
    }

    @GetMapping("/hi")
    public String hi(){
        return feignClient.sayHi();
    }

    @GetMapping("/haha")
    public String haha(){
        return feignClient.sayHaha();
    }


}
