<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
<p class="text-lightgray fw-7 fs-13">홈 > 병원 찾기 > 검색 결과</p>
	<!-- 검색 창 시작 -->
	<form id="search_form" class="d-flex justify-content-center align-items-center">
		<img id="search_back" src="/images/back.png" width="20" height="20">
		<input type="text" id="h-search" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상">
		<i id="h-search-icon" class="bi bi-search"></i>
	</form>
	<!-- 검색 창 끝 -->
	<!-- 검색 모드 시작 -->
	<div class="d-flex justify-content-start align-items-center m-3">
		<div id="filter-img-box">
			<img src="/images/filter.png" width="20" height="20">
		</div>
		<div class="border-end" style="height: 40px;"></div>
		<!-- SortType -->
		<div class="filter-item">가까운 순 <img src="/images/down.png" width="10" height="10"></div>
		<div id="sortType_box" style="display: none;">
		
		</div>
		<!-- commonFilter -->
		<div></div>
		<div class="filter-item">내 위치</div>
		<div class="filter-item">비대면 진료</div>
		<div class="filter-item">진료 중</div>
		<div class="filter-item">야간 진료</div>
		<div class="filter-item">주말 진료</div>
		
	</div>
	<!-- 검색 모드 끝 -->
	<!-- 병원 리스트 시작 -->
	<c:forEach items="${hosList}" var="hospital">
		<div class="hospital-box">
			<div class="hospital-name fs-17 fw-8 text-black-6">${hospital.hos_name}</div>
			<div class="hospital-sub fs-11 fw-6 text-gray-7">정형외과</div>
			
			<!-- 진료시간이 있으면-->
			<div class="hospital-open fs-13 fw-7 text-black-4">진료중 <div class="vr"></div> 19:30에 마감</div>
			
			<div class="hospital-address fs-12 fw-7 text-black-3">${hospital.hos_addr}</div>
			<div class="hospital-docCnt fs-11 fw-8 text-black-3">정형외과 전문의 ${hospital.doc_cnt}명</div>
		</div>
	<hr width="100%">
	</c:forEach>
	<!-- 병원 리스트 끝 -->
</div>
