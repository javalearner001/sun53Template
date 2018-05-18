package com.sun.pojo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Cart {

	//该购物车中存储的n个购物项
	private Map<String,CartItem> cartItems = new HashMap<String,CartItem>();
	
	//商品的总计
	private double total;
}
