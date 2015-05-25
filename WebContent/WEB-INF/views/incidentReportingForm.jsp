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
		<p>
				<label class="FormFieldLabel" for="rfo_radio" title="Request for">Request for:</label>
				<input id="rfo_radio"  name="rfo_radio" type="radio" value="Own accident"/>
				<label class="radio" for="rfo_radio">Are you reporting on your own behalf</label>
				<input id="rfs_radio" name="rfs_radio"  type="radio" value="Somebody else's accident"/>				
				<label class="radio" for="rfs_radio">or on another persons behalf</label>
			</p>
			
			<p align="center"><b><font size="3" >Injured Person's details</font></b></p>
			<p>
				<label class="FormFieldLabelRequired" for="typeOI_text" title="Title">Type of injury:<font color="red">*</font></label>
				<input class="text" id="typeOI_text" name="typeOI_text" type="text" maxlength="50"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="ipsn_text" title="Surname">Surname:<font color="red">*</font></label>
				<input class="text" id="ipsn_text" name="ipsn_text" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="ipfn_text" title="First Name">First Name:<font color="red">*</font></label>
				<input class="text" id="ipfn_text" name="ipfn_text"  type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="ipcn_text" title="Contact Number">Contact Number:<font color="red">*</font></label>
				<input class="text" id="ipcn_text" name="ipcn_text" type="text" maxlength="11"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="ipen_text" title="Employee Number">Employee Number:<font color="red">*</font></label>
				<input class="text" id="ipen_text" name="ipen_text" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="ipca_textarea" title="Address of incident">Contact address:<font color="red">*</font></label>
				<textarea class="text" cols="30" id="ipca_textarea" name="ipca_textarea" rows="5"> </textarea>
			</p>
			
					<%-- <label   for="">Request For</label>
														<div id="at2Disp" style="display: inline;">
										<input title="${i.abbreviation}"  type="text" disabled="disabled" id="attr2Disp"
											name="${i.id}" value="${i.value}" class="text">
									</div>
								
								<select name="attr2" id="attr2" style="width: 190px;" onchange="attr2Selected();">
							<c:forEach var="i" items="${attr2Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
							
								<select  name="attr2" id="attr2" class="text"
									onchange="attr2Selected();">
									<option value="-1" selected="selected">--Select--</option>
									
										<option   value="Reporting own Accident">Are you reporting on your own behalf</option>
										<option   value="Reporting others accident">on another persons behalf</option>
								</select>
								<br/>
								<span>
								<label   for="">Type of injury</label><input  type="text"  
											  class="text"></span>
											  
								<br/>
								<span>
								<label   for="">Surname</label><input  type="text"  
											  class="text"></span>
								
								<br/>
								<span>
								<label   for="">First Name</label><input  type="text"  
											  class="text"></span>
								
								<br/>
								<span>
								<label   for="">Contact Numebr</label><input  type="text"  
											  class="text"></span>	
											  
								<br/>
								<span>
								<label   for="">Employee Number</label><input  type="text"  
											  class="text"></span> 
								 <br/>
								<span>
								<label   for="">Contact Address</label></span><span><textarea rows="5"  cols="30"
											  class="text"></textarea></span><br/>		 --%>	
											  <p>
				<label class="FormFieldLabel" for="rpdr_cbox" title="Required">Reporting person details Required:</label>
				<input id="rpdr_cbox" name="rpdr_cbox"  type="checkbox" value="Required"/>
			</p>
							<br/>
							<div class="Form" id="reportingPersonDiv">
			<p align="center"><b><font size="3" >Reporting Person's details</font></b></p>
			<p>
				<label class="FormFieldLabelRequired" for="rpsurname_text" title="Surname">Surname:<font color="red">*</font></label>
				<input class="text" id="rpsurname_text" name="rpsurname_text" type="text" maxlength="50"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="rpfirstname_text" title="First Name">First Name:<font color="red">*</font></label>
				<input class="text" id="rpfirstname_text" name="rpfirstname_text"  type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="rpcontactN_text" title="Contact Number">Contact Number:<font color="red">*</font></label>
				<input class="text" id="rpcontactN_text" name="rpcontactN_text" type="text" maxlength="11"/>
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="rpempN_text" title="Employee Number">Employee Number:<font color="red">*</font></label>
				<input class="text" id="rpempN_text" name="rpempN_text" type="text" maxlength="10"/>
			</p>
			<p>
				<label class="FormFieldLabel" for="rpcontactA_textarea" title="Contact Address">Contact address:<font color="red">*</font></label>
				<textarea class="text" cols="30" id="rpcontactA_textarea" name="rpcontactA_textarea" rows="5"> </textarea>
			</p>
			</div>
			
			<p align="center"><b><font size="3" >About the Incident</font></b></p>
			<p>
				<label class="FormFieldLabelRequired" for="dt_timeStamp" title="Start">Date & Time:<font color="red">*</font></label>
				<input class="text datepicker hasDatepicker" id="dt_timeStamp" name="dt_timeStamp" readonly="readonly" type="text"  />
			</p>
			<p>
				<label class="FormFieldLabel" for="aiAddress_textarea" title="Address">Address:<font color="red">*</font></label>
				<textarea class="text" cols="30" id="aiAddress_textarea" name="aiAddress_textarea" rows="5"> </textarea>
			</p>
			
			<p>
				<label class="FormFieldLabel" for="ifaa_cbox" title="Required">Immediate First Aid Applied:</label>
				<input  id="ifaa_cbox" name="ifaa_cbox"  type="checkbox" value="Required"/>
			</p>
			<div id="firstAidDiv">
			<p>
				<label class="FormFieldLabel" for="details_textarea" title="First Aid Applied">Details:<font color="red">*</font></label>
				<textarea class="text" cols="30" id="details_textarea" name="details_textarea" rows="5"> </textarea>
			</p>
			</div>
			
			<p>
				<label class="FormFieldLabel" for="hdtih_textarea" title="Incident Happen">How did the Incident Happen:<font color="red">*</font></label>
				<textarea class="text" cols="30" id="hdtih_textarea" name="hdtih_textarea" rows="5"> </textarea>
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
	
	
	
	

