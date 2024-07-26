<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<br><br>
<h4>좋아요 리스트</h4>
<br><br><br>
<div class="align-center">
<h4>건강 매거진 좋아요 리스트</h4>
<c:if test="${count  ==0}">
	좋아요가 없습니다.
</c:if>
<c:if test="${count > 0}">
		<table class="align-center" id="review-table">
		 	<tr>
			 	<th>번호</th>
			 	<th>제목</th>
			 	<th>작성일</th>
			 	<th></th>
			</tr>
	<c:forEach var="r" items="${list}">
			<tr>
				
			</tr>
	</c:forEach>
	</table>
	
	<br><br>
	<div class="align-center">
		${page}
	</div>
</c:if>
<h4>건강 매거진 댓글 좋아요 리스트</h4>
<c:if test="${recount > 0}">
		<table class="align-center" id="review-table">
		 	<tr>
			 	<th>번호</th>
			 	<th>제목</th>
			 	<th>작성일</th>
			 	<th></th>
			</tr>
	<c:forEach var="re" items="${relist}">
			<tr>
	
			</tr>
	</c:forEach>
	</table>
	<br><br>
	<div class="align-center">
		${repage}
	</div>
</c:if>
</div>