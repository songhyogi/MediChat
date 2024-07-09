<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<h2>의약품 상세검색</h2>
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
				<c:if test="${empty drug.drg_img}">
					<td><img src="${pageContext.request.contextPath}/images/noIMG.png" width="20"></td>
				</c:if>
				<c:if test="${!empty drug.drg_img}">
					<td><img src="${drug.drg_img}" width="40"></td>
				</c:if>
				<td>${drug.drg_name}</td>
				<td>${drug.drg_company}</td>
				<td><img src="${pageContext.request.contextPath}/images/magnifier.png" width="20"></td>
			</tr>
			</c:forEach>
		</table><br>
	</c:if>
	
	<!-- 모달 시작 -->
</div>