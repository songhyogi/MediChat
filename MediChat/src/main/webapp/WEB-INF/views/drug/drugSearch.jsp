<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 의약품 검색 시작 -->
<div class="page-main">
	<h2>의약품 상세검색</h2>
	<span>홈 > 의약품 백과사전</span>
	<!-- 의약품 검색 -->
	<form action="search" id="search_form" method="get" class="align-center">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제품명</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>회사명</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>효능</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form><br>
	<c:if test="${count == 0}">
		<div class="result-display">표시할 게시물이 없습니다</div>
	</c:if>
	<c:if test="${count > 0}">
		<table class="drug">
			<tr>
				<th>식별</th>
				<th>제품명</th>
				<th>회사명</th>
				<th>효능</th>
			</tr>
			<c:forEach var="drug" items="${list}">
			<tr>
				<td>
					<div>
						<c:if test="${empty drug.drg_img}">
							<img src="${pageContext.request.contextPath}/images/noIMG.png" width="20">
						</c:if>
						<c:if test="${!empty drug.drg_img}">
							<img class="img" src="${drug.drg_img}" width="40">
						</c:if>
					</div>
				</td>
				<td><a href="detail?drg_num=${drug.drg_num}"> ${drug.drg_name}</a></td>
				<td>${drug.drg_company}</td>
				<td><img src="${pageContext.request.contextPath}/images/magnifier.png" width="20"></td>
			</tr>
			</c:forEach>
		</table><br>
	</c:if>
	<div>${page}</div>
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
