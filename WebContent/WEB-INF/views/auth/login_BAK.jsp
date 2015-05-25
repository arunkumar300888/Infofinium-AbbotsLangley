<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- <title>Bentley Wood Login</title> -->
<!-- <title>ICSA</title> -->
<title>Ainscough Vanguard</title>
<link href="resources/style/loginstyle.css" rel="stylesheet"
	type="text/css" media="screen">
<link rel="stylesheet" type="text/css"
	href="resources/Extn/styleDSJV.css" />
<link rel="stylesheet" type="text/css" href="resources/style/style.css">
<link rel="stylesheet" type="text/css"
	href="resources/style/jquery-ui-1.8.4.custom.css">
<script src="resources/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src='resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>

<script type="text/javascript">
	window.onload = function() {
		//document.getElementById("userName").focus();
		$('#userName').focus();
	};

	/* 
	function validate() {
		var uid = document.getElementById("userName").value;
		var pwd = document.getElementById("password").value;
		if (uid == "" || pwd == "") {			
			//alert("Please enter the correct Username and Password");
			dialogTemplate("Login Failure","Please enter the correct Username and Password");
			return false;
		} else {
			 // debugger;
			document.form.action = "login";
			document.form.submit();
			return true;
		}
	} */

	$(function() {
		$('#loginBtn')
				.click(
						function() {
							var uid = $("#userName").val();
							var pwd = $("#password").val();
							if (uid == "" || pwd == "") {
								dialogTemplate("Login Failure",
										"Please enter the correct Username and Password");
								return false;
							}
						});

	});

	//Change Paswword
	function changePasswordClicked() {
		//  alert("Inside");
		$("#changePassword").attr("action", "changepassword");
		$("#changePassword").submit();
	}

	function forgotPasswordClicked() {
		//  alert("Inside");
		$("#forgotPassword").attr("action", "forgotpassword");
		$("#forgotPassword").submit();
	}

	function dialogTemplate(title, value) {

		var NewDialog = $("<div id='MenuDialog' title='"+title+"'>\<p>\<span class='ui-icon ui-icon-alert'" +
			" style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>" + "<b>" + value + "</b></p>\</div>");
		NewDialog.dialog({
			resizable : false,
			width : 300,
			height : 140,
			modal : true,
			buttons : {
				"Ok" : {
					text : 'Ok',
					class : 'butn',
					width : '40px',
					click : function() {
						$(this).dialog('destroy').remove();
						$('#userName').focus();
						return false;

						//$(this).dialog("close");		
					}
				}
			}
		});
	}
</script>

<style>
body {
	background: none;
}
</style>
</head>
<body>

	<div class="loginpage">
		<div class="login_logo" style='padding:4px;'> 
			<!-- <img src="resources/images/login_bentley_logo.jpg" align="center" /> -->
			<!-- <img src="resources/images/logo_Login_BWood.jpg" align="center"'/> -->
			<!-- <img src="resources/Extn/img/logo_Login_BWood.jpg" align="center"'/> -->
		   <!--  <img src="resources/Extn/img/logo_icsabanner.jpg" align="center"'/> -->
		  <!--   <img src="resources/Extn/img/ logo_ICSA.gif" align="center"'/>  -->
		   <!--  <img src="resources/Extn/img/nhs_logo.jpg" align="center" height="55px" /> -->
		    <img src="resources/Extn/img/logo_ainscough.jpg" align="center"' height="90px"/>  
		  
		</div>
		<div class="loginbg">
			<form action="login" method="post"
				style="padding: 35px 15px 15px 15px;">
				<c:choose>
					<c:when
						test="${message == 'Please enter the correct Username and Password'}">
						<div style="color: red;">
							<center>${message}</center>
						</div>
					</c:when>
					<c:otherwise>
						<div style="color: red;">
							<center>${message}</center>
						</div>

					</c:otherwise>
				</c:choose>
				<table width="250" cellpadding="5" cellspacing="5">
					<tr>
						<td class="text" align="left">User Name</td>
						<td><input id="userName" name="userName" type="text"
							placeholder="Enter the User Name" /></td>
					</tr>
					<br>
					<tr>
						<td class="text" align="left">Password</td>
						<td><input type="password" id="password" name="password"
							placeholder="**********" /></td>
					</tr>
				</table>
				<table width="250" cellpadding="5" cellspacing="5">
					<tr>
						<td align="center" style="padding-top: 10px;" colspan="0"><input
							name="login" id="loginBtn" style="border: none;"
							src="resources/images/loginbutton.png" type="image" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div align="center" class="loginlink">
			<table>
				<tr>
					<td>
						<form id="changePassword" method="get">
							<a href="#" onclick="changePasswordClicked();">Change
								Password</a>
						</form>
					</td>
					<td><span style="padding: 0px 20px;">|</span></td>
					<td><form id="forgotPassword" method="get">
							<a href="#" onclick="forgotPasswordClicked();">Forgot
								Password</a>
						</form></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
