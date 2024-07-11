<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            dateClick: function(info) {
                // 날짜 클릭 시 호출될 함수
                displayTimes(info.dateStr);
            }
        });
        calendar.render();
        
     // 선택한 날짜의 근무/휴무 시간을 표시하는 함수
        function displayTimes(date) {
            $('#time-buttons').empty(); // 기존 버튼들을 지우고 새로 시작
            const allTimes = generateTimesForDay(); // 9:00 to 18:00, excluding lunch time
            let output = '<div class="time-row">';
            allTimes.forEach((time, index) => {
                if (index > 0 && index % 4 == 0) {
                    output += '</div><div class="time-row">';
                }
                output += '<button class="working-time" data-time="' + time + '">' + time + '</button>';
            });
            output += '</div>';
            $('#time-buttons').html(output);
        }

        // 시간 범위를 생성하는 함수
        function generateTimesForDay() {
            let times = [];
            for (let hour = 9; hour < 18; hour++) { // 9시부터 18시 전까지 (17:30 포함)
                for (let minute = 0; minute < 60; minute += 30) { // 30분 간격
                    let time = hour + ':' + (minute == 0 ? '00' : minute);
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
</style>
<div id='calendar'></div>
<div id="time-buttons"></div>