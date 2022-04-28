package com.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ProductDAO {
	private Connection conn = DBConn.getConnection();
	
	// 상품등록
	public void insertProduct(ProductDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "INSERT INTO CategoryDetail(category_Num, categoryDetail_Name, categoryDetail_Kind) "
					+ " VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getCategory_Num());
			pstmt.setString(2, dto.getCategoryDetail_Name());
			pstmt.setString(3, dto.getCategoryDetail_Kind());
			
			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			sql = "INSERT INTO Product(product_Num, category_Num, product_Name, product_Price, product_Info, "
					+ " product_Date, product_Hits, product_Privacy) "
					+ " VALUES (product_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, 0, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getCategory_Num());
			pstmt.setString(2, dto.getProduct_Name());
			pstmt.setInt(3, dto.getProduct_Price());
			pstmt.setString(4, dto.getProduct_Info());
			pstmt.setInt(5, dto.getProduct_Privacy());
			
			pstmt.executeUpdate();

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
	
	
	// 데이터 개수(검색 안할경우)
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM Product";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	
	// 데이터 개수(검색 할경우)
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM Product p ";
			sql += " JOIN CategoryDetail c ON p.category_Num = c.category_Num ";
			
			// 상품명+내용인 경우 : product_Name, product_Info
			if(condition.equals("all")) {
				sql += "WHERE INSTR(product_Name, ?) >= 1 OR INSTR(product_Info, ?) >= 1 ";
			}
			
			// 등록일인 경우 : product_Date
			else if(condition.equals("product_Date")) {
				keyword = keyword.replaceAll("(\\-|\\.|\\/.)", "");
				sql += "WHERE TO_CHAR(product_Date, 'YYYYMMDD') = ? ";
			}
			
			// 나머지의 경우
			else {
				sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
			}
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	
	// 지정 범위의 리스트 가져오기 (검색 안할경우)
	public List<ProductDTO> listProduct(int start, int end) {
		List<ProductDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("       SELECT product_Num, categoryDetail_Name, categoryDetail_Kind, ");
			sb.append("			 product_Name, product_Price, product_Date, product_Hits, product_Privacy ");
			sb.append(" 		 FROM Product p ");
			sb.append("          JOIN CategoryDetail c ON p.category_Num = c.category_Num ");
			sb.append("          ORDER BY product_Num DESC ");
			sb.append("       )tb WHERE ROWNUM <= ? ");
			sb.append("   )WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setCategoryDetail_Name(rs.getString("categoryDetail_Name"));
				dto.setCategoryDetail_Kind(rs.getString("categoryDetail_Kind"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setProduct_Date(rs.getString("product_Date"));
				dto.setProduct_Hits(rs.getInt("product_Hits"));
				dto.setProduct_Privacy(rs.getInt("product_Privacy"));
								
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
		
	
	// 지정 범위의 리스트 가져오기 (검색 할경우)
	public List<ProductDTO> listProduct(int start, int end, String condition, String keyword) {
		List<ProductDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("       SELECT product_Num, categoryDetail_Name, categoryDetail_Kind, ");
			sb.append("			 product_Name, product_Price, product_Date, product_Hits, product_Privacy ");
			sb.append(" 		 FROM Product p ");
			sb.append("          JOIN CategoryDetail c ON p.category_Num = c.category_Num ");
			
			
			// 상품명+내용인 경우 : product_Name, product_Info
			if(condition.equals("all")) {
				sb.append("WHERE INSTR(product_Name, ?) >= 1 OR INSTR(product_Info, ?) >= 1 ");
			}
			
			// 등록일인 경우 : product_Date
			else if(condition.equals("product_Date")) {
				keyword = keyword.replaceAll("(\\-|\\.|\\/.)", "");
				sb.append("WHERE TO_CHAR(product_Date, 'YYYYMMDD') = ? ");
			}
			
			// 나머지의 경우
			else {
				sb.append("WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			
			
			sb.append("          ORDER BY product_Num DESC ");
			sb.append("       )tb WHERE ROWNUM <= ? ");
			sb.append("   )WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(condition.equals("all")) {
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
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setCategoryDetail_Name(rs.getString("categoryDetail_Name"));
				dto.setCategoryDetail_Kind(rs.getString("categoryDetail_Kind"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setProduct_Date(rs.getString("product_Date"));
				dto.setProduct_Hits(rs.getInt("product_Hits"));
				dto.setProduct_Privacy(rs.getInt("product_Privacy"));
								
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
		
		
		
}
