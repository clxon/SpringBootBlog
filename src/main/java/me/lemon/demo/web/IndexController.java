package me.lemon.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(
        @CookieValue(value = "username",required = false) String name ){ 
        System.out.println("AAAAAAAAAAAAAAAAAAA " + name);
        return "index";
    }
}