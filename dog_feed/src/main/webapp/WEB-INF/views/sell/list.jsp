<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- DTO, DAO(dataCount, list) // list.jsp -->  

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
			<p>
			</p>
			<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4">
				<c:forEach var="dto" items="${list}">
				
					<div class="col mb-5">
						<div class="card h-100">
							<!-- Product image-->
							<c:choose>
								<c:when test="${not empty dto.image_Name}">
									<img class="card-img-top" src="${pageContext.request.contextPath}/uploads/management/${dto.image_Name}">
								</c:when>
								<c:otherwise>
									<img class="card-img-top" src="${pageContext.request.contextPath}/resource/img/noimage.png">
								</c:otherwise>
							</c:choose>
							
							<!-- Product details-->
							<div class="card-body p-4">
								<div class="text-center">
									<!-- Product name-->
									<h5 class="fw-bolder">${dto.product_Name}</h5>
									<!-- Product price-->
									&#8361;	${dto.product_Price} 원
								</div>	
							</div>
							<!-- Product actions-->
							<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
								<div class="text-center">
									<a class="btn btn-outline-dark mt-auto" href="${articleUrl}&product_Num=${dto.product_Num}&bUri=${bUri}">상세정보</a>
								</div>
							</div>
							
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>