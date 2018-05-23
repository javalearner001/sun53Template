package com.sun.controller;

import com.sun.pojo.PictureResult;
import com.sun.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName PictureController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/23 15:05
 * @Version 1.0
 **/
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PictureResult upload(MultipartFile uploadFile) {
        PictureResult result = pictureService.uploadPicture(uploadFile);
        return result;
    }

}

