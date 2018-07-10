package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.OrderDao;
import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.model.OrderInfo;
import com.hz1202.miaosha.redis.OrderKey;
import com.hz1202.miaosha.vo.GoodsVo;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 11:24 2018/5/10
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    public MiaoShaOrder getMiaoShaOrderByUsreIdAndGoodsId(Long userId, long goodsId) {

        //return orderDao.getMiaoShaOrderByUsreIdAndGoodsId(userId,goodsId);
        return redisService.get(OrderKey.getMiaoShaOrderByUidGId,""+userId+"_"+goodsId,MiaoShaOrder.class);
    }

    @Transactional
    public OrderInfo creatOrder(MiaoShaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsCount(1L);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setDeliveryAddrId(1L);
        orderInfo.setStatus(0);
        orderInfo.setOrderChannel(1);
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderDao.insertInfo(orderInfo);
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setGoodsId(goodsVo.getId());
        miaoShaOrder.setOrderId(orderInfo.getId());
        miaoShaOrder.setUserId(user.getId());
        orderDao.insertMiaoShaOrder(miaoShaOrder);
        redisService.set(OrderKey.getMiaoShaOrderByUidGId,""+user.getId()+"_"+goodsVo.getId(),miaoShaOrder);
        return orderInfo;
    }

    public OrderInfo findById(long orderId) {

        return orderDao.findById(orderId);
    }
}
