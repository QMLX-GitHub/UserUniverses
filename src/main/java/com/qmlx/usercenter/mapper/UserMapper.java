package com.qmlx.usercenter.mapper;

import com.qmlx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author QMLX9999
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-03-03 20:51:31
* @Entity model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




