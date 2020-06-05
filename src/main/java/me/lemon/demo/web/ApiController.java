package me.lemon.demo.web;

import me.lemon.demo.bean.User;
import me.lemon.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserService userService;


    @GetMapping("/query")
    public String queryUser(Model m,
                            @RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "name", required = false) String name) {
        if (id == null && name == null) {
            List<User> users = userService.queryForUsers();
            m.addAttribute("users", users);
        } else if (id != null) {
            List<User> users = userService.selectById(id);
            m.addAttribute("users", users);
        } else if (name != null) {
            List<User> users = userService.selectByName(name);
            m.addAttribute("users", users);
        }
        return "query";
    }
}
