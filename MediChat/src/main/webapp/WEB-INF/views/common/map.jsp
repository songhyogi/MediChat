<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apiKey}&libraries=services"></script>
</head>
<body>
<div id="map" style="width:500px; height:500px;"></div>
<script>
kakao.maps.load(function(){
	// 카카오맵 API
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
					center: new kakao.maps.LatLng(33.450701, 126.570667), //중심 좌표 (필수)
					level: 3 //확대 수준 (기본값: 3)			
	};
	
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
});
	
</script>
</body>