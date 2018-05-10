package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.GoodsDao;
import com.hz1202.miaosha.model.Goods;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
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

    @Transactional
    public OrderInfo miaoSha(MiaoShaUser user, GoodsVo goodsVo) {

        goodsService.reduceStock(goodsVo);

        return orderService.creatOrder(user,goodsVo);
    }
}
