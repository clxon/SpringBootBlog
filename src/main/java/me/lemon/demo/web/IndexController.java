package me.lemon.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model m, HttpSession session){
        String name = (String)session.getAttribute("username");
        if (name != null) {
            m.addAttribute("username",name);
        }
        return "index";
    }
}