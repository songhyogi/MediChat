<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="/consultings/create" method="post">
	<label>구분</label>
	<select name="con_type">
		<option selected>선택해주세요</option>
		<option value="0">구분없음</option>
		<option value="1">만성질환</option>
		<option value="2">여성질환</option>
		<option value="3">소화기질환</option>
		<option value="4">영양제</option>
		<option value="5">정신건강</option>
		<option value="6">처방약</option>
		<option value="7">탈모</option>
		<option value="8">통증</option>
		<option value="9">여드름,피부염</option>
		<option value="10">임신,성고민</option>
	</select>
	
	<label id="con_title">제목</label>
	<input type="text" name="con_title" id="con_title">
	
	<label id="con_content">내용</label>
	<textarea rows="5" cols="15" id="con_content" name="con_content"></textarea>
	
	<input type="submit" value="등록">
</form>