<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 회원탈퇴 시작 -->
<div>
	<h2>회원탈퇴</h2>
	<form:form action="deleteDoctor" id="doctor_delete" modelAttribute="doctorVO">
		<form:hidden path="mem_num"/>
		<ul>
			<li>
				<form:label path="mem_id">아이디</form:label>
				<form:input path="mem_id" placeholder="아이디" autocomplete="off"/>
				<form:errors path="mem_id" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_passwd">비밀번호</form:label>
				<form:password path="doc_passwd" placeholder="비밀번호"/>
				<form:errors path="doc_passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_email">이메일</form:label>
				<form:input path="doc_email"/>
				<form:errors path="doc_email" cssClass="error-color"/>
			</li>
		</ul>
		<div>
			<form:button class="default-btn">회원탈퇴</form:button>
			<input type="button" value="MY페이지" class="default-btn" onclick="location.href='docPage'">
		</div>
	</form:form>
</div>
<!-- 회원탈퇴 끝 -->