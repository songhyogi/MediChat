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
            if (holi.holi_date === dateString) {
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
        data: { doc_num: doc_num },
        dataType: 'json',
        success: function(param) {
            if (param.result == 'success') {
                const doc_stime = param.workingHours.DOC_STIME;
                const doc_etime = param.workingHours.DOC_ETIME;
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
                        output += '<button class="' + getButtonClass(time, date) + '" data-time="' + time + '">' + time + '</button>';
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
                        output += '<button class="' + getButtonClass(time, date) + '" data-time="' + time + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';
                output += getButtonRowHtml();
                $('#time-buttons').html(output);

                handleButtonEvents(date);
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
function getButtonClass(time, date) {
    let buttonClass = 'working-time';
    const selectedDate = new Date(date);
    const selectedDay = selectedDate.getDay();
    if (regularDayOff.includes(selectedDay)) {
        buttonClass = 'time-off';
    }
    holidays.forEach(function(holi) {
        if (holi.holi_status == 1 && holi.holi_date === date && holi.holi_time === time) {
            buttonClass = 'time-off';
        }
        if (holi.holi_status == 2 && holi.holi_date === date && holi.holi_time === time) {
            buttonClass = 'working-time-red';
        }
    });
    return buttonClass;
}

// 시간 버튼의 클릭 이벤트를 처리하는 함수
function handleButtonEvents(date) {
    $('.modify-btn').click(function() {
    	modifiedTimes = {}; // 근무수정버튼을 클릭할 때마다 수정된 시간을 초기화
        $('.working-time, .time-off, .working-time-red').prop('disabled', false).addClass('active');
        $(this).hide();
        $('.complete-modify-btn, .cancel-btn').show();
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
            <input type="button" value="근무수정" class="modify-btn">
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