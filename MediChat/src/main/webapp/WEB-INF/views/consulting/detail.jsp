<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div>
	<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담 > 상세</p>
	<%-- <c:if test="${user.mem_num == consulting.member.mem_num}">
		<div class="float-end">
			<button onclick="location.href='/consultings/modify/${consulting.con_num}'" class="btn-green">글 수정</button>
			<button onclick="location.href='/consultings/remove/${consulting.con_num}'" class="btn-green">글 삭제</button>
		</div>
	</c:if> --%>
	<div class="p-2">
		<h1 class="fs-24 fw-8">${consulting.con_title}</h1>
		<div class="fs-14 text-black-4 mb-4">${fn:substring(consulting.member.mem_name,0,1)}OO | ${consulting.con_rDate}</div>
		<div class="fs-16 text-black-6 p-5">
			${consulting.con_content}
		</div>
		<div class="fs-12 bg-gray-1 d-inline rounded-2 p-1 text-gray-6">${consulting.con_type_name}</div>
		<div class="mt-4">
			<ul class="fs-10 text-black-2">
				<li>'의료 상담'의 모든 게시물은 저작권의 보호를 받습니다. 저작권자의 명시적 동의 없이 게시물을 복제, 배포, 전송 등 활용하는 것은 저작권 침해로서 법적 책임을 질 수 있으니 유의하세요!</li>
			</ul> 
		</div>
	</div>
	<div class="line"></div>
	<div style="height:70px; background-color: #F3F6F9; color:#2D2E2F" class="fs-18 fw-7 d-flex align-items-center p-4">${consulting.con_type_name}과 관련 ${consulting.con_re_cnt}개의 답변</div>
	<div id="cReBox" class="p-2">
		<c:forEach items="${cReList}" var="cRe">
			<div class="d-flex my-3">
				<div class="me-3">
					<img src="/doctor/docViewProfile?mem_num=${cRe.doctor.doc_num}" class="border rounded-circle" width="48px" height="48px">
				</div>
				<div>
					<div class="fs-16 fw-7 text-black-7">${cRe.doctor.mem_name} 선생님</div>
					<div class="fs-14 text-black-4">${cRe.hospital.hos_name} | ${fn:substring(cRe.con_re_rDate,0,10)}</div>
				</div>
			</div>
			<div class="line"></div>
			<div class="p-5">
				${cRe.con_re_content}
			</div>
			<div>
				<div class="fs-16 text-black-7 fw-6">답변을 작성한 선생님에게 진료 예약하기</div>
				<div class="border rounded-1 d-flex justify-content-between align-items-center py-3 px-2">
					<div class="me-3 border bg-gray-0 p-3 rounded-1">
						<img src="/images/hospital.png" width="72" height="72">
					</div>
					<div>
						<div class="fs-17 fw-7 text-black-7 text-center">
							${cRe.hospital.hos_name}
						</div>
						<div class="fs-14 text-black-4">
							${cRe.hospital.hos_addr}
						</div>
					</div>
					<div>
						<button class="toGoHosBtn" onclick="location.href='/hospitals/search/detail/${cRe.hospital.hos_num}'">병원 정보 보러가기 ></button>
					</div>
				</div>
			</div>
			<div>
				<ul class="fs-11 text-black-2">
					<li>'의료상담'에서의 답변은 의료진의 경험과 지식을 바탕으로 한 참고용 정보 제공입니다. 정확한 개인 증상 파악 및 진단은 의료기관 내방을 통해 진행하시기 바랍니다.</li>
				</ul>
			</div>
			<div class="d-flex justify-content-center my-2">
				<div class="goodBtn" data-cReNum="${cRe.con_re_num}"><img src="/images/good.png" width="30" height="30" class="me-4">만족해요</div>
				<div class="badBtn" data-cReNum="${cRe.con_re_num}"><img src="/images/bad.png"  width="30" height="30" class="me-4">만족스럽지 않아요</div>
			</div>
			<div class="line"></div>
			<div class="bg-gray-0" style="height: 20px;"></div>
		</c:forEach>
	</div>
	<c:if test="${consulting.con_re_cnt>5}">
		<div id="moreCReBox" style="height: 60px; margin: 20px 0; background-color: #F7F9FA; cursor: pointer;" class="d-flex justify-content-center align-items-center fs-18 fw-7 text-black-7">더보기</div>
	</c:if>
</div>

<c:if test="${user.mem_auth==3}">
	<form action="/consultings/createReply" method="post">
		<input type="hidden" name="con_num" value="${consulting.con_num}">
		<textarea rows="7" class="form-control" name="con_re_content"></textarea>
		<input type="submit" value="전송">
	</form>
</c:if>

<script>
$(document).ready(function() {
	const cReBox = $('#cReBox');
	let pageNum = 2;
	const pageItemNum = 5;
	const con_num = '${consulting.con_num}';
	$('#moreCReBox').click(function(){
		$.ajax({
			url: '/consultings/detail-ajax',
			data: {pageNum:pageNum, 
					pageItemNum:pageItemNum, 
					con_num:con_num},
			type: 'GET',
			dataType: 'JSON',
			success: function(param){
				if(param.length==0){
					return;
				}
				if(param.length<pageItemNum){
					$('#moreCReBox').removeClass("d-flex").hide();
				}
				pageNum++;
				let output = '';
				for(let i=0; i<param.length; i++){
					output += '<div class="d-flex my-3">';
					output += '<div class="me-3">';
					output += '<img src="/images/doctor.png" class="border rounded-circle" width="48px" height="48px">';
					output += '</div><div><div class="fs-16 fw-7 text-black-7">'+param[i].doctor.mem_name+' 선생님</div>';
					output += '<div class="fs-14 text-black-4">'+param[i].hospital.hos_name+' | '+param[i].con_re_rDate.substring(0,10)+'</div>';
					output += '</div></div><div class="line"></div><div class="p-5">';
					output += param[i].con_re_content+'</div><div>';
					output += '<div class="fs-16 text-black-7 fw-6">답변을 작성한 선생님에게 진료 예약하기</div>';
					output += '<div class="border rounded-1 d-flex justify-content-between align-items-center py-3 px-2">';
					output += '<div class="me-3 border bg-gray-0 p-3 rounded-1">';
					output += '<img src="/images/hospital.png" width="72" height="72">';
					output += '</div><div><div class="fs-17 fw-7 text-black-7 text-center">';
					output += param[i].hospital.hos_name+'</div><div class="fs-14 text-black-4">';
					output += param[i].hospital.hos_addr;
					output += '</div></div><div>';
					output += '<button class="toGoHosBtn" onclick="location.href=\'/hospitals/search/detail/'+param[i].hospital.hos_num+'\'">병원 정보 보러가기</button></div>';
					output += '</div></div><div><ul class="fs-11 text-black-2">';
					output += '<li>\'의료상담\'에서의 답변은 의료진의 경험과 지식을 바탕으로 한 참고용 정보 제공입니다. 정확한 개인 증상 파악 및 진단은 의료기관 내방을 통해 진행하시기 바랍니다.</li>';
					output += '</ul></div><div class="d-flex justify-content-center my-2">';
					output += '<div class="goodBtn" data-cReNum="'+param[i].con_re_num+'"><img src="/images/good.png" width="30" height="30" class="me-4">만족해요</div>';
					output += '<div class="badBtn" data-cReNum="'+param[i].con_re_num+'"><img src="/images/bad.png"  width="30" height="30" class="me-4">만족스럽지 않아요</div>';
					output += '</div><div class="line"></div><div class="bg-gray-0" style="height: 20px;"></div>';
				}
				cReBox.append(output);
			},
			error: function(){
				
			}
		})
	});
});
</script>