<%@ page import="com.member.MemberDAO"%>
<%@ page import="com.member.MemberDTO"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
table {
	margin: 0 auto;
	border: 1px solid black;
	border-collapse: collapse;
	background-color: white;
}

th, td {
	border: 1px solid black;
	padding: 3px;
	text-align: center;
	font-size: 14px;
	background-color: #e6ecff;
}

th {
	background: black;
	color: white;
}

.member_check {
	width: 80px;
}

.member_user_Id {
	width: 80px;
}

.member_user_Name {
	width: 80px;
}

.member_user_Email {
	width: 140px;
}

.member_user_Tel {
	width: 140px;
}

.member_user_Birth {
	width: 100px;
}

.member_user_Address1 {
	width: 200px;
}

.member_user_Address2 {
	width: 200px;
}

.member_login {
	width: 100px;
}

.w-btn-green2 {
    background-color: #519d9e;
    color: white;
}

.table1-list thead > tr:first-child{
	background: #f8f8f8;
}
.table1-list th, .table-list td {
	text-align: center;
}
.table1-list .left {
	text-align: left; padding-left: 5px; 
}

.table1-list .num {
	width: 60px; color: #787878;
}
.table1-list .subject {
	color: #787878;
}
.table1-list .name {
	width: 100px; color: #787878;
}
.table1-list .date {
	width: 100px; color: #787878;
}
.table1-list .hit {
	width: 70px; color: #787878;
}

</style>



<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>

<h2>* 회원목록 *</h2>
<%-- 값을 전달하기 위해 form 태그 사용 --%>
		<table class="table">
			<tr>
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
				<tr>
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
						<button type="button" class="w-btn w-btn-green2" id="removeBtn" onclick="delete_ok('${dto.user_Id}')">회원삭제</button>
						</c:if>
					</td>
					
				
				
		   </tr>
		   </c:forEach>	
	<br>
	</table>
	<p>	
		<form name="searchForm" action="${pageContext.request.contextPath}/member/list.do" method="post">
		<table class="table">
			<tr>
				<td width="100">
					<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/member/member_manager.do';">새로고침</button>
				</td>
				<td align="center">
						<select name="condition" class="form-select">
							<option value="all" ${condition=="all"?"selected='selected'":"" }>전체</option>
							<option value="user_Name" ${condition=="user_Name"?"selected='selected'":"" }>이름</option>
							<option value="user_Email"  ${condition=="user_Email"?"selected='selected'":"" }>이메일</option>
							<option value="user_Tel"  ${condition=="user_Tel"?"selected='selected'":"" }>전화번호</option>
						</select>
						<input type="text" name="keyword" value="${keyword}" class="form-control">
						<button type="button" class="btn" onclick="searchList();">검색</button>
				</td>
			</tr>
		</table>	
	</form>
	</p>
		<!-- <p>
			<button type="button" id="removeBtn">선택회원삭제</button>
		</p> -->
		<div id="message" style="color: red;"></div>


		

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function delete_ok(userId) {
	
	if (confirm(userId + "님을 삭제하시겠습니까?")) {
		/* $.ajax({
			type: "GET",
			url: "/member/member_delete_ok.do",
			data : param,
			success: function(data) {
				alert("삭제 완료");
				location.reload();
			},
			error: function(err) {
				alert("삭제 실패");
				location.reload();
			}
		}); */
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

/* 삭제버튼기능 */
// $("#removeBtn").click(function() {
//	/* 클래스선택자의 상태 : filter 이용 */
//	/* size() : 엘리먼트의 개수 반환 */
//	  if($(".check").filter(":checked").size()==0){
//		$("#message").text("선택된 회원이 없습니다!");
//		return;
//	}  
//	
//	var chck = $(".check"); // 체크 제이쿼리
//	var id = new Array(); // 체크한 아이디 모음
//	var chflg = false; // 체크 확인 여부
//	
//	for (var i = 0; i < chck.length; i++) {
//		if (chck[i].checked) { // 체크되면
//			id.push(chck[i].value); // 아이디에 담고
//			chflg = true; // 체크 확인 true
//		}
//	}
//	
//	if(!chflg){ // 체크 안됨
//		$("#message").text("선택된 회원이 없습니다!");
//		return;
//	} else { // 체크 됨
//		/* 어트리뷰트 변경 */
//		$("#chckId").val(id); //아이디 보내기
//		$("#memberForm").attr("method", "post");
//		$("#memberForm").attr("action", "/member/member_delete_ok.do");
//		$("#memberForm").submit();
//	}
//}); 

		

	 

</script>
