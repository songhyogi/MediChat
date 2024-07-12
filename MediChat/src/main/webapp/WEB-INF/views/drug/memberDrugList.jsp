<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/drug.member.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            selectable: true, // 사용자가 날짜를 선택할 수 있게 설정
            height: 'auto',
            width: 'auto',
            headerToolbar: {
            	left: 'title',
                right: 'today prev,next dayGridMonth multiMonthYear'
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
<div>
	<input type="button" value="등록">
</div>
<br>
<!-- 모달 시작 -->
<div class="modal-">
	<div class="modal-header">
		<div>의약품 복용 기록 등록</div>
		<div class="close">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="drugSearch">
		<ul>
			<li>
				증상 : <input type="text" class="check">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off" class="check">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				복용일자 : <input type="date" class="check">
			</li>
			<li>
				복용시간 : 
				<select>
					<option>아침</option>
					<option>점심</option>
					<option>저녁</option>
					<option>자기 전</option>
				</select>
			</li>	
			<li>
				복용량 : <input type="text" class="check"><br>
				메모 : <textarea></textarea>
			</li>	
		</ul>
		<div>
			<input type="submit" value="전송">
		</div>
	</form>
	</div>
</div>
<!-- 모달 끝 -->
<br>
<!-- 캘린더 -->
<div id='calendar'></div>
<br>