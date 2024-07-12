$(function(){
	/*------------------모달------------------ */
	//의약품 검색
	let drug_list = [];
	
	$('#drug_search').keyup(function(){
		if($('#drug_search').val().trim() ==''){//빈문자열
			$('#searchDrugList').empty();//searchDrugList의 모든 자식 요소 제거
			return;
		}
		$.ajax({
			url:'memberDrugSearchAjax',
			type:'get',
			data:{drg_name:$('#drug_search').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					let results = param.drugList.slice(0,10);//검색어 10개까지만 출력
					$('#searchDrugList').empty();
					$(results).each(function(index,item){
						let output = '';
						output += '<li data-name="'+item.drg_name+'">';
						output += item.drg_name;
						output += '</li>';
						$('#searchDrugList').append(output);
					});
				}else{
					alert('의약품 검색 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//검색된 의약품 선택
	$(document).on('click','#searchDrugList li',function(){
		let drg_name = $(this).text(); //선택한 의약품
		drug_list.push(drg_name);
		//선택한 의약품 화면에 표시
		let choice_drug = '<span class="drugSelect-span data-name="'+drg_name+'">';
		choice_drug += drg_name+'<sup>&times;</sup><span>'
		$('#drugSelect').append(choice_drug);
		$('#drug_search').val('');
		$('#searchDrugList').empty();
	});
	
	//검색된 의약품 삭제
	$(document).on('click','.drugSelect-span',function(){
		let drug_name = $(this).attr('data-name');
		//의약품이 저장된 배열에서 의약품명 제거
		drug_list.splice(drug_list.indexOf(drug_name),1);
		$(this).remove();//span 태그 삭제
	});
	
	//유효성 체크(복용내용 외 전부)
	
});