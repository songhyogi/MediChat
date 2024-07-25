<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<div class="page-one">
		<h4>홈 > 건강 블로그 > 건강 비디오 > ${video.video_title}</h4> 		
		<h2>&nbsp;&nbsp;&nbsp;${video.video_title}</h2>
		${video.v_reg_date}  조회수 :  ${video.video_hit} <br>
		<br>
		<div class="line"></div>
		<br><br>
				${video.video_content}

	
		
        &nbsp; &nbsp; &nbsp;<span id="hfav_cnt"></span><br>

<c:if test="${user.mem_num == video.mem_num}">
<div class="align-center">
	<input type="button" class="default-btn" value="글 수정" onclick="location.href='videoUpdate?video_num=${video.video_num}'">
	<input type="button" class="default-btn" value="글 삭제" id="v_delbtn" >
</div>
	<br>
	<script type="text/javascript">
		$(function(){
				$('#v_delbtn').click(function(){
					let choice  =confirm('삭제하시겠습니까?');
					if(choice){
						location.href='videoDelete?video_num=${video.video_num}';
					}
			});
		});
	</script>
</c:if>
		<div class="line"></div>
		</div>
		
		<br><br>
		<div class="align-center">
			<br><br><br>
			<input type="button" class="default-btn" value="비대면 진료 받으러 가기">
		</div>
	</div>
