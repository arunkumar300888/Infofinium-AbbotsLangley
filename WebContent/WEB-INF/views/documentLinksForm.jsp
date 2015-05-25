<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>

//<![CDATA[
var _popupDialogAjaxField = "${itemId}";
var documentName="${documentName}";
 

function _popupDialogOk()
{
	//_popupDialogContentOk(_popupDialogAjaxField);
	//alert(_popupDialogAjaxField);
	 try
	{
		 var rad=$("input[name=docChecks]:checked").val();
		// alert(documentName);
		 $.ajax({
				type : "GET",
				url : "removeTempDoc?docName=" + documentName,
				cache : false,
				success : function(data) {
					//document.getElementById(id).value="";
					 window.opener.setValue(_popupDialogAjaxField,rad);
						//var c = document.getElementById(_popupDialogAjaxField).name();
						// alert(c);
						//c.validateField(c);
						window.close();
				}
			});
		 
		 
		
		
	}
	catch(e)
	{
	} 

	//closePopupDialog();
}


//]]>





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
																 $('#documentLinksListsForms').dataTable({
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
																 $('#documentLinksListsForms').dataTable({
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
																 $('#documentLinksListsForms').dataTable({
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
																 $('#documentLinksListsForms').dataTable({
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

						oTable = $('#documentLinksListsForms').dataTable();
					});
	
	
		$("input:radio[name=docChecks]").click(function() {
		    var value = $(this).val();
		    //alert(value);
		});
	
</script>
<div id="documentLinksListsdiv">
	<table id='documentLinksListsForms' class='display'>
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

						
						
							<td><input type="radio"  style="display: inline;" name="docChecks"
								id="checkbox${status.count}" value="${i.name}"></td>
						

						<td id="name${status.count}">${i.name}</td>
						<td id="doctype${status.count}">${i.doctype.doctypeName}</td>
					<%-- 	<td id="ebNo${status.count}">${i.ebNo}</td> --%>
						<td id="author${status.count}">${i.author}</td>


					</tr>
				</c:forEach>


			</c:if>
		</tbody>
	</table>
	<c:if test="${not empty searchResult}">
	<div align="center">
<input  type='button' class="butn"  style='width:100px;' id="_popupdialog_ok" name='_popupdialog_ok' value='Ok' onclick='_popupDialogOk();' />

	</div>
	</c:if>
</div>





