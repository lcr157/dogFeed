package com.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		// uri에 따른 작업 구분
		if(uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete_ok.do") != -1) {
			deleteSubmit(req, resp);
		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		} else if (uri.indexOf("member_manager.do") != -1) {
			memberManagerForm(req, resp);
		} else if (uri.indexOf("memberDeleteOk.do") != -1) {
			memberDeleteSubmit(req, resp);
		}
	}
	
	// 로그인 창	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	
	// 로그인 처리
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(); // 세션 공간 만듦

		MemberDAO dao = new MemberDAO(); // DB에서 데이터 가져오는 객체
		String cp = req.getContextPath(); // default context 경로

		if (req.getMethod().equalsIgnoreCase("GET")) { // method : get / post
			resp.sendRedirect(cp + "/");
			return;
		}

		String user_Id = req.getParameter("user_Id"); // name = user_Id
		String user_Pwd = req.getParameter("user_Pwd"); // name = user_Pwd jsp 참고
				
		MemberDTO dto = dao.loginMember(user_Id, user_Pwd); // id, pwd 통해서 해당 회원 정보 가져오기
		
		if (dto != null) {
			// 로그인 성공 : 로그인정보를 서버에 저장
			// 세션의 유지시간을 20분설정(기본 30분)
			session.setMaxInactiveInterval(20 * 60);

			// 세션에 저장할 내용
			SessionInfo info = new SessionInfo();
			info.setUserId(dto.getUser_Id());
			info.setUserName(dto.getUser_Name());
			info.setUserRoll(dto.getUser_Role());
			//SessionInfo 난 id name roll만 챙기겠다 라는 뜻임
			//사실상 dto로 담아도 됨
			
			// 세션에 member이라는 이름으로 저장
			session.setAttribute("member", info);
			
			//로그인 마지막 날짜 업데이트
			dao.updateLastLogin(user_Id);
			
			// 메인화면으로 리다이렉트
			resp.sendRedirect(cp + "/");
			return;
		}
		
		// 로그인 실패인 경우(다시 로그인 폼으로)
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	
	
	// 로그아웃
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		// 세션에 저장된 정보를 지운다.
		session.removeAttribute("member");

		// 세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.
		session.invalidate();

		// 루트로 리다이렉트
		resp.sendRedirect(cp + "/");
	}
	
	
	// 회원가입 폼
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}

	
	// 회원가입 처리
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			MemberDTO dto = new MemberDTO();
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

			dao.insertMember(dto);
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
	}

	
	// 패스워드 확인 폼
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}

	
	// 패스워드 확인
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			// DB에서 해당 회원 정보 가져오기
			MemberDTO dto = dao.readMember(info.getUserId());
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			String user_Pwd = req.getParameter("user_Pwd");
			String mode = req.getParameter("mode");
			if (!dto.getUser_Pwd().equals(user_Pwd)) {
				if (mode.equals("update")) {
					req.setAttribute("title", "회원 정보 수정");
				} else {
					req.setAttribute("title", "회원 탈퇴");
				}

				req.setAttribute("mode", mode);
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				return;
			}
			
			// 회원탈퇴
			if (mode.equals("delete")) {
				dao.deleteMember(info.getUserId());

				session.removeAttribute("member");
				session.invalidate();

				resp.sendRedirect(cp + "/");
				return;
			}

			// 회원정보수정 - 회원수정폼으로 이동
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}

	
	// 회원정보 수정 완료
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			// 로그아웃 된 경우
			if (info == null) {
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			MemberDTO dto = new MemberDTO();

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

			dao.updateMember(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}

	
	// 회원탈퇴
	private void deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			String mode = req.getParameter("mode");
			
			// 회원탈퇴
			if (mode.equals("delete")) {
				dao.deleteMember(info.getUserId());

				session.removeAttribute("member");
				session.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/");
		return;
	}
	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 아이디 중복검사 (AJAX : JSON)
		MemberDAO dao = new MemberDAO();
		
		String userId = req.getParameter("user_Id");
		MemberDTO dto = dao.readMember(userId);
		
		String passed = "false";
		if(dto == null) {
			passed = "true";
		}
		
		JSONObject job = new JSONObject();
		job.put("passed", passed);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	private void memberManagerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		try {
		
			MemberDTO dto = new MemberDTO();

			dto.setUser_Id(req.getParameter("user_Id"));
			dto.setUser_Pwd(req.getParameter("user_Pwd"));
			dto.setUser_Name(req.getParameter("user_Name"));

			String user_Email2 = req.getParameter("user_Email2");
			String user_Email3 = req.getParameter("user_Email3");
			dto.setUser_Email1(user_Email2 + "@" + user_Email3);

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setTel(tel1 + "-" + tel2 + "-" + tel3);

			dto.setUser_Address1(req.getParameter("user_Address1"));
			dto.setUser_Address2(req.getParameter("user_Address2"));

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/member/member_manager.jsp");
	}
	
	private void memberDeleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		try {
			
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			String mode = req.getParameter("mode");
			String userId = req.getParameter("user_Id");
			
			// 관리자 이며 삭제 눌렀을 때
			if (info.getUserId().equals("admin") && mode.equals("ok")) {
				dao.deleteMember(userId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/member/member_manager.do");
		return;
	}
	
}
