<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(function() {
	$("#datepicker").datepicker({dateFormat:'dd-mm-yy'});
});
</script>
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
				<td  style="font: 15px Arial;" align="left" ><select style=" width:200px;" name="documentType" id="documentType">
					<c:forEach var="i" items="${doctypes}">
						<option value="${i.id}">${i.doctypeName}</option>
					</c:forEach></select>								
				</td>
			</tr>
			<tr>
				<td  align="left" class="documentlist">TargetDate</td>
				<td  align="left"><input type="text" id="datepicker"  style=" width:200px;" name="targetDate"></td>
			</tr>
			  <tr>
				<td align="left" class="documentlist">EbNo</td>
				<td align="left"><input type="text" name="ebNo" style=" width:200px;" id="ebNo"></td>
			</tr>
			<tr>
				<td align="left" class="documentlist">SelectFile</td>
				<td   align="left"><input type="file" style=" width:200px;" name="docFile"  id="docFile"></td>
			</tr>
			<tr>
			<td><input type="button" class="post" value="Upload" id="btnGoUpload" onclick="uploadNewDocClicked();">
			
			<input type="button" class="post" value="Cancel" id="btnCancelUpload" onclick="cancelUploadNewDocClicked();"></td>
			</tr>
		</table>

	</form>
	
</div>
<div id="newDocCrtStatusDiv"></div>
<div id="hiddenDivCreate">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame"
		style="display: none" onload="uploadNewDocCompleted();"> </iframe>
</div>