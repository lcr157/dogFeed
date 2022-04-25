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

<style type="text/css">
h3 {
	margin-left: 300px;
}

.table {
	width:60%;
}

.table td {
	text-align: left;
}

.table tr:first-child {
	border-top: 2px solid #212529; 
}

.table tr > td:first-child {
	width: 150px; text-align: center; background: #f8f8f8;
}

.table-submit {
	width:60%; margin: 0 auto; 
	text-align: center;
	border: none;
}

</style> 

<script type="text/javascript">
function sendBoard() {
	const f = document.boardForm;
	let str;
	
	str = f.product_Name.value.trim();
	if(!str){
		alert("상품명 입력하세요");
		f.product_Name.focus();
		return;
	}
	
	str = f.product_Price.value.trim();
	if(!str){
		alert("상품가격 입력하세요");
		f.product_Price.focus();
		return;
	}
	
	str = f.product_Info.value.trim();
	if(!str){
		alert("상품설명을 입력하세요");
		f.product_Info.focus();
		return;
	}	
	
	// f.action="${pageContext.request.contextPath}/admin/${mode}_ok.do";
	f.action="${pageContext.request.contextPath}/admin/management.do";
	f.submit();
}

</script>


</head>


<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	<!-- Header -->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
	
	
	<main>
		<h3>상품등록</h3>
		<p></p>
		
		<form name="boardForm" method="post" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td>상품명</td>
					<td>
						<input type="text" name="product_Name" maxlength="100" class="form-control" value="${dto.subject }">
					</td>
				</tr>
				
				<tr>
					<td>작성자</td>
					<td>관리자</td>
				</tr>
				
				<tr>
					<td>카테고리</td>
					<td>
						<select name="category">
							<option value="feed" ${category =="feed" ? "selected='selected'" : "" }>사료</option>
							<option value="snack" ${category =="snack" ? "selected='selected'" : "" }>간식</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>상품가격</td>
					<td> <input type="number" name="product_Price">원 </td>
				</tr>
				
				<tr> 
					<td style="height: 300px;">상품설명</td>
					<td> 
						<textarea style="height: 300px;" name="product_Info" class="form-control">${dto.product_Info}</textarea>
					</td>
				</tr>
				
				<tr>
					<td>공개여부</td>
					<td>
						<select name="product_Privacy">
							<option value="1" ${open =="yes" ? "selected='selected'" : "" }>공개</option>
							<option value="0" ${open =="no" ? "selected='selected'" : "" }>비공개</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>사&nbsp;&nbsp;&nbsp;&nbsp;진</td>
					<td style="text-align: left;"> 
						<input type="file" name="selectFile">
					</td>
				</tr>
			</table>
			<p></p>
		
			<table class="table-submit">
				<tr> 
					<td align="center">
						<button class="btn" type="button" onclick="sendBoard();">${mode=='update'?"수정완료":"등록하기"}</button>
						<button class="btn" type="reset">다시입력</button>
						<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/management.do';">${mode=="update"?"수정취소":"등록취소"}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="num" value="${dto.num }">
							<input type="hidden" name="page" value="${page }">
						</c:if>
					</td>
				</tr>
			</table>
		</form>
		<p></p>
		
	</main>


	<!-- footer -->
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>