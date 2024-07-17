<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

.time-button.selected {
	background-color: blue;
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
	width: 90px;
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
</style>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
function initializeCalendar(hos_num) {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        selectable: true,// 사용자가 날짜를 선택할 수 있게 설정
        height: 'auto',
        headerToolbar: {
            left: 'title',
            right: 'today prev,next'
        },
        initialView: 'dayGridMonth', //초기 보여줄 뷰 설정
        locale: 'ko',
        dayCellContent: function(e) {
        	// 날짜 셀 내용 설정 (1일, 2일 -> '일' 빼고 1, 2만 보이게)
            var dayNumber = e.date.getDate();
            var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
            return { html: html };
        },
        fixedWeekCount: false,// 다음 달의 날짜가 보여지지 않게 설정
        dateClick: function(info){
        	//날짜 클릭 시 호출될 함수
            displayhosTimes(info.dateStr, info.date.getDay(), hos_num);
        }
    });
    calendar.render();
}

//선택한 날짜의 병원 진료시간을 표시하는 함수
function displayhosTimes(date, day, hos_num) {
    $('#time-buttons').empty();
 	//요일에 따라 적절한 필드 이름을 매핑 (풀캘린더와 병원API의 정해진 필드가 다르기 때문)
    const dayMapping = {
        0: { start: 'hos_time7S', end: 'hos_time7C' }, // 일요일
        1: { start: 'hos_time1S', end: 'hos_time1C' }, // 월요일
        2: { start: 'hos_time2S', end: 'hos_time2C' }, // 화요일
        3: { start: 'hos_time3S', end: 'hos_time3C' }, // 수요일
        4: { start: 'hos_time4S', end: 'hos_time4C' }, // 목요일
        5: { start: 'hos_time5S', end: 'hos_time5C' }, // 금요일
        6: { start: 'hos_time6S', end: 'hos_time6C' }  // 토요일
    };
    const fields = dayMapping[day];
    console.log(`Fields: ${JSON.stringify(fields)}`);// 디버깅용 콘솔 출력
 	//AJAX 요청을 통해 진료 시간 정보를 가져옴
    $.ajax({
        url: '/reservation/hosHours',
        method: 'get',
        data: { hos_num: hos_num },
        dataType: 'json',
        success: function(param){
            console.log(param);// 디버깅용 콘솔 출력
            if (param.result === "success") {
                const hospitalVO = param.hospitalVO;
                const startTime = convertTimeFormat(hospitalVO[fields.start]);
                const endTime = convertTimeFormat(hospitalVO[fields.end]);

                if (!startTime || !endTime) {
                    alert('해당 날짜에 대한 진료 시간이 없습니다.');
                    return;
                }
                let times = generateTimesForDay(startTime, endTime);
                let output = '<div class="time-section"><h5>오전</h5><div class="time-row">';
                times.forEach((time, index) => {
                    if (time < "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button class="time-button" data-time="' + time + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';

                output += '<div class="time-section"><h5>오후</h5><div class="time-row">';
                times.forEach((time, index) => {
                    if (time >= "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button class="time-button" data-time="' + time + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';
                $('#time-buttons').html(output);
             	// 시간 버튼 클릭 이벤트 핸들러 추가 - 근무가능한 의사 정보 가져오기
                $('.time-button').click(function() {
                    var selectedTime = $(this).data('time');
                    TimeButtonEvents(hos_num, date, selectedTime, day);
                });
            } else if (param.result === "logout") {
                alert("로그인이 필요합니다.");
            }
        },
        error: function(){
            alert('네트워크 오류 발생');
        }
    });
}
//시간을 "HHmm" 형식에서 "HH:mm" 형식으로 변환하는 함수
function convertTimeFormat(timeStr) {
    console.log(`Converting time: ${timeStr}`);
    if (!timeStr) return null;
    if (timeStr.length != 4) {
        console.error(`Invalid time string: ${timeStr}`);
        return null;
    }
    return timeStr.slice(0, 2) + ':' + timeStr.slice(2);
}
//시간 범위를 생성하는 함수
function generateTimesForDay(startTime, endTime) {
    let times = [];
    if (!startTime || !endTime) {
        return times;// 시작시간이나 종료시간이 없을 경우 빈 배열 반환
    }

    let [startHour, startMinute] = startTime.split(':').map(Number);
    let [endHour, endMinute] = endTime.split(':').map(Number);

    for (let hour = startHour; hour <= endHour; hour++) {
        for (let minute = (hour == startHour ? startMinute : 0); minute < 60; minute += 30) {
            if (hour == endHour && minute >= endMinute) break;
            let time = hour.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0');
            times.push(time);
        }
    }

    return times;
}
//시간 버튼의 클릭 이벤트를 처리하는 함수
function TimeButtonEvents(hos_num, date, time, dayOfWeek){
	$('#doctor-info').empty();
	$.ajax({
		url:'/reservation/availableDoctor',
		method:'get',
		data:{
			hos_num:hos_num,
			date:date,
			time: time,
            dayOfWeek: dayOfWeek
		},
		dataType:'json',
		success:function(param){
			if(param.result=='logout'){
				alert('로그인이 필요합니다.');
			}else if(param.result=='success'){
				let output = '';
				output += '<div class="doctor-section">';
				param.doctors.forEach(function(doctor) {
                    output += `
                        <div class="doctor-card" data-doc-num="${doctor.doc_num}">
                            <img src="${doctor.image}" alt="${doctor.mem_name}" class="doctor-image">
                            <div class="doctor-name">${doctor.mem_name}</div>
                        </div>
                    `;
                });
                output += '</div>';
                $('#doctor-info').html(output);
				output += '</div>';
			}else{
				alert('시간 버튼 이벤트 오류 발생');
			}
		},
		error:function(){
			alert('네트워크 오류 발생');
		}
	});
}
</script>

<div id="calendar"></div>
<div id="time-buttons"></div>
<div id="doctor-info"></div>