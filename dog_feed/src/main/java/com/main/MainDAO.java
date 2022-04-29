package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MainDAO {
	private Connection conn = DBConn.getConnection();
	
	// 최근에 등록한 상품 8개
	public List<MainDTO> newItemList() {
		List<MainDTO> newList = new ArrayList<MainDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM ( "
				+ " 	SELECT ROWNUM rnum, tb.* FROM ( "
				+ "  		SELECT p.product_Num, product_Name, product_Info, image_Num, image_Name, product_Price, "
				+ "   			   TO_CHAR(product_Date,'YYYY-MM-DD')product_Date "	
				+ "  		FROM product p  LEFT OUTER JOIN ( "
				+ "     					SELECT image_Num, image_Name, product_num"
				+ "  						FROM ( SELECT image_Num, image_Name, product_num, "
				+ "  						 		ROW_NUMBER() OVER(PARTITION BY product_num ORDER BY image_Num ASC) rank "
				+ "   								 FROM productimage)"
				+ "							WHERE rank =1) "
				+ "			i on p.product_num = i.product_num "
				+ " 		ORDER BY product_date DESC "
				+ "		) tb WHERE ROWNUM <= 8  "
				+ ") WHERE rnum >= 1 ";
				
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MainDTO dto = new MainDTO();
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setProduct_Info(rs.getString("product_Info"));
				dto.setProduct_Date(rs.getString("product_Date"));
				dto.setImage_Num(rs.getInt("image_Num")); 
				dto.setImage_Name(rs.getString("image_Name"));
				
				newList.add(dto);
				
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
		return newList;
	}
	
	public List<MainDTO> userItemList() {
		List<MainDTO> userItemList = new ArrayList<MainDTO>();
		return userItemList;
	}

}
