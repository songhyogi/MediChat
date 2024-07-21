<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kyj.css" type="text/css">
<!-- 파일 목록 시작 -->
<div class="chat-main col-9">
	<div class="chat-header bg-green-7" id="file_header">
		<!-- 예약내역을 클릭한 경우에만 예약번호와 진료일시, 의사 표시 -->
	</div>
	<div class="chat-body">
		<c:forEach var="list" items="${list}">
			<table>
				<tr class="list-head bg-gray-3">
					<th>진료의사</th>
					<th>서류유형</th>
					<th>발급일자</th>
					<th>유효일자</th>
				</tr>
				<tr>
					<td>${list.doc_num}</td>
					<c:if test="${list.file_type==0}"><td>처방전</td></c:if>
					<c:if test="${list.file_type==1}"><td>소견서</td></c:if>
					<c:if test="${list.file_type==2}"><td>진단서</td></c:if>
					<c:if test="${list.file_type==3}"><td>진료비 세부내역서</td></c:if>
					<td>${list.file_reg_date}</td>
					<td>${list.file_valid_date}</td>
				</tr>
			</table>
			<button type="button" class="btn-green" onclick="/chat/downloadFile?file_num=${list.file_num}">
				다운로드
			</button>
		</c:forEach>
	</div>
</div>
<!-- 파일 목록 끝 -->