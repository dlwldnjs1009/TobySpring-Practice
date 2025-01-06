package com.example.TobySpring;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {
    @RequestMapping("/hello")
    public String home(){
        return"hello";
    }
}
