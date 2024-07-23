<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
<style>
.memberInfo {
    display: flex;
    margin-top: 70px;
}

.leftInfo, .rightInfo {
    flex: 1;
    padding: 0 10px;
}

.leftInfo li, .rightInfo li {
    list-style-type: none;
    margin-bottom: 10px;
}

.leftInfo li:first-child, .rightInfo li:first-child {
    font-size: 25px;
    font-weight: bold;
}

.leftInfo li:not(:first-child), .rightInfo li:not(:first-child) {
    font-size: 15px;
}

.collapsible {
    cursor: pointer;
    user-select: none;
    background-color: #f0f0f0;
    padding: 10px;
    border: none;
    text-align: left;
    outline: none;
    width: 100%;
}

.collapsible:hover {
    background-color: #ccc;
}

.content {
    display: none;
    padding: 0 18px;
    overflow: hidden;
    background-color: #f9f9f9;
}

@media (max-width: 768px) {
    .memberInfo {
        flex-direction: column;
    }
}
</style>
</head>
<body>
    <div class="memberInfo">
        <div class="leftInfo">
            <ul>
                <li style="font-size:25px; font-weight:bold;">이름</li>
                <li style="font-size:15px;">${member.mem_name}</li>
                <li style="font-size:25px; font-weight:bold;">전화번호</li>
                <li style="font-size:15px;">${member.mem_phone}</li>
                <li style="font-size:25px; font-weight:bold;">생년월일</li>
                <li style="font-size:15px;">${member.mem_birth}</li>
                <li style="font-size:25px; font-weight:bold;">이메일</li>
                <li style="font-size:15px;">${member.mem_email}</li>
            </ul>
        </div>
        <div class="rightInfo">
            <ul>
                <li style="font-size:25px; font-weight:bold;">우편번호</li>
                <li style="font-size:15px;">${member.mem_zipcode}</li>
                <li style="font-size:25px; font-weight:bold;">주소</li>
                <li style="font-size:15px;">${member.mem_address1} ${member.mem_address2}</li>
                <li style="font-size:25px; font-weight:bold;">가입일</li>
                <li style="font-size:15px;">${member.mem_reg}</li>
                <c:if test="${!empty member.mem_modify}">
                    <li style="font-size:25px; font-weight:bold;">정보 수정일</li>
                    <li style="font-size:15px;">${member.mem_modify}</li>
                </c:if>
            </ul>
        </div>
    </div>

    <!-- 이용내역 -->
    <button class="collapsible">이용내역</button>
   		<div>
			<a href="#">진료기록</a>
         	<a href="${pageContext.request.contextPath}/reservation/myResList">예약내역</a>
         	<a href="#">문의내역</a>
    	</div>

    <!-- 나의 활동 -->
    <button class="collapsible" style="margin-top: 10px;">나의 활동</button>
		<div>
            <a href="#">좋아요</a>
            <a href="#">댓글</a>
   		</div>

    <script>
        var coll = document.getElementsByClassName("collapsible");
        var i;

        for (i = 0; i < coll.length; i++) {
            coll[i].addEventListener("click", function() {
                this.classList.toggle("active");
                var content = this.nextElementSibling;
                if (content.style.display === "block") {
                    content.style.display = "none";
                } else {
                    content.style.display = "block";
                }
            });
        }
    </script>
</body>
</html>
