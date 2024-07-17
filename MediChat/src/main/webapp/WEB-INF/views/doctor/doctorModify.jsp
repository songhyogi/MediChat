<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<!-- 의사회원정보 수정 시작 -->
<div class="container">
	<h2 class="title">회원정보수정(의사)</h2>
	<hr size="1" width="100%" noshade="noshade">
	<span style="font-weight:bold;">정보입력</span>
	<br>
	<span class="title2">회원정보수정에 필요한 정보를 입력합니다.</span>
	<form:form action="modifyUser" id="doctor_modify" modelAttribute="doctorVO">
		<form:hidden path="doc_num"/>
		<div class="form-main">
		<ul>
			<li style="margin-top:20px;">
				<form:label path="mem_name">이름</form:label>
				<form:input path="mem_name"/>
				<form:errors path="mem_name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_email">이메일</form:label>
				<form:input path="doc_email"/>
				<form:errors path="doc_email" cssClass="error-color"/>
			</li>
			<li>
				<!-- 병원 목록 검색 -->
				<form:label path="hos_num">병원</form:label>
				<form:hidden path="hos_num"/>
				<input type="search" name="keyword" id="keyword" value="${keyword}">
                <input type="button" id="search_button" value="검색">
                <form:errors path="hos_num" cssClass="error-color"/>
            </li>
            <li style="margin-left:205px;">
            	<form:select path="hos_num">
              	 	<c:forEach var="hos" items="${hosList}">
            			<option value="${hos.hos_num}">${hos.hos_name}/${hos.hos_addr}</option>
            		</c:forEach>
                </form:select>
            </li>
            <li>
				<form:label path="doc_history">연혁</form:label>
				<form:textarea path="doc_history" placeholder="연혁을 입력해주세요." style="height: 150px; vertical-align: top; margin-bottom:15px;"/>
			</li>
		</ul>
		</div>
		<hr size="1" width="100%" noshade="noshade">
		<div style="text-align:right;">
			<input type="button" value="MY페이지" id="reload_btn" onclick="location.href='docPage'">
			<form:button class="default-btn">수정</form:button>
		</div>
	</form:form>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    $('#search_button').on('click', function() {
		        var keyword = $('#keyword').val();
		
		        $.ajax({
		            url:'${pageContext.request.contextPath}/doctor/hosList',
		            type:'post',
		            dataType:'json',
		            data:{keyword:keyword},
		            success:function(data){
		                if(data.success){
		                   var hosList = data.hosList;
		                   var options = '';
		                   // 기존 옵션 초기화
		                    $('form').find('select[name="hos_num"]').empty();
	
		                   $.each(hosList, function(index, hospital) {
		                       options += '<option value="' + hospital.hos_num + '">' 
		                                + hospital.hos_name + ' / ' + hospital.hos_addr + '</option>';
		                   });
		                   $('form').find('select[name="hos_num"]').append(options);
		                }else{
		                   alert('병원 목록을 가져오는 중에 오류가 발생하였습니다.');
		                }
		            },
		            error:function(){
		               alert('서버 통신 중 오류가 발생하였습니다.');
		            }
		        });
		    });
		 	// select 태그 값 변경 시
		    $('form').on('change','select[name="hos_num"]',function() {
		        var selectedHosNum = $(this).val();
		        $('form').find('input[name="hos_num"]').val(selectedHosNum); // 숨겨진 input 태그에 선택한 값 할당
		    });
		});
	</script>
</div>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<!-- 의사회원정보 수정 시작 -->
<div>
	<h2>회원정보수정(의사)</h2>
	<hr size="1" width="100%" noshade="noshade">
	<span style="font-weight:bold;">정보입력</span>
	<br>
	<span class="title2">회원정보수정에 필요한 정보를 입력합니다.</span>
	<form:form action="modifyUser" id="doctor_modify" modelAttribute="doctorVO">
		<form:hidden path="doc_num"/>
		<ul>
			<li>
				<form:label path="mem_name">이름</form:label>
				<form:input path="mem_name"/>
				<form:errors path="mem_name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_email">이메일</form:label>
				<form:input path="doc_email"/>
				<form:errors path="doc_email" cssClass="error-color"/>
			</li>
			<li>
				<!-- 병원 목록 검색 -->
				<form:label path="hos_num">병원</form:label>
				<form:hidden path="hos_num"/>
				<input type="search" name="keyword" id="keyword" value="${keyword}">
                <input type="button" id="search_button" value="검색">
                <form:errors path="hos_num" cssClass="error-color"/>
            </li>
            <li style="margin-left:205px;">
            	<form:select path="hos_num">
              	 	<c:forEach var="hos" items="${hosList}">
            			<option value="${hos.hos_num}">${hos.hos_name}/${hos.hos_addr}</option>
            		</c:forEach>
                </form:select>
            </li>
            <li>
				<form:label path="doc_history">연혁</form:label>
				<form:textarea path="doc_history" placeholder="연혁을 입력해주세요." style="height: 150px; vertical-align: top;"/>
			</li>
		</ul>
		<div>
			<input type="button" value="MY페이지" class="default-btn" onclick="location.href='docPage'">
			<form:button class="default-btn">수정</form:button>
		</div>
	</form:form>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    $('#search_button').on('click', function() {
		        var keyword = $('#keyword').val();
		
		        $.ajax({
		            url:'${pageContext.request.contextPath}/doctor/hosList',
		            type:'post',
		            dataType:'json',
		            data:{keyword:keyword},
		            success:function(data){
		                if(data.success){
		                   var hosList = data.hosList;
		                   var options = '';
		                   // 기존 옵션 초기화
		                    $('form').find('select[name="hos_num"]').empty();
	
		                   $.each(hosList, function(index, hospital) {
		                       options += '<option value="' + hospital.hos_num + '">' 
		                                + hospital.hos_name + ' / ' + hospital.hos_addr + '</option>';
		                   });
		                   $('form').find('select[name="hos_num"]').append(options);
		                }else{
		                   alert('병원 목록을 가져오는 중에 오류가 발생하였습니다.');
		                }
		            },
		            error:function(){
		               alert('서버 통신 중 오류가 발생하였습니다.');
		            }
		        });
		    });
		 	// select 태그 값 변경 시
		    $('form').on('change','select[name="hos_num"]',function() {
		        var selectedHosNum = $(this).val();
		        $('form').find('input[name="hos_num"]').val(selectedHosNum); // 숨겨진 input 태그에 선택한 값 할당
		    });
		});
	</script>
</div>
>>>>>>> refs/remotes/origin/kcy-feature
<!-- 회원정보 수정 끝 -->