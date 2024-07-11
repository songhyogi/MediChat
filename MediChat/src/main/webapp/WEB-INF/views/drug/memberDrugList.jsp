<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            selectable: true, // 사용자가 날짜를 선택할 수 있게 설정
            height: 'auto',
            width: 'auto',
            headerToolbar: {
                left: 'prev',
                center: 'title',
                right: 'next'
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
        });
        calendar.render();
    });
</script>
<div id='calendar'></div>