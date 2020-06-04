package me.lemon.demo.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.lemon.demo.bean.User;
import me.lemon.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/query")
    public String queryUser(Model m,
        @RequestParam(value = "id",required = false) Integer id,
        @RequestParam(value = "name",required = false) String name) {
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

    @GetMapping("/add")
    public String addUser(Model m) {
        m.addAttribute("message", "");
        return "add";
    }

    @PostMapping("/add")
    public String registerUser(Model m,
        @RequestParam("name") String name,
        @RequestParam("password") String password) {
        boolean isRegisterOk = true;
        List<User> users = userService.queryForUsers();
        for(User u : users) {
            if (name.equals(u.getName())) {
                isRegisterOk = false;
            }
        }
        if (isRegisterOk) {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            userService.insertUser(user);
            m.addAttribute("message", "注册成功！");
        } else {
            m.addAttribute("message", "用户名重复！");
        }
        return "add";
    }

    @GetMapping("/del")
    public String delUserGet(Model m) {
        m.addAttribute("message", "");
        return "del";
    }

    @PostMapping("/del")
    public String delUserPost(Model m,
        @RequestParam("name") String name,
        @RequestParam("password") String password) {
        boolean canDel = false;
        List<User> users = userService.queryForUsers();
        for (User u : users) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                canDel = true;
            }
        }
        if (canDel) {
            userService.deleteByName(name);
            m.addAttribute("message", "删除用户成功！");
        } else {
            m.addAttribute("message", "删除用户失败！");
        }
        return "del";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model m,
        HttpServletResponse resp,
        @RequestParam("name") String name,
        @RequestParam("password") String password) {
        List<User> users = userService.queryForUsers();
        for (User u : users) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                Cookie cookie = new Cookie("username", name);
                resp.addCookie(cookie);
                return "redirect:/";
            }
        }
        m.addAttribute("message", "登录失败！");
        return "_error";
    }
}