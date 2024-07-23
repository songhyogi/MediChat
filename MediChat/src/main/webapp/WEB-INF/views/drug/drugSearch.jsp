<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.align-center {/* 가운데 정렬 */
  text-align: center;
}
ul {
    list-style-type: none; /* 모든 ul 태그의 점 제거 */
}

ul li {
    list-style-type: none; /* 각각의 li 태그의 점 제거 */
     display: inline-block;
    margin-right: 10px; /* 각 항목 사이의 간격 조정 */
}
a {
    text-decoration: none;
    color: inherit; /* 기본 색상 상속 */
}	
</style>
<!-- 의약품 검색 시작 -->
<div class="page-main">
	<p class="text-lightgray fw-7 fs-13">홈 > 의약품 백과사전</p>
	<h4>의약품 상세검색</h4>
	
	<!-- 의약품 검색 -->
	<form action="search" id="search_form" method="get" class="align-center">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제품명</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>회사명</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>효능</option>
				</select>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
				<input type="submit" value="검색">
			</li>
			<%-- <li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li> --%>
		</ul>
	</form><br>
	<c:if test="${count == 0}">
		<div class="result-display">표시할 게시물이 없습니다</div>
	</c:if>
	<c:if test="${count > 0}">
		<table class="table table-hover">
			<tr class="table-light align-center">
				<th>번호</th>
				<th>제품명</th>
				<th>회사명</th>
				<th>효능</th>
			</tr>
			<c:forEach var="drug" items="${list}">
			<tr>
				<td>${drug.drg_num}</td>
				<td><a href="detail?drg_num=${drug.drg_num}"> ${drug.drg_name}</a></td>
				<td class="align-center">${drug.drg_company}</td>
				<td class="align-center"><img src="${pageContext.request.contextPath}/images/magnifier.png" width="20"></td>
			</tr>
			</c:forEach>
		</table><br>
	</c:if>
	<nav aria-label="Page navigation example">
		<div class="align-center">${page}</div>
	</nav>
	<!-- 모달 시작 --> 	
	<div class="modal">
		<!-- 이미지 모달 -->
		<div class="drugImg-modal">
			<div class="modal-header">
				<div>의약품 이미지 확대보기</div>
				<div class="close">&times;</div>
			</div>
			<div class="modal-body">
				<img class="drugModal-img" src="${drug.drg_img}" width="300">
			</div>
		<br>
		</div>
	</div>
	<!-- 모달 끝 --> 	
</div>
<!-- 의약품 검색 끝 -->
<!-- 
스크립트 시작
<script>
$(document).ready(function(){
	const imgModal = document.querySelector("#img_modal")
	const img = document.querySelector(".img")
	const modal_img = document.querySelector("#imgModal")
	const close = document.querySelector(".close")
	
	$(".img").click(function(){//이미지 클릭 이벤트
		modal.style.display = "block";
		modalImg.src = this.src;
	});
	
	// 모달 닫기
	close.onclick = function() { 
		modal.style.display = "none";
	}

	// 모달 외부 클릭 시 닫기
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
});
</script>
스크립트 끝 -->
