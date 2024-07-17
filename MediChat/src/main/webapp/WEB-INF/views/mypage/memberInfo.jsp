<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<body>
	<div class="memberInfo">
		<ul>
			<li>이름 : ${member.mem_name}</li>
			<li>생년월일 : ${member.mem_birth}</li>
			<li>전화번호 : ${member.mem_phone}</li>
			<li>이메일 : ${member.mem_email}</li>
			<li>우편번호 : ${member.mem_zipcode}</li>
			<li>주소 : ${member.mem_address1} ${member.mem_address2}</li>
			<li>가입일 : ${member.mem_reg}</li>
			<c:if test="${!empty member.mem_modify}">
				<li>정보 수정일 : ${member.mem_modify}</li>
			</c:if>
		</ul>
		<div class="align-center">
			<input type="button" value="회원정보 수정"
				onclick="location.href='memberModify.jsp'"> <input
				type="button" value="비밀번호 변경" onclick="location.href='#'">
		</div>
	</div>
</body>
</html>