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
<link href="${pageContext.request.contextPath}/resource/css/admin.css" rel="stylesheet" type="text/css"/>

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
				<td width="50%" style="text-align: left;">${dataCount}1개(${page}1/${total_page}1 페이지)</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table class="table">
			<tr>
				<th>주문날짜</th>
				<th>구매자명</th>
				<th>구매자아이디</th>
				<th>상품명</th>
				<th>상품가격</th>
				<th>구매개수</th>
				<th>총액</th>
			</tr>
				
			<tr>
				<!-- 게시글 자리 -->
				<td>2022-XX-XX</td>
				<td>손님</td>
				<td>guest</td>
				<td>제일껌</td>
				<td>1200원</td>
				<td>3개</td>
				<td>3600원</td>
			</tr>
		</table>			
		
		<div style='margin: 20px 0;'>
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
		</div>
			
		<table class="table">	
			<tr>
				<td width="100">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/salesStatus.do';">새로고침</button>
				</td>
				<td>
					<form name="searchForm" action="${pageContext.request.contextPath}/admin/salesStatus.do">
						<select name="condition">
							<option value="all">주문날짜</option>
							<option value="all">구매자명</option>
							<option value="all">구매자아이디</option>
							<option value="all">상품명</option>
						</select>
						<input type="text" name="keyword">
						<button class="btn" type="button" onclick="searchList();">검색</button>
					</form>
				</td>
			</tr>
		</table>
		
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>