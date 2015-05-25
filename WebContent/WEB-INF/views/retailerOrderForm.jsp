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

var total;

if("${totals}"){
	
	total=parseInt("${totals}");
	//alert(total);
}
else{
	total=0;
}
/* $(document).ready(function() {
	var k=2;
	var rowCount=parseInt("${tableRows}");
	var tableValues="${tableValues}";
	var finalval = tableValues.split(",");
	for(var j=0 ; j<finalval.length-1;j++){
	//alert(j);
	
	
	
	if(j < rowCount){
		if(rowCount%3==0){
 	$('#productOrderTbl tr').eq(-2).after('<tr class="even"><td><input style="width: 100%;" class="text" value="'+finalval[j+2]+'" name="productCodeToBeOrdered'+k
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text" value="'+finalval[j+3]+'" name="quantity'+k
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" value="'+finalval[j]+'" name="price'+k+'"</td></tr>');
	
	j=j+2;
		}
		else if(rowCount%2==0){
		 	$('#productOrderTbl tr').eq(-2).after('<tr class="even"><td><input style="width: 100%;" class="text" value="'+finalval[j+2]+'" name="productCodeToBeOrdered'+k
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text" value="'+finalval[j+4]+'" name="quantity'+k
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" value="'+finalval[j]+'" name="price'+k+'"</td></tr>');
			
			j=j+2;
				}
	}
	
         
  	}
}); */

$(document).ready(function() {
	var k=2;
	var rowCount=parseInt("${tableRows}");
	var tableValues="${tableValues}";
	var finalval = tableValues.split(",");
	for(var j=0 ; j<rowCount;j++){
	//alert(j);
	
	if(j%2==0){
 	$('#productOrderTbl tr').eq(-2).after('<tr class="even"><td><input style="width: 100%;" class="text" id="productCodeToBeOrdered'+k+'"  name="productCodeToBeOrdered'+k
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text" id="quantity'+k+'"  name="quantity'+k
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" id="price'+k+'"  name="price'+k+'"/></td></tr>');
	  k++;
	}else{
		$('#productOrderTbl tr').eq(-2).after('<tr class="odd"><td><input style="width: 100%;" class="text" id="productCodeToBeOrdered'+k+'"  name="productCodeToBeOrdered'+k
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text" id="quantity'+k+'"  name="quantity'+k
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" id="price'+k+'"  name="price'+k+'"/></td></tr>');
			  k++;
	}
	}
	
	var val;
	var spl;
	for(var i=0;i<finalval.length-1;i++){
		
		val=finalval[i];
		//alert(val);
		spl=val.split("-");
		for(var l=0;l<spl.length-1;l++){
			//alert(spl[l]);
			document.getElementById(spl[l]).value=spl[l+1];
		}
	}
	
});


$(document).ready(function() {
	
	var rowCount=parseInt("${tableRows}");
	
	
	
	var cnt;
	
	if(rowCount=="" || isNaN(rowCount)){
		cnt=2;
	}else{
		//alert(rowCount);
		cnt=rowCount+2;
	}

	  calcFunc();
	

	$("#addRo").click(function(){
   

 // alert(cnt);
    
   if(cnt%2==0){
	   //alert("if");
    $('#productOrderTbl tr').eq(-2).after('<tr class="even"><td><input style="width: 100%;" class="text" id="productCodeToBeOrdered'+cnt
   		+	'" name="productCodeToBeOrdered'+cnt
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text"  id="quantity'+cnt
		+	'" name="quantity'+cnt
		+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" id="price'+cnt
		+	'" name="price'+cnt+'"></td></tr>');
    cnt++;
   }else{
	   //alert("else");
	   $('#productOrderTbl tr').eq(-2).after('<tr class="odd"><td><input style="width: 100%;" class="text" id="productCodeToBeOrdered'+cnt
		   		+	'" name="productCodeToBeOrdered'+cnt
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" class="text"  id="quantity'+cnt
				+	'" name="quantity'+cnt
				+	'" type="text" maxlength="50" /></td><td><input style="width: 66px;" type="text" id="price'+cnt
				+	'" name="price'+cnt+'"></td></tr>');
		    cnt++;
   }
   calcFunc();

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

$(document).ready(function() {
	
	var	termsAndConditions="${termsAndConditions}";

		
		$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
		
		if(termsAndConditions!="" && termsAndConditions!=null && termsAndConditions=="Required"){
		$( "#termsAndConditions" ).prop( "checked", true );
		}
		
	$("#preferredMethodOfPayment option[value='${preferredMethodOfPayment}']").attr('selected', 'selected');
	$("#orderStatus option[value='${orderStatus}']").attr('selected', 'selected');
	
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
	$("#formDatas").attr("action", "formsNew/saveRetailerOrderForDraft");
	$("#formDatas").submit();
	
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

// $(document).ready(function() {
// 	   $('input').change(function(e)  {
// 	        
// 	        var qty = parseFloat($('#quantity1').val());
// 	        var price = parseFloat($('#price1').val());
	       
// 	        total = qty * price;
// 	        alert(total);
// 	        if(isNaN(total)){
// 	        	//alert("total");
// 	        	total="";
// 	        }
// 	        $('#totals').val( total);
// 	    });
// 	   });

/* $(document).ready(function() {
	   var total = 0;
	   $('td:nth-child(n+3)').change(function(e)  {
	       
		    alert($(this).val());
	        var price = parseFloat($(this).val());
	        var qty = parseFloat($(this).prev().val());
	       
	        total += qty * price;
	        alert(total);
	        if(isNaN(total)){
	        	//alert("total");
	        	total="";
	        }
	        $('#totals').val( total);
	    });
	   }); */


/* function calcFunc(){
		 
		   
    $('table tr td:last-child').change(function()  {
        var priceId="#"+$(this)[0].childNodes[0].id;
		var qtyId="#"+$(this).prev()[0].childNodes[0].id;
        var price =parseInt($(priceId).val());
        var qty =parseInt($(qtyId).val());
		total += qty * price;
        if(isNaN(total)){
        	//alert("total");
        	total="";
        }
        $('#totals').val( total);
    });
	} */
	var tot={};
function calcFunc(){
		
		function calc(priceId,qtyId){
			var total;
			if("${totals}"){
				
				var tableValues="${tableValues}";
				var finalval = tableValues.split(",");
				alert(tableValues.toString());
				total=parseInt("${totals}");
			}
			else{
				total=0;
			}
	        var price =parseInt($(priceId).val());
	        var qty =parseInt($(qtyId).val());
			if(isNaN(qty * price)){
	        	tot[priceId]=0;
	        }
			else{
				tot[priceId]=(qty * price);
				}
			for(x in tot){
				total+=tot[x];
			}
	        $('#totals').val( total);
		}
		
		$('table tr td:last-child').prev().change(function()  {
	        var qtyId="#"+$(this)[0].childNodes[0].id;
			var priceId="#"+$(this).next()[0].childNodes[0].id;
	        calc(priceId,qtyId);
	    });

	    $('table tr td:last-child').change(function()  {
			var priceId="#"+$(this)[0].childNodes[0].id;
			var qtyId="#"+$(this).prev()[0].childNodes[0].id;
			calc(priceId,qtyId);
	    });
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
				<b><font size="3" >Standard Product Retailer Order Form</font></b>
			</p>
			
			<h4><span class="glyphicon glyphicon-triangle-bottom"></span>  Retailer Details</h4>
			<div class="hideFalse">
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="termsAndConditions" title="Terms">Terms and Conditions:</label>
				<input id="termsAndConditions" name="termsAndConditions"  type="checkbox" value="Required"/>
			</p>
			
			<br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="title" title="Title">Title:</label>
				<input class="text" id="title" name="title" type="text" value="${ title}" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="firstName" title="First Name">First Name:</label>
				<input class="text" id="firstName" name="firstName" type="text" value="${ firstName}" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="lastName" title="Last Name">Last Name:</label>
				<input class="text" id="lastName" name="lastName" type="text" value="${ lastName}" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="companyName" title="Company Name">Company Name:</label>
				<input class="text" id="companyName" name="companyName" type="text" value="${companyName}" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="contactNumber" title="Contact Number">Contact Number:</label>
				<input class="text" id="contactNumber" name="contactNumber" value="${contactNumber}" type="text" maxlength="15"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="mobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="mobileNumber" name="mobileNumber" type="text" value="${mobileNumber}" maxlength="15"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="emailAddress" title="Email Address">Email Address:</label>
				<input class="text" id="emailAddress" name="emailAddress" type="text" value="${emailAddress}" maxlength="15"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="billingAddress" title="Billing Address">Billing Address:</label>
				<textarea class="text" cols="43" id="billingAddress" name="billingAddress" rows="6">${billingAddress } </textarea>
			</p>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="shippingAddress" title="Shipping Address">Shipping Address:</label>
				<textarea class="text" cols="43" id="shippingAddress" name="shippingAddress" rows="6">${shippingAddress } </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="notes" title=" Notes">Notes:</label>
				<textarea class="text" cols="43" id="notes" name="notes" rows="6">${notes} </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="preferredMethodOfPayment" title=" Method of Payment">Preferred Method of Payment</label>
				<select class="text" id="preferredMethodOfPayment" name="preferredMethodOfPayment">
				<option value="-1">--Select--</option>
				<option value="Cash">Cash</option>
				<option value="Card">Card</option>
				<option value="Paypal">Paypal</option>
				<option value="Other">Other</option> 
				</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="paymentDetails" title=" Payment Details">Payment Details:</label>
				<input class="text" id="paymentDetails" name="paymentDetails" value="${paymentDetails}" type="text" maxlength="15"/>
			</p>
			<br/>
			</section>
			
			<section>
			<p>
			<input type="button" value="Add Row" class="download" id="addRo">
			</p>
			
			<table class="display" id="productOrderTbl">
					<thead>
						<tr>
							<th>Product Code To Be Ordered</th>
							<th>Quantity</th>
							<th>Price</th>
						</tr>
					</thead>
					
					<tbody>
						<tr class="odd">
							<td><input style="width: 100%;" class="text" value="${ productCodeToBeOrdered}" id="productCodeToBeOrdered1" name="productCodeToBeOrdered1"
								type="text" maxlength="50" /></td>
							<td><input class="text" style="width: 66px;" id="quantity1" value="${ quantity}" name="quantity1"
								type="text" maxlength="50" /></td>
							<td><input class="text" style="width: 66px;" value="${ price}" id="price1" name="price1"
								type="text" maxlength="50" /></td>
						</tr>
						
					
					</tbody>
					<tfoot>
					<tr>
						<td align="right">Totals</td>
						<td><input class="text" id="totals" value="${ totals}" style="width: 66px;" readonly="readonly" name="totals"
								type="text" maxlength="50" /></td>
						
						</tr>
					</tfoot>
				</table>
				
				
				<p>
				<label class="FormFieldLabelRequired" for="orderStatus" title="Order Status">Order Status</label>
				<select class="text" id="orderStatus" name="orderStatus">
				<option value="-1">--Select--</option>
				<option value="Dispatched">Dispatched</option>
				<option value="Received">Received</option>
				</select>
			</p>
				
				<p>
			<input type="button" class="download" value="Generate Invoice"/>
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
	
	
	
	

