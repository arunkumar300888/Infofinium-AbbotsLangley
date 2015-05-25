<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<script>
	var oTable;
	$(function() {
		var docname= $("#name").val();
		var path= $("#path").val();
		var caseid= $("#caseid").val();
		var stepList=$("#stepList").val();
		var adminMeta=$("#adminMeta").val();
			//	alert(adminMeta);
		 						$('#input1')
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

												if ($('#input1').val() == "Edit Links") {

													var docid = '<c:out value="${docId}"/>';
													
												//	alert("Doc Id:" +docid);

													
  $.ajax({
		type : "GET",
		url : "updatereferencelinks1?docids="+ docids + "&docId="+ docid,
		cache : false,
		success : function(data) {
					
			
		//	alert("first");
			 $('#documentLinksListsdiv').html(data);
			 $('#documentLinksLists').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"iDisplayLength" : 25,
					"bFilter" : true,
					"bDestroy" : true
				}); 
			 
			 $('#documentLinksListsResultdiv').hide(); 
			 
			 $('#documentLinksListsResultUpdiv')
				.text(
						"Reference documents edited sucessfully"); 
				 $('#documentLinksListsResultUpdiv').addClass('success');
				 parent.loadMetadata(docname, path, caseid,stepList,adminMeta);
			 
		},
		error : function(data) {
		}
	}); 
			}
			else {
													
													//alert("IN Save Ref");
													 $.ajax({
															type : "GET",
															url : "savereference?docids="+ docids,
															cache : false,
															success : function(data) {
																//alert("sec");
																//alert("In Save Edit");
																
															},
															error : function(data) {
																//alert("In Save Edit");
																
																 $('#documentLinksListsResultUpdiv')
																	.text(
																			"Reference documents added sucessfully"); 
																 $('#documentLinksListsResultUpdiv').addClass('success');
															// alert('failure:'+data);
															}
													 
													 
													 
														}); 			
												}

											} else {

												if ($('#input1').val() == "Edit Links") {

													var docids = new Array();
													var docid = '<c:out value="${docId}"/>';
													
													
													 $.ajax({
															type : "GET",
															url : "updatereferencelinks1?docids="
																+ docids
																+ "&docId="
																+ docid,
															cache : false,
															success : function(data) {
																
																
															//	alert("third");
																
															  $('#documentLinksListsdiv').html(data);
																 $('#documentLinksLists').dataTable({
																		"bJQueryUI" : true,
																		"sPaginationType" : "full_numbers",
																		"iDisplayLength" : 25,
																		"bFilter" : true,
																		"bDestroy" : true
																	});
																	
																 $('#documentLinksListsResultdiv').hide(); 
																 $('#input1').hide();
																	 $('#documentLinksListsResultUpdiv')
																		.text(
																				"Reference documents edited sucessfully"); 
																		 $('#documentLinksListsResultUpdiv').addClass('success');
																		 parent.loadMetadata(docname, path, caseid,stepList,adminMeta);
																		 
															},
															error : function(data) {
															}
													 
														}); 	
												

												} else {

													//alert("In else");
													$('#documentLinksListsdiv')
															.text(
																	"Please select  documents to add");
													 $('#documentLinksListsdiv').addClass('success');

												}
											}

										});

						oTable = $('#documentLinksLists').dataTable();
					}); 
</script>

<div id="documentLinksListsdiv">
<input type="hidden" name="adminMeta" id="adminMeta" value="${adminMeta }"/>
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
						<td><input type="checkbox"  name="checkbox${status.count}"
							id="checkbox${status.count}" value="${i.id}" checked="checked"></td>

						<%-- <td id="name${status.count}"><a href="#"
							onclick="downloadReferenceLinks('${i.filePath}','${i.name}','${url}')">
								${i.name}</a></td> --%>
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
<c:if test="${not empty searchResult}">
		<input id="input1" class="butn" type="button" value="Edit Links">
		</c:if>
		<form id="downloadReferenceForm" name="downloadReferenceForm"
			method="post">
			<input type="hidden" name="path" value="" id="path"> 
			<input type="hidden" name="name" value="" id="name">

		</form>
	</div>
</div>
<div align="center" style="padding: 4px 0px;" id="documentLinksListsResultUpdiv" ></div>

