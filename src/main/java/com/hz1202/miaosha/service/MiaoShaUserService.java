package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.MiaoShaUserDao;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.utils.MD5Util;
import com.hz1202.miaosha.utils.ValidatorUtil;
import com.hz1202.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 12:49 2018/5/4
 */
@Service
public class MiaoShaUserService {

    @Autowired
    private MiaoShaUserDao userDao;

    public MiaoShaUser getById(Long id){
        return userDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo){
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        /*if(StringUtils.isEmpty(password)){
            return CodeMsg.PASSWORD_EMPTY;
        }

        if(StringUtils.isEmpty(mobile)){
            return CodeMsg.MOBILE_EMPTY;
        }
        if(ValidatorUtil.isMobile(mobile)){
            return CodeMsg.MOBILE_ERROR;
        }*/
        //登录
        MiaoShaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            return CodeMsg.MOBILE_NOT_EXIT;
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String formPassToDBPass = MD5Util.formPassToDBPass(password, saltDB);
        if(!formPassToDBPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
