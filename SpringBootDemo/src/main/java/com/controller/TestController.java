package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.redis.RedisService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisService redisService;

    //获取缓存数据  根据key值
    @ResponseBody
    @RequestMapping("/get")
    public Object get(@RequestParam String key,HttpServletResponse response) {
    	return "\\"+redisService.get(key)+"\\"+"";
    }
    
    //保存  key - value
    @ResponseBody
    @RequestMapping(value="/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return "success";
    }
    
}