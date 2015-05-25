<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="uk.co.jmr.sdp.web.util.*"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>




<script type="text/javascript">

function tenancyMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showTenancy" + "?tenancyId="
				+ id,
		cache : false,
		success : function(data) {
			 $("#search").hide();	
			//Next 3 lines for properties page to Dashboard Page
			 $('#csvDownloadSearch').hide();
			$('#dynContentMetadata').hide();
			
			$("#header2").show();				
			$("#dynContent").html(data);
			$('#dynContent').show();
			$("#header3").show();
			
		}
	});
}


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

function tenancyMetaData(id){
	//alert(id);
	$.ajax({
		type : "GET",
		url : "forms/showTenancy" + "?tenancyId="
				+ id,
		cache : false,
		success : function(data) {
			 $("#search").hide();	
			//Next 3 lines for properties page to Dashboard Page
			 $('#csvDownloadSearch').hide();
			$('#dynContentMetadata').hide();
			
			$("#header2").show();				
			$("#dynContent").html(data);
			$('#dynContent').show();
			$("#header3").show();
			
		}
	});
}

function saveForm(){
	//alert("saveForm clicked");
	loadProgress();
	$("#formDatas").attr("action", "forms/createRentReceiptDetail");
	$("#formDatas").submit();
	tenancyMetaData($("#tenancyId").val());
}

function submitForm(){
//	alert($("#tenancyId").val());
	loadProgress();
	$("#formDatas").attr("action", "forms/createRentReceiptDetail");
	$("#formDatas").submit();
	//tenancyMetaData($("#tenancyId").val());
}


function CancelForm(){
	//alert("CancelForm clicked");
	loadProgress();
	$("#formDatas").attr("action", "forms/cancelRentReceiptDetail");
	$("#formDatas").submit();
}

function showInWindow()
{
//viewGivingNoticeForm
}
$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});


$(document).ready( function () {
	$("#propertyId option[value='${propertyId}']").attr('selected', 'selected');
	$("#tenancyId option[value='${tenancyId}']").attr('selected', 'selected');
	
	}); 

function showInWindow(value1, value2)
{
	/* alert("value1"+value1);
	alert("value2"+ value2); */
	var url = value1;
		        window.open(url, "pop", "width=400,height=800");
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
	
// var jsonString=${jsonStringValue};
</script>


<span class="grid-box-header" id="header2title">Rent Account <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" >
		
		<!-- <p align="center">
				<b><font size="3" >Rent Receipt Form</font></b>
			</p> -->
		
			<p>
				<label class="FormFieldLabelRequired" for="propertyId" title="Property ID">Property ID:</label>
				<%-- <input class="text" id="propertyId" name="propertyId" type="text" maxlength="50" value="${propertyId}"/> --%>
				<select class="text" id="propertyId" name="propertyId" onchange="propertySelected('propertyId');">
				
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyId" title="Tenancy ID"> Tenancy ID:</label>
				<%-- <input class="text" id="tenancyId" name="tenancyId" type="text" value="${tenancyId}"/> --%>
				<select class="text" id="tenancyId" name="tenancyId" >
				<c:forEach var="i" items="${tenancyForms}">
			<option  value="${i.id }">${i.tenantFirstName }</option>
			</c:forEach>
			</select>
			</p>
			
			
			
				<p>
				<label class="FormFieldLabelRequired" for="f_9_4" title="Product Name"> Payment Type:</label>
				<select class="text" id="paymenttype" name="paymenttype">
				<option value="nil">Select</option> 
				<option value="Cheque">Cheque</option>
				<option value="Transfer ">Transfer</option>
				<option value="Cash">Cash</option>
				</select>
				<c:out value="${paymenttype}"></c:out><br/>
				</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="reference" title="reference">Payment Reference :</label>
				<input class="text" id="reference" name="reference" type="text" value="${reference}"/>
			</p>
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="pay_date" title="pay_date">Payment Date:</label>
				<input class="text datepicker" id="pay_date" name="pay_date" value="${pay_date }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="rent_amount" title="rent_amount">Rent Amount:</label>
				<input class="text" id="rent_amount" name="rent_amount" type="text" value="${rent_amount}"/>
			</p>
			
			
			
			
			
		</div>
</form>
		<div id="saveSubmit" align="center">
		
		<span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="tenancyMetaData('${tenancyId}');"></span>
				<!-- <span><input
					type="button" class="download" id="cancelFormAttr"
					value="cancel" onclick="CancelForm();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span> -->
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
					
					<%-- <span id="viewAttr_Link"><input class="download" type="button" value="View History" onclick="showInWindow('formsNew/viewRentPaymentTransactionHistorybyUserID?userId=', '<%=currentUserID %>')"/>
					</span> --%>
					
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

