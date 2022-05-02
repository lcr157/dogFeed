package com.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.product.ProductDTO;
import com.util.MyServlet;
import com.util.MyUtil;

// 메인화면
@WebServlet("/main/*")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		// 로그인 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "management";
		
		if(uri.indexOf("main.do") != -1) {
			mainItemList(req, resp);
		} else if (uri.indexOf("product_Detail.do") != -1 ){
			product_Detail(req, resp);
		} else if (uri.indexOf("productOrder_ok") != -1) {
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
					   
			   String articleUrl;
			   String num;
			   num = 
			   articleUrl = cp + "/main/product_Detail.do?";
			   

			   // 포워딩할 JSP에 전달할 속성
			   req.setAttribute("newList", newList);
			   req.setAttribute("newList", newList);
			   req.setAttribute("articleUrl", articleUrl);


			
		} catch (Exception e) {
	         e.printStackTrace();
		}
		   
		 forward(req, resp, "/WEB-INF/views/main/main.jsp");

	   }
	   
	   protected void product_Detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   	 MainDAO dao = new MainDAO();	   	 
		   	 
		   	 try {

		   		int num = Integer.parseInt(req.getParameter("num"));
		   		
		   		// 게시물 가져오기
		   		MainDTO dto = dao.readProduct(num);
		   		
		   		List<MainDTO> PhotoList = dao.readPhotoFile(num);
		   		
		   		req.setAttribute("dto", dto);
		   		req.setAttribute("PhotoList", PhotoList);
						   		
			} catch (Exception e) {
				e.printStackTrace();
			}

		   	 forward(req, resp, "/WEB-INF/views/product_order/product_Detail.jsp");

	   }
	   
	   protected void product_order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   	 MainDAO dao = new MainDAO();
		   	 HttpSession session = req.getSession();
		   	 SessionInfo info = (SessionInfo) session.getAttribute("member");
			 String cp = req.getContextPath();
			 
			 if (info == null) { // 로그인되지 않은 경우
					resp.sendRedirect(cp + "/member/login.do");
					return;
				}
			 
			 try {
				 ProductOrderDTO dto = new ProductOrderDTO();
				 
				 dto.setUser_Id(info.getUserId());
				 dto.setOrder_Price(Integer.parseInt(req.getParameter("order_Price")));
				 dto.setOrderDetail_Price(Integer.parseInt(req.getParameter("OrderDetail_Price")));
				 dto.setOrderDetail_Quant(Integer.parseInt(req.getParameter("orderDetail_Quant")));

				 dto.setProduct_Num(Integer.parseInt(req.getParameter("product_Num")));

				 
				 dao.product_order(dto);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 resp.sendRedirect(cp + "/main/main.do");	   
	   }
	   
}
