<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<link href="${pageContext.request.contextPath}/css/fullcalendar.bundle.min.css" rel="stylesheet" />
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            selectable: true, // 사용자가 날짜를 선택할 수 있게 설정
            height: 'auto',
            headerToolbar: {
                left: 'prev,next',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
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
            datesSet: function(info) {
                // 모든 날짜 셀을 순회하면서 요일을 콘솔에 출력
                setTimeout(function() { // 렌더링이 끝난 후 실행되도록 설정
                    var dayCells = document.querySelectorAll('.fc-daygrid-day');
                    dayCells.forEach(function(cell) {
                        var dateStr = cell.getAttribute('data-date');
                        if (dateStr) {
                            var date = new Date(dateStr);
                            console.log(dateStr + ' 요일: ' + date.getDay());
                        }
                    });
                }, 0);
            }
        });
        calendar.render();
    });
</script>
<style>
#calendar {
    margin: 0 auto; /* 가운데 정렬을 위한 margin */
    width: 80%;
    max-width: 800px; /* 최대 너비 */
}

.dot {
    height: 10px;
    width: 10px;
    border-radius: 50%;
    display: inline-block;
    position: absolute;
    bottom: 5px;
    right: 5px;
}
</style>
<div id='calendar'></div>
<div id="time-buttons"></div>
