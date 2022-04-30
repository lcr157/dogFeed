<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${mode=='update'?"상품수정":"상품등록"}</title>

<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" type="text/css"/>
<!-- admin css가져오기 -->
<link href="${pageContext.request.contextPath}/resource/css/admin.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
h3 {
	margin-left: 300px;
}

.table {
	width:60%;
}

.table td {
	text-align: left;
}

.table tr:first-child {
	border-top: 2px solid #212529; 
}

.table tr > td:first-child {
	width: 150px; text-align: center; background: #f8f8f8;
}

.table-submit {
	width:60%; margin: 0 auto; 
	text-align: center;
	border: none;
}

.img-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, 65px);
	grid-gap: 5px;
}

.img-grid .item {
    object-fit: cover; /* 가로세로 비율은 유지하면서 컨테이너에 꽉 차도록 설정 */
    width: 65px;
    height: 65px;
	cursor: pointer;
}

.img-box {
	max-width: 600px;
	padding: 5px;
	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}

.img-box img {
	width: 37px; height: 37px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}
</style> 

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function sendBoard() {
	const f = document.boardForm;
	let str;
	
	str = f.product_Name.value.trim();
	if(!str){
		alert("상품명을 입력하세요");
		f.product_Name.focus();
		return;
	}
	
	str = f.product_Price.value.trim();
	if(! str){
		alert("상품가격을 입력하세요");
		f.product_Price.focus();
		return;
	}
	
	str = f.selectFile.value.trim();
	if(!str && ${mode=="write"}){
		alert("상품 사진을 넣어주세요");
		return;
	}	
	
	f.action="${pageContext.request.contextPath}/admin/${mode}_ok.do";
	f.submit();
}


$(function() {
	var sel_files = [];
	
	$("body").on("click", ".table-form .img-add", function(event){
		$("form[name=boardForm] input[name=selectFile]").trigger("click"); 
	});
	
	$("form[name=boardForm] input[name=selectFile]").change(function(){
		if(! this.files) {
			let dt = new DataTransfer();
			for(file of sel_files) {
				dt.items.add(file);
			}
			document.boardForm.selectFile.files = dt.files;
			
	    	return false;
	    }
	    
		// 유사 배열을 배열로 변환
        const fileArr = Array.from(this.files);

		fileArr.forEach((file, index) => {
			sel_files.push(file);
			
			const reader = new FileReader();
			const $img = $("<img>", {class:"item img-item"});
			$img.attr("data-filename", file.name);
            reader.onload = e => {
            	$img.attr("src", e.target.result);
            };
            
            reader.readAsDataURL(file);
            
            $(".img-grid").append($img);
        });
		
		let dt = new DataTransfer();
		for(file of sel_files) {
			dt.items.add(file);
		}
		document.boardForm.selectFile.files = dt.files;		
	    
	});
	
	$("body").on("click", ".table-form .img-item", function(event) {
		if(! confirm("선택한 파일을 삭제 하시겠습니까 ?")) {
			return false;
		}
		
		let filename = $(this).attr("data-filename");
		
	    for(let i = 0; i < sel_files.length; i++) {
	    	if(filename === sel_files[i].name){
	    		sel_files.splice(i, 1);
	    		break;
			}
	    }

		let dt = new DataTransfer();
		for(file of sel_files) {
			dt.items.add(file);
		}
		document.boardForm.selectFile.files = dt.files;
		
		$(this).remove();
	});
	
});


<c:if test="${mode=='update'}">
function deleteFile(image_Num) {
	if(! confirm("이미지를 삭제 하시겠습니까 ?")) {
		return;
	}
	
	let query = "num=${dto.product_Num}&image_Num=" + image_Num + "&page=${page}";
	let url = "${pageContext.request.contextPath}/admin/deleteFile.do?" + query;
	location.href = url;
}
</c:if>

</script>
</head>


<body>
	<!-- navigation -->
	<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	<!-- Header -->
	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>
	
	
	<main>
		<h3>${mode=='update'?"상품수정":"상품등록"}</h3>
		<p></p>
		
		<form name="boardForm" method="post" enctype="multipart/form-data">
			<table class="table table-form">
				<tr>
					<td>상품명</td>
					<td>
						<input type="text" name="product_Name" maxlength="100" class="form-control" value="${dto.product_Name}">
					</td>
				</tr>
				
				<tr>
					<td>작성자</td>
					<td>관리자</td>
				</tr>
				
				<tr>
					<td>카테고리</td>
					<td>
						<select name="categoryDetail_Name">
							<option value="feed" ${dto.categoryDetail_Name =="사료" ? "selected='selected'" : ""}>사료</option>
							<option value="snack" ${dto.categoryDetail_Name =="간식" ? "selected='selected'" : ""}>간식</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>카테고리 종류</td>
					<td>
						<select name="category_Num">
							<option value="1">사료 - 소프트</option>
							<option value="2">사료 - 하드</option>
							<option value="3">간식 - 건식</option>
							<option value="4">간식 - 껌</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>상품가격</td>
					<td> <input type="number" name="product_Price" value="${dto.product_Price}"> 원 </td>
				</tr>
				
				<tr> 
					<td style="height: 300px;">상품설명</td>
					<td> 
						<textarea style="height: 300px;" name="product_Info" class="form-control">${dto.product_Info}</textarea>
					</td>
				</tr>
				
				<tr>
					<td>공개여부</td>
					<td>
						<select name="product_Privacy">
							<option value="1" ${dto.product_Privacy =="1" ? "selected='selected'" : "" }>공개</option>
							<option value="0" ${dto.product_Privacy =="0" ? "selected='selected'" : "" }>비공개</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>사&nbsp;&nbsp;&nbsp;&nbsp;진</td>
					<td style="text-align: left;"> 
						<div class="img-grid"><img class="item img-add" src="${pageContext.request.contextPath}/resource/assets/add_photo.png"></div>
						<input type="file" name="selectFile" accept="image/*" multiple="multiple" style="display: none;" class="form-control">
					</td>
				</tr>
			
			<c:if test="${mode=='update'}">
					<tr>
						<td>첨부된 사진</td>
						<td> 
							<div class="img-box">
								<c:forEach var="vo" items="${listFile}">
									<img src="${pageContext.request.contextPath}/uploads/management/${vo.image_Name}"
									onclick="deleteFile('${vo.image_Num}');">
								</c:forEach>
							</div>
						</td>
					</tr>
				</c:if>
			</table>
			<p></p>
			
			<table class="table-submit">
				<tr> 
					<td align="center">
						<button class="btn" type="button" onclick="sendBoard();">${mode=='update'?"수정완료":"등록하기"}</button>
						<button class="btn" type="reset">다시입력</button>
						<button class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/admin/management.do';">${mode=="update"?"수정취소":"등록취소"}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="num" value="${dto.product_Num}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
					</td>
				</tr>
			</table>
		</form>
		<p></p>
		
	</main>


	<!-- footer -->
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	
</body> </html>