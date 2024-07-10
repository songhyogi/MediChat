<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- 비대면진료 채팅창 시작 -->
<div class="chat-main">
	<div class="chat-header">
		<!-- 예약내역을 클릭한 경우에만 예약번호와 진료일시, 의사 표시 -->
		<div id="chat_title">
			예약번호: ${reservation.res_num}
			${reservation.res_date} ${reservation.res_time}
		</div>
		<!-- 채팅창 표시 시작 -->
	</div>
</div>
<!-- 비대면진료 채팅창 끝 -->