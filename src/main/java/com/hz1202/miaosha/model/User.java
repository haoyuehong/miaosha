package com.hz1202.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 17:03 2018/5/2
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Date createTime;
}
