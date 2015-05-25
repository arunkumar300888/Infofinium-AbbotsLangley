<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Group Role</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
    <form method="post" id="createGroupRoleFrm" name="createGroupRoleFrm">
     <input type="hidden" id="userId" name="userId" value="${userId}">
    <table cellspacing="7">
            <tr>
              <td class="label">Select Group</td>
              <td class="star">*</td>
              <td><select id="group" name="group" class="text" style="width: 198px;">
              <option value="-1" selected="selected">--Select--</option>
              	<c:forEach var="i" items="${groups}">
							<option title="${i.groupName}" value="${i.id}">${i.groupName}</option>
						</c:forEach>
              </select></td>
              <td><div id="result" style="color: red;">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Select Role</td>
              <td class="star">*</td>
              <td><select id="role" name="role" class="text" style="width: 198px;">
              <option value="-1" selected="selected">--Select--</option>
              	<c:forEach var="i" items="${roles}">
							<option title="${i.roleName}" value="${i.id}">${i.roleName}</option>
						</c:forEach>
              </select></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Create" onclick="groupRoleCreation();"/>
              <input name="" type="reset" class="butn" value="Reset" />
              <input type="button" class="butn" value="Back" onclick="userGroupRoleClicked('${userId}');"/>
              </td>
            </tr>
   
          </table>
   </form>

</body>
</html>