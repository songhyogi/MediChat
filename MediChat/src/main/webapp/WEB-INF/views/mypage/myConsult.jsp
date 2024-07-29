<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="container">
	<h2 class="title">나의 의료상담 내역</h2>
	<hr size="1" width="80%" noshade="noshade">
	<div id="consulting-div">
		<c:forEach items="${consultList}" var="consult">
			<div class="consulting-item-div" data-conNum="${consult.con_num}">
				<div class="consulting-item-title">${consult.con_title}</div>
				<div class="consulting-item-content">${consult.con_content}</div>
				<div class="consulting-item-type"><span>${consult.con_type_name}</span></div>
				<div class="consulting-item-reCnt">
					<div>
						<div>${consult.con_rDate}</div>
					</div>
				</div>
			</div>
			<div class="line"></div>
		</c:forEach>
	</div>
</div>