package com.sun.controller;

import com.sun.pojo.Cart;
import com.sun.pojo.CartItem;
import com.sun.pojo.Product;
import com.sun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName CartItemController
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/9 16:19
 * @Version 1.0
 **/
@Controller
public class ShoppingCartController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/addProductToCart")
    public void addProductToCart(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        //获得商品pid和商品购买数量
        String pid=request.getParameter("pid");
        int buyNum=Integer.parseInt(request.getParameter("buyNum"));
        Product product=productService.findProductByPid(pid);
        //计算小计
        double subtotal = product.getShop_price()*buyNum;
        //封装CartItem
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setBuyNum(buyNum);
        item.setSubtotal(subtotal);

        //获得购物车---判断是否在session中已经存在购物车
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart==null){
            cart = new Cart();
        }

        //将购物项放到车中---key是pid
        //先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
        //如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
        Map<String, CartItem> cartItems = cart.getCartItems();

        double newsubtotal = 0.0;

        if(cartItems.containsKey(pid)){
            //取出原有商品的数量
            CartItem cartItem = cartItems.get(pid);
            int oldBuyNum = cartItem.getBuyNum();
            oldBuyNum+=buyNum;
            cartItem.setBuyNum(oldBuyNum);
            cart.setCartItems(cartItems);
            //修改小计
            //原来该商品的小计
            double oldsubtotal = cartItem.getSubtotal();
            //新买的商品的小计
            newsubtotal = buyNum*product.getShop_price();
            cartItem.setSubtotal(oldsubtotal+newsubtotal);

        }else{
            //如果车中没有该商品
            cart.getCartItems().put(product.getPid(), item);
            newsubtotal = buyNum*product.getShop_price();
        }

        //计算总计
        double total = cart.getTotal()+newsubtotal;
        cart.setTotal(total);


        //将车再次访问session
        session.setAttribute("cart", cart);

        //直接跳转到购物车页面
        try {
            response.sendRedirect(request.getContextPath()+"/cart.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
