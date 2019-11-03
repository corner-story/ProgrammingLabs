package com.se.device.entity;

import com.se.device.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


/*
    测试User实体
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    public UserService userService;

    @Test
    public void testUser(){
        System.out.println("test user!");
        userService.insertUser("wangyi", "123");
        List<User> users = userService.findAll();
        users.forEach(user ->{
            System.out.println(user);
        });
    }
}
