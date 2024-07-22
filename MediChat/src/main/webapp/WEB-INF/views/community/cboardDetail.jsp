<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 커뮤니티 글상세 시작 -->
<div class="page-main">
	<div class="body-header">
		<p class="text-lightgray fw-7 fs-13">홈 > 커뮤니티</p>
		<div class="category">
			<c:if test="${cboard.cbo_type == 1}">질환고민</c:if>
			<c:if test="${cboard.cbo_type == 2}">다이어트·헬스</c:if>
			<c:if test="${cboard.cbo_type == 3}">피부고민</c:if>
			<c:if test="${cboard.cbo_type == 4}">임신·성고민</c:if>
			<c:if test="${cboard.cbo_type == 5}">탈모고민</c:if>
			<c:if test="${cboard.cbo_type == 6}">마음건강</c:if>
			<c:if test="${cboard.cbo_type == 7}">뼈와관절</c:if>
			<c:if test="${cboard.cbo_type == 8}">영앙제</c:if>
			<c:if test="${cboard.cbo_type == 9}">자유게시판</c:if>
		</div>	
		<span><b>${cboard.cbo_title}</b></span>
		<ul>
			<li>
				<img src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${cboard.mem_num}" width="40" height="40">
			</li>
			<li>
				${cboard.mem_id}
				${cboard.cbo_rdate}
				${cboard.cbo_hit}
			</li>
		</ul>
		<div>
			${cboard.cbo_content}
		</div>
		<div>
			<c:if test="${!empty user && user.mem_num == cboard.mem_num}">
				<input type="button" value="수정" onclick="location.href='update?cbo_num=${cboard.cbo_num}'">
				<input type="button" value="삭제" id="delete_btn">
			<script>
				const delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					const choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('delete?cbo_num=${cboard.cbo_num}');
					}
				};
			</script>
			</c:if>
		</div>
	</div>
</div>
<!-- 커뮤니티 글상세 끝 -->