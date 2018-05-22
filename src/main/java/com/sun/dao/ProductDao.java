package com.sun.dao;

import com.sun.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
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

    @Select("select * from orders where uid=#{uid}")
    List<Order> findAllOrders(@Param("uid") String uid);

    List<Map<String, Object>> findAllOrderItemByOid(String oid);
}
