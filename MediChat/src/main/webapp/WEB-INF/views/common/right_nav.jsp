<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<a id="right-nav-img" href="#"><img src="/images/right_nav_img.png" width="290" height="420"></a>
	<div style="height: 50px;"></div>
	<div>
		<h5 class="fw-9 fs-18 my-4">실시간 상담 베스트</h5>
		<c:forEach begin="1" end="5">
		<div class="d-flex justify-content-start align-items-center mb-3 px-2"  style="height:24px;">
			<img src="/images/letter-q.png" width="24px" height="24px" class="me-2">
			<a class="text-gray-7 right-nav-q" href="#">이런것이 질문입니다. 답변 부안녕안녕안녕안녕안녕안녕안녕안녕</a>
		</div>
		</c:forEach>
	</div>
</div>