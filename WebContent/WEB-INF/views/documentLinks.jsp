<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>
	var oTable;
	$(function() {
						$('#input')
								.click(
										function() {

											var sData = $('input',
													oTable.fnGetNodes())
													.serialize();											
											if (sData != "") {

												var docids = new Array();
												var splitdata = sData
														.split("&");
												var index;
												for (index = 0; index < splitdata.length; index++) {

													var datasplits = splitdata[index]
															.split("=");

													docids[index] = datasplits[1];

												}

												if ($('#input').val() == "Edit Links") {

													 $.ajax({
															type : "GET",
															url : "editreference?docids="+ docids,
															cache : false,
															success : function(data) {
																
																 $('#documentLinksListsdiv').html(data);
																 $('#documentLinksLists').dataTable({
																		"bJQueryUI" : true,
																		"sPaginationType" : "full_numbers",
																		"iDisplayLength" : 25,
																		"bFilter" : true,
																		"bDestroy" : true
																	}); 
																 $('#documentLinksListsResultdiv')
																	.text(
																			"Reference documents edited sucessfully"); 
																 $('#documentLinksListsResultdiv').addClass('success');
																
																
															},
															error : function(data) {
															
															}
														}); 

												}
												
												
												else if ($('#input').val() == "Update Links") {	
													//alert("inside");
													var docname= $("#name").val();
													var path= $("#path").val();
													var caseid= $("#caseid").val();
													var stepList=$("#stepList").val();
													var adminMeta=$("#adminMeta").val();
													 $.ajax({
															type : "GET",
															url : "updatereferencedocs?docids="
																+ docids+"&adminMeta="+adminMeta+"&docId=<c:out value='${docId}'/>",
															cache : false,
															success : function(data) {
																 $('#documentLinksListsdiv').html(data);
																 $('#documentLinksLists').dataTable({
																		"bJQueryUI" : true,
																		"sPaginationType" : "full_numbers",
																		"iDisplayLength" : 25,
																		"bFilter" : true,
																		"bDestroy" : true
																	}); 
																
																$('#documentLinksListsResultdiv')
																.text(
																		"Reference documents updated sucessfully"); 
																 $('#documentLinksListsResultdiv').addClass('success');
																 parent.loadMetadata(docname, path, caseid,stepList,adminMeta);
																
															},
															error : function(data) {

															}
													 
														}); 

												}

												else {	
													 $.ajax({
															type : "GET",
															url : "savereference?docids="
																+ docids,
															cache : false,
															success : function(data) {
																
																$('#documentLinksListsdiv').html(data);
																 $('#documentLinksLists').dataTable({
																		"bJQueryUI" : true,
																		"sPaginationType" : "full_numbers",
																		"iDisplayLength" : 25,
																		"bFilter" : true,
																		"bDestroy" : true
																	}); 
																  $('#documentLinksListsResultdiv')
																	.text(
																			"Reference documents saved sucessfully"); 
																 $('#documentLinksListsResultdiv').addClass('success');
																
															},
															error : function(data) {

															}				 
													 
														}); 
												}

											} else {

												if ($('#input').val() == "Edit Links") {

													var docids = new Array();
													 $.ajax({
															type : "GET",
															url : "editreference?docids="
																+ docids,
															cache : false,
															success : function(data) {
																$('#documentLinksListsdiv').html(data);
																 $('#documentLinksLists').dataTable({
																		"bJQueryUI" : true,
																		"sPaginationType" : "full_numbers",
																		"iDisplayLength" : 25,
																		"bFilter" : true,
																		"bDestroy" : true
																	}); 
																 
																 
																 
																 $('#documentLinksListsResultdiv')
																	.text(
																			"Removed all reference documents sucessfully"); 
																 $('#documentLinksListsResultdiv').addClass('deletion');
														
																
															},
															error : function(data) {
															}							 
														}); 
												

												} else {

													$('#documentLinksListsdiv')
															.text(
																	"Please select reference documents to add");
													 $('#documentLinksListsdiv').addClass('success');

												}
											}

										});

						oTable = $('#documentLinksLists').dataTable();
					});
</script>
<div id="documentLinksListsdiv">
	<table id='documentLinksLists' class='display'>
		<thead>
			<tr>
				<th>Select</th>
				<th>Name</th>
				<th>Document Type</th>
			<!-- 	<th>Eb No</th> -->
				<th>Document Owner</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty searchResult}">

				<c:forEach var="i" items="${searchResult}" varStatus="status">
					<tr>

						<c:if test="${edit=='edit'}">
							<td><input type="checkbox"  name="checkbox${status.count}"
								id="checkbox${status.count}" value="${i.id}" checked="checked"></td>
						</c:if>
						<c:if test="${empty edit}">
							<td><input type="checkbox" name="checkbox${status.count}"
								id="checkbox${status.count}" value="${i.id}"></td>
						</c:if>
						<c:if test="${edit=='update'}">
							<td><input type="checkbox"  name="checkbox${status.count}"
								id="checkbox${status.count}" value="${i.id}"></td>
						</c:if>

						<td id="name${status.count}">${i.name}</td>
						<td id="doctype${status.count}">${i.doctype.doctypeName}</td>
					<%-- 	<td id="ebNo${status.count}">${i.ebNo}</td> --%>
						<td id="author${status.count}">${i.author}</td>


					</tr>
				</c:forEach>


			</c:if>
		</tbody>
	</table>


	<div align="center">
	<input type="hidden" id="adminMeta" name="adminMeta" value="${adminMeta}">
		<c:if test="${edit=='edit'}">
			<input id="input"  class="butn" type="button"  value="Edit Links">
		</c:if>
		<c:if test="${empty edit}">
			<input id="input" class="butn" type="button"  value="Save Links">
		</c:if>

		<c:if test="${edit=='update'}">

			<input id="input" class="butn" type="button"  value="Update Links">
		</c:if>

	</div>
</div>

<div id="documentLinksListsResultdiv" align="center" style="padding: 4px 0px;"></div>



