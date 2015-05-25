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
<title>Role</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}	</span>	
	
</div>

    <form method="post" id="createRoleFrm" name="createRoleFrm">
    <input type="hidden" id="roleId" name="roleId" value="${roleId}">
      <table cellspacing="7">
            <tr>
              <td class="label">Role Name</td>
              <td class="star">*</td>
              <td><input name="roleName" id="roleName" type="text" value="${roleName}" maxlength="40"  class="text" /></td>
              <td><div style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="download" value="Save" onclick="roleCreation();" />
              <input name="" type="button" class="download" value="Back" onclick="showRoles();" />
              </td>
            </tr>
          </table>
     </form>
   

</body>
</html>