<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
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
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<title>공지사항</title>
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

.table-subject .num{
	width: 5%;
}

.table-subject .subject{
	width: 60%;
}

.table-subject .name{
	width: 10%;
}

.table-subject .date{
	width: 15%;
}

.table-subject .hit{
	width: 10%;
}

.table-content {
	font-size: 19px;
}

.table-content td:not(.subject){
	text-align: center;
	padding: 9px 0;
	color: gray;
}

.table-content td.subject{
	padding-left: 7px;
}

.table-content td.subject a{
	text-decoration: none; color : black; 
}

.table-content td.subject a:hover{
	color: tomato;
	font-size: 20px;
	font-weight: bold;
}


tr.table-content:not(:last-child) td{
	border-bottom: 1px solid #eee;
}

tr.table-content:last-child td{
	border-bottom: 2px solid;
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

.class-btn .sch {
	font-size: 16px;
	height: 50px;
}

.class-btn select.sch{
	width: 110px;
}

.class-btn input.sch{
	width: 250px;
}

.class-btn button.sch {
	width: 80px;
	color: white;
	background: #808080;
	border: none;
}

.class-btn .bt {
	font-size: 13px;
	height: 30px;
	border-radius: 30px;
	font-weight: border;
	width: 70px;
}

.class-btn .left, .class-btn .right {
	width: 10%;
}

.class-btn .center {
	text-align: center;
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
<header>
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
</header>
	

<div class="frame">
		<table>
			<tr>
				<td class="table-title">
				<h2>공지사항</h2>
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
		
		<table>
				<tr class="table-subject">
					<th class="num">번호</th>
					<th class="subject">제목</th>
					<th class="name">작성자</th>
					<th class="date">작성일</th>
					<th class="hit">조회수</th>
				</tr>
			
				<c:forEach var="dto" items="${list}">
					<tr class="table-content">
						<td class="num">${dto.listNum}</td>
						<td class="subject">
							<a href="${articleUrl}&notice_Num=${dto.notice_Num}">${dto.notice_Subject}</a>
						</td>
						<td class="name">${dto.user_Name}</td>
						<td class="date">${dto.notice_Date}</td>
						<td class="hit">${dto.notice_Hits}</td>
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
		
		<table class="class-btn">
			<tr>
				<td class="left">
					<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/notice/list.do';">초기화</button>
				</td>
				<td class="center">
					<form name="searchForm" action="${pageContext.request.contextPath}/notice/list.do" method="post">
						<select class="sch" name="condition">
							<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
							<option value="user_Name" ${condition=="user_Name"?"selected='selected'":"" }>작성자</option>
							<option value="notice_Date"  ${condition=="notice_Date"?"selected='selected'":"" }>등록일</option>
							<option value="notice_Subject"  ${condition=="notice_Subject"?"selected='selected'":"" }>제목</option>
							<option value="notice_Content"  ${condition=="notice_Content"?"selected='selected'":"" }>내용</option>
						</select><input type="text" class="sch" name="keyword" value="${keyword}"><button type="button" class="sch" onclick="searchList();">검색</button>
					</form>
				</td>
				<td class="right">
				<c:if test="${sessionScope.member.userId=='admin'}">
					<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/notice/write.do';">글올리기</button>
				</c:if>
				</td>
			</tr>
		</table>	
</div>


<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>