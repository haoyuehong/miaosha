package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.redis.MiaoShaUserKey;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaUserService;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 16:36 2018/5/7
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private MiaoShaUserService userService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 1、在请求参数中获取token
     * 2、如果token为空，则返回登录页面
     * 3、根据获取到的token在Redis中获取用户信息
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String toListHtml(Model model,MiaoShaUser user
                             /*@CookieValue(value = MiaoShaUserService.COOKIE_TOKEN_NAME,required = false)String cookieToken,
                             @RequestParam(value = MiaoShaUserService.COOKIE_TOKEN_NAME,required = false)String paramCookieToken*/){
        /*if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramCookieToken)){
            return "login";
        }
        String token = StringUtils.isEmpty(paramCookieToken)?cookieToken:paramCookieToken;
        MiaoShaUser user = userService.getByToken(token,response);*/
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{id}")
    public String toDetail(@PathVariable("id")long id,Model model,MiaoShaUser user){
        GoodsVo  goodsVo = goodsService.findById(id);
        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus ;
        int remianSeconds ;
        if(now < startTime){
            miaoshaStatus = 0;
            remianSeconds = (int) (startTime - now)/1000;
        }else if(now > endTime){
            miaoshaStatus = 2;
            remianSeconds = -1;
        }else{
            miaoshaStatus = 1;
            remianSeconds = 0;
        }

        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remianSeconds",remianSeconds);
        model.addAttribute("goodsVo",goodsVo);
        model.addAttribute("user",user);
        return "goods_detail";


    }
}
