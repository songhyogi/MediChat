<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- CK 에디터 -->
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<!-- 커뮤니티 글쓰기 시작 -->
<div class="page-main">
	<div class="body-header">
		<p class="text-lightgray fw-7 fs-13">홈 > 커뮤니티</p>
		<h4>커뮤니티 글 등록</h4>
		<form:form action="write" modelAttribute="communityVO">
			<ul>
				<li>
					<form:label path="cbo_type">분류</form:label>
					<form:select path="cbo_type">
						<option disabled="disabled" selected>카테고리</option>
						<form:option value="1">질환고민</form:option>
						<form:option value="2">다이어트·헬스</form:option>
						<form:option value="3">피부고민</form:option>
						<form:option value="4">임신·성고민</form:option>
						<form:option value="5">탈모고민</form:option>
						<form:option value="6">마음건강</form:option>
						<form:option value="7">뼈와관절</form:option>
						<form:option value="8">영앙제</form:option>
						<form:option value="9">자유게시판</form:option>
					</form:select>
					<form:errors path="cbo_type" cssClass="error-color"/>
				</li>
				<li>
					<form:label path="cbo_title">제목</form:label>
					<form:input path="cbo_title"/>
					<form:errors path="cbo_title" cssClass="error-color"/>
				</li>
				<li>
					<form:textarea path="cbo_content"/>
					<form:errors path="cbo_content" cssClass="error-color"/>
					<script>
					 function MyCustomUploadAdapterPlugin(editor) {
						    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
						        return new UploadAdapter(loader);
						    }
						}
					 
					 ClassicEditor
			            .create( document.querySelector( '#cbo_content' ),{
			            	extraPlugins: [MyCustomUploadAdapterPlugin]
			            })
			            .then( editor => {
							window.editor = editor;
						} )
			            .catch( error => {
			                console.error( error );
			            } );
				    </script>
				</li>
			</ul>
			<div>
				<form:button class="submit-btn">등록</form:button>
			</div>
		</form:form>
	</div>
</div>
<!-- 커뮤니티 글쓰기 끝 -->