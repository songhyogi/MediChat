$(function(){
	let chat_num = '';	//진료 채팅 번호
	let doc_name = '';	//담당 의사 이름
	let pay_amount = ''; //결제 금액
	let mem_num = '';	//결제 회원 번호
	let mem_phone =''; //결제 회원 휴대폰 번호(iamport 실행 시 필요)
	

	$(document).on('click','#chat_payment',function requestPay(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		var IMP = window.IMP;
		IMP.init("imp21212228"); //고객사 식별코드로 SDK 초기화
		
		chat_num = $(this).data('chat_num');
		pay_amount = $(this).data('pay_amount');
		
		console.log('결제 대금: '+pay_amount);
		console.log('결제 채팅방 번호: '+chat_num);
		
		//서버와 통신
		$.ajax({
			url:'/chat/chatPayment',
			type:'get',
			data:{chat_num:chat_num},
			dataType:'json',
			success:function(param){
				mem_phone = param.payment.mem_phone;
				mem_num = param.payment.mem_num;
				doc_name = param.doc_name;
					
				console.log('mem_phone:'+mem_phone);
				console.log('chat_num:'+chat_num);
				console.log('mem_num:'+mem_num);
				console.log('doc_name:'+doc_name);
                    
				IMP.request_pay(
				{
					pg: 'html5_inicis',		//KG이니시스 pg파라미터 값
					pay_method: 'card',		//결제 방법
					merchant_uid: chat_num,//주문번호
					name: doc_name,				//상품 명
					amount: pay_amount,			//금액
					buyer_name: mem_num,
					buyer_tel: mem_phone,
				},
				function (rsp) { // 결제 시 콜백 함수
					if (rsp.success) {
                		// 결제 성공 시 서버에 추가 요청
                		$.ajax({
                    		url: '/chat/paymentConfirmation',
                    		type: 'post',
                    		data: {
                        		chat_num:chat_num,
                        		doc_name:doc_name,
                        		pay_amount:pay_amount,
                        		mem_num:mem_num
                    		},
                    		dataType: 'json',
                    		success: function(response) {
                        		alert("결제가 완료되었습니다.");
                        		console.log('서버 응답:', response);
                    		},
                        	error: function(xhr, status, error) {
                            	alert('결제 완료 후 서버 통신 오류');
                            	console.log(xhr.responseText);
                            	console.log('Ajax Error:', status, error);
                        	}
                    }); //end of callback ajax
                } else {
                    alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
                }
            }); //end of callback > end of request_pay
				}, //end of success
				error:function(xhr, status, error){
					alert('네트워크 오류 발생');
					console.log(xhr.responseText);
					console.log('Ajax Error:', status, error)
				}
		}); //end of click event ajax

	}); //end of click event	
});