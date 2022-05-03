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
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
*{
	margin: 0; padding: 0;
    box-sizing: border-box;
}

body {
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
}
.btn {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline; /* 부모 요소의 기준선에 맞춤 */
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
.boxTF {
	border: 1px solid #999;
	padding: 5px 5px;
	background-color: #fff;
	border-radius: 4px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td  { padding: 10px 0; vertical-align: middle; }
.table th {background: #eee;}

.table-list td, .table-list th { border: 1px solid #ccc; }
.table-list td { padding: 3px 0; text-align: center; }

.table-list .check {width: 50px; }
.table-list .date { width: 100px; }
.table-list .content { width: 100px; }
.table-list .amount { width: 100px; }
.table-list .memo { width: 200px; }
.table-list .change {width: 20px}
</style>

<script type='text/javascript'>
function settingsInput(option, value) {
	const startDateObj = document.getElementById("startDate");
	const endDateObj = document.getElementById("endDate");
	
	let date = new Date();
	let y = date.getFullYear();
	let m = date.getMonth()+1;
	let d = date.getDate();
	
	endDateObj.value = dateToString(date);
	
	if(option === "day") {
		startDateObj.value = dateToString(date);
	} else if(option === "week") {
		startDateObj.value = dateToString(new Date(y, m-1, d-7));
	} else if(option === "month") {
		let last = (new Date(y, m-value, 0)).getDate();
		if(d > last) {
			d = last;
		}
		startDateObj.value = dateToString(new Date(y, m-1-value, d+1));
	} else if(option === "year") {
		startDateObj.value = dateToString(new Date(y-value, m-1, d+1));
	}
}

function searchList() {
	const f = document.searchForm;
	
	if(! isValidDateFormat(f.startDate.value)) {
		f.startDate.focus();
		return;
	}
	
	if(! isValidDateFormat(f.endDate.value)) {
		f.endDate.focus();
		return;
	}
	
	if(diffDays(f.startDate.value, f.endDate.value) < 0) {
		alert("시작일은 종료일보다 클수 없습니다.");
		f.startDate.focus();
		return;
	}

	if(diffDays(f.endDate.value, dateToString(new Date())) < 0) {
		alert("종료일은 오늘보다 클수 없습니다.");
		f.endDate.focus();
		return;
	}
	
	alert("성공...");
	
}

// 날짜를 문자열로
function dateToString(date) {
	let y = date.getFullYear();
	let m = date.getMonth() + 1;
    if(m < 10) m='0'+m;
    let d = date.getDate();
    if(d < 10) d='0'+d;
    
    return y + '-' + m + '-' + d;
}

// 문자열을 날짜로
function stringToDate(data) {
	if(! isValidDateFormat(data)) {
		throw "날짜 형식이 올바르지 않습니다.";
	}

	let format = /(\.)|(\-)|(\/)/g;
	data = data.replace(format, "");
    
	let y = parseInt(data.substr(0, 4));
	let m = parseInt(data.substr(4, 2));
	let d = parseInt(data.substr(6, 2));
    
    return new Date(y, m-1, d);
}

function isValidDateFormat(data){
	if(data.length !== 8 && data.length !== 10) return false;
		
	let p = /(\.)|(\-)|(\/)/g;
	data = data.replace(p, "");
	if(data.length !== 8) return false;
	
	// let format = /^[12][0-9]{3}[0-9]{2}[0-9]{2}$/;
	let format = /^[12][0-9]{7}$/;
	if(! format.test(data)) return false;
    
	let y = parseInt(data.substr(0, 4));
	let m = parseInt(data.substr(4, 2));
	let d = parseInt(data.substr(6));

	if(m<1 || m>12) return false;
	let lastDay = (new Date(y, m, 0)).getDate();
	if(d<1 || d>lastDay) return false;
	
	return true;
}

// 두 날짜 사이의 일자 구하기
function diffDays(startDate, endDate) {
	if(! isValidDateFormat(startDate) || ! isValidDateFormat(endDate)) {
		throw "날짜 형식이 올바르지 않습니다.";
	}

	let format = /(\.)|(\-)|(\/)/g;
    startDate = startDate.replace(format, "");
    endDate = endDate.replace(format, "");
   
    let sy = parseInt(startDate.substr(0, 4));
    let sm = parseInt(startDate.substr(4, 2));
    let sd = parseInt(startDate.substr(6));
    
    let ey = parseInt(endDate.substr(0, 4));
    let em = parseInt(endDate.substr(4, 2));
    let ed = parseInt(endDate.substr(6));

    let fromDate = new Date(sy, sm-1, sd);
    let toDate = new Date(ey, em-1, ed);
    
    let sn = fromDate.getTime();
    let en = toDate.getTime();
    
    let diff = en-sn;
    let day = Math.floor(diff/(24*3600*1000));
    
    return day;
}

function deleteAccount(num) {
    if(confirm("가계부를 삭제하시겠습니까?")) {
       let url = "${pageContext.request.contextPath}/account/delete.do?page=${page}&accountBook_Num=" + num; 
       location.href = url;
    }
}
 
function updateAccount(num) {
    if(confirm("가계부를 수정하시겠습니까?")) {
       let url = "${pageContext.request.contextPath}/account/update.do?page=${page}&accountBook_Num=" + num; 
       location.href = url;
    }
}

</script>


</head>
<body>

<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container" style="width: 800px; height:700px; margin: 10px auto;">
		<div class="body-title">
				<h3><i class="fas fa-chalkboard-teacher"></i> 가계부 </h3>
		</div>
		
		<form name="searchForm" style="margin: 10px auto;">
	       <button type="button" class="btn" onclick="settingsInput('day', 0);">오늘</button>
	       <button type="button" class="btn" onclick="settingsInput('week', 1);">1주일</button>
	       <button type="button" class="btn" onclick="settingsInput('month', 1);">1개월</button>
	       <button type="button" class="btn" onclick="settingsInput('month', 3);">3개월</button>
	       <button type="button" class="btn" onclick="settingsInput('month', 6);">6개월</button>
	       <button type="button" class="btn" onclick="settingsInput('year', 1);">1년</button>
	       
	       <input type="text" name="startDate" id="startDate" class="boxTF">
	       ~
	       <input type="text" name="endDate" id="endDate" class="boxTF">
	       
	       <button type="button" class="btn" onclick="searchList();">검색</button>
   		</form>
   		
   		
   		<table class="table">
			<tr>
				<td align="right">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/account/write.do';">등록하기</button>
				</td>
			</tr>
		</table>
		
		<form name="listForm" method="post">
		<table class="table table-border table-list">
			<thead>
				<tr align="center">
					<th class="date">날짜</th>
					<th class="content">사용내역</th>
					<th class="amount">사용금액</th>
					<th class="memo">메모</th>
					<th class="change" colspan="2">변경</th>
				</tr>
			</thead>
			
			<tbody>
				
				<c:forEach var="dto" items="${accountList}">
					<tr align="center">
						<td>${dto.accountBook_Date}</td>
						<td>${dto.content}</td>
						<td>${dto.amount} 원</td>
						<td>${dto.memo}</td>
						<td>
						<c:choose>
							<c:when test="${sessionScope.member.userId == dto.user_Id}">
								<button type="button" class="btn" onclick="updateAccount(${dto.accountBook_Num});">수정</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" disabled="disabled">수정</button>
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${sessionScope.member.userId == dto.user_Id}">
								<button type="button" class="btn" onclick="deleteAccount(${dto.accountBook_Num});">삭제</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" disabled="disabled">삭제</button>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>



</html>