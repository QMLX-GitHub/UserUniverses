package com.qmlx.usercenter.service;

import com.qmlx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author QMLX9999
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-03 20:51:31
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验码
     * @return 插入成功返回用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request
     * @return 返回脱敏的用户信息
     */
    User dologin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     * @param request 前端请求
     * @return
     */
    void userLogout(HttpServletRequest request);
}
