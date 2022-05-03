<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>Q&amp;A</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
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

.table-content tr:first-child td {
	font-size: 20px;
	border-top: 2px solid black;
	padding: 7px 0;
}

.table-content tr:nth-child(2) td {
	font-size: 14px;
	color: gray;
	border-bottom: 1px solid black;
	padding-bottom: 7px;
}

.table-content tr:last-child td {
	padding : 8px 0 0 5px;;
	min-height: 300px;
	border-bottom: 2px solid;
	height: 600px;
}

.class-btn .bt {
	font-size: 13px;
	height: 30px;
	border-radius: 30px;
	font-weight: border;
	width: 70px;
	margin-left: 15px;
}

.class-btn td {
	padding-top: 20px;
}

</style>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.user_Id || sessionScope.member.userId=='admin'}">
function deleteQna() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
        let query = "qna_Num=${dto.qna_Num}&${query}";
        let url = "${pageContext.request.contextPath}/qna/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>




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
					<div>
						<h2>Q&amp;A</h2>
					</div>
				</td>
			</tr>
		</table>

		<table class="table-content">
			<tr>
				<td align="center"><c:if test="${dto.qna_Depth!=0 }">[Re] </c:if>${dto.qna_Subject}</td>
			</tr>

			<tr>
				<td align="center">작성일 ${dto.qna_Date} &nbsp;|&nbsp; 작성자 ${dto.user_Name} &nbsp;|&nbsp; 제품 ${dto.product_Name}</td>
			</tr>
			<tr>
				<td valign="top">${dto.qna_Content}</td>
			</tr>
		</table>
		
		<table class="class-btn">
			<tr>
				<td width="50%">
					<c:if test="${sessionScope.member.userId=='admin'}">
					<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/qna/reply.do?qna_Num=${dto.qna_Num}&page=${page}';">답변</button>
					</c:if>
						<c:if test="${sessionScope.member.userId==dto.user_Id}">
							<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/qna/update.do?qna_Num=${dto.qna_Num}&page=${page}';">수정</button>
						</c:if>
			    		<c:if test="${sessionScope.member.userId==dto.user_Id || sessionScope.member.userId=='admin'}">
			    			<button type="button" class="bt" onclick="deleteQna();">삭제</button>
			    		</c:if>
				</td>
				<td align="right">
					<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/qna/list.do?${query}';">리스트</button>
				</td>
			</tr>
		</table>
        
	</div>








	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>