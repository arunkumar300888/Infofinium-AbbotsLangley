<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>Forgot Password</title>
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
				<span  class="float-left grid-box-header top3">Forgot Password</span>
			<div align="right">
                           <input type="button" class="download" value="Back"  onclick="logoutClicked();"/>
                          <!-- <img border="0"
							title="Home" alt="Home" src="resources/images/back4.png"
							class="pointer" onclick="history.back();" /> --></td>

					<%-- 	<td align="right" style="font-weight: bold; font-size: 14px;"><span>Welcome</span>
							&nbsp;<span style="text-transform: uppercase">${username}</span>
						</td> --%>
						
				<form id="logoutFrm" method="get"></form>
			</div>
		</div>	
		  <div align="right" style="padding: 8px;"><font class="mand"> * Mandatory field</font>	</div>  
			
			
      <div id="forgotPasswordScreen" align="center">
	<form method="post" id="frmForgotPassword" action="requestPassword" target="hiddenNewFrame">
					<table cellspacing="8">
						<tr>
						<td class="label">Select User:</td>
							<td><em class="asterisk"> * </em> <select id="userName" name="userName" class="text">
				<option value="-1" selected="selected">--Select--</option>
					<c:forEach var="i" items="${userLists}">.
							<option title="${i.userName}" value="${i.userName}">${i.userName}</option>
						</c:forEach>
				</select></td>
				</tr>
						<tr>
							<td class="label">Email ID:</td>
							<td><em class="asterisk"> * </em> <input type="text" id="emailId" 
								name="emailId" class="text" size="33" maxlength="40"></td>	
								<td align="left"><img title="Please enter email id carefully." src="resources/images/comment_question.png"  />
								</td>							
						</tr>						
						<tr>
						<td></td>
						<td>
					<div style="color: red;">${message}</div></td>
						</tr>
						<tr>
							<td></td>
							<td align="center">
									<input type="submit" class="download" id="btnSend" value="Send" > 
									<input type="reset" name="clear" class="download" value="Clear Screen">
							</td>
						</tr>
					</table>
				</form>
				</div>
				
<div id="hiddenDivForgotPwd">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame" style="display: none">
	</iframe>
</div>	
<div id="forgotPasswordDiv">
<div id="forgotPasswordDialog" title="Forgot Password Result">			
<p><span class='ui-icon ui-icon-check' style='float: left; margin: 0 7px 20px 0;'></span><b>Password sent successfully</b></div></div>
		</div>
		<div class="clear"></div>
	</div>
	 <jsp:include page="include/footer.jsp" />
 </div>

 <script type="text/javascript">
 
/*  onclick="validate(this.form);" */
 $(function(){	 
	    $('#forgotPasswordDiv').hide(); 
		$('#userName').val('');	 
		$('#btnSend').click(function(){			
		    //alert("Inside");
			var username=$('#userName').val();
			var emailid=$('#emailId').val();
			
			//alert("Pass:" +password + "Confirm:" +confirmpassword + "OLD:" +oldpassword);	
			if(username==""|| emailid==""){
				//alert("In");
				dialogTemplate("Forgot Password", "All fields are mandatory");
			    return false;
			}
			
			if(username==-1){
				dialogTemplate("Forgot Password", "Please select a valid user name");
			    return false;
			}
			loadProgress();
			
		});	
	});  
	
  function dialogTemplate(title,value){	
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
  
  function forgotPasswordCompleted(result) {
	  removeProgress();
		if (result == 1) {
			/* var ForgotPasswordDialog = $("<div id='MenuDialog' title='Forgot Password Result'>\<p>\<span class='ui-icon ui-icon-check' "
					+ "style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>Password sent successfully<b></div>");

	 ForgotPasswordDialog.dialog({
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
			});  */
			 $("#forgotPasswordDialog").dialog({
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
			$('#userName').val('');	
			dialogTemplate("Forgot Password Result", "Please enter correct username");
		}
	   else if (result==3){
		    $('#userName').val('');
			$('#emailId').val('');
			dialogTemplate("Forgot Password Result", "Please enter the correct Username and EmailId");
		}
		else {
			dialogTemplate("Forgot Password Result", "Password not Sent");
		}
	}
  
  
  function removeCloseButton() {
		$('a.ui-dialog-titlebar-close').remove();
	}
  
  function loadProgress(){	
		var ProgressDialog = $("<div id='ProgressBarDialog' title='Please wait...' align='center' style='padding-top: 6px'><img align='middle' src='resources/images/progress_bar.gif' /></div>");
		ProgressDialog.dialog({
			height : 85,
			width : 224,
			modal : true,
			closeOnEscape: false,
			resizable: false,
			draggable: false
		});		
	}
  
	function removeProgress(){
		 $('#ProgressBarDialog').dialog('destroy').remove();
	}
	
 </script>
</body>
	</html>
	
	
				


					
