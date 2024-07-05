<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
	document.addEventListener('DOMContentLoaded', function() {
		var calendarEl = document.getElementById('calendar');
		
		var calendar = new FullCalendar.Calendar(calendarEl, {
			selectable : true,
			height:'auto',
			width:'auto',
			headerToolbar: {
				left:'prev',
				center:'title',
				right:'next'
			},
			initialView : 'dayGridMonth',
			locale: 'ko',
			dayCellContent: function(e) {//1일,2일 -> '일'을 빼고 1,2만 보이게
	            var dayNumber = e.date.getDate(); // 날짜에서 일(day) 값을 가져옴
	            var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>'; // 숫자만 포함하는 HTML 생성
	            return { html: html }; // 생성된 HTML 반환
	        },
			fixedWeekCount:false //다음 달의 날짜가 보여지지 않게
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
</style>
<div id='calendar'></div>