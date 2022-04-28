package com.product;

// product와 CategoryDetail을 합쳐서 저장
public class ProductDTO {
	// listNum은 순서정렬 용도
	private int listNum;
	private int product_Num;
	private int category_Num;
	private String product_Name;
	private int product_Price;
	private String product_Info;
	private String product_Date;
	private int product_Hits;
	private int product_Privacy;
	
	private int categoryDetail_Num;
	private String categoryDetail_Name;
	private String categoryDetail_Kind;
	
	// gap은 시간 측정용도
	private long gap;
	
	
	public long getGap() {
		return gap;
	}
	public void setGap(long gap) {
		this.gap = gap;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getProduct_Num() {
		return product_Num;
	}
	public void setProduct_Num(int product_Num) {
		this.product_Num = product_Num;
	}
	public int getCategory_Num() {
		return category_Num;
	}
	public void setCategory_Num(int category_Num) {
		this.category_Num = category_Num;
	}
	public String getProduct_Name() {
		return product_Name;
	}
	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	public int getProduct_Price() {
		return product_Price;
	}
	public void setProduct_Price(int product_Price) {
		this.product_Price = product_Price;
	}
	public String getProduct_Info() {
		return product_Info;
	}
	public void setProduct_Info(String product_Info) {
		this.product_Info = product_Info;
	}
	public String getProduct_Date() {
		return product_Date;
	}
	public void setProduct_Date(String product_Date) {
		this.product_Date = product_Date;
	}
	public int getProduct_Hits() {
		return product_Hits;
	}
	public void setProduct_Hits(int product_Hits) {
		this.product_Hits = product_Hits;
	}
	public int getProduct_Privacy() {
		return product_Privacy;
	}
	public void setProduct_Privacy(int product_Privacy) {
		this.product_Privacy = product_Privacy;
	}
	public int getCategoryDetail_Num() {
		return categoryDetail_Num;
	}
	public void setCategoryDetail_Num(int categoryDetail_Num) {
		this.categoryDetail_Num = categoryDetail_Num;
	}
	public String getCategoryDetail_Name() {
		return categoryDetail_Name;
	}
	public void setCategoryDetail_Name(String categoryDetail_Name) {
		this.categoryDetail_Name = categoryDetail_Name;
	}
	public String getCategoryDetail_Kind() {
		return categoryDetail_Kind;
	}
	public void setCategoryDetail_Kind(String categoryDetail_Kind) {
		this.categoryDetail_Kind = categoryDetail_Kind;
	}
		
	
}
