package com.hz1202.miaosha.dao;

import com.hz1202.miaosha.model.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 12:46 2018/5/4
 */
@Mapper
public interface MiaoShaUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoShaUser getById(@Param("id") Long id);
}
