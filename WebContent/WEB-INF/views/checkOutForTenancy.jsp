<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Check Out</title>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>
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


<script type="text/javascript">


$(document).ready(function() {
	
var selectedRooms="${selectedRooms}";
var tenantSig="${tenantSignature}";

if(tenantSig!=""){
	
	$("input[name=garden][value=" + tenantSig + "]").attr('checked', 'checked');
}
		
var wrapper         = $("#rooms");

if(selectedRooms!=""){
	
	//alert(selectedRooms);
	var str_array =selectedRooms.split(',');
	
   
  	
  for(var i = 0; i <= str_array.length-1; i++) {
 	 //	alert(str_array[i]);
  		$(wrapper).append('<p> <label class="FormFieldLabelRequired" for="'+str_array[i]+ '" title="'+str_array[i+1]+ '">'+str_array[i+1]+':</label>'+
  				'<input class="text" id="'+str_array[i]+ '"  name="'+str_array[i]+ '" value="'+str_array[i+1]+'" type="text" /></p>');
  ++i;  
  }
}
	
	
	$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
	$("#linkToProperty option[value='${propertyName}']").attr('selected', 'selected');
	$("#linkToTenancy option[value='${tenancyName}']").attr('selected', 'selected');
	
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
	
	

	//end of validation

	function saveForm(){
		
		
		
		loadProgress();
		$("#formDatas").attr("action", "forms/saveCheckingOutForDraft");
		$("#formDatas").submit();
		
		
		
	}

	function submitForm(){
		
		loadProgress();
		$("#formDatas").attr("action", "forms/createCheckingOut");
		$("#formDatas").submit();
		
		
	}

	$(function() {
		
		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy',
			changeYear : true
		});
	});

	



	function propertySelected(id){

		//alert(id);
		
		var propertyName=document.getElementById(id).value;
		
		
		if(propertyName=="0"){
			$('#linkToTenancy').find('option').remove();
			
		}else{
			
			
			$('#linkToTenancy').find('option').remove();
			
		
		$.ajax({
			type : "GET",
			url : "forms/getTenancyList?propertyId=" + propertyName,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				propertySelectCompleted(data);
			}
		});
		}
	}

	function propertySelectCompleted(tenancyList){
		
		
		$('#linkToTenancy')
	    .find('option')
	    .remove();
		
		$('#linkToTenancy').append(new Option("--Select--", "0", true, true));
		
		if(tenancyList!=""){
	        var str_array = tenancyList.split(',');
	      	for(var j = 0; j <= str_array.length-1; j++) {
	        	
	        	
	        	$('#linkToTenancy').append(new Option(str_array[j], str_array[++j], false, false));
	        }
	       
		}
	}
	
	function tenancySelected(id){

		
		var tenancyName=document.getElementById(id).value;
		
		
		if(tenancyName=="0"){
			
			
		}else{
			
			
			
			
		
		$.ajax({
			type : "GET",
			url : "forms/getTenancyDetailsForCheckOutForm?tenancyId=" + tenancyName,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				tenancySelectCompleted(data);
			}
		});
		}
	}
		
function tenancySelectCompleted(tenancyDetails){
	
	var wrapper         = $("#rooms");
		
		if(tenancyDetails!=""){
			var rooms = tenancyDetails.split('-');
			
			var str_array = rooms[0].split(',');
			var str_array1 = rooms[1].split(',');
	        
	      	for(var j = 0; j <= str_array.length-1; j++) {
	     	 	//alert(str_array[j]);
	        	document.getElementById("tenantName").value=str_array[0];
	        	document.getElementById("landlordName").value=str_array[1];
	        	document.getElementById("rentedPropertyAddress").value=str_array[2];
	        	document.getElementById("frontDoorKeyCode").value=str_array[3];
	        	document.getElementById("backDoorKeyCode").value=str_array[4];
	        }
	      	
	      for(var i = 0; i <= str_array1.length-1; i++) {
	     	 	//alert(str_array[i]);
	      		$(wrapper).append('<p> <label class="FormFieldLabelRequired" for="room'+i+'" title="'+str_array1[i]+ '">'+str_array1[i]+':</label>'+
	      				'<input class="text" id="room'+i+'"  name="room'+i+'" value="'+str_array1[i]+'" type="text" /></p>');
	        }
		}
	}

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






</script>
</head>
<body>












<span class="grid-box-header" id="header2title">Checking Out <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2" >
	
		<input type="hidden" value="${checkingOutId }" id="checkingoutId" name="checkingoutId"/>
	
		
		<h4>General Informations</h4>
			<div class="hideFalse">
			<section>
		
		
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToProperty" title="Property">Property:</label>
			<select class="text" id="linkToProperty" name="linkToProperty" onchange="propertySelected('linkToProperty');">
				<option selected="selected" value="0">--Select--</option>
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToTenancy" title="Tenancy">Tenancy:</label>
			<select class="text" id="linkToTenancy" name="linkToTenancy" onchange="tenancySelected('linkToTenancy');">
				
			</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantName" title="Tenant Name">Tenant Names:</label>
				<input class="text" id="tenantName" name="tenantName" value="${tenantName }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="landlordName" title="Landlord Name">Landlord Names:</label>
				<input class="text" id="landlordName" name="landlordName" value="${ landlordName}" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="rentedPropertyAddress" title="Rented Property Address">Rented Property Address:</label>				
				<textarea class="text" cols="43" id="rentedPropertyAddress" name="rentedPropertyAddress" rows="6"> ${rentedPropertyAddress }</textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="nameOfEmployees" title="Name Of Employee">Name Of Employee:</label>
				<input class="text" id="nameOfEmployees" name="nameOfEmployees" value="${nameOfEmployees}" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="checkOutAppointmentTime" title="Checkout Appointment Time">Checkout Appointment Time:</label>
				<input class="text datepicker" id="checkOutAppointmentTime" name="checkOutAppointmentTime" value="${checkOutAppointmentTime }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="agentCompletingCheckout" title="Agent Completing Checkout">Agent completing checkout:</label>
				<input class="text" id="agentCompletingCheckout" name="agentCompletingCheckout" value="${agentCompletingCheckout }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="damage" title="Damage">Damage:</label>				
				<textarea class="text" cols="43" id="damage" name="damage" rows="6">${damage } </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="notes" title="Notes">Notes:</label>				
				<textarea class="text" cols="43" id="notes" name="notes" rows="6">${notes } </textarea>
			</p>
			</section>
			</div>
			
			<h4>Location Of Keys</h4>
			<div class="hideFalse">
			<section>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="frontDoorKeyCode" title="Front Door">Front Door:</label>
				<input class="text" id="frontDoorKeyCode" name="frontDoorKeyCode" value="${frontDoor }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="backDoorKeyCode" title="Back Door">Back Door:</label>
				<input class="text" id="backDoorKeyCode" name="backDoorKeyCode" value="${backDoor }" type="text"/>
			</p>
			
			<div id="rooms"></div>
			
			<!-- <p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 1:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 2:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 3:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 4:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 5:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 6:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 7:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Room 8:</label>
				<input class="text" id="f_5_5" name="f_5_5" type="text"/>
			</p> -->
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantSignature" title="Tenant Signature">Tenant Signature:</label>
				<input  id="tenantSignature" name="tenantSignature" type="checkbox" value="Required"/>
			</p>
			<br/>
			
			
			
			
			<p align="center">
				
				<input class="download" id="f_5_3" name="f_5_3" type="button" value="Generate Taxation Letter" maxlength="50"/>
			</p>
			
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
	
	
	
	



</body>
</html>