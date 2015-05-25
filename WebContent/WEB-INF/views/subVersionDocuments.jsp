<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/jscrollpane.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.css" />


<!-- <link rel="stylesheet" type="text/css" href="resources/style/jquery.treeview.css" /> -->
<link rel="stylesheet" type="text/css" href="../resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/demo_table_jui.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/TableTools_JUI.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/TableTools.css" />
<link rel="stylesheet" type="text/css" href="../resources/cleditor/jquery.cleditor.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/thickbox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../resources/style/form-styles.css" />

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script> -->

<script src="../resources/js/unused/jquery-1.7.2.min.js"></script>

<!-- <script type="text/javascript" src='resources/JScript/jquery.collision.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.coords.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.draggable.js'></script>
<script type="text/javascript" src='resources/JScript/utils.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.gridster.js'></script>
<script type="text/javascript" src='resources/JScript/scripts.js'></script>
<script type="text/javascript" src="resources/JScript/jquery.resizableColumns.js"></script>
<script type="text/javascript" src="resources/JScript/store.min.js"></script> -->


<script src='../resources/js/jquery.dataTables.min.js' type="text/javascript"></script>
<script src='../resources/js/jQuery.fn.table2CSV.js' type="text/javascript"></script>
<script src='../resources/js/shCore.js' type="text/javascript"></script>
<script src="../resources/js/Datatable2CSV.js" type="text/javascript"></script>
<script src='../resources/js/JmrSdp.js' type="text/javascript"></script>
<script type="text/javascript" src="../resources/js/thickbox.js"></script>
<script src="../resources/cleditor/jquery.cleditor.js" type="text/javascript" charset="utf-8"></script>
<script src="../resources/cleditor/jquery.cleditor.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="../resources/js/ReferenceLinks.js"></script>
<script src='../resources/js/TableTools.js' type="text/javascript"></script>
<script src="../resources/js/TableTools.min.js" type="text/javascript"></script>
<script src="../resources/js/ZeroClipboard.js" type="text/javascript"></script>
<script src='../resources/js/popupDialog.js' type="text/javascript"></script>
<script src='../resources/js/common.js' type="text/javascript"></script>
<script src='../resources/js/ajax.js' type="text/javascript"></script>
<script src="../resources/js/jquery-ui-1.8.20.custom.min.js"></script>
<html>
<head>
<title>Subversion Documents</title>
<script>
$( document ).ready(function() {
$('#documentVersionLists').dataTable({
	"bJQueryUI" : true,
	"sPaginationType" : "full_numbers",
	"iDisplayLength" : 25,
	"bFilter" : true,
	"bDestroy" : true
}); 
});

function downloadSubVersionClicked(path,docName) {
	//alert("coming in");
	$("#path").val(path);
	$("#documentName").val(docName);
	$("#downloadSubVersion").attr("action", "downloadSubDocument");
	$("#downloadSubVersion").submit();
}
</script>
</head>
<body>

	<table id='documentVersionLists' class='documents-table display'>

		<thead>
			<tr>
				<!-- <th>Select</th> -->
				<th>Name</th>
				<th>Download</th>
				<!-- <th>Document Type</th>
				<th>Eb No</th>
				<th>Document Owner</th> -->
			</tr>
		</thead>
		<tbody>


			
				<c:forEach var="i" items="${searchResult}" varStatus="status">
					<tr>
						
						<td>${i.key}</td>
						<td>
						<form id="downloadSubVersion" name="downloadSubVersion" method="post">
						<input type="hidden" id="path" name="path" value=""/>
						<input type="hidden" id="documentName" name="documentName" value=""/>
						<input type="button" value="Download" class="butn" onclick="downloadSubVersionClicked('${i.value}','${i.key}')"/>
						</form>
						 </td>
						
					</tr>
				</c:forEach>


			
		</tbody>
	</table>
	
</body>
</html>
