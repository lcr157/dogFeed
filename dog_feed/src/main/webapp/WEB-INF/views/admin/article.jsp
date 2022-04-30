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
	width:60%;
}

.table tr:first-child {
	border-top: 2px solid #212529; 
}

.table-form {
	text-align: left;
}

.table-submit tr td {
	padding: 10px 0;
}

.img-box {
	max-width: 1500px;
	padding: 5px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}

.img-box img {
	width: 100px; height: 100px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
		
		<table class="table table-form">
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
					상품설명 <br>
					${dto.product_Info}
				</td>
			</tr>	
			
			<tr style="border-bottom: none;">
				<td colspan="2" height="110">
					첨부된 사진 <p></p>
					<div class="img-box" style="border= none;">
						<c:forEach var="vo" items="${listFile}">
							<img src="${pageContext.request.contextPath}/uploads/management/${vo.image_Name}">
						</c:forEach>
					</div>
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