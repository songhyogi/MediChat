<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 비대면 진료 채팅창 목록 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="nav-body">
	<table class="nav-item">
		<tr class="nav-title">
			<th>나의 비대면 진료</th>
		</tr>
		<!-- 예약 내역 없는 경우 "예정된 비대면 진료 채팅방이 없습니다" 출력(c:if 사용) -->
		
		<!-- 예약된 비대면 진료 채팅방이 있는 경우(예약 목록대로 채팅방 목록 출력, c:forEach 사용) -->
		<c:forEach var="res" items="${reservation}">
		<!-- li에 들어가야 할 항목(일반회원)- 진료일시, 의사명 -->
		<!-- li에 들어가야 할 항목(의사회원)- 진료일시, 환자명 -->
		<tr>
			<td>${res.res_date} ${res.res_time}</td>
			<td>
		</tr>
		</c:forEach>
	</table>
</div>
<!-- 비대면 진료 채팅창 목록 끝 -->