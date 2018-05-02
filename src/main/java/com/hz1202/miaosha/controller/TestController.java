package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.User;
import com.hz1202.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 16:48 2018/5/2
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hello")
    public String test1(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }

    @RequestMapping("/user")
    @ResponseBody
    public User getById(Integer id){
        return userService.getById(id);
    }
}
