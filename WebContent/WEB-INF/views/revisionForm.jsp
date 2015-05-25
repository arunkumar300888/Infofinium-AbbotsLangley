<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/thickbox.css" media="screen" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>

<script type="text/javascript">

$(function(){
$("#formTargetDate").click(function(){
	//alert("date");
	$("#formTargetDate").datepicker({
		dateFormat : 'dd-mm-yy'
		
	});
});
    
});
</script>
<script type="text/javascript">
	
	function revisionFormBtnClicked() {

		var userFormId = $("#userFormId").val();
		var revisionDate = $("#formTargetDate").val();
		//alert(revisionDate);
		var now = new Date();
		now.setHours(0, 0, 0, 0);
		
		if (revisionDate == "") {
			parent.dialogTemplateLinks("Revision Document", "Enter submission date");
			$('#formTargetDate').focus();
			return false;

		}
		
		else
			{
			
			var n = revisionDate.split("-");
			var revisionDateValid = new Date(n[2], n[1] - 1, n[0]);
			if (revisionDateValid <= now) {
				parent.dialogTemplateLinks("Revision Form",
						"Enter submission date greater than today");
				$('#formTargetDate').focus();
				return false;
			}
			
			else
				{		
				
				
				parent.submitformRevision(userFormId,revisionDate);
				}
			}
		
	//	parent.submitform(modelName, revisionDate,docId);

	}
</script>

<style>
body {
	background: none;
}
</style>
</head>

<body>
<form action="revisionForm" method="get" name="frmRevForm" id="frmRevForm">
	<input type="hidden" id="userFormId"  name="userFormId"  value="${userFormId}">

    <div style="padding:7px 0px 0px 7px">
	<div id="revisionFormPopup" >
		<table>
			<tr valign="top">
				<td title="Submission Date"  class="label" width="123">Submission Date</td>
				<td>
				<input title="Submission Date" type="text"
								name="formTargetDate" id="formTargetDate" placeholder="Submission Date" readonly="true" class="text datepicker"/>
				</td>
				
			</tr>
<tr>
<td><input type="submit" class="butn" value="ok" /></td>
</tr>
		</table>
	</div>
	
	</div>
	</form>
</body>
</html>