<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="page-main">
	<div class="page-one">
		<h5>홈 > 질병백과사전 > ${disease.dis_name}</h5>
		<p>
		<h2>&nbsp;&nbsp;&nbsp;<b>${disease.dis_name}</b></h2>
		조회수 :  ${disease.dis_hit}
		<br>
		<hr size="1" width="100%">
		<br>
		<p class="fs-18"><b>질병코드</b><br>
			<p>
			${disease.sickcd}
		</p>
		<p class="fs-18"><b>진료과</b><br>
			<p>
			${disease.dis_department}
		</p>
		<p class="fs-18" ><b>증상</b><br>
			<p>
			${fn:replace(fn:replace(disease.dis_symptoms, ".", ".<br>"),"▶","<br>▶")}
			
		</p>
	</div>
	<br>
</div>
