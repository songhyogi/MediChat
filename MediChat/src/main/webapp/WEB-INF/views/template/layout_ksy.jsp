<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<tiles:insertAttribute name="bootstrap" ignore="true"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ksy.css" type="text/css">
<tiles:insertAttribute name="css" ignore="true"/>
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header"/>
	</div>
	<div class="custom-container pt-4 border">
		<div id="main_body" class="row">
			<div class="col-9 border-end">
				<tiles:insertAttribute name="body"/>
				<div id="main_footer">
					<tiles:insertAttribute name="footer"/>
				</div>
			</div>
			<div id="main_right_nav" class="col-3">
				<tiles:insertAttribute name="right_nav"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>