package com.sun.service;

import com.sun.pojo.Category;
import com.sun.pojo.Order;

import java.util.List;
import java.util.Map;

public interface AdminService {
    public List<Category> findAllCategory();

    public List<Order> getOrderList();

    List<Map<String ,Object>> getOrderInfo(String oid);
}
