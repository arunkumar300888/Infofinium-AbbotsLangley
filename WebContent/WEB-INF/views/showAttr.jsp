<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
$(function() {		
	
	$("#datepicker").datepicker({dateFormat:'dd-mm-yy'});
});
</script>
<div id="showCaseAttrDiv">
	<table border="0" class="value">
		<c:forEach var="i" items="${casesForShow}">
			<tr>
				<td><div style="padding: 4px;">${i.name}</div></td>
				<c:choose>
				<c:when test="${i.type == 'WF_ATTR_DATE'}">
					<td> <fmt:formatDate type="date" pattern="dd-MM-yyyy"  value="${i.value}" /></td>
				</c:when>
				<c:otherwise>
					<td>${i.value}</td> 				
				</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>

	<div align="left" class="showattributebg" >
<table width="400" style="margin: 15px 0 0 17px;" align="left" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td align="left"><h3 style="color: white;">Download Template here </h3>
			<td><input style="padding-right: 15px;" class="showattribute"   type="button" value="Download Template"onclick="downloadTemplateClicked('${caseId}');"></td>
		</tr>
		<tr>
			<td><h3 style="color: white;">Upload your Document</h3></td>
			<td><input class="showattribute" type="button" value="Upload Document" onclick="uploadDocumentClicked();"></td>	
		</tr>
	</table>
	</div>

<div id="uploadDocNew">
	<form id="frmCaseUploadNew" action="" method="post"	enctype="multipart/form-data" target="hiddenNewFrame">
	<input type="hidden" name="caseId" value="${caseId}">
	<table width="100%" style="padding: 15px;" cellpadding="0" cellspacing="0">
			<tr valign="top">
				<td width="80"  align="left">Discipline</td>
				<td align="left"  width="100" style="font: 15px Arial;">
				<select style="width: 200px;" name="discipline" id="discipline">
					
				<c:forEach var="i" items="${disciplines}">
					<option value="${i.id}">${i.disciplineName}</option>
				</c:forEach></select>
				</td>
			</tr>
			<tr>
				<td align="left" class="documentlist">Document Type</td>
				<td  style="font: 15px Arial;" align="left" >
				<select style=" width:200px;" name="documentType" id="documentType">
					<c:forEach var="i" items="${doctypes}">
						<option value="${i.id}">${i.doctypeName}</option>
					</c:forEach></select>								
				</td>
			</tr>
			<tr>
				<td align="left" class="documentlist">SelectFile</td>
				<td   align="left"><input type="file" style=" width:200px;" name="docFile"  id="docFile"></td>
			</tr>
			<tr>
				<td  align="left" class="documentlist">TargetDate </td>
				<td  align="left"><input type="text" id="datepicker"  style=" width:200px;" name="targetDate" value="${tgtDate}"></td>
			</tr>
			  <tr>
				<td align="left" class="documentlist">EbNo</td>
				<td align="left"><input type="text" name="ebNo" style=" width:200px;" id="ebNo" value="${ebn}"></td>
				
			</tr>
			<tr>
			<td><input type="button" class="butn" value="Upload" id="btnGoUpload" onclick="uploadNewDocClicked();">
			
			<input type="button" class="butn" value="Cancel" id="btnCancelUpload" onclick="cancelUploadNewDocClicked();"></td>
			</tr>
		</table>

	</form>
	
</div>
<div id="newDocCrtStatusDiv"></div>
<div id="hiddenDivCreate">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame"
		style="display: none" onload="uploadNewDocCompleted();"> </iframe>
</div>
</div>
