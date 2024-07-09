<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 카테고리 시작 -->
<div>
	<p class="text-lightgray fw-7" style="font-size:13px;">홈 > 병원 찾기</p>
	<h4 class="text-black fw-6" style="font-size: 21px;" >병원 찾기</h4>
	<p class="text-lightgray fw-6" style="font-size: 13px;">병원 예약하고 편하게 방문해보세요</p>
	<h4 class="text-lightblack fw-7">어떤 병원을 찾으세요?</h4>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">산부인과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p> 
				<p class="sub-name">이비인후과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p> 
				<p class="sub-name">내과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p> 
				<p class="sub-name">안과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">가정의학과</p>
			</div>
		</div>
	</div>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">소아과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">정형외과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">정신건강의학과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">비뇨기과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">치과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject border rounded-2 category-color text-center">
				<p class="sub-icon">아이콘</p>
				<p class="sub-name">신경외과</p>
			</div>
		</div>
	</div>
	<!-- 더 보기 시작 -->
	<hr>
	<div id="more">
		<h4 class="text-center text-lightgray fw-7">더 보기</h4>
	</div>
	<!-- 더 보기 끝 -->
</div>

<div style="height:20px;" class="bg-color"></div>
<!-- 카테고리 끝 -->
<!-- 어떻게 아프신가요 시작 -->
<div class="p-4">
	<h4 class="text-lightblack fw-6" style="font-size: 20px;">어떻게 아프신가요?</h4>
	<div class="row justify-content-between">
		<c:forEach items="${howSick}" var="hs">
			<div class="col-2">
				<div class="hs-item border rounded-2 category-color-bold">
					<p class="text-center">${hs}</p>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<!-- 어떻게 아프신가요 끝 -->
<!-- 검색창 + 인기 검색어 시작 -->
<div class="bg-color p-5">
	<form>
		<div class="d-flex">
			<input type="text" id="h-search" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상"><span id="h-search-icon"><i id="search-icon" class="bi bi-search"></i></span></input>
		</div>
	</form>
	<p class="text-lightgray fw-7 mt-3">인기 검색어</p>
	<div class="d-inline">
		<c:forEach items="${hotKeyWord}" var="hkw">
			<div class="hkw-item border text-center rounded-5 bg-gray-1 px-2">
				<p class="hkw fs-14 fw-7 text-gray-7">#${hkw}</p>
			</div>
		</c:forEach>
	</div>
</div>
<!-- 검색창 + 인기 검색어 끝 -->
<!-- 지도 시작 -->
<div id="mapDiv">
	<h4 class="fw-7 my-3">내 주변 병원</h4>
	<jsp:include page="/WEB-INF/views/common/map.jsp"/>
</div>
<!-- 지도 끝 -->
