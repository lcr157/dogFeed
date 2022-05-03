package com.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class FaqDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertFaq(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO Faq(faq_Num, user_Id, faq_Subject, faq_Content,  "
					+ " faq_Save, faq_Original, faq_FileSize, faq_Category ) "
				+ "VALUES( faq_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ? )";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUser_Id());
			pstmt.setString(2, dto.getFaq_Subject());
			pstmt.setString(3, dto.getFaq_Content());
			pstmt.setString(4, dto.getFaq_Save());
			pstmt.setString(5, dto.getFaq_Original());
			pstmt.setLong(6, dto.getFaq_FileSize());
			pstmt.setInt(7, dto.getFaq_Category());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}	
	}
	
	public int dataCount(int category) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Faq";
			if(category != 0) {
				sql += " WHERE faq_Category = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			if(category != 0) {
				pstmt.setInt(1, category);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
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

		return result;
	}
	
	public int dataCount(int category, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM faq"
				+ " WHERE ( INSTR(faq_Subject, ?) >= 1 OR INSTR(faq_Content, ?) >= 1 ) ";
			if(category != 0) {
				sql += " AND faq_Category = ? ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			if(category != 0) {
				pstmt.setInt(3, category);
			}

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
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

		return result;
	}
	
	public List<FaqDTO> listFaq(int start, int end, int category) {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT f.faq_Category, faq_Cname, faq_Num, faq_Subject, faq_Content, ");
			sb.append("         faq_Save, faq_Original, faq_FileSize ");
			sb.append("         FROM Faq f ");
			sb.append("         JOIN faqCategory c ON f.faq_Category = c.faq_Category ");
			if(category!=0) {
				sb.append("     			WHERE f.faq_Category = ? ");
			}
			sb.append("         ORDER BY faq_Num DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			if(category!=0) {
				pstmt.setInt(1, category);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			} else {
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			}

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FaqDTO dto = new FaqDTO();
				
				dto.setFaq_Category(rs.getInt("faq_Category"));
				dto.setFaq_Num(rs.getInt("faq_Num"));
				dto.setFaq_Subject(rs.getString("faq_Subject"));
				dto.setFaq_Content(rs.getString("faq_Content"));
				dto.setFaq_Cname(rs.getString("faq_Cname"));
				dto.setFaq_Save(rs.getString("faq_Save"));
				dto.setFaq_Original(rs.getString("faq_Original"));
				dto.setFaq_FileSize(rs.getLong("faq_Filesize"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	public List<FaqDTO> listFaq(int start, int end, String keyword, int category) {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT f.faq_Category, faq_Cname, faq_Num, faq_Subject, faq_Content, ");
			sb.append("         faq_Save, faq_Original, faq_FileSize ");
			sb.append("         FROM Faq f");
			sb.append("        	 	JOIN faqCategory c ON f.faq_Category = c.faq_Category ");
			sb.append("     			WHERE (INSTR(faq_Subject, ?) >= 1 OR INSTR(faq_Content, ?) >= 1) ");
			if(category!=0) {
				sb.append("     			AND faq_Category = ? ");
			}
			sb.append("         ORDER BY faq_Num DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");



			pstmt = conn.prepareStatement(sb.toString());
			
			
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			if(category==0) {
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			} else {
				pstmt.setInt(3, category);
				pstmt.setInt(4, end);
				pstmt.setInt(5, start);
			}
			
			

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FaqDTO dto = new FaqDTO();
				
				dto.setFaq_Num(rs.getInt("faq_Category"));
				dto.setFaq_Num(rs.getInt("faq_Num"));
				dto.setFaq_Subject(rs.getString("faq_Subject"));
				dto.setFaq_Content(rs.getString("faq_Content"));
				dto.setFaq_Cname(rs.getString("faq_Cname"));
				dto.setFaq_Save(rs.getString("faq_Save"));
				dto.setFaq_Original(rs.getString("faq_Original"));
				dto.setFaq_FileSize(rs.getLong("faq_Filesize"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	public FaqDTO readFaq(int num) {
		FaqDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT faq_Num, f.user_Id, faq_Subject, faq_Content, "
					+ " faq_Original, faq_Save, faq_FileSize, faq_Category "
					+ " FROM Faq f "
					+ " JOIN Member m ON f.user_Id=m.user_Id "
					+ " WHERE faq_Num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FaqDTO();
				
				dto.setFaq_Num(rs.getInt("faq_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setFaq_Subject(rs.getString("faq_Subject"));
				dto.setFaq_Content(rs.getString("faq_Content"));
				dto.setFaq_Category(rs.getInt("faq_Category"));
				dto.setFaq_Save(rs.getString("faq_Save"));
				dto.setFaq_Original(rs.getString("faq_Original"));
				dto.setFaq_FileSize(rs.getLong("faq_Filesize"));
				
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
	
	public void updateFaq(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE Faq SET faq_Subject=?, faq_Content=?, faq_Category=?, "
					+ " faq_Save=?, faq_Original=?, faq_Filesize=? WHERE faq_Num=? AND user_Id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFaq_Subject());
			pstmt.setString(2, dto.getFaq_Content());
			pstmt.setInt(3, dto.getFaq_Category());
			pstmt.setString(4, dto.getFaq_Save());
			pstmt.setString(5, dto.getFaq_Original());
			pstmt.setLong(6, dto.getFaq_FileSize());
			pstmt.setInt(7, dto.getFaq_Num());
			pstmt.setString(8, dto.getUser_Id());
			
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
	
	public void deleteFaq(int num, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (userId.equals("admin")) {
				sql = "DELETE FROM Faq WHERE faq_Num=? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM Faq WHERE faq_Num=? AND user_Id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
			}
			

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
	
	
	public List<FaqDTO> listCategory() {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT faq_Category, faq_Cname ");
			sb.append(" FROM FaqCategory ");
			sb.append(" ORDER BY faq_Category DESC");

			pstmt = conn.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FaqDTO dto = new FaqDTO();

				dto.setFaq_Category(rs.getInt("faq_Category"));
				dto.setFaq_Cname(rs.getString("faq_Cname"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	
}
