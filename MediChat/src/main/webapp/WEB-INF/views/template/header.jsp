<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<div id="header_box" class="d-flex justify-content-between">
	<div class="d-flex justify-content-start">
		<!-- 로고 시작 -->
		<div>
			<a href="/main/main"><img src="/images/logo.jpg" width="270" height="80"></a>
		</div>
		<!-- 로고 끝 -->
		<!-- 메뉴 시작 -->
		<div class="d-flex justify-content-between align-items-center">
			<div class="header-menu">
				<a href="/hospitals" class="header-menu-text">병원 찾기</a>
			</div>
			<div class="header-menu">
				<a href="#" class="header-menu-text">약국 찾기</a>
			</div>
			<div class="header-menu">
				<a href="#" class="header-menu-text">건강 블로그</a>
			</div>
			<div class="header-menu">
				<a href="/disease/diseaseDictionary" class="header-menu-text">질병 백과사전</a>
			</div>
			<div class="header-menu">
				<a href="/durg/search" class="header-menu-text">약품 백과사전</a>
			</div>
			<div class="header-menu">
				<a href="#" class="header-menu-text">고객센터</a>
			</div>
		</div>
		<!-- 메뉴 끝 -->
	</div>
	<div class="d-flex justify-content-end align-items-center">
		<!-- 검색 시작 -->
		<form id="header-search-form" class="d-flex">
			<input type="text" id="header-search-input" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상">
			<i id="header-search-icon" class="bi bi-search" id=""></i>
		</form>
		<!-- 검색 끝 -->
		<c:if test="${empty user}">
		<!-- 로그인/회원가입 시작 -->
		<div id="header-status-logout" class=" d-flex">
			<div class="header-status-leftBox">
				<a id="header-status-logout-text" href="#">로그인</a>
			</div>
			<div class="header-status-rightBox">
				<a id="header-status-logout-text" href="#">회원가입</a>
			</div>
		</div>
		<!-- 로그인/회원가입 끝 -->
		</c:if>
		<c:if test="${!empty user}">
		<!-- 알림 + 프로필 시작 -->
		<div id="header-status-login" class="d-flex">
			<div class="header-status-leftBox">
				<a href="#"><img id="header-notification" src="/images/notification.png" width="35" height="35"></a>
			</div>
			<div class="header-status-rightBox">
				<a href="#"><img id="header-profile" src="${pageContext.request.contextPath}/image_bundle/face.png" width="40" height="40" class="border rounded-circle"></a>
			</div>
		</div>
		<!-- 알림 + 프로필 끝 -->
		</c:if>
	</div>
</div>
<!-- 상단 끝 -->





