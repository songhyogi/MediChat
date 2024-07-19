<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
#calendar {
	margin: 0 auto;
	width: 80%;
	max-width: 800px;
}
.fc-day-sun a {
	color: red;
	text-decoration: none;
}
.fc-day-sat a {
	color: blue;
	text-decoration: none;
}
.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color: black;
    text-decoration: none;
}
#time-buttons {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 20px;
}
.time-button.selected {
	background-color: rgb(153, 204, 204);
}
.time-button:hover {
	background-color: rgb(204, 231, 231);
}
.time-section {
	margin-bottom: 20px;
	text-align: center;
}

.time-row {
	display: flex;
	justify-content: center;
	margin-bottom: 10px;
}

button {
	width: 140px;
	height: 45px;
	margin: 5px;
	font-size: 16px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

button:disabled {
	opacity: 1;
}

.button-row {
	display: flex;
	justify-content: center;
	margin-top: 10px;
}

.reserve-btn {
	width: 100px;
}
.doctor-section {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	margin-top: 20px;
}

.doctor-card {
	width: 200px;
	margin: 10px;
	text-align: center;
}

.doctor-image {
	width: 100px;
	height: 100px;
	border-radius: 50%;
	object-fit: cover;
}

.res-type-container {
	margin-top: 10px;
	text-align: left;
}
</style>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/reservation.reservation.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const hos_num = '${hos_num}';
    initializeCalendar(hos_num);
    initializeNextButton(); // 페이지 로드 시 다음 버튼 초기화
});
</script>
<c:set var="user" value="${sessionScope.user}" />
<div class="reservation-container">
    <div id="calendar"></div>
    <div id="time-buttons"></div>
    <div id="doctor-info"></div>
    <div id="next-button-container"></div>
</div>

<div class="confirm-container" style="display:none;">
    <div class="confirm-title">예약 정보를 다시 한 번 확인해 주세요</div>
    <div class="confirm-info">
    	<div>진료 유형: <span id="confirm-resType"></span></div>
        <div>병원: <span id="confirm-hospital"></span></div>
        <div>의사: <span id="confirm-doctor"></span></div>
        <div>날짜: <span id="confirm-date"></span></div>
        <div>시간: <span id="confirm-time"></span></div>
    </div>
    <div class="confirm-info">
        <div>이름: <span id="confirm-name">${user.mem_name}</span></div>
        <div>전화번호: <span id="confirm-phone">${user.mem_phone}</span></div>
        <div>상세 증상: <textarea id="confirm-symptoms"></textarea></div>
    </div>
    <div class="confirm-buttons">
        <input type="button" class="btn btn-secondary" value="이전" onclick="previousStep()">
        <input type="button" class="btn btn-primary" value="동의하고 예약하기" onclick="submitReservation()">
    </div>
</div>