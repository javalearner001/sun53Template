package com.sun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.pojo.Category;
import com.sun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/21 17:21
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/findAllCategory")
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response){
        List<Category> categoryList=adminService.findAllCategory();
        ObjectMapper mapper=new ObjectMapper();
        try {
            String categoryListJson=mapper.writeValueAsString(categoryList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(categoryListJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/addProduct")
    public void addProduct(HttpServletResponse response,HttpServletRequest request){

    }

}
