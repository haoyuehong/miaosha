package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:55 2018/5/8
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

  private Long id;
  private Long userId;
  private Long goodsId;
  private Long deliveryAddrId;
  private String goodsName;
  private Long goodsCount;
  private Double goodsPrice;
  private Integer orderChannel;
  private int status;
  private Date createDate;
  private Date payDate;
}
