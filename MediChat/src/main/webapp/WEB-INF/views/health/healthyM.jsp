<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="page-main">
	<div class="page-one" style="margin: auto;">
		<h5>홈 > 건강 블로그 > 건강매거진</h5>
		<p>

			<h1><img src="${pageContext.request.contextPath}/images/magazine.png"
				width="45px;"> <b>건강 매거진</b></h1>
		<br>
		<form action="healthBlog" method="get" id="form-health"
			class="align-center">
			<div class="container-input " style="width: 500px; margin: 0 auto;">
				<div class="d-flex justify-content-center align-items-center ">
					<select name="keyfield" class="form-control" id="selectinput">
						<option disabled="disabled"
							<c:if test="${empty keyfield}">selected</c:if>>선택</option>
						<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${keyfield ==2}">selected</c:if>>내용</option>
						<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목
							또는 내용</option>
						<option value="4" <c:if test="${keyfield ==4}">selected</c:if>>작성자</option>
					</select> &nbsp;&nbsp;<input type="text" id="h-search" class="form-control"
						placeholder="검색어를 입력하세요." name="keyword" value="${keyword}">
					<i id="h-search-icon" class="bi bi-search"></i>
					<script type="text/javascript">
						$('#h-search-icon').click(function() {
							$('#form-health').submit();
						});
					</script>
				</div>
			</div>
		</form>
		<hr size="1" width="100%">
		<br>
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button class="default-btn" type="button"
					onclick="location.href='${pageContext.request.contextPath}/health/healWrite'">글쓰기</button>
			</div>
		</c:if>
		<br>
		<br>
		<c:if test="${count  == 0}">
			건강 블로그 내역이 없습니다.
		</c:if>
		<c:if test="${count > 0}">

			<c:forEach var="h" items="${list}">
				<a href="${pageContext.request.contextPath}/health/healthDetail?healthy_num=${h.healthy_num}">
				<div class="healthy_list">
					<div class="float-left">
							<ul>
								<li style="font-size: 16pt;">${h.healthy_title}</li>
								<li>${fn:substring(h.healthy_content,0,fn:indexOf(h.healthy_content, '.')+1)}</li>
								<li class="fs-15 fw-5 text-black-3">${h.h_reg_date} 조회수:${h.healthy_hit} &nbsp; ♡ :
									${h.fav_cnt} &nbsp; <img src="../images/speech.png"
									width="15px;"> : ${h.re_cnt}
								</li>
							</ul>
					</div>
					<div class="healthy_image">
						<c:if test="${!empty h.h_filename}">
							<img width="120" style="border-radius: 10px;" height="120"
								src="${pageContext.request.contextPath}/upload/${h.h_filename}" />
						</c:if>
					</div>
				</div>
				</a>
				<div class="line"></div>
			</c:forEach>
			<br>
			<br>
			<br>
			<div class="align-center float-clear">${page}</div>
		</c:if>
		<br>
	</div>
</div>