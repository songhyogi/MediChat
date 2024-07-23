$(function(){
	let rowCount = 20;
	let currentPage;
	let count;
	
	/*==============================댓글==============================*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		
		$.ajax({
			url:'listComment',
			type:'get',
			data:{cbo_num:$('#cbo_num').val(),pageNum:pageNum,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				
			}
		});
	}
	
	//댓글 등록
	$('#comment_form').submit(function(event){
		event.preventDefault();//ajax 통신위해 기본이벤트 제거
		
		if($('#cre_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#cre_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		console.log("<<댓글등록 form_data>>:"+ form_data);
		
		$.ajax({
			url:'writeComment',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('댓글을 작성하려면 로그인 해주세요');
					//로그인창 이동
				}else if(param.result == 'success'){
					initForm(); //폼 초기화
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	
	/*-----댓글/답글 등록, 수정 공통-----*/
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 체크
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){
			$(this).val($(this).val().substring(0,300));
		}else{//300자 미만
			let remain = 300 - inputLength
			reamin += '/300';
			
			if($(this).attr('id')=='cre_content'){
				$('#re_first .letter-count').text(remain);
			}//댓글수정, 답글 등록,수정 추가 필요
		}
	});
});
	
	