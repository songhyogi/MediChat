<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="close-form">
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
				<input type="number" name="pay_amount">
				<input type="submit" value="입력">
			</form>
		</div>
		<div class="form-button">
		<button type="button" class="close-form-btn btn-green fs-20" id="close_cancel">취소</button>
		<button type="button" class="close-form-btn btn-green fs-20" id="close_next">다음으로</button>
		</div>
	</div>
</div>
<div class="close-form-bg"></div>	