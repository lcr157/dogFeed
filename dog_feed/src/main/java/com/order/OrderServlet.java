package com.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/order/*")
public class OrderServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		// 로그인안될시 로그인 창
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if (uri.indexOf("order.do") != -1) {
			order(req, resp);
		}
		
	}
	
	private void order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문정보
		try {
			OrderDAO dao = new OrderDAO();
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			List<OrderDTO> orderList = dao.getOrderList(info.getUserId());
			
			req.setAttribute("orderList", orderList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/order/order.jsp");
		
	}
	
}
