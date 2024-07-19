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
        dateClick: function(info) {
        	resetSelections(); // 이전 선택 초기화
            // 날짜 클릭 시 호출될 함수
        	displayHosTimes(info.dateStr, info.date.getDay(), hos_num);
            // 선택된 날짜의 데이터 속성 설정
            $('td.fc-daygrid-day').removeClass('fc-daygrid-day-selected');
            $(info.dayEl).addClass('fc-daygrid-day-selected').attr('data-date', info.dateStr);
        }
    });
    calendar.render();
}

//선택한 날짜의 병원 진료시간을 표시하는 함수
function displayHosTimes(date, dayOfWeek, hos_num) {
    // #time-buttons 요소를 비움
    $('#time-buttons').empty();

    // 요일에 따라 적절한 필드 이름을 매핑 (풀캘린더와 병원API의 정해진 필드가 다르기 때문)
    const dayMapping = {
        0: { start: 'hos_time7S', end: 'hos_time7C' }, // 일요일
        1: { start: 'hos_time1S', end: 'hos_time1C' }, // 월요일
        2: { start: 'hos_time2S', end: 'hos_time2C' }, // 화요일
        3: { start: 'hos_time3S', end: 'hos_time3C' }, // 수요일
        4: { start: 'hos_time4S', end: 'hos_time4C' }, // 목요일
        5: { start: 'hos_time5S', end: 'hos_time5C' }, // 금요일
        6: { start: 'hos_time6S', end: 'hos_time6C' }  // 토요일
    };
    // 선택한 요일에 해당하는 시작 및 종료 시간을 fields로 저장
    const fields = dayMapping[dayOfWeek];
    console.log(`Fields: ${JSON.stringify(fields)}`); // 디버깅용 콘솔 출력

    // AJAX 요청을 통해 진료 시간 정보를 가져옴
    $.ajax({
        url: '/reservation/hosHours',
        method: 'get',
        data: { hos_num: hos_num },
        dataType: 'json',
        success: function(param){
            console.log(param); // 디버깅용 콘솔 출력
            if (param.result == "success") {
                const hospitalVO = param.hospitalVO;
                // 시작 시간과 종료 시간을 HH:mm 형식으로 변환
                const startTime = convertTimeFormat(hospitalVO[fields.start]);
                const endTime = convertTimeFormat(hospitalVO[fields.end]);

                // 시작 시간 또는 종료 시간이 없으면 경고를 표시하고 반환
                if (!startTime || !endTime) {
                    alert('해당 날짜에 대한 진료 시간이 없습니다.');
                    return;
                }

                // 시작 시간과 종료 시간 사이의 30분 단위 시간을 생성
                let times = generateTimesForDay(startTime, endTime);
                let output = '<div class="time-section"><h5>오전</h5><div class="time-row">';
                // 오전 시간 버튼 생성
                times.forEach((time, index) => {
                    if (time < "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button class="time-button" data-time="' + time + '" data-day-of-week="' + dayOfWeek + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';

                output += '<div class="time-section"><h5>오후</h5><div class="time-row">';
                // 오후 시간 버튼 생성
                times.forEach((time, index) => {
                    if (time >= "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button class="time-button" data-time="' + time + '" data-day-of-week="' + dayOfWeek + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';
                $('#time-buttons').html(output);

                // 시간 버튼 클릭 이벤트 핸들러 추가 - 근무 가능한 의사 정보 가져오기
                $('.time-button').click(function() {
                	initializeNextButton();
                	// 모든 버튼에서 selected 클래스를 제거
                    $('.time-button').removeClass('selected');

                    // 클릭한 버튼에 selected 클래스 추가
                    $(this).addClass('selected');
                	
                    var selectedTime = $(this).data('time');
                    var selectedDayOfWeek = $(this).data('day-of-week');
                    console.log('Selected Time: ' + selectedTime);
                    console.log('Selected DayOfWeek: ' + selectedDayOfWeek);
                    getAvailableDoctors(hos_num, date, selectedTime, selectedDayOfWeek);
                });
            } else if (param.result == "logout") {
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
function getAvailableDoctors(hos_num, date, time, dayOfWeek) {
    console.log('시간 버튼 클릭 이벤트');
    console.log('hos_num:' + hos_num);
    console.log('date:' + date);
    console.log('time:' + time);
    console.log('dayOfWeek:' + dayOfWeek);

    $('#doctor-info').empty();
    $.ajax({
        url: '/reservation/availableDoctor',
        method: 'get',
        data: {
            hos_num: hos_num,
            date: date,
            time: time,
            dayOfWeek: dayOfWeek
        },
        dataType: 'json',
        success: function(param) {
            console.log('AJAX 응답 받음: ', param); // 전체 응답 로그 출력
            if (param.result == 'logout') {
                alert('로그인이 필요합니다.');
            } else if (param.result == 'success') {
                let output = '<div class="doctor-section">';
                param.doctors.forEach(function (doctor) {
                    console.log('doctor object: ', doctor); // doctor 객체 로그 출력
                    // 프로필 이미지 경로 설정
                    let imageSrc = '${pageContext.request.contextPath}/doctor/docPhotoView?doc_num=' + doctor.doc_num;

                    output += '<div class="doctor-card" data-doc-num="' + doctor.doc_num + '">';
                    output += '<img src="' + imageSrc + '" alt="' + doctor.mem_name + '" class="doctor-image">';
                    output += '<div class="doctor-name">' + doctor.mem_name + '</div>';
                    output += '<div class="res-type-container">';
                    output += '<label><input type="radio" name="res_type" value="0" class="res-type-radio"> 비대면 진료</label>';
                    output += ' <label><input type="radio" name="res_type" value="1" class="res-type-radio"> 방문 진료</label>';
                    output += '</div>';
                    output += '</div>';
                });
                output += '</div>';
                console.log('Output HTML: ', output); // 생성된 HTML 로그 출력
                $('#doctor-info').html(output);

                // 의사 프로필 클릭 시 비대면진료/방문진료 선택하는 라디오버튼 표시
                $('.doctor-card').click(function() {
                    $('.doctor-card').removeClass('selected');
                    $(this).addClass('selected');
                });

                // 라디오 버튼 클릭 이벤트 핸들러
                $('.res-type-radio').change(function() {
                    var resType = $(this).val();
                    $('.doctor-card.selected').attr('data-resType', resType);
                    $('#next-button-container input[type="button"]').prop('disabled', false).addClass('active');
                });
            } else {
                alert('시간 버튼 이벤트 오류 발생');
            }
        },
        error: function() {
            alert('네트워크 오류 발생');
        }
    });
}

// 다음 버튼 초기화 함수
function initializeNextButton() {
    const nextButton = '<input type="button" class="btn btn-primary" value="다음" style="margin-top: 20px;" disabled onclick="loadConfirmPage()">';
    const cancelButton = '<input type="button" class="btn btn-secondary" value="취소" style="margin-top: 20px;" onclick="resetAll()">';
    $('#next-button-container').html(nextButton + cancelButton);
}

//취소버튼 클릭 시 모든 데이터를 초기화하는 함수
function resetAll() {
    $('#calendar').html('');
    $('#time-buttons').empty();
    $('#doctor-info').empty();
    initializeNextButton();
    initializeCalendar('${hos_num}');
    $('#reservation_page').hide();
}

//캘린더 날짜 클릭 시 이미 선택된 항목들을 초기화하는 함수
function resetSelections() {
    $('#time-buttons').empty(); // 시간 버튼 초기화
    $('#doctor-info').empty(); // 의사 정보 초기화
    initializeNextButton(); // 다음 버튼 초기화
}

//다음 버튼 클릭 시 예약확인페이지로
function loadConfirmPage() {
    const selectedDate = $('td.fc-daygrid-day.fc-daygrid-day-selected').attr('data-date');
    const selectedTime = $('.time-button.selected').data('time');
    const selectedDoctor = $('.doctor-card.selected .doctor-name').text();
    const hospitalName = $('#reservation_btn').data('hos-name');
	const resTypeValue = $('.doctor-card.selected').attr('data-resType');
    const resType = resTypeValue == '0' ? '비대면 진료' : '방문 진료';
    
    console.log('진료타입'+resTypeValue);
	
    $('#confirm-hospital').text(hospitalName);
    $('#confirm-doctor').text(selectedDoctor);
    $('#confirm-date').text(selectedDate);
    $('#confirm-time').text(selectedTime);
    $('#confirm-resType').text(resType);

    // 사용자 이름과 전화번호는 reservation.jsp에서 설정됨

    $('.reservation-container').hide();
    $('.confirm-container').show();
}

//이전 버튼 클릭 시
function previousStep() {
    $('.confirm-container').hide();
    $('.reservation-container').show();
}

//동의하고 예약하기 클릭 시
function submitReservation() {
    const symptoms = $('#confirm-symptoms').val();
    const data = {
        hospital: $('#confirm-hospital').text(),
        doctor: $('#confirm-doctor').text(),
        date: $('#confirm-date').text(),
        name: $('#confirm-name').text(),
        phone: $('#confirm-phone').text(),
        symptoms: symptoms
    };

    $.ajax({
        url: '/reservation/reservationcompleted',
        method: 'post',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
            if (response.result == 'success') {
                alert('예약이 완료되었습니다.');
                //location.href = '/reservation/confirmation'; 예약내역페이지 만들면 수정 예정
            } else {
                alert('예약에 실패했습니다. 다시 시도해 주세요.');
            }
        },
        error: function() {
            alert('네트워크 오류 발생');
        }
    });
}