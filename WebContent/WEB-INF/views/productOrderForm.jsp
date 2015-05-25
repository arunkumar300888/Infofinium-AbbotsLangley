<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>



<script type="text/javascript">

function saveForm(){
	
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});

// var jsonString=${jsonStringValue};
</script>
 

<span class="grid-box-header" id="header2title">Form Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2">
		
			<p align="center">
				<b><font size="3" >Custom Product Order Form</font></b>
			</p>
			<br/>
			<b><font size="3" >Custom Details </font></b>
			<br/><br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_2" title="Title">Title:</label>
				<input class="text" id="f_17_2" name="f_17_2" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="First Name">First Name:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_4" title="Last Name">Last Name:</label>
				<input class="text" id="f_17_4" name="f_17_4" type="text" maxlength="10"/>
			</p>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_6" title="Contact Number">Contact Number:</label>
				<input class="text" id="f_17_6" name="f_17_6" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_7" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="f_17_7" name="f_17_7" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Email Address">Email Address:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Billing Address">Billing Address:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Delivery Address">Delivery Address:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Hand Delivered">Hand Delivered?</label>
				<input checked="checked" id="f_16_1" name="f_16_1" onchange="frmCbChanged(this);" type="checkbox" value="Required"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Customer Notes">Customer Notes:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title=" Method of Payment">Preferred Method of Payment</label>
				<select class="text" id="f_13_5">
				<option value="Cash">Cash</option>
				<option value="Card">Card</option>
				<option value="Paypal">Paypal</option>
				<option value="Other">Other</option> 
				</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title=" Payment Details">Payment Details:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/>
				<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title=" Product Information">Product Information:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Colours">Colours:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
						
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Pictures">Product Information:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="file" />
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Budget">Budget:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Date Required">Date Item Required?</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Wedding Date">Wedding/Event Date:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/>
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Budget">Customer Signature:</label>
				<input checked="checked" id="f_16_1" name="f_16_1" onchange="frmCbChanged(this);" type="checkbox" value="Required"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Date Signed">Date Signed:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/>
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Price">Price:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Money Reeceived Date">Money Received Date:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Delivery Address for Rai Jwellery">Delivery Address for Rai Jwellery:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/>
			<b><font size="3" >Amendments or Modifications</font></b>
			<br/><br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Product Information">Product information:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="colours">colours:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Pictures">Pictures:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Date Item Required">Date Item Required?</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<br/>
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Budget">Customer Signature:</label>
				<input checked="checked" id="f_16_1" name="f_16_1" onchange="frmCbChanged(this);" type="checkbox" value="Required"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Date Signed">Date Signed:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="New Payment Details">New Payment Details:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_17_3" title="Additional Cost">Additional Cost:</label>
				<input class="text" id="f_17_3" name="f_17_3" type="text" maxlength="10"/>
			</p>
			<br/><br/>
			<input type="button" class="download" value="Generate Invoice"/>
			
			
			
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
		<p align="left" id="comp">
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
		<p align="left" id="workflow">
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
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate" value="${submissionDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			
			
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
	
	
	
	

