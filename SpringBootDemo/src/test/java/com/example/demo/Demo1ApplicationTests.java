package com.example.demo;

import com.dao.user.UserDao;
import com.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void find() {
        User user = userDao.findById();
        System.out.println(user.getUsername());
    }

    @Test
    public void add() {
        User user = new User();
        user.setUsername("wangwu");
        user.setPassword("123321");
        userDao.addUser(user);
        System.out.println("==添加成功=="+user);
    }
}
