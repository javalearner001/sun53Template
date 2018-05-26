package com.sun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.pojo.*;
import com.sun.service.AdminService;
import com.sun.service.PictureService;
import com.sun.service.ProductService;
import com.sun.utils.FtpUtil;
import com.sun.utils.IDUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
           PictureResult pictureResult=pictureService.uploadPicture(upload);
            //创建一个Ftp对象
//            FTPClient ftpClient=new FTPClient();
//            System.out.println(FTP_ADDRESS);
//            //创建连接，端口
//            ftpClient.connect(FTP_ADDRESS,FTP_PORT);
//            //设置用户名，密码
//            ftpClient.login(FTP_USERNAME,FTP_PASSWORD);
//            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            //取原始文件名

            //4、上传文件
            //1）指定上传目录
//            ftpClient.enterLocalActiveMode();
//            ftpClient.changeWorkingDirectory(FTP_BASE_PATH);
//            //2）指定文件类型
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            //第一个参数：文件在远程服务器的名称
//            //第二个参数：文件流
//            ftpClient.storeFile("hello2.jpg", inputStream);
//            inputStream.close();
//            //5、退出登录
//            ftpClient.logout();
            productService.addProduct(request,pictureResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/orderList")
    public void getOrderList(HttpServletResponse response, HttpServletRequest request){
        try {
            //获得所以订单 List<order>
            List<Order> orderList=adminService.getOrderList();

            request.setAttribute("orderList",orderList);
            request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/getOrderInfo")
    public void getOrderInfo(HttpServletResponse response, HttpServletRequest request){
        try{
            //获得oid
            String oid=request.getParameter("oid");
            List<Map<String,Object>> mapList=adminService.getOrderInfo(oid);
            ObjectMapper mapper=new ObjectMapper();
            String mapListJson=mapper.writeValueAsString(mapList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(mapListJson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 后台显示所有商品
     */
    @RequestMapping("/getProductList")
    public void getProductList(HttpServletResponse response, HttpServletRequest request){
        try {
            List<Product> productList=adminService.getProductList();

            request.setAttribute("productList",productList);
            request.getRequestDispatcher("/admin/product/list.jsp").forward(request,response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("/deleteProduct")
    public void deleteProduct(HttpServletResponse response, HttpServletRequest request){
        try {
            //删除商品
            String pid=request.getParameter("pid");
            String result=adminService.deleteProduct(pid);

            request.setAttribute("deleteResult",result);
            request.getRequestDispatcher("/admin/product/deleteResult.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping("/getCategoryList")
    public void getCategoryList(HttpServletResponse response, HttpServletRequest request){
        try {
            //获得商品类别列表
            List<Category> categoryList=productService.findAllCategory();
            request.setAttribute("categoryList",categoryList);
            request.getRequestDispatcher("/admin/category/list.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("/deleteCategory")
    public void deleteCategory(HttpServletResponse response, HttpServletRequest request){
        try {
            //删除商品
            String cname=request.getParameter("cname");
            String result=adminService.deleteCategory(cname);

            request.setAttribute("deleteResult",result);
            request.getRequestDispatcher("/admin/product/deleteResult.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}