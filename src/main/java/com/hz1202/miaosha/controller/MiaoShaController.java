package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaService;
import com.hz1202.miaosha.service.OrderService;
import com.hz1202.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 11:04 2018/5/10
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> doMiaoSha(MiaoShaUser user, Model model, @RequestParam("goodsId")long goodsId){
        //model.addAttribute("user",user);
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
            //return "login";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.findById(goodsId);
        Integer stock = goodsVo.getStockCount();
        if(stock <= 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀过商品
        MiaoShaOrder miaoShaOrder = orderService.getMiaoShaOrderByUsreIdAndGoodsId(user.getId(),goodsId);
        if(miaoShaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存
        OrderInfo orderInfo = miaoShaService.miaoSha(user,goodsVo);
        return Result.success(orderInfo);
    }
}
