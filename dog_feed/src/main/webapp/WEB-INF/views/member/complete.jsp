<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<style type="text/css">
main .container {
	margin: 80px auto 50px;
	width: 430px;
	min-height: 350px;
}

.title-body {
	padding: 10px 0;
	text-align: center;
}

.title-body .article-title {
	font-weight: bold;
	font-size: 27px;
	color: #424951;
}

.messageBox {
	margin-top: 25px;
	width: inherit;
	border: 1px solid #DAD9FF;
	padding: 20px;
	color:#333;
	font-size:15px;
	text-align: center;
	border-radius:4px;
}

.messageBox .message {
	line-height: 150%;
}

.messageBox .btn-box {
	padding-top: 15px;
}

</style>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container">
	    	<div class="title-body">
	        	<span class="article-title">${title}</span>
	        </div>
	        
	        <div class="messageBox">
	            <div class="message">${message}</div>
	            <div class="btn-box">
                      <button type="button" onclick="location.href='${pageContext.request.contextPath}/';" class="btnConfirm">메인화면으로 이동</button>
                 </div>
	        </div>
    </div>	       
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>