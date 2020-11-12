package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Value("${name}")
    private String name;
    @Value("${url}")
    private String url;

    @RequestMapping("/hello")
    @ResponseBody
    public String say(){
        return "Hello SpringBoot!"+name+url;
    }
}
