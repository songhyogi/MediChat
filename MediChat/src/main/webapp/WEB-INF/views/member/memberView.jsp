<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.0.0/index.global.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
<style>
/* @import url('https://fonts.googleapis.com/icon?family=Material+Icons'); */
.memberInfo {
    display:flex;
    margin-top:40px;
}

.leftInfo, .rightInfo {
    flex:1;
    padding:0 10px;
}

.leftInfo li, .rightInfo li {
    list-style-type:none;
    margin-bottom:10px;
}

.leftInfo li:first-child, .rightInfo li:first-child {
    font-size:25px;
    font-weight:bold;
}

.leftInfo li:not(:first-child), .rightInfo li:not(:first-child) {
    font-size:15px;
}

.collapsible {
    cursor:pointer;
    user-select:none;
    background-color:#f0f0f0;
    padding:10px;
    border:none;
    text-align:left;
    outline:none;
    width:100%;
}

.collapsible:hover {
    background-color:#ccc;
}

.content {
    display:none;
    padding:0 18px;
    overflow:hidden;
    background-color:#f9f9f9;
}

@media (max-width: 768px) {
    .memberInfo {
        flex-direction:column;
    }
}

.dropdown{
  position :relative;
  display :inline-block;
  float:right;
}

/* .dropbtn_icon{
  font-family : 'Material Icons';
} */
.dropbtn{
  border:none;
  border-radius:4px;
  background-color:rgba(249, 247, 242, 0.7);
  font-weight:400;
  color:rgb(37, 37, 37);
  width:100px;
  text-align:left;
  cursor:pointer;
  font-size:15px;
}
.dropdown-content{
  display:none;
  position:absolute;
  z-index:1; /*다른 요소들보다 앞에 배치*/
  font-weight:400;
  background-color:#fff;
  min-width:100px;
}

.dropdown-content a{
  display:block;
  text-decoration:none;
  color:rgb(37, 37, 37);
  font-size:12px;
  padding:12px
}

.dropdown-content a:hover{
  background-color:#ececec
}

.dropdown:hover .dropdown-content {
  display:block;
}
#calendar{
	padding:30px;
	width:650px;
}
</style>
</head>
<body>
<%-- <div class="dropdown">
  <button class="dropbtn"> 
    <span class="dropbtn_icon"></span>
      나의 활동
  </button>
    <div class="dropdown-content">
      <a class="dropdown-item" href="#">좋아요</a>
      <a class="dropdown-item" href="${pageConetext.request.contextPath}/myPage/comments">댓글</a>
    </div>
  </div>
  <div class="dropdown">
    <button class="dropbtn"> 
      <span class="dropbtn_icon"></span>
      이용내역
    </button>
    <div class="dropdown-content">
      <a class="dropdown-item" href="#">진료기록</a>
      <a class="dropdown-item" href="${pageContext.request.contextPath}/reservation/myResList">예약내역</a>
      <a class="dropdown-item" href="${pageContext.request.contextPath}/mypage/myConsult">의료상담</a>
    </div>
</div> --%>
    <div class="memberInfo">
        <div class="leftInfo">
            <ul>
                <li style="font-size:17px; font-weight:bold;">이름</li>
                <li style="font-size:13px;">${member.mem_name}</li>
                <li style="font-size:17px; font-weight:bold;">전화번호</li>
                <li style="font-size:13px;">${member.mem_phone}</li>
                <li style="font-size:17px; font-weight:bold;">생년월일</li>
                <li style="font-size:13px;">${member.mem_birth}</li>
                <li style="font-size:17px; font-weight:bold;">이메일</li>
                <li style="font-size:13px;">${member.mem_email}</li>
            </ul>
        </div>
        <div class="rightInfo">
            <ul>
                <li style="font-size:17px; font-weight:bold;">우편번호</li>
                <li style="font-size:13px;">${member.mem_zipcode}</li>
                <li style="font-size:17px; font-weight:bold;">주소</li>
                <li style="font-size:13px;">${member.mem_address1} ${member.mem_address2}</li>
                <li style="font-size:17px; font-weight:bold;">가입일</li>
                <li style="font-size:13px;">${member.mem_reg}</li>
                <c:if test="${!empty member.mem_modify}">
                    <li style="font-size:17px; font-weight:bold;">정보 수정일</li>
                    <li style="font-size:13px;">${member.mem_modify}</li>
                </c:if>
            </ul>
        </div>
    </div>
    <!-- FullCalendar -->
    <div class="container mt-4">
        <div id="calendar"></div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // 달력을 월 단위로 시작
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                },
                editable: true,
                selectable: true,
                events: [
                    // 여기에 이벤트를 추가할 수 있습니다
                    // 예: { title: '이벤트 제목', start: '2024-07-30', end: '2024-07-31' }
                ]
            });

            calendar.render();
        });
    </script>
</body>
</html>
