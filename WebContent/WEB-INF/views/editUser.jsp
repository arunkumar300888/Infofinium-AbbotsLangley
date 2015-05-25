<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<!-- <link href="resources/Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script> -->
<title>User Updation</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title} -> ${userData.userName}	</span>	
	
 </div>
		<form method="post" id="updateUserFrm" name="updateUserFrm">
		  <input type="hidden" id="userId" name="userId" value="${userData.id}">
 <table cellspacing="7">
            <tr>
              <td class="label">E-Mail ID</td>
              <td class="star">*</td>
              <td><input id="updateEmail" name="updateEmail" type="text" class="text" maxlength="44" style="width: 235px;" value='${userData.emailId}'/>
              	  <img title="Please enter email id carefully." src="resources/images/comment_question.png"  />
              </td>
            </tr>
              <tr>
              <td class="label">Mobile No</td>
              <td class="star">*</td>
              <td><input id="updatemobileNo"  name="updatemobileNo" type="text" class="text" maxlength="10" style="width: 235px;" value='${userData.mobileNo}'/>
              	  <img title="Please enter mobile number carefully.Special characters are not allowed" src="resources/images/comment_question.png"  />
              </td>	
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input type="button" class="butn" value="Update"  onclick="userUpdation('${userData.id}');"/> 
              <input type="button" class="butn" value="Back" onclick="showUsers();"/>
              </td>
            </tr>
          </table>
          </form>
</body>
</html>
