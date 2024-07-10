<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

홈 > 병원 찾기 > 검색 결과
<div>
	<!-- 검색 창 시작 -->
	<form class="form-control">
		back<input type="text" placeholder="지역+과목명, 병원명을 입력해주세요" class="form-control">
	</form>
	<!-- 검색 창 끝 -->
	<!-- 검색 모드 시작 -->
	<div class="d-flex">
		<button class="btn btn-secondary">가까운 순</button>
		<button class="btn btn-secondary">내 위치</button>
		<button class="btn btn-secondary">비대면 진료</button>
		<button class="btn btn-secondary">진료 중</button>
		<button class="btn btn-secondary">주말 진료</button>
	</div>
	<!-- 검색 모드 끝 -->
	<!-- 병원 리스트 시작 -->
	<c:forEach items="${list}" var="hospital">
		<div>
			<ul class="list-unstyled">
				<li><h5>제이마취통증의학과</h5></li>
				<li><small>정형외과</small></li>
				
				<!-- 진료시간이 있으면-->
				<li>진료중 <div class="vr"></div> 19:30에 마감</li>
				
				<li>512m 서울특별시 강남구 역삼동</li>
				<li>전문의 2명</li>
			</ul>
		</div>
	<hr width="100%">
	</c:forEach>
	<!-- 병원 리스트 끝 -->
</div>
