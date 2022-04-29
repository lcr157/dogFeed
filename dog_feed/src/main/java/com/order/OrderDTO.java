package com.order;

public class OrderDTO {
	private int order_Num; // 주문번호
	private int product_Num; // 상품번호
	private String product_Name; // 상품명
	private int orderDetail_Quant; // 주문수량
	private int orderDetail_Price; // 주문가격
	private String orderDetail_Date; // 주문날짜
	
	public int getOrder_Num() {
		return order_Num;
	}
	public void setOrder_Num(int order_Num) {
		this.order_Num = order_Num;
	}
	public int getProduct_Num() {
		return product_Num;
	}
	public void setProduct_Num(int product_Num) {
		this.product_Num = product_Num;
	}
	public String getProduct_Name() {
		return product_Name;
	}
	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	public int getOrderDetail_Quant() {
		return orderDetail_Quant;
	}
	public void setOrderDetail_Quant(int orderDetail_Quant) {
		this.orderDetail_Quant = orderDetail_Quant;
	}
	public int getOrderDetail_Price() {
		return orderDetail_Price;
	}
	public void setOrderDetail_Price(int orderDetail_Price) {
		this.orderDetail_Price = orderDetail_Price;
	}
	public String getOrderDetail_Date() {
		return orderDetail_Date;
	}
	public void setOrderDetail_Date(String orderDetail_Date) {
		this.orderDetail_Date = orderDetail_Date;
	}
	
}
