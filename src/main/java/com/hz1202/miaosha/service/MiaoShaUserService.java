package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.MiaoShaUserDao;
import com.hz1202.miaosha.exception.GlobleException;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.redis.MiaoShaUserKey;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.utils.MD5Util;
import com.hz1202.miaosha.utils.UUIDUtil;
import com.hz1202.miaosha.utils.ValidatorUtil;
import com.hz1202.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 12:49 2018/5/4
 */
@Service
public class MiaoShaUserService {
    public static final String COOKIE_TOKEN_NAME = "token";


    @Autowired
    private MiaoShaUserDao userDao;
    @Autowired
    private RedisService redisService;



    public MiaoShaUser getById(Long id){
        return userDao.getById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
        if(loginVo == null){
            throw new GlobleException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //登录
        MiaoShaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIT);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String formPassToDBPass = MD5Util.formPassToDBPass(password, saltDB);
        if(!formPassToDBPass.equals(dbPass)){
            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
        }
        //生成token 将用户信息存入redis
        String token = UUIDUtil.getUUID();
        addCookie(token,user,response);
        return true;
    }

    public MiaoShaUser getByToken(String token,HttpServletResponse response) {
       if(StringUtils.isEmpty(token)){
           return null;
       }
       MiaoShaUser user = redisService.get(MiaoShaUserKey.userToken,token,MiaoShaUser.class);
       if(user != null){
           addCookie(token,user,response);
       }
       return user;
    }

    private void addCookie(String token,MiaoShaUser user,HttpServletResponse response){
        redisService.set(MiaoShaUserKey.userToken,token,user);
        //生成cookie
        Cookie cookie = new Cookie(COOKIE_TOKEN_NAME,token);
        cookie.setMaxAge(MiaoShaUserKey.userToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
