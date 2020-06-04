package me.lemon.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import me.lemon.demo.bean.User;

@Component
public class UserService {
    @Autowired
    private JdbcTemplate template;

    public List<User> queryForUsers(){
        return template.query("SELECT * FROM users", 
            new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> selectById(int id) {
        return template.query("SELECT * FROM users WHERE id=?", 
            new Object[]{id}, new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> selectByName(String name) {
        return template.query("SELECT * FROM users WHERE name=?", 
            new Object[]{name}, new BeanPropertyRowMapper<User>(User.class));
    }

    public int insertUser(User user) {
        String sql = "INSERT INTO users (name,password)VALUES(?,?)";
        return template.update(sql, user.getName(), user.getPassword());
    }

    public int deleteByName(String name) {
        String sql = "DELETE FROM users WHERE name=?";
        return template.update(sql, name);
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        return template.update(sql, id);
    }
}