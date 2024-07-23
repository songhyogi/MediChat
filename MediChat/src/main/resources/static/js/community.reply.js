$(function(){
	let rowCount = 20;
	let currentPage;
	let count;
	
	/*==============================댓글==============================*/
	/*-----댓글 목록-----*/
	function selectList(pageNum){
		currentPage = pageNum;
		console.log("<js ajax 호출 전>")
		$.ajax({
			url:'/medichatCommunity/listComment',
			type:'get',
			data:{cbo_num:$('#cbo_num').val(),pageNum:pageNum,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				count = param.count;
				console.log("<<댓글목록>>")
				
				if(pageNum == 1){//처음호출시 
					$('#output').empty();
				}
				console.log("<<댓글 목록 데이터>>", param.list);

				//댓글수 읽어 오기
				//displayReplyCount(param.count);
				
				$(param.list).each(function(index,item){
					//처음에는 보여지지 않고 다음 댓글부터 수평선에 보이게 처리
					if(index>0) $('#output').append('<hr size="1" width="100%">'); 
					
					let output = '<div class="item">';
					output += '     <ul class="detail-info">';
					output +='        <li>';
					output += '         <img src="../member/memViewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="profile">';
					output +='        </li>';
					output +='        <li>';
					output += item.mem_id + '<br>';
					output += '<span class="modify-date">' + item.cre_rdate + '</span>';
					output +='        </li>';
					output +='     </ul>';
					output +='     <div class="sub-item">';
					output += '    <p>' + item.cre_content.replace(/\r\n/g,'<br>') + '</p>';
					
					/*---좋아요 시작---*/
					/*---좋아요 끝---*/
					
					if(param.user_num===item.mem_num){
						//로그인 한 회원번호와 댓글 작성자 회원번호가 같으면
						output += '  <input type="button" data-num="'+item.cre_num+'" value="수정" class="modify-btn">';
						output += '  <input type="button" data-num="'+item.cre_num+'" value="삭제" class="delete-btn">';
					}
					
					/*---답글 시작---*/
					/*---답글 끝---*/
					
					output += '  </div>';
					output += '</div>';
					
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				console.log("currentPage:"+currentPage);
				console.log("count:"+count);
				console.log("rowCount:"+rowCount);
				
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				alert('목록 네트워크 오류');
			}
		});//end of ajax
	}
	
	/*-----댓글 등록-----*/
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
		$('#re_first .letter-count').text('0/300');
	}
	
	
	/*---------------댓글/답글 등록, 수정 공통---------------*/
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 체크
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){
			$(this).val($(this).val().substring(0,300));
		}else{//300자 미만
			let remain = inputLength + '/300';
			
			if($(this).attr('id')=='cre_content'){
				$('#re_first .letter-count').text(remain);
			}//댓글수정, 답글 등록,수정 추가 필요
		}
	});
	
	/*==============================초기 데이터(목록) 호출==============================*/
	selectList(1);
});
	
	