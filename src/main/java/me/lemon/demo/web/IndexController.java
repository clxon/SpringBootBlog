package me.lemon.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index( HttpSession session ){ 
        String name = (String)session.getAttribute("username");
        System.out.println(name);
        return "index";
    }
}