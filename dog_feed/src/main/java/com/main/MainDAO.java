package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	// 상품에서 1장만 사진 뽑아오기
	public MainDTO first_Image(int start, int end, int product_Num) {
		MainDTO dto=null;
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
				+ " 		WHERE p.product_Num = ?	"
				+ "			ORDER BY product_date DESC "
				+ "		) tb WHERE ROWNUM <= 8  "
				+ ") WHERE rnum >= 1 ";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_Num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setProduct_Info(rs.getString("product_Info"));
				dto.setProduct_Date(rs.getString("product_Date"));
				dto.setImage_Num(rs.getInt("image_Num")); 
				dto.setImage_Name(rs.getString("image_Name"));				
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
		
		return dto;
	}
	
	public MainDTO readProduct(int product_Num) {
		MainDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT  product_Num, product_Name, product_Info, product_Price "
					+ " FROM product WHERE product_Num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product_Num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new MainDTO();
				
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Info(rs.getString("product_Info"));
				dto.setProduct_Price(rs.getInt("product_Price"));				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		
		return dto;
	}
	
	public List<MainDTO> readPhotoFile(int product_Num) {
		List<MainDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql  = "SELECT product_Num,image_Name, image_Num FROM productImage WHERE product_Num = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_Num);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				MainDTO dto = new MainDTO();
				
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setImage_Name(rs.getString("image_Name"));
				dto.setImage_Num(rs.getInt("image_Num"));
				
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
	
	public void product_order(ProductOrderDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "INSERT INTO Order1( order_Num, order_Price, order_Date, user_Id) "
					+ " VALUES ( order1_seq.NEXTVAL , ?, SYSDATE, ? )";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getOrder_Price());
			pstmt.setString(2, dto.getUser_Id());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			
			sql = "INSERT INTO OrderDetail (order_Num, product_Num, orderDetail_Quant, orderDetail_Price, orderDetail_Date)"
					+ " VALUES ( order1_seq.CURRVAL, ?, ?, ?, SYSDATE) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getProduct_Num());
			pstmt.setInt(2, dto.getOrderDetail_Quant());
			pstmt.setInt(3, dto.getOrderDetail_Price());

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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
		}
		
	}
	
	// 데이터 개수세기
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM product";
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

	
}
