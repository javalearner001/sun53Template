package com.sun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.pojo.Category;
import com.sun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName CategoryListController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/9 13:54
 * @Version 1.0
 **/
@Controller
public class CategoryListController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/categoryList",method = RequestMethod.POST)
    @ResponseBody
    public void getCategoryList(HttpServletRequest request, HttpServletResponse response){
        try {
            //准备分类数据
            List<Category> categoryList = productService.findAllCategory();
            ObjectMapper mapper=new ObjectMapper();
            String jsonStr = mapper.writeValueAsString(categoryList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
