package com.sun.controller;

import com.sun.pojo.User;
import com.sun.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/4/5 14:19
 * @Version 1.0
 **/
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    //git测试昆仑决
    //用户登录
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();

            //获得输入的用户名和密码
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //对密码进行加密
            //password = MD5Utils.md5(password);

            //将用户名和密码传递给service层
            User user = null;
            user = loginService.login(username,password);

            //判断用户是否登录成功 user是否是null
            if(user!=null){
                //登录成功
                //***************判断用户是否勾选了自动登录*****************
                String autoLogin = request.getParameter("autoLogin");
                if("true".equals(autoLogin)){
                    //要自动登录
                    //创建存储用户名的cookie
                    Cookie cookie_username = new Cookie("cookie_username",user.getUsername());
                    cookie_username.setMaxAge(10*60);
                    //创建存储密码的cookie
                    Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
                    cookie_password.setMaxAge(10*60);

                    response.addCookie(cookie_username);
                    response.addCookie(cookie_password);

                }

                //***************************************************
                //将user对象存到session中
                session.setAttribute("user", user);

                //重定向到首页
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }else{
                request.setAttribute("loginError", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response,HttpServletRequest request){
        try{
            HttpSession session=request.getSession();
            session.removeAttribute("user");

            //将存储在客户端的cookie删除
            Cookie cookie_username=new Cookie("cookie_username","");
            cookie_username.setMaxAge(0);
            Cookie cookie_password=new Cookie("cookie_password","");
            cookie_password.setMaxAge(0);
            response.addCookie(cookie_password);
            response.addCookie(cookie_username);
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
