package com.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class AccountDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertAccount(AccountDTO dto) throws SQLException {
		// 가계부 추가
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO accountBook(accountBook_Num, user_Id, accountBook_Date, content, amount, memo) "
				+ "VALUES(accountBook_seq.NEXTVAL, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUser_Id());
			pstmt.setString(2, dto.getAccountBook_Date());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setString(5, dto.getMemo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public void updateAccount(AccountDTO dto) throws SQLException {
		// 가계부 수정
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE accountBook SET accountBook_Date=? content=? amount=? memo=? WHERE accountBook_Num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getAccountBook_Date());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getAmount());
			pstmt.setString(4, dto.getMemo());
			pstmt.setInt(5, dto.getAccountBook_Num());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
	}
	
	public void deleteAccount(int accountBook_Num, String user_Id) throws SQLException {
		// 가계부 삭제
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if (user_Id.equals("admin")) { // 관리자
				sql = "DELETE FROM accountBook WHERE accountBook_Num=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, accountBook_Num);
				
				pstmt.executeUpdate();
			} else { // 사용자
				sql = "DELETE FROM accountBook WHERE accountBook_Num=? AND user_Id=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, accountBook_Num);
				pstmt.setString(2, user_Id);
				
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
	}
	
	public int dataCount() {
		// 총 개수
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM accountBook";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public List<AccountDTO> listAccount(String id) {
		// 전체 리스트
		List<AccountDTO> accountList = new ArrayList<AccountDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT accountBook_Num, user_Id, TO_CHAR(accountBook_Date, 'YYYY-MM-DD') accountBook_Date, content, amount, NVL(memo, '-') memo "
				+ "FROM accountBook";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AccountDTO dto = new AccountDTO();
				
				dto.setAccountBook_Num(rs.getInt("accountBook_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setAccountBook_Date(rs.getString("accountBook_Date"));
				dto.setContent(rs.getString("content"));
				dto.setAmount(rs.getInt("amount"));
				dto.setMemo(rs.getString("memo"));
				
				accountList.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return accountList;
	}
	
	public AccountDTO readAccount(int accountBook_Num) {
		AccountDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT accountBook_Num, user_Id, TO_CHAR(accountBook_Date, 'YYYY-MM-DD') accountBook_Date, content, amount, NVL(memo, '-') memo "
				+ " FROM accountBook WHERE accountBook_Num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, accountBook_Num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new AccountDTO();
				dto.setAccountBook_Num(rs.getInt("accountBook_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setAccountBook_Date(rs.getString("accountBook_Date"));
				dto.setContent(rs.getString("content"));
				dto.setAmount(rs.getInt("amount"));
				dto.setMemo(rs.getString("memo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}
	
}
