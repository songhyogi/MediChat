<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- 비대면진료 채팅창 시작 -->
<jsp:include page="/WEB-INF/views/chat/chatNav.jsp" />
<div class="chat-main">
	<div class="chat-header">
		<!-- 예약내역을 클릭한 경우에만 예약번호와 진료일시, 의사 표시 -->
		<c:if test="${empty chat_num}">
		<div id="chat_title">
			예약번호: ${chat.res_num}
			${chat.res_date} ${chat.res_time}
		</div>
		</c:if>
		<c:if test="${user.mem_num == chat.doc_num}">
			<input type="button" value="진료 종료" id="chat_close">
		</c:if>
	</div>
	<div class="chat-body">
		<c:if test="${empty chat_num}">
		<div class="chat-empty">
			<img id="chat_bubble" src="${pageContext.request.contextPath}/images/chat_bubble.png">
			<h2>좌측의 채팅방을 선택하세요</h2>
		</div>
		</c:if>
		
		<c:if test="${!empty chat_num}">
			<!-- 채팅 표시 부분 -->
		</c:if>
	</div>
</div>
<!-- 비대면진료 채팅창 끝 -->