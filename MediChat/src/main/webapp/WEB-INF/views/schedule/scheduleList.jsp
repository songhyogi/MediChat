<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
    	const doc_num = ${doc_num};
    	const regularDayOffStr = '${regularDayOff}';
    	const regularDayOff = regularDayOffStr ? regularDayOffStr.split(',').map(Number) : []; //정기휴무요일이 하나일 경우에도 쉼표로 분할하여 배열로 변환
    	
    	let holidays = [];
    	
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            selectable: true, //사용자가 날짜를 선택할 수 있게 설정
            height: 'auto',
            width: 'auto',
            headerToolbar: {
                left: 'title',
                right: 'today prev,next'
            },
            initialView: 'dayGridMonth',// 초기 보여줄 뷰 설정
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
            dayCellDidMount: function(info){
            	const date = new Date(info.date);
            	const day = date.getDay();//요일 가져오기
            	if(regularDayOff.includes(day)){
            		info.el.classList.add('regular-day-off');//정기휴무요일에 해당하면 클래스 추가해서 스타일 적용
            	}
            	holidays.forEach(function(holi){
            		if(holi.holi_status==1 && holi.holi_date == dayE1.getAttribute('data-date')){
            			dayE1.classList.add('holiday-off');
            		}
            	});
            },
            datesSet: function(info) {//datesSet 이벤트는 달력이 새로 렌더링될 때마다 호출됨
                const days = document.querySelectorAll('.fc-daygrid-day');
                days.forEach(dayEl => {
                    const date = new Date(dayEl.getAttribute('data-date'));
                    const day = date.getDay();
                    if(regularDayOff.includes(day)){//datesSet 이벤트로 이전 달이나 다음 달로 이동했을 때도 정기휴무요일 적용
                        dayEl.classList.add('regular-day-off');
                    }
                    holidays.forEach(function(holi){
                		if(holi.holi_status==1 && holi.holi_date == dayE1.getAttribute('data-date')){
                			dayE1.classList.add('holiday-off');
                		}
                	});
                });
            }
        });
        calendar.render();
        
     	// 선택한 날짜의 근무/휴무 시간을 표시하는 함수
        function displayTimes(date) {
            $('#time-buttons').empty();
            // AJAX 요청을 통해 근무 시간 정보를 가져옴
            $.ajax({
                url: '/schedule/workingTimes',
                method: 'get',
                data: {doc_num: doc_num},
                dataType: 'json',
                success: function(param) {
                    console.log("AJAX response:", param);
                    if (param.result == 'success') {
                    	const doc_stime = param.workingHours.DOC_STIME;
                        const doc_etime = param.workingHours.DOC_ETIME;
                        const allTimes = generateTimesForDay(doc_stime, doc_etime);
                        let output = '<div class="time-row">';
                        allTimes.forEach((time, index) => {
                            if (index > 0 && index % 4 == 0) {
                                output += '</div><div class="time-row">';
                            }
                            output += '<button class="working-time" data-time="' + time + '">' + time + '</button>';
                        });
                        output += '</div>';
                        output += '<div class="button-row">';
                        output += '<input type="button" value="근무수정" class="modify-btn">';
                        output += '</div>';
                        output += '<div class="button-row">';
                        output += '<input type="button" value="취소" class="delete-btn">';
                        output += '</div>';
                        $('#time-buttons').html(output);
                    } else if (param.result == 'logout') {
                        alert('로그인이 필요합니다.');
                    } else if (param.result == 'wrongAccess') {
                        alert('잘못된 접근입니다.');
                    }
                },
                error: function() {
                    alert('근무 시간을 가져오는 데 실패했습니다.');
                }
            });
        }

        // 시간 범위를 생성하는 함수
        function generateTimesForDay(doc_stime, doc_etime) {
            let times = [];
            let [startHour, startMinute] = doc_stime.split(':').map(Number);
            let [endHour, endMinute] = doc_etime.split(':').map(Number);

            for (let hour = startHour; hour <= endHour; hour++) {
                for (let minute = (hour == startHour ? startMinute : 0); minute < 60; minute += 30) {
                    if (hour == endHour && minute >= endMinute) break; // 종료 시간에 도달하면 루프 종료
                    let time = hour.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0');
                    times.push(time);
                }
            }

            return times;
        }
        
        //Ajax로 개별 휴무 데이터를 가져옴
        $.ajax({
        	url: '/schedule/holidays',
        	method:'get',
        	data:{doc_num:doc_num},
        	dataType:'json',
        	success:function(param){
        		holidays = param;
        		console.log(holidays);
        		calendar.render();
        	},
        	error:function(){
        		alert('개별 휴무 데이터를 가져오는 데 실패했습니다.');
        	}
        });
    });
</script>
<style>
#calendar {
    margin: 0 auto; /* 가운데 정렬을 위한 margin */
    width: 80%;
    max-width: 800px; /* 최대 너비 */
}
.regular-day-off{
	background-color: #f2f2f2;
}
.time-off {
    background-color: gray;
}
.working-time {
    background-color: rgb(153, 204, 204);
}
#time-buttons {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-top: 20px;
}
.time-row {
    display: flex;
    width: 100%;
    justify-content: center; /* 가운데 정렬 */
    margin-bottom: 10px;
}
button {
    width: 195px;
    height: 45px;
    margin: 5px; /* 버튼 간의 간격 조정 */
    font-size: 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
button:disabled {
    cursor: not-allowed;
    opacity: 0.6;
}
.fc-day-sun a {/* 일요일 날짜 빨간색 */
  color: red;
  text-decoration: none;
}
.fc-day-sat a {/* 토요일 날짜 파란색 */
  color: blue;
  text-decoration: none;
}
.fc-prev-button, .fc-next-button, .fc-today-button {
	width:60px;
    font-size: 10px;
}
.button-row {
    display: flex;
    width: 100%;
    justify-content: center; /* 가운데 정렬 */
    margin-top: 10px;
}
.modify-btn{
	width: 800px;
	
}
.delete-btn{
	width: 800px;
}
</style>
<div id='calendar' data-doc-num="${doc_num}" data-regular-day-off="${regularDayOff}"></div>
<div id="time-buttons"></div>