package com.account;

public class AccountDTO {
	private int accountBook_Num; // 가계부 번호
	private String user_Id; // 아이디
	private String accountBook_Date; // 가계부 등록일
	private String content; // 사용내역
	private int amount; // 사용금액
	private String memo; // 메모
	
	public int getAccountBook_Num() {
		return accountBook_Num;
	}
	public void setAccountBook_Num(int accountBook_Num) {
		this.accountBook_Num = accountBook_Num;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getAccountBook_Date() {
		return accountBook_Date;
	}
	public void setAccountBook_Date(String accountBook_Date) {
		this.accountBook_Date = accountBook_Date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
