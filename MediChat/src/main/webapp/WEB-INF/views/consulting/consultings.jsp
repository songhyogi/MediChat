<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담</p>


	
	<button id="consulting-btn" onclick="location.href='/consultings/create'">글 작성</button>
	<c:forEach items="${consultingList}" var="consulting">
		<div class="consulting-item-div" data-conNum="${consulting.con_num}">
			<div class="consulting-item-title">${consulting.con_title}</div>
			<div class="consulting-item-content">${consulting.con_content}</div>
			<div class="consulting-item-type"><span>만성질환</span></div>
			<div class="consulting-item-reCnt">
				<div><img src="/images/doctor.png" width="23px" height="23px"></div>
				<div>0개의 답변</div>
			</div>
		</div>
		<div class="line"></div>
	</c:forEach>
	
</div>


<script>
	const consultingItems = document.getElementsByClassName('consulting-item-div');
	for(let i=0; i<consultingItems.length; i++){
		consultingItems[i].onclick = function(){
			location.href="/consultings/detail/"+consultingItems[i].getAttribute('data-conNum');
		}
	}
</script>