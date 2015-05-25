<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Document Template</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
    <form method="post" id="createNewTemplateFrm" name="createNewTemplateFrm"  enctype="multipart/form-data" target="hiddenDivTemplate">
   <%--  <input type="hidden" id="templateId" name="templateId" value="${templateId}"> --%>
      <table cellspacing="7">
            <tr>
              <td class="label">Select Project</td>
              <td class="star">*</td>
              <td><select name="projectTemplate" id="projectTemplate" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${projectList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
               <td ><div id="result" style="color: red;" align="right">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Select Category</td>
              <td class="star">*</td>
              <td><select name="categoryTemplate" id="categoryTemplate" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${categoryList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select Discipline</td>
              <td class="star">*</td>
              <td><select name="disciplineTemplate" id="disciplineTemplate" class="text" style="width: 198px;">
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${disciplineList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select Site</td>
              <td class="star">*</td>
              <td><select name="siteTemplate" id="siteTemplate" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${siteList}">
			  <option value="${i.id}">${i.value}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select Doc Type</td>
              <td class="star">*</td>
              <td><select name="doctypeTemplate" id="doctypeTemplate" class="text" style="width: 198px;" >
              <option value="-1" selected="selected">-SELECT-</option>
              <c:forEach var="i" items="${doctypesList}">
			  <option value="${i.id}">${i.doctypeName}</option>
			 </c:forEach>
              </select></td>
            </tr>
            <tr>
              <td class="label">Select File</td>
              <td class="star">*</td>
              <td><input name="templateFile" id="templateFile" type="file" class="text" /></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Upload Template" onclick="uploadTemplateClicked();"/>
              <input name="" type="reset" class="butn" value="Reset" />
              <input name="" type="button" class="butn" value="Back" onclick="showTemplates();"/>
              </td>
            </tr>
         
          </table>
     </form>
     
     <div id="templateUploading"  hidden="true" title="Please wait while uploading.." align="center" style="padding-top: 6px">
        <img align="middle" src="resources/images/progress_bar.gif" alt="" />
	</div> 	
			<div align="left" id="templateUploadResult"></div>			
						  <div id="hiddenDivTemplate">
	<iframe id="hiddenDivTemplate" name="hiddenDivTemplate" style="display: none">
	</iframe>
</div>
     
     
</body>
</html>