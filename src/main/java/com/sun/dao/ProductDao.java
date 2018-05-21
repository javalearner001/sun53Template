package com.sun.dao;

import com.sun.pojo.*;

import java.util.List;

public interface ProductDao {
    List<Product> findHotProductList();

    List<Product> findNewProductList();

    List<Category> findAllCategory();

    int getProductCount(String cid);

    List<Product> findProductByPage(PageBean pageBean);

    Product findProductByPid(String pid);

    void addOrders(Order order);

    void addOrderItem(OrderItem orderItem);

    void updateOrderAdrr(Order order);
}
