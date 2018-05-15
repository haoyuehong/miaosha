package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.MiaoshaApplication;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.redis.GoodsKey;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaUserService;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private ApplicationContext application;

    /**
     * 1、在请求参数中获取token
     * 2、如果token为空，则返回登录页面
     * 3、根据获取到的token在Redis中获取用户信息
     * 4、页面缓存：服务端直接想客户端返回一个html源代码(先从redis中获取，redis中没有再手动渲染，完成后先存入redis，然后在返回给前台)
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list",produces="text/html")
    @ResponseBody
    public String toListHtml(Model model, MiaoShaUser user, HttpServletRequest request,
                             HttpServletResponse response){
        //取缓存
        String str = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(str)){
            return str;
        }
        //缓存中没有，我们先获取商品信息
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        model.addAttribute("user",user);
        //然后进行手动渲染
        SpringWebContext swc = new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),application);
        //goods_list 指的是前端页面模版（即你要将数据返回到哪个页面）
        str = thymeleafViewResolver.getTemplateEngine().process("goods_list", swc);
        //存入redis
        if(!StringUtils.isEmpty(str)){
            redisService.set(GoodsKey.getGoodsList,"",str);
        }
        return str;
    }

    @RequestMapping(value = "/to_detail/{id}",produces = "text/html")
    @ResponseBody
    public String toDetail(@PathVariable("id")long id,Model model,MiaoShaUser user,HttpServletResponse response,HttpServletRequest request){
        //取缓存
        String str = redisService.get(GoodsKey.getGoodsDetail, ""+id , String.class);
        if(!StringUtils.isEmpty(str)){
            return str;
        }
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

        SpringWebContext swc = new SpringWebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap(),application);
        str = thymeleafViewResolver.getTemplateEngine().process("goods_detail",swc);
        if(!StringUtils.isEmpty(str)){
            redisService.set(GoodsKey.getGoodsDetail,""+id,str);
        }
        return str;
    }
}
