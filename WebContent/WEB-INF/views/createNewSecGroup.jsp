<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<script type="text/javascript">
$(document).keypress(
	    function(event){
	     if (event.which == '13') {
	        event.preventDefault();
	     }

	});
</script>
<title>Security Group</title>

</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>

    <form method="post" id="createNewSecGroupFrm" name="createNewSecGroupFrm">
    <input type="hidden" id="secGroupId" name="secGroupId" value="${secGroupId}">
      <table cellspacing="7">
            <tr>
              <td class="label">Security Group Name</td>
              <td class="star">*</td>
              <td><input name="secGroupName" id="secGroupName" maxlength="44" type="text" value="${secGroupName}" class="text" style="width: 196px;"/></td>
               <td><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Save" onclick="securityGroupCreation();" />
              <input name="" type="reset" class="butn" value="Reset" />
               <input name="" type="button" class="butn" value="Back" onclick="showSecurityGroups();"/>
              </td>
              
            </tr>
          </table>
     </form>
   

</body>
</html>