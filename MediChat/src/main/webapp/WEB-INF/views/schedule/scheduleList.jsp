<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/schedule.list.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 세션에서 가져온 doc_num과 regularDayOff를 초기화
    const doc_num = ${doc_num};
    const regularDayOffStr = '${regularDayOff}';
    const regularDayOff = regularDayOffStr ? regularDayOffStr.split(',').map(Number) : [];

    let holidays = []; // 휴일 정보를 저장할 배열
    let modifiedTimes = {}; // 수정된 시간 데이터를 저장할 객체

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
        dateClick: function(info) {
            // 날짜 클릭 시 호출될 함수
            displayTimes(info.dateStr);
        },
        dayCellDidMount: function(info) {
            // 특정 날짜 셀에 클래스를 적용하는 함수 호출
            applyClassesToCell(info.el, info.date);
        },
        datesSet: function(info) {
            // datesSet 이벤트는 달력이 새로 렌더링될 때마다 호출됨
            const days = document.querySelectorAll('.fc-daygrid-day');
            days.forEach(dayEl => {
                const date = new Date(dayEl.getAttribute('data-date'));
                applyClassesToCell(dayEl, date);
            });
        }
    });
    calendar.render();
    
 	// 특정 날짜 셀에 클래스를 적용하는 함수
    function applyClassesToCell(cell, date) {
        cell.classList.remove('regular-day-off', 'holiday-off', 'working-day');
        const day = date.getDay();
        const dateString = date.toISOString().split('T')[0];

        if (regularDayOff.includes(day)) {
            cell.classList.add('regular-day-off');
        }

        if (Array.isArray(holidays)) {
            holidays.forEach(function(holi) {
                if (holi.holi_date == dateString) {
                    if (holi.holi_status == 1) {
                        cell.classList.add('holiday-off');
                    } else if (holi.holi_status == 2) {
                        cell.classList.remove('regular-day-off');
                        cell.classList.add('working-day');
                    }
                }
            });
        }
    }

    // 선택한 날짜의 근무/휴무 시간을 표시하는 함수
    function displayTimes(date) {
        $('#time-buttons').empty();
        modifiedTimes = {}; // 선택한 날짜가 바뀔 때마다 수정된 시간을 초기화
        // AJAX 요청을 통해 근무 시간 정보를 가져옴
        $.ajax({
            url: '/schedule/workingTimes',
            method: 'get',
            data: { doc_num: doc_num, res_date: date},
            dataType: 'json',
            success: function(param) {
                if (param.result == 'success') {
                    const doc_stime = param.workingHours.DOC_STIME;
                    const doc_etime = param.workingHours.DOC_ETIME;
                    const reservedTimes = param.reservedTimes;
                    const allTimes = generateTimesForDay(doc_stime, doc_etime);
                    let output = '<div class="time-section">';
                    output += '<h3>오전</h3><div class="time-row">';
                    allTimes.forEach((time, index) => {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        if (time < "12:00") {
                            if (index > 0 && index % 4 == 0) {
                                output += '</div><div class="time-row">';
                            }
                            output += '<button class="' + getButtonClass(time, date, reservedTimes) + '" data-time="' + time + '">' + time + '</button>';
                        }
                    });
                    output += '</div></div>';

                    output += '<div class="time-section">';
                    output += '<h3>오후</h3><div class="time-row">';
                    allTimes.forEach((time, index) => {
                        if (time >= "12:00") {
                            if (index > 0 && index % 4 == 0) {
                                output += '</div><div class="time-row">';
                            }
                            output += '<button class="' + getButtonClass(time, date, reservedTimes) + '" data-time="' + time + '">' + time + '</button>';
                        }
                    });
                    output += '</div></div>';
                    output += getButtonRowHtml();
                    $('#time-buttons').html(output);

                    handleButtonEvents(date, reservedTimes);
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

    // 시간 버튼의 CSS 클래스를 반환하는 함수
    function getButtonClass(time, date,reservedTimes) {
        let buttonClass = 'working-time';
        const selectedDate = new Date(date);
        const selectedDay = selectedDate.getDay();
        if (regularDayOff.includes(selectedDay)) {
            buttonClass = 'time-off';
        }
        holidays.forEach(function(holi) {
            if (holi.holi_status == 1 && holi.holi_date == date && holi.holi_time == time) {
                buttonClass = 'time-off';
            }
            if (holi.holi_status == 2 && holi.holi_date == date && holi.holi_time == time) {
                buttonClass = 'working-time-red';
            }
        });
        if (reservedTimes.includes(time)) {
            buttonClass = 'reserved-time'; // 예약된 시간은 비활성화
        }
        return buttonClass;
    }

    // 시간 버튼의 클릭 이벤트를 처리하는 함수
    function handleButtonEvents(date, reservedTimes) {
        $('.modify-btn').click(function() {
        	modifiedTimes = {}; // 근무수정버튼을 클릭할 때마다 수정된 시간을 초기화
            $('.working-time, .time-off, .working-time-red').prop('disabled', false).addClass('active');
            $(this).hide();
            $('.complete-modify-btn, .cancel-btn').show();
            
         	// 이미 예약된 시간은 비활성화 상태 유지
            reservedTimes.forEach(function(time) {
                $('button[data-time="' + time + '"]').prop('disabled', true).removeClass('active');
            });
        });

        $('.cancel-btn').click(function() {
            displayTimes(date);
        });

        $(document).off('click', '.active').on('click', '.active', function() {
            const time = $(this).data('time');
            if ($(this).hasClass('working-time')) {
                $(this).removeClass('working-time').addClass('time-off');
                modifiedTimes[time] = 1;
            } else if ($(this).hasClass('time-off')) {
                $(this).removeClass('time-off').addClass('working-time');
                modifiedTimes[time] = 2;
            } else if ($(this).hasClass('working-time-red')) {
                $(this).removeClass('working-time-red').addClass('time-off');
                modifiedTimes[time] = 1;
            }
        });

        $('.complete-modify-btn').click(function() {
            let dataToSend = [];
            Object.keys(modifiedTimes).forEach(function(time) {
                dataToSend.push({
                    doc_num: doc_num,
                    holi_date: date,
                    holi_time: time,
                    holi_status: modifiedTimes[time]
                });
            });
            $.ajax({
                url: '/schedule/updateTimes',
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(dataToSend),
                success: function(response) {
                    if (response.result == 'success') {
                        alert('근무 시간이 성공적으로 업데이트되었습니다.');
                        // 수정 완료 후 즉시 반영
                        holidays = holidays.filter(holi => holi.holi_date !== date);
                        dataToSend.forEach(holi => holidays.push(holi));
                        displayTimes(date);
                    } else {
                        alert('근무 시간 업데이트에 실패했습니다.');
                        console.error(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert('근무 시간 업데이트 중 오류가 발생했습니다.');
                    console.error(error);
                }
            });
        });
    }

    // 시간 범위를 생성하는 함수
    function generateTimesForDay(doc_stime, doc_etime) {
        let times = [];
        let [startHour, startMinute] = doc_stime.split(':').map(Number);
        let [endHour, endMinute] = doc_etime.split(':').map(Number);

        for (let hour = startHour; hour <= endHour; hour++) {
            for (let minute = (hour == startHour ? startMinute : 0); minute < 60; minute += 30) {
                if (hour == endHour && minute >= endMinute) break;
                let time = hour.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0');
                times.push(time);
            }
        }

        return times;
    }

    // 버튼 행 HTML을 반환하는 함수
    function getButtonRowHtml() {
        return `
            <div class="button-row">
                <input type="button" value="근무수정하기" class="modify-btn">
                <input type="button" value="근무수정완료" class="complete-modify-btn" style="display:none;">
                <input type="button" value="취소" class="cancel-btn" style="display:none;">
            </div>
        `;
    }

    // Ajax로 개별 휴무 데이터를 가져옴
    $.ajax({
        url: '/schedule/holidays',
        method: 'get',
        data: { doc_num: doc_num },
        dataType: 'json',
        success: function(param) {
            holidays = param.holidays || [];
            calendar.render();
        },
        error: function() {
            alert('개별 휴무 데이터를 가져오는 데 실패했습니다.');
        }
    });
});
</script>
<style>
#calendar {
    margin: 0 auto;
    width: 80%;
    max-width: 800px;
}
.regular-day-off {/*정기휴무요일*/

}
.time-off {
    background-color: #f2f2f2;
}
.working-time {
    background-color: #b7e4c7;
}
.working-time-red {
    background-color: #b7e4c7;
}
.reserved-time{
	background-color: #d8f3dc;
	color:gray;
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
    width: 148px;
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
.fc-day-sun a {
    color: red;
    text-decoration: none;
}
.fc-day-sat a {
    color: blue;
    text-decoration: none;
}
.fc-prev-button, .fc-next-button, .fc-today-button {
    width: 60px;
    font-size: 10px;
}
.button-row {
    display: flex;
    justify-content: center;
    margin-top: 10px;
}
.modify-btn, .cancel-btn, .complete-modify-btn {
    width: 300px;
}
.modify-btn{
	background-color: #95d5b2;
}
.selected-time {
    background-color: orange;
}
.active {
    pointer-events: auto;
}
</style>
<div id="calendar"></div>
<div id="time-buttons"></div>