<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const hos_num = '${hos_num}';
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
            dateClick: function(info){
                //날짜 클릭 시 호출될 함수
                displayhosTimes(info.dateStr, info.date.getDay());
            }
        });
        calendar.render();
        
        // 선택한 날짜의 병원 진료시간을 표시하는 함수
        function displayhosTimes(date, day){
            $('#time-buttons').empty();
            
            // 요일에 따라 적절한 필드 이름을 매핑 (풀캘린더와 병원API의 정해진 필드가 다르기 때문)
            const dayMapping = {
                0: { start: 'hos_time7s', end: 'hos_time7c' }, // 일요일
                1: { start: 'hos_time1s', end: 'hos_time1c' }, // 월요일
                2: { start: 'hos_time2s', end: 'hos_time2c' }, // 화요일
                3: { start: 'hos_time3s', end: 'hos_time3c' }, // 수요일
                4: { start: 'hos_time4s', end: 'hos_time4c' }, // 목요일
                5: { start: 'hos_time5s', end: 'hos_time5c' }, // 금요일
                6: { start: 'hos_time6s', end: 'hos_time6c' }  // 토요일
            };
            const fields = dayMapping[day];
            console.log(`Fields: ${JSON.stringify(fields)}`); // 디버깅용 콘솔 출력
            
            //AJAX 요청을 통해 근무 시간 정보를 가져옴
            $.ajax({
                url: '/reservation/hosHours',
                method: 'get',
                data: { hos_num: hos_num },
                dataType: 'json',
                success: function(param){
                    console.log(param);  // 디버깅용 콘솔 출력
                    const startTime = convertTimeFormat(param[fields.start]);
                    const endTime = convertTimeFormat(param[fields.end]);
                    
                    console.log('Start Time: ${startTime}, End Time: ${endTime}');  // 변환된 시간 출력

                    if (!startTime || !endTime) {
                        alert('해당 날짜에 대한 진료 시간이 없습니다.');
                        return;
                    }
                    let times = generateTimesForDay(startTime, endTime);
                    let output = '<div class="time-section"><h3>오전</h3><div class="time-row">';
                    times.forEach((time, index) => {
                        if (time < "12:00") {
                             if (index > 0 && index % 4 == 0) {
                                 output += '</div><div class="time-row">';
                             }
                             output += '<button class="time-button" data-time="' + time + '">' + time + '</button>';
                         }
                     });
                     output += '</div></div>';

                     output += '<div class="time-section"><h3>오후</h3><div class="time-row">';
                     times.forEach((time, index) => {
                         if (time >= "12:00") {
                             if (index > 0 && index % 4 == 0) {
                                 output += '</div><div class="time-row">';
                             }
                             output += '<button class="time-button" data-time="' + time + '">' + time + '</button>';
                         }
                     });
                     output += '</div></div>';
                     output += getButtonRowHtml();
                     $('#time-buttons').html(output);

                     handleButtonEvents(date);
                },
                error: function(){
                    alert('네트워크 오류 발생');
                }
            });
        }
        
     	// 시간을 "HHmm" 형식에서 "HH:mm" 형식으로 변환하는 함수
        function convertTimeFormat(timeStr) {
        	console.log(`Converting time: ${timeStr}`); // 디버깅용 콘솔 출력
            if (!timeStr) return null;
            if (timeStr.length != 4) {
                console.error(`Invalid time string: ${timeStr}`);
                return null;
            }
            return timeStr.slice(0, 2) + ':' + timeStr.slice(2);
        }

        // 시간 범위를 생성하는 함수
        function generateTimesForDay(startTime, endTime) {
            let times = [];
            if (!startTime || !endTime) {
                return times;  // 시작시간이나 종료시간이 없을 경우 빈 배열 반환
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
    });
</script>
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
<div id="calendar"></div>
<div id="time-buttons"></div>
