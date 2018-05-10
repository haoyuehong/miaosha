package com.hz1202.miaosha.service;

import com.hz1202.miaosha.dao.GoodsDao;
import com.hz1202.miaosha.model.Goods;
import com.hz1202.miaosha.model.MiaoShaGoods;
import com.hz1202.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 10:55 2018/5/8
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo findById(long id) {

        return goodsDao.findById(id);
    }

    public void reduceStock(GoodsVo goodsVo) {
        MiaoShaGoods miaoShaGoods = new MiaoShaGoods();
        miaoShaGoods.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(miaoShaGoods);
    }
}
