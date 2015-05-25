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

$(document).ready(function() {
var numbRs=parseInt("${numberOfBedrooms}");
var wrapper         = $("#roomsAndWindows");
var max_fields      = 2000;
var roomDetails="${roomDetailsMap}";
//alert(roomDetails);
//var list=[1,2,3,4];

	//alert( index + ": " + value );

//alert(roomDetails+" "+numbRs);
if(numbRs == 0 || numbRs ==""){
	   //alert("inside if");
	   document.getElementById( 'roomsAndWindows' ).style.display = 'none';
 }else{
	    //alert("else "+numbRs);
	   $('#roomsAndWindows').show();
	   $('#roomsAndWindows').find('section').remove();
	   /*$('#roomsAndWindows').find('label').remove();
	   $('#roomsAndWindows').find('label').remove();
	   $('#roomsAndWindows').find('input').remove();
	   $('#roomsAndWindows').find('h5').remove();
	   $('#roomsAndWindows').find('u').remove();
	   $('#roomsAndWindows').find('b').remove();
	   $('#roomsAndWindows').find('br').remove();*/
	   //max input box allowed
		 // alert(numbRs);
		   var i=1;
		   //for(i=1;i<=numbRs;i++){
         //alert("inside for "+roomDetails);
      	if(roomDetails!=""){
         var str_array = roomDetails.split(',');
//alert(str_array.length+" "+str_array);
         for(var j = 0; j < str_array.length-1; j++) {
        	 //alert(str_array.length);
        	 //alert(j);
         $(wrapper).append('<section><h5>Room '+i+' Details</h5><p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Door" title="Bedroom '+i+ ' Door">Bedroom Door:</label>'+
			'<input class="text" id="bedroom'+i+'Door"  name="bedroom'+i+'Door" value="'+str_array[j]+'" type="text" /></p>'+
			'<p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Other" title="Bedroom '+i+ ' Other">Bedroom Other:</label>'+
			'<input class="text" id="bedroom'+i+'Other"  name="bedroom'+i+'Other" value="'+str_array[++j]+'" type="text" /></p>'+
			'<p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Window" title="Bedroom '+i+ ' Window">Bedroom Window:</label>'+
			'<input class="text" id="bedroom'+i+'Window"  name="bedroom'+i+'Window" value="'+str_array[++j]+'" type="text" /></p><br/></section>');
         i++;
         }
	   	//}
		   }
 }
	
	
});

$(document).ready(function() {
	
	$("#dateOfPerchase").prop("readonly", false);
	
	$(".opt, .optCmp").hide();
	
	$("#client").change(function(){
		if($("#client").val()=="landLordName"){
			$(".opt").show();
			$(".opt").parent().next().attr({required:"true"});
			$(".optCmp").parent().next().removeAttr("required");
			$(".optCmp").hide();
		}
		else if($("#client").val()=="company"){
			$(".optCmp").show();
			$(".opt").show();
			$(".opt, .optCmp").parent().next().attr({required:"true"});
		}
		else{
			$(".opt, .optCmp").hide();
			$(".opt, .optCmp").parent().next().removeAttr("required");
		}
	});
	
var	managementCompany="${managementCompany}";
var propertyId="${propertyId}";
if(propertyId!="0"){
	document.getElementById("managementCompany").value=managementCompany;
}else{
	document.getElementById("managementCompany").value="Abbotts Langley";
}
	
	
	
	$("#client option[value='${client}']").attr('selected', 'selected');
$("#propertyType option[value='${propertyType}']").attr('selected', 'selected');
$("#rentalType option[value='${rentalType}']").attr('selected', 'selected');

});

$(document).ready(function() {
	
	var max_fields      = 2000;
	 var wrapper         = $("#roomsAndWindows");
	 
	   $('#numberOfBedrooms').change(function(e)  {
		   
//		   $('#roomsAndWindows').remove();
	       var noOfRooms=document.getElementById("numberOfBedrooms");
	      // alert(noOfRooms.value);
	       if(noOfRooms.value == 0 || noOfRooms.value ==""){
	    	  // alert("inside if");
	    	   document.getElementById( 'roomsAndWindows' ).style.display = 'none';
	       }else{
	    	   // alert("else");
	    	   $('#roomsAndWindows').show();
	    	   $('#roomsAndWindows').find('section').remove();
	    	   /*$('#roomsAndWindows').find('label').remove();
	    	   $('#roomsAndWindows').find('input').remove();
	    	   $('#roomsAndWindows').find('h5').remove();
	    	   $('#roomsAndWindows').find('u').remove();
	    	   $('#roomsAndWindows').find('b').remove();
	    	   $('#roomsAndWindows').find('br').remove();*/
	    	   if(noOfRooms.value < max_fields){ //max input box allowed
	    		   var i;
	    		   for(i=1;i<=noOfRooms.value;i++){
	               //text box increment
	              // $('#roomsAndWindows').add();
	            
	               $(wrapper).append('<section><h5>Room '+i+' Details</h5><p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Door" title="Bedroom '+i+ ' Door">Bedroom Door:</label>'+
					'<input class="text" id="bedroom'+i+'Door" name="bedroom'+i+'Door" type="text" /></p>'+
					'<p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Window" title="Bedroom '+i+ ' Window">Bedroom Window:</label>'+
					'<input class="text" id="bedroom'+i+'Window" name="bedroom'+i+'Window" type="text" /></p>'+
					'<p> <label class="FormFieldLabelRequired" for="bedroom'+i+'Other" title="Bedroom '+i+ ' Other">Bedroom Other:</label>'+
					'<input class="text" id="bedroom'+i+'Other" name="bedroom'+i+'Other" type="text" /></p><br/></section>');
	              
	    	   	}
	    	   }
	       }
	    });
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
		if($("#managementCompany").val()){
			if($("#numberOfBedrooms").val()){
			return 1;
			}
			else{
				dialogTemplate('Required Fields','Please enter the number of bedrooms');
				return 0;
			}
		}
		else{
			dialogTemplate('Required Fields','Please enter the Management company');
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
				if($("#numberOfBedrooms").val()){
					return 1;	
				}
				else{
					dialogTemplate('Room Details','Please enter the number of bedrooms');
					return 0;
				}
			}
			else{
				dialogTemplate('Contact Details','Enter your mobile or landline number');
				return 0;
			}
		}
	}
	
	//end of validation

	function saveForm(){
				var check=saveCheck();
				if(check){ 
					loadProgress();
 		 			$("#formDatas").attr("action", "forms/savePropertyDetailsFormForDraft");
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
		$("#formDatas").attr("action", "forms/createPropertyDetails");
		$("#formDatas").submit();
		}
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


<span class="grid-box-header" id="header2title">Property Details Form <font class="mand">&nbsp;&nbsp;&nbsp;&nbsp;* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${propertyId }" id="propertyId" name="propertyId"/>
		<%-- <input type="hidden" value="${formDefId }" id="formDefId" name="formDefId"/> --%>
		
		<h4><span class="glyphicon glyphicon-triangle-bottom"></span>  General Information</h4>
			<div class="hideFalse">
			<section>
			<%-- <c:choose>
					<c:when test="${userFormID eq '-1'}">
			<p>
				<label class="FormFieldLabelRequired" for="managementCompany" title="Management Company">Management Company:</label>
				<input class="text" id="managementCompany" name="managementCompany" value="AbbottsLangley" type="text" maxlength="50"/>
			</p>
			</c:when>
			<c:otherwise> --%>
			<p>
				<label class="FormFieldLabelRequired" for="managementCompany" title="Management Company">Management Company<em class="mand">* </em>:</label>
				<input class="text" id="managementCompany" name="managementCompany"  type="text" maxlength="50"/>
			</p>
			
			<%-- </c:otherwise>
			</c:choose> --%>
			
			<p>
				<label class="FormFieldLabelRequired" for="client" title="Client">Client Type:</label>
				<select class="text" id="client" name="client">
				<option value="nil">Select</option>
				<option value="landLordName">Landlord</option>
				<option value="company">Company</option>				
				<option value="na">NA</option>	 
				</select>
				<%-- <input class="text" id="client" name="client" value="${client }" type="text"/> --%>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="companyName" title="Company Name">Company Name<em class="mand optCmp">* </em>:</label>
				<input class="text" id="companyName" name="companyName" value="${companyName }" type="text" maxlength="50"/>
			</p>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="firstName" title="First Name">First Name<em class="mand opt">* </em>:</label>
				<input class="text" id="firstName" name="firstName" value="${firstName }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="lastName" title="Last Name">Last Name<em class="mand opt">* </em>:</label>
				<input class="text" id="lastName" name="lastName" value="${lastName }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="landlineNumber" title="Landline Number">Landline Number:</label>
				<input class="text" id="landlineNumber" name="landlineNumber" value="${landlineNumber }"  type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="mobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="mobileNumber" name="mobileNumber" value="${mobileNumber }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Email Address">Email Address<em class="mand opt">* </em>:</label>
				<input class="text" id="emailAddress" name="emailAddress" value="${emailAddress }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="address" title="Address">Address<em class="mand opt">* </em>:</label>				
				<textarea class="text" cols="43" id="address" name="address" rows="6"> ${address }</textarea>
			</p>
			</section>
			<section>
			
			<p>
				<label class="FormFieldLabel" for="accountHoldersName" title="Account Holders Name">Account Holders Name<em class="mand opt">* </em>:</label>
				<input class="text" id="accountHoldersName" name="accountHoldersName" value="${accountHoldersName }" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="accountNumber" title="Account Number">Account Number<em class="mand opt">* </em>:</label>
				<input class="text" id="accountNumber" name="accountNumber" value="${accountNumber }" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabel" for="sortCode" title="Sort Code">Sort Code<em class="mand opt">* </em>:</label>
				<input class="text" id="sortCode" name="sortCode" value="${sortCode }" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabel" for="vatDetails" title="VAT Details">VAT Details:</label>
				<input class="text" id="vatDetails" name="vatDetails" value="${vatDetails }" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabel" for="referencesForPayment" title="References for Payment">References for Payment:</label>
				<textarea class="text" cols="43" id="referencesForPayment" name="referencesForPayment" rows="3">${referencesForPayment }</textarea>
			</p></section>
			</div>
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Property Information</h4>
			<div>
			<section>
			<p>
				<label class="FormFieldLabel" for="propertyAddressLine1" title="Property Address Line 1">Property Address Line 1<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="propertyAddressLine1" name="propertyAddressLine1" required="true" rows="3"> ${propertyAddressLine1 }</textarea>
			</p>	
		
			<p>
				<label class="FormFieldLabel" for="propertyAddressLine2" title="Property Address Line 2">Property Address Line 2<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="propertyAddressLine2" name="propertyAddressLine2" required="true" rows="3"> ${propertyAddressLine2 }</textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="town" title="Town">Town<em class="mand">* </em>:</label>
				<input class="text" id="town" name="town" required="true" value="${town }" type="text" maxlength="100"/>				
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="cityCountry" title="City/County">City/County<em class="mand">* </em>:</label>
				<input class="text" id="cityCountry" required="true" name="cityCountry" value="${cityCountry }" type="text" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="propertyPostCode" title="Property Postcode">Property Postcode<em class="mand">* </em>:</label>
				<input class="text" id="propertyPostCode" required="true" name="propertyPostCode" value="${propertyPostCode }" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="propertyType" title="Property Type">Property Type<em class="mand">* </em>:</label>
				<select class="text" id="propertyType" required="true" name="propertyType">
				<option value="nil">Select</option>
				<option value="Flat">Flat</option>
				<option value="Studio">Studio</option>				
				<option value="House">House</option> 				 
				</select>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="rentalType" title="Rental Type">Rental Type<em class="mand">* </em>:</label>
				<select class="text" id="rentalType" required="true" name="rentalType">
				<option value="nil">Select</option>
				<option value="wholeHouse">Whole Property</option>
				<option value="perBedroom">Room</option>				
				</select>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="propertyDescription" title="Property Description">Property Description:</label>
				<textarea class="text" cols="43" id="propertyDescription" name="propertyDescription" rows="6" maxlength="500">${propertyDescription } </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="houseMeasurements" title="House Measurements">House Measurements in Sq. Ft.:</label>
				<textarea class="text" cols="43" id="houseMeasurements" name="houseMeasurements" rows="3" maxlength="500"> ${houseMeasurements }</textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="floorPlan" title="Floor Plan">Floor Plan:</label>
				<input class="text searchtext" id="floorPlan" name="floorPlan" readonly="readonly" type="text" value="${floorPlan }" onclick="openUploadForm('floorPlan');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('floorPlan');">
				<input class="clearFile" type="button" value="" onclick="removeElement('floorPlan');">
			</p>
			</section>
			<section>
			<!-- <p>
				<label class="FormFieldLabel" for="f_6_22" title="Gas Meter Location">Gas Meter Location:</label>
				<input class="text" id="f_6_22" name="f_6_22" type="text" maxlength="100"/>
			</p> -->
			
			<p>
				<label class="FormFieldLabel" for="gasMeterLocation" title="Gas Meter Location">Gas Meter Location:</label>
				<textarea class="text" cols="43" id="gasMeterLocation" name="gasMeterLocation" rows="3" maxlength="500"> ${gasMeterLocation }</textarea>
			</p>			
			
		   <p>
				<label class="FormFieldLabel" for="electricMeterLocation" title="Electric Meter Location">Electric Meter Location:</label>
				<textarea class="text" cols="43" id="electricMeterLocation" name="electricMeterLocation" rows="3" maxlength="500"> ${electricMeterLocation }</textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="waterMeterLocation" title="Water Meter Location">Water Meter Location</label>							
				<textarea class="text" cols="43" id="waterMeterLocation" name="waterMeterLocation" rows="3" maxlength="500"> ${waterMeterLocation }</textarea>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="dateOfPerchase" title="Date Of Purchase">Date Of Purchase:</label>
				<input class="text  datepicker" id="dateOfPerchase" name="dateOfPerchase" value="${dateOfPerchase }" maxlength="10" readonly="readonly" type="text" format="dd-mm-yyyy"/>
			</p>				
			
			<p>
				<label class="FormFieldLabelRequired" for="priceOfPurchase" title="Price Of Purchase">Price Of Purchase:</label>							
				<input class="text" id="priceOfPurchase" name="priceOfPurchase" type="text" value="${priceOfPurchase }" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="estimatedValue" title="Estimated Value">Estimated Value:</label>							
				<input class="text" id="estimatedValue" name="estimatedValue" value="${estimatedValue }" type="text" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="asOfDate" title="As of Date">As of Date:</label> 
				<input class="text datepicker" id="asOfDate" name="asOfDate" value="${asOfDate }" maxlength="10" readonly="readonly" type="text" format="dd-mm-yyyy" />
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="annualRent" title="Annual Rent">Annual Rent<em class="mand">* </em>:</label>							
				<input class="text" id="annualRent" name="annualRent" required="true" value="${annualRent }" type="text" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="pictures" title="Picture">Picture:</label>
				<input class="text searchtext" id="pictures" name="pictures" value="${pictures }" readonly="readonly" type="text" onclick="openUploadForm('pictures');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('pictures');">
				<input class="clearFile" type="button" value="" onclick="removeElement('pictures');">
			</p>
			
			<p>
				<label class="FormFieldLabel" for="localAuthority" title="Local Authority">Local Authority:</label>
				<textarea class="text" cols="43" id="localAuthority" name="localAuthority" rows="3" maxlength="500"> ${localAuthority }</textarea>
			</p>
			
			
			</section>
			</div>
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Rooms & Key Codes</h4>
			<div>
			<section>
			<p>
			<label class="FormFieldLabel" for="frontDoorKeyCode" title="Front Door Key Code">Front Door Key Code:</label>
			<input class="text" id="frontDoorKeyCode" name="frontDoorKeyCode" value="${frontDoorKeyCode }" type="text" />
			</p>
		<!-- 	<p>
			<label class="FormFieldLabel" for="f_6_35" title="Front Door Key Code Document">Front Door Key Code Document:</label>
			<input class="text searchtext" id="f_6_35" name="f_6_35" readonly="readonly" type="text" onclick="openUploadForm('f_6_35');"/>
			<input class="download" type="button" value="Upload" onclick="openUploadForm('f_6_7');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('f_6_35');">
			<input class="clearFile" type="button" value="" onclick="removeElement('f_6_35');">
			</p> -->
			
			<p>
			<label class="FormFieldLabel" for="backDoorKeyCode" title="Back Door Key Code">Back Door Key Code:</label>
			<input class="text" id="backDoorKeyCode" name="backDoorKeyCode" value="${backDoorKeyCode }"  type="text" />
			</p>
		<!-- 	<p>
			<label class="FormFieldLabel" for="f_6_37" title="Back Door Key Code Document">Back Door Key Code Document:</label>
			<input class="text searchtext" id="f_6_37" name="f_6_37" readonly="readonly" type="text" onclick="openUploadForm('f_6_37');"/>
			<input class="download" type="button" value="Upload" onclick="openUploadForm('f_6_7');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('f_6_37');">
			<input class="clearFile" type="button" value="" onclick="removeElement('f_6_37');">
			</p>
			 -->
			<p>
			<label class="FormFieldLabel" for="porchDoorKeyCode" title="Porch Door Key Code">Porch Door Key Code:</label>
			<input class="text" id="porchDoorKeyCode" name="porchDoorKeyCode" value="${porchDoorKeyCode }" type="text" />
			</p>
			<!-- <p>
			<label class="FormFieldLabel" for="f_6_39" title="Porch Door Key Code Document">Porch Door Key Code Document:</label>
			<input class="text searchtext" id="f_6_39" name="f_6_39" readonly="readonly" type="text" onclick="openUploadForm('f_6_39');"/>
			<input class="download" type="button" value="Upload" onclick="openUploadForm('f_6_7');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('f_6_39');">
			<input class="clearFile" type="button" value="" onclick="removeElement('f_6_39');">
			</p>
			 -->
			<p>
			<label class="FormFieldLabel" for="flatDoor" title="Flat Door">Flat Door:</label>
			<input class="text" id="flatDoor" name="flatDoor" value="${flatDoor }" type="text" />
			</p>
			<!-- <p>
			<label class="FormFieldLabel" for="f_6_41" title="Flat Door Document">Flat Door Document:</label>
			<input class="text searchtext" id="f_6_41" name="f_6_41" readonly="readonly" type="text" onclick="openUploadForm('f_6_41');"/>
			<input class="download" type="button" value="Upload" onclick="openUploadForm('f_6_7');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('f_6_41');">
			<input class="clearFile" type="button" value="" onclick="removeElement('f_6_41');">
			</p>
			 -->
			<p>
				<label class="FormFieldLabel" for="others" title="others">Others:</label>
				<textarea class="text" cols="43" id="others" name="others" rows="3" maxlength="500">${others } </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="numberOfBedrooms" title="Number of Bedrooms">Number Of Bedrooms:</label>							
				<input class="text" min="1" max="100" id="numberOfBedrooms" name="numberOfBedrooms" value="${numberOfBedrooms }" type="number" maxlength="100"/>
			</p>
			</section>
			
			<div><div id="roomsAndWindows" style="display: none; "></div></div>
			
			<!-- <p>
				<label class="FormFieldLabelRequired" for="f_6_43" title="Bedroom 1 Door">Bedroom 1 Door:</label>							
				<input class="text" id="f_6_43" name="f_6_43" type="text" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_6_44" title="Bedroom 1 Window">Bedroom 1 Window:</label>							
				<input class="text" id="f_6_44" name="f_6_44" type="text" maxlength="100"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_6_45" title="Bedroom 1 Other">Bedroom 1 Other:</label>							
				<input class="text" id="f_6_45" name="f_6_45" type="text" maxlength="100"/>
			</p> -->
			
			<!-- <p>
				<label class="FormFieldLabelRequired" for="f_6_46" title="Annual Rent">Annual Rent</label>							
				<input class="text" id="f_6_46" name="f_6_46" type="text" maxlength="100"/>
			</p> -->
			
			
			
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Links</h4>
			<div>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="tenancyAgreement" title="Tenancy Agreement">Tenancy Agreement:</label>
				<input class="text searchtext" id="tenancyAgreement" name="tenancyAgreement"  readonly="readonly" value="${tenancyAgreement }" type="text" onclick="openUploadForm('tenancyAgreement');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('tenancyAgreement');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenancyAgreement');">
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="insuranceCertificate" title="Insurance Certificate">Insurance Certificate:</label>
				<input class="text searchtext" id="insuranceCertificate" name="insuranceCertificate" readonly="readonly" value="${insuranceCertificate }" type="text" onclick="openUploadForm('insuranceCertificate');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('insuranceCertificate');">
				<input class="clearFile" type="button" value="" onclick="removeElement('insuranceCertificate');">
			</p>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="epcCertificate" title="EPC Certificate">EPC Certificate:</label>
				<input class="text searchtext" id="epcCertificate" name="epcCertificate" readonly="readonly" value="${epcCertificate }" type="text" onclick="openUploadForm('epcCertificate');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('epcCertificate');">
				<input class="clearFile" type="button" value="" onclick="removeElement('epcCertificate');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="gasCertificate" title="Gas Certificate">Gas Certificate:</label>
				<input class="text searchtext" id="gasCertificate" name="gasCertificate" value="${gasCertificate }" readonly="readonly" type="text" onclick="openUploadForm('gasCertificate');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('gasCertificate');">
				<input class="clearFile" type="button" value="" onclick="removeElement('gasCertificate');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="electricCertificate" title="Electric Certificate">Electric Certificate:</label>
				<input class="text searchtext" id="electricCertificate" name="electricCertificate" value="${electricCertificate }" readonly="readonly" type="text" onclick="openUploadForm('electricCertificate');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('electricCertificate');">
				<input class="clearFile" type="button" value="" onclick="removeElement('electricCertificate');">
			</p>
			
			<%-- <p>
				<label class="FormFieldLabelRequired" for="hmoLicence" title="Tenancy Agreement">Tenancy Agreement:</label>
				<input class="text searchtext" id="tenancyAgreement" name="tenancyAgreement" value="${tenancyAgreement }" readonly="readonly" type="text" onclick="openUploadForm('tenancyAgreement');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('tenancyAgreement');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenancyAgreement');">
			</p> --%>
			
			<p>
				<label class="FormFieldLabelRequired" for="contractsAndWarranties" title="HMO License">HMO License:</label>
				<input class="text searchtext" id="hmoLicence" name="hmoLicence" value="${hmoLicence }" readonly="readonly" type="text" onclick="openUploadForm('hmoLicence');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('hmoLicence');">
				<input class="clearFile" type="button" value="" onclick="removeElement('hmoLicence');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="contractsAndWarranties" title="Contracts & Warranties">Contracts & Warranties:</label>
				<input class="text searchtext" id="contractsAndWarranties" name="contractsAndWarranties" value="${contractsAndWarranties }" readonly="readonly" type="text" onclick="openUploadForm('contractsAndWarranties');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('contractsAndWarranties');">
				<input class="clearFile" type="button" value="" onclick="removeElement('contractsAndWarranties');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="currentTenancyForm" title="Land Registry">Land Registry:</label>
				<input class="text searchtext" id="landRegistry" name="landRegistry" value="${landRegistry }" readonly="readonly" type="text" onclick="openUploadForm('landRegistry');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('landRegistry');">
				<input class="clearFile" type="button" value="" onclick="removeElement('landRegistry');">
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="currentTenancyForm" title="Current Tenancy Form">Current Tenancy Form:</label>
				<input class="text searchtext" id="currentTenancyForm" name="currentTenancyForm" value="${currentTenancyForm }" readonly="readonly" type="text" onclick="openUploadForm('currentTenancyForm');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('currentTenancyForm');">
				<input class="clearFile" type="button" value="" onclick="removeElement('currentTenancyForm');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="propertyTimeline" title="Property Timeline">Property Timeline:</label>
				<input class="text searchtext" id="propertyTimeline" name="propertyTimeline" value="${propertyTimeline }" readonly="readonly" type="text" onclick="openUploadForm('propertyTimeline');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('propertyTimeline');">
				<input class="clearFile" type="button" value="" onclick="removeElement('propertyTimeline');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToJobs" title="Link Of Jobs">Link to Jobs:</label>
				<input class="text searchtext" id="linkToJobs" name="linkToJobs" value="${linkToJobs }" readonly="readonly" type="text" onclick="openUploadForm('linkToJobs');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('linkToJobs');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToJobs');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="lendingInformation" title="Lending Information">Lending Information:</label>
				<input class="text searchtext" id="lendingInformation" name="lendingInformation" value="${lendingInformation }" readonly="readonly" type="text" onclick="openUploadForm('lendingInformation');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('lendingInformation');">
				<input class="clearFile" type="button" value="" onclick="removeElement('lendingInformation');">
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
					value="Back" onclick="propertyFormSelected();"></span> -->
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

