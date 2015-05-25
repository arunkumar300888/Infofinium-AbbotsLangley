  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <!DOCTYPE HTML>
<html>
<head>	
<link
	href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100'
	rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css"
	href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/wizart/style.css" />





<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>

<script src="../resources/js/ReferenceLinks.js" type="text/javascript"></script>
	<style>
body {
	background: none;
}
</style>
 <script type="text/javascript">
	function reassignOwnerClicked(documentId){
		var assignUserId=$("#reassignOwnerId").val();
		if(assignUserId==-1){
			parent.dialogTemplateLinks("Reassign Owner","Please select a user");
			return false;
		}
		
		//alert(assignUserId);
		//alert("test: "+caseId + ":" +stepId + ":" +assignUserId);
	     parent.loadProgress();
		$.ajax({
			type : "GET",
			url : "../adminTemplate/reassignDocumentOwner"+ "?documentId=" + documentId + "&userId="+assignUserId,
			cache : false,
			success : function(data) {
				parent.tb_remove();
				parent.dialogTemplateLinks("Reassign Owner","Owner reassigned successfully");
				parent.$("#showDynamicContent").html("");
				parent.$("#dynamicContent").html(data);
				parent.$('#documentListReassignOwnerDtbl').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"sScrollY" : "390px",
						"bFilter" : true,
						"bDestroy" : true
					});
				parent.removeProgress();
			//	parent.$("#showDynamicContent").html('');
		     // alert(data);
			},
			error : function(data) {
				parent.dialogTemplateLinks("Reassign Owner", "Failed to reassign owner");
				return false;
			}
		
		});
			
	}
	
 </script>
</head>
	<table cellspacing="8" >	
				   <tr valign="top">
				<td title="Select User"  style="width: 123px;">Select User</td>
				<td><select id="reassignOwnerId" name="reassignOwnerId" class="text" style="width: 213px;">
				<option value="-1" selected="selected">--Select--</option>
					<c:forEach var="i" items="${userLists}">.
							<option title="${i.userName}" value="${i.id}">${i.userName}</option>
						</c:forEach>
				</select></td></tr>
				<tr valign="top">
						<td></td>
							<td><input type="button" class="butn" value="Reassign"
								id="btnReassign" onclick="reassignOwnerClicked('${documentId}');"
								style="width: 100px;" />
								</td>
						</tr>
	</table>