<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/js/community.reply.js"></script>
<!-- 커뮤니티 글상세 시작 -->
<div class="page-main">
	<div class="cboard-detail-header">
		<p class="text-lightgray fw-7 fs-13" style="padding:16px;">홈 > 커뮤니티</p>
		<span class="cboard-category-box">
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
		<div class="cboard-title">${cboard.cbo_title}</div>
		<div class="cboard-info-wrapper">
			<div class="cboard-info" id="cboard-info">
		        <img src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${cboard.mem_num}" width="40" height="40">
		        <div class="cboard-profile">
		            <span>${cboard.mem_id}</span>
		            <span>${cboard.cbo_rdate}</span>
		        </div>
		    </div>
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
		</div>
		<br>
		<div>
			${cboard.cbo_content}
		</div>
		<div class="align-right">
			👁 ${cboard.cbo_hit}
			<!-- 좋아요 -->
			❤️ <span id="output_fcount"></span>
			<!-- 댓글수 -->
			<span id="output_rcount"></span>
		</div>
		</div>
	<hr><br>
	<!-- 댓글 -->
	<div class="align-center cboard-detail-reply">
		<span class="reply-title">댓글쓰기</span>
		<form id="comment_form">
			<input type="hidden" name="cbo_num" value="${cboard.cbo_num}" id="cbo_num">
			<textarea rows="7" cols="110" name="cre_content" id="cre_content" class="reply-textarea"
				<c:if test="${empty user}">disabled="disabled"</c:if>
				><c:if test="${empty user}">댓글을 작성하려면 로그인 해주세요.</c:if></textarea>
			<c:if test="${!empty user}">
				<div id="re_first"><span class="letter-count">0/300</span></div>
				<div id="re_second"><input type="submit" value="전송" class="reply-submit-btn"></div>
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