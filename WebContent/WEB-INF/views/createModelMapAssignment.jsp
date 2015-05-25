<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Workflow Model Assignment</title>
</head>
<body>
<div class="bg_title"><span class="grid-box-header"> ${title}->${doctype}->${modelName}</span></div>
    <form method="post" id="createNewModelAssignmentFrm" name="createNewModelAssignmentFrm">
    <input type="hidden" id="modelComboId" name="modelComboId" value="${modelComboId}">
    <input type="hidden" id="doctypeName" name="doctypeName" value="${doctype}">
    <input type="hidden" id="modelName" name="modelName" value="${modelName}">
    
      <table cellspacing="7">
           <tr>
              <td class="label">Doctype Name</td>
              <td class="star"></td>
              <td class="label">${doctype}</td>
            </tr>
            
            <tr>
              <td class="label">Model Name</td>
              <td class="star"></td>
              <td class="label">${modelName}</td>
            </tr>
          <%--  <tr>
              <td class="label">Doctype Name</td>
              <td class="star">*</td>
              <td><input name="doctypeName" id="doctypeName" value="${doctype}" readonly="readonly" type="text" class="txt" style="width: 192px;"/></td>
            </tr>
            
            <tr>
              <td class="label">Model Name</td>
              <td class="star">*</td>
              <td><input name="modelName" id="modelName" value="${modelName}" readonly="readonly" type="text" class="txt" style="width: 192px;"/></td>
            </tr> --%>
      
       		<tr>
              <td class="label">Select Company</td>
              <td class="star">*</td>
              <td><select name="companyModel" id="companyModel" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${companyList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
               <td ><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
      
            <tr>
              <td class="label">Select Department</td>
              <td class="star">*</td>
              <td><select name="projectModel" id="projectModel" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${projectList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
               <td ><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Select Area</td>
              <td class="star">*</td>
              <td><select name="categoryModel" id="categoryModel" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${categoryList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <%-- <tr>
              <td class="label">Select Discipline</td>
              <td class="star">*</td>
              <td><select name="disciplineModel" id="disciplineModel" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${disciplineList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select Site</td>
              <td class="star">*</td>
              <td><select name="siteModel" id="siteModel" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${siteList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr> --%>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Create" onclick="modelAssignementCreation();" />
              <input name="" type="reset" class="butn" value="Reset" />
               <input type="button" class="butn" value="Back" onclick="modelAssignmentClicked('${modelComboId}');"/>
              </td>
              
            </tr>
          </table>
     </form>
     
     
</body>
</html>