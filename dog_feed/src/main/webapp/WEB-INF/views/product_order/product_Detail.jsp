<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 주문</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

*, ::after, ::before {
	box-sizing: border-box;
}

body {
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
}

a {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

a:active, a:hover {
	text-decoration: underline;
	color: #F28011;
}

.btn {
	padding: 5px 10px;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	color: white;
	font-weight: 500;
	border: 1px solid #999;
	border-radius: 4px;
	background-color: black;
	cursor: pointer;
	vertical-align: baseline;
}

.btn:active, .btn:focus, .btn:hover {
	color: white;
	background : #5C5C5C;
	font-weight: bolder;
}

.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	filter: alpha(opacity = 65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
	cursor: not-allowed;
}

.form-control {
	padding: 5px 5px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	border: 1px solid #999;
	border-radius: 4px;
	background-color: #fff;
	vertical-align: baseline;
	text-align: center;
	margin: 0px auto;
}

.form-control[readonly] {
	background-color: white;
	font-weight: 550;
}

textarea.form-control {
	height: 170px;
	resize: none;
}

.form-select {
	padding: 4px 5px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	border: 1px solid #999;
	border-radius: 4px;
	background-color: #fff;
	vertical-align: baseline;
}

.form-select[readonly] {
	background-color: #f8f9fa;
}

textarea:focus, input:focus {
	outline: none;
}

input[type=checkbox], input[type=radio] {
	vertical-align: middle;
}

.table {
	width: 100%;
	border-spacing: 0;
	border-collapse: collapse;
}

.table th, .table td {
	padding-top: 10px;
	padding-bottom: 10px;
}

.table-border thead>tr {
	border-top: 2px solid #212529;
	border-bottom: 1px solid #ced4da;
}

.table-border tbody>tr {
	border-bottom: 1px solid #ced4da;
}

.table-border tfoot>tr {
	border-bottom: 1px solid #ced4da;
}

.td-border td {
	border: 1px solid #EAEAEA;
}

.left {
	text-align: left;
	padding-left: 5px;
}

.center {
	text-align: center;
}

.right {
	text-align: center;
	padding-right: 5px;
}

.body-container {
	width: 1000px;
	margin: 30px auto 10px;
}

.product-body {
	width: 100%;
}

.product-body:after {
	content: "";
	clear: both;
	display: block;
}

.product-body table thead>tr:first-child {
	background: #f8f8f8;
}

.product-body th, .product-body td {
	text-align: center;
}

.product-body table tfoot>tr:first-child {
	background: #f6f6f6;
}

.product-left {
	width: 400px;
	float: left;
}

.product-right {
	width: 580px;
	float: left;
	margin-left: 20px;
	padding: 0 5px 5px;
}

.buyAdd {
	cursor: pointer;
	width: 300px;
	height: 40px;
	text-align: center;
	background: black;
	color: white;
	border-radius: 3px;
	display: block;
	margin: 20px auto;
}

.buyAdd:active, .buyAdd:focus, .buyAdd:hover {
	font-weight: 700;
}

.input_totalAmt {
	width: 100px;
	height: 25px;
	text-align: center;
	background-color: #f8f9fa;
	border: 1px solid #999;
	border-radius: 4px;
}

.input_totalQty {
	width: 50px;
	height: 30px;
	text-align: center;
	font-weight: bold;
	background-color: #f6f6f6;
	border: none;
	border-radius: 4px;
	font-size: 14px;
}

.input_product_Price {
	width: 50px;
	height: 30px;
	text-align: center;
	font-weight: 550;
	border: none;
	border-radius: 4px;
	font-size: 16px;
}

.card-img-top {
	width: 300px;
	height: 300px;
}

.product_title {
	text-align: center;
	font-weight: 600;
	font-family: 맑은고딕;
	margin-bottom: 20px;
}

.infoImg {
	width: 500px;
	height: 600px;
}

.explanation {
text-align: center;
    font-size: 25px;
    color: balck;
    font-weight: 550;
    background: #f8f9fa;
    border-bottom: 3px solid black;
    border-top: 1px solid lightgray;
    padding-top: 15px;
    padding-bottom: 15px;
}



</style>
<script type="text/javascript"> 
$(function(){
	$("body").on("click", ".btnPlus", function(){
		let code = $(this).siblings("input[name=code]").val();
		let price = parseInt($(this).parent().next().children(".buyCancel").attr("data-price"));
		let qty = parseInt($(this).parent().children("input[name=quantity]").val());
		let totalBuyQty = parseInt($("#totalBuyQty").val());
		let totalBuyAmt = parseInt($("#totalBuyAmt").val());

		qty = qty+1;
		$(this).parent().children("input[name=quantity]").val(qty);
		
		totalBuyQty = totalBuyQty+1;
		totalBuyAmt = totalBuyAmt+price;
		$("#totalBuyQty").val(totalBuyQty);
		$("#totalBuyAmt").val(totalBuyAmt)

	});

	$("body").on("click", ".btnMinus", function(){
		let code = $(this).siblings("input[name=code]").val();
		let price = parseInt($(this).parent().next().children(".buyCancel").attr("data-price"));
		let qty = parseInt($(this).parent().children("input[name=quantity]").val());
		let totalBuyQty = parseInt($("#totalBuyQty").val());
		let totalBuyAmt = parseInt($("#totalBuyAmt").val());
		if(qty <= 1)
			return;
		
		qty = qty-1;
		$(this).parent().children("input[name=quantity]").val(qty);
		
		totalBuyQty = totalBuyQty-1;
		totalBuyAmt = totalBuyAmt-price;
		$("#totalBuyAmt").val(totalBuyAmt)
		$("#totalBuyQty").val(totalBuyQty);
	});
});

function product_order() {
	const f =
	document.buyForm;

		f.action = "${pageContext.request.contextPath}/main/productOrder_ok.do";
		f.submit();

	}
</script>


</head>
<body>
<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   <!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>

	<!-- Section-->
	<section class="py-5">
		<div class="body-container">
			<div class="product-body">
				<div class="product-left">
					<table class="table " style="height: 324px;">
						<tbody>
							<tr>
								<td><c:choose>
										<c:when test="${not empty Photo}">
											<img class="card-img-top"
												src="${pageContext.request.contextPath}/uploads/management/${Photo.image_Name}">
										</c:when>
										<c:otherwise>
											<img class="card-img-top"
												src="${pageContext.request.contextPath}/resource/img/no_Img2.jpg">
										</c:otherwise>
									</c:choose></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="product-right">
					<h3 class = "product_title">${dto.product_Name}</h3>
					<form name="buyForm" method="post">
						<table class="table table-border">
							<thead>
								<tr>
									<td class="left" colspan="3"><span
										style="font-weight: 700; padding-left: 10px;">주문</span></td>
									<td>
									<td><input type="hidden" name=product_Num
										value="${dto.product_Num}"></td>
							</thead>
							<tbody id="buyList">
								<tr id="buyTr100" style="height: 67px;">
									<td class="center" style="font-weight: 550; padding-top: 20px; border: none;">${dto.product_Name}</td>
									<td class="right" style="display: flex; ">
									
										<input type="text" name="quantity"
										class="form-control" style="width: 30%; " value="1" readonly="readonly"> 		
										<input type="button" class="btn btnPlus" value="+" style="margin-right: 5px;"> 
										<input type="button" class="btn btnMinus" value="-"> 
										<input type="hidden" class="code" value="code">
									</td>
									<td class="right"><input type="text"
										name="OrderDetail_Price" class="input_product_Price"
										value="${dto.product_Price}"> 
										<span class="buyCancel"	data-code="100" data-price="${dto.product_Price}">원</span></td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td class="right" colspan="5" >
										<p>
											<span class="input_totalQty">총수량 : </span><input
												class="input_totalQty" name="orderDetail_Quant"
												id="totalBuyQty" value="1" readonly="readonly"
												style="color: #2eb1d3; font-size: 16px;"> <span
												style="padding-right: 10px; font-weight: 700;">개</span>&nbsp;&nbsp;|&nbsp;&nbsp;
											<span style="font-weight: 700;">총 상품금액 : </span><input
												type="text" class="input_totalAmt" id="totalBuyAmt"
												style="font-weight: 900; color: #2eb1d3; font-size: 17px;"
												name="order_Price" value="${dto.product_Price}"
												readonly="readonly"> <span
												style="padding-right: 10px; font-weight: 700;">원</span>
										</p>
									</td>
								</tr>
								<tr>
									<c:if test="${sessionScope.member.userRoll != 1}">
										<td colspan="3">
											<button type="button" class="buyAdd"
												onclick="product_order();" data-code="100" data-price="1300">주문</button></td>
									</c:if>
								</tr>
							</tfoot>
						</table>
					</form>
				</div>
			</div>
				<p  class="explanation">상품설명</p>
		</div>
		
				<table class = "center" style="margin: 15px auto; ">

						<tr>
							<td style="font-size: 18px; font-weight: 550; margin-bottom: 15px; height: 100px;">	
								&lt; ${dto.product_Info} &gt;
							</td>
						</tr>
					<c:forEach var="dto" items="${listImg}">
						<tr>
							<td>
								<img class="infoImg" 
								src="${pageContext.request.contextPath}/uploads/management/${dto.image_Name}">
							</td>
						</tr>
					</c:forEach>
					<tr>
					<td class="left">
						<button type="button" class="buyAdd" onclick="location.href='${pageContext.request.contextPath}/main/main.do'" style="margin-top: 50px;">목록</button>
					</td>
					</tr>
				</table>
	</section>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>

</body>
</html>