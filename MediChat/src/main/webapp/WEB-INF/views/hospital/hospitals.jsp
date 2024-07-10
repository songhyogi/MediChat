<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- image: Flaticon.com -->
<!-- 카테고리 시작 -->
<div>
	<p class="text-lightgray fw-7 fs-13">홈 > 병원 찾기</p>
	<h4 class="text-black fw-6 fs-21 mb-4">병원 찾기</h4>
	<p class="text-lightgray fw-6 fs-13 mb-1">병원 예약하고 편하게 방문해보세요</p>
	<h4 class="text-black-6 fw-7 fs-23">어떤 병원을 찾으세요?</h4>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="피부과">
				<p class="sub-icon"><img src="/images/subIcon/skin.png" width="30" height="30"></p>
				<p class="sub-name">피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="산부인과">
				<p class="sub-icon"><img src="/images/subIcon/gynecology.png" width="30" height="30"></p>
				<p class="sub-name">산부인과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="이비인후과">
				<p class="sub-icon"><img src="/images/subIcon/ear.png" width="30" height="30"></p> 
				<p class="sub-name">이비인후과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="내과">
				<p class="sub-icon"><img src="/images/subIcon/physician.png" width="30" height="30"></p> 
				<p class="sub-name">내과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="안과">
				<p class="sub-icon"><img src="/images/subIcon/eye.png" width="30" height="30"></p> 
				<p class="sub-name">안과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="가정의학과">
				<p class="sub-icon"><img src="/images/subIcon/home.png" width="30" height="30"></p>
				<p class="sub-name">가정의학과</p>
			</div>
		</div>
	</div>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="소아과">
				<p class="sub-icon"><img src="/images/subIcon/child.png" width="30" height="30"></p>
				<p class="sub-name">소아과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="정형외과">
				<p class="sub-icon"><img src="/images/subIcon/bone.png" width="30" height="30"></p>
				<p class="sub-name">정형외과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="정신건강의학과">
				<p class="sub-icon"><img src="/images/subIcon/mental.png" width="30" height="30"></p>
				<p class="sub-name">정신건강의학과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="비뇨기과">
				<p class="sub-icon"><img src="/images/subIcon/urology.png" width="30" height="30"></p>
				<p class="sub-name">비뇨기과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="치과">
				<p class="sub-icon"><img src="/images/subIcon/tooth.png" width="30" height="30"></p>
				<p class="sub-name">치과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-1 text-center" data-keyword="신경외과">
				<p class="sub-icon"><img src="/images/subIcon/headache.png" width="30" height="30"></p>
				<p class="sub-name">신경외과</p>
			</div>
		</div>
	</div>
	<!-- 더 보기 시작 -->
	<hr>
	<div id="more">
		<h4 class="text-center text-lightgray fw-7">더 보기</h4>
	</div>
	<div class="overlay" id="overlay"></div>
	<div id="more_box" class="p-5 rounded-top-3 border bg-white" style="display: none;">
		<h5 class="fw-8">진료과목 선택</h5>
		<div>
			<c:forEach items="${subList}" var="sub">
				<c:forEach items="${sub}" var="item" varStatus="loop">
					<c:if test="${loop.index%3==0}">
						<c:set var="sub_name" value="${item}"/>
					</c:if>
					<c:if test="${loop.index%3==1}">
						<c:set var="sub_content" value="${item}"/>
					</c:if>
					<c:if test="${loop.index%3==2}">
						<c:set var="sub_icon" value="${item}"/>
					</c:if>
				</c:forEach>
				<div class="sub-item d-flex justify-content-between" data-keyword="${sub_name}">
					<div class="py-3">
						<h5 class="text-black-6 fw-8 fs-17">${sub_name}</h5>
						<h5 class="text-gray-6 fw-7 fs-14">${sub_content}</h5>
					</div>
					<div class="sub-img">
						<img width="50" height="50" src="/images/subIcon/${sub_icon}.png">
					</div>
				</div>
				<hr noshade="noshade">
			</c:forEach>
		</div>
	</div>
	<script>
        document.getElementById('more').addEventListener('click', function() {
            document.getElementById('more_box').style.display = 'block';
            document.getElementById('overlay').style.display = 'block';
            document.body.style.overflow = 'hidden'; // 외부 스크롤 비활성화
        });

        document.getElementById('overlay').addEventListener('click', function() {
            document.getElementById('more_box').style.display = 'none';
            document.getElementById('overlay').style.display = 'none';
            document.body.style.overflow = 'auto'; // 외부 스크롤 활성화
        });
    </script>
	<!-- 더 보기 끝 -->
</div>

<div style="height:20px;" class="bg-color"></div>
<!-- 카테고리 끝 -->
<!-- 어떻게 아프신가요 시작 -->
<div class="p-4">
	<h4 class="text-black-7 fw-6" style="font-size: 20px;">어떻게 아프신가요?</h4>
	<div class="row justify-content-between">
		<c:forEach items="${howSick}" var="hs">
			<div class="col-2">
				<div class="hs-item rounded-2 bg-green-6">
					<p class="text-center">${hs}</p>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<!-- 어떻게 아프신가요 끝 -->
<!-- 검색창 + 인기 검색어 시작 -->
<div class="bg-green-1 p-5">
	<form id="search_form" class="d-flex">
		<input type="text" id="h-search" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상">
		<i id="h-search-icon" class="bi bi-search"></i>
	</form>
	<p class="text-lightgray fw-7 mt-3">인기 검색어</p>
	<div class="d-inline">
		<c:forEach items="${hotKeyWord}" var="hkw">
			<div class="hkw-item border text-center rounded-5 bg-gray-1 px-2">
				<p class="hkw fs-14 fw-7 text-gray-7">#${hkw}</p>
			</div>
		</c:forEach>
	</div>
	
	<!-- 지도 시작 -->
	<div id="mapDiv">
		<h4 class="fw-7 my-4 fs-22">내 주변 병원</h4>
		<jsp:include page="/WEB-INF/views/common/map.jsp"/>
	</div>
	<!-- 지도 끝 -->
</div>
<!-- 검색창 + 인기 검색어 끝 -->