package com.sell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SellDAO {
	private Connection conn = DBConn.getConnection();
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM product";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
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
		
		return result;	
	}
	
	public List<SellDTO> listProduct(int start, int end) {
		List<SellDTO> list = new ArrayList<SellDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT p.product_Num, p.category_Num, product_Name, product_Price, categoryDetail_Name, categoryDetail_Kind, image_Name, product_Hits ");
			sb.append("			FROM product p ");
			sb.append("         JOIN CategoryDetail c ON p.category_Num = c.category_Num ");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             SELECT image_Num, product_Num, image_Name FROM ( ");
			sb.append("                 SELECT image_Num, product_Num, image_Name, ");
			sb.append("                       ROW_NUMBER() OVER(PARTITION BY product_Num ORDER BY image_Num ASC) rank ");
			sb.append("                 FROM ProductImage");
			sb.append("             ) WHERE rank = 1 ");
			sb.append("         ) i ON p.product_Num = i.product_Num ");			
			sb.append("			ORDER BY product_Hits DESC ");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				SellDTO dto = new SellDTO();
				
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setCategory_Num(rs.getInt("Category_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setCategoryDetail_Kind(rs.getString("categoryDetail_Kind"));
				dto.setCategoryDetail_Name(rs.getString("categoryDetail_Name"));
				dto.setImage_Name(rs.getString("image_Name"));
				dto.setProduct_Hits(rs.getInt("product_Hits"));
				
				list.add(dto);
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

		return list;	
	}
	
	public SellDTO readSell(int product_Num) {
		SellDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT p.product_Num, p.category_Num, product_Name, product_Price, categoryDetail_Name, categoryDetail_Kind, product_Hits "
				+ " FROM product p "
				+ " JOIN CategoryDetail c ON p.category_Num = c.category_Num "
				+ " WHERE product_Num = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product_Num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SellDTO();
				
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setCategory_Num(rs.getInt("category_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setCategoryDetail_Name(rs.getString("categoryDetail_Name"));
				dto.setCategoryDetail_Kind(rs.getString("categoryDetail_Kind"));
				dto.setProduct_Hits(rs.getInt("product_Hits"));
		
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
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	public List<SellDTO> listImage(int product_Num) {
		List<SellDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT image_Num, product_Num, image_Name FROM ProductImage WHERE product_Num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product_Num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SellDTO dto = new SellDTO();
				
				dto.setImage_Num(rs.getInt("image_Num"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setImage_Name(rs.getString("image_Name"));
				
				list.add(dto);
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
		}
		
		return list;
	}
}