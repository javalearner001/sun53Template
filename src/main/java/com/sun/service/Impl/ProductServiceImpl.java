package com.sun.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.dao.ProductDao;
import com.sun.pojo.Category;
import com.sun.pojo.PageBean;
import com.sun.pojo.Product;
import com.sun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/8 10:59
 * @Version 1.0
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findHotProductList() {
        int iDisplayStart=1;
        int iDisplayLength=10;
        PageHelper.startPage(iDisplayStart,iDisplayLength);
        List<Product> productList=productDao.findHotProductList();
        PageInfo pageInfo=new PageInfo(productList);
        List<Product> returnList=pageInfo.getList();
        return returnList;
    }

    @Override
    public List<Product> findNewProductList() {
        int iDisplayStart=1;
        int iDisplayLength=10;
        PageHelper.startPage(iDisplayStart,iDisplayLength);
        List<Product> productList=productDao.findNewProductList();
        PageInfo pageInfo=new PageInfo(productList);
        List<Product> returnList=pageInfo.getList();
        return returnList;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categoryList=productDao.findAllCategory();
        return categoryList;
    }

    @Override
    public Product findProductByPid(String pid) {

        Product product=productDao.findProductByPid(pid);
        return product;
    }

    @Override
    public PageBean findProductListByCid(String cid,int currentPage,int currentCount) {
        PageBean<Product> pageBean=new PageBean<Product>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentCount(currentCount);
        pageBean.setCid(cid);
        //显示当前页的商品
        //计算index
        int index=(currentPage-1)*currentCount;
        pageBean.setIndex(index);
        List<Product> productList=productDao.findProductByPage(pageBean);
        pageBean.setList(productList);
        //封装总条数
        int totalCount=productDao.getProductCount(cid);
        pageBean.setTotalCount(totalCount);
        //封装总页数
        int totalPage=(int)Math.ceil(1.0*totalCount/currentCount);
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }
}
