<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/drug.member.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {//'DOMContentLoaded'이벤트는 문서 내 DOM이 완전히 로드되고 파싱되었을 때 발생
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        window.calendar = new FullCalendar.Calendar(calendarEl, {
        	timeZone: 'Asia/Seoul',	//날짜를 한국 기준으로 설정
            selectable: true,	//날짜 선택가능
            height: 'auto',	//높이 자동 조절
            width: 'auto',	//너비 자동 조절
            //dayMaxEvents: true, //이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
            headerToolbar: {
            	left: 'title',
                right: 'prev,next today,dayGridMonth,multiMonthYear'
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
            //이벤트 적용
            events:[
            	<c:forEach var="memberDrug" items="${memberDrug}" varStatus="status">
            		<c:if test="${status.index>0}">,</c:if>
            		{
            			id: '${memberDrug.med_num}',
            			title: '${memberDrug.med_title}',
            			start: '${memberDrug.med_sdate}',
            			end : '${memberDrug.med_edate}',
            			allDay: true,
            			med_name: '${memberDrug.med_name}',
            			med_time: '${memberDrug.med_time}',
            			med_dosage: '${memberDrug.med_dosage}',
            			med_note: '${memberDrug.med_note}'
            		}
            	</c:forEach>
            	
            ],
            /*----------캘린더에서 드래그로 이벤트 생성----------*/
            select:function(arg){
            	var today = new Date(); //현재 날짜 및 시간
                var selectedSDate = new Date(arg.start); //선택한 시작 날짜
                var selectedEDate = new Date(arg.end); //선택한 끝 날짜
                selectedSDate.setHours(0, 0, 0, 0);	//선택한 날짜의 시간 초기화(한국 기준으로 날짜를 선택했기 때문에)
                selectedEDate.setHours(0, 0, 0, 0);
                
            	console.log('오늘날짜:',today);
            	console.log('시작시간:', selectedSDate);
            	console.log('끝시간:', selectedEDate);
            	
            	if(selectedSDate > today || selectedEDate > today){//arg.start : 선택한 날짜의 시작 시간을 나타내는 Date 객체
            		alert('오늘 이후 날짜는 선택할 수 없습니다.');
            	}else{
            		//선택한 날짜가 보여지도록 처리
            		$('#selectedSDate').val(arg.start.toISOString().slice(0, 10));
            		$('#selectedEDate').val(arg.end.toISOString().slice(0, 10));//끝날짜
            		$('#drugModal').show();
            	}
            },
            /*----------캘린더에서 하루 클릭으로 이벤트 생성----------*/
           dateClick:function(info){
            	var today = new Date(); //현재 날짜 및 시간
            	var clickDate = new Date(info.date); //선택한 날짜
            	clickDate.setHours(0, 0, 0, 0);	//선택한 날짜의 시간 초기화
            	if(clickDate > today){
            		alert('선택 불가');
            	}else{
            		//선택한 날짜 보여지도록 처리
            		$('#selectedSDate').val(info.dateStr.slice(0,10));
            		$('#selectedEDate').val(info.dateStr.slice(0,10));
            		$('#drugModal').show();
            	}
            },
          	/*----------생성된 이벤트 클릭(수정/삭제)----------*/
          	//이벤트 클릭
            eventClick:function(info){//이벤트 클릭 시 기존 데이터 출력
            	var event = info.event;//이벤트 객체 가져오기
                $('#updateDrug input[name="med_num"]').val(event.id);
                $('#updateDrug input[name="med_title"]').val(event.title);
                //의약품 목록
                var med_names = event.extendedProps.med_name;
                modifyDrugList(med_names);
                //
                console.log('이벤트 클릭 의약품 목록:' + med_names);
                $('#updateDrug input[name="med_sdate"]').val(event.start.toISOString().slice(0, 10));
                $('#updateDrug input[name="med_edate"]').val(event.end.toISOString().slice(0, 10));
                //복용시간 체크박스
                $('#updateDrug input[name="med_time"]').each(function() {
                   $(this).prop('checked', event.extendedProps.med_time.includes($(this).val()));
                });
                $('#updateDrug input[name="med_dosage"]').val(event.extendedProps.med_dosage);
                $('#updateDrug textarea[name="med_note"]').val(event.extendedProps.med_note);
                $('#updateDrug').show();
            }
        });
        calendar.render();
    });//end of fullcalendar
    
    /*====================모달창====================*/
    $(document).ready(function(){
    	$('.close').click(function(){//모달 닫기 버튼
    		 $(this).closest('.modal').hide();
    	});
    });
    
    /*==========기존 의약품 데이터 수정폼에 출력==========*/
    let med_list = [];

    //기존 의약품 데이터를 수정 폼에 출력하는 함수
    function modifyDrugList(med_names) {
        //의약품 배열 초기화
        med_list = med_names.split(',');
        console.log(med_list);
        $('#moDrugSelect').empty(); // 이전에 추가된 의약품들을 모두 제거
        //의약품 데이터를 기반으로 <span> 요소 생성
        med_list.forEach(function(med_name) {
            let choice_med = '<span class="moDrugSelect-span" data-name="' + med_name + '">';
            choice_med += med_name + '<sup>&times;</sup></span>';
            $('#moDrugSelect').append(choice_med);
            console.log("의약품 기반 <span> 생성:" + med_name);
        });
    }

    //검색된 의약품 선택
    $(document).on('click', '#moSearchDrugList li', function() {
        let med_name = $(this).text(); //선택한 의약품
        //
        console.log("med_name:" + med_name);
        med_list.push(med_name);
        console.log(med_list);
        //선택한 의약품 화면에 표시
        let choice_med = '<span class="moDrugSelect-span" data-name="' + med_name + '">';
        choice_med += med_name + '<sup>&times;</sup></span>';
        $('#moDrugSelect').append(choice_med);
        $('#moDrug_search').val('');
        $('#moSearchDrugList').empty();
    });
    
    //검색된 의약품 삭제
    $(document).on('click','.moDrugSelect-span',function(){
		let med_name = $(this).attr('data-name');
		//의약품이 저장된 배열에서 의약품명 제거
		med_list.splice(med_list.indexOf(med_name),1);
		$(this).remove();//span 태그 삭제
		console.log("검색 의약품 삭제:" + med_name);
	});
</script>
<br>
<!-- 등록 모달 시작 -->
<div class="modal" id="drugModal">
	<div class="modal-header">
		<div>의약품 복용 기록 등록</div>
		<div class="close">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="drugSearch">
		<ul>
			<li>
				증상 : <input type="text" id="title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				복용일자<br>
				<input type="date" id="selectedSDate" name="med_sdate"> ~ 
				<input type="date" id="selectedEDate" name="med_edate">
			</li>
			<li>
				복용시간 : 
				<input type="checkbox" name="med_time" value="아침" class="drug">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
			</li>	
			<li>
				복용량 : <input type="text" id="memberDosage" name="med_dosage"><br>
				메모 : <textarea name="med_note"></textarea>
			</li>	
		</ul>
		<div>
			<input type="submit" value="전송">
		</div>
	</form>
	</div>
</div>
<!-- 등록 모달 끝 -->
<!-- 수정 모달 시작 -->
<div class="modal" id="updateDrug">
	<div class="modal-header">
		<div>의약품 복용 기록 수정</div>
		<div class="close">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="modifyDrugSearch">
		<input type="hidden" name="med_num">
		<ul>
			<li>
				증상 : <input type="text" id="mo_title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="moDrug_search" autocomplete="off">
				<ul id="moSearchDrugList"></ul>
				<div id="moDrugSelect"></div>
			</li>
			<li>
				복용일자 : <input type="date" id="moSelectedSDate" name="med_sdate">
				<input type="date" id="moSelectedEDate" name="med_edate">
			</li>
			<li>
				복용시간 : 
				<input type="checkbox" name="med_time" value="아침">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
			</li>	
			<li>
				복용량 : <input type="text" id="moMemberDosage" name="med_dosage"><br>
				메모 : <textarea name="med_note"></textarea>
			</li>	
		</ul>
		<div>
			<input type="submit" value="수정">
			<input type="button" value="삭제" id="delete-btn">
		</div>
	</form>
	</div>
</div>
<!-- 수정 모달 끝 -->
<br>
<!-- 캘린더 -->
<div id='calendar'></div>
<br>
<!-- 스타일 적용 -->
<style>
/*FullCalendar*/
.fc-day-sun a {
    color: red;
    text-decoration: none;
}
.fc-day-sat a {
    color: blue;
    text-decoration: none;
}
.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color: black;
    text-decoration: none;
}
/* 모달 스타일 */
.modal {
    display: none;
    position: fixed;
    z-index: 1000; /* 다른 요소들보다 위에 위치하도록 설정 */
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%); /* 화면 가운데 정렬 */
    background-color: black; /* 모달 배경색은 흰색 */
    padding: 20px;
    border-radius: 10px; /* 모달 테두리를 둥글게 */
    width: 500px; /* 모달의 고정 너비 */
    height:500px;
    max-width: 80%; /* 반응형을 위한 최대 너비 설정 */
    box-sizing: border-box; /* 패딩과 테두리를 포함한 너비 계산 */
}
.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: bold;
    font-size: 18px;
    color: white;
    background-color: rgb(179, 217, 217); /* 헤더 배경색 */
    padding: 10px 20px;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}
.modal-body {
    background-color: white; /* 모달 본문 배경색은 흰색 */
    padding: 20px;
}
.close {
    color: white;
    cursor: pointer;
    font-size: 24px;
}
.close:hover {
    color: #ffffff99; /* 닫기 버튼에 마우스를 올렸을 때 약간 투명하게 만듭니다 */
}

/* 모달이 열릴 때 배경 표시 */
.modal.show {
    display: block;
}
/* 작은 화면에서 모달 중앙 정렬 */
@media (max-width: 768px) {
    .modal {
        width: 90%; /* 화면 너비의 90%로 설정 */
    }
}
</style>