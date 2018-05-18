package com.sun.service;

import com.sun.pojo.Category;
import com.sun.pojo.PageBean;
import com.sun.pojo.Product;

import java.util.List;

public interface ProductService {
    //获得热门商品
    public List<Product> findHotProductList();

    //获得最新商品
    public List<Product> findNewProductList();

    public List<Category> findAllCategory();

    public Product findProductByPid(String pid);

    public PageBean findProductListByCid(String cid,int currentPage,int currentCount);
}
