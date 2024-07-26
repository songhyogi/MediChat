<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ych.css" type="text/css">
<!-- MyPage 메뉴 시작 -->
<div class="side-bar">
	<h1>마이페이지</h1>
	<ul style="padding: 0 0 !important; margin-top:20px;">
		<li>
			<c:if test="${mem_profile == null}">
				<img src="${pageContext.request.contextPath}/member/memPhotoView"
				width="150" height="150" class="my-photo border rounded-circle" 
				onclick="location.href='${pageContext.request.contextPath}/member/myPage'">
				<div class="camera" id="photo_btn">
					<img src="${pageContext.request.contextPath}/images/reset.png" width="25">
				</div>			
			</c:if>
			<c:if test="${mem_profile != null}">
				<img src="${mem_profile}" width="150" height="150" class="border rounded-circle" 
				onclick="location.href='${pageContext.request.contextPath}/member/myPage'">
			</c:if>
		</li>
		<li>
			<div id="photo_choice" style="display:none;">
				<input type="file" id="upload" accept="image/gif,image/png,image/jpeg"><br>
				<input type="button" value="변경" id="photo_submit"> 
				<input type="button" value="취소" id="photo_reset"> 
			</div>
		</li>

        <li style="margin-bottom:10px;">
            <a href="${pageContext.request.contextPath}/memberDrug/list" class="detail-btn">약 복용 내역</a>
        </li>
        <li style="margin-bottom:10px;">
            <a href="${pageContext.request.contextPath}/review/reviewMemList" class="detail-btn">후기내역</a>
        </li>
        <li style="margin-bottom:10px;">
            <a href="${pageContext.request.contextPath}/chat/chatView" class="detail-btn">비대면 진료</a>
        </li>
		<li style="margin-bottom:10px;">
            <a href="${pageContext.request.contextPath}/member/modifyUser" class="detail-btn">회원정보 수정</a>
        </li>
        <li style="margin-top:50px;">
            <a href="${pageContext.request.contextPath}/member/logout" class="detail-btn">로그아웃</a>
        </li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.profile.js"></script>
<!-- MyPage 메뉴 끝 -->

