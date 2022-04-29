package com.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

// 메인화면
@WebServlet("/main/*")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		// 로그인 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		
		if(uri.indexOf("main.do") != -1) {
			mainItemList(req, resp);
			// forward(req, resp, "/WEB-INF/views/main/main.jsp");
		} else if (uri.indexOf("product_order.do") != -1 ){
			product_order(req, resp);
		}
	}
	
	   protected void mainItemList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   // 상품 리스트
		   MainDAO dao = new MainDAO();
		   MyUtil util = new MyUtil();
		   String cp = req.getContextPath();
		   
		   try {
			   List<MainDTO> newList = dao.newItemList();
			   List<MainDTO> todayList = dao.newItemList();

			   // 포워딩할 JSP에 전달할 속성
			   req.setAttribute("newList", newList);

			
		} catch (Exception e) {
	         e.printStackTrace();
		}
		   
		 forward(req, resp, "/WEB-INF/views/main/main.jsp");

	   }
	   
	   protected void product_order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  
			 forward(req, resp, "/WEB-INF/views/product_order/product_order.jsp");

	   }
	   
}
