<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script type="text/javascript" src="resources/js/thickbox.js"></script>
<script src="resources/js/ReferenceLinks.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/style/thickbox.css"
	type="text/css" media="screen" />
</head>

<script>
var intValue;
	$(function() {		
		
		 $("#listSecGroups").hide();
		 var isVisible = $('#attr2UplDisp').is(':visible');	
		 
		//	alert(isVisible);
			if(isVisible)
			{			
			var differentiate=$('#attr2UplDisp').val();
			if(differentiate=='Internal'){		
			    
				internalValues();
			}
			else{
				eBValues();
			}		
			}			
			else{
				var differentiate=$('#attr2Upl option:selected').text();
				if(differentiate=='Internal'){	
					internalValues();
					}
				else{
					eBValues();
				}
				} 
		
		
		/* var isVisible = $('#attr2UplDisp').is(':visible');		
	//	alert(isVisible);
		if(isVisible)
		{			
		var differentiate=$('#attr2UplDisp').val();
		if(differentiate=='Internal')
		{
		  //  $('#ebNumberUplInternal').hide();				
		    $('#ebNumberLbl').hide();	
			$('input:[id="it_Eb Number"]').hide();								
		}				
		}			
		else
			{

			var differentiate=$('#attr2Upl option:selected').text();
			if(differentiate=='Internal')
				{
			//	  $('#ebNumberUplInternal').hide();		
			 	$('#ebNumberLbl').hide();	
				$('input:[id="it_Eb Number"]').hide(); 
								
				}				
			} */
		
		
		$('input:[dtpk=true]').datepicker({
			dateFormat : 'dd-mm-yy'
			
		});
		  $('input:[dtpk=true]').click(function(){
			    $('input:[dtpk=true]').datepicker('show');
			});
		
	});
	
	function setValues(intValue){
		 $('#lblEbNumber').html(intValue);
		   $('#lblEbNumber')
		    .attr('title',intValue);
		    $('input:[id="it_Eb Number"]').attr('title',intValue);
	}

	function internalValues(){
		 intValue='Unique Doc ID/Eb Number';
		 setValues(intValue);
	}

	function eBValues(){
		 intValue='Eb Number';
			setValues(intValue);
	}
	
</script>
<div class="select1" style="min-height: 150px;">
	<div>
		<div id="showModelName" class="value"  align="right"></div>

		<div id="editAttrDiv">
			<form method="post" action="" enctype="multipart/form-data"
				name="submitAttrSaveForm" id="submitAttrSaveForm"
				target="hiddenNewFrame">
				<input type="hidden" name="securitySetting" id="securitySetting"
					value=""> <input type="hidden" name="modelName"
					value="${modelName}" id="modelName" /> <input type="hidden"
					name="timeBound" value="${timeBound}" id="timeBound" /> <input
					type="hidden" value="" id="referencelinkvalues"
					name="referencelinkvalues" />


				<c:forEach var="i" items="${attr}">
					<c:choose>
						<c:when test="${i.type == 'WF_ATTR_TEXT'}">
							<c:choose>
								<c:when test="${i.name=='Eb Number'}">
									<%-- <span width="123"><label>${i.name}</label>&nbsp;<font
										color="red">*</font></span> --%>
									<span id="ebNumberLbl" width="123"><label id=lblEbNumber></label></span>

								</c:when>
								<c:when test="${i.name=='Keywords'}">
									<span width="123"><label>Title/Keywords<font
										color="red">*</font></label></span>
								</c:when>

								<c:otherwise>
									<span width="123" title="${i.name}"><label>${i.name}</label></span>
								</c:otherwise>
							</c:choose>

						</c:when>

						<c:when test="${i.type=='WF_ATTR_DATE'}">

							<c:choose>
								<c:when test="${i.name=='Target Date'}">
								<c:if test="${modelName=='No Workflow'}">
									<span width="123" title="Submission Date"><label>Relevant 
											Date&nbsp;<!-- <font color="red">*</font> --></label></span>
											</c:if>
											<c:if test="${modelName!='No Workflow'}">
									<span width="123" title="Submission Date"><label>Submission 
											Date&nbsp;<font color="red">*</font></label></span>
											</c:if>
								</c:when>

								<c:otherwise>
									<span width="123" title="${i.name}"><label>${i.name}</label>&nbsp;<font
										color="red">*</font></span>
								</c:otherwise>
							</c:choose>

							<%-- <span width="123"><label>${i.name}</label>&nbsp;<font
								color="red">*</font></span> --%>

						</c:when>
						<c:otherwise>
							<span width="123" title="${i.name}"><label>${i.name}</label></span>
						</c:otherwise>
					</c:choose>

					<%-- <span width="123" > 
			<label>${i.name}</label></span>	 --%>
					<c:choose>
						<c:when test="${i.type == 'WF_ATTR_TEXT'}">

							<c:choose>
								<c:when test="${i.name=='Eb Number'}">
									<span><input type="text" name="it_${i.name}"
										id="it_${i.name}" maxlength="50" class="text" /> <img
										title="Eb Number" src="resources/images/comment_question.png" />
										<br></span>
								</c:when>
								<c:when
									test="${(i.name == 'MDL Number') && (isMdlDisp == true)}">
									<span><input title="${i.name}" type="text"
										name="it_${i.name}" id="it_${i.name}" value="C12.003"
										class="text" /> <img title="MDL Number"
										src="resources/images/comment_question.png" /> <br></span>
								</c:when>

								<c:when test="${(i.name == 'Keywords')}">
									<span><input name="it_${i.name}" id="it_${i.name}"
										maxlength="40" class="text" /> <img
										title="Please select keywords carefully as they create part of the file name. Only characters A -Z, 0 -9 and _ are allowed."
										src="resources/images/comment_question.png" /> <br></span>
								</c:when>
								<c:otherwise>
									<span><input title="${i.name}" type="text"
										name="it_${i.name}" id="it_${i.name}" class="text" /> <img
										title="${i.name}" src="resources/images/comment_question.png" />
										<br></span>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:when test="${i.type == 'WF_ATTR_DATE'}">
							<span><input type="text" name="id_${i.name}" dtpk="true"
								id="id_${i.name}" class="text datepicker" readonly='true' /> <img
								title="Submission Date" src="resources/images/comment_question.png" /> <br></span>
						</c:when>
						<c:when test="${i.type == 'WF_ATTR_DECIMAL'}">
							<span><input type="text" name="if_${i.name}"
								id="if_${i.name}" class="text" /><br></span>
						</c:when>
						<c:otherwise>
							<span><input title="${i.name}" type="text"
								name="in_${i.name}" id="in_${i.name}" class="text" /><BR></span>
						</c:otherwise>
					</c:choose>

				</c:forEach>

				<span align="left" class="documentlist" width="123"><label>Select File</label></span> <span
					align="left"><input type="file" class="text" style="width: 230px;"
					name="docFile" id="docFile"> </span> <span></span> <span><input
					type="submit" class="download" id="saveAttr"
					value="Upload Document" onclick="saveCaseAttrClicked();"> <img
					title="Controlled Upload"
					src="resources/images/comment_question.png" /> </span>

			</form>

			<div id="dialog-confirm" hidden="true"
				title="Controlled Upload Confirmation">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin: 0 7px 20px 0;"> </span> <b>Have
						you entered the correct data for the document uploaded? (Note -
						this data will define the document name and document location). <br />This
						document is set to default security setting.
					</b>
				</p>
			</div>

			<div id="uploading" hidden="true"
				title="Please wait while uploading.." align="center" style="padding-top: 6px">
				<img align="middle" src="resources/images/progress_bar.gif" alt="" />
			</div>

			<!-- Added For Security group Below this -->
			<div id="securitySettingDialog" hidden="true"
				title="Security Setting" class="main">
					<div align="center" class="value controlspan">
						<%-- <c:out value="${secGroups.size()}"></c:out>	 --%>	
						<input type="hidden" value="${open }" id="secOpen" name="secOpen"/>			
						<c:choose>					
							<c:when test="${secGroups.size()==0}">
								<input type="radio" name="secType" id="secType1" value="open"
									onClick="openClicked();" checked="checked">
									<%-- <input type="hidden" value="${open }" id="secOpen" name="secOpen"/> --%>
								<label for="secType1" class="radio">Open</label>
								<input type="radio" name="secType" id="secType2"
									value="restricted" disabled="disabled">
										<label for="secType2" class="radio">Restricted</label>
							</c:when>
							<c:otherwise>
								<input type="radio" name="secType" id="secType3" value="open"
									onClick="openClicked();" checked="checked">
								
									<label for="secType3" class="radio">Open</label>
								<input type="radio" name="secType" id="secType4"
									value="restricted"
									onclick="restrictedClicked(${defaultSG.id});">
										<label for="secType4" class="radio">Restricted</label>
											<div id="listSecGroups" align="left" class="value controlspan"
									hidden="true">
									<!-- 	<hr> -->
									<c:forEach var="sg" items="${secGroups}">
										<c:choose>
											<c:when test="${(sg.id)==(defaultSG.id)}">
												<input type="radio" name="sgrp" id="sgrp1_${sg.id}" value="${sg.id}"
													checked="checked">
													<label for="sgrp1_${sg.id}" class="radio">${sg.name}</label>
												<br>
											</c:when>
											<c:otherwise>
												<input type="radio" name="sgrp" id="sgrp2_${sg.id}" value="${sg.id}">
													<label for="sgrp2_${sg.id}" class="radio">${sg.name}</label>
												<br>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
			</div>
		</div>
		<div id="newDocCrtStatusDiv" align="left"></div>
		<div id="hiddenDivCreate">
			<iframe id="hiddenNewFrame" name="hiddenNewFrame"
				style="display: none"> </iframe>
		</div>
	</div>
</div>