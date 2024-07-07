<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="containerm">
	<div class="text-center">
		연습
	</div>
	<span id="messages"></span>
</div>


<script>
	//서버에서 /sse 엔드포인트로부터 SSE를 수신
	var eventSource = new EventSource('/sse');
	
	// 메시지를 수신할 때마다 호출되는 이벤트 리스너
	eventSource.onmessage = function(event) {
	    var messageDiv = document.getElementById('messages');
	    var newMessage = document.createElement('div');
	    newMessage.textContent = event.data;
	    messageDiv.appendChild(newMessage);
	};
	
	// 연결이 열렸을 때 호출되는 이벤트 리스너
	eventSource.onopen = function(event) {
	    console.log("Connection to server opened.");
	};
	
	// 에러가 발생했을 때 호출되는 이벤트 리스너
	eventSource.onerror = function(event) {
	    console.log("Error occurred: ", event);
	    if (event.eventPhase == EventSource.CLOSED) {
	        console.log("Connection was closed.");
	    }
	};
</script>