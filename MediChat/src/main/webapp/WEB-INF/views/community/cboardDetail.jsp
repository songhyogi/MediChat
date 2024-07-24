<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/js/community.reply.js"></script>
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
			<li>${cboard.mem_id}</li>
			<li>${cboard.cbo_rdate}</li>
		</ul>
		<c:if test="${!empty user && user.mem_num == cboard.mem_num}" >
			<div class="dropdown">	
			<img src="${pageContext.request.contextPath}/images/dots.png" width="20" id="dropdownToggle">
			<ul class="dropdown-menu" id="dropdownMenu">
				<li><a class="dropdown-btn" href="update?cbo_num=${cboard.cbo_num}">수정</a></li>
				<li><hr></li>
				<li><a class="dropdown-btn" href="#" id="delete_btn">삭제</a>
					<script>
						const dropdownToggle = document.getElementById('dropdownToggle');
	                    const dropdownMenu = document.getElementById('dropdownMenu');
	
	                    dropdownToggle.onclick = function() {
	                        dropdownMenu.classList.toggle('show');
	                    };
	
	                    window.onclick = function(event) {
	                        if (!event.target.matches('#dropdownToggle')) {
	                            if (dropdownMenu.classList.contains('show')) {
	                                dropdownMenu.classList.remove('show');
	                            }
	                        }
	                    };
                    
						const delete_btn = document.getElementById('delete_btn');
						delete_btn.onclick=function(){
							const choice = confirm('삭제하시겠습니까?');
							if(choice){
								location.replace('delete?cbo_num=${cboard.cbo_num}');
							}
						};
					</script>		
				</li>
			</ul>
			</div>
		</c:if>
		<div>
			${cboard.cbo_content}
		</div>
		<!-- 좋아요 -->
		<!-- 댓글수 -->
		<hr>
	</div>
	<!-- 댓글 -->
	<div>
		<span>댓글쓰기</span>
		<form id="comment_form">
			<input type="hidden" name="cbo_num" value="${cboard.cbo_num}" id="cbo_num">
			<textarea rows="7" cols="110" name="cre_content" id="cre_content"
				<c:if test="${empty user}">disabled="disabled"</c:if>
				><c:if test="${empty user}">댓글을 작성하려면 로그인 해주세요.</c:if></textarea>
			<c:if test="${!empty user}">
				<div id="re_first"><span class="letter-count">0/300</span></div>
				<div id="re_second"><input type="submit" value="전송"></div>
			</c:if>
		</form>
	</div>
	<!-- 댓글목록 -->
	<div id="output"></div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="더보기">
	</div>
</div>
<!-- 커뮤니티 글상세 끝 -->