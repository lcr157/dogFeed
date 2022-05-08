<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매현황</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/resource/css/admin1.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
.table-subject {
	background: #eee;
    text-align: center;
}

.table1 {
	border-bottom: 2px solid #eee;
}
</style>

<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
} 
</script>
</head>


<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	<!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>


	<main>
		<h3>판매 현황</h3>
		<p></p>
		
		<table class="table">
			<tr>
				<td width="50%" style="text-align: left;">주문개수 - ${dataCount}개</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table class="table">
			<tr class = "table-subject">
				<th>주문날짜</th>
				<th>주문번호</th>
				<th>상품명</th>
				<th>상품가격</th>
				<th>주문수량</th>
				<th>주문총액</th>
			</tr>
				
			<!-- 게시글 자리 -->
			<c:forEach var="dto" items="${list}">
				<tr class="table1">
					<td>${dto.orderDetail_Date}</td>
					<td>${dto.order_Num}</td>
					<td>${dto.product_Name}</td>
					<td>${dto.orderDetail_Price}원</td>
					<td>${dto.orderDetail_Quant}개</td>
					<td>${dto.orderDetail_Price * dto.orderDetail_Quant}원</td>
				</tr>
			</c:forEach>
		</table>
		
		<div style='margin: 20px 0; text-align: center;'>
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
		</div>
			
		<table class="table">	
			<tr>
				<td width="100" style="text-align: right;">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/salesStatus.do';">새로고침</button>
				</td>
			</tr>
		</table>
		
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>