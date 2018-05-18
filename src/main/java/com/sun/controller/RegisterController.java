package com.sun.controller;

import com.sun.pojo.BaseResponse;
import com.sun.pojo.User;
import com.sun.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @ClassName RegisterController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/4/5 14:21
 * @Version 1.0
 **/
@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String insertInToUser(HttpServletRequest request){
        BaseResponse baseResponse=new BaseResponse();
        User user=new User();
        try {
        //给user对象赋值
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setSex(request.getParameter("sex"));
        user.setName(request.getParameter("name"));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(sf.parse(request.getParameter("birthday")));
        user.setEmail(request.getParameter("email"));
        user.setTelephone(request.getParameter("telephone"));
        user.setCode(request.getParameter("code"));
        Boolean flag=registerService.insertInToUser(user);
        if (flag){
            return "registerSuccess";
        }
        baseResponse.setCode(400);
        baseResponse.setMessage("注册失败");
        } catch (ParseException e) {
            e.printStackTrace();
            baseResponse.setCode(500);
            baseResponse.setMessage("注册异常");
            return "registerFail";
        }
        System.out.println(baseResponse);
        return "registerFail";
    }

    @RequestMapping(value = "/checkUsername",method = RequestMethod.POST)
    @ResponseBody
    public String getUserByUserName(HttpServletRequest request){
        String username=request.getParameter("username");
        Boolean flag=registerService.checkUsername(username);
        String json="{\"isExist\":"+flag+"}";
        return json;
    }

}
