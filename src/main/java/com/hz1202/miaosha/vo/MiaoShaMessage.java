package com.hz1202.miaosha.vo;

import com.hz1202.miaosha.model.MiaoShaUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaMessage {
    private MiaoShaUser miaoShaUser;

    private Long goodsId;
}
