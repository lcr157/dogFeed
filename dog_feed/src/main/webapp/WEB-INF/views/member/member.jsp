
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
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/resource/css/admin.css" rel="stylesheet" type="text/css"/>
<title>${title}</title>

<style>
.visual{ height:1000px; border:1px solid red; }
.table-form td {
	padding: 7px 0;
}
.table-form tr:first-child {
	border-top: 2px solid #212529; 
}
.table-form tr td:first-child{
	background: #f8f8f8;
	text-align: center;
	width: 120px;
	font-weight: 500;
}
.table-form tr td:nth-child(2) {
	text-align: left; padding-left: 10px; 
}
.table-form input[type=text]:focus, .table-form input[type=date]:focus, .table-form input[type=password]:focus {
	border: 1px solid #222;
}
.help-block, .block {
	margin-top: 5px;
}
.msg-box {
	text-align: center; color: blue;
}
      div.container{
    }
 
      div.insert{
    }
 
    div.create{
    width: 800px;
    text-align: center;
    padding: 30px;
    border-bottom: 1px solid black;
    margin: auto;
    }
 
    table{
    height: 300px;
    width: 900px;
    border-top: 3px solid black;
    margin-right: auto;
    margin-left: auto;
    }
 
    td{
    border-bottom: 1px dotted black;
    }
 
    caption{
    text-align: left;
    }
 
    .col1 {
    background-color: #e8e8e8;
    padding: 10px;
    text-align: right;
    font-weight: bold;
    font-size: 0.8em;
    }
 
    .col2 {
    text-align: left;
    padding: 5px;
    }
 
    .but1 {
    height: 25px;
    width: 80px;
    color: white;
    background-color: black;
    border-color: black;
    }
 
    .but2 {
    height: 27px;
    width: 120px;
    color: white;
    background-color: black;
    border-color: black;
    }
 
    .but3 {
    height: 35px;
    width: 150px;
    background-color: white;
    border: 2px solid black;
    }
 
    .but4{
    height: 35px;
    width: 150px;
    background-color: white;
    border: 2px solid black;
    }
    
    .but1:hover {
    background-color: #b9b9b9;
    color: black;
    border: 2px solid black;
    }
 
    .but2:hover {
    background-color: #b9b9b9;
    color: black;
    border: 2px solid black;
    }
 
    .but3:hover {
    background-color: black;
    color: white;
    border: 2px solid black;
    }
 
    .but4:hover {
    background-color: black;
    color: white;
    border: 2px solid black;
    }
    
    p{
    font-size: 0.7em;
    }
 
    .g{
    font-size: 0.7em;
    }
 
    .c{
    font-size: 0.7em;
    }
 
    .a{
    font-size: 0.7em;
    }
    
    .num{
    color: red;
    }
.help-block, .block {
	margin-top: 5px;
}
.modal_wrap {
	display: none;
	width: 500px;
	height: 500px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -250px 0 0 -250px;
	background: #eee;
	z-index: 2;
}
.black_bg {
	display: none;
	position: absolute;
	content: "";
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	top: 0;
	left: 0;
	z-index: 1;
}
.modal_close {
	width: 26px;
	height: 26px;
	position: absolute;
	top: -30px;
	right: 0;
}
.modal_close>a {
	display: block;
	width: 100%;
	height: 100%;
	background: url(https://img.icons8.com/metro/26/000000/close-window.png);
	text-indent: -9999px;
}
</style>

<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>

<script type="text/javascript">
function memberOk() {
	const f = document.memberForm;
	let str;
	str = f.user_Id.value;
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(str) ) { 
		alert("아이디를 다시 입력 하세요. ");
		f.user_Id.focus();
		return;
	}
	str = f.user_Pwd.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.user_Pwd.focus();
		return;
	}
	if( str !== f.user_Pwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.user_Pwd.focus();
        return;
	}
	
    str = f.user_Name.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.userName.focus();
        return;
    }
    str = f.user_Birth.value;
    if( !str ) {
        alert("생년월일를 입력하세요. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }
    str = f.tel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }
    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.user_Email2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.user_Email2.focus();
        return;
    }
    str = f.user_Email3.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.user_Email3.focus();
        return;
    }
   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}
function changeEmail() {
    const f = document.memberForm;
	    
    let str = f.selectEmail.value;
    if(str !== "direct") {
        f.user_Email3.value = str; 
        f.user_Email3.readOnly = true;
        f.user_Email2.focus(); 
    }
    
    else {
        f.user_Email3.value = "";
        f.user_Email3.readOnly = false;
        f.user_Email2.focus();
    }
}
function next(){
	 if(confirm("탈퇴하시려면 예를 누르시고 하지 않으시려면 아니오를 눌러주세요"))
	 {
	  location.href="${pageContext.request.contextPath}/member/delete_ok.do?mode=delete";
	 }
	 else
	 {
	 alert('아니오를 누르셨습니다');
	 }
	}
	
function userIdCheck() {
	// 아이디 중복 검사 : AJAX
	let userId = $("#user_Id").val();
	
	if(! /^[a-z][a-z0-9_]{4,9}$/i.test(userId)) {
		let s = "아이디는 5~10자 이내이며 첫글자는 영문자로 시작합니다.";
		$("#user_Id").focus();
		$("#user_Id").parent().next(".help-block").html(s);
		return;
	}
	
	let url = "${pageContext.request.contextPath}/member/userIdCheck.do";
	let query = "user_Id=" + userId;
	
	$.ajax({
		type:"post",
		url:url,
		data:query,
		dataType:"json",
		success:function(data) {
			let passed = data.passed;
			
			if(passed === "true") {
				let s = "<span style='color:blue;font-weight:600;'>"+userId+"</span> 아이디는 사용가능합니다.";
				$("#user_Id").parent().next(".help-block").html(s);
				$("#user_IdValid").val("true");
			} else {
				let s = "<span style='color:red;font-weight:600;'>"+userId+"</span> 아이디는 사용 할 수 없습니다.";
				$("#user_Id").parent().next(".help-block").html(s);
				$("#user_IdValid").val("false");
				$("#user_Id").focus();
			}
			
		},
		error:function(e) {
			console.log(e.responseText);
		}
	});
}
</script> 
</head>

<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	<!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
<main>
    <div class="body-container">
        <div class="body-title">
            <h3><i class="fas fa-user"></i> ${title} </h3>
        </div>
        
		<form name="memberForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					<p>
						<input type="text" name="user_Id" id="user_Id" maxlength="10" class="form-control" value="${dto.user_Id}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
						<c:if test="${mode=='member'}">
							<button type="button" class="btn" onclick="userIdCheck();">아이디 중복검사</button>
						</c:if>				
					</p>
					<c:if test="${mode=='member'}">
						<p class="help-block">아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.</p>
					</c:if>
				</td>
			</tr>
		
			<tr>
				<td>패스워드</td>
				<td>
					<p>
						<input type="password" name="user_Pwd" class="form-control" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드는 5~10자 이내이며, 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드 확인</td>
				<td>
					<p>
						<input type="password" name="user_Pwd2" class="form-control" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드를 한번 더 입력해주세요.</p>
				</td>
			</tr>
		
			<tr>
				<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
				<td>
					<input type="text" name="user_Name" maxlength="10" class="form-control" value="${dto.user_Name}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
				</td>
			</tr>
		
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="user_Birth" class="form-control" value="${dto.user_Birth}" style="width: 50%;">
				</td>
			</tr>
		
			<tr>
				<td>이 메 일</td>
				<td>
					  <select name="selectEmail" class="form-select" onchange="changeEmail();">
							<option value="">선 택</option>
							<option value="naver.com"   ${dto.user_Email3=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
							<option value="hanmail.net" ${dto.user_Email3=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
							<option value="gmail.com"   ${dto.user_Email3=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
							<option value="hotmail.com" ${dto.user_Email3=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
							<option value="direct">직접입력</option>
					  </select>
					  <input type="text" name="user_Email2" maxlength="30" class="form-control" value="${dto.user_Email2}" style="width: 33%;"> @ 
					  <input type="text" name="user_Email3" maxlength="30" class="form-control" value="${dto.user_Email3}" style="width: 33%;" readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					  <select name="tel1" class="form-select">
							<option value="">선 택</option>
							<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
							<option value="02"  ${dto.tel1=="02"  ? "selected='selected'" : ""}>02</option>
							<option value="031" ${dto.tel1=="031" ? "selected='selected'" : ""}>031</option>
							<option value="032" ${dto.tel1=="032" ? "selected='selected'" : ""}>032</option>
							<option value="033" ${dto.tel1=="033" ? "selected='selected'" : ""}>033</option>
							<option value="041" ${dto.tel1=="041" ? "selected='selected'" : ""}>041</option>
							<option value="042" ${dto.tel1=="042" ? "selected='selected'" : ""}>042</option>
							<option value="043" ${dto.tel1=="043" ? "selected='selected'" : ""}>043</option>
							<option value="044" ${dto.tel1=="044" ? "selected='selected'" : ""}>044</option>
							<option value="051" ${dto.tel1=="051" ? "selected='selected'" : ""}>051</option>
							<option value="052" ${dto.tel1=="052" ? "selected='selected'" : ""}>052</option>
							<option value="053" ${dto.tel1=="053" ? "selected='selected'" : ""}>053</option>
							<option value="054" ${dto.tel1=="054" ? "selected='selected'" : ""}>054</option>
							<option value="055" ${dto.tel1=="055" ? "selected='selected'" : ""}>055</option>
							<option value="061" ${dto.tel1=="061" ? "selected='selected'" : ""}>061</option>
							<option value="062" ${dto.tel1=="062" ? "selected='selected'" : ""}>062</option>
							<option value="063" ${dto.tel1=="063" ? "selected='selected'" : ""}>063</option>
							<option value="064" ${dto.tel1=="064" ? "selected='selected'" : ""}>064</option>
							<option value="070" ${dto.tel1=="070" ? "selected='selected'" : ""}>070</option>
					  </select>
					  <input type="text" name="tel2" maxlength="4" class="form-control" value="${dto.tel2}" style="width: 33%;"> -
					  <input type="text" name="tel3" maxlength="4" class="form-control" value="${dto.tel3}" style="width: 33%;">
				</td>
			</tr>
		
			<tr>
				<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
				<td>
					<p>
						<input type="text" name="user_Address1" id="user_Address1" maxlength="50" class="form-control" value="${dto.user_Address1}" style="width: 96%;">
					</p>
					<p class="block">
						<input type="text" name="user_Address2" id="user_Address2" maxlength="50" class="form-control" value="${dto.user_Address2}" style="width: 96%;">
					</p>
				</td>
			</tr>
			
		</table>
		
		<table class="table">
			<c:if test="${mode=='member'}">
				<tr>
					<td align="center">
						<span>
							<input type="checkbox" name="terms" value="1" checked="checked" onchange="form.btnOk.disabled = !checked">
							개인정보 수집 및 이용에 동의하시겠습니까 ?
						</span>



    							</div>
							<div>
					    </span>
					</td>
				</tr>
			</c:if>
					
			<tr>
				<td style="width:50%;" align="center">
				    <button type="button" class="btn" name="btnOk" onclick="memberOk();"> ${mode=="member"?"회원가입":"정보수정"} </button>
				    <button type="reset" class="btn"> 다시입력 </button>
				    <button type="button" class="btn" 
				    	onclick="javascript:location.href='${pageContext.request.contextPath}/';"> ${mode=="member"?"가입취소":"수정취소"} </button>
				</td>
				
				<c:if test="${mode != 'member'}">
				<td style="width:50%; text-align: right;">
					<button type="button" class="btn" onclick="next()">회원탈퇴</button>
				</td>
				</c:if>
			</tr>
			
			
			<tr>
				<td align="center">
					<span class="msg-box">${message}</span>
				</td>
			</tr>
		</table>
		</form>
    <footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
    </div>

</main>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수
                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }
                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }
               
                document.getElementById('addr1').value = fullAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
    
    function modal() {
    	document.getElementById("modal").toggle();
	}
    
    window.onload = function() {
    	 
        function onClick() {
            document.querySelector('.modal_wrap').style.display ='block';
            document.querySelector('.black_bg').style.display ='block';
        }   
        function offClick() {
            document.querySelector('.modal_wrap').style.display ='none';
            document.querySelector('.black_bg').style.display ='none';
        }
     
       document.getElementById('modal_btn').addEventListener('click', onClick);
       document.querySelector('.modal_close').addEventListener('click', offClick);
     
    };
</script>

</body> </html>
