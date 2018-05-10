package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:46 2018/5/8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
