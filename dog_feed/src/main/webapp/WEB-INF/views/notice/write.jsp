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

.table-subject tr td:first-child {
	background: #eee;
	text-align: center;
	font-weight: bold;
	width: 20%;
}

.table-subject :not(tr:first-child) td {
	border-bottom: 1px solid #ede;
}

.table-subject tr:last-child td {
	border-bottom: 2px solid;
}

.table-subject tr:first-child td {
	border-top: 2px solid;
}

.table-subject tr td:last-child{
	padding: 15px 10px;
}

input[type='text'] {
	width: 98%;
	height: 50px;
	box-sizing: border-box;
	border: solid 1px #eee;
	border-radius: 5px;
	font-size: 16px;
}

textarea {
	width: 98%;
	height: 500px;
	box-sizing: border-box;
	border: solid 1px #eee;
	border-radius: 5px;
	font-size: 16px;
	resize: none;
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
function sendNotice() {
    const f = document.noticeForm;
	let str;
	
    str = f.notice_Subject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.notice_Subject.focus();
        return;
    }

    str = f.notice_Content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.notice_Content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/notice/${mode}_ok.do";
    f.submit();
}

</script>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<div class="frame">
	<form name="noticeForm" method="post">
		<table>
			<tr>
				<td class="table-title">
				<h2>공지사항 작성</h2>
				</td>
			</tr>
		</table>
	
	
		<table class="table-subject">
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="notice_Subject" maxlength="70" value="${dto.notice_Subject}">
				</td>
			</tr>
			
			<tr>
				<td>작성자</td>
				<td>
					<p>${sessionScope.member.userName}</p>
				</td>
			</tr>
			
			<tr>
				<td>내용</td>
				<td>
					<textarea name="notice_Content">${dto.notice_Content}</textarea>
				</td>
			</tr>
		</table>
			<table class="class-btn">
				<tr> 
					<td align="center">
						<button type="button" class="bt" onclick="sendNotice();">${mode=='write'?'작성완료':'수정완료'}</button>
						<button type="reset" class="bt">다시입력</button>
						<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/notice/list.do';">${mode=='update'?'수정취소':'작성취소'}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="notice_Num" value="${dto.notice_Num}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
					</td>
				</tr>
			</table>
	</form>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>