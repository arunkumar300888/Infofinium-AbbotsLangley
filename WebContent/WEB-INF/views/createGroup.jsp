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
<title>Group</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>

    <form method="post" id="createGroupFrm" name="createGroupFrm">
    <input type="hidden" id="groupId" name="groupId" value="${groupId}">
      <table cellspacing="7">
            <tr>
              <td class="label">Group Name</td>
              <td class="star">*</td>
              <td><input name="groupName" id="groupName" type="text" value="${groupName}" maxlength="40"  class="text" /></td>
               <td><div style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Save" onclick="groupCreation();" />
              <input name="" type="button" class="butn" value="Back" onclick="showGroups();" />
              </td>
            </tr>
          </table>
     </form>
   

</body>
</html>