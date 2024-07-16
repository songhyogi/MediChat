$(function(){
	
	console.log('페이지 로딩 완료');
	
	/*=======================
	  스크롤 채팅 최하단으로 내리기
	=========================*/
	function scrollToBottom() {
            const chatBody = document.getElementById('chat_body');
            chatBody.scrollTop = chatBody.scrollHeight;
        };
	
	/*=======================
		    메시지 불러오기
	=========================*/
	function selectChat(chat_num,res_date,res_time,res_num){
        
		//서버와 통신
		$.ajax({
			url:'/chat/chatDetail',
			type:'get',
			data:{chat_num:chat_num, res_date:res_date, res_time:res_time, res_num:res_num},
			dataType:'json',
			success:function(param){
				if(param.user=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}else{
					//로그인 상태인 경우
					$('#chat_num').val(chat_num);
					$('#res_date').val(res_date);
					$('#res_time').val(res_time);
					$('#res_num').val(res_num);
					let res_title = '';
					res_title += '			<div class="chat-title fs-25 fw-8" id="chat_title">';
					res_title += '				예약번호: '+res_num;
					res_title += '			</div>';
					res_title += '			<div class="chat-date fs-20">';
					res_title += '				'+res_date+'  '+res_time;
					res_title += '			</div>';
					if(param.type=='3'){
						res_title += '			<button class="chat-image" value="진료 종료" id="chat_close">';
					};

					$('#chat_header').html(res_title);
					
					let message = '';
					message += '		<ul>';
					if(param.chat=='open'){
						//예약시간이 되어서 채팅방을 쓸 수 있는 상태
						$('.chat-input input,.chat-input button').prop('disabled', false);
						$(param.list).each(function(index,item){
							if(param.type=='1'|| param.type=='2'){
								if(item.msg_sender_type == 0){ //일반 회원이 0
									//유저가 일반 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="my-message bg-green-7 fs-20">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 1){
									//유저가 일반 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="other-message bg-gray-6 fs-20">'+item.msg_content+'</li>';
								}
							}else if(param.type=='3'){
								if(item.msg_sender_type == 1){ //의사 회원이 1
									//유저가 의사 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="my-message bg-green-7 fs-20">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 0){
									//유저가 의사 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="other-message bg-gray-6 fs-20">'+item.msg_content+'</li>';
								}
							}//end of param.type(이용자 type)
						}); //end of message list
						
						message += '	</ul>'
						
					}else if(param.chat=='close'){
						message += '		<div class="close-room">';
						message += '			<img src="../images/chat_bubble.png" width="40px" height="40px" class="chat-bubble">';
						message += '			<span class="fs-21 chat-notice fw-8">아직 진료가 시작되지 않은 채팅방입니다</span>'
						message += '		</div>'
					}
					
					$('#chat_body').html(message);
					scrollToBottom();
				}//end of user login
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
	}; //end of selectChat
		
	/*=======================
	  채팅방 선택 시 채팅방 불러오기
	=========================*/
	$('.chat-room').click(function(event){
		event.preventDefault();
		
		let chat_num = $(this).data('chat-num');
        let res_date = $(this).data('res-date');
        let res_time = $(this).data('res-time');
        let res_num = $(this).data('res-num');
        
        const selected = $(this).parent().parent();
        const not_selected = $('.chat-room').parent().parent();
        console.log(selected);
        
        not_selected.removeClass('selected-chat bg-gray-6');
        selected.addClass('selected-chat bg-gray-6');
        
        selectChat(chat_num, res_date, res_time, res_num);
	});
	
	/*=======================
		    메시지 입력하기
	=========================*/
	$('#chat_input').submit(function insertMsg(event){
		
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('전송 이벤트 발생');
		if($('#msg_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#msg_content').val('').focus();
			return false;
		}
		
		//form 제출 데이터
		let formArray = $(this).serializeArray();
		console.log(formArray);
		
		//서버와 통신
		$.ajax({
			url:'/chat/chatRoom',
			type:'post',
			data:$(this).serialize(),
			dataType:'json',
			success:function(param){
				if(param.user=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}else if(param.user=='login'){
					//로그인 상태인 경우
					let chat_num = formArray.find(item => item.name === 'chat_num').value;
	                let res_date = formArray.find(item => item.name === 'res_date').value;
	                let res_time = formArray.find(item => item.name === 'res_time').value;
	                let res_num = formArray.find(item => item.name === 'res_num').value;
					selectChat(chat_num, res_date, res_time, res_num);
					initForm();	
					console.log('메시지 입력');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
		}); //end of ajax
	});//end of insertMsg
	
	
	//채팅 입력 폼 초기화
	function initForm(){
		$('#msg_content').val('');
	}
	
	//300자 이하로 입력
	$(document).on('keydown','textarea',function(){
		let inputLength = $(this).val().length;
		
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300));
		}
	});
	
	/*=======================
		 이미지 전송 폼 노출
	=========================*/
	$('#chat_image').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		$('.image-form-bg').show();
		$('.image-form').show();
		
	});
	
	//모달 창 닫기 버튼 클릭
	$('.image-form .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		$('.image-form-bg').hide();
		$('.image-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.image-form').click(function(event) {
		event.stopPropagation();
	});
	
	/*=======================
		 	이미지 전송
	=========================*/
	$('#image_input').submit(function(event){
		//기본 이벤트 제거	
		event.prevnetDefault();
		
		if($('#select_image').val().trim()==''){
			alert('첨부파일을 선택하세요');
			$('#select_image').val('').focus();
			return false;
		}
		
		//form 제출 데이터
		let formArray = $(this).serializeArray();
		console.log(formArray);
		
		//서버와 통신
		$.ajax({
			url:'input_image',
			type:'post',
			data:$(this).serialize(),
			dataType:'json',
			success:function(param){
				if(param.user=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}else if(param.user=='login'){
					//로그인 상태인 경우
					let chat_num = formArray.find(item => item.name === 'chat_num').value;
	                let res_date = formArray.find(item => item.name === 'res_date').value;
	                let res_time = formArray.find(item => item.name === 'res_time').value;
	                let res_num = formArray.find(item => item.name === 'res_num').value;
					selectChat(chat_num, res_date, res_time, res_num);
					initForm();	
					console.log('메시지 입력');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
	}); //end of submit image
	
});