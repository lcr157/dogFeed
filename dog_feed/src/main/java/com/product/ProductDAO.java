package com.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class ProductDAO {
	private Connection conn = DBConn.getConnection();
	
	// 상품등록
	public void insertProduct(ProductDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "INSERT INTO member(user_Id, user_Pwd, user_Name, user_Birth, user_Email, user_Tel, "
					+ " user_Address1, user_Address2) VALUES (?, ?, ?, TO_DATE(?,'YYYYMMDD'), ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			//pstmt.setString(1, dto.getUser_Id());
			//pstmt.setString(2, dto.getUser_Pwd());
			
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
	
	
	
}
