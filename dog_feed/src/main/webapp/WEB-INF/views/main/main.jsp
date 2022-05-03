<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강아지 사료 판매사이트에 오신걸 환영합니다.</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.card-img-top {
	height: 200px;
}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
$(function(){	
	let product = JSON.parse(localStorage.getItem("productToday")) || [];
	
	$(".todayList").empty();
	
	product.forEach(function(data) {
		let productNum = data.productNum;
		let productName = data.productName;
		let productImg = data.productImg;
		let productPrice = data.productPrice;
		
		
		let p;
		p  = '<div class="col mb-5">';
		p += '   <div class="card h-100" ">';
		p += '      <img class="card-img-top" src="${pageContext.request.contextPath}/uploads/management/'+productImg+'">';
		p += '      <div class="card-body p-4">';
		p += '          <div class="text-center">';
		p += '             <h5 class="fw-bolder">' + productName + '</h5>';
		p += '   			 &#8361; '+ productPrice +' 원'		
		p += '          </div>';
		p += '      </div>';
		
		p += '    	<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">'
		p += '      	<div class="text-center">' 
		p += '             <a class="btn btn-outline-dark mt-auto" data-productNum="${dto.product_Num}" href="${pageContext.request.contextPath}/main/product_Detail.do?product_Num='+productNum+'"> 주문하기</a>'
		p += '           </div>'
		p += '      </div>'
		
		p += '   </div>';
		p += '</div>';

		$(".todayList").append(p);
	});
});

$(function(){
	$(".btnOrder").click(function(){
		if(typeof(Storage) === "undefined") {
			return false;
		}
		
		let productNum = $(this).attr("data-productNum");
		let productName = $(this).attr("data-productName");
		let productImg = $(this).attr("data-productImg");
		let productPrice = $(this).attr("data-productPrice");
		
		
		let product = JSON.parse(localStorage.getItem("productToday")) || [];
		
		// 스트로지에 저장된 상품중 선택한 상품과 동일한 상품코드의 데이터 삭제
		product.forEach(function(data) {
			if(data.productNum == productNum) {
				var idx = product.indexOf(data);
				if (idx > -1) product.splice(idx, 1); 
				return;
			}
		});
		
		
		if(product.length >= 4) {
			product.splice(product.length-1, 1); // 배열 마지막 데이터 삭제
		}
		
		let obj = {productNum:productNum, productName:productName, productImg:productImg, productPrice:productPrice};
		product.unshift(obj); // 배열 가장 앞에 추가
		
		// 웹스트로지에 저장
		let p = JSON.stringify(product);
		localStorage.setItem("productToday", p);

		// 상품 상세 보기 페이지로 이동

		var url = "${pageContext.request.contextPath}/main/product_Detail.do?product_Num="+productNum;
		location.href = url;

	});
});

$(function(){
	$(".btnOrder2").click(function(){
		if(typeof(Storage) === "undefined") {
			return false;
		}
		
		let productNum = $(this).attr("data-productNum");
	
		// 상품 상세 보기 페이지로 이동

		var url = "${pageContext.request.contextPath}/main/product_Detail.do?product_Num="+productNum;
		location.href = url;

	});
});


</script>
</head>

<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   <!-- Header-->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>


	<!-- Section-->
	<section class="py-5">
		<div class="container px-4 px-lg-5 mt-5">
			<p style="font-weight: bold; font-size: 30px;"> 신상품 </p>
			
			<div
				class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
		 <c:forEach var="dto" items="${newList}">
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Product image-->
							<c:choose>
								<c:when test="${not empty dto.image_Name}">
									<img class="card-img-top" src="${pageContext.request.contextPath}/uploads/management/${dto.image_Name}">
								</c:when>
								<c:otherwise>
									<img class="card-img-top" src="${pageContext.request.contextPath}/resource/img/no_Img2.jpg">
								</c:otherwise>
							</c:choose>			
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">${dto.product_Name}</h5>

								<!-- Product price-->
								 &#8361; ${dto.product_Price} 원
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center">
								<a class="btn btn-outline-dark mt-auto btnOrder" data-productNum="${dto.product_Num}" data-productName="${dto.product_Name}" data-productImg="${dto.image_Name}" data-productPrice="${dto.product_Price}"> 주문하기</a>
							</div>
						</div>						
					</div>
				</div>
		   </c:forEach>
			</div>
			
		</div>
		
		<!--  오늘본 상품 -->
		<div class="container px-4 px-lg-5 mt-5">
			<p style="font-weight: bold; font-size: 30px;"> 최근 본 상품 </p>
			<div
				class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 todayList">
				
								
				
			</div>
		</div>
	</section>
	
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body> </html>