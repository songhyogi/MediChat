<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/schedule.list.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 세션에서 가져온 doc_num과 regularDayOff를 초기화
    const doc_num = ${doc_num};
    const regularDayOffStr = '${regularDayOff}';
    const regularDayOff = regularDayOffStr ? regularDayOffStr.split(',').map(Number) : [];

    let holidays = []; // 휴일 정보를 저장할 배열
    let modifiedTimes = {}; // 수정된 시간 데이터를 저장할 객체

    // FullCalendar 초기화
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        selectable: true, // 사용자가 날짜를 선택할 수 있게 설정
        height: 'auto',
        width: 'auto',
        headerToolbar: {
            left: 'title',
            right: 'today prev,next'
        },
        initialView: 'dayGridMonth', // 초기 보여줄 뷰 설정
        locale: 'ko', // 로케일 설정
        dayCellContent: function(e) {
            // 날짜 셀 내용 설정 (1일, 2일 -> '일' 빼고 1, 2만 보이게)
            var dayNumber = e.date.getDate();
            var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
            return { html: html };
        },
        fixedWeekCount: false, // 다음 달의 날짜가 보여지지 않게 설정
        dateClick: function(info) {
            // 날짜 클릭 시 호출될 함수
            displayTimes(info.dateStr);
        },
        dayCellDidMount: function(info) {
            // 특정 날짜 셀에 클래스를 적용하는 함수 호출
            applyClassesToCell(info.el, info.date);
        },
        datesSet: function(info) {
            // datesSet 이벤트는 달력이 새로 렌더링될 때마다 호출됨
            const days = document.querySelectorAll('.fc-daygrid-day');
            days.forEach(dayEl => {
                const date = new Date(dayEl.getAttribute('data-date'));
                applyClassesToCell(dayEl, date);
            });
        }
    });
    calendar.render();
});
</script>
<style>
#calendar {
    margin: 0 auto;
    width: 80%;
    max-width: 800px;
}
.regular-day-off {
    background-color: #f2f2f2;
}
.time-off {
    background-color: #f2f2f2;
}
.working-time {
    background-color: rgb(153, 204, 204);
}
.working-time-red {
    background-color: rgb(153, 204, 204);
}
#time-buttons {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
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
    width: 195px;
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
.fc-day-sun a {
    color: red;
    text-decoration: none;
}
.fc-day-sat a {
    color: blue;
    text-decoration: none;
}
.fc-prev-button, .fc-next-button, .fc-today-button {
    width: 60px;
    font-size: 10px;
}
.button-row {
    display: flex;
    justify-content: center;
    margin-top: 10px;
}
.modify-btn, .cancel-btn, .complete-modify-btn {
    width: 300px;
}
.selected-time {
    background-color: orange;
}
.active {
    pointer-events: auto;
}
</style>
<div id="calendar"></div>
<div id="time-buttons"></div>