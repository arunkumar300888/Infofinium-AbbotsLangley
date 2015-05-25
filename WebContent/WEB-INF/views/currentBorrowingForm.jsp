<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>


<script type="text/javascript">
	
	sApp.controller('borrowCtrl',function($scope){
		$scope.saveForm=function(){
			alert("inside save");
		}
	});
</script>

<script type="text/javascript">



$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});

	
</script>

<span class="grid-box-header" id="header2title">Form Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<div ng-controller="borrowCtrl">
{{1+1}}
<form>
<!-- <input type="hidden" value="" id="borrow" name="borrow"/> -->
		<div id="at2">
		
				<p align="center">
				<b><font size="3" >Current Borrowing Form</font></b>
			</p>
			
			<table class="display">
					<thead>
						<tr>
							<th>Property Address</th>
							<th>Property value</th>
							<th>Annual Rental Income</th>
							<th>Lender</th>
							<th>Account Number</th>
							<th>Loan Amount</th>
							<th>Monthly Repayments</th>
						</tr>
					</thead>
					
					<tbody>
						<tr class="odd">
							<td><input class="text" id="f_8_1" name="f_8_1"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_2" name="f_8_2"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_3" name="f_8_3"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_4" name="f_8_4"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_5" name="f_8_5"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_6" name="f_8_6"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_6" name="f_8_6"
								type="text" maxlength="10" /></td>
						</tr>
						<tr class="even">
							<td><input class="text" id="f_8_7" name="f_8_7"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_8" name="f_8_8"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_9" name="f_8_9"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_10" name="f_8_10"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_11" name="f_8_11"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_12" name="f_8_12"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_13" name="f_8_13"
								type="text" maxlength="10" /></td>
						</tr>
						<!-- <tr class="odd">
							<td><input class="text" id="f_8_14" name="f_8_14"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_15" name="f_8_15"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_36" name="f_8_36"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_37" name="f_8_37"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_38" name="f_8_38"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_39" name="f_8_39"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_8_40" name="f_8_40"
								type="text" maxlength="10" /></td>
						</tr> -->
					</tbody>
				</table>
			<br/>
			<p>
				<label class="FormFieldLabelRequired" for="f_8_14" title="Product Name">Total Repayments:</label>
				<input class="text" ng-model="borrow.f_8_14" id="f_8_14" name="f_8_14" type="text" maxlength="50"/>
			</p>
			<br/>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="f_8_15" title="Product Code">Total Mortgages:</label>
				<input class="text" id="f_8_15" ng-model="borrow.f_8_15" name="f_8_15" type="text" maxlength="10"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="f_8_16" title="Product Description">Total Business Loans:</label>
				<input class="text" id="f_8_16" ng-model="borrow.f_8_16" name="f_8_16" type="text" maxlength="50"/><br/>
				</p>
				<p>
				<label class="FormFieldLabelRequired" for="f_8_17" title="Product Description">Total Borrowings:</label>
				<input class="text" id="f_8_17" name="f_8_17" ng-model="borrow.f_8_17" type="text" maxlength="50"/>
			</p>
			
			
			
			<c:choose>
						<c:when test="${secGroups.size() == 1}">
						<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" name="secGrp" ng-model="borrow.secGrp">
			<c:forEach var="i" items="${secGroups}">
			<option selected="selected" value="${i.securityGroup.id }">${i.securityGroup.name }</option>
			</c:forEach>
			</select><br/>
			</p>
						</c:when>
						<c:otherwise>
			<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" ng-model="borrow.secGrp" name="secGrp">
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
			<select class="text" id="compGrp" ng-model="borrow.compGrp" name="compGrp">
			<c:forEach var="i" items="${compGroups}">
			<option selected="selected" value="${i.id }">${i.value }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="comp">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Company</label>
			<select class="text" id="compGrp" ng-model="borrow.compGrp" name="compGrp">
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
			<select class="text" id="wrkFlw" ng-model="borrow.wrkFlw" name="wrkFlw">
			<c:forEach var="i" items="${workflows}">
			<option selected="selected" value="${i.id }">${i.name }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="workflow">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Workflow</label>
			<select class="text" id="wrkFlw" ng-model="borrow.wrkFlw" name="wrkFlw">
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
				<input class="text datepicker" ng-model="borrow.submissionDate" id="submissionDate" name="submissionDate" value="${submissionDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			
			</div>
<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="cancel" onclick="homeClicked();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm(borrow);"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
				</div>
		
</form>


</div>
		
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

