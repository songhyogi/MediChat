<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 의약품 목록 상세 시작 -->
<div class="page-main">
	<h2>의약품 상세 정보</h2>
	<span>홈 > 의약품 백과사전 > 의약품 상세정보</span>
	<br>
	<h3>제품 기본 정보</h3>
	<table>
		<tr>
			<td>제품명</td>
			<td>${drug.drg_name}</td>
			<td rowspan="3">
				<c:if test="${empty drug.drg_img}">
					<img src="${pageContext.request.contextPath}/images/noIMG.png" width="20">
				</c:if>
				<c:if test="${!empty drug.drg_img}">
					<img class="img" src="${drug.drg_img}" width="200">
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
	<br>
	<h3>허가정보 · 복약정보</h3>
	<ul>
		<li>
			<button class="button">복용법</button>
			<button class="button">효능·효과</button>
			<button class="button">주의사항</button>
			<button class="button">부작용</button>
			<button class="button">보관방법</button>
		</li>
		<li>
			<h4>복용법</h4>
			<div>${drug.drg_dosage}</div>
		</li>
		<li>
			<h4>효능·효과</h4>
			<div>${drug.drg_effect}</div>
		</li>
		<li>
			<h4>주의사항</h4>
			<div>${drug.drg_warning}</div><br>
			<div>${drug.drg_precaution}</div><br>
			<div>${drug.drg_interaction}</div>
		</li>
		<li>
			<h4>부작용</h4>
			<div>${drug.drg_seffect}</div>
		</li>
		<li>
			<h4>보관방법</h4>
			<div>${drug.drg_storage}</div>
		</li>
	</ul>
	
</div>
<!-- 의약품 목록 상세 끝 -->