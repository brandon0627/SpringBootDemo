package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.User;
import com.services.redis.RedisService;
import com.services.user.UserService;

import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    //添加用户
    @ResponseBody
    @RequestMapping(value="/save")
    public String addUser(@RequestParam String username, @RequestParam String password) { 
    	System.out.println("===================");
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        System.out.println(user);
        userService.addUser(user);
        redisService.set(user.getUsername(), user);
//        redisService.set("a",user,3,TimeUnit.DAYS);
        return "Saved";
    }
    
    //从redis获取某个用户
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody User getRedis(@RequestParam String key) {
        return (User)redisService.get(key);
    }
    
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody String find() {
        return userService.find();
    }

    //获取所有用户
//  @RequestMapping(value = "/getusers", method = RequestMethod.GET)
//  public @ResponseBody Page<User> list(Model model, Pageable pageable){
//      return userService.findAll(pageable); 
//  }

}