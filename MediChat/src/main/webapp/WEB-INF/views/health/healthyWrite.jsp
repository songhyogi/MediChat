<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<div class="page-main">
	<h4>홈 > 건강 블로그 >  글쓰기</h4> 		
	<h2>건강 블로그 </h2>
	
	<hr size="1" width="80%">
	
	<form:form action="healWrite" id="register_form" method="post" modelAttribute="healthyBlogVO" enctype="multipart/form-data">
			<ul>
				<li>
					<form:input path="healthy_title" placeholder="제목을 입력하세요"/>
					<form:errors path="healthy_title" cssClass="error-color"/>
				</li>
				<li>
					<form:textarea path="healthy_content" placeholder="내용을 입력하세요"/>
					<form:errors path="healthy_content" cssClass="error-color"/>
					<script>
						 function MyCustomUploadAdapterPlugin(editor) {
							    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							        return new UploadAdapter(loader);
							    }
							}
						 
						 ClassicEditor
				            .create( document.querySelector( '#content' ),{
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
				<li>
					<form:label path="upload">파일업로드</form:label>
					<input type="file" id="upload" name="upload">
				</li>
				
		</ul>
		<div class="align-center">
			<form:button class="default-btn">전송</form:button>
			<input type="button" class="default-btn" value="목록" onclick="location.href='list'">
		</div>
	
		</form:form>

</div>