<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 정보 조회</title>
</head>
<body>
	<div class="container">
		<div class="basic">
			<ul>
				<li>이용내역</li>
			</ul>
			<div class="border p-5">
				<div class="row mb-5">
					<div class="col-4 d-flex justify-content-center align-items-center">진료기록</div>
					<div class="col-4 d-flex justify-content-center align-items-center">예약내역</div>
					<div class="col-4 d-flex justify-content-center align-items-center">문의내역</div>
				</div>
				<div class="row mt-5">
					<div class="col-4 d-flex justify-content-center align-items-center"><input type="button" value="0" onclick="location.href='#'"></div>
					<div class="col-4 d-flex justify-content-center align-items-center"><input type="button" value="0" onclick="location.href='#'"></div>
					<div class="col-4 d-flex justify-content-center align-items-center"><input type="button" value="0" onclick="location.href='#'"></div>
				</div>
			</div>
			<ul>
				<li>나의 활동</li>
			</ul>
			<div class="border p-5">
				<div class="row mb-5">
					<div class="col-4 d-flex justify-content-center align-items-center">좋아요</div>
					<div class="col-4 d-flex justify-content-center align-items-center">댓글</div>
				</div>
				<div class="row mt-5">
					<div class="col-4 d-flex justify-content-center align-items-center"><input type="button" value="0" onclick="location.href='#'"></div>
					<div class="col-4 d-flex justify-content-center align-items-center"><input type="button" value="0" onclick="location.href='#'"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
