package com.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/admin/*")
public class AdminServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		// 로그인 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(uri.indexOf("management.do") != -1) {
			managementForm(req, resp);
		}
		
		else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		}
		
		else if(uri.indexOf("salesStatus.do") != -1) {
			salesStatusForm(req, resp);
		}
		
		else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		}
		
		else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		}
		
		else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}
		
		else if(uri.indexOf("delete.do") != -1) {
			deleteFile(req, resp);
		}
	}
	
	
	// 상품관리
	protected void managementForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리스트
		// ProductDAO dao = new ProductDAO();
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
			
			
			int rows = 10;
			int dataCount, total_page;
			
			// 검색인 경우 -> condition과 keyword 넣어주기
			if(keyword.length() == 0) {
				// dataCount = dao.dataCount();
			} else {
				// dataCount = dao.dataCount(condition, keyword);
			}
			
			/*
			total_page = myUtil.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			*/
			
			// 시작번호와 끝번호 설정
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 리스트 가져오기
			// List<ProductDTO> list = null;
			if(keyword.length() == 0) {
				// list = dao.listProduct(start, end);
			} else {
				// list = dao.listProduct(start, end, condition, keyword);
			}
			
			// 공지글
			// List<ProductDTO> listProduct = null;
			// listProduct = dao.listProduct();
			
			
			// 게시판 게시글의 순서 변수
			int listNum, n=0;
			/*
			for(ProductDTO dto : list) {
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				
				// String -> java.util.Date로 변환
				Date date = sdf.parse(dto.getReg_date());
				gap = (curDate.getTime() - date.getTime()) / (1000*60*60);
				dto.setGap(gap);
				
				// 년월일로 시간을 짤라냈음
				dto.setReg_date(dto.getReg_date().substring(0, 10));
				
				n++;
			}
			*/
			
			String query = "";
			String listUrl, articleUrl;
			
			listUrl = cp + "/admin/management.do";
			articleUrl = cp + "/admin/article.do?page=" + current_page;
			
			if(keyword.length() == 0) {
				query += "condition="+condition+"&keyword"+URLEncoder.encode(keyword, "utf-8");listUrl = "?" + query;
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			// String paging = myUtil.paging(current_page, total_page, listUrl);
			
			// req.setAttribute("list", list);
			// req.setAttribute("listProduct", listProduct);		
			req.setAttribute("page", current_page);
			// req.setAttribute("dataCount", dataCount);
			// req.setAttribute("total_page", total_page);
			// req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/admin/management.jsp");
	}
	
	
	// 상품등록 폼
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/admin/write.jsp");
	}
	
	
	// 상품등록 완료
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/notice/list.do");
			return;
		}
		
		
		
		forward(req, resp, "/WEB-INF/views/admin/management.jsp");
	}
		
	// 게시글 보기
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		try {
			// 정상 작동시 article로 이동
			forward(req, resp, "/WEB-INF/views/admin/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/admin/management.do");
	}
		
	// 판매현황
	protected void salesStatusForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/admin/salesStatus.jsp");
	}	
	
	// 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "update");
		forward(req, resp, "/WEB-INF/views/admin/write.jsp");
	}
	
	// 삭제
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		try {
			
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/admin/management.do");
	}
	
}
