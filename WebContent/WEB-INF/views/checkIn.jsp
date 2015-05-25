
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>	
	<link rel="stylesheet" type="text/css"
	href="../resources/style/wizart/style.css" />
	<style>
body {
	background: none;
}
</style>
</head>


<div class="demo">
	<div id="dialog-modal1" title="Check In Document">
		<form id="frmCheckin1" method="post" enctype="multipart/form-data"
			target="hiddenUploadFrame1">
			<table cellspacing="8">
				<tr>				
				<%--     <c:out value="${documentId}"></c:out>
				    <c:out value="${path}"></c:out>		 --%>	
				
				    
					<td>
					    
					<input type="hidden" name="path" id="path" value="${path}">
						<input type="hidden" name="documentId" id="documentId"
						value="${documentId}"> <input type="hidden" name="url"
						id="url" value="${url}"><input type="file"
						name="docFile123" id="docFile123">
						<input type="hidden" name="stepId" id="stepId" value="${stepId}">
						<input type="hidden" name="adminMeta" id="adminMeta" value="${adminMeta}">						
						</td>
						<c:out value="${workingCopyName}"></c:out>
					<td><input type="button" class="butn" value="Check In"
						id="btnGoCheckin" onclick="parent.goCheckinClicked(frmCheckin1);"
						style="width: 107px;" /></td>
				</tr>
			</table>
		</form>
			
	</div>
</div>




