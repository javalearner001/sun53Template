package com.sun.controller;

import com.sun.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/3 13:59
 * @Version 1.0
 **/
@Controller
public class TestController {
    @RequestMapping(value = "/test")
    @ResponseBody
    public User test(){
        User user=new User();
        //user.setUid("1");
        user.setName("sunkai");
        user.setSex("24");
        return user;
    }
}
