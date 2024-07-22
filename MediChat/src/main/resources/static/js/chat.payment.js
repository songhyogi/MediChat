$(function(){
	let chat_num = '';
	let doc_name = '';
	let pay_amount = '';
	let mem_num = '';
	let mem_phone =''; //member table에서 가져와야
	

	
	$(document).on('click','#chat_payment',function requestPay(){
		var IMP = window.IMP;
		IMP.init("imp21212228"); //고객사 식별코드로 SDK 초기화
		
		pay_amount = $(this).data('pay_amount');
		chat_num = $(this).data('chat_num');
		
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
					
				console.log('mem_phone:', mem_phone);
				console.log('chat_num:', chat_num);
				console.log('mem_num:', mem_num);
                    
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
						//rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
						if (rsp.success) {
							//서버 검증 요청 부분
							alert("결제 성공: "+rsp.name);
						} else {
							alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
						}
					} //end of callback
				); 
				}, //end of success
				error:function(){
					alert('네트워크 오류 발생');
				}
		}); //end of ajax

	}); //end of click event
});