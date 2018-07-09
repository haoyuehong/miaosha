package com.hz1202.miaosha.vo;

import com.hz1202.miaosha.model.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;


}
