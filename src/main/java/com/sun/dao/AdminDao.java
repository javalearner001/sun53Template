package com.sun.dao;

import com.sun.pojo.Category;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface AdminDao {
    @Select("select * from category")
    List<Category> findAllCategory();
}
