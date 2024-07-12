 $(function(){
	let pageNum;
	let count;
	
	
		$('#hFav').click(function(){
			let healthy_num = $(this).attr('data-num');
				$.ajax({
					url:'clickHFav',
					type:'post',
					data:{healthy_num:healthy_num},
					dataType:'json',
					success:function(param){
						if(param.result =='logout'){
							$('#Give-It-An-Id').prop('checked',false);
							alert('로그인 후 좋아요 가능');
							
						}else if(param.result=='success'){
							if(param.count == null){
								count=0;
							}else{
								count = param.count
							}
							
							if(param.status =='noFav'){
								$('#Give-It-An-Id').attr('checked',false);
								$('#hfav_cnt').text(count);
							}else {
								$('#Give-It-An-Id').attr('checked',true);
								$('#hfav_cnt').text(count);
							}
						}else{
							alert('좋아요 클릭 오류');
						}
						
					},
					error:function(){
						alert('네트워크 오류');
					}
					
					
				});		
		});//게시글 좋아요 끝
	
	//댓글 입력
	$('#hreWrite').submit(function(event){
	 	
		if($('#hre_content').val().trim()==''){
			alert('내용을 입력해주세요');
			$('#hre_content').val('').focus();
			return false;
		}
			if($('#hre_content').val().length >300){
			alert('300자까지 입력가능합니다.');
			$('#hre_content').val($('#hre_content').val().substring(0,300)).focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		$.ajax({
			url:'insertHreply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 댓글 작성 가능');
				}else if(param.result=='success'){
					 selectReply(1);	
					$('#hre_content').val('');
				}else{
					alert('댓글 쓰기 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		})
		
		event.preventDefault();
	});
	
	
	
	function selectReply(currentNum){
		pageNum= currentNum;
		$.ajax({
			url:'selectHReply',
			type:'post',
			data:{healthy_num:$('#replyList').attr('data-num'),pageNum:pageNum},
			dataType:'json',
			success:function(param){
					if(param.count==null){
						count =0;
					}else{
						count = param.count;
					}
					$('#replyList').empty();
					
					$(param.list).each(function(index,item){
						let output ='';
						output +='<div class="items">';
						output +='<ul>';
						output+='<li>'+item.id+'</li>';
						if(item.hre_modify_date){
							output+='<li> 수정일 : '+item.hre_modify_date+'</li>';
						}else{
							output+='<li> 등록일 : '+item.hre_reg_date+'</li>';
						}
						output +='</ul>';
						output+='<p>'+item.hre_content+'<p>';
						if(param.user_num == item.mem_num){
							output+='<div class="align-right btnspace"><input type="button" data-num="'+item.hre_num+'" data-mem="'+item.mem_num+'" value="수정" class="modify-btn"><input type="button" value="삭제" class="delete-btn"></div>';	
						}
						output +='</div>';
						$('#replyList').append(output);
					})
					
				
			
				
			},
			error:function(){
				alert('네트워크 오류');
			}
		})
	};
	
	$(document).on('click','.modify-btn',function(){
		let content =$(this).parent().find('p');
		$(this).parent().hide();
		let output = '<div id="modifyform>';
		output+='<form  id="hreModify">';
		output+='<input type="hidden" id="hre_num"  name ="hre_num" value="'+$(this).attr('data-num')+'">';
		output +='<input type="hidden" name="mem_num" value="'+$(this).attr('data-mem')+'">';
		output+='<textarea rows="5" cols="35" name="hre_content" id="hre_content"  style="resize:none;" placeholder="300자까지 입력가능" >'+content.val()+'</textarea>';
		output+='<input type="submit" value="댓글쓰기"></form>';
		output+='</div>';
		$(this).parent().find('.items').append(output);
	});
	 selectReply(1);
 });