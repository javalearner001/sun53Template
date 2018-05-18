package com.sun.controller;

import com.sun.pojo.PageBean;
import com.sun.pojo.Product;
import com.sun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/9 14:30
 * @Version 1.0
 **/
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/productListByCid")
    public void productListByCid(HttpServletRequest request, HttpServletResponse response){
        try {
            //获得cid
            String cid=request.getParameter("cid");
            String currentPageStr=request.getParameter("currentPage");
            int currentPage;
            if (StringUtils.isEmpty(currentPageStr)){
                currentPage=1;
            }else {
                currentPage=Integer.parseInt(currentPageStr);
            }
            int currentCount=10;
           /* String currentCountStr=request.getParameter("currentCount");
            int currentCount;
            if (StringUtils.isEmpty(currentCountStr)){
                currentCount=10;
            }
            currentCount=Integer.parseInt(currentCountStr);*/

            PageBean pageBean=productService.findProductListByCid(cid,currentPage,currentCount);
            request.setAttribute("pageBean",pageBean);
            request.setAttribute("cid",cid);
            request.getRequestDispatcher("/product_list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/productInfo")
    public void productInfoByPid(HttpServletRequest request,HttpServletResponse response){
        try {
            //获得当前页和商品类别
            String cid=request.getParameter("cid");
            String currentPage=request.getParameter("currentPage");
            //获得要查询商品的pid
            String pid=request.getParameter("pid");
            Product product=productService.findProductByPid(pid);
            request.setAttribute("product",product);
            request.setAttribute("cid",cid);
            request.setAttribute("currentPage",currentPage);

            //获得客户端携带cookie---获得名字是pids的cookie
            String pids = pid;
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(Cookie cookie : cookies){
                    if("pids".equals(cookie.getName())){
                        pids = cookie.getValue();
                        //1-3-2 本次访问商品pid是8----->8-1-3-2
                        //1-3-2 本次访问商品pid是3----->3-1-2
                        //1-3-2 本次访问商品pid是2----->2-1-3
                        //将pids拆成一个数组
                        String[] split = pids.split("-");//{3,1,2}
                        List<String> asList = Arrays.asList(split);//[3,1,2]
                        LinkedList<String> list = new LinkedList<String>(asList);//[3,1,2]
                        //判断集合中是否存在当前pid
                        if(list.contains(pid)){
                            //包含当前查看商品的pid
                            list.remove(pid);
                            list.addFirst(pid);
                        }else{
                            //不包含当前查看商品的pid 直接将该pid放到头上
                            list.addFirst(pid);
                        }
                        //将[3,1,2]转成3-1-2字符串
                        StringBuffer sb = new StringBuffer();
                        for(int i=0;i<list.size()&&i<7;i++){
                            sb.append(list.get(i));
                            sb.append("-");//3-1-2-
                        }
                        //去掉3-1-2-后的-
                        pids = sb.substring(0, sb.length()-1);
                    }
                }
            }


            Cookie cookie_pids = new Cookie("pids",pids);
            response.addCookie(cookie_pids);

            request.getRequestDispatcher("/product_info.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
