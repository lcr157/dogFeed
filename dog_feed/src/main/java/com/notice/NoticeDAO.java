package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {		
			sql = "INSERT INTO notice(notice_Num, user_Id, notice_Subject, notice_Content, notice_Hits, notice_Date) "
					+ " VALUES (notice_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE )";
			
			pstmt = conn.prepareStatement(sql);

			
			pstmt.setString(1, dto.getUser_Id());
			pstmt.setString(2, dto.getNotice_Subject());
			pstmt.setString(3, dto.getNotice_Content());

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
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Notice";

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
			sql = "SELECT NVL(COUNT(*), 0) FROM Notice n JOIN Member m ON n.user_Id = m.user_Id ";
			if (condition.equals("all")) {
				sql += " WHERE INSTR(notice_Subject, ?) >= 1 OR INSTR(notice_Content, ?) >= 1 ";
			} else if (condition.equals("notice_Date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(notice_Date, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
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
	
	public List<NoticeDTO> listNotice(int start, int end) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT notice_Num, notice_Subject, user_Name, notice_Hits, ");
			sb.append("               TO_CHAR(notice_Date, 'YYYY-MM-DD') notice_Date ");
			sb.append("		FROM notice n ");
			sb.append("		JOIN Member m ON n.user_Id=m.user_Id ");
			sb.append("		ORDER BY notice_Num DESC ");
			sb.append("	) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setNotice_Num(rs.getInt("notice_Num"));
				dto.setNotice_Subject(rs.getString("notice_Subject"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setNotice_Hits(rs.getInt("notice_Hits"));
				dto.setNotice_Date(rs.getString("notice_Date"));

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
	
	public List<NoticeDTO> listNotice(int start, int end, String condition, String keyword) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT notice_Num, notice_Subject, user_Name, notice_Hits, ");
			sb.append("               TO_CHAR(notice_Date, 'YYYY-MM-DD') notice_Date ");
			sb.append("		FROM Notice n");
			sb.append("		JOIN Member m ON n.user_Id=m.user_Id ");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(notice_Subject, ?) >= 1 OR INSTR(notice_Content, ?) >= 1 ");
			} else if (condition.equals("notice_Date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(notice_Date, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("			ORDER BY notice_Num DESC ");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ?");

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
				NoticeDTO dto = new NoticeDTO();
				dto.setNotice_Num(rs.getInt("notice_Num"));
				dto.setNotice_Subject(rs.getString("notice_Subject"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setNotice_Hits(rs.getInt("notice_Hits"));
				dto.setNotice_Date(rs.getString("notice_Date"));

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
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}
	
	public void updateHitCount(int notice_Num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE Notice SET notice_Hits=notice_Hits+1 WHERE notice_Num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, notice_Num);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

	}
	
	public NoticeDTO readNotice(int notice_Num) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT notice_Num, n.user_Id, user_Name, notice_Subject, notice_Content, notice_Date, notice_Hits "
					+ " FROM Notice n "
					+ " JOIN member m ON n.user_Id=m.user_Id "
					+ " WHERE notice_Num = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, notice_Num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNotice_Num(rs.getInt("notice_Num"));
				dto.setUser_Id(rs.getString("user_Id"));
				dto.setUser_Name(rs.getString("user_Name"));
				dto.setNotice_Subject(rs.getString("notice_Subject"));
				dto.setNotice_Content(rs.getString("notice_Content"));
				dto.setNotice_Date(rs.getString("notice_Date"));
				dto.setNotice_Hits(rs.getInt("notice_Hits"));
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
	
	public void updateNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE Notice SET notice_Subject=?, notice_Content=? WHERE notice_Num=? AND user_Id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getNotice_Subject());
			pstmt.setString(2, dto.getNotice_Content());
			pstmt.setInt(3, dto.getNotice_Num());
			pstmt.setString(4, dto.getUser_Id());
			
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

	// 게시물 삭제
	public void deleteNotice(int notice_Num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM Notice WHERE notice_Num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, notice_Num);
			
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
