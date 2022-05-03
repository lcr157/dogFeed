package com.admin;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.order.OrderDAO;
import com.order.OrderDTO;
import com.product.ProductDAO;
import com.product.ProductDTO;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/admin/*")
public class AdminServlet2 extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// 세션 정보 -> 관리자가 아니면 다시 로그인해야됨
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if ( info == null ) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		
		// 이미지 파일 저장 경로
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "management";
		
		
		// uri에 따른 작업구분
		// 상품 관리
		if(uri.indexOf("management.do") != -1) {
			managementForm(req, resp);
		}
		// 상품 정보
		else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		}
		// 상품 등록
		else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		}
		// 상품 등록완료
		else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		}
		// 상품 수정
		else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}
		// 상품 수정완료
		else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}
		// 상품 삭제
		else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} 
		// 상품 이미지 삭제
		else if (uri.indexOf("deleteFile") != -1) {
			deleteFile(req, resp);
		}
		
		
		// 판매현황
		else if(uri.indexOf("salesStatus.do") != -1) {
			salesStatusForm(req, resp);
		}
		
		
	}
	
	
	// 상품관리
	protected void managementForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리스트
		ProductDAO dao = new ProductDAO();
		MyUtil myUtil = new MyUtil();
		String cp = req.getContextPath();
		
		try {
			// 현재 페이지 1로 시작
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			
			// 검색 변수들 (condition : 검색종류, keyword : 검색명)
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// GET방식이면 -> 디코딩으로 받기(한글인경우도있음)
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			
			int rows = 4;
			int dataCount=0, total_page;
			
			// 검색인 경우 -> condition과 keyword 넣어주기
			if(keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}

			total_page = myUtil.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			
			// 시작번호와 끝번호 설정
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 리스트 가져오기
			List<ProductDTO> list = null;
			if(keyword.length() == 0) {
				list = dao.listProduct(start, end);
			} else {
				list = dao.listProduct(start, end, condition, keyword);
			}
						
			// 게시판 게시글의 순서 변수
			// 현재시간 : curDate, 등록시간 : date
			int listNum, n=0;
			long gap;
			Date curDate = new Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// 게시판 게시글의 순서 변수
			for(ProductDTO dto : list) {
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				
				// String -> java.util.Date로 변환
				Date date = sdf.parse(dto.getProduct_Date());
				gap = (curDate.getTime() - date.getTime()) / (1000*60*60);
				dto.setGap(gap);
				
				// 년월일로 시간을 짤라냈음
				dto.setProduct_Date(dto.getProduct_Date().substring(0, 10));
				
				n++;
			}
			
			String query = "";
			
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			String listUrl, articleUrl;
			
			listUrl = cp + "/admin/management.do";
			articleUrl = cp + "/admin/article.do?page=" + current_page;
			
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = myUtil.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/admin2/management.jsp");
		
	}
	
	
	// 상품등록 폼
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/admin2/write.jsp");
	}
	
	
	// 상품등록 완료
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
				
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			ProductDTO dto = new ProductDTO();
			
			dto.setProduct_Name(req.getParameter("product_Name"));
			dto.setProduct_Price(Integer.parseInt(req.getParameter("product_Price")));
			dto.setProduct_Info(req.getParameter("product_Info"));
			dto.setProduct_Privacy(Integer.parseInt(req.getParameter("product_Privacy")));
			dto.setCategory_Num(Integer.parseInt(req.getParameter("category_Num")));
			
			// 상세카테고리이름과 상세카테고리종류를 정확히 명시해준다.
			String name = req.getParameter("category_Num");
			String kind = req.getParameter("categoryDetail_kind");
			if(req.getParameter("category_Num").equals("1")) {
				name = "사료";
				kind = "소프트";
			} else if(req.getParameter("category_Num").equals("2")) {
				name = "사료";
				kind = "하드";
			} else if(req.getParameter("category_Num").equals("3")) {
				name = "간식";
				kind = "건식";
			} else if(req.getParameter("category_Num").equals("4")) {
				name = "간식";
				kind = "껌";
			}
			
			dto.setCategoryDetail_Name(name);
			dto.setCategoryDetail_Kind(kind);
			
			// 사진 저장
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			// 상품등록
			dao.insertProduct(dto);
			
			resp.sendRedirect(cp + "/admin/management.do");
			return;
			
		} catch (Exception e) {
			message = "실패 했습니다.";
			e.printStackTrace();
		}
		
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/views/admin2/management.jsp");
	}
		
	
	// 게시글 보기
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int num = Integer.parseInt(req.getParameter("num"));
			
			// 게시물 가져오기
			ProductDTO dto = dao.readProdcuct(num);
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/admin/management.do?" + query);
				return;
			}
			dto.setProduct_Info(dto.getProduct_Info().replaceAll("\n", "<br>"));
			dto.setProduct_Info(util.htmlSymbols(dto.getProduct_Info()));
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			// 조회수 증가
			dao.updateHitCount(num);
			
			// 이전글 다음글
			ProductDTO preReadDto = dao.preReadProdcuct(dto.getProduct_Num(), condition, keyword);
			ProductDTO nextReadDto = dao.nextReadProdcuct(dto.getProduct_Num(), condition, keyword);
			
			// 이미지 파일리스트
			List<ProductDTO> listFile = dao.listPhotoFile(num);

			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("listFile", listFile);
			
			// 포워딩
			forward(req, resp, "/WEB-INF/views/admin2/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/management.do?" + query);
	}
		
	
	// 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int num = Integer.parseInt(req.getParameter("num"));
			ProductDTO dto = dao.readProdcuct(num);
			if(dto == null) {
				resp.sendRedirect(cp+"admin/management.do?page="+page);
				return;
			}
			
			// 관리자가 아니라면 -> 상품관리로 이동
			if(! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"admin/management.do?page="+page);
				return;
			}
			
			List<ProductDTO> listFile = dao.listPhotoFile(num);

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listFile", listFile);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/admin2/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/admin/management.do?page="+page);
		
	}
	
	
	// 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/admin/management.do");
			return;
		}
		String page = req.getParameter("page");
		
		
		try {
			ProductDTO dto = new ProductDTO();
			
			dto.setProduct_Name(req.getParameter("product_Name"));
			dto.setCategory_Num(Integer.parseInt(req.getParameter("category_Num")));
			dto.setProduct_Price(Integer.parseInt(req.getParameter("product_Price")));
			dto.setProduct_Info(req.getParameter("product_Info"));
			dto.setProduct_Privacy(Integer.parseInt(req.getParameter("product_Privacy")));
			dto.setProduct_Num(Integer.parseInt(req.getParameter("num")));
						
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
			}
			
			dao.updateBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/management.do?page=" + page);
	}
	
	
	// 삭제
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			int num = Integer.parseInt(req.getParameter("num"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			
			if(info.getUserId().equals("admin")) {
				// 이미지 파일 지우기 후에 테이블 데이터 삭제
				List<ProductDTO> listFile = dao.listPhotoFile(num);
				for (ProductDTO vo : listFile) {
					FileManager.doFiledelete(pathname, vo.getImage_Name());
				}
				dao.deletePhotoFile("all", num);
				dao.deleteProduct(num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/management.do?" + query);
	}
	
	
	// 상품 이미지 삭제
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");

		try {
			int num = Integer.parseInt(req.getParameter("num"));
			int image_Num = Integer.parseInt(req.getParameter("image_Num"));
			
			ProductDTO dto = dao.readProdcuct(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/admin/management.do?page=" + page);
				return;
			}

			if (!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/admin/management.do?page=" + page);
				return;
			}
			
			
			ProductDTO vo = dao.readPhotoFile(image_Num);
			if(vo != null) {
				FileManager.doFiledelete(pathname, vo.getImage_Name());
				
				dao.deletePhotoFile("one", image_Num);
			}

			resp.sendRedirect(cp + "/admin/update.do?num=" + num + "&page=" + page);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/admin/management.do?page=" + page);
	}
	
	
	
	// 판매현황
	protected void salesStatusForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderDAO dao = new OrderDAO();
		
		try {
			// 리스트 가져오기
			List<OrderDTO> list = null;
			list = dao.OrderList();
			
			int dataCount=0;
			dataCount = dao.dataCount();
			
			// 년월일로 시간을 짤라냈음
			//for(OrderDTO dto : list) {
				//dto.setOrderDetail_Date(dto.getOrderDetail_Date().substring(0, 10));
			//}
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/admin2/salesStatus.jsp");
	}	
	
	
}
