<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>


<script type="text/javascript">

$(document).ready(function() {
	$("h4").next("div").not(".hideFalse").prev().addClass("accordBack");
	$("h4").click(function(){
		
		if($(this).find("span").hasClass("glyphicon-triangle-bottom")){
			$(this).addClass('accordBack');
			$(this).find("span.glyphicon-triangle-bottom").addClass("glyphicon-triangle-right").removeClass("glyphicon-triangle-bottom");
		}
		else if($(this).find("span").hasClass("glyphicon-triangle-right")){
			$(this).removeClass('accordBack');
			$(this).find("span.glyphicon-triangle-right").toggleClass("glyphicon-triangle-bottom");
		}
		
	});
	
});

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
//validation start

function saveCheck(){
	if($("#companyName").val()){
		return 1;
	}
	else{
		dialogTemplate('Required Fields','Please fill the company name');
		return 0;
	}
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
		if($("#mobileNumber").val()||$("#landlineNumber").val()){
			return 1;
		}
		else{
			dialogTemplate('Contact Details','Enter your mobile or landline number');
			return 0;
		}
	}
}

//validation ends


function saveForm(){
	var check=saveCheck();
	if(check){
	loadProgress();
	$("#formDatas").attr("action", "forms/saveBuilderDetailForDraft");
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
	$("#formDatas").attr("action", "forms/createBuilderDetail");
	$("#formDatas").submit();
	}
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy', 
		changeYear : true
	});
});


// var jsonString=${jsonStringValue};
</script>


<span class="grid-box-header" id="header2title">Builder Details<font class="mand">&nbsp;&nbsp;&nbsp;&nbsp;* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${builderDetailsId }" id="builderDetailsId" name="builderDetailsId"/>
		
		
		
		<!-- <h4><span class="glyphicon glyphicon-triangle-bottom"></span></h4> -->
			<div class="hideFalse">
			<section>
		
			
			<p>
			<label class="FormFieldLabelRequired" for="firstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="firstName" name="firstName" value="${firstName }" required type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="lastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="lastName" name="lastName" value="${lastName }" required type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Email Address">Email Address<em class="mand">* </em>:</label>
				<input class="text" id="emailAddress" name="emailAddress" value="${emailAddress }" required type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="landlineNumber" title="Landline Number">Landline Number:</label>
				<input class="text" id="landlineNumber" name="landlineNumber" value="${landlineNumber }" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="mobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="mobileNumber" name="mobileNumber" value="${mobileNumber }" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="companyName" title="Company Name">Company Name<em class="mand">* </em>:</label>
				<input class="text" id="companyName" name="companyName"  value="${companyName }" required type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="companyAddress" title="Company Address">Company Address<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="companyAddress"  name="companyAddress" required rows="5">${companyAddress } </textarea><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="companyNumber" title="Company Number">Company Number<em class="mand">* </em>:</label>
				<input class="text" id="companyNumber" name="companyNumber"  value="${companyNumber }" required type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="vatNumber" title="VAT Number">VAT Number<em class="mand">* </em>:</label>
				<input class="text" id="vatNumber" name="vatNumber" value="${vatNumber }" required type="text" maxlength="50"/><br/>
				</p>
			</section>
			<!-- </div> -->	
			
			<!-- <h4>Bank Details</h4>
			<div class="hideFalse"> -->
			<section>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="accountHoldersName" title="Account Holder's Name">Account Holder's Name<em class="mand">* </em>:</label>
				<input class="text" id="accountHoldersName" name="accountHoldersName" required value="${accountHoldersName }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="accountNumber" title="Account Number">Account Number<em class="mand">* </em>:</label>
				<input class="text" id="accountNumber" name="accountNumber" required value="${accountNumber }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="sortCode" title="Sort Code">Sort Code<em class="mand">* </em>:</label>
				<input class="text" id="sortCode" name="sortCode" required value="${sortCode }" type="text"/>
			</p>
			<!-- </section>
			</div>
			
			<br/>
			
			<h4>Links</h4>
			<div class="hideFalse">
			<section> -->
			
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToInvoice" title="Link To Invoice">Link To Invoice:</label>
				<input class="text searchtext" id="linkToInvoice" name="linkToInvoice"  readonly="readonly" value="${linkToInvoice }" type="text" onclick="openUploadForm('linkToInvoice');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('linkToInvoice');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToInvoice');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToJobs" title="Link To Jobs - Current jobs & Closed Jobs">Link To Jobs - Current jobs & Closed Jobs:</label>
				<input class="text searchtext" id="linkToJobs" name="linkToJobs"  readonly="readonly" value="${linkToJobs }" type="text" onclick="openUploadForm('linkToJobs');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('linkToJobs');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToJobs');">
			</p>
			
			
			
			
			
			
			
			</section>
			</div>
			
			</div>
			
		
</form>
		<div id="saveSubmit" align="center">
				<!-- <span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="homeClicked();"></span> -->
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="submitForm();"></span>
					<!-- <span><input
					type="button" class="download" id="back"
					value="Back" onclick="builderDetailsFormSelected();"></span> -->
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

