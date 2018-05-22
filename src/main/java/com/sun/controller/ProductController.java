package com.sun.controller;

import com.sun.pojo.*;
import com.sun.service.ProductService;
import com.sun.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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

    //删除单一商品
    @RequestMapping("/delProFromCart")
    public void delProFromCart(HttpServletRequest request, HttpServletResponse response){
        try {
            //获得要删除的item的pid
            String pid = request.getParameter("pid");
            //删除session中的购物车中的购物项集合中的item
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if(cart!=null){
                Map<String, CartItem> cartItems = cart.getCartItems();
                //需要修改总价
                cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
                //删除
                cartItems.remove(pid);
                cart.setCartItems(cartItems);

            }

            session.setAttribute("cart", cart);

            //跳转回cart.jsp
            response.sendRedirect(request.getContextPath()+"/cart.jsp");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //清空购物车
    @RequestMapping("/clearCart")
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("cart");

            //跳转回cart.jsp
            response.sendRedirect(request.getContextPath()+"/cart.jsp");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    //提交订单
    @RequestMapping("/submitOrder")
    public void submitOrder(HttpServletRequest request, HttpServletResponse response){
        try{
            HttpSession session = request.getSession();
            //判断用户是否已经登录 未登录下面代码不执行
            User user = (User) session.getAttribute("user");
            if(user==null){
                //没有登录
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return;
            }
            //目的：封装好一个Order对象 传递给service层
            Order order = new Order();
            //1、private String oid;//该订单的订单号
            String oid = CommonUtils.getUUid();
            order.setOid(oid);
            //2、private Date ordertime;//下单时间
            order.setOrdertime(new Date());
            //3、private double total;//该订单的总金额
            //获得session中的购物车
            Cart cart = (Cart) session.getAttribute("cart");
            double total = cart.getTotal();
            order.setTotal(total);

            //4、private int state;//订单支付状态 1代表已付款 0代表未付款
            order.setState(0);

            //5、private String address;//收货地址
            order.setAddress(null);

            //6、private String name;//收货人
            order.setName(null);

            //7、private String telephone;//收货人电话
            order.setTelephone(null);

            //8、private User user;//该订单属于哪个用户
            order.setUser(user);

            //9、该订单中有多少订单项List<OrderItem> orderItems = new ArrayList<OrderItem>();
            //获得购物车中的购物项的集合map
            Map<String, CartItem> cartItems = cart.getCartItems();
            for(Map.Entry<String, CartItem> entry : cartItems.entrySet()){
                //取出每一个购物项
                CartItem cartItem = entry.getValue();
                //创建新的订单项
                OrderItem orderItem = new OrderItem();
                //1)private String itemid;//订单项的id
                orderItem.setItemid(CommonUtils.getUUid());
                //2)private int count;//订单项内商品的购买数量
                orderItem.setCount(cartItem.getBuyNum());
                //3)private double subtotal;//订单项小计
                orderItem.setSubtotal(cartItem.getSubtotal());
                //4)private Product product;//订单项内部的商品
                orderItem.setProduct(cartItem.getProduct());
                //5)private Order order;//该订单项属于哪个订单
                orderItem.setOrder(order);

                //将该订单项添加到订单的订单项集合中
                order.getOrderItems().add(orderItem);
            }


            //order对象封装完毕
            //传递数据到service层
            productService.submitOrder(order);


            session.setAttribute("order", order);

            //页面跳转
            response.sendRedirect(request.getContextPath()+"/order_info.jsp");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping("/confirmOrder")
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response){
        try {
            //提交订单成功
            //校验address，name，telephone
            if (org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("address"))){
                System.out.println("收货地址为空");
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("name"))){
                System.out.println("收货人为空");
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("telephone"))){
                System.out.println("电话为空");
            }

            //修改order的state，收货地址，收货人，电话
            Order order=new Order();
            order.setName(request.getParameter("name"));
            order.setAddress(request.getParameter("address"));
            order.setTelephone(request.getParameter("telephone"));
            order.setState(1);
            order.setOid(request.getParameter("oid"));

            productService.updateOrderAdrr(order);
            //页面跳转
            response.sendRedirect(request.getContextPath()+"/orderPaySuccess.jsp");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/myOrders")
    //获得我的订单
    public void myOrders(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user==null){
                //没有登录
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return;
            }
            //查询该用户的所有的订单信息(单表查询orders表)
            //集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据
            List<Order> orderList = productService.findAllOrders(String.valueOf(user.getUid()));
            //循环所有的订单 为每个订单填充订单项集合信息
            if(orderList!=null){
                for(Order order : orderList){
                    //获得每一个订单的oid
                    String oid = order.getOid();
                    //查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
                    List<Map<String, Object>> mapList = productService.findAllOrderItemByOid(oid);
                    //将mapList转换成List<OrderItem> orderItems
                    for(Map<String,Object> map : mapList){
                        //从map中取出count subtotal 封装到OrderItem中
                        OrderItem item = new OrderItem();
                        item.setCount(Integer.parseInt(map.get("count").toString()));
                        item.setSubtotal(Double.parseDouble(map.get("subtotal").toString()));
                        //从map中取出pimage pname shop_price 封装到Product中
                        Product product = new Product();
                        product.setPimage(map.get("pimage").toString());
                        product.setPname(map.get("pname").toString());
                        product.setShop_price(Double.parseDouble(map.get("shop_price").toString()));
                        //将product封装到OrderItem
                        item.setProduct(product);
                        //将orderitem封装到order中的orderItemList中
                        order.getOrderItems().add(item);
                    }

                }
            }
            //orderList封装完整了
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("/order_list.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
