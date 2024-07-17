<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<div id="header_box" class="d-flex justify-content-between">
	<div class="d-flex justify-content-start">
		<!-- 로고 시작 -->
		<div>
			<a href="/main/main"><img src="/images/logo8.png" width="220" height="70"></a>
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
				<a href="/drug/search" class="header-menu-text">의약품 백과사전</a>
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
			<i id="header-search-icon" class="bi bi-search"></i>
		</form>
		<!-- 검색 끝 -->
		<c:if test="${empty user}">
		<!-- 로그인/회원가입 시작 -->
		<div id="header-status-logout" class=" d-flex">
			<div class="header-status-leftBox">
				<a id="header-login-text">로그인</a>
			</div>
			<div class="header-status-rightBox">
				<a id="header-register-text">회원가입</a>
			</div>
			<div id="header-register-div" style="display: none;">
				<div class="header-register-select">
					<a href="/member/registerUser">일반 회원가입</a>
				</div>
				<div class="select-line"></div>
				<div class="header-register-select">
					<a href="/doctor/registerDoc">의사 회원가입</a>
				</div>
			</div>
		</div>
		<div id="header-login-div" style="display: none;">
			<div class="header-login-select">
				<a href="/member/login">일반 로그인</a>
			</div>
			<div class="select-line"></div>
			<div class="header-login-select">
				<a href="/doctor/login">의사 로그인</a>
			</div>
		</div>
		
		<!-- 로그인/회원가입 끝 -->
		</c:if>
		<c:if test="${!empty user}">
		<!-- 알림 + 프로필 시작 -->
		<div id="header-status-login" class="d-flex align-items-center">
			<div class="header-status-leftBox">
				<a href="#"><img id="header-notification" src="/images/notification.png" width="35" height="35"></a>
			</div>
			<c:if test="${user.mem_auth==2}">
				<div class="header-status-rightBox">
					<div class="text-center">
						<img id="header-profile" src="${pageContext.request.contextPath}/member/memPhotoView" width="40" height="40" class="border rounded-circle">
			    	</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/member/myPage">내 정보</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/member/logout">로그아웃</a>
					</div>
				</div>
			</c:if>
			<c:if test="${user.mem_auth==3}">
				<div class="header-status-rightBox">
					<div class="text-center">
				    	<img id="header-profile" src="${pageContext.request.contextPath}/doctor/docPhotoView" width="40" height="40" class="border rounded-circle">
					</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/doctor/docPage">내 정보</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/doctor/logout">로그아웃</a>
				   </div>
				</div>
			</c:if>
			<c:if test="${user.mem_auth==9}">
				<div class="header-status-rightBox">
					<div class="text-center">
						<img id="header-profile" src="${pageContext.request.contextPath}/member/memPhotoView" width="40" height="40" class="border rounded-circle">
			    	</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/doctor/agree">의사회원가입 승인</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/member/logout">로그아웃</a>
					</div>
				</div>
			</c:if>
		</div>
		<!-- 알림 + 프로필 끝 -->
		</c:if>
	</div>
</div>
<!-- 상단 끝 -->

<script>
	const header_profile = document.getElementById('header-profile');
	const header_status_div = document.getElementById('header-status-div');
	if(${!empty user}){
		header_profile.onclick = function(){
			if(header_status_div.style.display == 'block'){
				header_status_div.style.display = 'none';
			} else {
				header_status_div.style.display = 'block';
			}
		};
	}
	const loginText = document.getElementById('header-login-text');
	const headerLoginDiv = document.getElementById('header-login-div');
	loginText.onclick = function(){
		if(headerLoginDiv.style.display == 'block'){
			headerLoginDiv.style.display = 'none';
		} else {
			headerLoginDiv.style.display = 'block';
		}
	};
	const registerText = document.getElementById('header-register-text');
	const headerRegisterDiv = document.getElementById('header-register-div');
	registerText.onclick = function(){
		if(headerRegisterDiv.style.display == 'block'){
			headerRegisterDiv.style.display = 'none';
		} else {
			headerRegisterDiv.style.display = 'block';
		}
	};
</script>