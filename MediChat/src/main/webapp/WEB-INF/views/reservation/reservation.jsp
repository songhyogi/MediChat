<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
#calendar {
    margin: 50px auto 0;
    width: 80%;
    max-width: 800px;
}
.fc .fc-button-primary{
    background-color: #fff;
    border-color: #fff;
    color: #000;
    width: 80px;
}
.fc .fc-button-primary:hover{
    background-color: #d8f3dc;
    border-color: #fff;
    color: #fff;
}
.fc .fc-button-primary:focus, .fc .fc-button-primary:active {
    outline: none;
    box-shadow: none;
}
.fc .fc-button-primary:active{
    background-color: transparent !important;
    outline: none !important;
    box-shadow: none !important;
    border-color: transparent !important;
}
.fc .fc-button-primary:disabled{
    background-color: #fff;
    border-color: #fff;
    color: #000;
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
.fc .fc-daygrid-day.fc-day-today {
    background-color: #f6fff7;
}
.fc .fc-highlight{
    background-color: #d8f3dc;
}
.time-header {
    margin-left: 10px;
    /* font-weight: bold; */
    /* color: #757575; */
}
#time-buttons {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
}
button, .time-button {
    background-color: transparent;
    border: 1px solid #ccc;
    color: #000;
    padding: 10px 20px;
    cursor: pointer;
    margin: 7px;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;
    font-size: 16px;
    width: 180px; /* 버튼의 너비를 통일 */
    height: 45px; /* 버튼의 높이를 통일 */
}
button:disabled, .time-button.disabled {
    background-color: transparent;
    color: gray;
    border: 1px solid #d6d6d6;
    cursor: default; /* 기본 커서로 설정 */
    opacity: 1;
}
button:hover, .time-button:hover {
    background-color: #f2fcf3;
}
button.selected, .time-button.selected {
    background-color: #d8f3dc;
}
.time-section {
    margin-bottom: 20px;
    text-align: left;
}
.time-row {
    display: flex;
    justify-content: left;
    margin-bottom: 10px;
    width: 100%;
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
    margin-bottom: 40px;
}
.doctor-card {
    width: 200px;
    margin: 10px;
    text-align: center;
}
.doctor-image {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    object-fit: cover;
    margin-bottom: 12px;
}
.doctor-name{
	margin-bottom: 15px;
	font-size: 1em;
	font-weight: bold;
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
.reservation-details {
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
    width: 100px; /* 버튼의 너비 조정 */
    height: 50px; /* 버튼의 높이 조정 */
    font-size: 15px; /* 폰트 크기 조정 */
    border-radius: 10px; /* 모서리 반경 조정 */
    margin-right: 5px;
}
.btn-primary {
    background-color: #d8f3dc;
    border-color: #d8f3dc;
    color: white; /* 텍스트 색상 변경 */
}
.btn-primary:hover {
    background-color: #b7e4c7;
    border-color: #b7e4c7;
    color: white; /* 텍스트 색상 변경 */
}
.btn-primary.active {
    background-color: #74c69d; /* 활성화 상태 색상 */
    border-color: #74c69d;
    color: #fff;
}
.btn-primary:disabled {
    background-color: #b7e4c7; /* 비활성화 상태 색상 */
    border-color: #b7e4c7;
    color: #fff;
    opacity: 0.6; /* 비활성화 상태 불투명도 */
    cursor: not-allowed; /* 비활성화 상태 커서 */
}
.btn-secondary {
    background-color: #bdbdbd;
    border-color: #bdbdbd;
    color: white; /* 텍스트 색상 변경 */
}
.btn-secondary:hover {
    background-color: #e0e0e0;
    border-color: #e0e0e0;
    color: white; /* 텍스트 색상 변경 */
}
.btn-secondary.active {
    background-color: #bdbdbd; /* 활성화 상태 색상 */
    border-color: #bdbdbd;
    color: #fff;
}
.btn-secondary.active:hover {
    background-color: #e0e0e0;
    border-color: #e0e0e0;
    color: #fff;
}
.next-button-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
}
.next-button-container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin: 0 103px 20px 0;
}
.res-type-container label {
    display: block;
    margin: 5px 0 5px 52px;
}
.no-time {
    padding: 100px 275px; /* 패딩 값 설정 */
    margin: 20px 0; /* 위아래 여백 설정 */
    text-align: center; /* 텍스트 중앙 정렬 */
    border-radius: 10px; /* 모서리를 둥글게 설정 */
    background-color: #f8f9fa; /* 배경색 설정 */
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
        <c:if test="${user_type != 'doctor'}">
        <div class="detail-item">
            <label>전화번호 | </label>
            <span id="confirm-phone">${user.mem_phone}</span>
        </div>
        </c:if>
        <div class="detail-item">
            <label>상세 증상 | </label>
            <textarea id="confirm-symptoms"></textarea>
        </div>
    </div>
	<div class="reservation-footer">
        <input type="button" class="btn prev-btn" value="이전" onclick="previousStep()">
        <input type="button" class="btn agree-btn" value="동의하고 예약하기" onclick="submitReservation()">
    </div>
    
</div>