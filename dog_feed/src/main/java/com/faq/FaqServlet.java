package com.faq;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/faq/*")
public class FaqServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
	
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "faq";
		
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		}
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao = new FaqDAO();
		MyUtil util = new MyUtil();
		

		String cp = req.getContextPath();
		
		try {
		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);
		}
		
		
		int category = 0;
		String faq_Category = req.getParameter("faq_Category");
		if(faq_Category!=null) {
			category = Integer.parseInt(faq_Category);
		}
		
		String keyword = req.getParameter("keyword");
		if (keyword == null) {
			keyword = "";
		}
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount(category);
		} else {
			dataCount = dao.dataCount(category, keyword);
		}
		
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;

		
		List<FaqDTO> list = null;
		if (keyword.length() == 0) {
			list = dao.listFaq(start, end, category);
		} else {
			list = dao.listFaq(start, end, keyword, category);
		}
		
		List<FaqDTO> listCategory = dao.listCategory();
		
		
		int listNum, n = 0;
		for (FaqDTO dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
		}
		
		String query = "faq_Category="+category;
		if (keyword.length() != 0) {
			query += "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl = cp + "/faq/list.do";
		if (query.length() != 0) {
			listUrl += "?" + query;
		}

		
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("listUrl", listUrl);
		req.setAttribute("listCategory", listCategory);
		req.setAttribute("page", current_page);
		req.setAttribute("query", query);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("paging", paging);
		req.setAttribute("keyword", keyword);
		req.setAttribute("category", category);

		} catch (Exception e) {
		e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/faq/list.jsp");
		
	}
	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/faq/write.jsp");
	}
	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao = new FaqDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/faq/list.do");
			return;
		}
		
		try {
			FaqDTO dto = new FaqDTO();
			
			dto.setUser_Id(info.getUserId());
			
			dto.setFaq_Subject(req.getParameter("faq_Subject"));
			dto.setFaq_Content(req.getParameter("faq_Content"));
			dto.setFaq_Category(Integer.parseInt(req.getParameter("faq_Category")));
			
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				long size = p.getSize();
				dto.setFaq_Save(saveFilename);
				dto.setFaq_Original(originalFilename);
				dto.setFaq_FileSize(size);
			}
			dao.insertFaq(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/faq/list.do");
		
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao = new FaqDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();

		String page = req.getParameter("page");		

		try {
			int num = Integer.parseInt(req.getParameter("faq_Num"));
			FaqDTO dto = dao.readFaq(num);

			if (dto == null) {
				resp.sendRedirect(cp + "/faq/list.do?page=" + page);
				return;
			}

			if (!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/faq/list.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/faq/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/faq/list.do?page=" + page);
	}
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao = new FaqDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/faq/list.do");
			return;
		}
		
		String page = req.getParameter("page");

		try {
			FaqDTO dto = new FaqDTO();
			dto.setFaq_Num(Integer.parseInt(req.getParameter("faq_Num")));
			dto.setFaq_Subject(req.getParameter("faq_Subject"));
			dto.setFaq_Content(req.getParameter("faq_Content"));
			dto.setFaq_Category(Integer.parseInt(req.getParameter("faq_Category")));
			dto.setFaq_Save(req.getParameter("faq_Save"));
			dto.setFaq_Original(req.getParameter("faq_Original"));
			dto.setFaq_FileSize(Long.parseLong(req.getParameter("faq_FileSize")));

			dto.setUser_Id(info.getUserId());

			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if (map != null) {
				if (req.getParameter("faq_Save").length() != 0) {
					
					FileManager.doFiledelete(pathname, req.getParameter("faq_Save"));
				}

				
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				long size = p.getSize();
				dto.setFaq_Save(saveFilename);
				dto.setFaq_Original(originalFilename);
				dto.setFaq_FileSize(size);
			}

			dao.updateFaq(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/faq/list.do?page=" + page);
	}
	
	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
				FaqDAO dao = new FaqDAO();
				
				String cp = req.getContextPath();

				String page = req.getParameter("page");

				try {
					int num = Integer.parseInt(req.getParameter("faq_Num"));
					FaqDTO dto = dao.readFaq(num);
					if (dto == null) {
						resp.sendRedirect(cp + "/faq/list.do?page=" + page);
						return;
					}

					// 파일삭제
					FileManager.doFiledelete(pathname, dto.getFaq_Save());

					// 파일명과 파일크기 변경
					dto.setFaq_Original("");
					dto.setFaq_Save("");
					dto.setFaq_FileSize(0);
					dao.updateFaq(dto);
					

					req.setAttribute("dto", dto);
					req.setAttribute("page", page);

					req.setAttribute("mode", "update");

					forward(req, resp, "/WEB-INF/views/faq/write.jsp");
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}

				resp.sendRedirect(cp + "/faq/list.do?page=" + page);
			}
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao = new FaqDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int faq_Num = Integer.parseInt(req.getParameter("faq_Num"));
			String keyword = req.getParameter("keyword");
			if (keyword == null) {
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			FaqDTO dto = dao.readFaq(faq_Num);
			if (dto == null) {
				resp.sendRedirect(cp + "/faq/list.do?" + query);
				return;
			}

			if (dto.getFaq_Save() != null && dto.getFaq_Save().length() != 0) {
				FileManager.doFiledelete(pathname, dto.getFaq_Save());
			}

			dao.deleteFaq(faq_Num, info.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/faq/list.do?" + query);
	}

	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
		FaqDAO dao = new FaqDAO();
		boolean b = false;

		try {
			int faq_Num = Integer.parseInt(req.getParameter("faq_Num"));

			FaqDTO dto = dao.readFaq(faq_Num);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getFaq_Save(), dto.getFaq_Original(), pathname, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}
	}
}
