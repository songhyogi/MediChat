<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Reservations</title>
    <style>
        .reservation-list {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
        }
        .reservation-list th, .reservation-list td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .reservation-list th {
            background-color: #f2f2f2;
        }
        .pagination {
            text-align: center;
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
</head>
<body>
    <h2>My Reservations</h2>
    <table class="reservation-list">
        <thead>
            <tr>
                <th>Reservation Number</th>
                <th>Doctor Name</th>
                <th>Status</th>
                <th>Type</th>
                <th>Date</th>
                <th>Time</th>
                <th>Details</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reservation" items="${list}">
                <tr>
                    <td>${reservation.res_num}</td>
                    <td>${reservation.doc_name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${reservation.res_status == 0}">Pending</c:when>
                            <c:when test="${reservation.res_status == 1}">Scheduled</c:when>
                            <c:when test="${reservation.res_status == 2}">Completed</c:when>
                            <c:otherwise>Cancelled</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${reservation.res_type == 0}">Online</c:when>
                            <c:otherwise>Offline</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${reservation.res_date}</td>
                    <td>${reservation.res_time}</td>
                    <td>${reservation.res_content}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="pagination">
        <c:forEach var="i" begin="1" end="${page.totalPage}">
            <a href="myResList?pageNum=${i}" class="${page.currentPage == i ? 'active' : ''}">${i}</a>
        </c:forEach>
    </div>
</body>
</html>
