package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConn {
	private static Connection conn = null;

	private DBConn() {
	}
	
	public static Connection getConnection() {
		// String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";  // 11g
		String url = "jdbc:oracle:thin:@//127.0.0.1:1521/XE"; // 12C 이상
		String user = "dogfeed";
		String pwd = "java$!";
		
		if(conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pwd);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}

	public static Connection getConnection(String url, String user, String pwd) {
		if(conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}

	public static Connection getConnection(String url, String user, String pwd, String internal_logon) {
		if(conn == null) {
			try {
				Properties info = new Properties();
				info.put("user", user);
				info.put("password", pwd);
				info.put("internal_logon", internal_logon);  // sysdba, Normal 등 roll
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public static void close() {
		if(conn == null) {
			return;
		}
		
		try {
			if(! conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
		}
		
		conn = null;
	}
}
