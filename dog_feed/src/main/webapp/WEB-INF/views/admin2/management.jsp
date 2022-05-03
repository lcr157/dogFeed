<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 관리</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/resource/css/admin.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
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
	border-collapse: collapse;
}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
				<td width="50%" style="text-align: left;">${dataCount}개(${page}/${total_page}페이지)</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table class="table">
			<tr>
				<th>상품번호</th>
				<th>상품카테고리</th>
				<th>상품카테고리 종류</th>
				<th>상품명</th>
				<th>상품가격</th>
				<th>상품등록일</th>
				<th>상품조회수</th>
				<th>상품공개여부</th>
			</tr>
				
			<!-- 상품 자리 -->
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.listNum}</td>
					<td>${dto.categoryDetail_Name}</td>
					<td>${dto.categoryDetail_Kind}</td>
					<td>
						<a href="${articleUrl}&num=${dto.product_Num}">${dto.product_Name}</a>
					</td>
					<td>${dto.product_Price}원</td>
					<td>${dto.product_Date}</td>
					<td>${dto.product_Hits}</td>
					<td>
						<c:if test="${dto.product_Privacy eq '1'}">
							공개
						</c:if>
						
						<c:if test="${dto.product_Privacy eq '0'}">
							비공개
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>			
		
		<div style='margin: 20px 0; text-align: center;'>
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
							<option value="all" ${condition =="all" ? "selected='selected'" : "" }>상품명+내용</option>
							<option value="categoryDetail_Name" ${condition =="categoryDetail_Name" ? "selected='selected'" : "" }>상품카테고리</option>
							<option value="categoryDetail_Kind" ${condition =="categoryDetail_Kind" ? "selected='selected'" : "" }>상품카테고리 종류</option>
							<option value="product_Date" ${condition =="product_Date" ? "selected='selected'" : "" }>상품등록일</option>
							<option value="product_Privacy" ${condition =="product_Privacy" ? "selected='selected'" : "" }>상품공개여부</option>
						</select>
						<input type="text" name="keyword" value="${keyword}">
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