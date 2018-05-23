package com.sun.service;

import com.sun.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    PictureResult uploadPicture(MultipartFile uploadFile);
}
