package com.hz1202.miaosha.vo;

import com.hz1202.miaosha.model.MiaoShaUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {

    private GoodsVo goodsVo;
    private int miaoshaStatus ;
    private int remianSeconds ;
    private MiaoShaUser miaoShaUser;
}
