package com.hz1202.miaosha.vo;

import com.hz1202.miaosha.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 8:18 2018/5/4
 */
@Setter
@Getter
@ToString
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    private String password;
}
