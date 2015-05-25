<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(function() {
		var test = '<c:out value="${canShowAllButtons}"/>';
		//	alert("Test:" +test);
		if (test == 'false') {
			//alert("Test:" +test);
			disableButton();
		}
		$('input:[dtpk=true]').datepicker({
			dateFormat : 'dd-mm-yy',
			showOn : "button",
			buttonImage : "resources/images/calendar.gif",
			buttonImageOnly : true
		});
		$("#datepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		var isChkout = $("#isCheckedout").val();
		$("#checkinStatus").hide();
		if (isChkout == "true") {
			//alert("if **");
			$("#checkinDiv").show();
			$("#checkoutDiv").hide();
		} else if (isChkout == "false") {
			//alert("else *");
			$("#checkoutDiv").show();
			$("#checkinDiv").hide();
		} else {
			$("#checkoutDiv").hide();
			$("#checkinDiv").hide();
		}

		/* 	 patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
			$('#it_Keywords').bind('keypress', function (event) {
			    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		         if (!patt.test(key)) {
			    	//alert("inside");
			       event.preventDefault();
			       return false;
			    }
		
			    
			});	 */
	});

	$(document)
			.ready(
					function() {
						$('table.tbStyle tr:even').addClass('row2');
						$('table.tbStyle tr:odd').addClass('row1');

						/* $('#comments').wysiwyg({controls:"bold,italic,underline,strikeThrough,|,undo,redo,|,insertOrderedList,insertUnorderedList"}); */
						$("#comments")
								.cleditor(
										{
											width : 450, // width not including margins, borders or padding
											height : 200, // height not including margins, borders or padding
											controls : // controls to add to the toolbar
											"bold italic underline strikethrough subscript superscript | font size "
													+ "style | color highlight removeformat | bullets numbering | outdent "
													+ "indent | alignleft center alignright justify | undo redo  "
													+ "rule image link unlink | cut copy paste pastetext | print source",

											colors : // colors in the color popup
											"FFF FCC FC9 FF9 FFC 9F9 9FF CFF CCF FCF "
													+ "CCC F66 F96 FF6 FF3 6F9 3FF 6FF 99F F9F "
													+ "BBB F00 F90 FC6 FF0 3F3 6CC 3CF 66C C6C "
													+ "999 C00 F60 FC3 FC0 3C0 0CC 36F 63F C3C "
													+ "666 900 C60 C93 990 090 399 33F 60C 939 "
													+ "333 600 930 963 660 060 366 009 339 636 "
													+ "000 300 630 633 330 030 033 006 309 303",
											fonts : // font names in the font popup
											"Arial,Arial Black,Comic Sans MS,Courier New,Narrow,Garamond,"
													+ "Georgia,Impact,Sans Serif,Serif,Tahoma,Trebuchet MS,Verdana",
											sizes : // sizes in the font size popup
											"1,2,3,4,5,6,7",
											styles : // styles in the style popup
											[ [ "Paragraph", "<p>" ],
													[ "Header 1", "<h1>" ],
													[ "Header 2", "<h2>" ],
													[ "Header 3", "<h3>" ],
													[ "Header 4", "<h4>" ],
													[ "Header 5", "<h5>" ],
													[ "Header 6", "<h6>" ] ],
											useCSS : false, // use CSS to style HTML when possible (not supported in ie)
											docType : // Document type contained within the editor
											'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">',
											docCSSFile : // CSS file used to style the document contained within the editor
											"",
											bodyStyle : // style to assign to document body contained within the editor
											"margin:4px; font:10pt Arial,Verdana; cursor:text"
										});

					});
</script>



<div id="top">
	<div id="docReview" class="my-document complete box-content">

		<span class="float-left grid-box-header top3">Document Comments</span>
		<div align="right">
			<input id="btnBackToList"
				onclick="loadMetadata('${docName}','${path}','${caseId}','${stepList}','false');"
				type="button" value="Back" class="download" />

		</div>

	</div>

	<div id="docProproperties">
		<span class="float-left grid-box-header top3">Document
			Properties</span>
		<div align="right">
			<input type="button" value="Back" id="btnBackToList"
				onclick="loadDocuments();" class="download" />
		</div>
	</div>
</div>
<div id="metaDatView">
	<div align="center" style="background-color: #EAEAEA;">
		<small id="docNameheader" class="title1">${docName} >
			${ActionStatus} > ${DocumentStatus}</small>
	</div>

	<div id="metaSpaceDiv"
		class='my-document complete box-content float-left'
		style="width: 980px">
		<div id="properties">

			<table class='display' id='documentProperties'>

				<thead>
					<tr class='first-row'>
						<th class='col order'>Title</th>
						<th class='col order'></th>
						<th class='col order'>Description</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${metaData}">
						<tr>
							<c:choose>

								<%-- <c:when test="${entry.key=='Document Link'}">
								<td style="white-space: nowrap;">${entry.key}</td>
									<td>:</td>
									<td id="documentLink"><a href="#">${entry.value}</a></td>
								
								</c:when> --%>
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

					<c:forEach var="i" items="${downloadablereferenceDoc}"
						varStatus="status">
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
									<td><a href="#"
										onclick="downloadForms('${i.referenceDocument.userFormId}');">${i.referenceDocument.name}</a>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>

						<%-- 						<tr>
											<td>Reference Link ${status.count}</td>
											<td>:</td>
											<td><a href="#"
												onclick="downloadReferenceLinksMain

('${i.referenceDocument.filePath}','${i.referenceDocument.name}')">${i.referenceDocument.name}</a></td>
										</tr> --%>

					</c:forEach>


					<c:forEach var="i" items="${undownloadablereferenceDoc}"
						varStatus="status">

						<tr>
							<td>Reference Link
								${fn:length(downloadablereferenceDoc)+status.count}</td>
							<td>:</td>
							<td>${i.referenceDocument.name}</td>
						</tr>

					</c:forEach>


					<%-- <c:choose>
								
						<c:when test="${canShowRefLinks==true}">
                             <c:forEach var="i" items="${referenceDoc}" varStatus="status">
							<tr>
                                <td>Reference Link ${status.count}</td>
									<td>:</td>			
								<td><a href="#" onclick="downloadReferenceLinksMain('${i.referenceDocument.filePath}','${i.referenceDocument.name}')">${i.referenceDocument.name}</a></td>
							</tr>
						
						</c:forEach>						
						</c:when>
								
								
								<c:otherwise>
								
								<c:forEach var="i" items="${referenceDoc}" varStatus="status">
							
							<tr>
                                <td>Reference Link ${status.count}</td>
									<td>:</td>			
								<td>${i.referenceDocument.name}</td>
							</tr>
						
						</c:forEach>
								
								</c:otherwise>								
								</c:choose>	 --%>
				</tbody>
			</table>

			<form id="downloadReferenceForm1" name="downloadReferenceForm1"
				method="post">
				<input type="hidden" name="path" value="" id="path"> <input
					type="hidden" name="name" value="" id="name">
			</form>
		</div>

		<%-- <div id="updateLinksProp" align="right">
					<c:if test="${(isOwner == true) && (stepAssigned == true)}">
			<br /> <br />
							<input type="button" class="butn" value="Update Properties"
								onClick="editClicked();"  />
							<!-- <br /> -->
							
							<input type="button" class="butn" value="Update Ref Links"
								onClick="updateLinks('${documentId}','${stepList}','${path}','${docName}','${caseId}');"  />
							<!-- <br /> -->
							<br /><br />
							
						</c:if> 
						</div> --%>


		<div id="updateLinksProp" align="right">
			<c:if test="${(stepAssigned == true)}">
				<c:if test="${canShowAdminAll}">
					<br />
					<br />
					<input type="button" class="butn" value="Update Properties"
						id="updateProperties" onClick="editClicked();"
						style="width: 160px;" />
				</c:if>
				<c:if test="${canShowAdminUpdateProperties}">
					<br />
					<br />
					<input type="button" class="butn" value="Update Properties"
						id="updateProperties" onClick="editClicked();"
						style="width: 160px;" />
				</c:if>
			</c:if>

			<%-- <c:if test="${(isOwner == true) && (stepAssigned == true)}">                                          
							<input type="button" class="butn" value="Update Ref Links"
								onClick="updateLinks('${documentId}','${stepList}','${path}','${docName}','${caseId}');"  />
							<br /><br />
						</c:if>  --%>
			<c:if test="${canShowAdminAll}">
				<input type="button" class="butn" value="Update Ref Links"
					id="updateRefLinks" style="width: 160px;"
					onClick="updateLinks('${documentId}','${stepList}','${path}','${docName}','${caseId}','false');"
					 />
				<br />
				<br />
			</c:if>

			<c:if
				test="${(canShowAdminRefLinks == true) && (stepAssigned == true)}">
				<input type="button" class="butn" value="Update Ref Links"
					id="updateRefLinks" style="width: 160px;"
					onClick="updateLinks('${documentId}','${stepList}','${path}','${docName}','${caseId}','false');"
					 />
				<br />
				<br />
			</c:if>

		</div>

		<!-- For Reviews -->
		<div id="review" align="left">
			
			<%-- 	</c:if> --%>
			<!-- <div style="width: 800px; height: 570px; overflow: auto;"> -->

			<table id="reviewComments" class="display">
				<thead>
					<tr>
						<th colspan="3"><div align="right">
								<input type="button" id="btnPrint" class="butn" value="Print"
									onClick="printClicked('${caseId}');">
							</div></th>
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
							<%-- <td style="width: 160px; text-align: center;"><fmt:formatDate
												type="date" pattern="dd-MM-yyyy hh:mm:ss"
												value="${entry.reviewDate}" /></td> --%>
							<td style="width: 160px; text-align: center;"><fmt:formatDate
									type="both" pattern="dd-MM-yyyy HH:mm:ss"
									value="${entry.reviewDate}" /></td>
							<td>${entry.comments}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<%-- 	<c:if test="${(stepAssigned == true)}"> --%>
			<form id="frmReviews">
				<input type="hidden" name="documentName" value="${docName}"
					id="documentName" /> <input type="hidden" name="path"
					value="${path}" /> <input type="hidden" name="caseId"
					value="${caseId}" /> <input type="hidden" name="stepAssigned"
					value="${stepAssigned}" />

				<!-- 2132013 -->
				<input type="hidden" name="stepList" value="${stepList}"
					id="stepList">
				<!-- 2132013 -->

				<table style="width:auto; align_content:center; padding-left: 300px;">
					<tr>
						<td><textarea id="comments" name="comments"></textarea></td>
					</tr>
					<tr>
						<!-- <td valign="bottom"><input class="butnnred" type="button"
											value="Save Comment" onclick="postReviewClicked();"
											style="width: 120px;"></td> -->
						<td valign="bottom" align="right"><input class="butnblue"
							type="button" value="Save Comment" id="saveComment"
							onclick="postReviewClicked('false');"></td>
					</tr>
				</table>
			</form>
		</div>

		<!-- End of reviews -->

		<!-- For Edit -->
		<div id="editAttrDiv" class="select-box" style="padding: 10px;">
			<form method="post" id="frmAttr" action="">

				<input type="hidden" name="caseId" value="${caseId}"> <input
					type="hidden" name="path" value="${path}" id="pathId"> <input
					type="hidden" name="documentName" value="${docName}">

				<!-- 832013 -->
				<input type="hidden" name="stepList" value="${stepList}"
					id="stepList">
				<!-- 832013 -->

				<c:forEach var="i" items="${attributes}">
					<c:choose>
						<c:when test="${i.type=='WF_ATTR_DATE'}">
							<label>Submission Date</label>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${isInternal==true && i.name=='Eb Number'}">
									<label>Unique Doc ID/Eb Number</label>
								</c:when>
								<c:otherwise>
									<label size="20">${i.name}</label>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${i.type == 'WF_ATTR_TEXT'}">
							<c:choose>
								<c:when test="${i.name=='Eb Number'}">
									<input type="text" name="it_${i.name}" class="text"
										id="it_${i.name}" value="${i.value}" size="29" maxlength="50" />
									<br>
								</c:when>
								<c:when test="${i.name=='Keywords'}">
									<input type="text" name="it_${i.name}" class="text"
										id="it_${i.name}" value="${i.value}" size="29" maxlength="200" />
									<br>
								</c:when>

								<c:otherwise>
									<input type="text" name="it_${i.name}" class="text"
										id="it_${i.name}" value="${i.value}" size="29" maxlength="50" />
									<br>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${i.type == 'WF_ATTR_DATE'}">
							<input type="text" name="id_${i.name}" size="29" class="text"
								dtpk="true" id="id_${i.name}" value="${i.value}" />
							<br>
						</c:when>
						<c:when test="${i.type == 'WF_ATTR_DECIMAL'}">
							<input type="text" name="if_${i.name}" size="29" class="text"
								id="if_${i.name}" value="${i.value}" />
							<br>
						</c:when>
						<c:otherwise>
							<input type="text" name="in_${i.name}" size="29" class="text"
								id="in_${i.name}" value="${i.value}" />
							<br>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<label></label><input type="button" id="btnPost" class="download"
					value="Save" onClick="saveAttrClicked(false);">
			</form>
		</div>
	</div>
	<div class="float-right"
		style="width: 213px; min-height: 350px; background-color: #ECECFB;"
		align="center">
		<div style="padding: 20px;">
			<c:if test="${(docAttached == true) && (stepAssigned == true)  }">
				<input type="hidden" name="jsonvals" id="jsonvals"
					value="${jsonVals}">
				<form id="goActionForm" name="goActionForm" method="post">
					<!-- Have to see -->
					<input type="hidden" name="stepList" value="${stepList}"
						id="stepList"> <input type="hidden" name="actionComments"
						value="" id="actionComments"> <input type="hidden"
						name="actionDocName" value="" id="actionDocName"> <input
						type="hidden" name="actionDocPath" value="" id="actionDocPath">
					<input type="hidden" name="caseId" value="${caseId}" /> <select
						name="actions" id="actions" class="text" style="width: 100%;"
						onchange="actionSelected();">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="a" items="${actions}">
							<option value="${a}">${a}</option>
						</c:forEach>
					</select>
					<textarea id="actionReason" name="actionReason"
						style="resize: none; width: 154px; height: 100px;" class='text'></textarea>
					<br /> <input type="button" value="Next" class="download"
						id="goAction"
						onclick="goActionClicked('${modelName}','${path}','${docName}','${canShowCommentsPopUp}');" />
					<br /> <br />
				</form>
			</c:if>
<div class="right_butn">
			<c:if test="${(docAttached == true)}">

				<%-- <c:if test="${(isOwner == true)}">
								<span id="hidechk"> <input type="hidden"
									id="isCheckedout" name="isCheckedout" value="${isCheckedout}" />
								</span>
								<span id="checkoutDiv"> <input class="butngreen" type="button"
									value="Check out" id="btnCheckout" onclick="checkoutClicked();"
									 />
								</span>
								<div id="checkinDiv">
									<input class="butnnred" type="button" value="Check In"
										id="btnCheckin" onclick="checkinClicked('${documentId}','${path}');"
										 />
								</div>
							</c:if> --%>
				<c:if test="${canShowAdminAll}">
					<span id="hidechk"> <input type="hidden" id="isCheckedout"
						name="isCheckedout" value="${isCheckedout}" />
					</span>
					<span id="checkoutDiv"><input class="butngreen"
						type="button" value="Check out" id="btnCheckout"
						onclick="checkoutClicked();"  />
					</span>
					
					<div id="checkinDiv">
						<input class="butnblue" type="button" value="Check In"
							id="btnCheckin"
							onclick="checkinClicked('${documentId}','${path}','${stepId}','${docName}','false');"
							 />
						<input class="butnblue" type="button"
							value="Undo Check out" id="btnCancelCheckout"
							onclick="cancelcheckout('${documentId}','${path}','${stepId}','${docName}');"
							 />
					</div>
					
				</c:if>
				<c:if test="${canShowAdminAll}">
					<input type="button" class="download" value="Download"
						id="btnDownload" onclick="downloadClicked();"
						 />
				</c:if>
				<c:if test="${canShowAdminAll}">
				
				<input class="download" type="button" value="Show Sub Versions"
							id="btnVersion"
							onclick="showSubVersionDocs('${documentId}');"
							 />
							 </c:if>
				<c:if test="${(stepAssigned == true)}">
					<%-- 	<c:if test="${(isOwner == true)}"> --%>
					<c:if test="${canShowAdminCheckOut}">
						<span id="hidechk"> <input type="hidden" id="isCheckedout"
							name="isCheckedout" value="${isCheckedout}" />
						</span>
						<span id="checkoutDiv"> <input class="butngreen"
							type="button" value="Check out" id="btnCheckout"
							onclick="checkoutClicked();"  />
						</span>
						
					</c:if>
					<c:if test="${canShowAdminCheckInUndoCheckOut}">
						<div id="checkinDiv">
							<input class="butnblue" type="button" value="Check In"
								id="btnCheckin"
								onclick="checkinClicked('${documentId}','${path}','${stepId}','${docName}');"
								 />
							<input class="butnblue" type="button"
								value="Undo Check out" id="btnCancelCheckout"
								onclick="cancelcheckout('${documentId}','${path}','${stepId}','${docName}');"
								 />
						</div>
					</c:if>
					<c:if test="${(canShowAdminDownload == true)}">
						<input type="button" class="download" value="Download"
							id="btnDownload" onclick="downloadClicked();"
							 />
					</c:if>
					<c:if test="${(canShowAdminSubVersions == true)}">
				<input class="download" type="button" value="Show Sub Versions"
							id="btnVersion"
							onclick="showSubVersionDocs('${documentId}');"
							 />
							 </c:if>
				</c:if>

				<%-- Newly added for workflow completed sec group--%>
				<c:if test="${(canShowDownload == true)}">
					<c:if test="${(canShowAdminDownload == true)}">
						<input type="button" class="download" value="Download"
							id="btnDownload" onclick="downloadClicked();"
							 />
						
						
					</c:if>
				</c:if>
				
				<c:if test="${(canShowDownload == true)}">
				<c:if test="${(canShowAdminSubVersions == true)}">
				<input class="download" type="button" value="Show Sub Versions"
							id="btnVersion"
							onclick="showSubVersionDocs('${documentId}');"
							 />
							 </c:if>
				</c:if>
				<%-- <c:if test="${(canShowDownload == true)}">
						<input type="button" class="butn" value="Download"
							id="btnDownload" onclick="downloadClicked();"
							 />
						 						
						</c:if> --%>

				<input type="button" class="download" value="Properties"
					id="btnProperties" onclick="propBtnClicked();"
					 />
				
				<c:if test="${canShowAdminAll}">
					<input type="button" class="download" value="History"
						id="btnhistory" onclick="historyBtnClicked('${caseId}');"
						 />
					
				</c:if>
				<c:if test="${canShowAdminHistory}">
					<input type="button" class="download" value="History"
						id="btnhistory" onclick="historyBtnClicked('${caseId}');"
						 />
					
				</c:if>
				<c:if test="${canShowAdminAll}">
					<input type="button" class="download" value="Comments  (${commentsCount})" id="btnComment"
						id="btnComment"
						onclick="reviewBtnClicked('${path}','${docName}','${caseId}','${stepAssigned}','${stepList}','false');"
						 />
					
					
				</c:if>
				<c:if test="${canShowAdminComments}">
					<input type="button" class="download" value="Comments  (${commentsCount})" id="btnComment"
						id="btnComment"
						onclick="reviewBtnClicked('${path}','${docName}','${caseId}','${stepAssigned}','${stepList}','false');"
						 />
					
					
				</c:if>


				<%-- <c:if test="${(isOwner==true) && (canShowRevision==true) && revisionable}">
						<c:if test="${(isActive==true)}">
						<input type="button" class="butn" value="Revision"
							onclick="revisionbtnclicked('${documentId}')"
							 />
						
						</c:if>
						</c:if> --%>
				<c:if
					test="${(canShowAdminAll==true) && (canShowRevision==true) && revisionable}">
					<c:if test="${(isActive==true)}">
						<input type="button" class="download" value="Revision"
							onclick="revisionbtnclicked('${documentId}')"
							 />
						
						
					</c:if>
				</c:if>
				<c:if
					test="${(canShowAdminRevision==true) && (canShowRevision==true) && revisionable}">
					<c:if test="${(isActive==true)}">
						<input type="button" class="download" value="Revision"
							onclick="revisionbtnclicked('${documentId}')"
							 />
						
						
					</c:if>
				</c:if>

				<c:if test="${canShowAdminAll}">
				<c:if test="${stepId != 0}">
					<input type="button" class="download" value="Abandon" id="abandon"
						onClick="abandonClicked('${stepId}','false');"  />
					
				</c:if>
				</c:if>
				<c:if
					test="${(canShowAdminAbandon == true) && (stepAssigned == true)}">
					<input type="button" class="download" value="Abandon" id="abandon"
						onClick="abandonClicked('${stepId}','false');"  />
					
				</c:if>

				<%-- <c:if test="${(isOwner == true) && (stepAssigned == true)}">
						<input type="button" class="butn" value="Abandon"
								onClick="abandonClicked('${stepId}');"  />
							
						</c:if> --%>

				<c:if test="${(canPick == true) && (stepAssigned == true)}">
					<c:if test="${canShowAdminAll}">
						<input type="button" class="download" value="Claim"
							onClick="onPickClicked('${path}','${docName}','${caseId}','${stepId}','false');"
							 />
					</c:if>
					<c:if test="${canShowAdminClaim}">
						<input type="button" class="download" value="Claim"
							onClick="onPickClicked('${path}','${docName}','${caseId}','${stepId}','false');"
							 />
					</c:if>
				</c:if>

			</c:if>
			</div>
			<%-- ${stepId}
					${stepList} --%>
		</div>
	</div>
	<form id="downloadForm" name="downloadForm" method="post">
		<input type="hidden" name="path" value="${path}" id="pathId">
		<input type="hidden" name="documentName" value="${docName}"
			id="documentNameForRefresh"> <input type="hidden"
			name="documentId" value="${documentId}"> <input type="hidden"
			name="caseId" value="${caseId}" id="caseIdForRefresh"> <input
			type="hidden" name="stepId" value="${stepId}">
	</form>
	<div id="checkinStatus"></div>
	<div id="hiddenDiv">
		<iframe id="hiddenUploadFrame1" name="hiddenUploadFrame1" src="#"
			style="display: none"> </iframe>
	</div>
	<div id="caseDocHistory"></div>
	<div id="testdiv"></div>

	<div id="postConfirm-dialog">
		<div id="postConfirmation" title="Document Comments">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"> </span> <b>Do you
					want to post comments before navigation?</b>
			</p>
		</div>
	</div>
	<div id="checkInProgress" title="Please wait while check in"
		hidden="true" align="center" style="padding-top: 6px">
		<img align="middle" src="resources/images/progress_bar.gif" alt="" />
	</div>


</div>