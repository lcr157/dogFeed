<%@ page import="com.member.MemberDAO"%>
<%@ page import="com.member.MemberDTO"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객정보</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/resource/css/admin1.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
.table-subject {
	background: #eee;
    text-align: center;
}

.table1 {
	border-bottom: 2px solid #eee;
}

</style>

<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body>
<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   <!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
	
	<main>
	<h3>고객정보</h3>
<%-- 값을 전달하기 위해 form 태그 사용 --%>

		<table class="table">
			<tr class="table-subject">
				<!-- <th><input type="checkbox" id="allCheck"></th> -->
				<th>직급</th>
				<th>아이디</th>
				<th>이름</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>주소1</th>
				<th>주소2</th>
				<th>가입일</th>
				<th>최근접속</th>
				<th>회원삭제</th>
			</tr>

			<c:forEach var="dto" items="${list}">
				<tr class="table1">
					<td>
						<c:if test="${dto.user_Role eq  '1'}">
							관리자
						</c:if>
						
						<c:if test="${dto.user_Role eq  '0'}">
							회원
						</c:if>
				    </td>	
					<td>${dto.user_Id}</td>
					<td>${dto.user_Name}</td>
					<td>${dto.user_Email1}${dto.user_Email2}${dto.user_Email3}</td>
					<td>${dto.tel}</td>
					<td>${dto.user_Address1}</td>
					<td>${dto.user_Address2}</td>
					<td>${dto.joinDate}</td>
					<td>${dto.lastLogin}</td>
					<td>
						<c:if test="${dto.user_Role eq  '1'}">
						
						</c:if>
						<c:if test="${dto.user_Role eq  '0'}">
						<button type="button" class="btn" id="removeBtn" onclick="delete_ok('${dto.user_Id}')">회원삭제</button>
						</c:if>
					</td>
		   		</tr>
		   </c:forEach>	
		</table>
		
		<p></p>
		<table class="table">
			<tr>
				<td width="100">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/member/member_manager.do';">새로고침</button>
				</td>
				<td>
					<form name="searchForm" action="${pageContext.request.contextPath}/member/list.do" method="post">
						<select name="condition">
							<option value="all" ${condition=="all"?"selected='selected'":"" }>전체</option>
							<option value="user_Name" ${condition=="user_Name"?"selected='selected'":"" }>이름</option>
							<option value="user_Email"  ${condition=="user_Email"?"selected='selected'":"" }>이메일</option>
							<option value="user_Tel"  ${condition=="user_Tel"?"selected='selected'":"" }>전화번호</option>
						</select>
						<input type="text" name="keyword" value="${keyword}">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				</tr>
			</table>
		
			<div id="message" style="color: red;"></div>
	
	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
	function delete_ok(userId) {
		
		if (confirm(userId + "님을 삭제하시겠습니까?")) {
			location.href="${pageContext.request.contextPath}/member/memberDeleteOk.do?user_Id=" + userId + "&mode=ok";
		}
		
	}
	
	/* 전체 선택 및 전체선택 해제기능 */
	 $("#allCheck").change(function() {
		if($(this).is(":checked")){
			/* prop() : 속성값의 변경 */
			$(".check").prop("checked", true);
		} else {
			$(".check").prop("checked", false);
		}
	});
	
	function searchList() {
			const f = document.searchForm;
			f.submit();
	}
	
	</script>

		</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body> </html>