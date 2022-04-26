package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	// 로그인 함수
	public MemberDTO loginMember(String user_Id, String user_Pwd) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT user_Id, user_Name, user_Pwd, user_Role "
					+ " FROM member WHERE user_Id = ? AND user_Pwd = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user_Id);
			pstmt.setString(2, user_Pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setUser_Pwd(rs.getString("user_Pwd"));
				dto.setUser_Role(rs.getInt("user_Role"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return dto;
	}
	
	
	// 회원가입 함수
	public void insertMember(MemberDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "INSERT INTO member(user_Id, user_Pwd, user_Name, user_Birth, user_Email, user_Tel, "
					+ " user_Address1, user_Address2) VALUES (?, ?, ?, TO_DATE(?,'YYYYMMDD'), ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_Id());
			pstmt.setString(2, dto.getUser_Pwd());
			pstmt.setString(3, dto.getUser_Name());
			pstmt.setString(4, dto.getUser_Birth());
			pstmt.setString(5, dto.getUser_Email1());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getUser_Address1());
			pstmt.setString(8, dto.getUser_Address2());
			
			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}

	}

	
	// 회원정보 가져오기 함수
	public MemberDTO readMember(String user_Id) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT user_Id, user_Name, user_Pwd,");
			sb.append("      TO_CHAR(user_Birth, 'YYYY-MM-DD') user_Birth, ");
			sb.append("      user_Email, user_Tel,");
			sb.append("      user_Address1, user_Address2");
			sb.append("  FROM member ");
			sb.append("  WHERE user_Id = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, user_Id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new MemberDTO();

				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Pwd(rs.getString("user_Pwd"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setUser_Birth(rs.getString("user_Birth"));
				dto.setTel(rs.getString("user_Tel"));
				if (dto.getTel() != null) {
					String[] ss = dto.getTel().split("-");
					if (ss.length == 3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				dto.setUser_Email1(rs.getString("user_Email"));
				if (dto.getUser_Email1() != null) {
					String[] ss = dto.getUser_Email1().split("@");
					if (ss.length == 2) {
						dto.setUser_Email2(ss[0]);
						dto.setUser_Email3(ss[1]);
					}
				}

				dto.setUser_Address1(rs.getString("user_Address1"));
				dto.setUser_Address2(rs.getString("user_Address2"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return dto;
	}

	
	// 회원수정 함수
	public void updateMember(MemberDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE member SET user_Pwd=? WHERE user_Id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_Pwd());
			pstmt.setString(2, dto.getUser_Id());

			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			sql = "UPDATE member SET user_Birth=TO_DATE(?,'YYYYMMDD'), "
					+ " user_Email=?, user_Tel=?, user_Address1=?, user_Address2=? WHERE user_Id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_Birth());
			pstmt.setString(2, dto.getUser_Email1());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getUser_Address1());
			pstmt.setString(5, dto.getUser_Address2());
			pstmt.setString(6, dto.getUser_Id());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	
	// 회원탈퇴 함수
	public void deleteMember(String user_Id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM member WHERE user_Id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user_Id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	
}
