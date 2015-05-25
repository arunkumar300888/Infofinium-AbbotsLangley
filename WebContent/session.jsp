<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
       <% String url =  request.getContextPath();

%>
<Style type="text/css">
h2{
line-height: 22px;
	font: normal 20px Verdana; 
	color: #FF0000; 
	
	}
	h2 a
	{
	text-decoration: blink;
	}
</Style>

<meta HTTP-EQUIV="REFRESH" content="10; url=<%=url%>">
<title>Auto Page Redirect</title>
<link rel="stylesheet" type="text/css" href="resources/style/wizart/style.css" />
</head>
<body>

<div class="main">
<div align="center">
<br /><br />	
<h2>You are already logged in to another session,<br />please select the link to logout of the previous session and activate this current session, or wait for 10 sec to be redirected.<a href="<%=url%>">click here</a></h2>
</div></div>
</body>
</html>