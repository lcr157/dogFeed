<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap"
	rel="stylesheet">
<script src="https://kit.fontawesome.com/53a8c415f1.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="./login.css">
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

<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: "Noto Sans KR", sans-serif;
}

a {
	text-decoration: none;
	color: black;
}

li {
	list-style: none;
}

.wrap {
	width: 100%;
	height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	background: rgba(0, 0, 0, 0.1);
}

.login {
	width: 30%;
	height: 600px;
	background: white;
	border-radius: 20px;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
}

h2 {
	color: tomato;
	font-size: 2em;
}

.login_sns {
	padding: 20px;
	display: flex;
}

.login_sns li {
	padding: 0px 15px;
}

.login_sns a {
	width: 50px;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 10px;
	border-radius: 50px;
	background: white;
	font-size: 20px;
	box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.4), -3px -3px 5px
		rgba(0, 0, 0, 0.1);
}

.login_id {
	margin-top: 20px;
	width: 80%;
}

.login_id input {
	width: 100%;
	height: 50px;
	border-radius: 30px;
	margin-top: 10px;
	padding: 0px 20px;
	border: 1px solid lightgray;
	outline: none;
}

.login_pw {
	margin-top: 20px;
	width: 80%;
}

.login_pw input {
	width: 100%;
	height: 50px;
	border-radius: 30px;
	margin-top: 10px;
	padding: 0px 20px;
	border: 1px solid lightgray;
	outline: none;
}

.login_etc {
	padding: 10px;
	width: 80%;
	font-size: 14px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-weight: bold;
}

.submit {
	margin-top: 50px;
	width: 80%;
}

.submit input {
	width: 100%;
	height: 50px;
	border: 0;
	outline: none;
	border-radius: 40px;
	background: linear-gradient(to left, rgb(255, 77, 46), rgb(255, 155, 47));
	color: white;
	font-size: 1.2em;
	letter-spacing: 2px;
}

.msg-box {
	text-align: center; color: blue;
}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("input").not($(":button")).keypress(function(evt) {
			// 엔터키의 키코드값 : 13
			// 엔터를 누르면 아이디, 비밀번호, 로그인 순으로 실행
			if (evt.keyCode === 13) {
				const fields = $(this).parents('form').find('button,input');
				console.log(fields);

				let index = fields.index(this);

				if (index > -1 && (index + 1) < fields.length) {
					fields.eq(index + 1).focus();
				}
				return false;
			}
		});
	});

	function sendLogin() {
		const f = document.loginForm;

		let str = f.user_Id.value;
		if (!str) {
			alert("아이디를 입력하세요. ");
			f.user_Id.focus();
			return;
		}

		str = f.user_Pwd.value;
		if (!str) {
			alert("패스워드를 입력하세요. ");
			f.user_Pwd.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/member/login_ok.do";
		f.submit();
	}
</script>

</head>


<body>
<form name="loginForm" method="post">
	<div class="wrap">
		<div class="login">
			<h2>Log-in</h2>
			<div class="login_id">
				<h4>아이디</h4>
				<input type="text" name="user_Id" id="user_Id" placeholder="user_Id">
			</div>
			<div class="login_pw">
				<h4>비밀번호</h4>
				<input type="password" name="user_Pwd" id="user_Pwd" maxlength="8"
					placeholder="user_Pwd">
			</div>
			<div class="submit">
				<input type="submit" value="submit" onclick="sendLogin();">
			</div>
			<table class="table">
				<tr>
					<td class="msg-box">${message}</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>