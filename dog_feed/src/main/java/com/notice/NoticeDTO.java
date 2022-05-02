package com.notice;

public class NoticeDTO {
	private int listNum;
	private int notice_Num;
	private String user_Id;
	private String user_Name;
	private String notice_Subject;
	private String notice_Content;
	private String notice_Date;
	private int notice_Hits;
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getNotice_Num() {
		return notice_Num;
	}
	public void setNotice_Num(int notice_Num) {
		this.notice_Num = notice_Num;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getNotice_Subject() {
		return notice_Subject;
	}
	public void setNotice_Subject(String notice_Subject) {
		this.notice_Subject = notice_Subject;
	}
	public String getNotice_Content() {
		return notice_Content;
	}
	public void setNotice_Content(String notice_Content) {
		this.notice_Content = notice_Content;
	}
	public String getNotice_Date() {
		return notice_Date;
	}
	public void setNotice_Date(String notice_Date) {
		this.notice_Date = notice_Date;
	}
	public int getNotice_Hits() {
		return notice_Hits;
	}
	public void setNotice_Hits(int notice_Hits) {
		this.notice_Hits = notice_Hits;
	}
	
}
