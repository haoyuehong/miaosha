package com.hz1202.miaosha.dao;

import com.hz1202.miaosha.model.Goods;
import com.hz1202.miaosha.model.MiaoShaGoods;
import com.hz1202.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 10:56 2018/5/8
 */
@Mapper
public interface GoodsDao {


    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();
    @Select("select  g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date   from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsVo findById(@Param("id") long id);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoShaGoods  miaoShaGoods);
}
