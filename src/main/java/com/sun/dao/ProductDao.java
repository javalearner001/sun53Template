package com.sun.dao;

import com.sun.pojo.Category;
import com.sun.pojo.PageBean;
import com.sun.pojo.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findHotProductList();

    List<Product> findNewProductList();

    List<Category> findAllCategory();

    int getProductCount(String cid);

    List<Product> findProductByPage(PageBean pageBean);

    Product findProductByPid(String pid);
}
