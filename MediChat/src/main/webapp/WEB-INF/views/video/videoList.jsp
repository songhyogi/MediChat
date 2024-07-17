<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="page-main">
<div class="page-one">
		<h5>홈 > 건강 비디오</h5> 
		<p>		
		<h2>&nbsp;&nbsp;&nbsp;<b>건강 비디오</b></h2>
		<br>
		<form action="videoList" method="get" class="align-center">
			<div class="container-input">
			<select name="keyfield" id="selectinput">
				<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
				<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>내용</option>
				<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목 또는 내용</option>
				<option value="4"<c:if test="${keyfield ==4}">selected</c:if>>작성자</option>
			</select>
			<input type="text" name="keyword" placeholder="Search" name="text" class="input">
				<button style="height:35px;'" class="button font" type="submit">검색</button>
			</div>
		</form>
		<hr size="1" width="100%">
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button  class="default-btn" type="button"  onclick="location.href='${pageContext.request.contextPath}/video/videoWrite'">글쓰기</button>
			</div>
		</c:if>
	<c:if test="${count  == 0}">
			건강 비디오 내역이 없습니다.
	</c:if>
	<c:if test="${count > 0}">
		<c:forEach var="v" items="${list}">
					<br><br>
					${fn:substring(v.video_content,0,fn:indexOf(v.video_content, '</figure>')+9)}
					<a href="${pageContext.request.contextPath}/video/videoDetail?video_num=${v.video_num}">
							<ul class="align-center">
								<li style="font-size:16pt;">${v.video_title}</li>
								<li> ${v.v_reg_date} 조회수 : ${v.video_hit} &nbsp;   </li>
							</ul>
						</a>
			
		</c:forEach>
		<div class="align-center float-clear">
			${page}
		</div>
	</c:if>
	</div>
</div>