package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class FreemanController {
    @RequestMapping("/ftl")
    public String index(ModelMap map) {
        map.addAttribute("host","https://github.com/Inverseli/");
        return "ftl/hello";
    }
}
