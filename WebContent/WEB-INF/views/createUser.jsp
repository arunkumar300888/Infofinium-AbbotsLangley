<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<!-- <script src="resources/js/JmrSdp.js" type="text/javascript"></script> -->
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript">
$(function(){
	   $("select").multiselect(); 
	});
</script>
<title>User Creation</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}	</span>	
	
</div>
		<form method="post" id="createUserFrm" name="createUserFrm">
 <table cellspacing="7">
            <tr>
              <td class="label">User name</td>
              <td class="star">*</td>
              <td><input id="createNewUser" name="createNewUser" type="text"  maxlength="44"  class="text" style="width: 196px;"/></td>
              <td><div id="result" style="color: red;">${result}
				</div>
              </td>
            </tr>
            <tr>
              <td class="label">Password</td>
              <td class="star">*</td>
              <td><input id="createNewPassword" name="createNewPassword" type="password"  maxlength="18" class="text" style="width: 196px;"/>
              	   <img title="Please select password carefully. Length must be between 6 to 18 characters. Only characters a-z , A -Z, 0 -9 , - and _ are allowed." src="resources/images/comment_question.png"  />	
              </td>
            </tr>
            <tr>
              <td class="label">E-Mail ID</td>
              <td class="star">*</td>
              <td><input id="createNewEmail"  name="createNewEmail" type="text" class="text" maxlength="44" style="width: 196px;"/></td>
            </tr>
              <tr>
              <td class="label">Mobile No</td>
              <td class="star">*</td>
              <td><input id="mobileNo"  name="mobileNo" type="text" class="text" maxlength="16" style="width: 196px;"/>
                  <img title="Please enter mobile number carefully.Special characters are not allowed" src="resources/images/comment_question.png"  />
              </td>
            </tr>
            
           
            
              <tr>
              <td class="label">Select Company</td>
              <td class="star">*</td>
              <td>
             <select name="companyId" id="companyId" class="text" style="width: 196px;" multiple="multiple">
								
								<c:forEach var="i" items="${companyList}">
									<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
								</c:forEach>
							</select>
             
              </td>
            </tr>
            
            <tr>
              <td></td>
              <td></td>
              <td><input type="button" class="download" value="Create User"  onclick="userCreation();"/> <input type="reset" class="download" value="Reset" />
               <input type="button" class="download" value="Back" onclick="showUsers();"/>
              </td>
            </tr>
            
            
          </table>
          </form>
</body>
</html>