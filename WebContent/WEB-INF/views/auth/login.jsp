<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html" />
  <meta name="author" content="" />
  <title>InfoFinium Login</title>
  <link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="resources/style/wizart/style.vali.css" />
  <link rel="stylesheet" href="resources/style/wizart/style.css" />
  <link rel="stylesheet" type="text/css" href="resources/style/jquery-ui.css" />
  
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="resources/js/jquery-ui-1.8.20.custom.min.js"></script>

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
	
	//Forgot Password
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
			width : 370,
			height : 150,
			modal : true,
			buttons : {
				"Ok" : {
					text : 'Ok',
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
  
</head>
<body class="login-page">
<div class="mask">
  <div class="top">
    <div class="top-wrapper">
      <p>INFORMATION GOVERNANCE FRAMEWORK</p>
      <p>&trade;</p>
    </div>      
  </div>

  <div class="content">
    <div class="hcenter">
      <div class="left-form">
        <div class="login-form">
          
             <h2>User Login</h2> 
             <div class="form-inputs">
                  <form action="login" method="post"> 
                  
                    <img class="login-logo" src="resources/images/wizart/login-logo.png" alt="Info FINIUM"/>
                    
                    <div class="username">
                      <p>User Name</p><input id="userName" name="userName"  type="text"
							placeholder="Enter the User Name" title="Enter the User Name" />
                    </div>
                    <div class="input-drop-shadow"></div>

                    <div class="password">
                      <p>Password</p><input type="password" id="password" name="password" 
							placeholder="**********" title="Enter the Password"/>
                    </div>
                    
                    <div class="input-drop-shadow"></div>
                <div style="Color:red; font-size: 11px;">
                    <c:choose>
						<c:when
						test="${message == 'Incorrect Username or Password'}">
						
							<center>${message}</center>
						
					</c:when>
					<c:otherwise>
						
							<center>${message}</center>
						

					</c:otherwise>
				</c:choose>
				</div>
				<div align="right">
                    <table><tr>
                    <td>
                    <!-- <input type="checkbox" name="thirdPartyUser"  id="thirdPartyUser" style="display: inline;">
                    <label for="thirdPartyUser" class="checkbox">Third Party</label></td> -->
                    <td><input name="login" id="loginBtn" type="submit" value="Login" class="butn" />		</td>
							<td style="width: 40px;">&nbsp;</td>
                    </tr>
                    </table>
                    </div>
                    
                  </form>
                  
                  
                  <div class="login-links" align="right">
                  <table><tr><td>
                  <form id="changePassword" method="get">
							<a href="#" onclick="changePasswordClicked();">Change Password</a></form></span>
                        </td><td>|</td>
                        <td><form id="forgotPassword" method="get">
							<a href="#" onclick="forgotPasswordClicked();">Forgot Password</a></form></td>
							</tr>
							</table>
                  </div>
                  <div class="clearFix"></div>
             </div>

            

        </div>
      </div>
     <!--  <div class="right-side">
          <span>Good Morning</span>
          <div class="space"></div>
          <span>Have a Great Day!</span>
          <div class="clearFix"></div>
          <div class="change-wallpaper">Change Wallpaper</div>
      </div> -->
    </div>
  </div>

</div>

</body>
</html>