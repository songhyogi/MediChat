<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
	<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담 > 상세</p>
	<c:if test="${user.mem_num == consulting.member.mem_num}">
		<div>
			<button onclick="location.href='/consultings/modify/${consulting.con_num}'">글 수정</button>
			<button onclick="location.href='/consultings/remove/${consulting.con_num}'">글 삭제</button>
		</div>
	</c:if>
	<div class="p-2">
		<h1 class="fs-24 fw-8">${consulting.con_title}</h1>
		<div class="fs-14 text-black-4 mb-4">${fn:substring(consulting.member.mem_name,0,1)}OO | ${consulting.con_rDate}</div>
		<div class="fs-16 text-black-6 my-3">
			${consulting.con_content}
		</div>
		<div class="fs-12 bg-gray-1 d-inline rounded-2 p-1 text-gray-6">만성질환</div>
	</div>
	<div class="line"></div>
	<div style="height:70px; background-color: #F3F6F9;">답변</div>
	<div class="p-2">
		<c:forEach items="${cReList}" var="cRe">
			<div class="d-flex my-3">
				<div class="me-3">
					<img src="/images/doctor.png" class="border rounded-circle" width="48px" height="48px">
				</div>
				<div>
					<div class="fs-16 fw-7 text-black-7">${cRe.doctor.mem_name} 선생님</div>
					<div class="fs-14 text-black-4">${cRe.hospital.hos_name} | ${fn:substring(cRe.con_re_rDate,0,10)}</div>
				</div>
			</div>
			<div class="line"></div>
			<div class="py-3">
				${cRe.con_re_content}
			</div>
			<div>
				답변을 작성한 선생님에게 진료 예약하기
				<div class="border rounded-1 d-flex justify-content-center align-items-center">
					<div>
						<img src=".." width="72" height="72">
					</div>
					<div>
						${cRe.hospital.hos_name}
					</div>
					<div>
						${cRe.hospital.hos_addr}
					</div>
					<div>
						<button onclick="location.href='/hospitals/search/detail/${cRe.hospital.hos_num}'">병원 정보 보러가기 ></button>
					</div>
				</div>
			</div>
			<div>
				<ul class="fs-12 text-black-2">
					<li>'의료상담'에서의 답변은 의료진의 경험과 지식을 바탕으로 한 참고용 정보 제공입니다. 정확한 개인 증상 파악 및 진단은 의료기관 내방을 통해 진행하시기 바랍니다.</li>
				</ul>
			</div>
			<div class="d-flex justify-content-center my-2">
				<button>만족해요</button>
				<button>만족스럽지 않아요</button>
			</div>
			<div class="line"></div>
			<div class="bg-gray-0" style="height: 20px;"></div>
		</c:forEach>
	</div>
</div>

<div style="height: 500px;"></div>

<c:if test="${user.mem_auth==3}">
	<form action="/consultings/createReply" method="post">
		<input type="hidden" name="con_num" value="${consulting.con_num}">
		
		<textarea rows="7" class="form-control" name="con_re_content"></textarea>
		
		<input type="submit" value="전송">
	</form>
</c:if>