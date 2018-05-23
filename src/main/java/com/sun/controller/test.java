package com.sun.controller;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName test
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/23 14:29
 * @Version 1.0
 **/
public class test {

    public static void main(String[] args) {
        try{
            //创建一个Ftp对象
            FTPClient ftpClient=new FTPClient();
            //创建连接，端口
            ftpClient.connect("120.79.59.251");
            //设置用户名，密码
            ftpClient.login("ftpuser","456123z");
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器

            //3、读取本地文件
            FileInputStream inputStream = new FileInputStream(new File("D:\\testUpload\\1.jpg"));
            //4、上传文件
            //1）指定上传目录
//            ftpClient.enterLocalActiveMode();
            ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
            //2）指定文件类型
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //第一个参数：文件在远程服务器的名称
            //第二个参数：文件流
            ftpClient.storeFile("hello.jpg", inputStream);
            inputStream.close();
            //5、退出登录
            ftpClient.logout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
