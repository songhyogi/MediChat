<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ych.css" type="text/css">
<tiles:insertAttribute name="font" ignore="true"/>
<tiles:insertAttribute name="css" ignore="true"/>
<tiles:insertAttribute name="bootstrap" ignore="true"/>
<style>
.container{
	margin-top:30px;
	margin-bottom: 10px;
	border:none;
}
#main_leftNav{
	margin-top:5%;
	background-color:#fff;
}
#main_body {
    width: 800px; 
    height: 600px;
    position: relative;
    margin-top:5%;
    background-color:#fff;
    overflow-y: scroll;
}
#main_body::-webkit-scrollbar {
	display: none;
}
#background{
	background-color:rgba(57, 174, 164, 0.21);
	position:relative;
	width:auto;
	height:300px;
}
</style>
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header"/>
	</div>
	<div id="background">
	<div class="custom-container row d-flex justify-content-center pt-4">
		<div id="main_leftNav" class="col-3">
			<tiles:insertAttribute name="navDoc"/>
		</div>
		<div id="main_body" class="col-9">
			<tiles:insertAttribute name="body"/>
		</div>
		<div id="main_footer">
			<tiles:insertAttribute name="footer"/>
		</div>

		</div>
</div>
</div>
</body>
</html>