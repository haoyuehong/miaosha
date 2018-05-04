package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 12:43 2018/5/4
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaUser {
    private Long id;
    private String nickName;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
