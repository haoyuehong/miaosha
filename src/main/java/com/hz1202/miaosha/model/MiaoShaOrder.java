package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 10:53 2018/5/8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaOrder {
    private Long id;
    private Long goodsId;
    private Long userId;
    private Long orderId;
}
