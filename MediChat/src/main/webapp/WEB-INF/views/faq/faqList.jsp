<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="page-main">
<div class="page-one">
		<h5>홈 > 자주 묻는 질문(FAQ) </h5> 
		<p>		
		<h2>&nbsp;&nbsp;&nbsp;<b>자주 묻는 질문(FAQ)</b></h2>
		<br>
		<form action="faqList" method="get" class="align-center">
			<div class="container-input">
			<select name="keyfield" id="selectinput">
				<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
				<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>내용</option>
				<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목 또는 내용</option>
			</select>
			<input type="text" name="keyword" placeholder="Search" name="text" class="input">
				<button style="height:35px;'" class="button font" type="submit">검색</button>
			</div>
		</form>
		<hr size="1" width="100%">
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button  class="default-btn" type="button"  onclick="location.href='${pageContext.request.contextPath}/faq/faqWrite'">글쓰기</button>
			</div>
		</c:if>
	<c:if test="${count  == 0}">
			자주 묻는 질문(FAQ) 내역이 없습니다.
	</c:if>
	<c:if test="${count > 0}">
		<c:forEach var="f" items="${list}">
	
					<a href="${pageContext.request.contextPath}/faq/faqDetail?faq_num=${f.faq_num}">
							<ul style="margin:0 auto; float:left; width:80%">
								<li style="font-size:16pt;">${f.faq_title}</li>
								<li> ${f.f_reg_date} 조회수 : ${f.faq_hit} &nbsp;   </li>
							</ul>
						</a>
				<div>
					 <input id="checkbox" type="checkbox">
				    <label class="toggle" for="checkbox">
				        <div id="bar1" class="bars"></div>
				        <div id="bar2" class="bars"></div>
				        <div id="bar3" class="bars"></div>
				    </label>
				</div>
		</c:forEach>
		<div class="align-center float-clear">
			${page}
		</div>
	</c:if>
	</div>
</div>