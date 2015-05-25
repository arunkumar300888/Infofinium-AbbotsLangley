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
function openUploadForm(id){
	
	var docName=document.getElementById(id).value;
	 
					
					var url = "formsNew/openForUpload?itemId="+id+"&docName="+docName;
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
		
	var url="formsNew/openDoc?docName=" + docName;

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
		url : "formsNew/removeTempDoc?docName=" + documentName,
		cache : false,
		success : function(data) {
			document.getElementById(id).value="";
		}
	});
	
}



$(document).ready(function() {
	
var	mailingList="${mailingList}";

	
	$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
	
	if(mailingList!="" && mailingList!=null && mailingList=="required"){
	$( "#mailingList" ).prop( "checked", true );
	}
	
$("#preferredMethodOfPayment option[value='${preferredMethodOfPayment}']").attr('selected', 'selected');

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

	function saveForm(){
		loadProgress();
		$("#formDatas").attr("action", "formsNew/saveCustomerDetailsForDraft");
		$("#formDatas").submit();
		
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();
	
}
	
	function saveForm(){
		loadProgress();
		$("#formDatas").attr("action", "formsNew/saveCustomerDetailsForDraft");
		$("#formDatas").submit();
		
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();
	
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});

// var jsonString=${jsonStringValue};
</script>
 

<span class="grid-box-header" id="header2title">Form Definition <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		
		
		<input type="hidden" value="${userFormId }" id="userFormId" name="userFormId"/>
		<input type="hidden" value="${formDefId }" id="formDefId" name="formDefId"/>
		
		
		
		<p align="center">
				<b><font size="3" >Customer Details Form</font></b>
			</p> <br/>
			
			<h4><span class="glyphicon glyphicon-triangle-bottom"></span>  Customer Info</h4>
			<div class="hideFalse">
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="title" title="Title">Title:</label>
				<input class="text" id="title" name="title" value="${title }" type="text" maxlength="50"/>
			<br/>
			</p>
			<p>
			
				<label class="FormFieldLabelRequired" for="firstName" title="First Name">First Name:</label>
				<input class="text" id="firstName" name="firstName" value="${firstName }" type="text" maxlength="50"/>
			<br/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="lastName" title="Last Name">Last Name:</label>
				<input class="text" id="lastName" name="lastName" value="${lastName }" type="text" maxlength="50"/>
			<br/>
			</p>
			
			<p>
			
				<label class="FormFieldLabelRequired" for="contactNumber" title="Contact Number">Contact Number:</label>
				<input class="text" id="contactNumber" name="contactNumber" value="${contactNumber }" type="text"/>
			<br/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="mobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="mobileNumber" name="mobileNumber" value="${mobileNumber }" type="text"/>
			<br/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Email Address">Email Address:</label>
				<input class="text" id="emailAddress" name="emailAddress" value="${emailAddress }" type="text"/>
			<br/>
			</p>
			<p>
			
				<label class="FormFieldLabelRequired" for="billingAddress" title="Billing Address">Billing Address:</label>				
				<textarea class="text" cols="43" id="billingAddress" name="billingAddress" rows="6"> ${billingAddress }</textarea>
			<br/>
			</p>
			<p>
			<label class="FormFieldLabelRequired" for="shippingAddress" title="Shipping Address">Shipping Address:</label>				
				<textarea class="text" cols="43" id="shippingAddress" name="shippingAddress" rows="6">${shippingAddress } </textarea>
			<br/>
			</p>
			<p>
			<label class="FormFieldLabelRequired" for="personalHandDelivered" title="Personal Hand Delivered">Personal Hand Delivered:</label>				
				<textarea class="text" cols="43" id="personalHandDelivered" name="personalHandDelivered" rows="6">${personalHandDelivered } </textarea>
			<br/>
			</p>
			
			</section>
			<!-- </div> -->
			
			<!-- <h4>Payment Details</h4>
			<div class="hideFalse"> -->
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="paymentDetails" title="Payment Details">Payment Details:</label>
				<input class="text" id="paymentDetails" name="paymentDetails" value="${paymentDetails }" type="text" maxlength="50"/><br/>
				</p>
			<p>
				<label class="FormFieldLabelRequired" for="preferredMethodOfPayment" title="Preferred Method Of Payment">Preferred Method of Payment:</label>
				<select class="text" id="preferredMethodOfPayment" name="preferredMethodOfPayment">
				<option value="-1">Select</option>
				<option value="cash">Cash</option> 
				<option value="card">Card</option> 
				<option value="cheque">Cheque</option> 
				<option value="bank transfer">Bank Transfer</option> 
				</select>
				</p>
				
				<p>
			<label class="FormFieldLabelRequired" for="notes" title="Notes">Notes:</label>				
				<textarea class="text" cols="43" id="notes" name="notes"  rows="6">${notes } </textarea>
			<br/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="mailingList" title="Mailing List">Mailing List:</label>
				<input id="mailingList" name="mailingList"  type="checkbox"  value="required"/>
				</p>
			
			<!-- </section> -->
			<!-- </div> -->
			
			<!-- <h4>Others</h4>
			<div class="hideFalse">
			<section> -->
			
			<p>
				<label class="FormFieldLabelRequired" for="orderHistory" title="Order History">Order History:</label>
				<input class="text" id="orderHistory" name="orderHistory" value="${orderHistory }" readonly="readonly" type="text" onclick="openUploadForm('orderHistory');"/>
				<input class="openFile" type="button"  onclick="openDoc('orderHistory');">
				<input class="clearFile" type="button" class="download" onclick="removeElement('orderHistory');">
			<br/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="appointmentHistory" title="Appointment History">Appointment History:</label>
				<input class="text" id="appointmentHistory" name="appointmentHistory" value="${appointmentHistory }" readonly="readonly" type="text" onclick="openUploadForm('appointmentHistory');"/>
				<input class="openFile" type="button"  onclick="openDoc('appointmentHistory');">
				<input class="clearFile" type="button" class="download" onclick="removeElement('appointmentHistory');">
			<br/>
			</p>
			
			
			
			<c:choose>
						<c:when test="${secGroups.size() == 1}">
						<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" name="secGrp">
			<c:forEach var="i" items="${secGroups}">
			<option selected="selected" value="${i.securityGroup.id }">${i.securityGroup.name }</option>
			</c:forEach>
			</select><br/>
			</p>
						</c:when>
						<c:otherwise>
			<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" name="secGrp">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${secGroups}">
			<option value="${i.securityGroup.id }">${i.securityGroup.name }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
						<c:when test="${compGroups.size() == 1}">
		<p style="display: none;" align="left" id="comp">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Company</label>
			<select class="text" id="compGrp" name="compGrp">
			<c:forEach var="i" items="${compGroups}">
			<option selected="selected" value="${i.id }">${i.value }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="comp">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Company</label>
			<select class="text" id="compGrp" name="compGrp">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${compGroups}">
			<option value="${i.id }">${i.value }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
		<c:when test="${workflows.size() == 1}">
		<p style="display: none;" align="left" id="workflow">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Workflow</label>
			<select class="text" id="wrkFlw" name="wrkFlw">
			<c:forEach var="i" items="${workflows}">
			<option selected="selected" value="${i.id }">${i.name }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="workflow">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Workflow</label>
			<select class="text" id="wrkFlw" name="wrkFlw">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${workflows}">
			<option value="${i.id }">${i.name }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
						<c:when test="${subDate == true}">
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate" value="${submissionDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			</c:when>
			<c:otherwise>
			<p style="display: none;" align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate"  readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			</c:otherwise>
			</c:choose>
			</section>
			</div>
			
		</div>
</form>
		<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="cancel" onclick="homeClicked();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

