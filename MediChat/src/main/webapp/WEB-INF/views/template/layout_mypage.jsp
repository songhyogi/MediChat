<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>

<tiles:insertAttribute name="font" ignore="true"/>
<tiles:insertAttribute name="bootstrap" ignore="true"/>


</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header"/>
	</div>
	<div class="custom-container row d-flex justify-content-center pt-4 border">
		<div id="main_leftNav" class="col-3">
			<tiles:insertAttribute name="nav"/>
		</div>
		<div id="main_body" class="col-9">
			<tiles:insertAttribute name="body"/>
		</div>
		<div id="main_footer">
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>

</div>
</body>
</html>