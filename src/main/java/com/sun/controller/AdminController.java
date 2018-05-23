package com.sun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.pojo.Category;
import com.sun.pojo.PictureResult;
import com.sun.pojo.Product;
import com.sun.service.AdminService;
import com.sun.service.PictureService;
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

            PictureResult result=pictureService.uploadPicture(upload);
            /*//创建一个Ftp对象
            FTPClient ftpClient=new FTPClient();
            //创建连接，端口
            ftpClient.connect("120.79.59.251",21);
            //设置用户名，密码
            ftpClient.login("ftpuser","456123z");
            //3、读取本地文件
            FileInputStream inputStream = new FileInputStream(new File("D:\\testUpload\\1.jpg"));
            //4、上传文件
            //1）指定上传目录
            ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
            //2）指定文件类型
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //第一个参数：文件在远程服务器的名称
            //第二个参数：文件流
            ftpClient.storeFile("hello.jpg", inputStream);
            //5、退出登录
            ftpClient.logout();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}