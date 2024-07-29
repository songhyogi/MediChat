<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    position: relative;
    height: auto;
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
    position: absolute;
    bottom: 20px;
    right: 20px;
}
.reservation-actions button {
    padding: 5px 10px;
    border: 1px solid #000;
    background-color: #fff;
    cursor: pointer;
}
.reservation-actions .approve-button {
    color: white;
}
.reservation-actions .reject-button {
    color: white;
}
.reservation-actions .complete-button {
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
<div class="container">
	<h2>의사 예약 내역</h2>
	<c:forEach var="reservation" items="${list}">
	    <div class="reservation-container">
	        <div class="reservation-header">
	            <img src="/images/hos_icon.png">
	            <h3>${reservation.patient_name} 님</h3>
	        </div>
	        <div class="reservation-body">
	            <p><strong><c:choose>
	                <c:when test="${reservation.res_type == 0}">비대면 진료</c:when>
	                <c:otherwise>방문진료</c:otherwise>
	            </c:choose></strong> - <c:choose>
	                <c:when test="${reservation.res_status == 0}">예약 승인 대기</c:when>
	                <c:when test="${reservation.res_status == 1}">진료 예정</c:when>
	                <c:when test="${reservation.res_status == 2}">진료 완료</c:when>
	                <c:otherwise>예약 취소</c:otherwise>
	            </c:choose></p>
	            <p>등록일: ${reservation.res_reg}</p>
	            <p>진료일: ${reservation.res_date} ${reservation.res_time}</p>
	            <p>상세증상: ${reservation.res_content}</p>
	        </div>
	        <div class="reservation-actions">
	            <c:choose>
	                <c:when test="${reservation.res_status == 0}">
	                    <input type="button" class="approve-button" data-resnum="${reservation.res_num}" data-action="1" value="승인">
	                    <input type="button" class="reject-button" data-resnum="${reservation.res_num}" data-action="3" value="거절">
	                </c:when>
	                <c:when test="${reservation.res_status == 1}">
	                    <input type="button" class="complete-button" data-resnum="${reservation.res_num}" data-action="2" value="진료완료">
	                </c:when>
	            </c:choose>
	        </div>
	    </div>
	</c:forEach>
	<div class="pagination">
	    ${page}
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
    $(".approve-button, .reject-button, .complete-button").click(function() {
        var resNum = $(this).data("resnum");
        var action = $(this).data("action");
        var confirmationMessage = "";
        if (action == 1) {
            confirmationMessage = "예약을 승인하시겠습니까?";
        } else if (action == 2) {
            confirmationMessage = "진료를 완료하시겠습니까?";
        } else if (action == 3) {
            confirmationMessage = "예약을 거절하시겠습니까?";
        }
        if (confirm(confirmationMessage)) {
            $.ajax({
                type: 'post',
                url: '/reservation/updateReservation',
                data: {
                    res_num: resNum,
                    res_status: action
                },
                dataType: "json",
                success: function(param) {
                    if (param.result == "success") {
                        alert("처리가 완료되었습니다.");
                        location.reload();
                    } else if (param.result == "logout") {
                        alert("로그인이 필요합니다.");
                    } else {
                        alert("처리에 실패했습니다.");
                    }
                },
                error: function() {
                    alert("네트워크 오류가 발생했습니다.");
                }
            });
        }
    });
});
</script>