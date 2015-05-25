<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>

<!-- <script type="text/javascript" src="resources/js/ReferenceLinks.js"></script> -->
<!-- <script src='resources/js/JmrSdp.js' type="text/javascript"></script> -->

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
		
	})
	
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


$(document).ready(function() {
	
	$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
	
	 var garden="${garden}";
	 var garage="${garage}";
	 var offRoadParking="${offRoadParking}";
	/* var garden="${garden}";
	alert(garden);
	if(garden=="Yes" || garden=="undefined"){
		$("#garden1 option[value='Yes']").attr('selected', 'selected');
	}else{
		//alert("No");
		$("#garden2 option[value='No']").attr('selected', 'selected');
	} */
	
	$("input[name=garden][value=" + garden + "]").attr('checked', 'checked');
	$("input[name=garage][value=" + garage + "]").attr('checked', 'checked');
	$("input[name=offRoadParking][value=" + offRoadParking + "]").attr('checked', 'checked');
	$("#type option[value='${type}']").attr('selected', 'selected');
	
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

//start of validation
	function saveCheck(){
		if($("#firstName").val()){
			return 1;
		}
		else{
			dialogTemplate('Required Fields','Please enter the first name');
			return 0;
		}
	}
//end of validation


function saveForm(){
	var check=saveCheck();
	if(check){
	loadProgress();
	$("#formDatas").attr("action", "forms/savePotentialTenantForDraft");
	$("#formDatas").submit();
	}
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

function submitForm(){
	loadProgress();
	$("#formDatas").attr("action", "forms/createPotentialTenant");
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


<span class="grid-box-header" id="header2title">Potential Tenant<font class="mand">&nbsp;&nbsp;&nbsp;&nbsp;* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${potentialTenantId }" id="potentialTenantId" name="potentialTenantId"/>
		
		
		
		
			<div class="hideFalse">
			<section>
		
		<!-- <p align="left">
				<u><b><font size="3" >Potential Tenant Form</font></b></u>
			</p> -->
			<p>
				<label class="FormFieldLabelRequired" for="title" title="Title">Title:</label>
				<input class="text" id="title" value="${title}" name="title" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="firstName" title="First Name">First Name:</label>
				<input class="text" id="firstName" value="${firstName}" name="firstName" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="lastName" title="Last Name">Last Name:</label>
				<input class="text" id="lastName" value="${lastName}" name="lastName" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="landLineNumber" title="LandLine Number">Landline Number:</label>
				<input class="text" id="landLineNumber" value="${landLineNumber}" name="landLineNumber" type="text" maxlength="50"/><br/>
				</p>
				</section>
				<section>
				<p>
				<label class="FormFieldLabelRequired" for="mobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="mobileNumber" value="${mobileNumber}" name="mobileNumber" type="text" maxlength="50"/><br/>
				</p>
				
				<p>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Email Address">Email Address:</label>
				<input class="text" id="emailAddress" value="${emailAddress}" name="emailAddress" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="nationalInsuranceNumber" title="National Insurance Number">National Insurance Number:</label>
				<input class="text" id="nationalInsuranceNumber" value="${nationalInsuranceNumber}" name="nationalInsuranceNumber" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="currentAddress" title="Current Address">Current Address:</label>
				<textarea class="text" cols="43" id="currentAddress"  name="currentAddress" rows="5">${currentAddress} </textarea>
				
			</p>
	</section>
			
			</div>
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Tenant Requirements</h4>
			<div>
			<section>
			<!-- <p align="left">
				<u><b><font size="3" >Tenant Requirements</font></b></u>
			</p> -->
			
			<p>
				<label class="FormFieldLabelRequired" for="type" title="Property Type">Property Type:</label>
				<select class="text" id="type" name="type">
				<option value="nil">Select</option> 
				<option value="flat">Flat</option>
				<option value="semiDetachedHouse">Semi Detached House</option>
				<option value="detachedHouse">Detached House</option>
				<option value="Bunglow">Bungalow</option>
				</select><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="numberOfBedRooms" title="Number Of Bedrooms">Number of Bedrooms:</label>
				<input class="text" id="numberOfBedRooms" value="${numberOfBedRooms}" name="numberOfBedRooms" type="text" maxlength="50"/><br/><br/>
				</p>
				<p>
				<label class="FormFieldLabel" for="garden" title="Garden">Garden:</label>
				<input checked="checked" id="garden1" name="garden" type="radio" value="Yes"/>
				<label class="radio" for="garden1">Yes</label>
				<input id="garden2" name="garden" type="radio" value="No"/>				
				<label class="radio" for="garden2">No</label>
				
			<br/><br/>
			
			</p>
			
			<p>
				<label class="FormFieldLabel" for="garden" title="Off Road parking">Off Road Parking:</label>
				<input checked="checked" id="offRoadParking1" name="offRoadParking" type="radio" value="Yes"/>
				<label class="radio" for="offRoadParking1">Yes</label>
				<input id="offRoadParking2" name="offRoadParking" type="radio" value="No"/>				
				<label class="radio" for="offRoadParking2">No</label>
				
			<br/><br/>
			
			</p>
			</section>
				<section>
			
			<p>
				<label class="FormFieldLabel" for="garden" title="garage">Garage:</label>
				<input checked="checked" id="garage1" name="garage" type="radio" value="Yes"/>
				<label class="radio" for="garage1">Yes</label>
				<input id="garage2" name="garage" type="radio" value="No"/>				
				<label class="radio" for="garage2">No</label>
				
			<br/><br/>
			
			</p>
			<%-- <p>
				<label class="FormFieldLabelRequired" for="offRoadParking" title="Off Road Parking">Off Road Parking:</label>
				<textarea class="text"  cols="30" id="offRoadParking" name="offRoadParking" rows="5">${offRoadParking} </textarea><br/>
				</p> --%>
				<%-- <p>
				<label class="FormFieldLabelRequired" for="garage" title="Garage">Garage:</label>
				<textarea class="text"  cols="30" id="garage" name="garage" rows="5">${garage} </textarea><br/>
				</p> --%>
				<p>
				<label class="FormFieldLabelRequired" for="other" title="Notes">Notes:</label>
				<textarea class="text"  cols="43" id="other" name="other" rows="5">${other} </textarea>
				
				
			</p>
					
			
			
			
			
			</section>
				</div>
		</div>
</form>
		<div id="saveSubmit" align="center">
		
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="homeClicked();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="submitForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

