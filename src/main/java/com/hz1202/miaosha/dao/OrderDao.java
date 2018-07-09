package com.hz1202.miaosha.dao;

import com.hz1202.miaosha.model.MiaoShaOrder;
import com.hz1202.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.*;

import javax.annotation.security.PermitAll;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 11:28 2018/5/10
 */
@Mapper
public interface OrderDao {

    @Select("SELECT * FROM miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    MiaoShaOrder getMiaoShaOrderByUsreIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id,goods_id," +
            "delivery_addr_id,goods_name,goods_count,goods_price," +
            "order_channel,status,create_date) values(#{userId},#{goodsId}," +
            "#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel}," +
            "#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    Long insertInfo(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id,order_id,goods_id) values(#{userId},#{orderId},#{goodsId})")
    void insertMiaoShaOrder(MiaoShaOrder miaoShaOrder);

    @Select("Select * from order_info where id = #{orderId}")
    OrderInfo findById(@Param("orderId") long orderId);
}
