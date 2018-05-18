package com.sun.pojo;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class PageBean<T> {

	private int currentPage;
	private int currentCount;
	private int totalCount;
	private int totalPage;
	private List<T> list;
	private int index;
	private String cid;

	
	
}
