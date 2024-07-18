<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="close-payment-form">
	<div class="form-title bg-green-7 fs-20 fw-4">
		진료 종료
		<button type="button" class="close-button">&times;</button>
	</div>
	<div class="close-form-body">
		<div class="form-notice">
				<span class="notice-title fw-8 fs-24">진료비</span>
		</div>
		<div class="form-charge">
			<form action="form-charge" method="post">
				<input type="number" name="pay_amount" id="pay_amount" placeholder="진료비 청구 금액을 숫자만 입력해주세요">
				<input type="submit" value="입력">
			</form>
			<div class="fs-16">&#8251;진료비 청구 전송 이후에는 청구 금액 변경이 불가능합니다.</div>
				<div class="fs-16">&#8251;진료비 청구 전송 이후 종료 확정을 누르면 채팅창 입력이 비활성화됩니다.</div>
		</div>
		<div class="form-button">
		<button type="button" class="close-form-btn btn-green fs-20" id="close_cancel">이전으로</button>
		<button type="button" class="close-form-btn btn-green fs-20" id="close_next">전송</button>
		</div>
	</div>
</div>
<div class="close-payment-form-bg"></div>	