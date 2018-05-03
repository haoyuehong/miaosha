package com.hz1202.miaosha.dao;

import com.hz1202.miaosha.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 17:03 2018/5/2
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") Integer id);

    @Insert("Insert into user (username,password) values(#{user.username},#{user.password})")
    void save(@Param("user") User user);
}
