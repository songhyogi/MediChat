<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
글상세
${consulting.con_title}
${consulting.con_num}

<button onclick="location.href='/consultings/modify/${consulting.con_num}'">글 수정</button>
<button onclick="location.href='/consultings/remove/${consulting.con_num}'">글 삭제</button>
수정과 삭제는 의사의 답변이 달리기 전까지만 가능합니다.

===========댓글 영역=============
<form action="/consultings/createReply" method="post">
	<input type="hidden" name="con_num" value="${consulting.con_num}">
	
	<input type="text" name="con_re_content">
	
	<input type="submit" value="전송">
</form>

<c:forEach items="${cReList}" var="cRe">
	${cRe.con_re_content}
</c:forEach>