package com.main;

public class ProductOrderDTO {
	private String user_Id;

	private int product_Num;
	private String product_Name;
	private String product_Info;
	private int product_Price;
	
 	private int order_Price;
 	private String order_Date;
 	private int order_Num;
 	
 	private int orderDetail_Quant;
 	private int orderDetail_Price;
 	private String orderDetail_Date;
 	
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public int getProduct_Num() {
		return product_Num;
	}
	public void setProduct_Num(int product_Num) {
		this.product_Num = product_Num;
	}
	public String getProduct_Info() {
		return product_Info;
	}
	public void setProduct_Info(String product_Info) {
		this.product_Info = product_Info;
	}
	public int getProduct_Price() {
		return product_Price;
	}
	public void setProduct_Price(int product_Price) {
		this.product_Price = product_Price;
	}
	public int getOrder_Price() {
		return order_Price;
	}
	public void setOrder_Price(int order_Price) {
		this.order_Price = order_Price;
	}
	public String getOrder_Date() {
		return order_Date;
	}
	public void setOrder_Date(String order_Date) {
		this.order_Date = order_Date;
	}
	public int getOrder_Num() {
		return order_Num;
	}
	public void setOrder_Num(int order_Num) {
		this.order_Num = order_Num;
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
	public String getProduct_Name() {
		return product_Name;
	}
	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	public String getOrderDetail_Date() {
		return orderDetail_Date;
	}
	public void setOrderDetail_Date(String orderDetail_Date) {
		this.orderDetail_Date = orderDetail_Date;
	}
 	
 	
 	
	
}
