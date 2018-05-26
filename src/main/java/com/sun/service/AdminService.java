package com.sun.service;

import com.sun.pojo.Category;
import com.sun.pojo.Order;
import com.sun.pojo.PictureResult;
import com.sun.pojo.Product;

import java.util.List;
import java.util.Map;

public interface AdminService {
    public List<Category> findAllCategory();

    public List<Order> getOrderList();

    List<Map<String ,Object>> getOrderInfo(String oid);

    List<Product> getProductList();

    String deleteProduct(String pid);

    String deleteCategory(String cname);
}
