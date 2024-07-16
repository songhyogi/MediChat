<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- 비대면 진료 신청 시작 -->
<div>
	<h2>비대면 진료 신청</h2>
	<hr size="1" width="80%" noshade="noshade">
	<h3>정보입력</h3>
	<span>신청에 필요한 정보를 입력합니다.</span>
	<form:form action="registerTreat" id="register_treat"
		modelAttribute="doctorVO">
		<ul>
			 <li>
                <form:label path="mem_name">이름</form:label> 
                <form:input path="mem_name" value="${doctor.mem_name}" readonly="true" /> 
                <form:errors path="mem_name" cssClass="error-color" />
            </li>
            <li>
                <form:label path="doc_email">이메일</form:label> 
                <form:input path="doc_email" value="${doctor.doc_email}" readonly="true" /> 
                <form:errors path="doc_email" cssClass="error-color" />
            </li>
            <li>
				<!-- 휴무일 입력 부분 --> 
				<label for="doc_off">휴무일</label>
				<div>
					<form:checkbox path="doc_off" id="doc_off_0" value="0" />
					<label for="doc_off_0">월</label>
					<form:checkbox path="doc_off" id="doc_off_1" value="1" />
					<label for="doc_off_1">화</label>
					<form:checkbox path="doc_off" id="doc_off_2" value="2" />
					<label for="doc_off_2">수</label>
					<form:checkbox path="doc_off" id="doc_off_3" value="3" />
					<label for="doc_off_3">목</label>
					<form:checkbox path="doc_off" id="doc_off_4" value="4" />
					<label for="doc_off_4">금</label>
					<form:checkbox path="doc_off" id="doc_off_5" value="5" />
					<label for="doc_off_5">토</label>
					<form:checkbox path="doc_off" id="doc_off_6" value="6" />
					<label for="doc_off_6">일</label>
				</div> 
				<form:errors path="doc_off" cssClass="error-color" />
			</li>
			<li><form:label path="doc_stime">업무시간</form:label>
				<div>
					<form:select path="doc_stime">
						<% for (int hour = 0; hour <= 23; hour++) { %>
						<% for (int minute = 0; minute <= 45; minute += 15) { %>
						<form:option
							value='<%= String.format("%02d%02d", hour, minute) %>'><%= String.format("%02d%02d", hour, minute) %></form:option>
						<% } %>
						<% } %>
					</form:select>
					부터
					<form:select path="doc_etime">
						<% for (int hour = 0; hour <= 23; hour++) { %>
						<% for (int minute = 0; minute <= 45; minute += 15) { %>
						<form:option
							value='<%= String.format("%02d%02d", hour, minute) %>'><%= String.format("%02d%02d", hour, minute) %></form:option>
						<% } %>
						<% } %>
					</form:select>
					까지
				</div> 
				<form:errors path="doc_time" cssClass="error-color" /></li>
			<li>
                <form:label path="doc_passwd">비밀번호</form:label> 
                <form:password path="doc_passwd" id="doc_passwd" />
                <form:errors path="doc_passwd" cssClass="error-color" />
            </li>
            <li>
            	<input type="submit" value="신청" onclick="location.href='${pageContext.request.contextPath}/main/main'">
            </li>
		</ul>
	</form:form>
</div>
<!-- 비대면 진료 신청 끝 -->
