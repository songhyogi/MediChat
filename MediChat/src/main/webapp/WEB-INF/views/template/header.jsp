<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->

<div class="d-flex justify-content-between p-5">
	<div class="d-flex justify-content-start">
		<!-- 로고 시작 -->
		<div>
			<a href="#" class="text-decoration-none text-dark"><h2>MediChat</h2></a>
		</div>
		<!-- 로고 끝 -->
		<!-- 메뉴 시작 -->
		<div class="d-flex justify-content-between">
			<div>
				<a href="#" class="text-decoration-none text-dark">비대면 진료</a>
			</div>
			<div>
				<a href="#" class="text-decoration-none text-dark">병원 찾기</a>
			</div>
			<div>
				<a href="#" class="text-decoration-none text-dark">약국 찾기</a>
			</div>
			<div>
				<a href="#" class="text-decoration-none text-dark">건강 블로그</a>
			</div>
			<div>
				<a href="#" class="text-decoration-none text-dark">질병 백과사전</a>
			</div>
			<div>
				<a href="#" class="text-decoration-none text-dark">약품 백과사전</a>
			</div>
		</div>
		<!-- 메뉴 끝 -->
	</div>
	<div class="d-flex justify-content-end">
		<!-- 검색 시작 -->
		<div class="d-flex">
			<form class="control-form">
				<input type="text" placeholder="검색"><button class="btn btn-primary">전송</button></input>
			</form>
		</div>
		<!-- 검색 끝 -->
		<c:if test="${!empty user}">
		<!-- 로그인/회원가입 시작 -->
		<div class="d-flex">
			<div>
				<a href="#">로그인</a>
			</div>
			<div>
				<a href="#">회원가입</a>
			</div>
		</div>
		<!-- 로그인/회원가입 끝 -->
		</c:if>
		<c:if test="${empty user}">
		<!-- 알림 + 프로필 시작 -->
		<div class="d-flex">
			<div>
				<a href="#">알림</a>
			</div>
			<div>
				<a href="#">프로필 사진</a>
			</div>
		</div>
		<!-- 알림 + 프로필 끝 -->
		</c:if>
	</div>
</div>
<!-- 상단 끝 -->





