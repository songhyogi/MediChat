<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<div class="page-one">
		<h5>홈 > 질병백과사전</h5> 		
		<p>
		<h2>&nbsp;&nbsp;&nbsp;<b>질병 백과사전</b></h2>
	<a href="${pageContext.request.contextPath}/health/healthBlog">건강 블로그</a>
	<form class="align-center" action="${pageContext.request.contextPath}/disease/diseaseDictionary" method="get">
		
		<div class="container-input">
			<select name="keyfield" id="selectinput">
				<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>질병명</option>
				<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>증상</option>
				<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>질병명 또는 증상</option>
			</select>
		  	<input type="text" name="keyword" placeholder="Search" name="text" class="input">
			<button style="height:35px;'" class="button font" type="submit">검색</button>
		</div>
		
	</form>
	<hr size="1" width="100%">
	<br>
		<c:if test="${count  == 0}">
				질병 사전 내역이 없습니다.
		</c:if>
		<c:if test="${count > 0}">
				<table id="diseaseDict">
					<tr>
						<th>
						</th>
					</tr>
				
		<c:forEach var="d" items="${list}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/disease/diseaseDictDetail?sickcd=${d.sickcd}">${d.dis_name}</a></td>
				</tr>
				
		</c:forEach>
				</table>
				<div class="align-center">
					<br>
					${page}
					<br>
				</div>
		</c:if>
	</div>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<h4>홈 > 질병백과사전</h4> 		<a href="${pageContext.request.contextPath}/health/healthBlog">건강 블로그</a>
	<h2>질병 백과사전</h2>
	<form action="${pageContext.request.contextPath}/disease/diseaseDictionary" method="get">
		<select name="keyfield">
			<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>질병명</option>
			<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>증상</option>
			<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>질병명 또는 증상</option>
		</select>
		<input type="search" name="keyword"  value="${keyword}"><input type="submit" value="검색">
	</form>
	<hr size="1" width="80%">
<c:if test="${count  == 0}">
		질병 사전 내역이 없습니다.
</c:if>
<c:if test="${count > 0}">
		<table>
			<tr>
				<th>
				</th>
			</tr>
		
<c:forEach var="d" items="${list}">
		<tr>
			<td><a href="${pageContext.request.contextPath}/disease/diseaseDictDetail?sickcd=${d.sickcd}">${d.dis_name}</a></td>
		</tr>
		
</c:forEach>
		</table>
		<div class="align-center">
			${page}
		</div>
</c:if>
>>>>>>> branch 'develop' of https://github.com/Kimseungyeon98/MediChat.git
</div>