<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<div class="page-one">
		<h4>홈 > 자주 묻는 질문(FAQ) > ${faq.faq_title}</h4> 		
		<h2>&nbsp;&nbsp;&nbsp;${faq.faq_title}</h2>
		<br>
		${faq.f_reg_date} &nbsp; 조회수 :  ${faq.faq_hit} <br>
		<div class="line"></div>
		<br><br>
		
		
				${faq.faq_content}

	
		
     <br><br><br><br>
<c:if test="${user.mem_num == faq.mem_num}">
<div class="align-center">
	<input type="button" class="default-btn" value="글 수정" onclick="location.href='faqUpdate?faq_num=${faq.faq_num}'">
	<input type="button" class="default-btn" value="글 삭제" id="f_delbtn" >
</div>
	<br>
	<script type="text/javascript">
		$(function(){
				$('#f_delbtn').click(function(){
					let choice  =confirm('삭제하시겠습니까?');
					if(choice){
						location.href='faqDelete?faq_num=${faq.faq_num}';
					}
			});
		});
	</script>
</c:if>
		</div>
		<div class="align-center">
			<br><br><br>
			<input type="button" class="default-btn" onclick="location.href='${pageContext.request.contextPath}/hospitals'" style="width:50% !important; height:50px; font-size:15pt;" value="비대면 진료 받으러 가기">
		</div>
	</div>
