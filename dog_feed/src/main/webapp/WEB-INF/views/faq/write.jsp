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
<title>자주묻는질문</title>
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
function sendOk() {
    const f = document.faqForm;
	let str;
	
	
    str = f.faq_Category.value;
    if(!str) {
        alert("카테고리를 입력하세요. ");
        f.faq_Category.focus();
        return;
    }
	
    str = f.faq_Subject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.faq_Subject.focus();
        return;
    }

    str = f.faq_Content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.faq_Content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/faq/${mode}_ok.do";
    f.submit();
}


<c:if test="${mode=='update'}">
function deleteFile(faq_Num) {
	if( !confirm("파일을 삭제하시겠습니까 ?") ) {
		return;
	}
	let url = "${pageContext.request.contextPath}/faq/deleteFile.do?faq_Num=" + faq_Num + "&page=${page}";
	location.href = url;
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
		<form name="faqForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td class="table-title">
					<h2>FAQ 작성</h2>
				</td>
			</tr>
		</table>
        
			<table class="table-subject">
				<tr>
					<td>분류</td>
					<td>
						<select name="faq_Category">
							<option value="">카테고리를 선택해주세요.</option>
							<option value="2" ${dto.faq_Category==2?"selected='selected'":""}>상품</option>
							<option value="3" ${dto.faq_Category==3?"selected='selected'":""}>배송</option>
							<option value="4" ${dto.faq_Category==4?"selected='selected'":""}>결제</option>
							<option value="5" ${dto.faq_Category==5?"selected='selected'":""}>홈페이지</option>
							<option value="6" ${dto.faq_Category==6?"selected='selected'":""}>기타</option>
						</select>
					</td>
				</tr>
				<tr> 
					<td>제목</td>
					<td> 
						<input type="text" name="faq_Subject" maxlength="100" value="${dto.faq_Subject}">
					</td>
				</tr>
		
				<tr> 
					<td>내용</td>
					<td> 
						<textarea name="faq_Content">${dto.faq_Content}</textarea>
					</td>
				</tr>
				
				<tr>
					<td>첨부</td>
					<td> 
						<input type="file" name="selectFile">
					</td>
				</tr>
				<c:if test="${mode=='update'}">
					<tr>
						<td>첨부된파일</td>
						<td> 
							<p>
								<c:if test="${not empty dto.faq_Save}">
									${dto.faq_Original}&nbsp;&nbsp;<a href="javascript:deleteFile('${dto.faq_Num}');">파일삭제</a>
								</c:if>
								&nbsp;
							</p>
						</td>
					</tr>
				</c:if>
			</table>
				
			<table class="class-btn">
				<tr> 
					<td align="center">
						<button type="button" class="bt" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
						<button type="reset" class="bt">다시입력</button>
						<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/faq/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="faq_Num" value="${dto.faq_Num}">
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="faq_FileSize" value="${dto.faq_FileSize}">
							<input type="hidden" name="faq_Save" value="${dto.faq_Save}">
							<input type="hidden" name="faq_Original" value="${dto.faq_Original}">
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