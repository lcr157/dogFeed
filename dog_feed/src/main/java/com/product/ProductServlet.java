package com.product;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.MemberDAO;
import com.member.MemberDTO;
import com.util.MyServlet;

@WebServlet("/product/*")
public class ProductServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		if(uri.indexOf("insert.do") != -1) {
			ProductInsert(req, resp);
		}
		
	}

	
	// 상품등록
	protected void ProductInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		/*
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			
			ProductDTO dto = new ProductDTO();
			dto.setUser_Id(req.getParameter("user_Id"));
			dto.setUser_Pwd(req.getParameter("user_Pwd"));
			dto.setUser_Name(req.getParameter("user_Name"));

			String user_Birth = req.getParameter("user_Birth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setUser_Birth(user_Birth);

			String user_Email2 = req.getParameter("user_Email2");
			String user_Email3 = req.getParameter("user_Email3");
			dto.setUser_Email1(user_Email2 + "@" + user_Email3);

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setTel(tel1 + "-" + tel2 + "-" + tel3);

			dto.setUser_Address1(req.getParameter("user_Address1"));
			dto.setUser_Address2(req.getParameter("user_Address2"));

			dao.insertProduct(dto);
			resp.sendRedirect(cp + "/");
			return;
			
		} catch (SQLException e) {
			if (e.getErrorCode() == 1)
				message = "아이디 중복으로 회원 가입이 실패 했습니다.";
			else if (e.getErrorCode() == 1400)
				message = "필수 사항을 입력하지 않았습니다.";
			else if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861)
				message = "날짜 형식이 일치하지 않습니다.";
			else
				message = "회원 가입이 실패 했습니다.";
			// 기타 - 2291:참조키 위반, 12899:폭보다 문자열 입력 값이 큰경우
		} catch (Exception e) {
			message = "회원 가입이 실패 했습니다.";
			e.printStackTrace();
		}
		
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		*/
	}
	
	
	
}
