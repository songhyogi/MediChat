<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
	<div style="text-align:right;">
	    <span style="display:block; font-weight:bold;">의사회원 로그인</span>
	</div>
	<form:form action="login" id="doctor_login" modelAttribute="doctorVO" style="padding-bottom:50px;">
<!-- 	<input type="hidden" id="lon" name="user_lon" value="" >
		<input type="hidden" id="lat" name="user_lat" value="" > -->
		<ul>
			<li>
				<form:input path="mem_id" placeholder="아이디" autocomplete="off" style="margin-top:130px;"/>
				<form:errors path="mem_id" cssClass="error-color"/>
			</li>
			<li>
				<form:password path="doc_passwd" placeholder="비밀번호" style="margin-top:15px;"/>
				<form:errors path="doc_passwd" cssClass="error-color"/>
			</li>
		</ul>
		<div>
			<form:errors element="div" cssClass="error-color"/>
			<form:button class="login_btn">로그인</form:button>
		</div>
		<div class="button-container">
			<label for="auto">
			<input type="checkbox" name="auto" id="auto">자동로그인</label>
			<input type="button" value="아이디 찾기" style="margin-right:10px;">
			<input type="button" value="비밀번호 찾기">
		</div>
	</form:form>
</div>
<!-- 위도 경도 보내기 -->
<!-- <script>
window.onload = function() {
    const log_btn = document.getElementById('login_btn');
    log_btn.onsubmit = function(event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

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
                    log_btn.submit(); // 폼 제출
                },
                function(error) {
                    const lat = defaultLat;
                    const lon = defaultLon;
                    document.getElementById('lat').value = lat;
                    document.getElementById('lon').value = lon;
                    log_btn.submit(); // 폼 제출
                }
            );
        } else {
            alert('Geolocation is not supported by this browser.');
            const lat = defaultLat;
            const lon = defaultLon;
            document.getElementById('lat').value = lat;
            document.getElementById('lon').value = lon;
            log_btn.submit(); // 폼 제출
        }
    };
};
</script> -->