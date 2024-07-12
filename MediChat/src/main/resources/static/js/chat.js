$(function(){
	
	/*=======================
		    메시지 불러오기
	=========================*/
	function selectMsg(){
		//서버와 통신
		$.ajax({
			url:'chatRoom',
			type:'get',
			data:{chat_num:chat_num},
			dataType:'json',
			success:function(param){
				if(param.user=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}else{
					//로그인 상태인 경우
					$('#chat_title').css('display','');
					let message = '';
					if(param.chat=='open'){
						//예약시간이 되어서 채팅방을 쓸 수 있는 상태
						$(param.list).each(function(index,item){
							message += '		<ul>';
							if(param.type=='1'|| param.type=='2'){
								if(item.msg_sender_type == 0){
									//유저가 일반 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="my-message">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 1){
									//유저가 일반 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="other-message">'+item.msg_content+'</li>';
								}
							}else if(param.type=='2'){
								if(item.msg_sender_type == 1){
									//유저가 일반 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="my-message">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 0){
									//유저가 일반 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									console.log('발신자 타입: '+item.msg_sender_type);
									console.log('발신 메시지: '+item.msg_content);
									message += '			<li class="other-message">'+item.msg_content+'</li>';
								}
							}//end of param.type(이용자 type)
						}); //end of message list
						
						message += '	</ul>'
						
						
					}else if(param.chat=='close'){
						message += '		<div class="close-room">';
						message += '			<img src="../images/chat_bubble.png" class="chat-bubble">';
						message += '			<span class="fs-17 chat-notice">아직 진료가 시작되지 않은 채팅방입니다</span>'
						message += '		</div>'
					}
					
					$('#chat_body').append(message);
				}//end of user login
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
});