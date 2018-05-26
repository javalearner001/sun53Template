package com.sun.service.Impl;

import com.sun.dao.AdminDao;
import com.sun.dao.ProductDao;
import com.sun.pojo.Category;
import com.sun.pojo.Order;
import com.sun.pojo.PictureResult;
import com.sun.pojo.Product;
import com.sun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Category> findAllCategory() {
        List<Category> categoryList=adminDao.findAllCategory();
        return categoryList;
    }

    @Override
    public List<Order> getOrderList() {
        List<Order> orderList=adminDao.getOrderList();
        return orderList;
    }

    @Override
    public List<Map<String, Object>> getOrderInfo(String oid) {
        List<Map<String, Object>> orderInfoList=productDao.findAllOrderItemByOid(oid);
        return orderInfoList;
    }

    @Override
    public List<Product> getProductList() {
        List<Product> productList=adminDao.getProductList();
        return productList;
    }

    @Override
    public String deleteProduct(String pid) {
        Boolean flag=adminDao.deleteProduct(pid);
        String result=null;
        if (flag){
            result="删除成功";
            return result;
        }
        result="删除失败";
        return result;
    }

    @Override
    public String deleteCategory(String cname) {
        Boolean flag=adminDao.deleteCategory(cname);
        String result=null;
        if (flag){
            result="删除成功";
            return result;
        }
        result="删除失败";
        return result;
    }
}
