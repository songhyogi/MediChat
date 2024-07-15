<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apiKey}&libraries=services"></script>
<div class="d-flex justify-content-center">
	<div id="map" style="width:700px; height:500px;" class="rounded-4"></div>
</div>
<script>
kakao.maps.load(function(){
	// 카카오맵 API
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
					center: new kakao.maps.LatLng(33.450701, 126.570667), //중심 좌표 (필수)
					level: 3 //확대 수준 (기본값: 3)			
	};
	
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

	
	//지도에 마커와 인포윈도우를 표시하는 함수입니다.
	function displayMarker(locPosition, message){
		
		//마커를 생성합니다.
		var marker = new kakao.maps.Marker({
			map: map,
			position: locPosition
		});
		
		var iwContent = message; //인포 윈도우에 표시할 내용
		var iwRemoveable = true;
		
		// 인포윈도우를 생성합니다
	    var infowindow = new kakao.maps.InfoWindow({
	        content : iwContent,
	        removable : iwRemoveable
	    });
	    
	    /* // 인포윈도우를 마커위에 표시합니다 
	    infowindow.open(map, marker);
	     */
	 	// 마커에 클릭이벤트를 등록합니다
	    kakao.maps.event.addListener(marker, 'click', function() {
	          // 마커 위에 인포윈도우를 표시합니다
	          infowindow.open(map, marker);  
	    });
	    
	    // 지도 중심좌표를 접속위치로 변경합니다
	    map.setCenter(locPosition);   
	}
	
	//내 위치 정보를 사용할 수 있는지 확인 geolocation
	if(navigator.geolocation){
		//GeoLocation을 이용해서 접속 위치를 얻어옵니다.
		navigator.geolocation.getCurrentPosition(function(position){
			
			const lat = position.coords.latitude; // 위도
			const lon = position.coords.longitude; // 경도
			
			const locPosition = new kakao.maps.LatLng(lat,lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다.
			var message = '<div style="padding:5px;">현재 위치</div>';
			
			//마커와 인포윈도우를 표시합니다.
			displayMarker(locPosition, message);
			
		});
	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포 윈도우 내용을 설정합니다.
		const locPosition = new kakao.maps.LatLng(33.450701, 126.570667);
		var message = 'geolocation을 사용할 수 없습니다.';
		
		displayMarket(locPosition, message);
	}
	
	// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();
	// 지도 타입 컨트롤을 지도에 표시합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
	
	// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl();
	// 지도 타입 컨트롤을 지도에 표시합니다
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
	
	// 장소 검색 객체를 생성
	const places = new kakao.maps.services.Places(); 

	// 키워드로 장소를 검색
	places.keywordSearch('입력 값', placesSearchCB); 

	// 키워드 검색 완료 시 호출되는 콜백함수
	function placesSearchCB (data, status, pagination) {
	    if (status === kakao.maps.services.Status.OK) {

	        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
	        // LatLngBounds 객체에 좌표를 추가
	        let bounds = new kakao.maps.LatLngBounds();

	        for (let i=0; i<data.length; i++) {
	            displayMarker(data[i]);    
	            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
	        }       

	        // 검색된 장소 위치를 기준으로 지도 범위를 재설정
	        map.setBounds(bounds);
	    } 
	}
});

</script>