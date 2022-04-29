package com.sell;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/sell/*")
public class SellServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		}
	}
	
	// http://localhost:9090/dog_feed/sell/list.do
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SellDAO dao = new SellDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체데이터 개수
			int dataCount = dao.dataCount();
			
			// 전체 페이지수
			int rows = 8;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 상품목록 가져올 시작과 끝위치
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 상품목록 가져오기
			List<SellDTO> list = dao.listProduct(start, end);
			
			String listUrl = cp + "/sell/list.do";
			String articleUrl = cp + "/sell/article.do?page=" + current_page;
			String paging = util.pagingUrl(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);;
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/sell/list.jsp");
	}

	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SellDAO dao = new SellDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int product_Num = Integer.parseInt(req.getParameter("product_Num"));
			
			SellDTO dto = dao.readSell(product_Num);
			if(dto == null) {
				resp.sendRedirect(cp + "/sell/list.do?page=" + page);
				return;
			}
			
			List<SellDTO> listImage = dao.listImage(product_Num);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listImage", listImage);
			
			forward(req, resp, "/WEB-INF/views/sell/article.jsp");
			return;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/sell/list.do?page=" + page);
	}

}
