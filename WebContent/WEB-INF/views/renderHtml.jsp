<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%
	String pageHtml = "";
	String lstDetails_Size = "0";
	try
	{
		pageHtml = (String) request.getAttribute("pageHtml");
		lstDetails_Size = (String) request.getAttribute("lstDetails_Size");
		//long lstTransSize = Long.parseLong(lstTransactionDetails_Size);
		//System.out.println("lstTransSize-from-render.html file = >>>>>>>>>>>>>>>>>>>>>>>>>>+++++"+lstTransSize);
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	String downloadTypeFlag="default";
	
	if(pageHtml.contains("<tr><td colspan=7 style='text-align:center;background:silver'>RentPaymentTransactionHistory</td></tr>"))
	{
		downloadTypeFlag = "RentPaymentTransactionHistory";
		System.out.println("downloadType-from-render.html file = >>>>>>>>>>>>>>>>>>>>>>>>>>+++++"+downloadTypeFlag);
	}
	else if(pageHtml.contains("<tr><td colspan=7 style='text-align:center;background:silver'>Giving Notice Details</td></tr>"))
	{
		downloadTypeFlag = "Giving Notice Details";
		System.out.println("downloadType-from-render.html file = >>>>>>>>>>>>>>>>>>>>>>>>>>+++++"+downloadTypeFlag);
	}
	else if(pageHtml.contains("<tr><td colspan=7 style='text-align:center;background:silver'>Rent Receipt Details</td></tr>"))
	{
		downloadTypeFlag = "Rent Receipt Details";
		System.out.println("downloadType-from-render.html file = >>>>>>>>>>>>>>>>>>>>>>>>>>+++++"+downloadTypeFlag);
	}
	else
	{
		System.out.println("downloadType-from-render.html file = >>>>>>>>>>>>>>>>>>>>>>>>>>+++++ Unable to set downloadTypeFlag");
	}
	
%>

<%=pageHtml%>
<script type="text/javascript">
<!--

//-->

$(document).ready( function () {

	
var lstTransSizeInScript = document.getElementById("lstDetails_Sizehidden").value;
var downloadTypehiddenValue = document.getElementById("downloadTypehidden").value;


if(lstTransSizeInScript == 0 || downloadTypehiddenValue.toLowerCase() == "giving notice details")
{
	$("#btnPdfDownload").hide();
	$("#btnExcelDownload").hide();
	
}
else if(downloadTypehiddenValue.toLowerCase() == "rentpaymenttransactionhistory"  && lstTransSizeInScript >0)
{
	$("#btnPdfDownload").show();
	$("#btnExcelDownload").hide();
}
else if(downloadTypehiddenValue.toLowerCase() == "rent receipt details" && lstTransSizeInScript >0)
{
	$("#btnPdfDownload").hide();
	$("#btnExcelDownload").show();
}


});
</script>

<form method="post" id="pdfDownloadForm" name="pdfDownloadFrm">
<!-- <input type="hidden" name="userFormId" value="${userFormId }" id="userFormId" /> -->
<input type="hidden" name="tenancy" value="${tenancyId }" id="tenancy" />
<input type="hidden" name="lstDetails_Sizehidden" value="<%=lstDetails_Size%>" id="lstDetails_Sizehidden" />
<input type="hidden" name="downloadTypehidden" value="<%=downloadTypeFlag%>" id="downloadTypehidden" />

<input type="submit" id="btnPdfDownload" class="butn" value="PDF Download"
					onClick="pdfDownloadFormClicked();">


<input type="submit" id="btnExcelDownload" class="butn" value="Excel Download"
					onClick="btnExcelDownloadFormClicked();">


</form>
<!-- 
<form method="post" id="ExcelDownload" name="ExcelDownload">

<input type="submit" id="btnExcelDownload" class="butn" value="Excel Download"
					onClick="btnExcelDownloadFormClicked();">

</form> -->
<!-- <form method="post" id="downForm" name="downForm" >
<input type="hidden" id="docName" name="docName" value="">
<input type="submit" value="Download"  id="submitDown" name="submitDown">

</form> -->

<%-- <input type="button" id="btnPrintForm" class="butn" value="Print"
					onClick="printFormClicked('${userFormId}');"> --%>
 
 
 <script type="text/javascript">

function pdfDownloadFormClicked(){
		//alert("coming into download");
	$("#pdfDownloadForm").attr("action", "pdfDownloadForm");
	$("#pdfDownloadForm").submit(); 
}
	
function btnExcelDownloadFormClicked(){
	//alert("coming into download");
$("#pdfDownloadForm").attr("action", "csvOfgetCsvofTransactionDetails");
$("#pdfDownloadForm").submit(); 

}



	
	
function openDoc(docName){
	//alert(id);
	//var docName=document.getElementById(id).value;
	//alert(docName);
	
	var url="openDoc?docName=" + docName;
					var w=window.open(url,"Document Upload",'');	
					setTimeout(function(){ w.close()}, 3000);
	
	/* $.ajax({
		type : "POST",
		url : "finalDoc?docName="+docName,
		cache : false,
		success : function(data) {
			//dialogTemplate("Download","Document Downloaded Successfully");
		} ,error: function(err){
			dialogTemplate("Download",
			"Document Download not successful");
			
		} 
	});	 */	
	
	

}
	

</script>
