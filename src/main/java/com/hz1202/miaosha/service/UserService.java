package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.UserDao;
import com.hz1202.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 17:10 2018/5/2
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Integer id){
        return userDao.selectById(id);
    }
}
