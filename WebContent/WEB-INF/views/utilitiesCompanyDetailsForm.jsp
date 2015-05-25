<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>



<script type="text/javascript">

function openUploadForm(id){
	
	var docName=document.getElementById(id).value;
	 
					
					var url = "forms/openForUpload?itemId="+id+"&docName="+docName;
					//alert(url);
					 window.open(url,"Document Upload",'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes'); 
					 //tb_show('Update Links', url, null); 
				
		
}

function setValue(id,val){
	//alert(val+""+id);
	document.getElementById(id).value=val;
	
}

function openDoc(id){
	//alert(id);
	var docName=document.getElementById(id).value;
	//alert(docName);
	if(docName!=""){
		
	var url="forms/openDoc?docName=" + docName;

		var win=window.open(url,"Document Upload",'');
		setTimeout(function(){ win.close()}, 3000);
	}
	else{
		
		dialogTemplate("Document", "No item");
	}

}

function removeElement(id){
	var documentName=document.getElementById(id).value;
	
	//alert(documentName);
	$.ajax({
		type : "GET",
		url : "forms/removeTempDoc?docName=" + documentName,
		cache : false,
		success : function(data) {
			document.getElementById(id).value="";
		}
	});
	
}

$(function(){
	var alternate=false;
	$('.form2 h4').click(function(){
		if(alternate==true)
			{
			$('.form2 > div').hide();
			$(this).next('div').show(300);
			}		
		else		
		$(this).next('div').toggle(300);
	});
	
});


$(document).ready(function() {
	
	$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
	
	
	
	$("#secGrp option[value='${securityGrp}']").attr('selected', 'selected');
	$("#compGrp option[value='${companyGrp}']").attr('selected', 'selected');
	$("#wrkFlw option[value='${modelId}']").attr('selected', 'selected');
	});


function loadProgress() {
	//alert("comes in load progress");
	var ProgressDialog = $("<div id='ProgressBarDialog' title='Please wait...' align='center' style='padding-top: 6px'><img align='middle' src='resources/images/progress_bar.gif' /></div>");
	ProgressDialog.dialog({
		height : 125,
		width : 254,
		modal : true,
		closeOnEscape : false,
		resizable : false,
		draggable : false
	});
}

function removeProgress() {
	//alert("comes in");
	$('#ProgressBarDialog').dialog('destroy').remove();
}

function mandatoryCheck(){
	var count=0;
	$("input[required], textarea[required]").each(function(){
		if(!$(this).val()){
			count++;
		}
	});
	if(count){
		dialogTemplate('Mandatory Fields','Please fill all the mandatory fields');
		return 0;
	}
	else{
		return 1;
	}
}

function saveForm(){
	var check=mandatoryCheck();
	if(check){
	loadProgress();
	$("#formDatas").attr("action", "forms/saveUtilitiesCompanyDetailsForDraft");
	$("#formDatas").submit();
	}
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

function submitForm(){
	var check=mandatoryCheck();
	if(check){
	loadProgress();
	$("#formDatas").attr("action", "forms/createUtilitiesCompanyDetails");
	$("#formDatas").submit();
	}
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});


// var jsonString=${jsonStringValue};
</script>

<span class="grid-box-header" id="header2title">Utilities Company Details<font class="mand">&nbsp;&nbsp;&nbsp;&nbsp;* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${utilitiesCompanyId }" id="utilitiesCompanyId" name="utilitiesCompanyId"/>
	
		
		
			<div class="hideFalse">
			<section>
		
		
			<p>
			
				<label class="FormFieldLabelRequired" for="companyName" title="Company Name">Company Name<em class="mand">* </em>:</label>
				<input class="text" id="companyName" name="companyName" type="text" value="${companyName }" required maxlength="50"/><br/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="companyType" title="Company Type">Company Type<em class="mand">* </em>:</label>
				<input class="text" id="companyType" name="companyType" type="text" value="${companyType }" required maxlength="50"/><br/>
				</p>
			<p>
				<label class="FormFieldLabelRequired" for="companyTelephone" title="Company Telephone">Company Telephone<em class="mand">* </em>:</label>
				<input class="text" id="companyTelephone" name="companyTelephone" value="${companyTelephone }" required type="text" maxlength="50"/><br/>
				</p>
			<p>
				<label class="FormFieldLabelRequired" for="companyAddress" title="Company Address">Company Address<em class="mand">* </em>:</label>
				<textarea class="text" required cols="43" id="companyAddress" name="companyAddress"  rows="5">${companyAddress } </textarea><br/>
				</p>
			<p>
				<label class="FormFieldLabelRequired" for="openingAndClosingTimes" title="Opening and Closing Times">Opening and Closing Times:</label>
				<textarea class="text" cols="43" id="openingAndClosingTimes" name="openingAndClosingTimes"  rows="5">${openingAndClosingTimes } </textarea><br/>
				</p>
				</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="mainContactName" title="Main Contact Name">Main Contact Name:</label>
				<input class="text" id="mainContactName" name="mainContactName" value="${mainContactName }" type="text" maxlength="50"/><br/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="mainContactTelephone" title="Main Contact Telephone">Main Contact Telephone:</label>
				<input class="text" id="mainContactTelephone" name="mainContactTelephone" value="${mainContactTelephone }" type="text" maxlength="50"/><br/>
				</p>
			<p>
				<label class="FormFieldLabelRequired" for="mainContactEmailAddress" title="Main Contact Email Address">Main Contact Email Address:</label>
				<input class="text" id="mainContactEmailAddress" name="mainContactEmailAddress" value="${mainContactEmailAddress }" type="text" maxlength="50"/><br/>
				</p>
			
		
			</section>
			
			</div>
		</div>
</form>
		<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="Back" onclick="utilitiesCompanyFormSelected();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="submitForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

