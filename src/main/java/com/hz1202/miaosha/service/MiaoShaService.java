package com.hz1202.miaosha.service;

import com.hz1202.miaosha.model.MiaoShaGoods;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
import com.hz1202.miaosha.redis.MiaoShaKey;
import com.hz1202.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 11:34 2018/5/10
 */
@Service
public class MiaoShaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;


    @Transactional
    public OrderInfo miaoSha(MiaoShaUser user, GoodsVo goodsVo) {

        Boolean success = goodsService.reduceStock(goodsVo);

        if(success){
            return orderService.creatOrder(user,goodsVo);
        }
        setGoodsOver(goodsVo.getId());
        return null;
    }

    public long getMiaoShaResult(Long id, Long goodsId) {
        MiaoShaOrder miaoShaOrder = orderService.getMiaoShaOrderByUsreIdAndGoodsId(id, goodsId);
        if(miaoShaOrder != null){
            return miaoShaOrder.getId();
        }else{
            boolean flage = getGoodsOver(goodsId);
            if(flage){
                return -1;
            }else{
                return 0;
            }
        }
    }

    private Boolean getGoodsOver(Long goodsId) {

        return redisService.exists(MiaoShaKey.isGoodsOver,""+goodsId);
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoShaKey.isGoodsOver,""+goodsId,true);
    }
}
