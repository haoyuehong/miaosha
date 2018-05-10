package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:50 2018/5/8
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaGoods {
    private Long id;
    private Long goodsId;
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
