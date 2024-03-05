package com.qmlx.usercenter.service;

import com.qmlx.usercenter.utils.CheckPasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class ElseTest {
    @Test
    void testpassword(){
        System.out.println(CheckPasswordUtils.checkPassword("68965IIJdeoi12"));
        String s = DigestUtils.md5DigestAsHex("asdafe23".getBytes());
        System.out.println(s);
    }
}
