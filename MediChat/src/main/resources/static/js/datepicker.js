    $(function() {
        // Datepicker 초기화 함수
        function initDatepicker() {
            $('#mem_birth').datepicker({
                dateFormat: 'yy-mm-dd',
                changeYear: true,
                yearRange: '1900:2024',
                showAnim: 'slideDown',
                monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
            });
        }

        // 버튼 클릭 시 Datepicker 표시
        $('#calendarButton').click(function() {
            $('#mem_birth').datepicker('show'); // Datepicker 표시
        });

        // 폼 서브밋 시 선택한 날짜 값을 폼 필드에 설정
        $('#registerUser').submit(function(event) {
            var selectedDate = $('#mem_birth').val(); // 선택한 날짜 가져오기
            $('#mem_birth').val(selectedDate); // 폼 필드에 값 설정
        });

        // jQuery UI 로드 후 Datepicker 초기화
        $.getScript('https://code.jquery.com/ui/1.13.0/jquery-ui.min.js').done(function() {
            initDatepicker(); // Datepicker 초기화
        });
    });
