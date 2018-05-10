package com.hz1202.miaosha.vo;

import com.hz1202.miaosha.model.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 10:57 2018/5/8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
