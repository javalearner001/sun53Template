package com.sun.pojo;

import lombok.Data;

@Data
public class CartItem {

	private Product product;
	private int buyNum;
	private double subtotal;

}
