<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="image-form">
		<div class="form-title bg-green-7 fs-18">
		진료 종료
			<button type="button" class="close-button">&times;</button>
		</div>
		<div class="form-body">
		<form action="image_input" id="image_input">
			<label for="select_image" class="">파일 선택</label>
			<input type="file" id="select_image" accept="image/gif,image/png,image/jpeg">
			<input type="submit" value="전송">
		</form>
		</div>
	</div>
<div class="image-form-bg"></div>