<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
	<span class="text-lightgray fw-7 fs-13">홈 > 병원 찾기 > 검색 결과 > 병원 상세</span>
	<div id="detail_hosName">
		<h4 class="fw-7">${hospital.hos_name}</h4>
		<div class="d-flex">
			<div class="detail-hospital-info-item fs-14 fw-7">${hospital.hos_divName}</div>
			<c:if test="${hospital.hos_weekendAt=='Y'}">
				<div class="detail-hospital-info-item fs-14 fw-7">주말 운영</div>
			</c:if>
			<c:if test="${hospital.hos_eryn==1}">
				<div class="detail-hospital-info-item fs-14 fw-7">응급실 운영</div>
			</c:if>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	
	<div id="detail_hosTime">
		<div class="d-flex align-items-center mb-3">
			<div class="fs-18 fw-7">진료 시간</div>
			<c:if test="${day==1}">
				<c:if test="${hospital.hos_time1S<=time and time<hospital.hos_time1C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time1S>time or hospital.hos_time1C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==2}">
				<c:if test="${hospital.hos_time2S<=time and time<hospital.hos_time2C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time2S>time or hospital.hos_time2C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==3}">
				<c:if test="${hospital.hos_time3S<=time and time<hospital.hos_time3C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time3S>time or hospital.hos_time3C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==4}">
				<c:if test="${hospital.hos_time4S<=time and time<hospital.hos_time4C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time4S>time or hospital.hos_time4C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==5}">
				<c:if test="${hospital.hos_time5S<=time and time<hospital.hos_time5C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time5S>time or hospital.hos_time5C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==6}">
				<c:if test="${hospital.hos_time6S<=time and time<hospital.hos_time6C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time6S>time or hospital.hos_time6C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==7}">
				<c:if test="${hospital.hos_time7S<=time and time<hospital.hos_time7C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time7S>time or hospital.hos_time7C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		</div>
		<div class="bg-gray-0 p-3 d-flex rounded-3 row">
			<div id="detail_timeAndEtc" class="col-6">
				<div class="fs-15 fw-7 text-black-6">오늘</div>
				<div class="fs-15 fw-3">
					<c:if test="${day==1 and (hospital.hos_time1S!='null' or hospital.hos_time1C!='null')}">
						${fn:substring(hospital.hos_time1S,0,2)}:${fn:substring(hospital.hos_time1S,2,4)}
						-
						${fn:substring(hospital.hos_time1C,0,2)}:${fn:substring(hospital.hos_time1C,2,4)}
				    </c:if>
				    <c:if test="${day==2 and (hospital.hos_time2S!='null' or hospital.hos_time2C!='null')}">
						${fn:substring(hospital.hos_time2S,0,2)}:${fn:substring(hospital.hos_time2S,2,4)}
						-
						${fn:substring(hospital.hos_time2C,0,2)}:${fn:substring(hospital.hos_time2C,2,4)}
				    </c:if>
				    <c:if test="${day==3 and (hospital.hos_time3S!='null' or hospital.hos_time3C!='null')}">
						${fn:substring(hospital.hos_time3S,0,2)}:${fn:substring(hospital.hos_time3S,2,4)}
						-
						${fn:substring(hospital.hos_time3C,0,2)}:${fn:substring(hospital.hos_time3C,2,4)}
				    </c:if>
				    <c:if test="${day==4 and (hospital.hos_time4S!='null' or hospital.hos_time4C!='null')}">
						${fn:substring(hospital.hos_time4S,0,2)}:${fn:substring(hospital.hos_time4S,2,4)}
						-
						${fn:substring(hospital.hos_time4C,0,2)}:${fn:substring(hospital.hos_time4C,2,4)}
				    </c:if>
				    <c:if test="${day==5 and (hospital.hos_time5S!='null' or hospital.hos_time5C!='null')}">
						${fn:substring(hospital.hos_time5S,0,2)}:${fn:substring(hospital.hos_time5S,2,4)}
						-
						${fn:substring(hospital.hos_time5C,0,2)}:${fn:substring(hospital.hos_time5C,2,4)}
				    </c:if>
				    <c:if test="${day==6 and (hospital.hos_time6S!='null' or hospital.hos_time6C!='null')}">
						${fn:substring(hospital.hos_time6S,0,2)}:${fn:substring(hospital.hos_time6S,2,4)}
						-
						${fn:substring(hospital.hos_time6C,0,2)}:${fn:substring(hospital.hos_time6C,2,4)}
				    </c:if>
					<c:if test="${day==7 and (hospital.hos_time7S!='null' or hospital.hos_time7C!='null')}">
						${fn:substring(hospital.hos_time7S,0,2)}:${fn:substring(hospital.hos_time7S,2,4)}
						-
						${fn:substring(hospital.hos_time7C,0,2)}:${fn:substring(hospital.hos_time7C,2,4)}
				    </c:if>
		    	</div>
			</div>
			<div class="col-6">
				<div class="fs-15 fw-7 text-black-6">기타정보</div>
				<div id="detail_hospitalEtc" class="fs-15 fw-1">
					<c:if test="${hospital.hos_etc!='null'}">
						${hospital.hos_etc}
					</c:if>
					<c:if test="${hospital.hos_etc=='null'}">
						없음
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	<div id="detail_hosSub">
		<p class="fs-18 fw-7">병원 정보</p>
		<div class="text-black-5 fs-15 fw-6">
			<c:if test="${hospital.hos_info!='null'}">
				${hospital.hos_info}
			</c:if>
			<c:if test="${hospital.hos_info=='null'}">
				정보없음
			</c:if>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	<div id="detail_hosPosition">
		<p class="fs-18 fw-7">병원 위치</p>
		<div class="fs-14 text-black-6 fw-7 mb-3">${hospital.hos_addr}</div>
		<div class="mb-3">
			<jsp:include page="/WEB-INF/views/common/staticHosMap.jsp"/>
		</div>
		<c:if test="${hospital.hos_mapImg!='null'}">
			<div class="fs-15 text-black-7 fw-9 text-center">&lt;간이약도&gt;</div>
			<div class="fs-14 text-black-6 fw-7 text-center">${hospital.hos_mapImg}</div>
		</c:if>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	<div id="detail_hosTell" class="d-flex">
		<div class="col-6">
			<p class="fs-18 fw-7">병원 번호</p>
			<p>${hospital.hos_tell1}</p>
		</div>
		<c:if test="${hospital.hos_eryn==1}">
			<div class="col-6">
				<p class="fs-18 fw-7">응급실 번호</p>
				<p>${hospital.hos_tell3}</p>
			</div>
		</c:if>
	</div>

	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	<div id="detail_hosRev">
		<p class="fs-18 fw-7">진료 후기</p>
		<c:forEach begin="1" end="3">
			<div class="detail-hosRev-item d-flex align-items-center bg-black-1">
				<div class="detail_hosRev_profile">
					<img src="${PageContext.reqeust.contextPath }/image_bundle/face.png" width="35" height="35" class="rounded-circle">
					<div class="text-black-6 fw-7 fs-15 text-center">김OO</div>
				</div>
				<div class="detail_hosRev_content">
					진료비가 싸고 의사선생님이 너무 친절하세요!
				</div>
				<div class="detail_hosRev_score d-flex justify-content-center align-items-center">
					<img src="/images/star.png" width="15" height="15">
					<div>3.5</div>
				</div>
			</div>
		</c:forEach>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-green-1"></div>
	
	<div id="detail_btn_box">
		<div class="d-flex justify-content-center align-items-center">
			<c:if test="${hospital.doc_cnt>0}">
			<button id="reservation_btn" data-hos-name="${hospital.hos_name}" data-hos-num="${hospital.hos_num}">
                진료 예약하기
            </button>
            </c:if>
			<div id="call_btn">
				전화하기
			</div>
		</div>
	</div>
</div>

<div id="reservation_page" style="display:none;">
	<jsp:include page="/WEB-INF/views/reservation/reservation.jsp"/>
</div>   
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(function(){
    $('#reservation_btn').click(function(event){
        $.ajax({
            url: '/reservation/reservation',
            method: 'get',
            data: { hos_num: '${hospital.hos_num}'},
            dataType: 'json',
            success: function(param) {
            	if(param.result=='success'){
            		$('#reservation_page').show();
            		initializeCalendar('${hospital.hos_num}');
            	}else if(param.result=='logout'){
            		alert('로그인 후 이용해주세요')
            		location.href="/member/login";
            	}else{
            		alert('예약 신청 페이지 오류 발생');
            	}
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });
    });
    
});

</script>
