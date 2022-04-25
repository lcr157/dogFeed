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
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/semi_project/css/admin.css" rel="stylesheet" type="text/css"/>

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
		<h3>상품 관리</h3>
		<p></p>
		
		<table class="table">
			<tr>
				<td width="50%" style="text-align: left;">${dataCount}1개(${page}1/${total_page}1 페이지)</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table class="table">
			<tr>
				<th>상품번호</th>
				<th>상품카테고리코드</th>
				<th>상품명</th>
				<th>상품가격</th>
				<th>상품등록일</th>
				<th>상품조회수</th>
				<th>상품공개여부</th>
			</tr>
			
			<tr>
				<td>1</td>
				<td>1</td>
				<td><a href="${pageContext.request.contextPath}/admin/article.do">사료</a></td>
				<td>30,000원</td>
				<td>2022-04-20</td>
				<td>0</td>
				<td>공개</td>
			</tr>
				
			<!-- 게시물 자리 -->
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.listNum}</td>
					<td>${dto.categoryDetail_Num}</td>
					<td>${dto.product_Name}</td>
					<td>${dto.product_Price}</td>
					<td>${dto.product_Date}</td>
					<td>${dto.product_Hits}</td>
					<td>
					<c:if test="${dto.product_Privacy} == 1">
						공개
					</c:if>
					
					<c:if test="${dto.product_Privacy} == 0">
						비공개
					</c:if>
					</td>
				</tr>
				</c:forEach>
		</table>			
		
		<div style='margin: 20px 0;'>
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
		</div>
			
		<table class="table">	
			<tr>
				<td width="100">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/management.do';">새로고침</button>
				</td>
				<td>
					<form name="searchForm" action="${pageContext.request.contextPath}/admin/management.do">
						<select name="condition">
							<option value="p_Name">상품명</option>
							<option value="p_Num">상품번호</option>
							<option value="p_Category">상품카테고리코드</option>
							<option value="p_Date">상품등록일</option>
						</select>
						<input type="text" name="keyword">
						<button class="btn" type="button" onclick="searchList();">검색</button>
					</form>
				</td>
				<td align="right" width="100">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/write.do';">상품등록</button>
				</td>
			</tr>
		</table>
		
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>