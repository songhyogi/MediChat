<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.find.js"></script>  
<div class="container">
    <h2 class="title">비밀번호 찾기</h2>
    <hr size="1" width="100%" noshade="noshade">
    <span class="fs-15">
		가입할 때 사용한 아이디와 이메일를 입력하시면 이메일로 임시비밀번호를 보내드립니다.<br>		
	</span>
    <form:form action="memberFindPassword" id="member_findPasswd" modelAttribute="memberVO">
        <ul>
            <li>
                <form:input path="mem_id" placeholder="아이디" style="margin-top:50px;"/>
            </li>
            <li>
                <form:input path="mem_email" placeholder="test@test.com 형식으로 입력"/>
            </li>
        </ul>
        <div>
            <form:button class="login_btn fw-7 fs-17">이메일 전송</form:button>
        </div>
        <div class="button-container">
            <input type="button" value="홈으로" style="margin-right:10px;">
            <input type="button" value="로그인">
        </div>
    </form:form>
</div>