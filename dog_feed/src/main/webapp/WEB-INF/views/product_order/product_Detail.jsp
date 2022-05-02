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
<style type="text/css">
* { margin: 0; padding: 0; box-sizing: border-box; }
*, ::after, ::before { box-sizing: border-box; }

body { font-size: 14px; font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif; }

a { color: #000; text-decoration: none; cursor: pointer; }
a:active, a:hover { text-decoration: underline; color: #F28011; }

.btn {
	padding: 5px 10px;
	font-size: 14px; font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	color: #333; font-weight: 500;
	border: 1px solid #999; border-radius: 4px;
	background-color: #fff;
	cursor:pointer;
	vertical-align: baseline;
}
.btn:active, .btn:focus, .btn:hover {
	color:#333;
	background-color: #f8f9fa;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
	cursor: not-allowed;
}

.form-control {
	padding: 5px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #212529; border-bottom: 1px solid #ced4da; }
.table-border tbody > tr { border-bottom: 1px solid #ced4da; }
.table-border tfoot > tr { border-bottom: 1px solid #ced4da; }
.td-border td { border: 1px solid #EAEAEA; }

.left { text-align: left; padding-left: 5px; }
.center { text-align: center; }
.right { text-align: center; padding-right: 5px; }

.body-container { width: 1000px; margin: 30px auto 10px; }

.product-body { width: 100%; }
.product-body:after { content: ""; clear: both; display: block; }

.product-body table thead > tr:first-child { background: #f8f8f8; }
.product-body th, .product-body td { text-align: center; }
.product-body table tfoot > tr:first-child { background: #f6f6f6; }

.product-left { width: 400px; float: left; }
.product-right { width: 580px; float: left; margin-left: 20px; padding: 0 5px 5px; }

.buyAdd { 
cursor: pointer; width: 300px; height: 40px; text-align: center; 
background: black; color: white; border-radius: 3px; display: block; margin: 0px auto;}
.buyAdd:active, .buyAdd:focus, .buyAdd:hover { font-weight: 700; }

.input_totalAmt {
	width: 100px;
	height : 25px;
	text-align: center;
	background-color: #f8f9fa; 
	border: 1px solid #999;
    border-radius: 4px;
}

.input_totalQty {
	width: 50px;
	height : 30px;
	text-align: center;
	font-weight : bold;
	background-color:#f6f6f6; 
	border: none;
    border-radius: 4px;
    font-size: 14px;
}

.input_product_Price {
	width: 50px;
	height : 30px;
	text-align: center;
	font-weight : bold;
	border: none;
    border-radius: 4px;
    font-size: 14px;
}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
	const f = document.buyForm;
	
	f.action = "${pageContext.request.contextPath}/main/productOrder_ok.do";
	f.submit();
	
}
</script>


</head>
<body>
 <div class="body-container">
 	<div class="product-body">
		<div class="product-left">
			<table class="table ">
				<tbody>
				<tr>
					<td>
					<c:choose>
						<c:when test="${not empty dto.image_Name}">
							<img class="card-img-top" src="${pageContext.request.contextPath}/uploads/main/${dto.image_Name}">
						</c:when>
						<c:otherwise>
							<img class="card-img-top" src="${pageContext.request.contextPath}/resource/img/no_Img2.jpg">
						</c:otherwise>
					</c:choose>							
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="product-right">

		    <form name="buyForm" method="post">
			    <table class="table table-border">
			        <thead>
				    	<tr>
				    		<td class="left" colspan="3"><span style="font-weight: 700; padding-left: 10px;">주문</span></td>
				    		<td>				    		<td>
				    		<input type="hidden" name=product_Num value="${dto.product_Num}">
				    		</td>

			    	</thead>
			    	<tbody id="buyList">
			    	<tr id="buyTr100"> 
			    		<td class="center"> ${dto.product_Name} </td>
			    		<td class="right"><input type="text" name="quantity" class="form-control" style="width: 30%;" value="1" readonly="readonly"> 
			    				<input type="button" class="btn btnPlus" value="+">	
			    				<input type="button" class="btn btnMinus" value="-">
			    				<input type="hidden" class="code" value="code">
			    		</td>
			    		<td class="right"> 
			    		<input type="text" name = "OrderDetail_Price" class="input_product_Price" value="${dto.product_Price}"> 
			    		<span class="buyCancel" data-code="100" data-price="${dto.product_Price}">원</span>		
			    		</td>		    		
			    		</tr>
			    	</tbody>
			    			<tfoot>
				    	<tr>
				    		<td class="right" colspan="3">
				    		<p>
				    		   <span class="input_totalQty">총수량 : </span><input class="input_totalQty" name="orderDetail_Quant" id="totalBuyQty" value="1" readonly="readonly" style="color: #2eb1d3;">
				    		   <span style="padding-right: 10px; font-weight: 700; ">개</span>&nbsp;&nbsp;|&nbsp;&nbsp;
				    		   <span style="font-weight: 700;">총 상품금액 : </span><input type="text"  class="input_totalAmt" id="totalBuyAmt" style="font-weight: 900; color: #2eb1d3; font-size: 17px;" name="order_Price" value="${dto.product_Price}" readonly="readonly">
				    		   <span style="padding-right: 10px; font-weight: 700;">원</span>
				    		</p>
				    		 </td>
				    	</tr>
				    	<tr>
						<td colspan="3"><button type="button" class="buyAdd" onclick="product_order();" data-code="100" data-price="1300" >주문</button></td>
					</tr>
			    	</tfoot>
			    </table>
			</form>
			
		</div>
	</div>
 </div>
</body>
</html>