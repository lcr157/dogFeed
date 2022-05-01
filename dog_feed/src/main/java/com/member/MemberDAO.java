package com.member;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	private static MemberDAO _dao;
	
	public MemberDAO() {
		
	}
	
	static {
		_dao = new MemberDAO();
	}
	
	public static MemberDAO getDAO() {
		return _dao;
	}
	
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
					+ " user_Address1, user_Address2, join_date) VALUES (?, ?, ?, TO_DATE(?,'YYYYMMDD'), ?, ?, ?, ?, SYSDATE)";
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
	
	// MEMBER테이블에 저장된 모든 회원목록을 검색하여 반환하는 메소드
	public List<MemberDTO> selectAllMember() throws SQLException {
		Connection con=null;
		PreparedStatement psm=null;
		ResultSet rs=null;
		List<MemberDTO> memberList=new ArrayList<MemberDTO>();
		
		try {
			con = DBConn.getConnection();
			String sql="SELECT * FROM MEMBER ORDER BY user_Id";
			psm = con.prepareStatement(sql);
			rs = psm.executeQuery();
			
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setUser_Id(rs.getString("user_Id"));
				member.setUser_Pwd(rs.getString("user_Pwd"));
				member.setUser_Name(rs.getString("user_Name"));
				member.setUser_Birth(rs.getString("user_Birth"));
				member.setUser_Email1(rs.getString("user_Email"));
				member.setTel(rs.getString("user_Tel"));
				member.setUser_Address1(rs.getString("user_Address1"));
				member.setUser_Address2(rs.getString("user_Address2"));
				member.setUser_Role(rs.getInt("user_Role"));
				member.setJoinDate(rs.getDate("join_date"));
				member.setLastLogin(rs.getDate("last_login"));
				memberList.add(member);
			}
		
		} catch (SQLException e) {
			System.out.println("[Error] selectAllMember()의 SQL오류 >> "+e.getMessage());
		} finally {

		}
		return memberList;
		
		
	}
	
	// 아이디를 전달받아 MEMBER테이블에 해당 아이디의 회원정보를 검색하여 반환하는 메소드 >> 단일행검색
	// => null : 전달된 회원정보 미검색 >>  해당 아이디 사용가능
	// => MemberDTO : 전달된 회원정보 검색 >>  해당 아이디 사용불가
	public MemberDTO selectIdMmember(String user_Id) {
		Connection con = null;
		PreparedStatement psm =null;
		ResultSet rs = null;
		MemberDTO member = null;
		
		try {
			con = DBConn.getConnection();
			String sql = "SELECT * FROM MEMBER WHERE USER_ID=?";
			psm=con.prepareStatement(sql);
			psm.setString(1, user_Id);
			rs=psm.executeQuery();
			
			if(rs.next()) {
				member = new MemberDTO();
				// ResultSet에서 컬럼명 그대로 작성주의!
				member.setUser_Id(rs.getString("user_Id"));
				member.setUser_Pwd(rs.getString("user_Pwd"));
				member.setUser_Name(rs.getString("user_Name"));
				member.setUser_Birth(rs.getString("user_Birth"));
				member.setUser_Email1(rs.getString("user_Email"));
				member.setTel(rs.getString("user_Tel"));
				member.setUser_Address1(rs.getString("user_Address1"));
				member.setUser_Address2(rs.getString("user_Address2"));
				member.setUser_Role(rs.getInt("user_Role"));

			}
			
		} catch (SQLException e) {
			System.out.println("[Error] selectIdMember()의 SQL오류 >> "+e.getMessage());
		} finally {
			
		}
		return member;
	}
	
	// 아이디를 전달받아 MEMBER 테이블에 저장된 해당 회원정보의 마지막로그인날짜를 현재로 변경하고 변경행의 개수를 반환하는 메소드
	public int updateLastLogin(String user_Id) {
		Connection con = null;
		PreparedStatement psm = null;
		int rows = 0;
		
		try {
			con = DBConn.getConnection();
			String sql="UPDATE MEMBER SET LAST_LOGIN=SYSDATE WHERE user_ID=?";
			psm=con.prepareStatement(sql);
			psm.setString(1, user_Id);
			
			rows=psm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[Error] updateLastLogin()의 SQL오류 >> "+e.getMessage());
		} finally {
			
		} 
		return rows;
	}
	
	// 아이디와 상태를 전달받아 MEMBER테이블에 저장된 회원의 상태를 변경행의 게수를 반환하는 메소드 [관리자 페이지용]
	public int updateUser_Role(String user_Id, int user_Role) {
		Connection con = null;
		PreparedStatement psm = null;
		int rows = 0;
		
		try {
			con = DBConn.getConnection();
			String sql = "UPDATE MEMBER SET USER_ROLE=? WHERE USER_ID=?";
			psm = con.prepareStatement(sql);
			psm.setInt(1, user_Role);
			psm.setString(2, user_Id);
			
			rows = psm.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("[Error] updateStatus()의 SQL오류 >> "+e.getMessage());
		} finally {
			
		}
		return rows;
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
