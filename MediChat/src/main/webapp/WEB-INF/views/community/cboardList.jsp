<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 커뮤니티 목록 시작 -->
<div class="page-main">	
	<p class="text-lightgray fw-7 fs-13">홈 > 커뮤니티</p>
	<h4>커뮤니티</h4>
	<div class="page-body">
		<div>
			<a href="list">전체</a>
			<a href="list?cbo_type=1">질환고민</a>
			<a href="list?cbo_type=2">다이어트·헬스</a>
			<a href="list?cbo_type=3">피부고민</a>
			<a href="list?cbo_type=4">임신·성고민</a>
			<a href="list?cbo_type=5">탈모고민</a>
			<a href="list?cbo_type=6">마음건강</a>
			<a href="list?cbo_type=7">뼈와관절</a>
			<a href="list?cbo_type=8">영앙제</a>
			<a href="list?cbo_type=9">자유게시판</a>
		</div>
		<form action="list" method="get">
			<input type="hidden" name="category" value="${param.category}">
			<ul class="search">
				<li>
					<input type="search" name="keyword" id="keyword" value="${param.category}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
			<div>
				<select id="order" name="order">
					<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
					<option value="2" <c:if test="${param.order == 1}">selected</c:if>>조회수순</option>
					<option value="3" <c:if test="${param.order == 1}">selected</c:if>>공감순</option>
					<option value="4" <c:if test="${param.order == 1}">selected</c:if>>댓글순</option>
				</select>
			</div>
		</form>
		<c:if test="${!empty user}">
			<input type="button" value="글쓰기" onclick="location.href='write'"><br>
		</c:if>
		<c:if test="${count == 0}">
			<div>표시할 게시물이 없습니다</div>
		</c:if>
		<c:if test="${count > 0}">
			<c:forEach var="cboard" items="${list}">
				<div class="cboard-list" onclick="location.href='detail?cbo_num=${cboard.cbo_num}'" style="cursor: pointer;">
					<span>
						<c:if test="${cboard.cbo_type == 1}">질환고민</c:if>
						<c:if test="${cboard.cbo_type == 2}">다이어트·헬스</c:if>
						<c:if test="${cboard.cbo_type == 3}">피부고민</c:if>
						<c:if test="${cboard.cbo_type == 4}">임신·성고민</c:if>
						<c:if test="${cboard.cbo_type == 5}">탈모고민</c:if>
						<c:if test="${cboard.cbo_type == 6}">마음건강</c:if>
						<c:if test="${cboard.cbo_type == 7}">뼈와관절</c:if>
						<c:if test="${cboard.cbo_type == 8}">영앙제</c:if>
						<c:if test="${cboard.cbo_type == 9}">자유게시판</c:if>
					</span>
					<h5>${cboard.cbo_title}</h5>
					<div>
						${cboard.cbo_content}
					</div>
					<ul>
						<li><!-- 프로필 사진 -->
						<img src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${cboard.mem_num}" width="40" height="40">	
						</li>
						<li>${cboard.mem_id}</li>
						<li>${cboard.cbo_rdate}</li>
						<li>${cboard.cbo_hit}</li>
						<li>${cboard.fav_cnt}</li>
					</ul>
					<hr>
				</div>
			</c:forEach>
			<div>${page}</div>
		</c:if>
	</div>
</div>
<!-- 커뮤니티 목록 끝 -->