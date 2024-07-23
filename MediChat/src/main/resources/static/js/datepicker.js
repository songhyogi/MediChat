$(function() {
    // Datepicker 초기화 함수
    function initDatepicker() {
        $('#mem_birth').datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            yearRange: '1900:2024',
            showAnim: 'slideDown',
            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
            beforeShow: function(input, inst) {
                // Datepicker를 보이기 전에 실행되는 함수
                if ($('#mem_birth').is(':focus')) {
                    $('.ui-datepicker').show(); // 포커스된 경우에만 보이도록 설정
                } else {
                    $('.ui-datepicker').hide(); // 포커스가 아닌 경우 숨김 처리
                }
            }
        });
    }
    // 버튼 클릭 시 Datepicker 표시
    $('#calendarButton').click(function() {
        $('#mem_birth').datepicker('show'); // Datepicker 표시
    });

    // jQuery UI Datepicker를 동적으로 로드
    $.getScript('https://code.jquery.com/ui/1.13.0/jquery-ui.min.js').done(function() {
        initDatepicker(); // Datepicker 초기화
    });
});
