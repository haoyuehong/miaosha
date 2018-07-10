package com.hz1202.miaosha.rabbitmq;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import com.hz1202.miaosha.service.GoodsService;
import com.hz1202.miaosha.service.MiaoShaService;
import com.hz1202.miaosha.service.OrderService;
import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.GoodsVo;
import com.hz1202.miaosha.vo.MiaoShaMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void miaoshaReceiver(String message){
        MiaoShaMessage miaoShaMessage = RedisService.StringToBean(message, MiaoShaMessage.class);
        MiaoShaUser miaoShaUser = miaoShaMessage.getMiaoShaUser();
        Long goodsId = miaoShaMessage.getGoodsId();
        GoodsVo goodsVo = goodsService.findById(goodsId);
        Integer stock = goodsVo.getStockCount();
        if(stock <= 0){
            return;
        }
        //判断是否已经秒杀过商品
        MiaoShaOrder miaoShaOrder = orderService.getMiaoShaOrderByUsreIdAndGoodsId(miaoShaUser.getId(),goodsId);
        if(miaoShaOrder != null){
            return;
        }
        //减库存
        miaoShaService.miaoSha(miaoShaUser,goodsVo);
    }
}
