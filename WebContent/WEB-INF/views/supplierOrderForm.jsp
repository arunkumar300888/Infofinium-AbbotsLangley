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
 <script>
	/* $(function() {
		
		var isVisibleQupl = $('#attr2qUpl').is(':visible');		
		if(isVisibleQupl)
			{
		
		$('#attr2qUpl option[value="2"]').attr("selected",true);	
		attr2qUplSelected();
		$("#attr2qUpl").attr('disabled','disabled');
			}
	
		else
			{
			$('#attr2Upl option[value="3"]').attr("selected",true);	
			attr2UplSelected();
			$("#attr2Upl").attr('disabled','disabled');
			}
			
	});	 */
	
	
	   /* $(function(){
		
		   alert("----->"+jsonString);
		if(jsonString!=""){
		var ncontrolsVal=jsonString.controlsVal;
		for(i=0; i<ncontrolsVal.length; i++){			
			if(ncontrolsVal[i].controlType=="text")
			{
				$('#'+ncontrolsVal[i].controlId).val(ncontrolsVal[i].controlValue);
			}
			else if(ncontrolsVal[i].controlType=="radio"){
				$('#'+ncontrolsVal[i].controlId).attr("checked","checked");
			}else if(ncontrolsVal[i].controlType=="textarea"){
				$('#'+ncontrolsVal[i].controlId).val(ncontrolsVal[i].controlValue);
			}else if(ncontrolsVal[i].controlType=="select"){
				$('#'+ncontrolsVal[i].controlId).val(ncontrolsVal[i].controlValue);
			}else if(ncontrolsVal[i].controlType=="check"){
				$('#'+ncontrolsVal[i].controlId).attr("checked","checked");
			}
		}
		}
	});    */
	
	$(function(){
	    $("#reportingPersonDiv").hide();
	    $("#rpdr_cbox").on("click", function(){
	        $("#reportingPersonDiv").toggle();
	    });
	});
	
	/* jQuery(document).ready(function(){
		jQuery("#workFlow_selects,#secGrp_selects").multiselect().multiselectFilter(); 
		}); */
	
	$(function(){
	    $("#firstAidDiv").hide();
	    $("#ifaa_cbox").on("click", function(){
	        $("#firstAidDiv").toggle();
	    });
	});
	
	jQuery('#dt_timeStamp').datetimepicker({
		theme : 'default' 
	});
	
	$("#rfs_radio").change(function () {
		$("#rfo_radio").prop("checked", false);
		$("#rfs_radio").attr('checked', 'checked');
	});
	
	$("#rfo_radio").change(function () {
		$("#rfs_radio").prop("checked", false);
		$("#rfo_radio").attr('checked', 'checked');
	});
	
	
	 jQuery(document).ready(function(){
		jQuery("#workFlow_selects").chosen();
	}); 
	
	
	
	 jQuery(document).ready(function(){
		//alert("radio");
		if($('#rfo_radio').is(':checked')){
			$("#rfo_radio").attr('checked', 'checked');
			$("#rfs_radio").prop("checked", false);
		}else if($('#rfs_radio').is(':checked')){
			$("#rfs_radio").attr('checked', 'checked');
			$("#rfo_radio").prop("checked", false);
		}else {
			$("#rfo_radio").attr('checked', 'checked');
			$("#rfs_radio").prop("checked", false);
		}
			
			  }); 
	jQuery(document).ready(function(){
	if($('#ifaa_cbox').is(':checked')){
		$("#firstAidDiv").toggle();;
	}else if($('#rpdr_cbox').is(':checked')){
		 $("#reportingPersonDiv").toggle();
	}
	});
</script>
<c:if test="${canShow==true }">
<span class="grid-box-header" id="header2title">Form Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>

</c:if>
<form method="post" id="formDatas">
		<div id="at2">
		<%-- <input type="hidden" value="${secGrp }" id="secGrpName" name="secGrpName"/>
		<input type="hidden" value="${workFlowName }" id="wfName" name="wfName"/>
		<input type="hidden" value="${kWord }" id="kwyWord" name="keyWord"/>
		<input type="hidden" value="${formType }" id="formName" name="formName"/> --%>
		<%-- <div id="otherDiv">
		<input type="hidden" value="${formUserId }" id="formUserId_hidden" name="formUserId_hidden"/>
		<input type="hidden" value="${formType }" id="formName_hidden" name="formName_hidden"/>
								<span width="123"><label>Keywords<font
										color="red">*</font></label></span>
								<span><input name="keyword_text" id="keyword_text"
										maxlength="40" class="text" /> <img
										title="Please select keywords carefully as they create part of the file name. Only characters A -Z, 0 -9 and _ are allowed."
										src="resources/images/comment_question.png" /> </span><br>
									
									<label title="Form" for="">Select Security Group<font
										color="red">*</font></label>	
									<select multiple="multiple" name="secGrp_selects" id="secGrp_selects"   class="text">
									<option value="-1" >--Select--</option>
									<option value="0" >Open</option>
									<c:forEach var="j" items="${secGrps}">
										<option title="${j.name}" value="${j.id}">${j.name}</option>
									</c:forEach>
								</select><br/>
								
								<label title="Form" for="">Select Workflow<font
										color="red">*</font></label>	
									<select multiple="multiple" name="workFlow_selects" id="workFlow_selects"   class="text">
									<option value="-1" >--Select--</option>
									
									<c:forEach var="k" items="${wfs}">
										<option title="${k.name}" value="${k.id}">${k.name}</option>
									</c:forEach>
								</select><br/>
								</div>  --%>
		<p align="center">
				<b><font size="3" >Supplier Order Form</font></b>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_16_1" title="Date">Date:</label>
				<input class="text" id="f_16_1" name="f_16_1" type="text"/>
			</p>
			<br/>
			<table class="display">
					<thead>
						<tr>
							<th>Product Code</th>
							<th>Quantity</th>
							<th>Shipping Address</th>
						</tr>
					</thead>
					
					<tbody>
						<tr class="odd">
							<td><input class="text" id="f_16_2" name="f_16_2"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_16_3" name="f_16_3"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_16_4" name="f_16_4"
								type="text" maxlength="10" /></td>
						</tr>
						<tr class="even">
							<td><input class="text" id="f_16_5" name="f_16_5"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_16_6" name="f_16_6"
								type="text" maxlength="10" /></td>
							<td><input class="text" id="f_16_7" name="f_16_7"
								type="text" maxlength="10" /></td>
						</tr>
					</tbody>
				</table>
			<br/>
			<p>
				<label class="FormFieldLabelRequired" for="f_16_8" title="Total">Total:</label>
				<input class="text" id="f_16_8" name="f_16_8" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_16_9" title="Total">Total:</label>
				<input class="text" id="f_16_9" name="f_16_9" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_16_10" title="Payment Details">Payment Details:</label>				
				<textarea class="text" cols="50" id="f_16_10" name="f_16_10" rows="3"> </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_16_11" title="Total">Total:</label>
				<input class="text" id="f_16_11" name="f_16_11" type="text" maxlength="50"/>
			</p>
			<br/>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_16_12" title="Upload Invoice">Link to Invoice:</label>
				<input class="text" id="f_16_12" name="f_16_12" type="text" maxlength="50"/>
			</p>
			
			<p>	
				<label class="FormFieldLabelRequired" for="f_16_13" title="Date Items Received">Date Items Received:</label>
				<input class="text" id="f_16_13" name="f_16_13" type="text" maxlength="50"/>
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
	
	
	
	

