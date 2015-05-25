<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="header3" class="title">Quick Upload</div>    
<div id="quick-upload">			
				<form id="quickUpload" method="post" enctype="multipart/form-data" target="hiddenDivQuickUpload">
					<table cellspacing="8" style="padding-left: 90px;">
					
					<tr valign="top">
				<td class="value">Select Document Type</td>
				<td><select style="width: 190px;" name="documentType"
					id="documentType">
					<c:forEach var="i" items="${doctypes}">
					  
					  <c:if test="${!i.workflow}">  
							<option value="${i.id}">${i.doctypeName}</option>
							</c:if>
							
						</c:forEach>
				</select></td></tr>
						<tr valign="top">
							<td align="left" class="value">Select File</td>
							<td align="left"><input type="file" style="width: 200px;"
								name="quickUpl" id="quickUpl"></td>
						</tr>
						<tr valign="top">

							<td><input type="button" class="butn" value="Quick Upload"
								id="btnQuickUpload" onclick="quickUpload();"
								style="width: 107px;" /></td>
						</tr>
					</table>
				</form>
	
				
			</div>
			<div align="left" id="quickUploadResult"></div>
			
						  <div id="hiddenDivQuick">
	<iframe id="hiddenDivQuickUpload" name="hiddenDivQuickUpload" style="display: none">
	</iframe>
</div>