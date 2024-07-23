<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
.container {
    width: 80%;
    margin: 0 auto;
    padding-top: 20px;
    padding-bottom: 60px;
}
.reservation-container {
	background-color: #f9f9f9;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
	padding: 20px;
	position: relative; /* 수정: relative로 설정 */
	height: auto; /* height를 auto로 설정하여 내용에 따라 크기 조정 */
}
.reservation-header {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}
.reservation-header img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
}
.reservation-header h3 {
    margin: 0;
}
.reservation-body {
    margin-top: 10px;
}
.reservation-body p {
    margin: 5px 0;
}
.reservation-actions {
    position: absolute; /* 수정: 절대 위치로 설정 */
    bottom: 20px; /* 수정: 컨테이너의 하단에서 20px 위로 설정 */
    right: 20px; /* 수정: 컨테이너의 오른쪽에서 20px 왼쪽으로 설정 */
}
.reservation-actions button {
    padding: 5px 10px;
    border: 1px solid #000;
    background-color: #fff;
    cursor: pointer;
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
</style>
<h2>나의 예약 내역</h2>
<c:forEach var="reservation" items="${list}">
    <div class="reservation-container">
    <input type="hidden" id="hos_num" value="${reservation.hos_num}">
        <div class="reservation-header">
            <img src="/images/hos_icon.png">
            <h3>${reservation.hos_name}</h3>
        </div>
        <div class="reservation-body">
            <p><strong><c:choose>
                <c:when test="${reservation.res_type == 0}">비대면 진료</c:when>
                <c:otherwise>방문진료</c:otherwise>
            </c:choose></strong> - <c:choose>
                <c:when test="${reservation.res_status == 0}">예약 승인 대기</c:when>
                <c:when test="${reservation.res_status == 1}">예약 확정</c:when>
                <c:when test="${reservation.res_status == 2}">진료 완료</c:when>
                <c:otherwise>예약 취소</c:otherwise>
            </c:choose></p>
            <p>${reservation.res_date} ${reservation.res_time}</p>
            <p>${reservation.doc_name} 의사</p>
            <p>상세증상: ${reservation.res_content}</p>
        </div>
        <div class="reservation-actions">
            <c:choose>
                <c:when test="${reservation.res_status == 0 || reservation.res_status == 1}">
                    <input type="button" class="cancel-button" data-resnum="${reservation.res_num}" value="예약 취소">
                </c:when>
                <c:when test="${reservation.res_status == 2}">
                    <input type="button" class="review-button" data-resnum="${reservation.res_num}" data-hosnum="${reservation.hos_num}" onclick="location.href='/review/writeReview?res_num=' + this.getAttribute('data-resnum') + '&hos_num=' + this.getAttribute('data-hosnum')"  value="리뷰 쓰기">
                </c:when>
            </c:choose>
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
