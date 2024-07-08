<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
	document.addEventListener('DOMContentLoaded', function() {
		var calendarEl = document.getElementById('calendar');
		
		var calendar = new FullCalendar.Calendar(calendarEl, {
			selectable : true,//사용자가 날짜를 선택할 수 있게
			height:'auto',
			width:'auto',
			headerToolbar: {
				left:'prev',
				center:'title',
				right:'next'
			},
			initialView : 'dayGridMonth',//초기에 보여줄 뷰
			locale: 'ko',
			dayCellContent: function(e) {//1일,2일 -> '일'을 빼고 1,2만 보이게
	            var dayNumber = e.date.getDate(); // 날짜에서 일(day) 값을 가져옴
	            var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>'; // 숫자만 포함하는 HTML 생성
	            return { html: html }; // 생성된 HTML 반환
	        },
			fixedWeekCount:false, //다음 달의 날짜가 보여지지 않게
			dateClick:function(info){//날짜를 클릭했을 때 호출될 함수
				fetchDayoffTimes(info.date);
			}
		});
		calendar.render();
		
		//모델에서 doc_num 가져오기
		//const doc_num = ${doc_num};
		
		function fetchDayoffTimes(date) {
		    $.ajax({
		        url:'/schedule/dayoffTimes',
		        data:{doff_date: date},
		        success:function(param) {
		            if(param.result == 'logout'){
		            	alert('로그인 후 이용하세요');
		            }else if(param.result == 'success'){
		            	displayTimes(date, param.times);
		            }else{
		            	alert('스케줄 휴무 오류 발생');
		            }
		        },
		        error:function(){
		        	alert('네트워크 오류 발생');
		        }
		    });
		}

		function displayTimes(date, dayoffTimes) {
			$('#time-buttons').empty(); //기존 버튼들을 지우고 새로 시작
		    const allTimes = generateTimesForDay(date);  // 9:00 to 18:00, excluding lunch time
		    allTimes.forEach(time => {
		        const button = $('<button>')
		            .addClass(dayoffTimes.includes(time) ? 'time-off' : 'working-time')
		            .text(time);
		        $('#time-buttons').append(button);
		    });
		}
		function generateTimesForDay() {
		    let times = [];
		    for (let hour = 9; hour < 18; hour++) { // 9시부터 18시 전까지 (17:30 포함)
		        for (let minute = 0; minute < 60; minute += 30) { // 30분 간격
		            let time = '${hour}:${minute == 0 ? '00' : minute}';
		            // 점심 시간 (13:00 - 13:59) 제외
		            if (hour == 13 && (minute == 0 || minute == 30)) {
		                continue;
		            }
		            times.push(time);
		        }
		    }
		    return times;
		}
    });
</script>
<style>
#calendar {
	margin: 0 auto; /* 가운데 정렬을 위한 margin */
	width: 80%;
	max-width: 800px; /* 최대 너비 */
}

.time-off {
	background-color: gray;
}

.working-time {
	background-color: skyblue;
}
</style>
<div id='calendar'></div>
<div id="time-buttons"></div>