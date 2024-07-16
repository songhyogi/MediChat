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
        var calendar = new FullCalendar.Calendar(calendarEl, {
        	timeZone: 'Asia/Seoul',	//날짜를 한국 기준으로 설정
            selectable: true,	//날짜 선택가능
            height: 'auto',	//높이 자동 조절
            width: 'auto',	//너비 자동 조절
            headerToolbar: {
            	left: 'title',
                right: 'today prev,next dayGridMonth multiMonthYear'
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
            			title: '${memberDrug.med_title}',
            			start: '${memberDrug.med_date}',
            			allDay: true
            		}
            	</c:forEach>
            ],
            //날짜 클릭 시
            select:function(arg){ //오늘 이후 날짜 선택 불가
            	var today = new Date(); //현재 날짜 및 시간
                var selectedDate = new Date(arg.start);	//선택한 날짜
                selectedDate.setHours(0, 0, 0, 0);	//선택한 날짜의 시간 초기화(한국 기준으로 날짜를 선택했기 때문에)
            	//console.log('시간:',today);
            	//console.log('시간2:', selectedDate);
            	if(selectedDate > today){//arg.start : 선택한 날짜의 시작 시간을 나타내는 Date 객체
            		alert('오늘 이후의 날짜는 선택할 수 없습니다.');
            	}else{
            		$('#selectedDate').val(arg.start.toISOString().slice(0, 10));
            		$('#drugModal').show();
            	}
            },
            eventClick:function(event){//이벤트 클릭
            	alert('이벤트 클릭');
            }
        });
        calendar.render();
    });
    
    $(document).ready(function(){
    	$('.close').click(function(){//모달 닫기 버튼
    		$('#drugModal').hide();
    	});
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
				증상 : <input type="text" class="check" id="title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off" class="check">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				복용일자 : <input type="date" id="selectedDate" class="check" name="med_date">
			</li>
			<li>
				복용시간 : 
				<input type="checkbox" name="med_time" value="아침" class="drug">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
				 
				<!-- <select id="selectedTime" name="med_time">
					<option value="" selected disabled>-복용시간-</option>
					<option value="1">아침</option>
					<option value="2">점심</option>
					<option value="3">저녁</option>
					<option value="4">자기 전</option>
				</select> -->
			</li>	
			<li>
				복용량 : <input type="text" id="memberDosage" class="check" name="med_dosage"><br>
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
	<form action="memberDrugSearch" method="post" id="drugSearch">
	<input type="hidden">
		<ul>
			<li>
				증상 : <input type="text" class="check" id="title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off" class="check">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				복용일자 : <input type="date" id="selectedDate" class="check" name="med_date">
			</li>
			<li>
				복용시간 : 
				<input type="checkbox" name="med_time" value="아침" class="drug">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
			</li>	
			<li>
				복용량 : <input type="text" id="memberDosage" class="check" name="med_dosage"><br>
				메모 : <textarea name="med_note"></textarea>
			</li>	
		</ul>
		<div>
			<input type="submit" value="전송">
		</div>
	</form>
	</div>
</div>
<!-- 수정 모달 끝 -->
<br>
<!-- 캘린더 -->
<div id='calendar'></div>
<br>