package com.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class OrderDAO {
	private Connection conn = DBConn.getConnection();

	public List<OrderDTO> getOrderList(String id) {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT od.order_Num, od.product_Num, p.product_Name, orderDetail_Quant, orderDetail_Price, orderDetail_Date "
				+ " FROM OrderDetail od"
				+ " JOIN Product p ON od.product_Num = p.product_Num "
				+ " JOIN Order1 o ON od.order_Num = o.order_Num "
				+ " JOIN Member m ON m.user_Id = o.user_Id"
				+ " WHERE m.user_Id = ?"; // 상품이름 조인
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderDTO dto = new OrderDTO();
				dto.setOrder_Num(rs.getInt("order_Num"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setOrderDetail_Quant(rs.getInt("orderDetail_Quant"));
				dto.setOrderDetail_Price(rs.getInt("orderDetail_Price"));
				dto.setOrderDetail_Date(rs.getString("orderDetail_Date"));
				
				orderList.add(dto);
			}
		} catch (Exception e) {
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
		
		return orderList;
	}
	
	// 전체 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM OrderDetail";
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
	
	
}
