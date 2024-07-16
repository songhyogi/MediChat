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
				<button type="button" class="chat-image bg-green-7" id="chat_image" disabled>
					<img src="${pageContext.request.contextPath}/images/chat_image.png" width="40px" height="40px">
				</button>
				<input type="text" name="msg_content" id="msg_content" disabled>
				<input type="submit" value="전송" id="message_btn" class="btn-green" disabled>
			</form>
		</div>
	</div>
<!-- 비대면 진료 채팅창 끝 -->

<!-- 이미지 전송 시 모달 -->
	<div class="image-form">
		<div class="form-title bg-green-7 fs-18">
		파일 전송
			<button type="button" class="close-button">&times;</button>
		</div>
		<div class="form-body">
		<form action="image_input" id="image_input">
			<label for="select_image" class="">파일 선택</label>
			<input type="file" id="select_image" accept="image/gif,image/png,image/jpeg">
			<input type="submit" value="전송">
		</form>
		</div>
	</div>
<div class="image-form-bg"></div>
<!-- 모달 창 끝 -->