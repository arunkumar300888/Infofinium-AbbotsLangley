<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
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

<title>Invalid session page</title>
<link rel="stylesheet" type="text/css" href="resources/style/wizart/style.css" />
</head>
<body>

<div class="main">
<div align="center">
<br /><br />	
<h2>${message}</h2>
	<form action="logout1" method="post">

			<input type="hidden" id="userId" name="userId" value="${userId}" />
			<input type="submit" value="Submit" class="butn">
		</form>
</div>
</div>

	<%-- <div
		style="border: 3px solid red; color: #red; padding: 20px; width: 500">
		<b><font size="5" face="Georgia, Arial, Garamond"></font>${message}
		</b>


		<form action="logout1" method="post">

			<input type="hidden" id="userId" name="userId" value="${userId}" />
			<input type="submit" value="submit">
		</form>

	</div> --%>
</body>
</html>