<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Security Group</title>
</head>
<body>
<div class="bg_title"><span class="grid-box-header"> ${title} </span></div>
    <form method="post" id="createNewWorkflowModelFrm" name="createNewWorkflowModelFrm">
    <input type="hidden" id="modelComboId" name="modelComboId" value="${modelComboId}">
      <table cellspacing="7">
            <tr>
              <td class="label">Workflow Models</td>
              <td class="star">*</td>
              <td>
              <select name="modelName" id="modelName"  class="text" >
								<option value="-1" selected="selected">-SELECT-</option>
									<%-- <c:forEach var="i" items="${workflowModels}"> --%>
									<option value="No Workflow">No Workflow</option>
									<%-- 	<option value="${i.name}">${i.name}</option> --%>
									<%-- </c:forEach> --%>
								</select></td>
             
               <td><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
             <tr>
              <td class="label">Description</td>
              <td class="star">*</td>
              <td><input name="description" id="description" maxlength="99"   type="text" class="text" /></td>
            </tr>
              <tr>
              <td class="label">Select Doc Type</td>
              <td class="star">*</td>
              <td><select name="doctype" id="doctype" class="text"  >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${doctypesList}">
			  <option value="${i.id}">${i.doctypeName}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Create" onclick="workflowModelMapCreation();" />
              <input name="" type="reset" class="butn" value="Reset" />
              <input type="button" class="butn" value="Back" onclick="showModelCombo();"/>
              </td>
            </tr>
          </table>
     </form>
   

</body>
</html>