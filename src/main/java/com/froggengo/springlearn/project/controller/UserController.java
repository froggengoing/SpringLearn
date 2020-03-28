package com.froggengo.springlearn.project.controller;

import com.froggengo.springlearn.project.domain.User;
import com.froggengo.springlearn.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate srt;

    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public List<User> userList(){

        List<User> users = userMapper.selectUserList();
/*        StringBuilder sb=new StringBuilder();
        users.forEach(n->{
            sb.append(n.toString());
            sb.append("/n");
        });*/
        users.forEach(n->srt.opsForValue().set(n.getName(),n.toString()));

        return users;
    }
}
