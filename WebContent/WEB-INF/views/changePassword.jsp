<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>Change Password</title>
  <link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="resources/style/wizart/style.vali.css" />
  <link rel="stylesheet" href="resources/style/wizart/style.css" />
  <link rel="stylesheet" type="text/css" href="resources/style/jquery-ui.css" />
 	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="resources/js/jquery-ui-1.8.20.custom.min.js"></script>
</head>
<body>
<div id="wrapper">
<header id="header1">				
				<span class="logo float-left">
					<img src="resources/images/wizart/logo.png"/>
				</span>
				</header>
				<div class="clear"></div>
<div class="inner-box" id="header2">

		<div style="height: 350px">
		
		    <div id="top" class="my-document complete box-content">
	
	
			<span  class="float-left grid-box-header top3">Change Password</span> 
			<div align="right">
			<input type="button" class="download" value="Back"  onclick="logoutClicked();"/>  <!-- history.back() -->
			<form id="logoutFrm" method="get"></form>
			</div>
		
</div>
<div align="right" style="padding: 8px;"><font class="mand"> * Mandatory field</font>	</div>
		
			
    <div id="changePasswordScreen" align="center">
	<form method="post" id="frmChangePassword" action="updatePassword" target="hiddenNewFrame">

					<table cellspacing="8">
					<tr>
						<td class="label">User Name:</td>
						<td><em class="mand"> * </em> <input type="text" id="userName" 
								name="userName" class="text" size="28" maxlength="28"></td>
								
								<td class="mand"
					valign="bottom"></td>
						</tr>
						<tr>
							<td class="label">Old Password:</td>
							<td><em class="mand"> * </em> <input type="password" id="oldPassword" 
								name="oldPassword" class="text" size="28" maxlength="18"></td>	
								<td align="left"><img title="Please enter your old password carefully." src="resources/images/comment_question.png"  />
								</td>							
						</tr>

						<tr>
							<td class="label">New Password:</td>
							<td><em class="mand"> * </em> <input type="password" id="password"
								name="password" size="28" maxlength="18" class="text">
								</td>
								<td align="left"><img title="Please select new password carefully. Length must be between 6 to 18 characters. Only characters a-z , A -Z, 0 -9 , - and _ are allowed." src="resources/images/comment_question.png"  />
								</td>

						</tr>
						<tr>
							<td class="label">Confirm New Password:</td>
							<td><em class="mand"> * </em><input type="password" id="confirmPassword"
								name="confirmPassword" size="28" maxlength="18" class="text">
								</td>
								<td align="left"><img title="Please select confirm password carefully. Length must be between 6 to 18 characters. Only characters a-z , A -Z, 0 -9 , - and _ are allowed." src="resources/images/comment_question.png"  /></td>
						</tr>
						<tr>
						<td></td>
						<td>
					<div style="color: red;">${message}</div></td>
						</tr>
						<tr>
							<td></td>
							<td align="center">
									<input type="submit" class="download" id="btnUpdate" value="Update" > 
									<input type="reset"  class="download" value="Clear Screen">
							</td>
						</tr>
					</table>
				</form>
				</div>
				
<div id="hiddenDivChangePwd">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame" style="display: none">
	</iframe>
</div>	

<div id="changePasswordDiv">
<div id="changePasswordDialog" title="Change Password Result">			
<p><span class='ui-icon ui-icon-check' style='float: left; margin: 0 7px 20px 0;'></span><b>Password updated successfully</b></div></div>
</div>		
		
		<div class="clear"></div>
	
</div>
 <jsp:include page="include/footer.jsp" />
 </div>

 <script type="text/javascript">
 
/*  onclick="validate(this.form);" */
 $(function(){		 
	    $('#changePasswordDiv').hide(); 
		$('#oldPassword').val('');	 
		$('#btnUpdate').click(function(){			
		    //alert("Inside");
			var oldpassword=$('#oldPassword').val();
			var password=$('#password').val();
			var confirmpassword=$('#confirmPassword').val();
			var username=$('#userName').val();
			//alert("Pass:" +password + "Confirm:" +confirmpassword + "OLD:" +oldpassword);	
			if(username==""||oldpassword=="" || password=="" || confirmpassword==""){
				//alert("In");
				dialogTemplate("Change Password", "All fields are mandatory");
			    return false;
			}
			
			else if(password!=confirmpassword){
				 dialogTemplate("Change Password", "Your passwords do not match. Please type more carefully.");
				 $('#password').val('');	 
			     $('#confirmPassword').val('');	
				    return false;
			}
						
			if(oldpassword==password){
				$('#oldPassword').val('');	 
				$('#password').val('');	 
				$('#confirmPassword').val('');	 
				 dialogTemplate("Change Password", "Old & new password are same");
				    return false;				
			}
			
			passwordRegex = /^[a-zA-Z0-9_-]{6,18}$/g;
			confirmpasswordRegex=/^[a-zA-Z0-9_-]{6,18}$/g;
			if (!passwordRegex.test(password) == true) {
					dialogTemplate("Change Password",
					"Your password must be between 6 - 8 characters in length. Special characters are not allowed. Please see the help tip for more information.");
					$('#password').val('');
					$('#confirmPassword').val('');		
			        return false;							
			}
			
			if (!confirmpasswordRegex.test(confirmpassword) == true) {
				//alert("In CP");
				dialogTemplate("Change Password",
				"Your confirm password must be between 6 - 8 characters in length. Special characters are not allowed. Please see the help tip for more information.");
				$('#confirmPassword').val('');	
				$('#password').val('');
		        return false;							
		}
			
		});
		
	});  
	
  function dialogTemplate(title,value){
//	alert("inside dt");
	var NewDialog = $("<div id='MenuDialog' title='"+title+"'>\<p>\<span class='ui-icon ui-icon-alert'" +
			" style='float: left; margin: 0 7px 20px 0;'> " +
			"</span>" +
			"<b>" + value + "</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 400,
		height : 150,
		modal : true,
		buttons : {
			"Ok" : {
				text : 'Ok',
				click : function() {	
					 $(this).dialog('destroy').remove();
					 return false;	
				}
			}
		}
	});	
	}
  
  
  function logoutClicked() {
		$("#logoutFrm").attr("action", "logout");
		$("#logoutFrm").submit();
	}
  
  function changePasswordCompleted(result) {
		if (result == 1) {
			/* var ChangePasswordDialog = $("<div id='MenuDialog' title='Change Password Result'>\<p>\<span class='ui-icon ui-icon-check' "
					+ "style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>Password updated successfully<b></div>");

			ChangePasswordDialog.dialog({
				resizable : false,
				width : 300,
				height : 140,
				modal : true,
				closeOnEscape : false,
				buttons : {
					"Ok" : {
						text : 'Ok',
						class : 'butn',
						width : '40px',
						click : function() {
							$(this).dialog("close");
							logoutClicked();
							
						}
					}
				}
			}); */
			
			 $("#changePasswordDialog").dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				closeOnEscape : false,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							logoutClicked();
							
						}
					}
				}
			});
			removeCloseButton();
		}
		
		else if(result==2){
			$('#oldPassword').val('');
			$('#password').val('');
			$('#confirmPassword').val('');	
			dialogTemplate("Change Password Result", "Old password you entered was wrong");
		}
	   else if (result==3){
		    $('#userName').val('');
			$('#oldPassword').val('');
			$('#password').val('');
			$('#confirmPassword').val('');
			dialogTemplate("Change Password Result", "Please enter the correct Username and Password");
			
		}
		else {
			dialogTemplate("Change Password Result", "Password not updated");
		}
	}
  
  
  function removeCloseButton() {
		$('a.ui-dialog-titlebar-close').remove();
	}
 </script>

</body>
	</html>
	
	
				


					
			