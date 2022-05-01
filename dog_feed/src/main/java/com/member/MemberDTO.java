package com.member;

import java.sql.Date;

public class MemberDTO {
	private String user_Id;
	private String user_Pwd;
	private String user_Name;
	private String user_Email1, user_Email2, user_Email3;
	private String tel, tel1, tel2, tel3;
	private String user_Address1;
	private String user_Address2;
	private String user_Birth;
	private int user_Role;
	private Date joinDate;
	private Date lastLogin;
	
	public MemberDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_Pwd() {
		return user_Pwd;
	}
	public void setUser_Pwd(String user_Pwd) {
		this.user_Pwd = user_Pwd;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getUser_Email1() {
		return user_Email1;
	}
	public void setUser_Email1(String user_Email1) {
		this.user_Email1 = user_Email1;
	}
	public String getUser_Email2() {
		return user_Email2;
	}
	public void setUser_Email2(String user_Email2) {
		this.user_Email2 = user_Email2;
	}
	public String getUser_Email3() {
		return user_Email3;
	}
	public void setUser_Email3(String user_Email3) {
		this.user_Email3 = user_Email3;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getUser_Address1() {
		return user_Address1;
	}
	public void setUser_Address1(String user_Address1) {
		this.user_Address1 = user_Address1;
	}
	public String getUser_Address2() {
		return user_Address2;
	}
	public void setUser_Address2(String user_Address2) {
		this.user_Address2 = user_Address2;
	}
	public String getUser_Birth() {
		return user_Birth;
	}
	public void setUser_Birth(String user_Birth) {
		this.user_Birth = user_Birth;
	}
	public int getUser_Role() {
		return user_Role;
	}
	public void setUser_Role(int user_Role) {
		this.user_Role = user_Role;
	}


	public Date getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}


	public Date getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	

}
