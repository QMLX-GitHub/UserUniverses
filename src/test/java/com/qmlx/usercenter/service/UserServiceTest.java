package com.qmlx.usercenter.service;

import com.qmlx.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/*
* 用户测试*/
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;
    @Test
    public void testAdder(){
        User user=new User();
        user.setUsername("测试");
        user.setUserAccount("1234");
        user.setAvatarUrl("https://wwads.cn/click/bundle?code=vONSyMvC6rQhcDehEx3oS9rlq9TAIX");
        user.setGender(0);
        user.setUserPassword("123456789");
        user.setPhone("123");
        user.setEmail("345");


        boolean res = userService.save(user);
        System.out.println(user.getId());

    }

    @Test
    void userRegister() {
        String userAccount="ceshi";
        String userPassword="123456";
        String checkPassword="123456";
        //挨个测试每种情况
        userAccount="";
        long res = userService.userRegister(userAccount,userPassword, checkPassword);
        Assertions.assertEquals(-1,res);

        userAccount="wer";
        res = userService.userRegister(userAccount,userPassword, checkPassword);
        Assertions.assertEquals(-1,res);

        userPassword="mima等";
        res = userService.userRegister(userAccount,userPassword, checkPassword);
        Assertions.assertEquals(-1,res);

        userPassword="12345";
        res = userService.userRegister(userAccount,userPassword, checkPassword);
        Assertions.assertEquals(-1,res);

        userAccount="lisi";
        res = userService.userRegister(userAccount,userPassword, checkPassword);
        Assertions.assertEquals(-1,res);


    }
}