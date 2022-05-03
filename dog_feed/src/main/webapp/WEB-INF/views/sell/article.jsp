<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강아지 사료 판매사이트에 오신걸 환영합니다.</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
h3 {
	width:50%;
	padding: 15px 0 0 0;
	margin: auto;
	text-align: center;
	font-weight: bold;
}

main {
	display: block;
}

a {
    color: #222;
    text-decoration: none;
    cursor: pointer;
}

a:hover {
	color: #FFC19E;
	 text-decoration: underline;
}

.table {
	width:60%; margin: 0 auto; 
	text-align: center;
}

.table tr:first-child {
	border-top: 2px solid #212529; 
}

.table-form {
	text-align: center;
}

.table-submit tr td {
	padding: 10px 0;
}

.img-box {
	max-width: 1000px;
	padding-top: 15px;
	padding-bottom: 5px;
    margin: 0 auto;
	box-sizing: border-box;
	flex-direction: row; 
	flex-wrap: nowrap;
	overflow-x: auto;
	display: block;
    vertical-align: inherit;
}

.img-box img {
	width: 500px;
	height: 500px;
	margin-left: 15px;
}

.btn {
	color: #333;
    border: 1px solid #999;
    background-color: #fff;
    padding: 5px 10px;
    border-radius: 4px;
    font-weight: 500;
    cursor: pointer;
    font-size: 14px;
    font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
    vertical-align: baseline;
}

.btn:active, .btn:focus, .btn:hover {
	background-color: #f8f9fa;
	color:#333;
}

</style>

</head>

<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   	<!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
	
<main>
	<h3>${dto.product_Name}</h3>
		<p></p>
		<table class="table table-form">
			
				<tr>
					<td colspan="2" style="text-align:left; font-size: 18px; padding-bottom: 10px;">
						제품 ▷ ${dto.categoryDetail_Name} ▷ ${dto.categoryDetail_Kind}
					</td>
				</tr>
				<tr>
					<td align="right" style="font-size: 15px;">
						조회수 ${dto.product_Hits}
					</td>
				</tr>
				<tr>
					<td colspan="2" width="50%" style="border-bottom: none; padding-bottom: 10px; 
					font-weight: bold; font-size: 20px; text-decoration: underline; text-underline-position:under;">
						&#8361; ${dto.product_Price} 원
					</td>
				</tr>
				
				<tr style="border-bottom: none;">
					<td colspan="2" height="200" style="font-size:20px; font-weight: bold;">
					&lt; ${dto.product_Info} &gt;
						<div class="img-box" style="border= none;">
							<c:forEach var="vo" items="${listImage}">
								<img src="${pageContext.request.contextPath}/uploads/management/${vo.image_Name}">
							</c:forEach>
						</div>
					</td>	
				</tr>
			
		</table>   
		    
		<table class="table table-submit">	
				<tr>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sell/${bUri}.do';">리스트</button>
					</td>
				</tr>
		</table>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>