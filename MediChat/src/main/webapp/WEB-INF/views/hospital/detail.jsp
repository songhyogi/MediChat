<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hos.css" type="text/css">
<div>
	<div class="p-3">
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
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	
	<div id="detail_hosTime" class="p-3">
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
		<div class="bg-gray-0 p-3 rounded-3 row">
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
	<div style="height:18px;" class="bg-gray-0"></div>
	
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
	<div style="height:15px;" class="bg-gray-0"></div>
	
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
	<div style="height:20px;" class="bg-gray-0"></div>
	
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
	<div style="height:18px;" class="bg-gray-0"></div>
	
	<div id="detail_hosRev">
		<p class="fs-18 fw-7">진료 후기</p>
		<c:if test="${reviewCount == 0}">
			<div class="align-center">
				<p>등록된 후기가 없습니다.
			</div>
		</c:if>
		<c:if test="${reviewCount >0}">
			<c:forEach var="r" items="${reviewList}">
				<div class="detail-hosRev-item d-flex align-items-center bg-black-1" style="margin:0 15%;">
					<div class="detail_hosRev_profile">
						<img src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${r.mem_num}" width="35" height="35" class="rounded-circle">
						<div class="text-black-6 fw-7 fs-15 text-center">${r.mem_id}</div>
					</div>
					<div class="detail_hosRev_content">
						${r.rev_title}
					</div>
					<div class="detail_hosRev_score d-flex justify-content-center align-items-center">
						<img src="/images/star.png" width="15" height="15">
						<div>${r.rev_grade}</div>
					</div>
					<div   style="float:right; margin-left:60px; padding-top:10px;">
					   <label class="container"  style="transform: rotate(180deg);">
						<input checked="checked"   type="checkbox"  >
						<svg viewBox="0 0 512 512" height="1em" 
						xmlns="http://www.w3.org/2000/svg" class="chevron-down toggle" >
						<path d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"></path></svg>
					</label>
					</div>
				</div>
				<div  id="review_content" class="hide"  style="margin:0 20%;">
					<div class="align-right" style="width:95%;" ><c:if test="${empty r.rev_modify}">작성일 : ${r.rev_reg}</c:if><c:if test="${!empty r.rev_modify}">수정일 : ${r.rev_modify}</c:if></div>
					<hr width="100%" size="1">
					<div style="margin:0 15px;"> ${r.rev_content}</div>
				</div>
			</c:forEach>
			<div style="text-align:center;">
				${reviewPage}
			</div>
			<script type="text/javascript">
				$('.chevron-down').click(function(){
					if($('#review_content').hasClass('hide')){
						$('#review_content').removeClass('hide');
					}else{
						$('#review_content').addClass('hide');
					}
				 });
		</script>
		</c:if>
	</div>
	
	<div class="line"></div>
	<div style="height:20px;" class="bg-gray-0"></div>
	
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
            data: {hos_num: '${hospital.hos_num}'},
            dataType: 'json',
            success: function(param) {
                if(param.result == 'success'){
                	$('#reservation_page').show();
                    initializeCalendar('${hospital.hos_num}');
                } else if(param.result == 'logout'){
                    alert('로그인 후 이용해주세요');
                    location.href = "${pageContext.request.contextPath}/member/login";
                } else if(param.result == 'doctor') {
                    alert('의사회원은 이용할 수 없습니다.');
                } else if(param.result == 'suspended') {
                    alert('정지회원입니다. 일반회원의 경우에만 이용할 수 있습니다.');
                } else if(param.result == 'unauthorized') {
                    alert('해당 서비스는 이용할 수 없습니다.');
                } else {
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
