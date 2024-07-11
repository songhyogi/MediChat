<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 의약품 목록 상세 시작 -->
<div class="page-main">
	<div class="body-header">
		<h2>의약품 상세 정보</h2>
		<span>홈 > 의약품 백과사전 > 의약품 상세정보</span> <br><br>
	</div>
	<div class="drug-body">
		<h3>제품 기본 정보</h3>
		<table>
			<tr>
				<td>제품명</td>
				<td>${drug.drg_name}</td>
				<td rowspan="3">
					<c:if test="${empty drug.drg_img}">
						<img src="${pageContext.request.contextPath}/images/noIMG.png"width="100">
					</c:if>
					<c:if test="${!empty drug.drg_img}">
						<img class="drg-img" src="${drug.drg_img}" width="200">
					</c:if>
				</td>
			</tr>
			<tr>
				<td>업체명</td>
				<td>${drug.drg_company}</td>
			</tr>
			<tr>
				<td>품목 기준 코드</td>
				<td>${drug.drg_code}</td>
			</tr>
		</table>
	</div>
	<br>
	<div class="drug-body">
		<h3>허가정보 · 복약정보</h3>
		<!-- 모든 데이터가 존재하지 않을 경우 -->
		<c:if test="${empty drug.drg_dosage && empty drug.drg_effect && empty drug.drg_warning && empty drug.drg_precaution && empty drug.drg_interaction && empty drug.drg_seffect && empty drug.drg_storage}">
			<div class="warin">
				제공되는 데이터가 없습니다
			</div>
		</c:if>
		<!-- 데이터가 하나 이상 존재할 경우 -->
		<c:if test="${!empty drug.drg_dosage || !empty drug.drg_effect || !empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction || !empty drug.drg_seffect || !empty drug.drg_storage}">
		<div class="drug-btn">
			<c:if test="${!empty drug.drg_dosage}">
				<button class="button">복용법</button>
			</c:if>
			<c:if test="${!empty drug.drg_effect}">
				<button class="button">효능·효과</button>
			</c:if>
			<c:if test="${!empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction}">
				<button class="button">주의사항</button>
			</c:if>
			<c:if test="${!empty drug.drg_seffect}">
				<button class="button">부작용</button>
			</c:if>
			<c:if test="${!empty drug.drg_storage}">
				<button class="button">보관방법</button>
			</c:if>
		</div>
		<br>
		<ul>
			<c:if test="${!empty drug.drg_dosage}">
				<li>
					<h4>복용법</h4>
					<div>${drug.drg_dosage}</div>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_effect}">
				<li>
					<h4>효능·효과</h4>
					<div>${drug.drg_effect}</div>
				</li>	
			</c:if>
			<c:if test="${!empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction}">
				<li>
					<h4>주의사항</h4>
					<c:if test="${!empty drug.drg_warning}">
						<div>${drug.drg_warning}</div><br>
					</c:if>
					<c:if test="${!empty drug.drg_precaution}">
						<div>${drug.drg_precaution}</div><br>
					</c:if>
					<c:if test="${!empty drug.drg_interaction}">
						<div>${drug.drg_interaction}</div>
					</c:if>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_seffect}">
				<li>
					<h4>부작용</h4>
					<div>${drug.drg_seffect}</div>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_storage}">
				<li>
					<h4>보관방법</h4>
					<div>${drug.drg_storage}</div>
				</li>
			</c:if>
		</ul>
		</c:if>
	</div>
</div>
<!-- 의약품 목록 상세 끝 -->
<!-- 모달창 시작 -->
<div class="modal">
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
<!-- 모달창 끝 -->
<!-- 스크립트 시작 -->
<script>
window.onload=function(){
	const modal = document.querySelector(".modal");
	const drgImg = document.querySelector(".drg-img");
	const close = document.querySelector(".close");
	
	//모달창 열기
	drgImg.onclick = function(){
		modal.style.display = "block";
	};
	
	//모달창 닫기
	close.onclick = function(){
		modal.style.display = "none";
	};
	
	// 모달 창 외부를 클릭하면 모달 창 닫기
	if (event.target == modal) {
		modal.style.display = "none";
	}

};
</script>
<!-- 스크립트 끝 -->