<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">
<style>
.reservation-container {
    width: 100%;
    margin: 0 auto;
    /* padding-top: 10px; */
    padding-bottom: 10px;
}
.reservation-header {
    display: flex;
    align-items: center;
    justify-content: space-between; /* 추가: 요소들을 양쪽 끝으로 정렬 */
    margin-bottom: 10px;
}
.reservation-header img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    margin-top: 8px;
}
.reservation-header h4 {
    margin: 0;
    flex-grow: 1; /* 추가: 남은 공간을 차지하도록 설정 */
}
.reservation-actions {
    margin-left: 10px; /* 추가: 병원 이름과 버튼 사이에 간격 추가 */
}
.cancel-button, .review-button {
    padding: 5px 10px;
    cursor: pointer;
    width: 90px;
    border-radius: 10px;
    border:none;
}
.cancel-button {
	background-color: #52b788;
    color: white; 
}
.review-button {
	background-color: #74c69d;
	color: white;
}
.reservation-body {
	/* background-color: #f9f9f9; */
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
	padding: 5px 20px 0px 20px;
	position: relative; /* 수정: relative로 설정 */
	height: 250px; /* height를 auto로 설정하여 내용에 따라 크기 조정 */
	margin-top: 10px;
}
.reservation-body p {
    margin: 5px 0;
}
.reservation-actions .review-button {
    color: white;
}
.reservation-actions .cancel-button {
    color: white;
}
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}
.pagination a {
    margin: 0 5px;
    text-decoration: none;
    color: #007bff;
}
.pagination a.active {
    font-weight: bold;
    color: #000;
}
.reservation-header img {
    width: 38px; /* 이 값이 우선 적용됨 */
    height: 38px;
    margin-right: 10px;
}
.reservation-header h4{
	margin-top:8px;
}
.detail-info {
    display: flex;
    padding: 10px 0;
    justify-content: space-between; /*제거 */
    border-bottom: 1px solid #eee;
}
.detail-item:last-child {
    border-bottom: none;
}
.detail-item label {
    font-weight: bold;
    color: #333;
    width: 25%;
}
.detail-info span {
    font-size: 16px;
    width: 75%;
}

</style>
<!-- <p>
<h4>[나의 예약 내역]</h4>
<p> -->
<c:forEach var="reservation" items="${list}">
<div class="reservation-container">
    <input type="hidden" id="hos_num" value="${reservation.hos_num}">
    <div class="reservation-header">
        <img src="/images/hos_icon.png">
        <h4><a href="/hospitals/search/detail/${reservation.hos_num}">${reservation.hos_name}</a></h4>
        <div class="reservation-actions">
            <c:choose>
                <c:when test="${reservation.res_status == 0 || reservation.res_status == 1}">
                    <input type="button" class="cancel-button" data-resnum="${reservation.res_num}" value="예약 취소">
                </c:when>
                <c:when test="${reservation.res_status == 2}">
                    <input type="button" class="review-button" onclick="location.href='/review/writeReview?res_num=${reservation.res_num}&hos_num=${reservation.hos_num}'"  value="리뷰 쓰기">
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="reservation-body">
        <div class="detail-info">
            <label>비대면/진료 | </label>
            <span>
                <c:choose>
                    <c:when test="${reservation.res_type == 0}">비대면 진료</c:when>
                    <c:otherwise>방문진료</c:otherwise>
                </c:choose>
            </span>
        </div>
        <div class="detail-info">
            <label>예약 상태 | </label>
            <span>
                <c:choose>
                    <c:when test="${reservation.res_status == 0}">예약 승인 대기</c:when>
                    <c:when test="${reservation.res_status == 1}">예약 확정</c:when>
                    <c:when test="${reservation.res_status == 2}">진료 완료</c:when>
                    <c:otherwise>예약 취소</c:otherwise>
                </c:choose>
            </span>
        </div>
        <div class="detail-info">
            <label>진료일 | </label>
            <span>${reservation.res_date} ${reservation.res_time}</span>
        </div>
        <div class="detail-info">
            <label>담당 의사 | </label>
            <span>${reservation.doc_name} 의사</span>
        </div>
        <div class="detail-info">
            <label>상세증상 | </label>
            <span>${reservation.res_content}</span>
        </div>
    </div>
</div>
</c:forEach>
<div class="pagination">
    ${page}
</div>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
	$(".cancel-button").click(function() {
		var resNum = $(this).data("resnum");
        if (confirm("예약을 취소하시겠습니까?")) {
            $.ajax({
            	type:'post',
            	url:'/reservation/cancelReservation',
            	data:{res_num:resNum},
            	dataType: "json",
            	success: function(param){
            		if(param.result == 'logout'){
            			alert('로그인 후 이용하세요');
            		}else if(param.result == 'success'){
            			alert('예약이 취소되었습니다.');
            			location.reload();
            		}else{
            			alert('예약취소에 실패했습니다.');
            		}
            	},
            	error:function(){
            		alert('에러가 발생했습니다.');
            	}
            });
         }
    });
});
</script>
