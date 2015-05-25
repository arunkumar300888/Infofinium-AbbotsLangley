
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	var intValue;

	$(function() {

		var isVisible = $('#attr2qUplDisp').is(':visible');

		if (isVisible) {
			var differentiate = $('#attr2qUplDisp').val();
			if (differentiate == 'Internal') {
				internalValues();

			} else {

				eBValues();
			}
		} else {
			var differentiate = $('#attr2qUpl option:selected').text();
			if (differentiate == 'Internal') {
				internalValues();
			} else {
				eBValues();
			}
		}

		/* var isVisible = $('#attr2qUplDisp').is(':visible');			
			if(isVisible)
			{
				
			var differentiate=$('#attr2qUplDisp').val();
			if(differentiate=='Internal')
			{
			$('#ebNumberQupl').hide();
							 
			}				
			}			
			else
				{

				var differentiate=$('#attr2qUpl option:selected').text();
				if(differentiate=='Internal')
					{
					$('#ebNumberQupl').hide();
									
					}				
				} */
	});
	function setValues(intValue) {
		$('#lblQEbNumber').html(intValue);
		$('#lblQEbNumber').attr('title', intValue);
		$('#ebnumberWof').attr('title', intValue);
	}

	function internalValues() {
		intValue = 'Unique Doc ID/Eb Number';
		setValues(intValue);
	}

	function eBValues() {
		intValue = 'Eb Number';
		setValues(intValue);
	}
</script>
<script>
	$(function() {
		$("#datepickerqUp").datepicker({
			dateFormat : 'dd-mm-yy'
		/*  ,
		showOn: "button",
		buttonImage: "resources/images/calendar.gif",
		buttonImageOnly: true */
		});
		$('#datepickerqUp').click(function() {
			$('#datepickerqUp').datepicker('show');
		});

	});
</script>


<div class="select1" style="min-height: 150px;">
<div id="quick-upload">
	<%-- <input type="hidden" name="isWorkflow" id="isWorkflow"
							value="${isWorkflow}">	 --%>
	<form id="quickUpload" method="post" enctype="multipart/form-data"
		target="hiddenDivQuickUpload">
		
		<input type="hidden" name="securitySettingQupl"
			id="securitySettingQupl" value=""> <label
			title="Select Doc Type" for="">Select Doc Type</label> <select
			class="text" name="documentTypeWof" id="documentTypeWof">
			<!-- <option value="-1" selected="selected">--Select--</option> -->
			<c:forEach var="i" items="${doctypes}">.
					 <c:if test="${!i.workflow}">
					<option title="${i.abbreviation}" value="${i.id}">${i.doctypeName}</option>
				</c:if>
			</c:forEach>
		</select> 
		<br/>
		<label id="lblQEbNumber" ></label> <input
			type="text" name="ebnumberWof" id="ebnumberWof" maxlength="50"
			class="text" /> <img title="Eb Number"
			src="resources/images/comment_question.png" /> <br> 
			<label
			>Title/Keywords<font color="red">*</font></label>
		    <input type="text" name="keywordsWof" id="keywordsWof" maxlength="40"
			class="text" /> <img
			title="Please select keywords carefully as they create part of the file name. Only characters A -Z, 0 -9 and _ are allowed."
			src="resources/images/comment_question.png" /> <br> <label>Relevant Date</label> <input
			title="Please select a date otherwise it takes today's date"
			type="text" name="datepickerqUp" id="datepickerqUp"
			class="text datepicker" readonly='true' /> <img
			title="Relevant Date" src="resources/images/comment_question.png" />
	     	<br> 
		<label title="Choose a file to upload">Select
			File</label> 
			<input type="file" style="width: 310px;" name="quickUpl"
			id="quickUpl"/> 
			<input type="button" class="butn"
			value="Quick Upload" id="btnQuickUpload" onclick="quickUpload();"
			class="text" /> <img title="Quick Upload"
			src="resources/images/comment_question.png" />
	</form>

	<div id="dialog-confirm-quick" hidden="true"
		title="Quick Upload Confirmation">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> <b>Have you
				entered the correct data for the document uploaded? (Note - this
				data will define the document name and document location).</b>
			<!-- <br />This document is set to default security setting.</b> -->
		</p>
	</div>


	<div id="secDialog"></div>

	<div id="quickUploading" hidden="true"
		title="Please wait while uploading.." align="center"
		style="padding-top: 6px">
		<img align="middle" src="resources/images/progress_bar.gif" alt="" />
	</div>
</div>
<div align="left" id="quickUploadResult"></div>
<div id="hiddenDivQuick">
	<iframe id="hiddenDivQuickUpload" name="hiddenDivQuickUpload"
		style="display: none"> </iframe>
</div>
</div>

