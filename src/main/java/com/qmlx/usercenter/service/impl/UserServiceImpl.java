package com.qmlx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmlx.usercenter.constant.UserConstant;
import com.qmlx.usercenter.model.domain.User;
import com.qmlx.usercenter.service.UserService;
import com.qmlx.usercenter.mapper.UserMapper;
import com.qmlx.usercenter.utils.CheckPasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
* @author QMLX9999
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-03 20:51:31
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    final String SALT="qmlx"; //一个签名，使得加密更加混乱

    //存储用户session中的键
    //public static final String USER_LOGIN_STATE="userloginstate";

    @Autowired
    private UserMapper userMapper;
    /**
     * 用户注册功能
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 用户校验码，和密码
     * @return 注册失败返回-1
     */
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //进行用户登录逻辑校验
        //1,输入不为空
        if(userAccount==null || userPassword==null || checkPassword==null){
            return -1;//TODO 后续修改为自定义异常类
        }
        //2,校验长度
        if(userAccount.length()<4 ||userPassword.length()<8 ||checkPassword.length()<8){
            return -1;
        }
        //3,密码不包含特殊字符
        if(!CheckPasswordUtils.checkPassword(userPassword)){
            return -1;
        }
        //4，密码和校验密码不能相同
        if(!userPassword.equals(checkPassword)){
            return -1;
        }
        //5,查询数据库，账号相同不能注册
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserAccount,userAccount);
        long res = this.count(qw);
        if(res>0){
            return -1;
        }

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes()));
        user.setCreateTime(new Date());
        boolean save = this.save(user);
        if(!save){
            return -1;
        }

        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword, HttpServletRequest request) {
        //1,输入不为空
        if(userAccount==null || userPassword==null ){
            return null;
        }
        //2,校验长度
        if(userAccount.length()<4 ||userPassword.length()<8 ){
            return null;
        }
        //3,密码不包含特殊字符
        if(!CheckPasswordUtils.checkPassword(userPassword)){
            return null;
        }
        //4,将密码加密，后续便于校验
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount,userAccount);
        lqw.eq(User::getUserPassword,md5Password);
        User user = userMapper.selectOne(lqw);
        //用户不存在
        if(user==null){
            log.info("user login failed！");
            return null;
        }

        //用户脱敏
        User retUser = new User();
        retUser.setId(user.getId());
        retUser.setUsername(user.getUsername());
        retUser.setUserAccount(user.getUserAccount());
        retUser.setAvatarUrl(user.getAvatarUrl());
        retUser.setGender(user.getGender());
        retUser.setUserPassword("");
        retUser.setPhone(user.getPhone());
        retUser.setEmail(user.getEmail());
        retUser.setUserStatus(user.getUserStatus());
        retUser.setCreateTime(user.getCreateTime());
        retUser.setUserRole(user.getUserRole());

        //用户存在，登陆成功，记录用户的登录态(session)
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE,user);

        return retUser;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        //用户登录的时候从session中存储了用户的登录信息，当用户注销的时候从session中移出用户信息即可
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
    }


}




