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
<style type="text/css">
/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #212529; border-bottom: 1px solid #ced4da; }
.table-border tbody > tr { border-bottom: 1px solid #ced4da; }
.table-border tfoot > tr { border-bottom: 1px solid #ced4da; }
.td-border td { border: 1px solid #EAEAEA; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }

.table-list thead > tr:first-child{
	background: #f8f8f8;
}
.table-list th, .table-list td {
	text-align: center;
}
.table-list .orderNum {
	width: 70px;
	width: 100px; color: #787878;
}
.table-list .productNum {
	width: 70px;
	color: #787878;
}
.table-list .productName {
	width: 200px; color: #787878;
}
.table-list .quant {
	width: 100px; color: #787878;
}
.table-list .price {
	width: 100px; color: #787878;
}
.table-list .date {
	width: 150px; color: #787878;
}

.page-box {
	padding: 20px 0;
	text-align: center;
}
</style>

</head>
<body>

<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container" style="width: 900px; height:700px; margin: 10px auto;">
	<div class="body-title">
			<h3><i class="fas fa-chalkboard-teacher"></i> 구매내역 </h3>
	</div>
		<table class="table table-border table-list">
			<thead>
				<tr>
					<th class="orderNum">주문번호</th>
					<th class="productNum">상품번호</th>
					<th class="productName">상품이름</th>
					<th class="quant">주문수량</th>
					<th class="price">주문가격</th>
					<th class="date">주문날짜</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="dto" items="${orderList}">
					<tr>
						<td>${dto.order_Num}</td>
						<td>${dto.product_Num}</td>
						<td>${dto.product_Name}</td>
						<td>${dto.orderDetail_Quant}</td>
						<td>${dto.orderDetail_Price}</td>
						<td>${dto.orderDetail_Date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : ""}
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>