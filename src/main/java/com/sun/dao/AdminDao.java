package com.sun.dao;

import com.sun.pojo.Category;
import com.sun.pojo.Order;
import com.sun.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
public interface AdminDao {
    @Select("select * from category")
    List<Category> findAllCategory();

    List<Order> getOrderList();

    @Select("select * from product")
    List<Product> getProductList();
    @Delete("DELETE FROM product WHERE pid=#{pid}")
    Boolean deleteProduct(@Param("pid") String pid);

    @Delete("DELETE FROM category WHERE cname=#{cname}")
    Boolean deleteCategory(@Param("cname") String cname);
}
