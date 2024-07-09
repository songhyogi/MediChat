<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // 로그인 후 메시지가 있을 경우 알림
        <c:if test="${!empty message}">
            alert('${message}');
        </c:if>

        // 모델에서 doc_num 가져오기
        const doc_num = ${doc_num};

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
                fetchDayoffTimes(doc_num, info.dateStr);
            }
        });
        calendar.render();

        // 선택한 날짜의 휴무 시간을 가져오는 함수
        function fetchDayoffTimes(doc_num, doff_date) {
            $.ajax({
                url: '/schedule/dayoffTimes',
                type: 'get',
                data: {
                    doc_num: doc_num,
                    doff_date: doff_date
                },
                success: function(param) {
                    if (param.result == 'logout') {
                        alert('로그인 후 이용하세요');
                        location.href = '/member/login';
                    } else if (param.result == 'success') {
                        displayTimes(doff_date, param.times);
                    } else if (param.result == 'wrongAccess') {
                        alert('잘못된 접근입니다.');
                        history.go(-1);
                    } else {
                        alert('스케줄 휴무 오류 발생');
                    }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                }
            });
        }

        // 선택한 날짜의 근무/휴무 시간을 표시하는 함수
        function displayTimes(date, dayoffTimes) {
            $('#time-buttons').empty(); // 기존 버튼들을 지우고 새로 시작
            const allTimes = generateTimesForDay(); // 9:00 to 18:00, excluding lunch time
            let output = '';
            allTimes.forEach(time => {
                output += '<button class="';
                if (dayoffTimes.includes(time)) {
                    output += 'time-off'; // 휴무 시간
                } else {
                    output += 'working-time'; // 근무 시간
                }
                output += '" data-time="' + time + '" disabled>' + time + '</button>';
            });
            output += '<input type="button" value="근무 수정" class="schedule-modify" data-date="' + date + '">';
            $('#time-buttons').html(output);
        }

        // 시간 범위를 생성하는 함수
        function generateTimesForDay() {
            let times = [];
            for (let hour = 9; hour < 18; hour++) { // 9시부터 18시 전까지 (17:30 포함)
                for (let minute = 0; minute < 60; minute += 30) { // 30분 간격
                    let time = `${hour}:${minute == 0 ? '00' : minute}`;
                    // 점심 시간 (13:00 - 13:59) 제외
                    if (hour == 13 && (minute == 0 || minute == 30)) {
                        continue;
                    }
                    times.push(time);
                }
            }
            return times;
        }

        // '근무 수정' 버튼 클릭 시 시간 버튼 활성화
        $(document).on('click', '.schedule-modify', function() {
            $('#time-buttons button').attr('disabled', false);
            let modifyButton = '<input type="button" value="근무 수정 완료" class="schedule-modify-complete" data-date="' + $(this).data('date') + '">';
            $('#time-buttons').append(modifyButton);
            $(this).hide();
        });

        // '근무 수정 완료' 버튼 클릭 시 변경된 데이터 서버로 전송
        $(document).on('click', '.schedule-modify-complete', function() {
            const selectedDate = $(this).data('date');
            const timesToAdd = [];
            const timesToRemove = [];
            $('#time-buttons button').each(function() {
                if ($(this).hasClass('time-off') && !$(this).data('original-off')) {
                    timesToAdd.push($(this).data('time'));
                } else if ($(this).hasClass('working-time') && $(this).data('original-off')) {
                    timesToRemove.push($(this).data('time'));
                }
            });
            $.ajax({
                url: '/schedule/updateDayoffTimes',
                type: 'post',
                data: {
                    doc_num: doc_num,
                    doff_date: selectedDate,
                    timesToAdd: timesToAdd,
                    timesToRemove: timesToRemove
                },
                success: function(response) {
                    if (response.result == 'success') {
                        alert('근무시간이 수정되었습니다.');
                        location.reload();
                    } else {
                        alert('근무시간 수정에 실패했습니다.');
                    }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                }
            });
        });

        // 시간 버튼 클릭 시 클래스 토글 (근무 시간 <-> 휴무 시간)
        $(document).on('click', '#time-buttons button', function() {
            if (!$(this).attr('disabled')) {
                $(this).toggleClass('time-off working-time');
                $(this).data('original-off', !$(this).hasClass('working-time'));
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

.time-off {
    background-color: gray;
}

.working-time {
    background-color: skyblue;
}
</style>
<div id='calendar'></div>
<div id="time-buttons"></div>
