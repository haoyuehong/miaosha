package com.hz1202.miaosha.controller;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.rabbitmq.MQSender;
import com.hz1202.miaosha.redis.GoodsKey;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaService;
import com.hz1202.miaosha.service.OrderService;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.GoodsVo;
import com.hz1202.miaosha.vo.MiaoShaMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 11:04 2018/5/10
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;

    /**
     * 系统初始化时调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        if(goodsVos == null){
            return;
        }
        for(GoodsVo goodsVo : goodsVos){
            redisService.set(GoodsKey.getMiaoShaGoodsStock,""+goodsVo.getId(),goodsVo.getStockCount());
        }
    }

    /**
     * 思路:系统初始化时就将商品库存数量加载到redis中,收到秒杀请求时进行预减库存,如果减完后库存小于零则返回失败,大于零则入队
     * @param user
     * @param model
     * @param goodsId
     * @return
     */
    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<Integer> doMiaoSha(MiaoShaUser user, Model model, @RequestParam("goodsId")long goodsId){
        //model.addAttribute("user",user);
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
            //return "login";
        }
        //预减库存(返回减后的数据)
        Long stock = redisService.decr(GoodsKey.getMiaoShaGoodsStock, "" + goodsId);
        if(stock < 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀过商品
        MiaoShaOrder miaoShaOrder = orderService.getMiaoShaOrderByUsreIdAndGoodsId(user.getId(),goodsId);
        if(miaoShaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入队
        MiaoShaMessage miaoShaMessage = new MiaoShaMessage(user,goodsId);
        mqSender.sendMiaoShaMessage(miaoShaMessage);

        return Result.success(0);
    }

    @RequestMapping("/result")
    @ResponseBody
    public Result miaoShaResult(MiaoShaUser miaoShaUser ,Long goodsId){
        if(miaoShaUser == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Long result = miaoShaService.getMiaoShaResult(miaoShaUser.getId(),goodsId);
        return Result.success(result);
    }


}
