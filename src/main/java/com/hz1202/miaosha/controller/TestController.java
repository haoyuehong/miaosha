package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.User;
import com.hz1202.miaosha.rabbitmq.MQSender;
import com.hz1202.miaosha.redis.UserKey;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;

    @RequestMapping(value = "/hello")
    public String test1(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }


    @RequestMapping("/user")
    @ResponseBody
    public Result getById(Integer id){
        return Result.success(userService.getById(id));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(User user){
        user.setCreateTime(new Date());
        userService.save(user);
        return Result.success("新增成功");
    }

    @RequestMapping("/save1")
    @ResponseBody
    @Transactional
    public Result save1(){
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        userService.save(user);
        int i = 1/0;
        return Result.success("新增成功");
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result redisGet(){
        User key1 = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(key1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result redisSet(){
        return Result.success(redisService.set(UserKey.getById,"id",userService.getById(6)));
    }


}
