<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
function sendLogin() {
	const f = document.loginForm
	
	f.action = "${pageContext.request.contextPath}/member/login_ok.do";
	f.submit();
	
}

</script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   <!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
	
	<main>
		<h3>로그인</h3>
		<form name="loginForm" method="post">
			<p> 아이디 <input type="text" name="user_Id"></p>
			<p> 비밀번호<input type="password" name="user_Pwd"></p>
			<p>
				<button type="button" onclick="sendLogin();">로그인</button>
				<button>회원가입</button>
			</p>
		</form>
	</main>
	
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body>
</html>