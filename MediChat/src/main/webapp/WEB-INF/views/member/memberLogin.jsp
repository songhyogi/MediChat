<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
	<form:form action="login" id="member_login" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color"/>
		<input type="hidden" id="lon" name="user_lon" value="" >
		<input type="hidden" id="lat" name="user_lat" value="" >
		<ul>
			<li class="floating-label">
				<form:input path="mem_id" placeholder="아이디" autocomplete="off" cssClass="form-input"/>
				<form:label path="mem_id">아이디</form:label>
				<form:errors path="mem_id" cssClass="error-color"/>
			</li>
			<li class="floating-label">
				<form:password path="mem_passwd" placeholder="비밀번호" cssClass="form-input"/>
				<form:label path="mem_passwd">비밀번호</form:label>
				<form:errors path="mem_passwd" cssClass="error-color"/>
			</li>
		</ul>
		<div>
			<form:button path="login-btn">로그인</form:button>
		</div>
		<div>
			<label for="auto">
			<input type="checkbox" name="auto" id="auto">자동로그인</label>
			<input type="button" value="아이디 찾기">
			<input type="button" value="비밀번호 찾기">
		</div>
	</form:form>
	<hr size="1" width="80%" noshade="noshade">
	<!--  카카오 로그인 시작 -->
	<img src="${pageContext.request.contextPath}/images/kakao_login_medium_narrow.png" width="200">
	<!--  카카오 로그인 끝 -->
	<!--  네이버 로그인 시작 -->
	<img src="${pageContext.request.contextPath}/images/naver_login.png" width="200">
	<!--  네이버 로그인 끝 -->
</div>
<script>
	window.onload = function(){
		const log_btn = document.getElementById('login-btn');
		log_btn.onsubmit= function(){
			//기본값 설정
			const defaultLat = 37.4981646510326;
			const defaultLon = 127.028307900881;
			
			// 위치 정보 가져오기
			if (navigator.geolocation) {
			  navigator.geolocation.getCurrentPosition(
			    function(position) {
			      const lat = position.coords.latitude;
			      const lon = position.coords.longitude;
			    },
			    function(error) {
			      const lat = defaultLat;
			      const lon = defaultLon;
			    }
			  );
			} else {
			  const lat = defaultLat;
			  const lon = defaultLon;
			}
			document.getElementById('lat').value = lat;
		    document.getElementById('lon').value = lon;
		};
	}
		
</script>