package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();
	
	
	public void insertQna(QnaDTO dto, String mode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;
		
		try {
			sql = "SELECT qna_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setQna_Num(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			if(mode.equals("write")) {
				
				dto.setQna_GroupNum(seq);
				dto.setQna_OrderNum(0);
				dto.setQna_Depth(0);
				dto.setQna_Parent(0);
			} else if(mode.equals("reply")) {
			
				updateQna_OrderNum(dto.getQna_GroupNum(), dto.getQna_OrderNum());
				
				dto.setQna_Depth(dto.getQna_Depth() + 1);
				dto.setQna_OrderNum(dto.getQna_OrderNum() + 1);
			}
			
			sql = "INSERT INTO qna(qna_Num, user_Id, qna_Subject, qna_Content, "
					+ "  qna_GroupNum, qna_Depth, qna_OrderNum, qna_Parent, qna_Date, "
					+ " qna_Privacy, qna_Check, product_Num ) "
					+ "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, 0, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getQna_Num());
			pstmt.setString(2, dto.getUser_Id());
			pstmt.setString(3, dto.getQna_Subject());
			pstmt.setString(4, dto.getQna_Content());
			pstmt.setInt(5, dto.getQna_GroupNum());
			pstmt.setInt(6, dto.getQna_Depth());
			pstmt.setInt(7, dto.getQna_OrderNum());
			pstmt.setInt(8, dto.getQna_Parent());
			pstmt.setInt(9, dto.getQna_Privacy());
			pstmt.setInt(10, dto.getProduct_Num());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
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
		
	
	public void updateQna_OrderNum(int qna_GroupNum, int qna_OrderNum) throws SQLException {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE qna SET qna_OrderNum = qna_OrderNum+1 WHERE qna_GroupNum = ? AND qna_OrderNum > ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qna_GroupNum);
			pstmt.setInt(2, qna_OrderNum);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna";
			pstmt = conn.prepareStatement(sql);

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
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna q JOIN member m ON q.user_Id = m.user_Id ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(qna_Subject, ?) >= 1 OR INSTR(qna_Content, ?) >= 1 ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
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
	
	public List<QnaDTO> listQna(int start, int end) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT qna_Num, q.user_Id, user_Name, ");
			sb.append("               qna_Subject, qna_GroupNum, qna_OrderNum, qna_Depth, ");
			sb.append("               qna_Privacy, qna_Check, q.product_Num, product_Name, ");
			sb.append("               TO_CHAR(qna_Date, 'YYYY-MM-DD') qna_Date ");
			sb.append("         FROM qna q ");
			sb.append("         JOIN member m ON q.user_Id = m.user_Id ");
			sb.append("         JOIN product p ON q.product_Num = p.product_Num ");
			sb.append("         ORDER BY qna_GroupNum DESC, qna_OrderNum ASC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQna_Num(rs.getInt("qna_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setQna_Subject(rs.getString("qna_Subject"));
				dto.setQna_GroupNum(rs.getInt("qna_GroupNum"));
				dto.setQna_Depth(rs.getInt("qna_Depth"));
				dto.setQna_Privacy(rs.getInt("qna_Privacy"));
				dto.setQna_Check(rs.getInt("qna_Check"));
				dto.setQna_OrderNum(rs.getInt("qna_OrderNum"));
				dto.setQna_Date(rs.getString("qna_Date"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				
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
	
	
	public List<QnaDTO> listQna(int start, int end, String condition, String keyword) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT qna_Num, q.user_Id, user_Name, ");
			sb.append("               qna_Subject, qna_GroupNum, qna_OrderNum, qna_Depth, ");
			sb.append("               qna_Privacy, qna_Check, q.product_Num, product_Name, ");
			sb.append("               TO_CHAR(qna_Date, 'YYYY-MM-DD') qna_Date ");
			sb.append("         FROM qna q ");
			sb.append("         JOIN member m ON q.user_Id = m.user_Id ");
			sb.append("         JOIN product p ON q.product_Num = p.product_Num ");
			if (condition.equals("all")) {
				sb.append("  WHERE INSTR(qna_Subject, ?) >= 1 OR INSTR(qna_Content, ?) >= 1 ");
			} else {
				sb.append("  WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("         ORDER BY qna_GroupNum DESC, qna_OrderNum ASC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			}

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQna_Num(rs.getInt("qna_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setQna_Subject(rs.getString("qna_Subject"));
				dto.setQna_GroupNum(rs.getInt("qna_GroupNum"));
				dto.setQna_Depth(rs.getInt("qna_Depth"));
				dto.setQna_Privacy(rs.getInt("qna_Privacy"));
				dto.setQna_Check(rs.getInt("qna_Check"));
				dto.setQna_OrderNum(rs.getInt("qna_OrderNum"));
				dto.setQna_Date(rs.getString("qna_Date"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				
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
	
	public QnaDTO readQna(int qna_Num) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT qna_Num, q.user_Id, user_Name, qna_Subject, qna_Content, qna_Date, qna_GroupNum, " 
					+ "    qna_Depth, qna_OrderNum, qna_Parent, qna_Privacy, qna_Check, q.product_Num, product_Name "
					+ " FROM qna q "
					+ " JOIN member m ON q.user_Id=m.user_Id "
					+ " JOIN product p ON q.product_Num = p.product_Num "
					+ " WHERE qna_Num = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qna_Num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
				dto.setQna_Num(rs.getInt("qna_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setQna_Subject(rs.getString("qna_Subject"));
				dto.setQna_Content(rs.getString("qna_Content"));
				dto.setQna_Date(rs.getString("qna_Date"));
				dto.setQna_GroupNum(rs.getInt("qna_GroupNum"));
				dto.setQna_Depth(rs.getInt("qna_Depth"));
				dto.setQna_OrderNum(rs.getInt("qna_OrderNum"));
				dto.setQna_Parent(rs.getInt("qna_Parent"));
				dto.setQna_Privacy(rs.getInt("qna_Privacy"));
				dto.setQna_Check(rs.getInt("qna_Check"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));

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
	
	public void updateQna(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qna SET qna_Subject=?, qna_Content=?, product_Num=?, qna_Privacy=? "
					+ " WHERE qna_Num=? AND user_Id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQna_Subject());
			pstmt.setString(2, dto.getQna_Content());
			pstmt.setInt(3, dto.getProduct_Num());
			pstmt.setInt(4, dto.getQna_Privacy());
			pstmt.setInt(5, dto.getQna_Num());
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
	
	public void deleteQna(int qna_Num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM qna WHERE qna_Num IN " 
					+ " (SELECT qna_Num FROM qna " 
					+ " START WITH qna_Num = ? "
					+ " CONNECT BY PRIOR qna_Num = qna_Parent)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qna_Num);
			
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
	
	
	public List<QnaDTO> listProduct() {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT product_Num, product_Name ");
			sb.append(" FROM product ");
			sb.append(" ORDER BY product_Num DESC");

			pstmt = conn.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));

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
