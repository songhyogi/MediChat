$(function(){
	//비밀번호 찾기
	$('#doctor_findPasswd').submit(function(event){
		if($('#mem_id').val().trim()==''){
			alert('아이디를 입력하세요.');
			$('#mem_id').val('').focus();
			return false;
		}
		if($('#doc_email').val().trim()==''){
			alert('이메일을 입력하세요.');
			$('#doc_email').val('').focus();
			return false;
		}
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'getPasswordInfo',
			type:'post',
			data:form_data,
			dataType:'json',
			beforeSend:function(){
				$('#loading').show();//표시
			},
			complete:function(){
				$('#loading').hide();//숨김
			},
			success:function(param){
				if(param.result == 'invalidInfo'){
					alert('아이디 또는 이메일 불일치');
				}else if(param.result == 'noAuthority'){
					alert('정지회원 입니다.');
				}else if(param.result == 'success'){
					alert('이메일로 임시비밀번호가 발송되었습니다.');
					location.href='login';
				}else{
					alert('비밀번호 찾기 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
});