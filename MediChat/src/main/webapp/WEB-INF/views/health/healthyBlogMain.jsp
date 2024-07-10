<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<h4>홈 > 건강 블로그</h4> 		
	<h2>건강 블로그</h2>
	<form action="${pageContext.request.contextPath}" method="get">
		<select name="keyfield">
			<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
			<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>내용</option>
			<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목 또는 내용</option>
			<option value="4"<c:if test="${keyfield ==4}">selected</c:if>>작성자</option>
		</select>
		<input type="search" name="keyword"  value="${keyword}"><input type="submit" value="검색">
	</form>
	<hr size="1" width="80%">
	<div class="align-right">
		<input type="button" value="글쓰기"  onclick="location.href='${pageContext.request.contextPath}/heath/healWrite'">
	</div>
<c:if test="${count  == 0}">
		건강 블로그 내역이 없습니다.
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
</div>