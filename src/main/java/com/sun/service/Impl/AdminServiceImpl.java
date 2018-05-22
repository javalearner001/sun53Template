package com.sun.service.Impl;

import com.sun.dao.AdminDao;
import com.sun.pojo.Category;
import com.sun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/21 17:42
 * @Version 1.0
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Category> findAllCategory() {
        List<Category> categoryList=adminDao.findAllCategory();
        return categoryList;
    }
}
