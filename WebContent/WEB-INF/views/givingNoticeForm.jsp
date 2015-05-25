<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="uk.co.jmr.sdp.web.util.*"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>




<script type="text/javascript">
var isGivingNoticeStatusfromController;
var global_ValueFromDB_isGivingNotice_equals_y;
<%

ServletContext sc = request.getServletContext();
String test =(String)sc.getAttribute("foldername.localUpload");
System.out.println("The test value is here "+ test);
/*UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");*/
HttpSession session1 = request.getSession();
UserInfo userInfo = (UserInfo) session1.getAttribute("LOGGED_IN_USERINFO");
String currentUserID = String.valueOf(userInfo.getUserId());
System.out.println("The test value is here "+ currentUserID);

%>

function saveForm(){
	//alert("saveForm clicked");
	//alert($("#leavingDate").val());
	if($("#leavingDate").val()==""){
		//alert("inside");
	dialogTemplate("Mandatory", "Please enter leaving date");
	return false;
	}
	
	
	 loadProgress();
	$("#formDatas").attr("action", "forms/createGivingNoticeFormDetail");
	$("#formDatas").submit(); 
	
}

function submitForm(){
	//alert("submitForm clicked");
	if($("#leavingDate").val()==""){
		dialogTemplate("Mandatory", "Please enter leaving date");
		return false;
		}
	
	loadProgress();
	$("#formDatas").attr("action", "forms/createGivingNoticeFormDetail");
	$("#formDatas").submit();
	
}

function mandatoryCheck(){
	$("#leavingDate").val();
}


$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});


function findIsGivingNoticeStatus(currentUser)
{
	var a;
	$.ajax({
		type:"GET",
		url : "forms/getIsGivingNoticeStatus" + "?currentUser="+ currentUser,
		cache : false,
		async:false,
		success : function(data) {
			alert('success part of findIsGivingNoticeStatus');
			isGivingNoticeStatusfromController = data;
			a = data;
			alert("------>"+isGivingNoticeStatusfromController);
		},
		error : function(data) {
		alert('failure part of findIsGivingNoticeStatus');
		alert("Error: " + data);
	}	
});

//alert('');
return(a);
}

function retrieve_ValueFromDB_isGivingNotice_equals_y(currentUser){
	var temp;
	$.ajax({
		type:"GET",
		url : "forms/GivingNoticeEqualsofY" + "?currentUser="+ currentUser,
		cache : false,
		success : function(data) {
			//alert('success part of retrieve_ValueFromDB_isGivingNotice_equals_y');
			temp = data;
			global_ValueFromDB_isGivingNotice_equals_y = data;
			//alert(temp);
		},
		error : function(data) {
		alert('failure part of findIsGivingNoticeStatus');
		alert("Error: " + data);
	}
	
		
	});


//alert('');
return temp;
}



$(document).ready( function () {
	//alert("control in ready");
	
	
				<%-- var b;
				
				findIsGivingNoticeStatus('<%= currentUserID %>');
				//alert("after function call>>>.>>>"+isGivingNoticeStatusfromController);
				b = isGivingNoticeStatusfromController;
				
				//alert("b value is>>>>"+b);
				//alert("isGivingNoticeStatusfromController from else part after call the function>bbbbbbbbbbbbbbb>>"+b);	
			
			if(b)	
			{
					if(b == 'y' || b == 'Y')
					{
						//alert('b not equal to null and equal to y');
						//alert('View Page>>>>before tempValueFromDB_isGivingNotice_equals_y');
						var tempValueFromDB_isGivingNotice_equals_y;
						retrieve_ValueFromDB_isGivingNotice_equals_y('<%= currentUserID %>');
						tempValueFromDB_isGivingNotice_equals_y = global_ValueFromDB_isGivingNotice_equals_y;
						//alert("tempValueFromDB_isGivingNotice_equals_y"+ tempValueFromDB_isGivingNotice_equals_y);
						// Enable view link +++ Hide submit - save - cancel
						$("#cancelFormAttr").hide();
						$("#saveFormAttr").hide();
						$("#submitSaveAttr").hide();
						$("#viewAttr_Link").show();
						
					}
					else if(b == 'user not available') 
					{
						// Enable submit - save - cancel +++ Hide View 
						//document.getElementById("viewAttr_Link").style.visibility = "hidden";
						$("#cancelFormAttr").show();
						$("#saveFormAttr").show();
						$("#submitSaveAttr").show();
						$("#viewAttr_Link").hide();
					}
			}
			else{
				alert('GivingNoticeStatus is undefined');
			}
	 --%>
	  
	}); 


function showInWindow(value1)
{
	/* alert("value1"+value1);
	alert("value2"+ value2); */
	 alert("value1"+value1);
	var url = value1;
		        window.open(url, "pop", "width=400,height=800");
}


function tenancySelected(id){

	
	var tenancyName=document.getElementById(id).value;
	
	
	if(tenancyName=="0"){
		
		
	}else{
		
		
		
		
	
	$.ajax({
		type : "GET",
		url : "forms/getTenancyDetailsForGivingNotice?tenancyId=" + tenancyName,
		cache : false,
		success : function(data) {
			//document.getElementById(id).value="";
			
			
			tenancySelectCompleted(data);
		}
	});
	}
}
	
function tenancySelectCompleted(tenancyDetails){


	
	if(tenancyDetails!=""){
		var rooms = tenancyDetails.split('-');
		
		var str_array = rooms[0].split(',');
	
        
      	for(var j = 0; j <= str_array.length-1; j++) {
     	 	//alert(str_array[j]);
        	document.getElementById("tenantName").value=str_array[0];
        	document.getElementById("address").value=str_array[1];
        	
        }
      	
     
	}
}

function propertySelected(id){

	//alert(id);
	
	var propertyName=document.getElementById(id).value;
	
	
	if(propertyName=="0"){
		$('#tenancyId').find('option').remove();
		
	}else{
		
		
		$('#tenancyId').find('option').remove();
		
	
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
	
	
	$('#tenancyId')
    .find('option')
    .remove();
	
	$('#tenancyId').append(new Option("--Select--", "0", true, true));
	
	if(tenancyList!=""){
        var str_array = tenancyList.split(',');
      	for(var j = 0; j <= str_array.length-1; j++) {
        	
        	
        	$('#tenancyId').append(new Option(str_array[j], str_array[++j], false, false));
        }
       
	}
}

$(document).ready( function () {
	$("#propertyId option[value='${propertyId}']").attr('selected', 'selected');
	$("#tenancyId option[value='${tenancyId}']").attr('selected', 'selected');
	
	}); 
	
// var jsonString=${jsonStringValue};
</script>


<span class="grid-box-header" id="header2title">Giving Notice <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2">
		<%-- <input type="hidden" value="${userFormId }" id="userFormId" name="userFormId"/>
		<input type="hidden" value="${formDefId }" id="formDefId" name="formDefId"/>
		<input type="hidden" value="${currentUser }" id="currentUser" name="currentUser"/> --%>
		
		<input type="hidden" value="${propertyId }" id="propertyId" name="propertyId"/>
		<input type="hidden" value="${tenancyId }" id="tenancyId" name="tenancyId"/>
	<!-- 	<p align="center">
				<b><font size="3" >Giving Notice Form</font></b>
			</p> -->
		
		
			<p>
				<label class="FormFieldLabelRequired" for="property" title="Property ID">Property ID:</label>
				<input class="text" id="property" name="property" type="text" value="${propertyTitle}"/>
				<%-- <input class="text" id="propertyId" name="propertyId" type="text" maxlength="50" value="${propertyId}"/> --%>
				<%-- <select class="text" id="propertyId" name="propertyId" onchange="propertySelected('propertyId');">
				
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select> --%>
			
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancy" title="Tenancy ID"> Tenancy ID:</label>
				<%-- <input class="text" id="tenancyId" name="tenancyId" type="text" value="${tenancyId}"/> --%>
				<%-- <select class="text" id="tenancyId" name="tenancyId" onchange="tenancySelected('tenancyId');">
				<c:forEach var="i" items="${tenancyForms}">
			<option  value="${i.id }">${i.tenantFirstName }</option>
			</c:forEach>
			</select> --%>
			<input class="text" id="tenancy" name="tenancy" type="text" maxlength="50" value="${tenancyTitle}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="rentaccountsId" title="Rent Accounts ID">Rent Accounts ID:</label>
				<input class="text" id="rentaccountsId" name="rentaccountsId" type="text" maxlength="50" value="${rentaccountsId}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantName" title="TenantName">TenantName:</label>
				<input class="text" id="tenantName" name="tenantName" type="text" value="${tenantName}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="address" title="Address">Address:</label>				
				<textarea cols="43" rows="6" class="text" id="address" name="address" >${address}</textarea> 
			</p>
			
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="leavingDate" title="leavingDate">Leaving Date:</label>
				<input class="text datepicker" id="leavingDate" name="leavingDate" value="${leavingDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="forwardAddress" title="Forward Address">Forward Address:</label>
				<input class="text" id="forwardAddress" name="forwardAddress" type="text" value="${forwardAddress}"/>
			</p>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="accountName" title="Account Name">Account Name:</label>
				<input class="text" id="accountName" name="accountName" type="text"  value="${accountName}"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="accountNumber" title="Account Number">Account Number:</label>
				<input class="text" id="accountNumber" name="accountNumber" type="text" value="${accountNumber}"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="sortCode" title="Sort Code">Sort Code:</label>
				<input class="text" id="sortCode" name="sortCode" type="text" value="${sortCode}" />
			</p>
			
			
			
		</div>
</form>
		<div id="saveSubmit" align="center">
				<!-- <span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="homeClicked();"></span> -->
					<!-- <span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span> -->
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
					
					<!-- <span id="viewAttr_Link"><input class="download" type="button" value="View" onclick="showInWindow('forms/viewGivingNoticeForm')"/>
					</span> -->
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

