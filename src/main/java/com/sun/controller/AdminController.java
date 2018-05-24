package com.sun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.pojo.Category;
import com.sun.pojo.PictureResult;
import com.sun.pojo.Product;
import com.sun.service.AdminService;
import com.sun.service.PictureService;
import com.sun.service.ProductService;
import com.sun.utils.IDUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ProductService productService;

    /**
     * 后台获取商品类别
     * @param request
     * @param response
     */
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

    /**
     * 后台添加商品
     * @param response
     * @param request
     */
    @RequestMapping("/addProduct")
    public void addProduct(MultipartFile upload, HttpServletResponse response, HttpServletRequest request){
        try {
            InputStream inputStream=upload.getInputStream();
            String pname=request.getParameter("pname");
            String is_hot=request.getParameter("is_hot");
            String market_price=request.getParameter("market_price");
            String shop_price=request.getParameter("shop_price");
            String cid=request.getParameter("cid");
            String pdesc=request.getParameter("pdesc");
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=upload.getOriginalFilename();// 文件原名称
            // 自定义的文件名称
            String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
            // 设置存放图片文件的路径
            String realPath="E:\\test59\\sun59Template\\src\\main\\webapp\\products\\getUpload\\";
//            E:\test59\sun59Template\src\main\webapp\products\getUpload
            path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
            System.out.println("存放图片文件的路径:"+path);
            // 转存文件到指定的路径
            upload.transferTo(new File(path));
            //存到数据库的图片路径
            String DBPath=null;

            //将商品添加到商品表中
            Product product=new Product();
            product.setPid(IDUtils.genImageName());
            product.setShop_price(Double.parseDouble(shop_price));
            product.setPname(pname);
            product.setIs_hot(Integer.parseInt(is_hot));
            product.setPdesc(pdesc);
            product.setMarket_price(Double.parseDouble(market_price));
            Category category=new Category();
            category.setCid(cid);
            product.setCategory(category);
            product.setPimage(path);
            productService.addProduct(product);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}