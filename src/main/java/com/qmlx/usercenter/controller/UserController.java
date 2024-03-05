package com.qmlx.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qmlx.usercenter.common.BaseResponce;
import com.qmlx.usercenter.common.ErrorCode;
import com.qmlx.usercenter.common.ResultUtils;
import com.qmlx.usercenter.constant.UserConstant;
import com.qmlx.usercenter.exception.BusinessException;
import com.qmlx.usercenter.model.domain.User;
import com.qmlx.usercenter.model.domain.request.UserLoginRequest;
import com.qmlx.usercenter.model.domain.request.UserRegisterRequest;
import com.qmlx.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户中心接口
 * @author QMLX9999
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public BaseResponce<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        long id=userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponce<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                          HttpServletRequest request){
        if(userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        User user = userService.dologin(userAccount, userPassword,request);
        return ResultUtils.success(user);
    }

    /**
     * 查询用户皆苦
     * @param username 用户名
     * @return 封装用户的集合
     */
    @GetMapping("/search")
    public BaseResponce<List<User>> searchUsers(String username, HttpServletRequest request){
        //鉴别权限,仅管理员可以擦查询用户,普通用户不可以
        //从sesion中去除用户信息
//       if(!isAdmin(request)){
//           throw new BusinessException(ErrorCode.NULL_ERROR);
//       }

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        //lqw.like(User::getUsername,username);
        List<User> userList = userService.list(lqw);
        return ResultUtils.success(userList);
    }

    @GetMapping("/delete")
    public BaseResponce<Boolean> deleteUser(Long id,HttpServletRequest request){
        //鉴别用户权限，自己在练习一遍，后续将其封装为一个函数调用
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user =(User) userObj;
        if(user==null || user.getUserRole()==UserConstant.DEFAULT_ROLE){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if(id<=0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean byId = userService.removeById(id);
        return ResultUtils.success(byId);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponce<Integer> logout(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        userService.userLogout(request);
        return ResultUtils.success(1);
    }

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponce<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user=(User) userObj;
        if(user==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long id = user.getId();
        User userResult = userService.getById(id);
        userResult.setUserPassword("");
        return ResultUtils.success(userResult);

    }
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }



}
