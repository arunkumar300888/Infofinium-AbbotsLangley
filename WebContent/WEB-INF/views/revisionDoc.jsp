<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/jquery-ui.css" />

<link rel="stylesheet" type="text/css" href="../resources/style/thickbox.css" media="screen" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>

<script type="text/javascript">
	function getdata() {

		var modelName = $("#modelname").val();
		if(modelName==-1)
			{
			$("#modelrevision").html("");
			return false;
			}
		
		//alert(modelName);

		$.ajax({
			type : "GET",
			url : "editattrcloned?modelName=" + modelName,
			cache : false,
			success : function(data) {
				$("#modelrevision").html(data);

			},
			error : function(data) {
				// alert('failure');
			}
		});

	}

	function submitform() {

		var modelName = $("#modelname").val();
		var revisionDate = $("#datepickerrev").val();
		var docId = $("#docid").val();
		var now = new Date();
		now.setHours(0, 0, 0, 0);
		
		if (revisionDate == "") {
			parent.dialogTemplateLinks("Revision Document", "Enter submission date");
			$('#datepickerrev').focus();
			return false;

		}
		
		else
			{
			
			var n = revisionDate.split("-");
			var revisionDateValid = new Date(n[2], n[1] - 1, n[0]);
			if (revisionDateValid <= now) {
				parent.dialogTemplateLinks("Revision Document",
						"Enter submission date greater than today");
				$('#datepickerrev').focus();
				return false;
			}
			
			else
				{				
				parent.submitform(modelName, revisionDate,docId);
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

	<input type="hidden" id="docid"  name="docid"  value="${docId}">

    <div style="padding:7px 0px 0px 7px">
	<div id="modelselrevision" >
		<table>
			<tr valign="top">
				<td title="Choice of Workflow"  class="label" width="123">Select Workflow</td>
				<td>
				<select name="modelname" id="modelname" onchange="getdata();" class="text">
								<option value="-1" selected="selected">--Select--</option>
								<c:forEach var="i" items="${modelsSet}">
									<option title="${i.description}" value="${i.modelName}">${i.modelName}</option>
								</c:forEach>
							</select></td>
			</tr>

		</table>
	</div>
	<div id="modelrevision"></div>
	</div>
</body>
</html>