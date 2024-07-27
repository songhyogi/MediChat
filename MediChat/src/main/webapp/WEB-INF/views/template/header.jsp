<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- 상단 시작 -->
<div id="header_box" class="d-flex justify-content-between">
	<div class="d-flex justify-content-start">
		<!-- 로고 시작 -->
		<div class="me-5">
			<a href="/main/main"><img src="/images/logo8.png" width="220" height="70"></a>
		</div>
		<!-- 로고 끝 -->
		<!-- 메뉴 시작 -->
		<div class="d-flex justify-content-between align-items-center">
			<div class="header-menu">
				<a href="/hospitals?user_lat=${user_lat}&user_lon=${user_lon}" class="header-menu-text">병원 찾기</a>
			</div>
			<div class="header-menu">
				<a href="/pharmacies/search?user_lat=${user_lat}&user_lon=${user_lon}" class="header-menu-text">약국 찾기</a>
			</div>
			<div class="header-menu">
				<a href="/consultings" class="header-menu-text">의료 상담</a>
			</div>
			<div class="header-menu">
				<a href="/health/healthBlog" class="header-menu-text">건강 블로그</a>
			</div>
			<div class="header-menu">
				<a href="/disease/diseaseDictionary" class="header-menu-text">질병 백과사전</a>
			</div>
			<div class="header-menu">
				<a href="/drug/search" class="header-menu-text">의약품 백과사전</a>
			</div>
			<div class="header-menu">
				<a href="/faq/faqList" class="header-menu-text">고객센터</a>
			</div>
		</div>
		<!-- 메뉴 끝 -->
	</div>
	<div class="d-flex justify-content-end align-items-center">
		<c:if test="${empty user}">
		<!-- 로그인/회원가입 시작 -->
		<div id="header-status-logout" class="d-flex">
			<div class="header-status-leftBox">
				<a id="header-login-text">로그인</a>
			</div>
			<div class="header-status-rightBox">
				<a id="header-register-text">회원가입</a>
			</div>
			<div id="header-login-div" style="display: none;">
				<div class="header-login-select">
					<a href="/member/login">일반 로그인</a>
				</div>
				<div class="select-line"></div>
				<div class="header-login-select">
					<a href="/doctor/login">의사 로그인</a>
				</div>
			</div>
			<div id="header-register-div" style="display: none;">
				<div class="header-register-select">
					<a href="/member/registerUser">일반 회원가입</a>
				</div>
				<div class="select-line"></div>
				<div class="header-register-select">
					<a href="/doctor/registerDoc">의사 회원가입</a>
				</div>
			</div>
		</div>
		<!-- 로그인/회원가입 끝 -->
		</c:if>
		<c:if test="${!empty user}">
		<div class="overlay" id="overlay"></div>
		<!-- 알림 + 프로필 시작 -->
		<div id="header-status-login" class="d-flex align-items-center">
			<div class="header-status-leftBox">
				<img id="header-notification" src="/images/notification.png" width="35" height="35">
				<span id="header-notification-unreaded" class="fs-10 translate-middle badge rounded-pill bg-danger">
  					${noti_cnt}
				</span>
			</div>
			<c:if test="${user.mem_auth==2}">
				<c:if test="${mem_profile == null}">
				<div class="header-status-rightBox">
					<div class="text-center">
						<img id="header-profile" src="${pageContext.request.contextPath}/member/memPhotoView" width="40" height="40" class="border rounded-circle">
			    	</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/member/myPage">내 정보</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<c:if test="${mem_gender == null}">
							<a href="/member/logout">로그아웃</a>						
						</c:if>
						<c:if test="${mem_gender != null}">
							<a href="/member/naverLogout">로그아웃</a>						
						</c:if>
					</div>
				</div>
				</c:if>
				<c:if test="${mem_profile != null}">
				<div class="header-status-rightBox">
					<div class="text-center">
						<img id="header-profile" src="${mem_profile}" width="40" height="40" class="border rounded-circle">
			    	</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/member/myPage">내 정보</a>
					</div>
				<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/member/kakaologout">로그아웃</a>
					</div>
				</div>
				</c:if>
			</c:if>
			<c:if test="${user.mem_auth==3}">
				<div class="header-status-rightBox">
					<div class="text-center">
				    	<img id="header-profile" src="${pageContext.request.contextPath}/doctor/docPhotoView" width="40" height="40" class="border rounded-circle">
					</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/doctor/docPage">내 정보</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/doctor/logout">로그아웃</a>
				   </div>
				</div>
			</c:if>
			<c:if test="${user.mem_auth==9}">
				<div class="header-status-rightBox">
					<div class="text-center">
						<img id="header-profile" src="${pageContext.request.contextPath}/member/memPhotoView" width="40" height="40" class="border rounded-circle">
			    	</div>
				</div>
				<div id="header-status-div" style="display: none;">
					<div class="header-status-select">
						<a href="/doctor/agree">의사회원가입 승인</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/member/changeAuth">회원권한 변경</a>
					</div>
					<div class="select-line"></div>
					<div class="header-status-select">
						<a href="/member/logout">로그아웃</a>
					</div>
				</div>
			</c:if>
		</div>
		<!-- 알림 + 프로필 끝 -->
		</c:if>
	</div>
</div>
<!-- 상단 끝 -->
<div id="noti_div" style="display:none;" class="bg-white">
	<div class="fw-7 fs-20 text-dark-7 text-center mb-4">알림 내역</div>
		<div id="noti_box">
			
		</div>
</div>
<script>
	function ring() {
		var img = document.getElementById('header-notification');
		img.src = "/images/notification-bell.png";
		img.classList.add('bell-shake');
	
		// 애니메이션이 끝난 후 클래스 제거 (애니메이션을 다시 트리거하기 위해)
		img.addEventListener('animationend', function() {
	  		img.classList.remove('bell-shake');
	  		img.src="/images/notification.png";
		}, { once: true });
	}
	
	function fetchNotifications(param) {
        if (param.result == 'success') {
            $('#header-notification-unreaded').text('');
            $('#header-notification-unreaded').text(param.cnt);
            
            var output = ' ';
            
            if (param.list.length > 0) {
                $('#noti_box').empty();
                
                for (let i = 0; i < param.list.length; i++) {
                    output += '<div data-notiNum="' + param.list[i].noti_num + '" class="noti-item'
                    if (param.list[i].noti_isRead == 1) {
                        output += ' noti-item-readed bg-gray-2">';
                    } else {
                        if (param.list[i].noti_priority == 0) {
                            output += ' noti-item-unreaded">';
                        } else {
                            output += ' noti-item-priority">';
                        }
                    }
                    output += '<div class="d-flex align-items-center">';
                    if (param.list[i].noti_priority == 1) {
                        output += '<div class="red-circle"></div>';
                        output += '<div class="text-dange fw-7 fs-13 me-1">중요</div>';
                    }
                    output += '<div class="noti-item-message fs-15">';
                    output +=' <div class="noti-item-message-wrap">';
                    output += param.list[i].noti_message;
                    output += '</div>';
                    output += '</div>';
                    output += '</div>';
                    output += '<div class="noti-item-category fs-13 fw-7">';
                    if (param.list[i].noti_category == 1) {
                        output += '진료 관련 알림';
                    } else if (param.list[i].noti_category == 2) {
                        output += '커뮤니티 관련 알림';
                    } else if (param.list[i].noti_category == 3) {
                        output += '정보 관련 알림';
                    } else {
                        output += '일반 관련 알림'
                    }
                    output += '</div>';
                    output += '<div class="noti-item-createdDate fs-11 text-black-5">';
                    output += param.list[i].noti_createdDate;
                    output += '</div>';
                    if (param.list[i].noti_link != null) {
                        output += '<div class="noti-item-link">';
                        output += param.list[i].noti_link;
                        output += '</div>';
                    }
                    output += '</div>';
                }
            } else {
            	output += '<div id="noti_noneNoti" class="fw-7 fs-17 text-dark-7 text-center">${user.mem_id}님에게 온 알림이 없습니다!</div>';
            }
            $('#noti_box').html(output);
        } else if (param.result == 'fail') {
            alert('ajax 오류');
        } else {
            alert('네트워크 오류');
        }
    }
	
	$('#header-notification').click(function(){
		ring();
		$.ajax({
	        url: "/notification-json",
	        dataType: 'json',
	        success: function(param) {
	        	fetchNotifications(param);
	        },
	        error: function(){
	        	alert('ajax 오류');
	        }
		});
	});
	$('#header-notification-unreaded').click(function(){
		ring();
		$.ajax({
	        url: "/notification-json",
	        dataType: 'json',
	        success: function(param) {
	        	fetchNotifications(param);
	        },
	        error: function(){
	        	alert('ajax 오류');
	        }
		});
	});
	
	/* 알림 읽기 */
	$(document).ready(function() {
	    $('#noti_box').on("click", ".noti-item", function() {
	        $.ajax({
	        	url: "/notificationReaded",
	        	data: {noti_num : $(this).attr('data-notiNum')},
	        	dataType: 'json',
	        	success: function(param){
	        		fetchNotifications(param);
	        	},
	        	error: function(){
	        		alert('네 트 워 크 오 류');
	        	}
	        });
	    });
	});
	
	
	
	
	/* 로그인 시 */
	if(${!empty user}){
		/* 로그인 시 */
		const header_profile = document.getElementById('header-profile');
		const header_status_div = document.getElementById('header-status-div');
		header_profile.onclick = function(){
			if(header_status_div.style.display == 'block'){
				header_status_div.style.display = 'none';
			} else {
				header_status_div.style.display = 'block';
			}
		};
		/* 로그인 시 */
		const noti_div = document.getElementById('noti_div');
		
		const header_notification = document.getElementById('header-notification');
		const overlay = document.getElementById('overlay');
		header_notification.onclick = function(){
			document.getElementById('noti_div').style.display = 'block';
		    overlay.style.display = 'block';
		    document.body.style.overflow = 'hidden'; // 외부 스크롤 비활성화
		};
		overlay.onclick = function() {
		    document.getElementById('noti_div').style.display = 'none';
		    overlay.style.display = 'none';
		    document.body.style.overflow = 'auto'; // 외부 스크롤 활성화
		};
		
		
		const header_notification_unreaded = document.getElementById('header-notification-unreaded');
		header_notification_unreaded.onclick = function(){
			document.getElementById('noti_div').style.display = 'block';
		    overlay.style.display = 'block';
		    document.body.style.overflow = 'hidden'; // 외부 스크롤 비활성화
		};
	} else {/* 비로그인 시 */
		/* 비로그인 시 */
		const loginText = document.getElementById('header-login-text');
		const headerLoginDiv = document.getElementById('header-login-div');
		if(${empty user}){
			loginText.onclick = function(){
				if(headerLoginDiv.style.display == 'block'){
					headerLoginDiv.style.display = 'none';
				} else {
					headerLoginDiv.style.display = 'block';
				}
			};
		}
		/* 비로그인 시 */
		const registerText = document.getElementById('header-register-text');
		const headerRegisterDiv = document.getElementById('header-register-div');
		registerText.onclick = function(){
			if(headerRegisterDiv.style.display == 'block'){
				headerRegisterDiv.style.display = 'none';
			} else {
				headerRegisterDiv.style.display = 'block';
			}
		};
	}
</script>