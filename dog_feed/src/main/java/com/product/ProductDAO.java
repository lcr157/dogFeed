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
		ResultSet rs = null;
		String sql;
		
		try {
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

			pstmt.close();
			pstmt = null;
			
			if(dto.getImageFiles() != null) {
				sql = "INSERT INTO ProductImage(image_Num, image_Name, product_Num) "
						+ " VALUES(productImage_seq.NEXTVAL, ?, product_seq.CURRVAL) ";
				pstmt = conn.prepareStatement(sql);
				
				for(int i=0; i<dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.executeUpdate();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		
	
	// 조회수 증가하기
	public void updateHitCount(int num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE Product SET product_Hits=product_Hits+1 WHERE product_Num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
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

	
	// 상품의 이미지 가져오기
	public List<ProductDTO> listPhotoFile(int num) {
		List<ProductDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT image_Num, product_Num, image_Name FROM ProductImage WHERE product_Num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setImage_Num(rs.getInt("image_Num"));
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setImage_Name(rs.getString("image_Name"));
				
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
	
	
	//  이미지번호를 통한 이미지 가져오기
	public ProductDTO readPhotoFile(int image_Num) {
		ProductDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT image_Num, image_Name, product_Num FROM ProductImage WHERE image_Num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, image_Num);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();

				dto.setImage_Num(rs.getInt("image_Num"));
				dto.setImage_Name(rs.getString("image_Name"));
				dto.setProduct_Num(rs.getInt("product_Num"));
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
	
	
	// 해당 게시물 보기
	public ProductDTO readProdcuct(int num) {
		ProductDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT product_Num, product_Name, product_Info, product_Date, product_Hits, "
					+ " product_Price, product_Privacy, categoryDetail_Name, categoryDetail_Kind "
					+ " FROM Product p "
					+ " JOIN CategoryDetail c ON p.category_Num = c.category_Num "
					+ " WHERE product_Num = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();
				
				dto.setProduct_Num(rs.getInt("product_Num"));
				dto.setProduct_Name(rs.getString("product_Name"));
				dto.setProduct_Info(rs.getString("product_Info"));
				dto.setProduct_Date(rs.getString("product_Date"));
				dto.setProduct_Hits(rs.getInt("product_Hits"));
				dto.setProduct_Price(rs.getInt("product_Price"));
				dto.setProduct_Privacy(rs.getInt("product_Privacy"));
				dto.setCategoryDetail_Name(rs.getString("categoryDetail_Name"));
				dto.setCategoryDetail_Kind(rs.getString("categoryDetail_Kind"));
								
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
	
	
	// 이전글
	public ProductDTO preReadProdcuct(int num, String condition, String keyword) {
		ProductDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT product_Num, product_Name, categoryDetail_Name, categoryDetail_Kind ");
				sb.append("    FROM Product p ");
				sb.append("    JOIN CategoryDetail c ON p.category_Num = c.category_Num ");
				sb.append("    WHERE product_Num > ? ");

				// 상품명+내용인 경우 : product_Name, product_Info
				if(condition.equals("all")) {
					sb.append(" AND (INSTR(product_Name, ?) >= 1 OR INSTR(product_Info, ?) >= 1) ");
				}
				
				// 등록일인 경우 : product_Date
				else if(condition.equals("product_Date")) {
					keyword = keyword.replaceAll("(\\-|\\.|\\/.)", "");
					sb.append(" AND TO_CHAR(product_Date, 'YYYYMMDD') = ? ");
				}
				
				// 나머지의 경우
				else {
					sb.append(" AND INSTR(" + condition + ", ?) >= 1 ");
				}
				
				sb.append("          ORDER BY product_Num ASC ");
				sb.append("       ) WHERE ROWNUM = 1 ");
			
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT product_Num, product_Name FROM Product ");
				sb.append("     WHERE product_Num > ? ");
				sb.append("     ORDER BY product_Num ASC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();
				
				dto.setProduct_Num(rs.getInt("Product_Num"));
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

	
	// 다음글
	public ProductDTO nextReadProdcuct(int num, String condition, String keyword) {
		ProductDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT product_Num, c.category_Num, product_Name, categoryDetail_Name, categoryDetail_Kind ");
				sb.append("    FROM Product p ");
				sb.append("    JOIN CategoryDetail c ON p.category_Num = c.category_Num ");
				sb.append("    WHERE product_Num < ? ");

				// 상품명+내용인 경우 : product_Name, product_Info
				if(condition.equals("all")) {
					sb.append(" AND (INSTR(product_Name, ?) >= 1 OR INSTR(product_Info, ?) >= 1) ");
				}
				
				// 등록일인 경우 : product_Date
				else if(condition.equals("product_Date")) {
					keyword = keyword.replaceAll("(\\-|\\.|\\/.)", "");
					sb.append(" AND TO_CHAR(product_Date, 'YYYYMMDD') = ? ");
				}
				
				// 나머지의 경우
				else {
					sb.append(" AND INSTR(" + condition + ", ?) >= 1 ");
				}
				
				sb.append("          ORDER BY product_Num DESC ");
				sb.append("       ) WHERE ROWNUM = 1 ");
			
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT product_Num, product_Name FROM Product ");
				sb.append("     WHERE product_Num < ? ");
				sb.append("     ORDER BY product_Num DESC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();
				
				dto.setProduct_Num(rs.getInt("Product_Num"));
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
	
	
	// 게시물 수정
	public void updateBoard(ProductDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE Product SET product_Name=?, category_Num=?, product_Price=?, "
					+ " product_Info=?, product_Privacy=? "
					+ " WHERE product_Num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getProduct_Name());
			pstmt.setInt(2, dto.getCategory_Num());
			pstmt.setInt(3, dto.getProduct_Price());
			pstmt.setString(4, dto.getProduct_Info());
			pstmt.setInt(5, dto.getProduct_Privacy());
			pstmt.setInt(6, dto.getProduct_Num());
			
			pstmt.executeUpdate();

			
			pstmt.close();
			pstmt = null;

			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO ProductImage(image_Num, image_Name, product_Num)"
						+ " VALUES(productImage_seq.NEXTVAL, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setString(1, dto.getImageFiles()[i]);
					pstmt.setInt(2, dto.getProduct_Num());
					pstmt.executeUpdate();
				}
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
	
	
	// 상품 이미지 삭제
	public void deletePhotoFile(String mode, int num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM ProductImage WHERE product_Num = ?";
			} else {
				sql = "DELETE FROM ProductImage WHERE image_Num = ?";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);

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
	
	
	// 상품 삭제
	public void deleteProduct(int num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			// 관리자일경우만 삭제가능
			sql = "DELETE FROM Product WHERE product_Num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
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
