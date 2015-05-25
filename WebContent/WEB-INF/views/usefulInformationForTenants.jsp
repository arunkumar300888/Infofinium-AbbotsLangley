<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>
<style>
div>b{
	float:left;
	width:50%
}

.rounded-box{
	background-color:#e4e4e4;
	border:1px;
	border-radius: 10px;
	width:30%;
	height:260px;
	margin-left:3%;
	float:left;
}
</style>

<script type="text/javascript">

function editFunction(){
	$("#modalForm").dialog({
		 width: 800,
		modal:true,
		buttons:{
			Update:function(){
				 $("#formDatas").attr("action", "forms/saveUsefulInfoForTenants");
				$("#formDatas").submit(); 
				
				/* $.ajax({
					type : "POST",
					url : "forms/saveUsefulInfoForTenants" ,
					cache : false,
					data : $('#formDatas').serialize(),
					success : function(data) {
						 $("#search").hide();	
						//Next 3 lines for properties page to Dashboard Page
						 $('#csvDownloadSearch').hide();
						$('#dynContentMetadata').hide();
						
						$("#header2").show();				
						$("#dynContent").html(data);
						$('#dynContent').show();
						$("#header3").show();
						//removeProgress();
					}
				}); */
				$(this).dialog("close");
			}
		}
	});
}

var details={companyName:'Abbots'};
$(document).ready(function(){
	$("#companyName").text(details.companyName);
});

function saveForm(){
	loadProgress();
	$("#formDatas").attr("action", "formsNew/createUsefulInformationforTenants");
	$("#formDatas").submit();
	
	
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});

//var jsonString=${jsonStringValue};
</script>
<div id="modalForm" style="display:none" title="Useful Information For Tenants">
			<form method="post" id="formDatas">
			<p align="left">
				<b><font size="3" >Company Details</font></b>
			</p><br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="companyName" title="Landline Number">Company Name</label>
				<input class="text" id="companyName" name="companyName" type="text" maxlength="50" value="${companyName}" /><br/>
				<label class="FormFieldLabelRequired" for="telephoneNumber" title="Product Name">Telephone number</label>
				<input class="text" id="telephoneNumber" name="telephoneNumber" type="text" maxlength="50" value="${telephoneNumber}"/><br/>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Product Name">Email Address</label>
				<input class="text" id="emailAddress" name="emailAddress" type="text" maxlength="50" value="${emailAddress}"/><br/>
				<label class="FormFieldLabelRequired" for="address" title="Product Name">Company Address</label>
				<input type="text" class="text" id="address" name="address" value="${address}" placeholder="${address}"/><br/>
			</p>
			<br/>
			<p align="left">
				<b><font size="3" >Payment Details</font></b>
			</p><br/>
			
			<p>
			
				<label class="FormFieldLabelRequired" for="instructions" title="Landline Number">Information on how to reference their payment</label>
				<input class="text" id="instructions" name="instructions" type="text" value="${instructions }"/>
			<br/>
				<label class="FormFieldLabelRequired" for="companyContactDetails" title="Product Name">Company Bank Account Details</label>
				<input class="text" type="text" id="companyContactDetails" name="companyContactDetails" value="${companyContactDetails }"/><br/>
				
		</p>		
			<br/>
			
			
			<p align="left">
				<b><font size="3" >Useful Contact Details</font></b>
			</p><br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_9_6" title="Product Name">Maintenance Query</label>
				<input type="text" class="text" id="maintenanceQuery" name="maintenanceQuery"  value="${maintenanceQuery}"> <br/>	
				<label class="FormFieldLabelRequired" for="f_9_6" title="Product Name">Rent Query</label>
				<input type="text" class="text"  id="rentQuery" name="rentQuery"  value="${rentQuery}" > <br/>
			</p>
			</form>
</div>



		<div >
		<div class="grid-box-header">USEFUL INFORMATION FOR TENANTS
		<input type="button" onclick="editFunction()" style="margin-left:5%" class="download float-right" value="Edit">
		</div>
		<div style="margin-top: 2%;margin-bottom:2%;margin-right:2%">
			<div class="rounded-box"><br/>
				<center><h3 style="color:red">Company Details</h3></center><br/>
				<div style="margin-left:5%">
					<b>Comapny Name:</b><div style="float:left"> ${companyName }</div><br/><br/>
					<b>Telephone:</b><div style="float:left"> ${telephoneNumber }</div><br/><br/>
					<b>Email:</b><div style="float:left">${emailAddress }</div><br/><br/>
					<b>Address:</b><div style="float:left">${address }</div><br style="clear: left;" /><br/>
				</div>
			</div>
			<div class="rounded-box"><br/>
				<center><h3 style="color:red">Payment Details</h3></center><br/>
				<div style="margin-left:5%">
					<b>Payment Reference:</b><div style="float:left"> ${instructions }</div><br/><br/>
					<b>Bank Account Details:</b><div style="float:left"> ${companyContactDetails }</div>
				</div>
			</div>
			<div class="rounded-box"><br/>
				<center><h3 style="color:red">Useful Contact Details</h3></center><br/>
				<div style="margin-left:5%">
					<b>Maintenance Query:</b><div style="float:left"> ${maintenanceQuery }<br/></div><br/><br/>
					<b>Rent Query:</b><div style="float:left"> ${rentQuery }</div><br/><br/>
				</div>
			</div>
			<br style="clear: left;" />
		</div>

	
	
	
	

