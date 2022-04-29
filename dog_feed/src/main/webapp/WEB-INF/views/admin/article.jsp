<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${dto.product_Name}</title>
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
h3 {
	margin-left: 300px;
}

.table {
	width:60%;
}

.table1 {
	border-collapse: collapse;
	border: 1px solid black; 
	text-align: left;
}

.table-submit tr td {
	padding: 10px 0;
}

</style>

<script type="text/javascript">
function deleteFile(fileNum) {
	if(confirm("게시글을 삭제하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/admin/delete.do?num=${dto.product_Num}&page=${page}";
	}
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
		<h3>상품 정보</h3>
		<p></p>
		
		<table class="table table1">
			<tr>
				<td colspan="2" style="text-align: center;">
					${dto.product_Name}
				</td>
			</tr>
				
			<tr>
				<td width="50%">
					등록자 : 관리자
				</td>
				<td align="right">
					등록일 : ${dto.product_Date} | 조회수 : ${dto.product_Hits}
				</td>
			</tr>
			
			<tr>
				<td colspan="2" width="50%" style="border-bottom: none; padding-bottom: 0;">
					상품가격 : ${dto.product_Price}원
				</td>
			</tr>
			
			<tr>
				<td colspan="2" style="border-bottom: none; padding-bottom: 0;">
					상품카테고리 : ${dto.categoryDetail_Name}
				</td>
			</tr>
			
			<tr>	
				<td colspan="2">
					상품카테고리 종류 : ${dto.categoryDetail_Kind}
				</td>
			</tr>
			
			<tr>
				<td colspan="2" valign="top" height="200">
					상품설명 : <br>
					${dto.product_Info}
				</td>
			</tr>	
			
			<tr>
				<td colspan="2">
					이전글
					<c:if test="${not empty preReadDto }">
						<a href="${pageContext.request.contextPath}/admin/article.do?${query}&num=${preReadDto.product_Num}">${preReadDto.product_Name}</a>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					다음글
					<c:if test="${not empty nextReadDto}">
						<a href="${pageContext.request.contextPath}/admin/article.do?${query}&num=${nextReadDto.product_Num}">${nextReadDto.product_Name}</a>
					</c:if>
				</td>
			</tr>
		</table>			
		
		
		<table class="table table-submit">	
			<tr>
				<td width="50%" align="left">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/update.do?num=${dto.product_Num}&page=${page}';">수정</button>
					<button class="btn" type="button" onclick="deleteFile();">삭제</button>
				</td>
				
				<td align="right">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/management.do';">리스트</button>
				</td>
			</tr>
		</table>
		
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>