package com.sun.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/3 14:01
 * @Version 1.0
 **/
@Data
public class User {
    private int uid;
    private String username;
    private String password;
    private String name;
    private String email;
    private String telephone;
    private Date birthday;
    private String sex;
    private int state;
    private String code;
}
