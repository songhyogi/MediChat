<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ych.css" type="text/css">
<!-- MyPage 메뉴 시작 -->
<div class="side-bar">
	<ul>
		<li>
			<img src="${pageContext.request.contextPath}/member/memPhotoView"
			width="150" height="150" class="my-photo border rounded-circle" 
			onclick="location.href='${pageContext.request.contextPath}/member/myPage'">
			<div class="camera" id="photo_btn">
			<img src="${pageContext.request.contextPath}/images/re.png" width="25">
			</div>
		</li>
		<li>
			<div id="photo_choice" style="display:none;">
				<input type="file" id="upload" accept="image/gif,image/png,image/jpeg"><br>
				<input type="button" value="변경" id="photo_submit"> 
				<input type="button" value="취소" id="photo_reset"> 
			</div>
		</li>
	</ul>
	<ul>
		<li>
			<input type="button" class="detail-btn" value="회원정보"
			onclick="location.href='${pageContext.request.contextPath}/mypage/memberInfo'">
		</li>
		<li>
			<input type="button" class="detail-btn" value="약 복용 내역"
			onclick="location.href='${pageContext.request.contextPath}/mypage/medicationHistory'">
		</li>
		<li>
			<input type="button" class="detail-btn" value="후기내역"
			onclick="location.href='${pageContext.request.contextPath}/mypage/reviewHistory'">
		</li>
		<li>
			<input type="button" class="detail-btn" value="비대면 진료"
			onclick="location.href='${pageContext.request.contextPath}/chat/chatView'">
		</li>
		<li>
			<input type="button" class="detail-btn" value="로그아웃"
			onclick="location.href='#'">
		</li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.profile.js"></script>
<!-- MyPage 메뉴 끝 -->











