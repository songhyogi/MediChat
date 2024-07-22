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

.time-button {
    background-color: transparent;
    border: 1px solid #ccc;
    color: #000;
    padding: 10px 20px;
    cursor: pointer;
    margin: 5px;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.time-button.disabled {
    background-color: transparent;
    color: gray;
    border: 1px solid #d6d6d6;
}

.time-button.disabled:hover {
    background-color: initial; /* 초기값으로 설정하여 hover 스타일을 제거 */
    cursor: default; /* 기본 커서로 설정 */
    color: gray; /* 텍스트 색상을 회색으로 설정 */
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

.confirm-container {
    padding: 20px;
    max-width: 600px;
    margin: 20px auto;
}

.reservation-header h3 {
    margin: 0;
    font-size: 18px;
    color: #333;
}

.reservation-header p {
    margin: 0;
    font-size: 14px;
    color: #666;
}

.reservation-details{
    margin-top: 20px;
    border: 3px solid #ddd;
    border-radius: 5px;
    padding: 15px;
    background-color: #fff;
}

.reservation-userInfo {
    margin-top: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 15px;
    background-color: #fff;
}

.detail-item {
    display: flex;
    justify-content: space-between;
    padding: 10px 0;
    border-bottom: 1px solid #eee;
}

.detail-item:last-child {
    border-bottom: none;
}

.detail-item label {
    font-weight: bold;
    color: #333;
}

.detail-item span, .detail-item textarea {
    color: #333;
    width: 70%;
}

.detail-item textarea {
    height: 80px;
    resize: none;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 5px;
}

.reservation-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.btn-primary {
    background-color: #007bff;
    color: #fff;
}

.btn-secondary {
    background-color: #6c757d;
    color: #fff;
}
.next-button-container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin: 0 90px 20px 0;
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
    <div id="next-button-container" class="next-button-container"></div>
</div>
<input type="hidden" id="user_mem_num" value="${user.mem_num}" />
<div class="confirm-container" style="display:none;">
	<div class="reservation-header">
		<h3 id="confirm-resType"></h3>
        <p>예약 정보를 다시 한 번 확인해 주세요</p>
	</div>
	<div class="reservation-details">
		<div class="detail-item">
            <label>병원 | </label>
            <span id="confirm-hospital"></span>
        </div>
        <div class="detail-item">
            <label>의사 | </label>
            <span id="confirm-doctor"></span>
        </div>
        <div class="detail-item">
            <label>날짜 | </label>
            <span id="confirm-date"></span>
        </div>
        <div class="detail-item">
            <label>시간 | </label>
            <span id="confirm-time"></span>
        </div>
	</div>
	<div class="reservation-userInfo">
	<h4>예약자 정보</h4>
		<div class="detail-item">
            <label>이름 | </label>
            <span id="confirm-name">${user.mem_name}</span>
        </div>
        <div class="detail-item">
            <label>전화번호 | </label>
            <span id="confirm-phone">${user.mem_phone}</span>
        </div>
        <div class="detail-item">
            <label>상세 증상 | </label>
            <textarea id="confirm-symptoms"></textarea>
        </div>
    </div>
	<div class="reservation-footer">
        <input type="button" class="btn btn-secondary" value="이전" onclick="previousStep()">
        <input type="button" class="btn btn-primary" value="동의하고 예약하기" onclick="submitReservation()">
    </div>
    
</div>