<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담</p>


	<button onclick="location.href='/consultings/create'">글 작성</button>
	
	<c:forEach items="${consultingList}" var="consulting">
		<div>
			<a href="/consultings/detail/${consulting.con_num}">${consulting.con_title}</a>
		</div>
	</c:forEach>
</div>