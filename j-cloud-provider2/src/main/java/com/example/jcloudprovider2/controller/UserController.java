package com.example.jcloudprovider2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author yanglin
 * @Date 2023/4/24 9:54
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/sayHello")
    public String sayhello(){
        return "我是provider2，老板您好！";
    }

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "我是provider2,害！老板！";
    }

    @RequestMapping("/sayHaha")
    public String sayHaha(){
        return "我是provider2,呵呵！老板！";
    }
}
