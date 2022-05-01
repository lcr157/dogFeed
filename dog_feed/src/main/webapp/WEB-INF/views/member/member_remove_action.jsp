<%@ page import="com.member.MemberDAO"%>
<%@ page import="com.member.MemberDTO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	// 비정상적인 요청에 대한 응답 처리
	if(request.getMethod().equals("GET")){
		out.println("<script type='text/javascript'>");
		out.println("location.href='"+request.getContextPath()+"/member/member_index.jsp?workgroup=error&work=error400';");
		out.println("</script>");
		return;
	}
	
	String[] checkUser_Id = request.getParameterValues("checkUser_Id");
	
	for (String user_Id:checkUser_Id) {
		MemberDAO.getDAO().deleteMember(user_Id);
	}
	
	out.println("<script type='text/javascript'>");
	out.println("location.href='"+request.getContextPath()+"/member/member_index.jsp?workgroup=admin&work=member_manager';");
	out.println("</script>");



%>
