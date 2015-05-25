<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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


<link rel="stylesheet" type="text/css"
	href="../resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/demo_table_jui.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/TableTools_JUI.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/TableTools.css" />


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>
<script src='../resources/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
<script src="../resources/js/ReferenceLinks.js" type="text/javascript"></script>
<script src="../resources/js/JmrSdp.js" type="text/javascript"></script>

<style>
body {
	background: none;
}
</style>

<script type="text/javascript">

$(function() { 
	//alert("func");
	$.ajax({
		type : "GET",
		url : "docLinksForm",
		cache : false,
		success : function(data) {

			//  alert("Success");		
			$("#documentLinksForms").html(data);

			$('#documentLinksListsForms').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true,
				"bDestroy" : true
			});

		}
	});
	});
	
	$(function() {

		$("#datepicker1Links").datepicker({
			dateFormat : 'dd-mm-yy'

		/* onClose : function() {

			this.focus();
		} */
		});

		$("#datepicker2Links").datepicker({
			dateFormat : 'dd-mm-yy'

		/* onClose : function() {

			this.focus();
		} */
		});

		$("#datepicker3Links").datepicker({
			dateFormat : 'dd-mm-yy',

		/* onClose : function() {

			this.focus();
		} */
		});
		$("#datepicker4Links").datepicker({
			dateFormat : 'dd-mm-yy',

		/* onClose : function() {

			this.focus();
		} */
		});

		var test = '<c:out value="${edit}"/>';
		if (test != "" && test == "edit") {

			$.ajax({
				type : "GET",
				url : "loadeditreferencelinks",
				cache : false,
				success : function(data) {

					//  alert("Success");		
					$("#documentLinks").html(data);

					$('#documentLinksListsForms').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						"bDestroy" : true
					});

				}
			});

		} else if (test != "" && test == "update") {
			//alert("in Update");

			$.ajax({
				type : "GET",
				url : "updatereferencelinks?docId="
						+ '<c:out value="${docId}"/>',
				cache : false,
				success : function(data) {

					$("#documentLinks").html(data);

					$('#documentLinksListsForms').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						"bDestroy" : true
					});

				}
			});
		}

	});
	
	$(function() {
		$('#dyncontentDiv').hide();
		 $('#uploadNewDoc').click(function() { 
			   /* if($('#radio_button').is(':checked')) { 
				   
			   } */
			   $('#attachLinksforms').hide();
			   $('#dyncontentDiv').show();
			  
			 }); 
		 $('#searchExisting').click(function() { 
			   /* if($('#radio_button').is(':checked')) { 
				   
			   } */
			   $('#dyncontentDiv').hide();
			   $('#attachLinksforms').show();
			 }); 
	});
</script>
</head>

<body>
<div align="center">
<!-- <label>Search Document</label> -->
<input type="button" class="download" style="display: inline;" value="Search Existing Document" name="uploadDoc" id="searchExisting" checked="checked">
<!-- <label>Upload New Document</label> -->
<input type="button" class="download" style="display: inline;"  name="uploadDoc" value="Upload New Document" id="uploadNewDoc" onclick="quickUploadFormProcess('${itemId}','${documentName}');">
</div>
	<div id="attachLinksforms">

		<div id="searchLinksforms">

<div style="width: 98%; margin: 0 auto;">
			<fieldset>
				<legend>Search Links</legend>
				<form method="post" id="frmSearchLinks" style="padding: 5px">
				<input type="hidden" value="${itemId}" name="itemId" id="itemId"/>
				<input type="hidden" value="${documentName}" name="docName" id="docName"/>
					<%-- <input type="hidden" id="edit" name="edit" value="${edit}">
					<input type="hidden" id="docId" name="docId" value="${docId}">
					<input type="hidden" id="path" name="path" value="${filepath}">
					<input type="hidden" id="name" name="name" value="${docname}">
					<input type="hidden" id="caseid" name="caseid" value="${caseid}">
					<input type="hidden" id="adminMeta" name="adminMeta" value="${adminMeta}">
					<input type="hidden" id="stepList" name="stepList"
						value="${stepList}"> --%>
					<div id="searchAboveLinks" valign="top" style="display: none;">


						<input type="button" id="btnSearchLinks" class="download"
							value="Search" onClick="searchBtnLinksClicked();" /> <input
							type="reset" name="reset" id="btnClearAllLinks" class="download"
							value="Clear All" /> 
							
							
							
							
							
							
							
							
					</div>
					<div class="select-box">
					<label>Document Title</label>  <input type="text"
							name="documentNameLinks" class="text" id="documentNameLinks"
							onkeypress="searchKeyPressLinks(event);"
							title="Search for Documents by searching the document title/file name" />
					<label>Document Type</label> <select name="documentTypeLinks" class="text" style="width: 252px;"
						id="documentTypeLinks" onkeypress="searchKeyPressLinks(event);">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${doctypes}">
							<option value="${i.id}">${i.doctypeName}</option>
						</c:forEach>
					</select> 
					<%-- <label>Site</label> <select name="sitesLinks" id="sitesLinks" class="text"
						onkeypress="searchKeyPressLinks(event);">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${metadata4}">
							<option value="${i.id}">${i.disciplineName}</option>
							<option value="${i.id}">${i.value}</option>
						</c:forEach>
					</select> 
					<label>Discipline</label> <select name="disciplineLinks" id="disciplineLinks"
						class="text" onkeypress="searchKeyPressLinks(event);">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${metadata3}">
							<option value="${i.id}">${i.disciplineName}</option>
							<option value="${i.id}">${i.value}</option>
						</c:forEach>
					</select>  --%>
					<label>Activity/Keywords</label> <input type="text" name="keywordsLinks"
						id="keywordsLinks" class="text"
						onkeypress="searchKeyPressLinks(event);"
						title="Search for documents by searching the activities/keywords.( You can use comma ',' to search multiple keywords )" />




					<label>Date Range From</label> <input type="text" id="datepicker3Links"
						name="relevantdatefromLinks"
						onkeypress="searchKeyPressLinks(event);" class="text datepicker" />
					<label>Date Range To</label> <input type="text" id="datepicker4Links"
						name="relevantdatetoLinks"
						onkeypress="searchKeyPressLinks(event);" class="text datepicker" />





					<label>Document Owner</label> <input name="authorLinks" id="ownerLinks"
						class="text" onkeypress="searchKeyPressLinks(event);" /> 
					<label>Unique Doc ID/Eb Number</label> <input type="text" name="ebNoLinks" id="ebNoLinks"
						class="text" onkeypress="searchKeyPressLinks(event);" /> <input
						type="button" id="btnSearchLinks" class="download" value="Search"
						onClick="searchBtnLinksFormClicked();" /> <input type="reset"
						name="reset" id="btnClearAllLinks" class="download"
						value="Clear All" />



</div>

				</form>

			</fieldset>

</div>

<div class="float-left" style="width: 100%; margin-top: 10px;">
			<div class="title2"> Document Links</div>
			
			<div id="documentLinksForms" style="min-height: 600px;">
			
			</div>
</div>
		</div>
		
		


	</div>
	<div id="dyncontentDiv">
<ul class='gridster'>
<li class='grid-box view-my-documents'>
<div id="dynContentForm" class='inner-box'></div>
<div class="clear"></div>
</li>
</ul>
</div>



</body>
</html>