package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
import com.hz1202.miaosha.redis.GoodsKey;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaUserService;
import com.hz1202.miaosha.service.OrderService;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.GoodsDetailVo;
import com.hz1202.miaosha.vo.GoodsVo;
import com.hz1202.miaosha.vo.OrderDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/oder")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/detail")
    @ResponseBody
    public Result<OrderDetailVo> toDetail(long orderId, MiaoShaUser user){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.findById(orderId);
        if(orderInfo == null){
            return Result.error(CodeMsg.ORDER_NULL);
        }
        Long goodsIds = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.findById(goodsIds);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoodsVo(goodsVo);
        orderDetailVo.setOrderInfo(orderInfo);
        return Result.success(orderDetailVo);
    }
}
