package com.sun.service;

import com.sun.pojo.Category;
import com.sun.pojo.Order;
import com.sun.pojo.PageBean;
import com.sun.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    //获得热门商品
    public List<Product> findHotProductList();

    //获得最新商品
    public List<Product> findNewProductList();

    public List<Category> findAllCategory();

    public Product findProductByPid(String pid);

    public PageBean findProductListByCid(String cid, int currentPage, int currentCount);

    //提交订单，将订单数据和订单项数据存储到数据库中
    public void submitOrder(Order order);

    public void updateOrderAdrr(Order order);

    public List<Order> findAllOrders(String uid);

    public List<Map<String ,Object>> findAllOrderItemByOid(String oid);

    public void addProduct(Product product);
}
