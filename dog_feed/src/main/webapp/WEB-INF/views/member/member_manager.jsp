<%@ page import="com.member.MemberDAO"%>
<%@ page import="com.member.MemberDTO"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	// MEMBEFR테이블에 저장된 회원목록을 검색하여 반환하는 DAO클래스 메소드
	List<MemberDTO> memberList=MemberDAO.getDAO().selectAllMember();
%>


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

</style>

<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>

<h2>* 회원목록 *</h2>
<%-- 값을 전달하기 위해 form 태그 사용 --%>
<form name="memberForm" id="memberForm">
	<table>
		<tr>
			<!-- <th><input type="checkbox" id="allCheck"></th> -->
			<th>직급</th>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
			<th>전화번호</th>
			<th>주소</th>
			<th>가입일</th>
			<th>최근접속</th>
			<th>회원삭제</th>
		</tr>
		<%-- 검색값을 출력하기위해 일괄처리사용 --%>
		<%for(MemberDTO member:memberList) {%>
		<tr>
			<td>
				<%if(member.getUser_Role()==1){%> 
				관리자
				 <%} else { %> 
				 <%-- 체크박스에 체크된경우 name변수로 value(체크된 회원의 ID)가 넘어감 : 고유값을 사용--%>
				<%-- <input type="checkbox" class="check" name="checkUser_Id" value="<%=member.getUser_Id()%>">  --%>
				회원
				<%} %>
			</td>	
			<td class="member_user_Id"><%=member.getUser_Id() %></td>
			<td class="member_user_Name"><%=member.getUser_Name() %></td>
			<td class="member_user_Email"><%=member.getUser_Email1() %></td>
			<td class="member_user_Tel"><%=member.getTel() %></td>
			<td class="member_user_Address1"><%=member.getUser_Address1() %> <%=member.getUser_Address2() %></td>
			<td class="member_user_Address2">
				<%if(member.getJoinDate()!=null) {%> <%=String.valueOf(member.getJoinDate()) %> <%} %>
			</td>
			<td class="member_login">
				<%if(member.getLastLogin()!=null) {%> <%=String.valueOf(member.getLastLogin()) %> <%} %></td>
		<td>
			<%if(member.getUser_Role()==1){%>
			<%} else { %>
			<button type="button" class="w-btn w-btn-green2" id="removeBtn" onclick="delete_ok('<%=member.getUser_Id() %>')">회원삭제</button>
			<%} %>
		</td>
	</tr>
		<%} %>

	</table>
	<!-- <p>
		<button type="button" id="removeBtn">선택회원삭제</button>
	</p> -->
	<div id="message" style="color: red;"></div>
</form>

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