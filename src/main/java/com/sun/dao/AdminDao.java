package com.sun.dao;

import com.sun.pojo.Category;
import com.sun.pojo.Order;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
public interface AdminDao {
    @Select("select * from category")
    List<Category> findAllCategory();

    List<Order> getOrderList();

}
