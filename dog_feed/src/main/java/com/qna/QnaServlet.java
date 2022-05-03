package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

				
		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("reply.do") != -1) {
			replyForm(req, resp);
		} else if(uri.indexOf("reply_ok.do") != -1) {
			replySubmit(req, resp);			
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
		
	}
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}

			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			int rows = 10;
			int total_page = util.pageCount(rows, dataCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;

			List<QnaDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listQna(start, end);
			} else {
				list = dao.listQna(start, end, condition, keyword);
			}

			int listNum, n = 0;
			for (QnaDTO dto : list) {
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				n++;
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			String listUrl = cp + "/qna/list.do";
			String articleUrl = cp + "/qna/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}	
		
		QnaDAO dao = new QnaDAO();
		List<QnaDTO> listProduct = dao.listProduct();
		
		req.setAttribute("mode", "write");
		req.setAttribute("listProduct", listProduct);
		forward(req, resp, "/WEB-INF/views/qna/write.jsp");
	}
	
	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}
		
		try {
			QnaDTO dto = new QnaDTO();

			dto.setUser_Id(info.getUserId());

			dto.setQna_Subject(req.getParameter("qna_Subject"));
			dto.setQna_Content(req.getParameter("qna_Content"));
			dto.setProduct_Num(Integer.parseInt(req.getParameter("product_Num")));
			if(req.getParameter("qna_Privacy")!=null) {
				dto.setQna_Privacy(Integer.parseInt(req.getParameter("qna_Privacy")));
			}

			dao.insertQna(dto, "write");
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do");
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page=" + page;

		try {
			int qna_Num = Integer.parseInt(req.getParameter("qna_Num"));
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

			QnaDTO dto = dao.readQna(qna_Num);
						
			if (dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?" + query);
				return;
			}
			dto.setQna_Content(util.htmlSymbols(dto.getQna_Content()));

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);

			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?" + query);
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			int qna_Num = Integer.parseInt(req.getParameter("qna_Num"));
			QnaDTO dto = dao.readQna(qna_Num);

			if (dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}

			if (! dto.getUser_Id().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}
			
			List<QnaDTO> listProduct = dao.listProduct();
			
			req.setAttribute("dto", dto);
			req.setAttribute("listProduct", listProduct);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?page=" + page);
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			QnaDTO dto = new QnaDTO();
			dto.setQna_Num(Integer.parseInt(req.getParameter("qna_Num")));
			dto.setQna_Subject(req.getParameter("qna_Subject"));
			dto.setQna_Content(req.getParameter("qna_Content"));
			dto.setProduct_Num(Integer.parseInt(req.getParameter("product_Num")));
			if(req.getParameter("qna_Privacy")!=null) {
				dto.setQna_Privacy(Integer.parseInt(req.getParameter("qna_Privacy")));
			}
			
			dto.setUser_Id(info.getUserId());

			dao.updateQna(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?page=" + page);
	}
	
	private void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			int qna_Num = Integer.parseInt(req.getParameter("qna_Num"));
			
			QnaDTO dto = dao.readQna(qna_Num);
			if(dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?page="+page);
				return;
			}
			
			String s = "[" + dto.getQna_Subject() + "] 에 대한 답변입니다.\n";
			dto.setQna_Content(s);
			
			List<QnaDTO> listProduct = dao.listProduct();
			
			req.setAttribute("listProduct", listProduct);
			req.setAttribute("mode", "reply");
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
		
			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/qna/list.do?page="+page);
	}
	
	private void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setQna_Subject(req.getParameter("qna_Subject"));
			dto.setQna_Content(req.getParameter("qna_Content"));
			
			dto.setQna_GroupNum(Integer.parseInt(req.getParameter("qna_GroupNum")));
			dto.setQna_OrderNum(Integer.parseInt(req.getParameter("qna_OrderNum")));
			dto.setQna_Depth(Integer.parseInt(req.getParameter("qna_Depth")));
			dto.setQna_Parent(Integer.parseInt(req.getParameter("qna_Parent")));
			dto.setProduct_Num(Integer.parseInt(req.getParameter("product_Num")));
			
			
			
			
			dto.setUser_Id(info.getUserId());
			
			dao.insertQna(dto, "reply");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/qna/list.do?page=" + page);		
	}
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int qna_Num = Integer.parseInt(req.getParameter("qna_Num"));
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

			QnaDTO dto=dao.readQna(qna_Num);
			
			if(dto==null) {
				resp.sendRedirect(cp + "/qna/list.do?" + query);
				return;
			}
			
			if(! dto.getUser_Id().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/qna/list.do?" + query);
				return;
			}
			
			dao.deleteQna(qna_Num);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?" + query);
	}
	
}
