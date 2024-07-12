<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<div id="detail_hosName">
		<h4 class="fw-7">${hospital.hos_name}</h4>
	</div>
	<div style="height:15px;" class="bg-green-1"></div>
	<div class="p-4">
		<p class="fs-18 fw-7">진료 시간</p>
	</div>
	<div style="height:15px;" class="bg-green-1"></div>
	<div class="p-4">
		<p class="fs-18 fw-7">진료 과목</p>
	</div>
	<div style="height:15px;" class="bg-green-1"></div>
	<div>
		<p class="fs-18 fw-7">병원 위치</p>
		<p>${hospital.hos_addr}</p>
		<div id="map"></div>
	</div>
	<div style="height:15px;" class="bg-green-1"></div>
	<div>
		<p class="fs-18 fw-7">병원 번호</p>
		<p>${hospital.hos_tell1}</p>
	</div>


</div>