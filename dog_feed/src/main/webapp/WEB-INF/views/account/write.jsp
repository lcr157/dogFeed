<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-size: 14px; font-family: 맑은 고딕, 돋움, sans-serif; }

a { color: #000; text-decoration: none; cursor: pointer; }
a:active, a:hover { text-decoration: underline; content: tomato; }

.btn { color: #333; border: 1px solid #333; background-color: #fff; font-weight: 500;
	padding: 5px 10px; border-radius: 4px; cursor: pointer;
	font-size: 14px; font-family: 맑은 고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6; border-color: #adadad; color: #333;
}

.boxTF {
	border: 1px solid #999; padding: 5px 5px;
	background-color: #fff; border-radius: 4px;
	font-family: 맑은 고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.selectField {
	border: 1px solid #999; padding: 4px 5px;
	background-color: #fff; border-radius: 4px;
	font-family: 맑은 고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

textarea:focus, input:focus { outline: none; }

.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding: 10px 0; }

.table-border tr { border-bottom: 1px solid #ccc; }
.table-border tr:first-child { border-top: 2px solid #ccc; }

.container { width: 500px; margin: 30px auto; }
.title { width: 100%; font-size: 16px; font-weight: bold; padding: 13px 0; }

.table-form td { padding: 7px 0; }
.table-form tr > td:first-child { text-align: center; background: #eee; width: 100px; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }
.table-form input[type=text], .table-form input[type=date] { width: 96% }

.msg-box { text-align: center; font-weight: 500; color: blue; }

</style>

<script type="text/javascript">
function sendAccount() {
	const f = document.accountForm;
	let str;
	
	str = f.accountBook_Date.value.trim();
	if(! str) {
		alert("날짜를 입력하세요.");
		f.accountBook_Date.focus();
		return;
	}
	
	str = f.content.value.trim();
	if(! str) {
		alert("사용내역을 입력하세요.");
		f.content.focus();
		return;
	}
	
	str = f.amount.value.trim();
	if(! str) {
		alert("사용금액을 입력하세요.");
		f.amount.focus();
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/account/${mode}_ok.do";
	f.submit();
}

</script>

</head>
<body>

<div class="container">
	<div class="title">
		<h3><span>|</span>가계부</h3>
	</div>
	
	<form name="accountForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>날 짜</td>
				<td>
					<input type="date" name="accountBook_Date" class="boxTF" maxlength="10" required="required" value="${dto.accountBook_Date}">
				</td>
			</tr>
			<tr>
				<td>사용내역</td>
				<td>
					<input type="text" name="content" class="boxTF" required="required" value="${dto.content}">
				</td>
			</tr>
			<tr>
				<td>사용금액</td>
				<td>
					<input type="text" name="amount" class="boxTF" required="required" value="${dto.amount}"
					pattern="\d{1,10}">
				</td>
			</tr>
			<tr>
				<td>메모</td>
				<td>
					<input type="text" name="memo" class="boxTF" value="${dto.memo}">
				</td>
			</tr>
		</table>
		<table class="table">
			<tr>
				<td align="center">
					<button type="submit" class="btn" onclick="sendAccount();">${mode=='update'?'수정완료':'등록하기'}</button>
					<button type="reset" class="btn">다시입력</button>
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/account/account.do';">등록취소</button>
				</td>
			</tr>
		</table>
	</form>	
</div>


</body>
</html>