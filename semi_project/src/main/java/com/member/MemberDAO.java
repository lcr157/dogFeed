package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	// 로그인
	public MemberDTO loginMember(String user_Id, String user_Pwd) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT user_Id, user_Name, user_Pwd");
			sb.append(" FROM member");
			sb.append(" WHERE user_Id = ? AND user_Pwd = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, user_Id);
			pstmt.setString(2, user_Pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Pwd(rs.getString("user_Pwd"));
				dto.setUser_Name(rs.getString("user_Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}
}
