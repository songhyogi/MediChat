<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 메인 시작 -->
<div class="page-main">	
	<a href="">실시간 의료 상담</a>
	<a href="">병원 찾기</a>
	<a href="">증상 검색</a>
	<div class="align-center">
	<h2>메디챗의 건강 블로그</h2>
		<a href="#">블로그 글1</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">블로그 글2</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">블로그 글3</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">블로그 글4</a>
	<h2>커뮤니티</h2>
	</div>
	<div class="align-right">
		<a href="">더보기</a>
	</div>
	<div class="align-center">
		<a href="#">커뮤니티 글1</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">커뮤니티 글2</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">커뮤니티 글3</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<br><br>
		<a href="#">커뮤니티 글4</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">커뮤니티 글5</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
		<a href="#">커뮤니티 글6</a>
		<a href="#" style="margin: 0 10px;">&nbsp;</a>
	</div>
</div>
<!-- 메인 끝 -->





<!-- 접속 시 위치정보 보낼 폼 -->
<form id="location_form" action="/locationInfo" method="post">
<input type="hidden" id="lat" name="user_lat" value="">
<input type="hidden" id="lon" name="user_lon" value="">
</form>
<!-- 위도 경도 보내기 -->
<script>
window.onload = function() {
	if(${user_lat} == null || ${user_lon} == null){
		const location_form = document.getElementById('location_form');
	    // 기본값 설정
	    const defaultLat = 37.4981646510326;
	    const defaultLon = 127.028307900881;
	    
	    // 위치 정보 가져오기
	    if (navigator.geolocation) {
	        navigator.geolocation.getCurrentPosition(
	            function(position) {
	                const lat = position.coords.latitude;
	                const lon = position.coords.longitude;
	                document.getElementById('lat').value = lat;
	                document.getElementById('lon').value = lon;
	                location_form.submit(); // 폼 제출
	            },
	            function(error) {
	                const lat = defaultLat;
	                const lon = defaultLon;
	                document.getElementById('lat').value = lat;
	                document.getElementById('lon').value = lon;
	                location_form.submit(); // 폼 제출
	            }
	        );
	    } else {
	        const lat = defaultLat;
	        const lon = defaultLon;
	        document.getElementById('lat').value = lat;
	        document.getElementById('lon').value = lon;
	        
	        location_form.submit(); // 폼 제출
	    }
	}
};
</script>