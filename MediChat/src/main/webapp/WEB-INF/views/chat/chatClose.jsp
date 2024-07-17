<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="close-form">
	<div class="form-title bg-green-7 fs-20 fw-4">
		진료 종료
		<button type="button" class="close-button">&times;</button>
	</div>
	<div class="close-form-body">
		<div class="form-notice">
			<span class="notice-title fw-8 fs-24">환자 열람 파일 등록</span>
			<span class="notice-sub fs-16">등록된 진료 파일은 환자의 진료비 결제 후에 자동으로 전송됩니다.</span>
		</div>
		<div class="form-list">
			<table id="file_table">
				<tr class="list-head">
					<th class="list-no">No.</th>
					<th class="list-name">파일명</th>
					<th class="list-type">파일 유형</th>
					<th class="list-valid-date">유효기간</th>
				</tr>
				<tr>
					
				</tr>
			</table>
		</div>
		<div class="form-body">
		<form action="file_input" id="file_input">
			<input type="hidden" name="chat_num" id="close_chat_num" value="">
			<select name="file_type">
				<option>처방전</option>
				<option>소견서</option>
				<option>진단서</option>
				<option>진료비 세부내역서</option>
			</select>
			<label for="valid_date">유효기간</label>
			<input type="date" name="valid_date">
			<label for="select_image" class="">파일 선택</label>
			<input type="file" name="select_file" id="select_file" accept=".pdf,.doc,.docx">
			<input type="submit" value="등록">
		</form>
		<div class="fs-16">파일은 word(.doc, .docx)와 PDF 형식 파일만 전송 가능합니다.</div>
		</div>
		<div class="form-button">
		<button type="button" class="btn-green" id="close_cancel">취소</button>
		<button type="button" class="btn-green" id="close_next">다음으로</button>
		</div>
	</div>
</div>
<div class="close-form-bg"></div>