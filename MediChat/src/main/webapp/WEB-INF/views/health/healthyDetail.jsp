<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/healthy.total.js"></script>
<div class="page-main">
	<h4>홈 > 건강 블로그 > ${healthy.healthy_title}</h4> 		
	<h2>${healthy.healthy_title}</h2>
	${healthy.h_reg_date}  조회수 :  ${healthy.healthy_hit} <br>
	<c:if test="${!empty healthy.h_filename}">
	<img  width="120" height="120" src="${pageContext.request.contextPath}/upload/${healthy.h_filename}"/>
	</c:if>
	<div>
			<p>${healthy.healthy_content}</p>
	</div>
	
	<div title="Like" class="heart-container" id="hFav" data-num="${healthy.healthy_num}">
            <input id="Give-It-An-Id" class="checkbox" type="checkbox" <c:if test="${healthy.click_num == user.mem_num && !empty user}">checked</c:if>>
            <div class="svg-container">
                <svg xmlns="http://www.w3.org/2000/svg" class="svg-outline" viewBox="0 0 24 24">
                    <path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Zm-3.585,18.4a2.973,2.973,0,0,1-3.83,0C4.947,16.006,2,11.87,2,8.967a4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,11,8.967a1,1,0,0,0,2,0,4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,22,8.967C22,11.87,19.053,16.006,13.915,20.313Z">
                    </path>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" class="svg-filled" viewBox="0 0 24 24">
                    <path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Z">
                    </path>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" height="100" width="100" class="svg-celebrate">
                    <polygon points="10,10 20,20"></polygon>
                    <polygon points="10,50 20,50"></polygon>
                    <polygon points="20,80 30,70"></polygon>
                    <polygon points="90,10 80,20"></polygon>
                    <polygon points="90,50 80,50"></polygon>
                    <polygon points="80,80 70,70"></polygon>
                </svg>
            </div>
       </div>
        <span id="hfav_cnt">${healthy.fav_cnt}</span><br>
        
<c:if test="${user.mem_num == healthy.mem_num}">
	<input type="button" value="수정" onclick="location.href='healthUpdate?healthy_num=${healthy.healthy_num}'">
	<input type="button" value="삭제" id="h_delbtn" >
	<br>
	<script type="text/javascript">
		$(function(){
				$('#h_delbtn').click(function(){
					let choice  =confirm('삭제하시겠습니까?');
					if(choice){
						location.href='healthDelete?healthy_num=${healthy.healthy_num}';
					}
				})
			
			
		})
	</script>
</c:if>

<div>
	<c:if test="${empty user}">
		<form >
		<input type="hidden"   name ="healthy_num" value="${healthy.healthy_num}">
		<input type="hidden" name="hre_renum" value="0">
		<input type="hidden" name="hre_level" value="0">
		<textarea rows="5" cols="35" name="hre_content" style="resize:none;" disabled="disabled" >로그인 후 작성가능합니다.</textarea>
		</form>
	</c:if>
	<c:if test="${!empty user}">
		<form  id="hreWrite" >
		<input type="hidden" id="healthy_num"  name ="healthy_num" value="${healthy.healthy_num}">
		<input type="hidden" name="hre_renum" value="0">
		<input type="hidden" name="hre_level" value="0">
		<textarea rows="5" cols="35" name="hre_content" id="hre_content"  style="resize:none;" placeholder="300자까지 입력가능" ></textarea>
		<input type="submit" value="댓글쓰기">
		</form>
	</c:if>
	
</div>
<div id="replyList" data-num="${healthy.healthy_num}">

</div>
<p>
<p>
<input type="button" value="비대면 진료 받으러 가기">
</div>