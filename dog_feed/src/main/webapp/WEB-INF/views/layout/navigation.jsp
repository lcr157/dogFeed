<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container px-4 px-lg-5">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/main/main.do">로고이름</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/main/main.do">홈</a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">제품</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/sell/list.do">전체보기</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/sell/feed_list.do">사료</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/sell/snack_list.do">간식</a></li>
                    </ul>
                </li>
                
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">커뮤니티</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/notice/list.do">공지사항</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/faq/list.do">자주 묻는 질문</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/qna/list.do">Q&amp;A</a></li>
                    </ul>
                </li>
                
                <c:if test="${sessionScope.member.userRoll == 0}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">마이페이지</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/order/order.do">구매내역</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/account.do">가게부</a></li>
                    </ul>
                </li>
                </c:if>
                
                <c:if test="${sessionScope.member.userRoll == 1}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">관리자메뉴</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/management.do">상품관리</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/salesStatus.do">판매현황</a></li>
                        <li><a class="dropdown-item" href="#!">고객정보(보류)</a></li>
                    </ul>
                </li>
                </c:if>
                
                <c:if test="${sessionScope.member.userRoll != 1}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="visibility:hidden" >관리자메뉴</a>
                </li>
                </c:if>
                
            </ul>
          	</div>
            
            <div class="navbar-collapse" id="navbarSupportedContent" style="flex-grow: 0;">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
					<c:if test="${empty sessionScope.member}">
	                	<li class="nav-item dropdown" style="text-align:right;">
	                		<a class="nav-link" href="${pageContext.request.contextPath}/member/login.do">로그인</a>
	                	</li>
	                	
	                	<li class="nav-item dropdown">
	                		<a class="nav-link" href="${pageContext.request.contextPath}/member/member.do">회원가입</a>
	                	</li>
	            	</c:if>
	            	
	            	
		            <c:if test="${not empty sessionScope.member}">
		                <li class="nav-item dropdown" style="color:blue; padding: 8px 0;">
							${sessionScope.member.userName}
						</li>
							<li class="nav-item dropdown" style="padding: 8px;">
							님&nbsp;|&nbsp;
						</li>
		                
		                <li class="nav-item dropdown">
							<a class="nav-link" href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
						</li>
						
						
						<c:if test="${sessionScope.member.userRoll == 0}">
							<li class="nav-item dropdown">
								<a class="nav-link" href="${pageContext.request.contextPath}/member/pwd.do?mode=update">정보수정</a>
							</li>
							
						</c:if>
					</c:if>
				</ul>
			</div>
      </div>
</nav>