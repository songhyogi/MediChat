<<<<<<< HEAD
$(function(){
    /*------------------
	    DatePicker
	--------------------*/
	$(document).ready(function() {
	    // jQuery UI Datepicker를 동적으로 로드
	    $.getScript('https://code.jquery.com/ui/1.13.0/jquery-ui.min.js').done(function() {
	        // Datepicker 초기화 및 설정
	        $('#mem_birth').datepicker({
	            dateFormat: 'yy-mm-dd', // 날짜 포맷 설정
	            changeYear: true, // 연도 선택 가능 여부
	            yearRange: '1900:2024', // 연도 범위 설정
	            showAnim: 'slideDown', // 애니메이션 설정 (옵션)
  				showOn: 'both', // 아이콘과 버튼 둘 다 클릭 가능하도록 설정 (옵션)
  				monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
           		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
	        });
	    });
	});
=======
$(function(){
    /*------------------
	    DatePicker
	--------------------*/
	$(document).ready(function() {
	    // jQuery UI Datepicker를 동적으로 로드
	    $.getScript('https://code.jquery.com/ui/1.13.0/jquery-ui.min.js').done(function() {
	        // Datepicker 초기화 및 설정
	        $('#mem_birth').datepicker({
	            dateFormat: 'yy-mm-dd', // 날짜 포맷 설정
	            changeYear: true, // 연도 선택 가능 여부
	            yearRange: '1900:2024', // 연도 범위 설정
	            showAnim: 'slideDown', // 애니메이션 설정 (옵션)
  				showOn: 'both' // 아이콘과 버튼 둘 다 클릭 가능하도록 설정 (옵션)
	        });
	    }).fail(function(jqxhr, settings, exception) {
	        console.error('Failed to load jQuery UI Datepicker:', exception);
	    });
	});

>>>>>>> refs/remotes/origin/kcy-feature
});