package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/html")
public class HtmlController {

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "1111");

        return "template/hello";
    }
}