package com.sun.controller;

import com.sun.pojo.Product;
import com.sun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/8 10:56
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/index")
    public void getProduct(HttpServletRequest request, HttpServletResponse response){

        try {
            //准备热门商品---List<Product>
            List<Product> hotProductList = productService.findHotProductList();

            //准备最新商品---List<Product>
            List<Product> newProductList = productService.findNewProductList();

            //准备分类数据
//            List<Category> categoryList = productService.findAllCategory();
//            request.setAttribute("categoryList", categoryList);
            request.setAttribute("hotProductList", hotProductList);
            request.setAttribute("newProductList", newProductList);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
