<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(function() {
		var test = '<c:out value="${canShowAllButtons}"/>';
		if (test == 'false') {
			//disableButton();
		}
		$('input:[dtpk=true]').datepicker({
			dateFormat: 'dd-mm-yy',
			showOn: "button",
			buttonImage: "resources/images/calendar.gif",
			buttonImageOnly: true
		});
		$("#datepicker").datepicker({
			dateFormat: 'dd-mm-yy'
		});
	});

	$(document)
		.ready(
		function() {
			$('table.tbStyle tr:even').addClass('row2');
			$('table.tbStyle tr:odd').addClass('row1');

			$("#comments")
				.cleditor(
				{
					width: 660, // width not including margins, borders or padding
					height: 300, // height not including margins, borders or padding
					controls: // controls to add to the toolbar
						"bold italic underline strikethrough subscript superscript | font size "
						+ "style | color highlight removeformat | bullets numbering | outdent "
						+ "indent | alignleft center alignright justify | undo redo  " +
						"rule image link unlink | cut copy paste pastetext | print source",
					colors: // colors in the color popup
						"FFF FCC FC9 FF9 FFC 9F9 9FF CFF CCF FCF "
						+ "CCC F66 F96 FF6 FF3 6F9 3FF 6FF 99F F9F "
						+ "BBB F00 F90 FC6 FF0 3F3 6CC 3CF 66C C6C "
						+ "999 C00 F60 FC3 FC0 3C0 0CC 36F 63F C3C "
						+ "666 900 C60 C93 990 090 399 33F 60C 939 "
						+ "333 600 930 963 660 060 366 009 339 636 "
						+ "000 300 630 633 330 030 033 006 309 303",
					fonts: // font names in the font popup
						"Arial,Arial Black,Comic Sans MS,Courier New,Narrow,Garamond,"
						+ "Georgia,Impact,Sans Serif,Serif,Tahoma,Trebuchet MS,Verdana",
					sizes: // sizes in the font size popup
						"1,2,3,4,5,6,7",
					styles: // styles in the style popup
						[["Paragraph", "<p>"],
							["Header 1", "<h1>"],
							["Header 2", "<h2>"],
							["Header 3", "<h3>"],
							["Header 4", "<h4>"],
							["Header 5", "<h5>"],
							["Header 6", "<h6>"]],
					useCSS: false, // use CSS to style HTML when possible (not supported in ie)
					docType: // Document type contained within the editor
						'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">',
					docCSSFile: // CSS file used to style the document contained within the editor
						"",
					bodyStyle: // style to assign to document body contained within the editor
						"margin:4px; font:10pt Arial,Verdana; cursor:text"
				});

		});
</script>
<div id="top">
<div id="docReview" class="my-document complete box-content">
	
			<span class="float-left grid-box-header top3">Form Comments </span>
			<div align="right">
				<input type="button" value="Back" id="btnBackToList" onclick="loadMetadata('${formName}', '${path}', '${caseId}', '${stepList}','true');" class="download" />
			</div>
		
</div>
<div id="docProperties" class="my-document complete box-content">
	
			<span class="float-left grid-box-header top3">Form Properties </span>
			<div align="right">
				<input type="button" value="Back" id="btnBackToList" onclick="showAllWipDocs();"  class="download" /></div>
		
</div>
</div>
<%-- <div id="docNameheader" class="title1">${formName}  > ${ActionStatus}</div> --%>

<div id="metaDatView">
		<div align="center" style="background-color: #EAEAEA;">
		<small id="docNameheader" class="title1">${formName}  > ${ActionStatus}</small></div>

				<div id="metaSpaceDiv" class='my-document complete box-content float-left'
			style="width: 673px">
					<div id="properties">
						<table class='display' id='documentProperties'>
							<thead>
								<tr>
									<th>Title</th>
									<th></th>
									<th>Description</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach var="entry" items="${metaDataForm}">
									<tr>
										<c:choose>
											<c:when test="${entry.key=='Document Link'}">
												<td style="white-space: nowrap;">${entry.key}</td>
												<td>:</td>
												<td id="documentLinkProp">${entry.value}</td>

											</c:when>

											<c:when test="${entry.key=='Target Date'}">
												<td style="white-space: nowrap;">Submission Date</td>
												<td>:</td>
												<td>${entry.value}</td>

											</c:when>

											<c:otherwise>
												<td style="white-space: nowrap;">${entry.key}</td>
												<td>:</td>
												<td>${entry.value}</td>

											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
								<c:forEach var="i" items="${downloadablereferenceDoc}" varStatus="status">
									<c:set var="type" scope="page">${i.referenceDocument.discriminator}</c:set>
									<tr>
									
										<td>Reference Link ${status.count}</td>
										<td>:</td>
										<c:choose>
										<c:when test="${type eq 'D'}">
										<td><a href="#"
											   onclick="downloadReferenceLinksMain('${i.referenceDocument.filePath}', '${i.referenceDocument.name}')">${i.referenceDocument.name}</a>
										</td>
										</c:when>
										<c:otherwise>									
										<td>
							             <a href="#" 
											   onclick="downloadForms('${i.referenceDocument.userFormId}');">${i.referenceDocument.name}</a>
										</td>
										</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
								<c:forEach var="i" items="${undownloadablereferenceDoc}" varStatus="status">
									<tr>
										<td>Reference Link
											${fn:length(downloadablereferenceDoc)+status.count}</td>
										<td>:</td>
										<td>${i.referenceDocument.name}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<form id="downloadReferenceForm1" name="downloadReferenceForm1" method="post">
							<input type="hidden" name="path" value="" id="path">
							<input type="hidden" name="name" value="" id="name">
						</form>
					</div>

					<div id="updateLinksProp" align="right">
					 <%--  <c:if test="${(stepAssigned == true)}"> --%>
                        <c:if test="${canShowAdminAll}"> 
						 <br /> <br />   
                                                <input type="button" class="butn" value="Update Properties" id="updateProperties"
                                                                onClick="editClicked();" style="width: 160px;" />
						</c:if>
						<%-- <c:if test="${canShowAdminUpdateProperties}">
						 <br /> <br />   
                         <input type="button" class="butn" value="Update Properties" id="updateProperties"
                                       onClick="editClicked();" style="width: 160px;" />
					                     	 </c:if>    --%>    
                                                <%-- </c:if> --%>
                                                <input type="button" class="butn" value="Update Ref Links" id="updateRefLinks"
								onClick="updateLinks('${documentId}','${stepList}','${path}','${docName}','${caseId}','true');" style="width: 142px;" />
							<br /><br />
					</div>

					<!-- For Reviews -->
					<div id="review" align="left">
						<form id="frmReviews">
							<input type="hidden" name="documentName" value="${docName}" id="documentName" />
							<input type="hidden" name="path" value="${path}" />
							<input type="hidden" name="caseId" value="${caseId}" />
							<input type="hidden" name="stepAssigned" value="${stepAssigned}" />
							<input type="hidden" name="stepList" value="${stepList}" id="stepList">
							<table>
								<tr>
									<td><textarea id="comments" name="comments"></textarea></td>
								</tr>
								<tr>
									<td valign="bottom" align="right">
										<input class="butnblue" type="button"
											   value="Save Comment" id="saveComment" onclick="postReviewClicked('true');"
											   style="width: 120px;" />
									</td>
								</tr>
							</table>
						</form>

						<%-- <input type="button" id="btnPrint" class="butn" value="Print" onClick="printClicked('${caseId}');" style="width: 50px;"> --%>
						<table id="reviewComments" class="display">
							<thead>
							<tr>
					<th colspan="3" ><div align="right"><input type="button" id="btnPrint" class="butn" value="Print"
					onClick="printClicked('${documentId}');"></div></th>
					</tr>
								<tr>
								
									<th>Author</th>
									<th style="text-align: center;">Date</th>
									<th>Comments</th>
								</tr>
							</thead>

							<tbody>

								<c:forEach var="entry" items="${reviewDocs}">
									<tr>
										<td>${entry.reviewer.userName}</td>
										<td style="width: 160px; text-align: center;"><fmt:formatDate
												type="both" pattern="dd-MM-yyyy HH:mm:ss"
												value="${entry.reviewDate}" /></td>
										<td>${entry.comments}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<!-- End of reviews -->

					<!-- For Edit -->
					<div id="editAttrDiv" class="select-box" style="padding: 10px;">
						<form method="post" id="frmAttr" action="">
							
								<input type="hidden" name="caseId" value="${caseId}">
								<input type="hidden" name="path" value="${path}" id="pathId">
								<input type="hidden" name="documentName" value="${formName}">
								<input type="hidden" name="stepList" value="${stepList}" id="stepList">

								<c:forEach var="i" items="${attributes}">
									
										<c:choose>
											<c:when test="${i.type=='WF_ATTR_DATE'}">
												<td width="123">
													<label>Submission Date</label></td>
												</c:when>

											<c:otherwise>
												
													<c:choose>
														<c:when test="${isInternal==true && i.name=='Eb Number'}">
															<label>Unique Doc ID/Eb Number</label>
														</c:when>
														<c:otherwise>
															<label>${i.name}</label>
														</c:otherwise>
													</c:choose>
												
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${i.type == 'WF_ATTR_TEXT'}">
												<c:choose>
													<c:when test="${i.name=='Eb Number'}">
														<input type="text" name="it_${i.name}" id="it_${i.name}"  value="${i.value}" size="29" maxlength="50" /><br>
														</c:when>
														<c:when test="${i.name=='Keywords'}">
														<input type="text" name="it_${i.name}" id="it_${i.name}" class="text" value="${i.value}" size="29" maxlength="200" /><br>
														</c:when>
													    <c:otherwise>
														<input type="text" name="it_${i.name}" id="it_${i.name}" class="text"  value="${i.value}"  size="29" maxlength="50" /><br>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${i.type == 'WF_ATTR_DATE'}">
												<input type="text" name="id_${i.name}" size="29" class="text"
														   dtpk="true" id="id_${i.name}" value="${i.value}"   /> <br>
												</c:when>
												<c:when test="${i.type == 'WF_ATTR_DECIMAL'}">
												<input type="text" name="if_${i.name}" size="29" class="text"
														   id="if_${i.name}" value="${i.value}" /> <br>
												</c:when>
												<c:otherwise>
												<input type="text" name="in_${i.name}" size="29" class="text"
														   id="in_${i.name}" value="${i.value}" /> <br>
												</c:otherwise>
											</c:choose>
									
								</c:forEach>
							
							<label></label><input type="button" id="btnPost" class="butn" value="Save"
								   onClick="saveAttrClicked(true);">
						</form>
					</div>
				</div>
			</div>

			<div class="float-right" style="width: 213px; min-height: 350px; background-color: #ECECFB;" align="center">
					<div style="padding: 20px;"><%-- 	<c:if test="${(formAttached == true) && (stepAssigned == true)  }"> --%>
					<%-- <c:if test="${(stepAssigned == true)}">
						<input type="hidden" name="jsonvals" id="jsonvals" value="${jsonVals}">
						<form id="goActionForm" name="goActionForm" method="post">
							<input type="hidden" name="stepList" value="${stepList}" id="stepList">
							<input type="hidden" name="actionComments" value="" id="actionComments">
							<input type="hidden" name="actionDocName" value="" id="actionDocName">
							<input type="hidden" name="actionDocPath" value="" id="actionDocPath">
							<input type="hidden" name="caseId" value="${caseId}" />
							<select name="actions" id="actions" class="text"  style="width: 100%;" onchange="actionSelected();" >
								<option value="-1" selected="selected">--Select--</option>
								<c:forEach var="a" items="${actions}">
									<option value="${a}">${a}</option>
								</c:forEach>
							</select>
							<textarea id="actionReason" name="actionReason" style="resize: none;width:154px;height:100px;" class='text'></textarea>
							<br /> <input type="button" value="Next" class="butn"
										  id="goAction" 
										  onclick="goActionClicked('${modelName}', '${path}', '${formName}', '${canShowCommentsPopUp}');" /> <br /><br />
						</form>
					</c:if> --%>

					<br />
					<c:if test="${(formAttached == true)}">
						<c:if test="${(stepAssigned == true)}">
							<input type="button" class="butn" value="Form"
								   id="btnForm" onclick="formBtnClicked('${userFormId}','true');"
								   style="width: 142px;" />
							<br />
						</c:if>

						<c:if test="${(canShowCancel == true)}">
							<input type="button" class="butn" value="Form" id="btnShowForm"
								   onclick="showFormBtnClicked('${userFormId}');"
								   style="width: 142px;" />
							<br />
						</c:if>
						
						<input type="button" class="butn" value="Properties" id="btnProperties"
							   onclick="loadFormProperties('${formName}', '${path}', '${caseId}', '${stepList}','true');" style="width: 142px;" />
						<br />
						<c:if test="${canShowAdminAll}">  
							<input type="button" class="butn" value="History" id="btnhistory"
							   onclick="historyBtnClicked('${caseId}');" style="width: 142px;" />
						<br />
						</c:if>
                        <c:if test="${canShowAdminHistory}">  
						<input type="button" class="butn" value="History" id="btnhistory"
							   onclick="historyBtnClicked('${caseId}');" style="width: 142px;" />
						<br />
						</c:if>

                        <c:if test="${canShowAdminAll}">  
						<input type="button" class="butn" value="Comments (${commentsCount})" id="btnComment"
							   onclick="reviewBtnClicked('${path}', '${formName}', '${caseId}', '${stepAssigned}', '${stepList}','true');"
							   style="width: 142px;" />
						<br />
						</c:if>
						<c:if test="${canShowAdminComments}">  
						<input type="button" class="butn" value="Comments (${commentsCount})" id="btnComment"
							   onclick="reviewBtnClicked('${path}', '${formName}', '${caseId}', '${stepAssigned}', '${stepList}','true');"
							   style="width: 142px;" />
						<br />
						</c:if>

						<%-- <c:if test="${(canShowAbandon == true)}"> --%>
							<input type="button" class="butn" value="Abandon" id="abandon"
								   onClick="abandonClicked('${stepId}','true');" style="width: 142px;" />
							<br />
						<%-- </c:if> --%>

						<%-- <c:if test="${(canShowCancel == true)}">
							<input type="button" class="butn" value="Cancel"
								   onClick="abandonClicked('${stepId}','true');" style="width: 142px;" />
							<br />
						</c:if> --%>

						<%-- <c:if test="${(canPick == true) && (stepAssigned == true)}">
						 <c:if test="${canShowAdminAll}">  
							<input type="button" class="butn" value="Claim"
								   onClick="onPickFormClicked('${path}', '${formName}', '${caseId}', '${stepId}','true');" style="width: 142px;" />
								   </c:if>
								   <c:if test="${canShowAdminClaim}">  
								   <input type="button" class="butn" value="Claim"
								   onClick="onPickFormClicked('${path}', '${formName}', '${caseId}', '${stepId}','true');" style="width: 142px;" />
								   </c:if> 
						</c:if> --%>

					</c:if>
				</div>
				<div id="caseDocHistory"></div>
				<div id="testdiv"></div>

				<div id="postConfirm-dialog">
					<div id="postConfirmation" title="Document Comments">
						<p>
							<span class="ui-icon ui-icon-alert"
								  style="float: left; margin: 0 7px 20px 0;"> </span> <b>Do you want to post comments before navigation?</b>
						</p>
					</div>
				</div>
			
		
</div>
