<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Security Group Assignment</title>
</head>
<body>
<div class="bg_title">
<span class="grid-box-header">${title}</span>	
</div>
    <form method="post" id="createNewSecGroupAssignmentFrm" name="createNewSecGroupAssignmentFrm">
    <input type="hidden" id="secGroupId" name="secGroupId" value="${secGroupId}">
    <input type="hidden" id="secGroupName" name="secGroupName" value="${secGroupName}">
      <table cellspacing="7">
              <tr>
               <td class="label">Security Group Name</td>
               <td class="star"></td>
               <td class="label">${secGroupName}</td>
            </tr>
            <tr>
              <td class="label">Select ${attr1Title }</td>
              <td class="star">*</td>
              <td><select name="projectSec" id="projectSec" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${companyList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
               <td ><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Select ${attr2Title }</td>
              <td class="star">*</td>
              <td><select name="categorySec" id="categorySec" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${departmentList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select ${attr3Title }</td>
              <td class="star">*</td>
              <td><select name="disciplineSec" id="disciplineSec" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${areaList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <%-- <tr>
              <td class="label">Select Site</td>
              <td class="star">*</td>
              <td><select name="siteSec" id="siteSec" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${siteList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr> --%>
            <tr>
              <td class="label">Select Doc Type</td>
              <td class="star">*</td>
              <td><select name="doctypeSec" id="doctypeSec" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${doctypesList}">
			  <option value="${i.id}">${i.doctypeName}</option>
			 </c:forEach>
              </select></td>
            </tr>
          
           <%--  <tr>
              <td class="label">Security Group Name</td>
              <td class="star">*</td>
              <td><input name="secGroupName" id="secGroupName" value="${secGroupName}" readonly="readonly" type="text" class="txt" style="width: 196px;"/></td>
            </tr> --%>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="download" value="Create" onclick="securityGroupAssignmentCreation();" />
              <input name="" type="reset" class="download" value="Reset" />
                <input name="" type="button" class="download" value="Back" onclick="editSecGroupAssignmentClicked('${secGroupId}');"/>
              </td>
            </tr>
          </table>
     </form>
     
     
</body>
</html>