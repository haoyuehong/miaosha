package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 13:44 2018/5/4
 */
@Controller
@RequestMapping("/user")
public class MiaoShaUserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoShaUser> info(MiaoShaUser user){
        return Result.success(user);
    }


}
