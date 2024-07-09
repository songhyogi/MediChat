<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
	<span><a href="location.href='memberLogin'">일반회원 로그인</a></span>
	<span><a href="location.href='${pageContext.request.contextPath}/doctor/doctorLogin'">의사회원 로그인</a></span>
</div>