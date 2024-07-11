<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main" class="d-flex justify-content-center align-items-center text-center fs-20" style="height:700px;">
	메인 요소 나중에 수정
</div>


<!-- 접속 시 위치정보 보낼 폼 -->
<form id="location_form" action="/main/locationInfo" method="post">
	<input type="hidden" id="lat" name="user_lat" value="">
	<input type="hidden" id="lon" name="user_lon" value="">
</form>
<!-- 위도 경도 보내기 -->
<script>
window.onload = function() {
	const location_form = document.getElementById('location_form');
	if('${user_lat}'=='정보 없음' || '${user_lon}'=='정보 없음'){
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