<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- 비대면진료 채팅창 시작 -->
	<div class="chat-main col-9">
		<div class="chat-header bg-green-7" id="chat_header">
			<!-- 예약내역을 클릭한 경우에만 예약번호와 진료일시, 의사 표시 -->
		</div>
		<!-- 채팅방 표시 시작 -->
		<div class="chat-body" id="chat_body">
		</div>
		<!-- 입력창 표시 -->
		<div class="chat-input">
			<form action="#" id="chat_input">
				<input type="hidden" name="chat_num" id="chat_num" value="">
				<input type="hidden" name="res_date" id="res_date" value="">
				<input type="hidden" name="res_time" id="res_time" value="">
				<input type="hidden" name="res_num" id="res_num" value="">
				<c:if test="${user.mem_auth==1 || user.mem_auth==2}">
					<input type="hidden" name="msg_sender_type" value="0">
				</c:if>
				<c:if test="${user.mem_auth==3}">
					<input type="hidden" name="msg_sender_type" value="1">
				</c:if>
				<input type="button" class="bg-green-7">
				<input type="text" name="msg_content" id="msg_content">
				<input type="submit" value="전송" id="message_btn" class="btn-green">
			</form>
		</div>
</div>