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

input[name='qna_Privacy'] {
	
}


</style>
<script type="text/javascript">

function sendQna() {
    const f = document.qnaForm;
	let str;
	
    str = f.product_Num.value;
    if(!str) {
        alert("????????? ???????????????.");
        f.product_Num.focus();
        return;
    }
	
    str = f.qna_Subject.value.trim();
    if(!str) {
        alert("????????? ???????????????. ");
        f.subject.focus();
        return;
    }

    str = f.qna_Content.value.trim();
    if(!str) {
        alert("????????? ???????????????. ");
        f.content.focus();
        return;
    }
    
    str = f.qna_Content.value.trim();
    if(!str) {
        alert("????????? ???????????????. ");
        f.content.focus();
        return;
    }
    
    f.action = "${pageContext.request.contextPath}/qna/${mode}_ok.do";
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
		<form name="qnaForm" method="post">
		<table>
			<tr>
				<td class="table-title">
				<h2>Q&amp;A ??????</h2>
				</td>
			</tr>
		</table>
        
			<table class="table-subject">
				<tr> 
					<td>??????</td>
					<td> 

						<c:choose>
							<c:when test="${mode=='reply'}">
								<input type="hidden" name="product_Num" value="${dto.product_Num}">
								${dto.product_Name}
							</c:when>
							<c:when test="${dto.qna_OrderNum > 0}">
								<input type="hidden" name="product_Num" value="${dto.product_Num}">
								${dto.product_Name}
							</c:when>
							<c:otherwise>
									<select name="product_Num">
										<option value="">????????? ??????????????????.</option>
										<c:forEach var="pro" items="${listProduct}">
											<option value="${pro.product_Num}"  ${dto.product_Num==pro.product_Num?"selected='selected'":"" }>${pro.product_Name}</option>
										</c:forEach>
									</select>
							</c:otherwise>
						</c:choose>
					
					
						
					</td>
				</tr>
				<tr> 
					<td>???&nbsp;&nbsp;&nbsp;&nbsp;???</td>
					<td> 
						<input type="text" name="qna_Subject" maxlength="100" value="${dto.qna_Subject}">
					</td>
				</tr>
				
				<tr> 
					<td>?????????</td>
					<td> 
						<p>${sessionScope.member.userName}</p>
					</td>
				</tr>
				
				<tr> 
					<td>???&nbsp;&nbsp;&nbsp;&nbsp;???</td>
					<td> 
						<textarea name="qna_Content" >${dto.qna_Content}</textarea>
					</td>
				</tr>
				
				<tr> 
					<td>?????????</td>
					<td>
						<c:choose>
							<c:when test="${mode=='reply'}">
								<input type="hidden" name="qna_Privacy" value="${dto.qna_Privacy}">
								${dto.qna_Privacy==1 ? "?????????":"??????" } 
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="qna_Privacy" value="1" ${dto.qna_Privacy==1 ? "checked='checked'":"" }>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
				
			<table class="class-btn">
				<tr> 
					<td align="center">
						<button type="button" class="bt" onclick="sendQna();">${mode=='update'?'????????????':(mode=='reply'? '????????????':'????????????')}</button>
						<button type="reset" class="bt">????????????</button>
						<button type="button" class="bt" onclick="location.href='${pageContext.request.contextPath}/qna/list.do';">${mode=='update'?'????????????':(mode=='reply'? '????????????':'????????????')}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="qna_Num" value="${dto.qna_Num}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
						<c:if test="${mode=='reply'}">
							<input type="hidden" name="qna_GroupNum" value="${dto.qna_GroupNum}">
							<input type="hidden" name="qna_OrderNum" value="${dto.qna_OrderNum}">
							<input type="hidden" name="qna_Depth" value="${dto.qna_Depth}">
							<input type="hidden" name="qna_Parent" value="${dto.qna_Num}">
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