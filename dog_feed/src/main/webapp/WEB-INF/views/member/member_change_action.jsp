<%@ page import="com.member.MemberDAO"%>
<%@ page import="com.member.MemberDTO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%


	String user_Id = request.getParameter("user_Id");
	int user_Role = Integer.parseInt(request.getParameter("user_Role"));
	
	MemberDAO.getDAO().updateUser_Role(user_Id, user_Role);
	
	out.println("<script type='text/javascript'>");
	out.println("location.href='"+request.getContextPath()+"/member/member_index.jsp?workgroup=admin&work=member_manager';");
	out.println("</script>");
	
%>