$(function(){
	let message_socket; //웹소켓 식별자
	console.log('페이지 로딩 완료');
	
	/*=======================
	  		 웹소켓 연결
	=========================*/
	function connectWebSocket(){
		message_socket = new WebSocket('ws://localhost:8000/message-ws');
		
		//채팅 페이지 접속 시
		message_socket.onopen=function(evt){
			console.log('채팅페이지 접속 : ' + $('#chat_body').length);
			if($('#chat_body').length==1){
				message_socket.send('msg');
			}
		};
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		message_socket.onmessage=function(evt){
			//메시지 읽기
			let data = evt.data;
			if($('#chat_body').length==1 
			           && data.substring(0,3)=='msg'){
				selectChat();
			}
		};
		message_socket.onclose=function(evt){
			//소켓이 종료된 후 부과적인 작성이 있을 경우 명시
			console.long('chat close');
		};
	}
	
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
	function selectChat(){
        
		//서버와 통신
		$.ajax({
			url:'/chat/chatDetail',
			type:'get',
			data:{chat_num:chat_num, res_date:res_date, res_time:res_time, res_num:res_num},
			dataType:'json',
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					message_socket.close();
					window.location.href='/main/main';
				}else{
					//로그인 상태인 경우
					$('#chat_num').val(chat_num);
					$('#res_date').val(res_date);
					$('#res_time').val(res_time);
					$('#res_num').val(res_num);
					let res_title = '';
					res_title += '			<div class="res-title">'
					res_title += '			<div class="chat-title fs-25 fw-8" id="chat_title">';
					res_title += '				예약번호: '+res_num;
					res_title += '			</div>';
					res_title += '			<div class="chat-date fs-20">';
					res_title += '				'+res_date+'  '+res_time;
					res_title += '			</div>';
					res_title += '			</div>';
					if(param.type=='3'){
						res_title += '			<button class="chat-close-btn" id="chat_close">진료 종료</button>';
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
									message += '			<li class="my-message bg-green-7 fs-20">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 1){
									//유저가 일반 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									message += '			<li class="other-message bg-gray-6 fs-20">'+item.msg_content+'</li>';
								}
							}else if(param.type=='3'){
								if(item.msg_sender_type == 1){ //의사 회원이 1
									//유저가 의사 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
									message += '			<li class="my-message bg-green-7 fs-20">'+item.msg_content+'</li>';
								}else if(item.msg_sender_type == 0){
									//유저가 의사 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
									message += '			<li class="other-message bg-gray-6 fs-20">'+item.msg_content+'</li>';
								}
							}//end of param.type(이용자 type)
						}); //end of message list
						
						message += '	</ul>'
						
					}else if(param.chat=='close'){
						//예약시간이 되지 않아서 채팅방을 사용할 수 없는 상태
						message_socket.close();
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
				message_socket.close();
			}
		}); //end of ajax
	}; //end of selectChat
		
	/*=======================
	  채팅방 선택 시 채팅방 불러오기
	=========================*/
	let chat_num;
	let res_date;
	let res_time;
	let res_num;
	
	
	$('.chat-room').click(function(event){
		event.preventDefault();
		
		connectWebSocket();
		console.log('채팅방 선택 이벤트 발생');
		
		chat_num = $(this).data('chat-num');
        res_date = $(this).data('res-date');
        res_time = $(this).data('res-time');
        res_num = $(this).data('res-num');
        
        
        const selected = $(this).parent().parent();
        const not_selected = $('.chat-room').parent().parent();
        console.log(selected);
        
        not_selected.removeClass('selected-chat bg-gray-6');
        selected.addClass('selected-chat bg-gray-6');
        
        selectChat();
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
		
		
		//서버와 통신
		$.ajax({
			url:'/chat/chatRoom',
			type:'post',
			data:$(this).serialize(), //form 제출 데이터를 문자열로 직렬화
									  //메시지 입력창에서 메시지에 해당하는 부분
			dataType:'json',
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					message_socket.close();
					window.location.href='/main/main';
				}else if(param.userCheck=='login'){
					//로그인 상태인 경우
					message_socket.send('msg');
					initForm();	
					console.log('메시지 입력');
				}
			},
			error:function(){
				message_socket.close();
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
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}else if(param.userCheck=='login'){
					//로그인 상태인 경우
					
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
	}); //end of submit image
	
	/*=======================
		  채팅 종료 폼 노출
	=========================*/
	$(document).on('click','#chat_close',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('버튼 클릭 이벤트 발생');
		
		$('#close_chat_num').val(chat_num);
		$('.close-file-form-bg').show();
		$('.close-file-form').css('display','block');
		
	});
	
	//모달 창 닫기 버튼 클릭
	$('.close-file-form .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('닫기 버튼 클릭 이벤트 발생');
		
		
		$('.close-file-form-bg').hide();
		$('.close-file-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.close-form').click(function(event) {
		event.stopPropagation();
	});
	
	/*=======================
		 채팅 종료 폼 파일 전송
	=========================*/
	$('#file_input').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		let form_data = new FormData(this);
		
		let file = '';
		
		$.ajax({
			url:'/chat/chatClose',
			type:'post',
			data:form_data,
			contentType: false,  // 파일 업로드시 필요
        	processData: false,
			dataType:'json',
			success:function(param){
				
				file += '<tr>'
				if(param.file_name=='emptyFile'){
					//파일을 등록하지 않은 경우
					alert('파일을 등록해주세요.');
					$('#select_file').val('').focus();
					return false;
				}else{
					file += '	<td>'+param.file_name+'</td>';
				}
				file += '	<td>'+param.file_type+'</td>';
				if(param.valid_date=='emptyDate'){
					//유효기간을 등록하지 않은 경우
					alert('유효기간을 등록해주세요.');
					return false;
				}else if(param.valid_date=='pastDate'){
					//유효기간을 지난 날짜로 설정한 경우
					alert('유효기간은 당일 이전으로 설정할 수 없습니다.');
					$('#file_valid_date').focus();
					return false;
				}else{
					file += '	<td>'+param.valid_date+'</td>';	
					file += '	<td><button type="button" class="list-delete" data-file_num='+param.file_num+'>&times;</button></td>'
				}
				file += '</tr>';
				
				$('#file_table').append(file);   
	           },
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	/*=======================
		채팅 종료 폼 파일 삭제
	=========================*/
	$('#file_table').on('click', '.list_delete', function(){
		console.log('파일 삭제 클릭 이벤트 발생');
        //x 표시가 속하는 file_num 가져오기
        let file_num = $(this).data('file_num');
		
		$.ajax({
			url:'/chat/deleteFile',
			type:'post',
			data:{'file_num':file_num},
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					$('td[data-file_num="' + fileNum + '"]').closest('tr').remove();
				}else{
					alert('파일 삭제에 실패했습니다.');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	
	
	});
	
	/*=======================
		 진료비 청구 폼 호출
	=========================*/
	$(document).on('click','#close_file_next',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('버튼 클릭 이벤트 발생');
		
		$('.close-file-form-bg').hide();
		$('.close-file-form').hide();
		
		$('#close_chat_num').val(chat_num);
		$('.close-payment-form-bg').show();
		$('.close-payment-form').css('display','block');
		
	});
	
	//모달 창 닫기 버튼 클릭
	$('.close-payment-form .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('닫기 버튼 클릭 이벤트 발생');
		
		
		$('.close-payment-form-bg').hide();
		$('.close-payment-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.close-form').click(function(event) {
		event.stopPropagation();
	});
});