<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<tiles:insertAttribute name="css" ignore="true"/>
</head>
<body>
<div>
	<div>
		<tiles:insertAttribute name="header"/>
	</div>
	<div class="custom-container p-5 border">
		<div id="main_body" class="row">
			<div class="col-9 border-end">
				<tiles:insertAttribute name="body"/>
			</div>
			<div class="col-3">
				<tiles:insertAttribute name="right_nav"/>
			</div>
		</div>
	</div>
	<div>
		<tiles:insertAttribute name="footer"/>
	</div>
</div>
</body>
</html>