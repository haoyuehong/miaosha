package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.MiaoShaUserService;
import com.hz1202.miaosha.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private MiaoShaUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        CodeMsg codeMsg = userService.login(loginVo);
        if(codeMsg.getCode() == 0){
            return Result.success(codeMsg);
        }else{
            return Result.error(codeMsg);
        }

    }
}
