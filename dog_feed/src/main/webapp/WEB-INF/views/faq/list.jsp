<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css"
	rel="stylesheet" type="text/css" />
<title>자주묻는질문</title>
<style type="text/css">

.frame * {
	margin: 0; padding: 0;
}

.frame {
	margin-bottom: 50px; 
}

.frame table {
	margin : 0 auto;
	width: 1000px;
}
.table-title{
	text-align: center;
	padding: 40px 0px;
}

.table-subject {
	background: #eee;
	text-align: center;
}
.table-subject th{
	padding: 5px 0;
	font-size: 18px;
	border-bottom: 2px solid;
	border-top: 1px solid;
}

.table-subject .category{
	width: 20%;
}

.table-subject .subject{
	width: 80%;
}



ul.category {
	display: flex;
	list-style-type: none;
	flex-wrap: wrap;
}

ul.category li {
	border: 1px solid #efe;
	width: 150px; height: 50px;
	line-height: 50px;
	cursor:pointer;
}

ul.category li:active {
	border: 2px solid tomato;
}

#table-category {
	text-align: center;
	width: 900px;
	margin-bottom: 20px;
}

.tg2 {
	display: none;
}

.tg2 td {
	padding: 8px;
}

.tg:not(:last-child) {
	border-bottom: 1px solid #eee;
}

.tg td {
	height: 40px;
	font-size: 16px;
	cursor:pointer;
}

.tg td:last-child:hover {
	font-size: 17px;
	color: tomato;
}

.tg td:first-child {
	text-align: center;
	border-right: 1px solid #eee; 
}

.tg td:last-child {
	padding-left: 40px; 
}


.tg2 {
	border-bottom: 1px solid #eee;
	background: #fafafa;
}


.tg2 .content {
	min-height: 200px;
}

.tg2 div:nth-child(2) {
	font-size: 15px;
} 

.tg2 td div:first-child{
	border-bottom: 1px solid gray;
}

.table-datacount {
	font-size: 15px;
}

.table-datacount td {
	padding: 5px;
}

.table-datacount span{
	font-size : 17px;
	font-weight: bold;
	color: #ff7f00;
}

.page-box {
	clear: both;
	padding: 20px 0;
	text-align: center;
}
.paginate {
	clear: both;
	text-align: center;
	white-space: nowrap;
	font-size: 18px;	
}
.paginate a {
	border: 1px solid #ccc;
	color: #000;
	font-weight: 600;
	text-decoration: none;
	padding: 5px 10px;
	margin-left: 4px;
	vertical-align: middle;
}
.paginate a:hover, .paginate a:active {
	color: #6771ff;
}
.paginate span {
	border: 1px solid #ccc;
	background: #abc;
	color: white;
	font-weight: 600;
	padding: 5px 10px;
	margin-left: 4px;
	vertical-align: middle;
}
.paginate :first-child {
	margin-left: 0;
}

.sch {
	font-size: 16px;
	height: 50px;
}

select.sch{
	width: 110px;
}

input.sch{
	width: 250px;
}

button.sch {
	width: 80px;
	color: white;
	background: #808080;
	border: none;
}

.bt {
	font-size: 13px;
	height: 30px;
	border-radius: 30px;
	font-weight: border;
	width: 70px;
}

form[name='searchForm'] {
	text-align: right;
	margin-bottom: 10px; 
}

</style>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.8.8/i18n/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
}
	
	$(function(){
		$(".ex .tg").click(function() {
				 $(this).next(".tg2").fadeToggle(100);
	 
		});
	});
	
function deleteFaq() {
	    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
		    let query = "faq_Num=${dto.faq_Num}&${query}&page=${page}";
		    let url = "${pageContext.request.contextPath}/faq/delete.do?" + query;
	    	location.href = url;
	    }
}

$(function(){
	$(".category li").click(function(){
		var faq_Category = $(this).attr("data-category");
	    let url = "${pageContext.request.contextPath}/faq/list.do?faq_Category=" + faq_Category;
    	location.href = url;
	});
});

</script>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>

<div class="frame">		
	<table>
			<tr>
				<td class="table-title">
				<h2>자주 묻는 질문</h2>
				</td>
			</tr>
		</table>
	<table>
		<tr>
			<td>
				<form name="searchForm"	action="${pageContext.request.contextPath}/faq/list.do"	method="post">
					<input class="sch" type="text" name="keyword" value="${keyword}"><input type="hidden" disabled="disabled"><button class="sch" type="button" onclick="searchList();">검색</button>
				</form>
			</td>
		</tr>
	</table>

	<table class="table-category" id="table-category">
		<tr>
			<td>
				<ul class='category'>
					<li data-category='0'> 전체</li>
					<c:forEach var="cat" items="${listCategory}">
						<li data-category='${cat.faq_Category}'>${cat.faq_Cname}</li>
					</c:forEach>
				</ul>
			</td>
		</tr>


	</table>

		<table>
			<tr class="table-datacount">
				<td width="50%">
					총 <span>${dataCount}</span> 개의 게시물이 있습니다. 
				</td>
				<td align="right">(${page}/${total_page} 페이지)</td>
			</tr>
		</table>

	<table class="ex">
		<tr class="table-subject">
			<th class="category">분류</th>
			<th class="subject">제목</th>
		</tr>

		<c:forEach var="dto" items="${list}">
			<tr class="tg">
				<td>${dto.faq_Cname}</td>
				<td>
					${dto.faq_Subject} 
				</td>
			</tr>

			<tr class="tg2">
				<td colspan="2">
					<div class="content">${dto.faq_Content}</div>
					<div>
						<c:if test="${not empty dto.faq_Save}">
								첨부파일 : <a href="${pageContext.request.contextPath}/faq/download.do?faq_Num=${dto.faq_Num}">${dto.faq_Original}</a>
								(<fmt:formatNumber value="${dto.faq_FileSize/1024}" pattern="#,##0.00"/> kb)
						</c:if>
						<c:if test="${sessionScope.member.userId=='admin'}">
						<br><br>
							<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/faq/update.do?faq_Num=${dto.faq_Num}&page=${page}';">수정</button>
							<button type="button" class="bt" onclick="deleteFaq();">삭제</button>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>

		<table>
		<tr>
		<td>
		<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
		</div>
		</td>
		</tr>
		</table>
	
	<table>
	<tr><td>
	<c:if test="${sessionScope.member.userId=='admin'}">
					<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/faq/write.do';">글올리기</button>
				</c:if>
	</td></tr>
	</table>
</div>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>