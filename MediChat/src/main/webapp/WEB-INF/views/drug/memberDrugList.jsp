<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/drug.member.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {//'DOMContentLoaded'이벤트는 문서 내 DOM이 완전히 로드되고 파싱되었을 때 발생
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
        	timeZone: 'Asia/Seoul',	//날짜를 한국 기준으로 설정
            selectable: true,	//날짜 선택가능
            height: 'auto',	//높이 자동 조절
            width: 'auto',	//너비 자동 조절
            headerToolbar: {
            	left: 'title',
                right: 'today prev,next dayGridMonth multiMonthYear'
            },
            locale: 'ko', // 로케일 설정
            initialDate: new Date().toISOString().slice(0, 10), // 오늘 날짜로 설정
            initialView: 'dayGridMonth', // 초기 보여줄 뷰 설정
            dayCellContent: function(e) {
                // 날짜 셀 내용 설정 (1일, 2일 -> '일' 빼고 1, 2만 보이게)
                var dayNumber = e.date.getDate();
                var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
                return { html: html };
            },
            fixedWeekCount: false, // 다음 달의 날짜가 보여지지 않게 설정 
            select:function(arg){ //오늘 이후 날짜 선택 불가
            	var today = new Date(); //현재 날짜 및 시간
                var selectedDate = new Date(arg.start);	//선택한 날짜
                selectedDate.setHours(0, 0, 0, 0);	//선택한 날짜의 시간 초기화(한국 기준으로 날짜를 선택했기 때문에)
            	//console.log('시간:',today);
            	//console.log('시간2:', selectedDate);
            	if(selectedDate > today){//arg.start : 선택한 날짜의 시작 시간을 나타내는 Date 객체
            		alert('오늘 이후의 날짜는 선택할 수 없습니다.');
            	}else{
            		$('#selectedDate').val(arg.start.toISOString().slice(0, 10));
            		$('#drugModal').show();
            	}
            }
            
        });
        calendar.render();
    });
    
    $(document).ready(function(){
    	$('.close').click(function(){//모달 닫기 버튼
    		$('#drugModal').hide();
    	});
    });
</script>
<div>
	<input type="button" value="등록">
</div>
<br>
<!-- 모달 시작 -->
<div class="modal" id="drugModal">
	<div class="modal-header">
		<div>의약품 복용 기록 등록</div>
		<div class="close">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="drugSearch">
		<ul>
			<!-- <li>
				증상 : <input type="text" class="check">
			</li> -->
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off" class="check">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				복용일자 : <input type="date" id="selectedDate" class="check">
			</li>
			<li>
				복용시간 : 
				<select id="selectedTime">
					<option value="" selected disabled>-복용시간-</option>
					<option value="1">아침</option>
					<option value="2">점심</option>
					<option value="3">저녁</option>
					<option value="4">자기 전</option>
				</select>
			</li>	
			<li>
				복용량 : <input type="text" id="memberDosage" class="check"><br>
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