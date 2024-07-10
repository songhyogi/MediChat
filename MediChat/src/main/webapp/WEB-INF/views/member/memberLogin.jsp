<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
	<form:form action="login" id="member_login" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color"/>
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
			<form:button id="login_btn">로그인</form:button>
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
