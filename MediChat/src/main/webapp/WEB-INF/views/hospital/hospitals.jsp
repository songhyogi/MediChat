<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 카테고리 시작 -->
<div>
	홈 > 병원 찾기
	<h4>병원 찾기</h4>
	<p>병원 예약하고 편하게 방문해보세요</p>
	<h4>어떤 병원을 찾으세요?</h4>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
	</div>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color text-center">
				<p>아이콘</p>
				<p>피부과</p>
			</div>
		</div>
	</div>
	<!-- 더 보기 시작 -->
	<hr>
	<h4 class="text-center text-bold">더 보기</h4>
	<!-- 더 보기 끝 -->
</div>

<div style="height:50px;" class="bg-color"></div>
<!-- 카테고리 끝 -->
<!-- 어떻게 아프신가요 시작 -->
<div>
	<h3>어떻게 아프신가요?</h3>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">독감</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">탈모</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">비염</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">대상포진</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">다이어트</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border rounded-5 category-color-bold">
				<p class="text-center">아토피</p>
			</div>
		</div>
	</div>
</div>
<!-- 어떻게 아프신가요 끝 -->
<!-- 검색창 + 인기 검색어 시작 -->
<div class="bg-color">
	<form class="form-control">
		<div class="d-flex">
			<input type="text" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상">
			<input type="submit" value="검색">
		</div>
	</form>
	<p>인기 검색어</p>
	<div class="row justify-content-between">
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#여드름</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#지루성 피부염</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#감기</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#두드러기</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#역류성 식도염</p>
			</div>
		</div>
		<div class="col-2">
			<div class="border text-center rounded-5 bg-white">
				<p>#보톡스</p>
			</div>
		</div>
	</div>
</div>
<!-- 검색창 + 인기 검색어 끝 -->
<!-- 지도 시작 -->
<div>
	<h4>내 주변 병원</h4>
	<jsp:include page="/WEB-INF/views/common/map.jsp"/>
</div>
<!-- 지도 끝 -->
