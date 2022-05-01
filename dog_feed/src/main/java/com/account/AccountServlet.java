package com.account;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/account/*")
public class AccountServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		// 로그인안될시 로그인 창
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("account.do") != -1) {
			account(req, resp); // 전체 보기
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp); // 가계부 작성
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp); // 가계부 등록
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp); // 가계부 수정
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp); // 가계부 수정 완료
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp); // 가계부 삭제
		}

	}
	
	protected void account(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가계부 리스트
		try {
			AccountDAO dao = new AccountDAO();
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			List<AccountDTO> accountList = dao.listAccount(info.getUserId());
			
			req.setAttribute("accountList", accountList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/account/account.jsp");
		
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가게부 쓰기 폼
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/account/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가계부 저장
		AccountDAO dao = new AccountDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContentType();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		try {
			AccountDTO dto = new AccountDTO();
			
			dto.setUser_Id(info.getUserId());
			
			dto.setAccountBook_Num(Integer.parseInt(req.getParameter("accountBook_Num")));
			dto.setAccountBook_Date(req.getParameter("accountBook_Date"));
			dto.setContent(req.getParameter("content"));
			dto.setAmount(Integer.parseInt(req.getParameter("content")));
			dto.setMemo(req.getParameter("memo"));
			
			dao.insertAccount(dto, "write");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/account/account.do");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가계부 수정
		AccountDAO dao = new AccountDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			int accountBook_Num = Integer.parseInt(req.getParameter("accountBook_Num"));
			AccountDTO dto = dao.readAccount(accountBook_Num);
			
			if(dto == null) {
				resp.sendRedirect(cp+"/account/account.do?page=" + page);
				return;
			}
			
			// 게시물을 올린 사용자가 아니면
			if (! dto.getUser_Id().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/account/account.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/account/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/account/account.do?page=" + page);
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가계부 수정 완료
		AccountDAO dao = new AccountDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/account/account.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			AccountDTO dto = new AccountDTO();
			dto.setAccountBook_Num(Integer.parseInt(req.getParameter("accountBook_Num")));
			dto.setAccountBook_Date(req.getParameter("accountBook_Date"));
			dto.setContent(req.getParameter("content"));
			dto.setAmount(Integer.parseInt(req.getParameter("amount")));
			dto.setMemo(req.getParameter("memo"));
			
			dto.setUser_Id(info.getUserId());
			
			dao.updateAccount(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/account/account.do?page=" + page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 가계부 삭제
		AccountDAO dao = new AccountDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			int accountBook_Num = Integer.parseInt(req.getParameter("accountBook_Num"));
			
			AccountDTO dto = dao.readAccount(accountBook_Num);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/account/account.do?"+ query);
				return;
			}
			
			// 게시물을 올린 사용자나 admin이 아니면
			if(! dto.getUser_Id().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/account/list.do?" + query);
				return;
			}
			
			dao.deleteAccount(accountBook_Num, info.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/account/account.do?" + query);
		
	}
	
}
