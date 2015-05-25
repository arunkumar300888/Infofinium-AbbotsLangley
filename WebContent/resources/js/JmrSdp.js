$(function() {
	// alert("inside");
	$('#trayDiv').load(refreshTray());
	$('#lastDownloadedDiv').load(refreshLastDownloadedDocument());
	$('#lastActionedDiv').load(refreshLastActionedDocument());
	loadAllDocuments();
	$('#dynContentMetadata').hide();
	$('#photo-upload').hide();
	$('#csvDownloadSearch').hide();
	$('#search').hide();
	/*
	 * $("#browser span").click(function(event) { var path =
	 * $(this).attr("path"); loadDocumentsForPath(path); event.preventDefault();
	 * });
	 */
});

// For Doc Referencing on 28.01.13

function editLink() {
	var url = "docupload/editLinks?TB_iframe=true&height=585&width=900";
	tb_show('Edit Links', url, null);
}

function updateLinks(docId, stepList, path, docName, caseId,adminMeta) {
	var patt1 = new RegExp("&", "g");
	if (patt1.test(docName) == true) {
		docName = docName.replace(patt1, "*");
	}
	var url = "docupload/updateLinks?docId=" + docId + "&stepList=" + stepList
			+ "&path=" + path + "&docName=" + docName + "&caseId=" + caseId
			+ "&adminMeta="  + adminMeta 
			+"&TB_iframe=true&height=585&width=890";
	tb_show('Update Links', url, null);
}

function showSubVersionDocs(docId) {

	var url = "docs/subVersionDocuments?docId=" + docId;
var newwindow = window
.open(url,'Sub Version','height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');
if (window.focus) {
	newwindow.focus();
	// newwindow.location.href=aurl;
	}
	return false;
}

function attachLinkClicked() {
	var url = "docupload/attachLinks?&TB_iframe=true&height=585&width=900";
	tb_show('Attach Links', url, null);
}

function savereference() {
	$.get("savereference?docids=" + docids);
}

// End of Doc Referencing

// For Home
function homeClicked() {
refreshToolbar();
	//	
	// $('#downloadTemplateImg').show();
	// $('#quickUploadImg').show();
	// $('#uploadDocumentImg').show();
	//	
	// $('#leftlinks').show();
	// $('#myIntrays').show();
	// $("#dynContentMetadata").hide();
	// $("#dynContent").show();
	// loadAllDocuments();
}

function photoUploadClicked() {

	var value = "";
	$("#photoUploadResult").html("");
	$('#photo').val(value);
	$('#photo-upload').dialog({
		height : 170,
		width : 450,
		modal : true
	});
}

function photoUpload() {

	// alert("In Photo upload dialog");
	var value = $('#photo').val();
	if (value == "")

	{
		alert("Please select a photo to upload");
		return false;
	}

	if (!value.match(/(?:gif|jpg|png|bmp)$/)) {
		alert("Please upload only photo");
		return false;
	}

	$("#uploadPhotos").attr("action", "docupload/uploadPhotos");
	$("#uploadPhotos").submit();
}

function checkCategory(value) {
	if (value == 2) {
		$("#editCaseAttrDiv").show();
		$("#showCategory").hide();
	} else {
		/*
		 * $("#editCaseAttrDiv").show(); $("#showCategory").hide();
		 */
		$("#editCaseAttrDiv").hide();
	}
}

function simpleSearchKeyPress(e) {
	//alert("in search");

	if (typeof e == 'undefined' && window.event) {
		
		e = window.event;
	}
	
	

	if (e.keyCode == 13) {
		
		/* 
		 * $('#datepicker1').css({'background-color' : defaultcolor});
		 * $('#datepicker2').css({'background-color' : defaultcolor});
		 */
		var isVisibleSearch=$('#btnSimple').is(':visible');
		if(isVisibleSearch){
			 e.preventDefault();
			//alert("visible search");
		document.getElementById('btnSimple').click();
		}
	}
}

function searchKeyPress(e) {
	// alert("in search");

	if (typeof e == 'undefined' && window.event) {
		e = window.event;
	}

	if (e.keyCode == 13) {
		/*
		 * $('#datepicker1').css({'background-color' : defaultcolor});
		 * $('#datepicker2').css({'background-color' : defaultcolor});
		 */
		var isVisibleSearch=$('#btnSearch').is(':visible');
		if(isVisibleSearch){
		document.getElementById('btnSearch').click();
		}
	}
}

function loadAllDocumentsForTemplate() {
	$.ajax({
		type : "GET",
		url : "dash/getAllDocuments",
		cache : false,
		success : function(data) {

			$("#downloadTemplateForm").attr("action",
					"template/downloadTemplate");
			$("#downloadTemplateForm").submit();
			$('#csvDownloadSearch').hide();

			// alert(data);
			$("#dynContent").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true

			});

		}
	});
}



function loadAllDocuments() {
	// alert("In loadAllDocuments");
	// $('#fullDashboard').show();
	$("#category").val("LA");
	$("#dynContent").hide();
	
	/*$.ajax({
		type : "GET",
		url : "dash/getAllDocuments",
		cache : false,
		success : function(data) {

			// $("#downloadTemplateForm").attr("action",
			// "template/downloadTemplate");
			// $("#downloadTemplateForm").submit();

			// $('#csvDownloadSearch').hide();
			$('#csvDownloadSearch').show();

			// alert(data);
			$("#dynContent").html(data);
			// $("#fullDashboard").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true
			// "sScrollY" : "450px",
			// "sScrollX" : "600px",
			// "sDom" : 'T<"clear">lfrtip',
			// "oTableTools" : {
			// "aButtons" : [ {
			// "sExtends" : "print",
			// "sMessage" : "Generated by DataTables"
			// } ]
			// },

			});

			// $('#dynContent').hide();

			// $("#docList table tbody tr").live('click',docListClicked);

		}
	});*/

}

function loadDocumentsForPath(path) {
	// $('#fullDashboard').show();

	$.ajax({
		type : "GET",
		url : "dash/docs" + "?path=" + path,
		cache : false,
		success : function(data) {
			// alert(data);
			$('#csvDownloadSearch').hide();
			$("#dynContent").html(data);
			// $("#fullDashboard").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true
			});

			// For Photos Download Screen
			$('#photosDownload').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 50,
				"bFilter" : true
			});

			// $('#dynContent').hide();
			// alert("table Styled");
			// $("#docList table tbody tr").live('click',docListClicked);
			$("#newContent").hide();
		}
	});
}

function adminBackBtnClicked() {
	//alert("inside");
	$("#adminBack").attr("action", "loadHomePage");
	$("#adminBack").submit();
}

function showUsefulInfoForTenants() {
	//alert("inside");
	loadProgress();
	$.ajax({
				type : "GET",
				url : "forms/usefulInfoForTenants",
				cache : false,
				success : function(data) {
					 $("#search").hide();	
					//Next 3 lines for properties page to Dashboard Page
					 $('#csvDownloadSearch').hide();
					$('#dynContentMetadata').hide();
					
					$("#header2").show();				
					$("#dynContent").html(data);
					$('#dynContent').show();
					$("#header3").show();
					removeProgress();
				}
			});
}

function backBtnClicked() {
	refreshTray();
	refreshLastDownloadedDocument();
	refreshLastActionedDocument();
	$.ajax({
		type : "GET",
		url : "dash/btd",
		cache : false,
		success : function(data) {
			// alert(data);
			$("#dynContent").html(data);
			// $("#fullDashboard").html(data);
			$('#docsSearched').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"bFilter" : false
			});

			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				// "sScrollY" : "450px",
				"bFilter" : true
			});

			$("#dynContentMetadata").hide();
			$("#dynContent").show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();

			// For Header visibility back button

			$('#downloadTemplateImg').show();
			$('#quickUploadImg').show();
			$('#uploadDocumentImg').show();
			$('#formsImg').show();
			
			//For CSV Download
			$('#csvDownloadSearch').show();
			

			// $('#dynContent').hide();

			// alert("table Styled");
			// $("#docList table tbody tr").live('click',docListClicked);

			$("#newContent").hide();
		}
	});

}

function loadDocuments() {

	var isVisible = $('#actionReason').is(':visible');
	if (isVisible) {
		var actionValue = $('#actionReason').val();
		if (actionValue != "" && actionValue != undefined) {
			reasonCheck(actionValue);
		} else if (actionValue != undefined) {
			backBtnClicked();
		}
	} else {
		backBtnClicked();
	}

	// backBtnClicked();

	// if (!document.getElementById("comments")) {
	// // alert("Not Exists");
	// backBtnClicked();
	//
	// }
	//
	// var comments = document.getElementById("comments").value;
	// if (comments != "") {
	// $("#postConfirmation").dialog({
	// resizable : false,
	// width : 450,
	// height : 150,
	// modal : true,
	// buttons : {
	// "Yes" : {
	// text : 'Yes',
	// click :
	//
	// function() {
	// $(this).dialog("close");
	// return false;
	//
	// }
	// },
	// "No" : {
	// text : "No",
	// click : function() {
	// $(this).dialog("close");
	// backBtnClicked();
	// }
	// }
	// }
	// });
	// }
	//
	// else {
	// backBtnClicked();
	// }
}

function docListClicked(event) {
	//alert("docListClicked");
	var path = $(this).attr("path");
	var documentName = $(this).attr("documentName");
	var caseId = $(this).attr("caseId");
	loadMetadata(documentName, path, caseId);
}

function regexSplit(documentName) {
	var patt1 = new RegExp("&", "g");
	if (patt1.test(documentName) == true) {
		documentName = documentName.replace(patt1, "*");
		// alert(documentName);
		return documentName;
	}
	return documentName;
}

function reasonCheck(actionValue) {
	if (actionValue != "") {
		dialogTemplate("Go Back", "Please press the next button to save reason");
		return false;
	}
}

function loadMetadataCheck(comments, documentName, path, caseId, stepList,
		actionValue) {
	// alert(comments);
	// alert("Not Null");

	// alert("AV1:"+actionValue);
	if (comments == '<br>' || comments == '<p>&nbsp;</p>') {
		// alert("empty");
		$('#comments').val('');
		loadMetadata(documentName, path, caseId, stepList);
	} else if (actionValue != "") {
		reasonCheck(actionValue);
		// alert("AV2:" +actionValue);f
	}

	else {
		// alert("Data");
		$("#postConfirmation").dialog(
				{
					resizable : false,
					width : 450,
					height : 150,
					modal : true,
					buttons : {
						"Yes" : {
							text : 'Yes',
							click : function() {
								$(this).dialog("close");
								$.ajax({
									type : "POST",
									url : "dash/postReview",
									cache : false,
									data : $('#frmReviews').serialize(),
									success : function(data) {
										$('#comments').val('');
										loadMetadata(documentName, path,
												caseId, stepList);
									}
								});

								// return false;
							}
						},
						"No" : {
							text : "No",
							click : function() {
								$(this).dialog("close");
								$('#comments').val('');
								loadMetadata(documentName, path, caseId,
										stepList);
							}
						},
						"Cancel" : {
							text : "Cancel",
							click : function() {
								$(this).dialog("close");
								return false;
							}
						}

					}

				});
		// return false;
	}
}

function loadMetadata(documentName, path, caseId, stepList,adminMeta) {

	 /*alert("inside metadata");
	alert(stepList);*/
	//alert("Step List "+stepList);
	var comments = $("#comments").val();

	var isVisible = $('#actionReason').is(':visible');
	var actionValue = "";
	if (isVisible) {
		actionValue = $('#actionReason').val();
	}

	if (comments != "" && comments != undefined || actionValue != ""
			&& actionValue != undefined) {

		loadMetadataCheck(comments, documentName, path, caseId, stepList,
				actionValue);
	} else {

		// alert("In Metadata");
		var patt1 = new RegExp("&", "g");
		// var patt2 = new RegExp("#", "g");
		//
		if (patt1.test(documentName) == true) {
			// alert("***** In Match Pattern 1");
			documentName = documentName.replace(patt1, "*");
			// alert(documentName);
		}
		//
		// if (patt2.test(documentName) == true) {
		// // alert("***** In Match Pattern 2");
		// documentName = documentName.replace(patt2, "@");
		// // alert(documentName);
		// }
		if (caseId == 0) {
			// alert("In Non Workflow");
			$.ajax({
				type : "GET",
				url : "dash/metaData" + "?path=" + path + "&documentName="
						+ documentName + "&caseId=" + caseId + "&stepList="
						+ stepList +"&adminMeta="+adminMeta,
				cache : false,
				success : function(data) {
					$("#dynContentMetadata").html(data);
					$("#dynContentMetadata").show();
					$("#dynamicContent").html(data);
					$("#dynContentMetadata").show();
					$('#leftlinks').hide();
					$('#myIntrays').hide();
					$('#downloadedList').hide();
					$('#actionedList').hide();
					$('#header2').hide();
					
					$('#dynContent').hide();
					$('#csvDownloadSearch').hide();

					// For Header visibility disable in properties page

					$('#downloadTemplateImg').hide();
					$('#quickUploadImg').hide();
					$('#uploadDocumentImg').hide();
					$('#formsImg').hide();

					// For Document Properties Page
					$('#documentPropertiesNonWorkflow').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						/*
						 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : {
						 * "aButtons" : [ { "sExtends" : "print", "sButtonText" :
						 * "", "sMessage" : "Generated by Document Portal" } ] },
						 */
						"sScrollX" : "100%",
						"aaSorting" : [],
						"aoColumns" : [ {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						} ]
					});
				}
			});
		}

		else {
			//alert(stepList);
			$.ajax({
				type : "GET",
				url : "dash/metaData" + "?path=" + path + "&documentName="
						+ documentName + "&caseId=" + caseId + "&stepList="
						+ stepList+"&adminMeta="+adminMeta,
				cache : false,
				success : function(data) {
					$("#dynContentMetadata").html(data);
					$("#dynContentMetadata").show();
					$("#dynamicContent").html(data);
					$('#postConfirm-dialog').hide();
					$('#checkInProgress').hide();
					// $('#postConfirmation').hide();
					$('#csvDownloadSearch').hide();
					$('#review').hide();
					$('#docReview').hide();

					// $("#properties").hide();
					$("#fileCheckin").hide();
					// $('#docProperties').hide();
					$('#editAttrDiv').hide();
					$('#uploadNew').hide();
					$('#actionReason').hide();
					$('#downloadedList').hide();
					$('#actionedList').hide();
					$('#leftlinks').hide();
					$('#myIntrays').hide();
					
					$('#header2').hide();
					$('#dynContent').hide();

					// For Header visibility disable in properties page

					$('#downloadTemplateImg').hide();
					$('#quickUploadImg').hide();
					$('#uploadDocumentImg').hide();
					$('#formsImg').hide();

					// For Claim

					// For Document Properties Page
					$('#documentProperties').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						/*
						 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : {
						 * "aButtons" : [ { "sExtends" : "print", "sButtonText" :
						 * "", "sMessage" : "Generated by Document Portal" } ] },
						 */
						//"sScrollX" : "100%",
						"aaSorting" : [],
						"aoColumns" : [ {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						} ]
					});
				}
			});

			// event.preventDefault();
		}
	}
}

/*function loadMetadataAdmin(documentName, path, caseId, stepList,bool) {

	 alert("inside metadata");
	alert(stepList);
	//alert("Step List "+stepList);
	var comments = $("#comments").val();
	alert(bool);
	var isVisible = $('#actionReason').is(':visible');
	var actionValue = "";
	if (isVisible) {
		actionValue = $('#actionReason').val();
	}

	if (comments != "" && comments != undefined || actionValue != ""
			&& actionValue != undefined) {

		loadMetadataCheck(comments, documentName, path, caseId, stepList,
				actionValue);
	} else {

		// alert("In Metadata");
		var patt1 = new RegExp("&", "g");
		// var patt2 = new RegExp("#", "g");
		//
		if (patt1.test(documentName) == true) {
			// alert("***** In Match Pattern 1");
			documentName = documentName.replace(patt1, "*");
			// alert(documentName);
		}
		//
		// if (patt2.test(documentName) == true) {
		// // alert("***** In Match Pattern 2");
		// documentName = documentName.replace(patt2, "@");
		// // alert(documentName);
		// }
		if (caseId == 0) {
			// alert("In Non Workflow");
			$.ajax({
				type : "GET",
				url : "dash/metaData" + "?path=" + path + "&documentName="
						+ documentName + "&caseId=" + caseId + "&stepList="
						+ stepList+"&bool="
						+ bool,
				cache : false,
				success : function(data) {
					$("#dynamicContend").html(data);
					

					// For Document Properties Page
					$('#documentPropertiesNonWorkflow').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						
						 "sDom" : 'T<"clear">lfrtip', "oTableTools" : {
						 * "aButtons" : [ { "sExtends" : "print", "sButtonText" :
						 * "", "sMessage" : "Generated by Document Portal" } ] },
						 
						"sScrollX" : "100%",
						"aaSorting" : [],
						"aoColumns" : [ {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						} ]
					});
				}
			});
		}

		else {
			//alert(stepList);
			$.ajax({
				type : "GET",
				url : "dash/metaData" + "?path=" + path + "&documentName="
						+ documentName + "&caseId=" + caseId + "&stepList="
						+ stepList+"&bool="
						+ bool,
				cache : false,
				success : function(data) {
					$("#dynamicContent").html(data);
					

					$('#postConfirm-dialog').hide();
					$('#checkInProgress').hide();
					// $('#postConfirmation').hide();
					$('#csvDownloadSearch').hide();
					$('#review').hide();
					$('#docReview').hide();

					// $("#properties").hide();
					$("#fileCheckin").hide();
					// $('#docProperties').hide();
					$('#editAttrDiv').hide();
					$('#uploadNew').hide();
					$('#actionReason').hide();
					$('#downloadedList').hide();
					$('#actionedList').hide();
					$('#leftlinks').hide();
					$('#myIntrays').hide();
					
					$('#header2').hide();
					$('#dynContent').hide();

					// For Header visibility disable in properties page

					$('#downloadTemplateImg').hide();
					$('#quickUploadImg').hide();
					$('#uploadDocumentImg').hide();
					$('#formsImg').hide();

					// For Claim

					// For Document Properties Page
					$('#documentProperties').dataTable({
						"bJQueryUI" : true,
						"sPaginationType" : "full_numbers",
						"iDisplayLength" : 25,
						"bFilter" : true,
						
						  "sDom" : 'T<"clear">lfrtip', "oTableTools" : {
						 * "aButtons" : [ { "sExtends" : "print", "sButtonText" :
						 * "", "sMessage" : "Generated by Document Portal" } ] },
						 
						//"sScrollX" : "100%",
						"aaSorting" : [],
						"aoColumns" : [ {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						} ]
					});
				}
			});

			// event.preventDefault();
		}
	}
}*/

function newDocumentClicked() {
	$("#newContent").show();
}

function cancelCreateClicked() {
	$("#newContent").hide();
}

function goCreateClicked() {
	$("#frmCreateNew").attr("action", "docs/create");
	$("#frmCreateNew").submit();
	$("#newContent").hide();
}

function downloadClicked(event) {
	$("#downloadForm").attr("action", "docs/download");
	$("#downloadForm").submit();
}



// For Quickupload on 03.01.13
function downloadClickedNonWorkflow() {
	// alert("In Down ");

	$("#downloadFormNonWorkflow").attr("action",
			"template/downloadReferenceLinks");
	$("#downloadFormNonWorkflow").submit();

}

function quickUploadCompleted(result, name) {
	$("#quickUploading").remove();
	if (result == 1) {
		var QuickUploadDialog = $("<div id='MenuDialog' title='Quick Upload Result'>\<p>\<span class='ui-icon ui-icon-check' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>File uploaded successfully with name: <b>"
				+ name
				+ "</b></p>\</div>");
		QuickUploadDialog.dialog({
			resizable : false,
			width : 540,
			height : 170,
			modal : true,
			closeOnEscape : false,
			buttons : {
				"Ok" : {
					text : 'Ok',
					click : function() {
						$(this).dialog("close");
						$("#quickUploadResult").html("Success");
						refreshTray();
						refreshLastDownloadedDocument();
						refreshLastActionedDocument();
						loadAllDocuments();

					}
				}
			}
		});
		removeCloseButton();
	} else {
		// alert("Failed to upload a file");
		dialogTemplate("Quick Upload",
				"This file failed to upload due to a large file size.");
		// $("#quickUploadResult").html("Failed to upload a file");
	}
}

function downloadReferenceDocument(url) {

	// alert("Url:" +url);
	$('#templateUrl').val(url);
	$("#downloadReferenceForm").attr("action", "template/downloadTemplate");
	$("#downloadReferenceForm").submit();
}

function downloadPhotos(path, name) {
	// alert("Path:"+path + "name: " +name);
	$('#path').val(path);
	$('#name').val(name);
	$("#downloadPhotosForm").attr("action", "template/downloadReferenceLinks");
	$("#downloadPhotosForm").submit();
}

function downloadForms(userFormId) {
	// alert("inside");
	var url = "forms/showStaticForm" + "?userFormId=" + userFormId
			+ "&TB_iframe=true&height=400&width=400";
	tb_show('View Form', url, null);
}

function downloadReferenceLinksMain(path, name) {
	// alert(canShowRefLinks);

	// alert("Path:"+path + "name: " +name);

	var NewDialog = $("<div id='MenuDialog' title='"
			+ name
			+ "'>\<p>\<span class='ui-icon ui-icon-alert'"
			+ " style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span>"
			+ "<b>Are you sure you want to download this document?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 380,
		height : 170,
		modal : true,
		position : [ "center", 100 ],
		buttons : {
			"Yes" : {
				text : 'Yes',
				click : function() {
					$(this).dialog("close");
					$('#path').val(path);
					$('#name').val(name);
					$("#downloadReferenceForm1").attr("action",
							"template/downloadReferenceLinks");
					$("#downloadReferenceForm1").submit();

				}
			},

			"No" : {

				text : 'No',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	//	
	// $('#path').val(path);
	// $('#name').val(name);
	// $("#downloadReferenceForm1").attr("action",
	// "template/downloadReferenceLinks");
	// $("#downloadReferenceForm1").submit();

}

function btnTestDiv(isCheckout) {
	// alert("in alertFunc");
	if (isCheckout == true) {
		$("#checkinDiv").show();
		$("#checkoutDiv").hide();
	} else {
		$("#checkoutDiv").show();
		$("#checkinDiv").hide();
	}
}

function checkoutClicked(event) {
	// var NewDialog = $("<div id='MenuDialog' title='Check Out
	// Document'>\<p>\<span class='ui-icon ui-icon-alert'"
	// + " style='float: left; margin: 0 7px 20px 0;'> "
	// + "</span>"
	// + "<b>Document successfully checked out.Please do not change the file
	// name</b></p>\</div>");

	var NewDialog = $("<div id='MenuDialog' title='Check Out Document'><table><tr><th class='ui-icon ui-icon-alert'></th>"
			// + " style='float: left; margin: 0 7px 20px 0;'> "
			+ "<th>Attention</th></tr></table> <ul style='margin-left: 20px;'>"
			+ "<li>You are about to check out a document to your local computer</li>"
			+ "<li>Make sure you use SAVE AS and remember the document's location</li>"
			+ "<li>Do not ever change the filename (it will always be working copy)</li>"
			+ "<li> If you are asked to 'replace existing file', say 'yes'</li></ul> </div>");
	NewDialog.dialog({
		resizable : false,
		width : 460,
		height : 210,
		modal : true,
		position : [ "center", 280 ],
		buttons : {
			"Ok" : {
				text : 'Ok',
				click : function() {

					$(this).dialog("close");
					$("#downloadForm").attr("action", "docs/checkout");
					$("#downloadForm").submit();
					$("#checkinDiv").show();
					$("#checkoutDiv").hide();
					// return false;
				}
			}
		}
	});

	// $("#downloadForm").attr("action", "docs/checkout");
	// $("#downloadForm").submit();
	// $("#checkinDiv").show();
	// $("#checkoutDiv").hide();
}

// function checkinClicked(path,documentId)
function checkinClicked(event) {
	// $("#docFile").val('');
	// $("#fileCheckin").show();
	$("#dialog-modal").dialog({
		height : 150,
		width : 450,
		modal : true
	});
}

// Thickbox Checkin issue by 31.01.13

function checkinClicked(docId, path, stepId, docname,adminMeta) {
	// alert("In Check in");
	//alert(adminMeta);
	docname = regexSplit(docname);
	var url = "dash/checkinUpload" + "?documentId=" + docId + "&path=" + path
			+ "&stepId=" + stepId + "&docname=" + docname + "&adminMeta=" + adminMeta
			+ "&TB_iframe=true&height=120&width=570";
	// alert(url);
	tb_show('Check In Document', url, null);
	// $("#checkInUploading").hide();

}

function goCheckinClicked(form) {
	// alert("inside go checkin");
	var docFile = form.elements["docFile123"].value;
	if (docFile == "") {
		// alert("docfile null");
		var NewDialog = $("<div id='MenuDialog' title='File Not Found'>\<p>\<span class='ui-icon ui-icon-alert'"
				+ " style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>"
				+ "<b>Please select a file to check in</b></p>\</div>");
		NewDialog.dialog({
			resizable : false,
			width : 300,
			height : 150,
			modal : true,
			buttons : {
				"Ok" : {
					text : 'Ok',
					click : function() {

						$(this).dialog("close");
						return false;
					}
				}
			}
		});

	}

	else {
		$("#checkInProgress").dialog({
			height : 125,
			width : 254,
			modal : true,
			closeOnEscape : false,
			resizable : false,
			draggable : false
		});

		// alert("Doc:" +docFile);
		var url = form.elements["url"].value;
		form.action = url + "/docs/checkin";
		form.submit();
		// tb_remove();
	}
}

// End TB Check in

function checkinCompleted(result,docName,path,caseid,stepList,adminMeta) {
	
	if (result == 1) {
		// alert("Document successfully checked in");
		// $("#checkInProgress").remove();

		// $('#checkInProgress').dialog('destroy').remove();
		parent.$("#checkInProgress").dialog('close');
		
		//
		tb_remove();
		//alert(result+" "+docName+" "+path+" "+caseid+" "+stepList+" "+adminMeta);
		var NewDialog = $("<div id='MenuDialog' title='Check In Document Result'>\<p>\<span class='ui-icon ui-icon-alert'"
				+ " style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>"
				+ "<b>Document successfully checked in</b></p>\</div>");
		NewDialog.dialog({
			resizable : false,
			width : 300,
			height : 150,
			modal : true,
			buttons : {
				"Ok" : {
					text : 'Ok',
					click : function() {
						$(this).dialog("close");
						$('#checkInProgress').dialog('destroy').remove();
						loadMetadata(docName, path, caseid, stepList,adminMeta);
						
					}
				}
			}
		});
		parent.$("#dialog-modal").dialog('close');
		// $("#dialog-modal",window.opener.document).dialog('close');
		// $("#dialog-modal").dialog("close");
		$("#checkinDiv").hide();
		$("#checkoutDiv").show();
	} else

	{

		dialogTemplate("Check In Document Result",
				"Please select the correct file to check in");
		parent.$("#checkInProgress").dialog('close');

	}

}

function downloadCompleted(result) {
	
	
		var NewDialog = $("<div id='MenuDialog' title='Document Download'>\<p>\<span class='ui-icon ui-icon-alert'"
				+ " style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>"
				+ "<b>"+result+"</b></p>\</div>");
		NewDialog.dialog({
			resizable : false,
			width : 300,
			height : 150,
			modal : true,
			buttons : {
				"Ok" : {
					text : 'Ok',
					click : function() {
						$(this).dialog("close");
						//$('#checkInProgress').dialog('destroy').remove();
						//loadMetadata(docName, path, caseid, stepList,adminMeta);
						
					}
				}
			}
		});
		
}
function createCompleted() {
	checkinStatus = parent.hiddenCreateFrame1.document
			.getElementById("hiddenStatus").value;
	// alert("Completed: "+checkinStatus);
	$("#createStatusDiv").html(checkinStatus);
}

function propCommon() {
	$('#docProperties').show();
	$('#properties').show();

	$('#documentProperties').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"iDisplayLength" : 25,
		"bFilter" : true,
		/*
		 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
		 * "sExtends" : "print", "sButtonText" : "", "sMessage" : "Generated by
		 * Document Portal" } ] },
		 */
		"sScrollX" : "100%",
		"aaSorting" : [],
		"bDestroy" : true,
		"aoColumns" : [ {
			"bSortable" : false
		}, {
			"bSortable" : false
		}, {
			"bSortable" : false
		} ]
	});

	$('#docReview').hide();
	$('#checkInProgress').hide();
	$('#review').hide();
	$('#editAttrDiv').hide();
	$('#comments').val('');

	$('#updateLinksProp').show();

}

function propBtnClicked() {
	// alert("Comments:" +comments);
	if (!document.getElementById("comments")) {
		// alert("Comments ");
		propCommon();
	}
	var comments = document.getElementById("comments").value;
	var isVisible = $('#actionReason').is(':visible');
	var actionValue = "";
	if (isVisible) {
		actionValue = $('#actionReason').val();
	}

	if (comments != "" || (actionValue != "" && actionValue != undefined)) {

		if (comments == '<br>' || comments == '<p>&nbsp;</p>') {
			$('#comments').val('');
			propCommon();

		} else if (actionValue != "" && actionValue != undefined) {
			// alert(actionValue);
			reasonCheck(actionValue);
		} else {
			$("#postConfirmation").dialog({
				resizable : false,
				width : 450,
				height : 150,
				modal : true,
				buttons : {
					"Yes" : {
						text : 'Yes',
						click :

						function() {
							$(this).dialog("close");
							$.ajax({
								type : "POST",
								url : "dash/postReview",
								cache : false,
								data : $('#frmReviews').serialize(),
								success : function(data) {
									$('#comments').val('');
									propCommon();
								}
							});

							// return false;

						}
					},

					"No" : {
						text : "No",
						click : function() {
							$(this).dialog("close");
							propCommon();

						}
					},
					"Cancel" : {
						text : "Cancel",
						click : function() {
							$(this).dialog("close");
							return false;
						}
					}

				}

			});
		}
	}

	else {
		propCommon();

	}

	// $('#postConfirm-dialog').hide();

	// $('#comments').val('');
	// $('#docProperties').show();
	// $('#properties').show();
	// $('#documentProperties').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "bFilter" : true,
	// "sDom": 'T<"clear">lfrtip',
	// "oTableTools": {
	// "aButtons": [
	// {
	// "sExtends": "print",
	// "sMessage": "Generated by DataTables"
	// }
	// ]
	// },
	// "sScrollX": "100%",
	// "aaSorting" : [],
	// "bDestroy": true,
	// "aoColumns" : [ {
	// "bSortable" : false
	// }, {
	// "bSortable" : false
	// }, {
	// "bSortable" : false
	// } ]
	// });
	// $('#docReview').hide();
	// $('#review').hide();
	// $('#editAttrDiv').hide();
	// }
}

function historyBtnClicked(caseId) {
	var url = "dash/historyUrlBlock?caseId=" + caseId;
	var newwindow = window
			.open(
					url,
					'History',
					'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');

	if (window.focus) {
		newwindow.focus();
		// newwindow.location.href=aurl;
	}
	return false;

	// var url = "dash/historyUrlBlock?caseId=" + caseId
	// +"&TB_iframe=true&height=600&width=1000";
	// tb_show('History', url, null);

}

function historyBtnFormClicked(userFormId) {
	var url = "forms/formHistory?userFormId=" + userFormId;
	var newwindow = window
			.open(
					url,
					'History',
					'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');

	if (window.focus) {
		newwindow.focus();
		// newwindow.location.href=aurl;
	}
	return false;

	// var url = "dash/historyUrlBlock?caseId=" + caseId
	// +"&TB_iframe=true&height=600&width=1000";
	// tb_show('History', url, null);

}

function reviewBtnCommon(path, documentName, caseId, stepAssigned, stepList,adminMeta) {
	var body = $("html, body");
	$.ajax({
		type : "GET",
		url : "dash/metaData" + "?path=" + path + "&documentName="
				+ documentName + "&caseId=" + caseId + "&stepList=" + stepList +"&adminMeta="+adminMeta,

		cache : false,
		success : function(data) {
			$('#dynContent').hide();

			$("#dynContentMetadata").html(data);
			$("#dynamicContent").html(data);

			$('#reviewComments').dataTable({

				"oLanguage" : {
					"sEmptyTable" : "No comments to review"
				},
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				// "aaSorting" : [ [ 1, "desc" ] ],
				"sScrollY" : "auto",
				"sScrollX" : "100%",
				"aaSorting" : [],
				"aoColumns" : [ {
					"bSortable" : false
				}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ],
				/*
				 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
				 * "sExtends" : "print", "sButtonText" : "", "sMessage" :
				 * "Generated by Document Portal" } ] },
				 */
				"bFilter" : true
			});

			$('#postConfirm-dialog').hide();
			$('#checkInProgress').hide();
			$("#properties").hide();
			$('#docProperties').hide();
			$("#dynContentMetadata").show();
			$('#docReview').show();
			$("#fileCheckin").hide();
			$('#dialog-modal').hide();
			$('#editAttrDiv').hide();
			$('#uploadNew').hide();
			$('#actionReason').hide();

			$('#updateLinksProp').hide();

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#downloadedList').hide();
			$('#actionedList').hide();
			$('#header2').hide();
			body.animate({scrollTop:0}, '800', 'swing');
		}
	});

}

function reviewBtnClicked(path, documentName, caseId, stepAssigned, stepList,adminMeta) {

	var patt1 = new RegExp("&", "g");
	var patt2 = new RegExp("#", "g");

	if (patt1.test(documentName) == true) {
		// alert("***** In Match Pattern 1");
		documentName = documentName.replace(patt1, "*");
		// alert(documentName);
	}

	if (patt2.test(documentName) == true) {
		// alert("***** In Match Pattern 2");
		documentName = documentName.replace(patt2, "@");
		// alert(documentName);
	}

	reviewBtnCommon(path, documentName, caseId, stepAssigned, stepList,adminMeta);
}

/*function reviewBtnAdminCommon(path, documentName, caseId, stepAssigned, stepList) {
	$.ajax({
		type : "GET",
		url : "adminTemplate/metaData" + "?path=" + path + "&documentName="
				+ documentName + "&caseId=" + caseId + "&stepList=" + stepList,

		cache : false,
		success : function(data) {
			

			$("#dynamicContent").html(data);

			$('#reviewCommentsAdmin').dataTable({

				"oLanguage" : {
					"sEmptyTable" : "No comments to review"
				},
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				// "aaSorting" : [ [ 1, "desc" ] ],
				"sScrollY" : "400px",
				"sScrollX" : "100%",
				"aaSorting" : [],
				"aoColumns" : [ {
					"bSortable" : false
				}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ],
				
				 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
				 * "sExtends" : "print", "sButtonText" : "", "sMessage" :
				 * "Generated by Document Portal" } ] },
				 
				"bFilter" : true
			});

			$('#postConfirm-dialog').hide();
			$('#checkInProgress').hide();
			$("#properties").hide();
			$('#docProperties').hide();
			$("#dynContentMetadata").show();
			$('#docReview').show();
			$("#fileCheckin").hide();
			$('#dialog-modal').hide();
			$('#editAttrDiv').hide();
			$('#uploadNew').hide();
			$('#actionReason').hide();

			$('#updateLinksProp').hide();

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#downloadedList').hide();
			$('#actionedList').hide();
			$('#header2').hide();

		}
	});

}*/

/*function reviewBtnAdminClicked(path, documentName, caseId, stepAssigned, stepList) {

	var patt1 = new RegExp("&", "g");
	var patt2 = new RegExp("#", "g");

	if (patt1.test(documentName) == true) {
		// alert("***** In Match Pattern 1");
		documentName = documentName.replace(patt1, "*");
		// alert(documentName);
	}

	if (patt2.test(documentName) == true) {
		// alert("***** In Match Pattern 2");
		documentName = documentName.replace(patt2, "@");
		// alert(documentName);
	}

	reviewBtnAdminCommon(path, documentName, caseId, stepAssigned, stepList);
}
*/
/*function postReviewAdminClicked() {
	var isVisible = $('#actionReason').is(':visible');
	if (isVisible) {
		var actionValue = $('#actionReason').val();
		if (actionValue != "") {
			dialogTemplate("Save Comments",
					"Please press the next button to save comments");
			return false;
		}
	}
	$.ajax({
		type : "POST",
		url : "adminTemplate/postReview",
		cache : false,
		data : $('#frmReviews').serialize(),
		success : function(data) {

			// $("#dynContent").html(data);
			$('#csvDownloadSearch').hide();
			$("#dynamicContent").html(data);
			$('#dynamicContent').show();
			// $("#dynContentfullScreen").html(data);
			$("#reviews").show();

			$('#reviewCommentsAdmin').dataTable({
				"oLanguage" : {
					"sEmptyTable" : "No comments to review"
				},
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				// "aaSorting" : [ [ 1, "desc" ] ],
				"sScrollY" : "400px",
				"sScrollX" : "100%",
				"aaSorting" : [],
				"aoColumns" : [ {
					"bSortable" : false
				}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ],
				
				 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
				 * "sExtends" : "print", "sButtonText" : "", "sMessage" :
				 * "Generated by Document Portal" } ] },
				 
				"bFilter" : true
			});

			$('#postConfirm-dialog').hide();
			$('#checkInProgress').hide();
			$("#properties").hide();
			$("#fileCheckin").hide();
			$('#editAttrDiv').hide();
			$('#uploadNew').hide();
			$('#docProperties').hide();
			$('#actionReason').hide();

			$('#updateLinksProp').hide();

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#downloadedList').hide();
			$('#actionedList').hide();
			$('#header2').hide(); //Visible Action Trays
			$('#dynContent').hide();

		}
	});
}
*/
function postReviewClicked(adminMeta) {
	var body = $("html, body");
	var isVisible = $('#actionReason').is(':visible');
	if (isVisible) {
		var actionValue = $('#actionReason').val();
		if (actionValue != "") {
			dialogTemplate("Save Comments",
					"Please press the next button to save comments");
			return false;
		}
	}
	loadProgress();
	$.ajax({
		type : "POST",
		url : "dash/postReview?adminMeta="+adminMeta,
		cache : false,
		data : $('#frmReviews').serialize(),
		success : function(data) {

			// $("#dynContent").html(data);
			$('#csvDownloadSearch').hide();
			$("#dynContentMetadata").html(data);
			$("#dynamicContent").html(data);
			$('#dynContentMetadata').show();
			// $("#dynContentfullScreen").html(data);
			$("#reviews").show();

			$('#reviewComments').dataTable({
				"oLanguage" : {
					"sEmptyTable" : "No comments to review"
				},
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				// "aaSorting" : [ [ 1, "desc" ] ],+
				"sScrollY" : "auto",
				"sScrollX" : "100%",
				"aaSorting" : [],
				"aoColumns" : [ {
					"bSortable" : false
				}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ],
				/*
				 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
				 * "sExtends" : "print", "sButtonText" : "", "sMessage" :
				 * "Generated by Document Portal" } ] },
				 */
				"bFilter" : true
			});

			$('#postConfirm-dialog').hide();
			$('#checkInProgress').hide();
			$("#properties").hide();
			$("#fileCheckin").hide();
			$('#editAttrDiv').hide();
			$('#uploadNew').hide();
			$('#docProperties').hide();
			$('#actionReason').hide();

			$('#updateLinksProp').hide();

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#downloadedList').hide();
			$('#actionedList').hide();
			$('#header2').hide(); //Visible Action Trays
			$('#dynContent').hide();
			removeProgress();
			body.animate({scrollTop:0}, '800', 'swing');
		}
	});
}

function colorChangedateCreatedTo() {
	/*$('#datepicker2').css({
		'background-color' : '#333333'
	});*/
}

function colorChangedateCreatedFrom() {
	/*$('#datepicker1').css({
		'background-color' : '#333333'
	});*/
}

function colorChangerelevantdatefrom() {
	/*$('#datepicker3').css({
		'background-color' : '#333333'
	});*/

}

function colorChangerelevantdateto() {
	/*$('#datepicker4').css({
		'background-color' : '#333333'
	});*/

}

function holidayDateSelected(){
	
	var fromDate = $("#holidayDateFrom").val();
	var toDate = $("#holidayDateTo").val();
	
	if (fromDate == "" || toDate == "" ) {
		dialogTemplateLinks("Holiday", "All fields are mandatory");
		return false;
	}

	
	
	var nRelevantDateFrom = fromDate.split("-");
	var nRelevantDateTo = toDate.split("-");
	
	var n1RelevantDateFrom = new Date(nRelevantDateFrom[2],
			nRelevantDateFrom[1] - 1, nRelevantDateFrom[0]);
	//alert(n1RelevantDateFrom);
	var n1RelavantDateTo = new Date(nRelevantDateTo[2], nRelevantDateTo[1] - 1,
			nRelevantDateTo[0]);
	//alert(n1RelavantDateTo);
	
	
	if (fromDate != "" && toDate != "") {

		// alert("From:" +relevantdatefrom);
		// alert("From:" +relevantdateto);

		if (n1RelavantDateTo < n1RelevantDateFrom) {

			var NewDialog = $("<div id='MenuDialog' title='Holiday'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select a valid date range. To date must be greater than start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						/*class : 'butn',
						width : '40px',*/
						click : function() {
							$(this).dialog("close");

							// return false;

						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}
	}
	
	$.ajax({
		type : "POST",
		url : "user/setHolidayDateForUser",
		cache : false,
		data : $('#frmUserHoliday').serialize(),
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
		}
	});
	
	
}

function selectHolidayClicked(userId){
//	alert("inside");
	$.ajax({
		type : "GET",
		url : "user/goshowHoliday?userId=" + userId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}/*,
	error: function(err){
		alert('err');
		
	}
	}).done(function(){
		
		alert('succ');*/
	});
}
function getSearchControlValue(control, fallbackValue) {
	var r = fallbackValue;
	control = document.getElementById(control);
	if (control != null) {
		r = trim(control.value);
		if (r == null)
			return r = fallbackValue;
	}

	return r;
}

// Search Forms & Documents

function showSearch() {
	$('#search').show();
	$('#dynContentMetadata').hide();
	$('#dynContent').show();
}

function allSearchClicked() {
	// var all=$('#all').val();
	// alert("All: " +all);
	//$('#all').val(all);
	$("#dynamicMainFormsDocuments").html("");
}

function allDocumentsClicked() {
	//alert($('#searchoptions input:checked').val());
	// alert("Doc: "+documents);
	//$('#all').val(documents);
	$.ajax({
		type : "GET",
		url : "dash/goshowDocuments",
		cache : false,
		success : function(data) {
			$("#dynamicMainFormsDocuments").html(data);
		}
	});
}

function allFormsClicked() {
	// alert("In Forms");
	//$('#all').val(forms);
	$.ajax({
		type : "GET",
		url : "dash/goshowForms",
		cache : false,
		success : function(data) {
			$("#dynamicMainFormsDocuments").html(data);
		}
	});
}

function raisedDateValidation(fromDate, toDate, n1FromDate, n1ToDate) {
	if (fromDate == "" && toDate != "" || fromDate != "" && toDate == "") {
		if (fromDate == "") {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select raised start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker1').focus();
							/*$('#datepicker1').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		else if (toDate == "") {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select raised end date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker2').focus();
							/*$('#datepicker2').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});

			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}
	}

	else if (fromDate != "" && toDate != "") {
		if (n1ToDate < n1FromDate) {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select a valid date range. End date must be greater than start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',

						click : function() {
							$(this).dialog("close");
							$('#datepicker2').focus();
							/*$('#datepicker2').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		/*$('#datepicker1').css({
			'background-color' : '#333333'
		});
		$('#datepicker2').css({
			'background-color' : '#333333'
		});*/
	}
	loadProgress();
	$.ajax({
		type : "POST",
		url : "dash/searchAll",
		cache : false,
		data : $('#frmSearch').serialize(),
		success : function(data) {
			$('#csvDownloadSearch').show();
			//For Search Issue in Metadata Page
			$("#dynContentMetadata").hide();
			$("#dynContent").show();
			
			$("#dynContent").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
				null,null,{
					"sType" : 'numeric'
				}, null, null, null, null, null, null, null ],

				"bFilter" : true
			});
		},
		error : function(data) {
		}
	});
	removeProgress();

}

function raisedAndRelevantDateValidation(fromDate, toDate, n1FromDate,
		n1ToDate, relevantdatefrom, relevantdateto, n1RelevantDateFrom,
		n1RelavantDateTo, discriminator,specificData) {
	if (fromDate == "" && toDate != "" || fromDate != "" && toDate == "") {
		if (fromDate == "") {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select raised start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker1').focus();
							/*$('#datepicker1').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		else if (toDate == "") {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select raised end date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker2').focus();
							/*$('#datepicker2').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});

			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}
	}

	else if (fromDate != "" && toDate != "") {
		if (n1ToDate < n1FromDate) {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select a valid date range. End date must be greater than start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker2').focus();
							/*$('#datepicker2').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		/*$('#datepicker1').css({
			'background-color' : '#333333'
		});
		$('#datepicker2').css({
			'background-color' : '#333333'
		});*/
	}

	if (relevantdatefrom == "" && relevantdateto != ""
			|| relevantdatefrom != "" && relevantdateto == "") {

		if (relevantdatefrom == "") {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>" + "<b>Please select start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker3').focus();
							/*$('#datepicker3').css({
								'background-color' : 'Red'
							});*/

						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		else if (relevantdateto == "") {
			// alert("Please select end date");
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>" + "<b>Please select end date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker4').focus();
							/*$('#datepicker4').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});

			$('a.ui-dialog-titlebar-close').remove();

			return false;
		}
	}

	else if (relevantdatefrom != "" && relevantdateto != "") {
		if (n1RelavantDateTo < n1RelevantDateFrom) {
			var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
					+ " style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>"
					+ "<b>Please select a valid date range. End date must be greater than start date</b></p>\</div>");
			NewDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				buttons : {
					"Ok" : {
						text : 'Ok',
						click : function() {
							$(this).dialog("close");
							$('#datepicker4').focus();
							/*$('#datepicker4').css({
								'background-color' : 'Red'
							});*/
						}
					}
				}
			});
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		/*$('#datepicker3').css({
			'background-color' : '#333333'
		});
		$('#datepicker4').css({
			'background-color' : '#333333'
		});*/
	}

	if (discriminator == 'D') {
	//	alert("inside docs");
		loadProgress();
		$.ajax({
			type : "POST",
			url : "dash/searchDocuments",
			cache : false,
			data : $('#frmSearch').serialize(),
			success : function(data) {
				//For Search Issue in Metadata Page
				$("#dynContentMetadata").hide();
				$("#dynContent").show();
				
				$('#csvDownloadSearch').show();
				$("#dynContent").html(data);
				$('#example').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"iDisplayLength" : 25,
					"aoColumns" : [ 
					null,null,{	"sType" : 'numeric'}, null, null, null, null, null, null, null ],
					"bFilter" : true
				});
				removeProgress();
			},
			error : function(data) {
				removeProgress();
			}
		});
		
	} else {
		// alert("In Forms");
		loadProgress();
		$.ajax({
			type : "POST",
			url : "dash/searchForms",
			cache : false,
			data : $('#frmSearch').serialize(),
			success : function(data) {
				//For Search Issue in Metadata Page
				$("#dynContentMetadata").hide();
				$("#dynContent").show();
				
				$('#csvDownloadSearch').show();
				$("#dynContent").html(data);
				$('#example').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"iDisplayLength" : 25,
					"aoColumns" : [ 
					null,null,{
						"sType" : 'numeric'
					}, null, null, null, null, null, null, null ],

					"bFilter" : true
				});
				removeProgress();
			},
			error : function(data) {
				removeProgress();
			}
		});
		//removeProgress();
	}
}

function simpleSearchClicked(){
	
	var value =$('#simple').val();
	$('#userId').val();
	if(value==""){
		dialogTemplate("Simple Search",
		"Please enter a value for search");
		return false;
	}
	loadProgress();
	$.ajax({
		type : "POST",
		url : "dash/simpleSearch",
		cache : false,
		data : $('#frmSimpleSearch').serialize(),
		success : function(data) {
			//For Search Issue in Metadata Page
			$('#csvDownloadSearch').show();
			//For Search Issue in Metadata Page
			$("#dynContentMetadata").hide();
			$("#dynContent").show();
			
			$("#dynContent").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ null,null,{
					"sType" : 'numeric'
				}, null, null, null, null, null, null, null ],

				"bFilter" : true
			});
			removeProgress();
		},
		error : function(data) {
			removeProgress();
		}
	});
	//removeProgress();
	
}

function searchBtnClicked() {
	// alert("in search");
	$("#category").val("SC");
	$("#userId").val();
	var documentName = getSearchControlValue("documentName", "");
	var keywords = getSearchControlValue("keywords", "");
	var owner = getSearchControlValue("owner", "");
	var refNo = getSearchControlValue("refNo", "");
	var fromDate = document.getElementById("datepicker1").value;
	var toDate = document.getElementById("datepicker2").value;
	var relevantdatefrom = getSearchControlValue("datepicker3", "");
	var relevantdateto = getSearchControlValue("datepicker4", "");
	var documentType = getSearchControlValue("documentType", "-1");
	var attr1 = getSearchControlValue("metadata1", "-1");
	var attr2 = getSearchControlValue("metadata2", "-1");
	var attr3 = getSearchControlValue("metadata3", "-1");
	/*var attr4 = getSearchControlValue("metadata4", "-1");*/
	var nFromDate = fromDate.split("-");
	var nToDate = toDate.split("-");
	var n1FromDate = new Date(nFromDate[2], nFromDate[1] - 1, nFromDate[0]);
	var n1ToDate = new Date(nToDate[2], nToDate[1] - 1, nToDate[0]);
	var nRelevantDateFrom = relevantdatefrom.split("-");
	var nRelevantDateTo = relevantdateto.split("-");
	var n1RelevantDateFrom = new Date(nRelevantDateFrom[2],
			nRelevantDateFrom[1] - 1, nRelevantDateFrom[0]);
	var n1RelavantDateTo = new Date(nRelevantDateTo[2], nRelevantDateTo[1] - 1,
			nRelevantDateTo[0]);

	var specificData=$('#specificData').val();
	
	var disValue = $('#searchoptions input:checked').val();
	//alert("Value:" +disValue);
	
	$('#discriminator').val(disValue);
	if (disValue == 'M') {
		// alert("in Mix");
		if (documentName == "" && owner == "" && refNo == "" && keywords == ""
				&& fromDate == "" && toDate == "") {
			dialogTemplate("Quick Search",
					"Please enter a value for Quick Search");
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		} else if (documentName != "" || owner != "" || refNo != ""
				|| keywords != "" || fromDate != "" || toDate != "") {
			raisedDateValidation(fromDate, toDate, n1FromDate, n1ToDate);
		}
	} else if (disValue == 'D') {
		// alert("In Doc:");
		if (documentName == "" && owner == "" && refNo == "" && keywords == ""
				&& fromDate == "" && toDate == "" && relevantdatefrom == ""
				&& relevantdateto == "" && documentType == "-1"
				&& attr1 == "-1" && attr2 == "-1" && attr3 == "-1"
				) {
			dialogTemplate("Quick Search",
					"Please enter a value for Quick Search");
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		else if (documentName != "" || owner != "" || refNo != ""
				|| keywords != "" || fromDate == "" || toDate == ""
				|| relevantdatefrom == "" || relevantdateto == ""
				|| documentType != "-1" || attr1 == "-1" || attr2 == "-1"
				|| attr3 == "-1" ) {
			raisedAndRelevantDateValidation(fromDate, toDate, n1FromDate,
					n1ToDate, relevantdatefrom, relevantdateto,
					n1RelevantDateFrom, n1RelavantDateTo, disValue);
		}
	}

	else {
		var formType = getSearchControlValue("formType", "-1");
		if (documentName == "" && owner == "" && refNo == "" && keywords == ""
				&& fromDate == "" && toDate == "" && relevantdatefrom == ""
				&& relevantdateto == "" && formType == "-1" && specificData == "") {
			dialogTemplate("Quick Search",
					"Please enter a value for Quick Search");
			$('a.ui-dialog-titlebar-close').remove();
			return false;
		}

		else if (documentName != "" || owner != "" || refNo != ""
				|| keywords != "" || fromDate == "" || toDate == ""
				|| relevantdatefrom == "" || relevantdateto == ""
				|| formType != "-1" && specificData=="") {
			raisedAndRelevantDateValidation(fromDate, toDate, n1FromDate,
					n1ToDate, relevantdatefrom, relevantdateto,
					n1RelevantDateFrom, n1RelavantDateTo, disValue,specificData);
		}
	}
}


function searchBtnClickedOLd() {

	$("#category").val("SC");

	var relevantdatefrom = getSearchControlValue("datepicker3", "");
	var relevantdateto = getSearchControlValue("datepicker4", "");
	var documentName = getSearchControlValue("documentName", "");
	var discipline = getSearchControlValue("discipline", "-1");
	var sites = getSearchControlValue("sites", -1);
	var documentType = getSearchControlValue("documentType", "-1");
	var owner = getSearchControlValue("owner", "");
	var ebNo = getSearchControlValue("ebNo", "");
	var keywords = getSearchControlValue("keywords", "");

	var nRelevantDateFrom = relevantdatefrom.split("-");
	var nRelevantDateTo = relevantdateto.split("-");

	var n1RelevantDateFrom = new Date(nRelevantDateFrom[2],
			nRelevantDateFrom[1] - 1, nRelevantDateFrom[0]);

	var n1RelavantDateTo = new Date(nRelevantDateTo[2], nRelevantDateTo[1] - 1,
			nRelevantDateTo[0]);

	if (documentName == "" && owner == "" && ebNo == "" && keywords == ""
			&& discipline == "-1" && documentType == "-1" && sites == "-1"
			&& relevantdatefrom == "" && relevantdateto == "") {

		dialogTemplate("Quick Search", "Please enter a value for Quick Search");
		$('a.ui-dialog-titlebar-close').remove();
		return false;

	}

	else if (documentName != "" || owner != "" || ebNo != "" || keywords != ""
			|| discipline != "-1" || documentType != "-1" || sites != "-1"
			|| relevantdatefrom != "" || relevantdateto != "") {
		// alert("*** In Case 2");

		if (relevantdatefrom == "" && relevantdateto != ""
				|| relevantdatefrom != "" && relevantdateto == "") {

			if (relevantdatefrom == "") {
				var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select start date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 150,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							click : function() {
								$(this).dialog("close");
								$('#datepicker3').focus();
								// $('#datepicker3').datepicker('show');
								/*$('#datepicker3').css({
									'background-color' : 'Red'
								});*/

							}
						}
					}
				});
				$('a.ui-dialog-titlebar-close').remove();
				return false;
			}

			else if (relevantdateto == "") {
				// alert("Please select end date");
				var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select end date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 150,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							click : function() {
								$(this).dialog("close");
								$('#datepicker4').focus();
								// $('#datepicker4').datepicker('show');
								/*$('#datepicker4').css({
									'background-color' : 'Red'
								});*/
							}
						}
					}
				});

				$('a.ui-dialog-titlebar-close').remove();

				return false;
			}
		}

		else if (relevantdatefrom != "" && relevantdateto != "") {

			//				
			// alert("rDF1 :" +n1RelevantDateFrom);
			// alert("rDT1:" +n1RelavantDateTo);

			if (n1RelavantDateTo < n1RelevantDateFrom) {

				// alert("rDF2:" +n1RelevantDateFrom);
				// alert("rDT2:" +n1RelavantDateTo);

				var NewDialog = $("<div id='MenuDialog' title='Quick Search'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select a valid date range. End date must be greater than start date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 150,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							click : function() {
								$(this).dialog("close");
								$('#datepicker4').focus();
								// $('#datepicker4').datepicker('show');
								/*$('#datepicker4').css({
									'background-color' : 'Red'
								});*/

								// return false;

							}
						}
					}
				});
				$('a.ui-dialog-titlebar-close').remove();
				return false;
			}

			/*$('#datepicker3').css({
				'background-color' : '#333333'
			});
			$('#datepicker4').css({
				'background-color' : '#333333'
			});*/
		}

		// if (fromDate == "" && toDate != "" || fromDate != "" && toDate == "")
		// {
		//
		// if (fromDate == "") {
		// // alert("Please select start date");
		// var NewDialog = $("<div id='MenuDialog' title='Quick
		// Search'>\<p>\<span class='ui-icon ui-icon-alert'"
		// + " style='float: left; margin: 0 7px 20px 0;'> "
		// + "</span>"
		// + "<b>Please select raised start date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 150,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		//							
		//							
		// click : function() {
		//
		// $(this).dialog("close");
		// $('#datepicker1').focus();
		// $('#datepicker1').css({
		// 'background-color' : 'Red'
		// });
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close').remove();
		// return false;
		// }
		//
		// else if (toDate == "") {
		// // alert("Please select end date");
		// var NewDialog = $("<div id='MenuDialog' title='Quick
		// Search'>\<p>\<span class='ui-icon ui-icon-alert'"
		// + " style='float: left; margin: 0 7px 20px 0;'> "
		// + "</span>"
		// + "<b>Please select raised end date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 150,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		//							
		//							
		// click : function() {
		// $(this).dialog("close");
		// $('#datepicker2').focus();
		// $('#datepicker2').css({
		// 'background-color' : 'Red'
		// });
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close').remove();
		//
		// return false;
		// }

		// }

		// else if (fromDate != "" && toDate != "") {
		//
		// if (n1ToDate < n1FromDate) {
		//
		// var NewDialog = $("<div id='MenuDialog' title='Quick
		// Search'>\<p>\<span class='ui-icon ui-icon-alert'"
		// + " style='float: left; margin: 0 7px 20px 0;'> "
		// + "</span>"
		// + "<b>Please select a valid raised date range. End date must be
		// greater than start date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 150,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		//							
		//							
		// click : function() {
		// $(this).dialog("close");
		// $('#datepicker2').focus();
		// $('#datepicker2').css({
		// 'background-color' : 'Red'
		// });
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close').remove();
		// return false;
		// }
		//
		// $('#datepicker1').css({
		// 'background-color' : '#333333'
		// });
		// $('#datepicker2').css({
		// 'background-color' : '#333333'
		// });
		// }

		$.ajax({
			type : "POST",
			url : "dash/search",
			cache : false,
			data : $('#frmSearch').serialize(),
			success : function(data) {

				$('#csvDownloadSearch').show();
				$("#dynContent").html(data);
				$('#example').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"iDisplayLength" : 25,
					"aoColumns" : [ {
						"sType" : 'numeric'
					}, null, null, null, null, null, null, null ],

					"bFilter" : true
				});
				/*
				 * $('#example').dataTable({ "bJQueryUI" : true,
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25, //
				 * "sScrollY" : "450px", "bFilter" : true });
				 */

			},
			error : function(data) {
				// alert('failure');
			}
		});

	}

}

function removeCloseButton() {
	$('a.ui-dialog-titlebar-close').remove();
}

function trayClicked(trayLabel) {
	$("#category").val("TC");
	$.ajax({
		type : "GET",
		url : "dash/trayDocs" + "?trayLabel=" + trayLabel,
		cache : false,
		success : function(data) {
			// alert(data);
			$('#csvDownloadSearch').hide();
			$("#dynContent").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bDestroy" : true,
				// "sScrollY" : "450px",
				"bFilter" : true
			});

			$('#csvDownloadSearch').show();

			// $("#docList table tbody tr").live('click',docListClicked);
		}
	});
}

function createNewClicked() {

	$.ajax({
		type : "GET",
		url : "case/goCreate",
		cache : false,
		success : function(data) {
			// alert(data);
			$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$("#dynContent").html(data);
			$('#dynContent').show();

			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();

			// $('dynContentMetadata').
			$("#attrFormDiv").hide();
			$("#header2title").hide();
		}
	});
}

function createCaseClicked() {

	// alert("In Create Case");
	var modelId = $("#modelForCase").val();
	$.ajax({
		type : "GET",
		url : "case/createCase" + "?modelId=" + modelId,
		cache : false,
		success : function(data) {
			$("#createDiv").hide();
			$('#csvDownloadSearch').hide();
			$("#header1").hide();
			$("#header2").show();
			$('#downloadedList').show();
			$('#actionedList').show();
			// $('#myIntrays').show();
			$('#leftlinks').show();

			$("#attrFormDiv").load(refreshTray());
			
			$("#attrFormDiv").show();
			$("#attrFormDiv").html(data);

			$("#editCaseAttrDiv").hide();

		}
	});
}

function dialogTemplate(title, value) {

	var NewDialog = $("<div id='MenuDialog' align='center' title='" + title
			+ "'>\<p>\<span class='ui-icon ui-icon-alert'"
			+ " style='float: left; margin: 0 7px 20px 0;'> " + "</span>"
			+ "<b>" + value + "</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 300,
		height : 150,
		modal : true,
		align : 'center',
		buttons : {
			
			"Ok" : {
				text : 'Ok',
				align : 'center',
				click : function() {
					$(this).dialog("close");
					$('#actionReason').focus();
					// return false;
				}
			}
		}
	});

}

function saveCaseAttrClicked() {
	var timeBound = $("#timeBound").val();
	var target = $('input:[id^="id_"]').val();
	var ebNumber = $('input:[id="it_Eb Number"]').val();
	var docFile = $("#docFile").val();
	var keywords = $('input:[id="it_Keywords"]').val();

	// patt=/^[a-zA-Z0-9\s\[\]]*$/g;
	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;

	if (target == "") {
		/*dialogTemplate("Upload Document", "Enter submission date");
		// $(foc[0]).focus();
		$('input:[id^="id_"]').focus();
		return false;*/
		target=new Date();
	}
	
	if (!patt.test(keywords) == true) {
		dialogTemplate("Upload Document",
				"Special characters not allowed in title/keywords");
		return false;
	}

	var now = new Date();
	now.setHours(0, 0, 0, 0);
	if (timeBound == 'Y') {
		if (target == "") {
			/*dialogTemplate("Upload Document", "Enter submission date");
			// $(foc[0]).focus();
			$('input:[id^="id_"]').focus();
			return false;*/
			target=new Date();
		} else {

			//var n = target.split("-");
			/*var targDate = new Date(n[2], n[1] - 1, n[0]);
			if (targDate <= now) {
				dialogTemplate("Upload Document",
						"Enter submission date greater than today");
				$('input:[id^="id_"]').focus();
				return false;
			} else {*/
				if ($.trim(keywords) == "") {
					dialogTemplate("Upload Document", "Please enter keywords");
					$('input:[id="it_Keywords').focus();
					return false;
				}
				if ($.trim($('input:[id="it_Keywords"]').val()).length > 100) {
					dialogTemplate("Upload Document",
							"Please enter keywords less than 100 characters");
					$('input:[id="it_Keywords').focus();
					return false;
				}
				if (docFile == "") {
					dialogTemplate("Upload Document",
							"Please select a file to upload");
					// $(foc[2]).focus();
					$('#docFile').focus();
					return false;
				}

				uploadCorrectFile("Upload Document",
						"Please remember to upload the correct file", keywords,
						docFile, 1);

				// getMetaDataForWorkflow(keywords, docFile);
			}
		//}
	} else {
		$("#submitAttrSaveForm").attr("action", "docupload/saveCaseUploadDoc");
		$("#submitAttrSaveForm").submit();
	}
}

function uploadNewDocClicked() {
	$("#frmCaseUploadNew").attr("action", "case/newUpload?");
	$("#frmCaseUploadNew").submit();
}

function cancelUploadNewDocClicked() {
	$("#uploadDocNew").hide();
}

function uploadPhotoCompleted(result) {

	if (result == 1) {
		alert("Photo successfully uploaded");
		$("#photoUploadResult").html("Success");
		parent.$("#photo-upload").dialog('close');

	}

	else {

		alert("Failed to upload photo");
		$("#photoUploadResult").html("Failure");

	}

}

function uploadNewDocCompleted(result, name, modelName) {
	// alert("in upl" +name+":"+modelName);
	$("#uploading").remove();
	if (result == 1) {

		var UploadDocumentDialog = $("<div id='MenuDialog' title='Upload Document Result'>\<p>\<span class='ui-icon ui-icon-check' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>Document uploaded successfully with name: <b>"
				+ name
				+ "</b> with <b>" + modelName + "</b></p>\</div>");

		UploadDocumentDialog.dialog({
			resizable : false,
			width : 540,
			height : 170,
			modal : true,
			closeOnEscape : false,
			buttons : {
				"Ok" : {
					text : 'Ok',

					click : function() {
						$(this).dialog("close");
						$("#newDocCrtStatusDiv").html("Success");
						refreshTray();
						refreshLastDownloadedDocument();
						refreshLastActionedDocument();
						loadAllDocuments();

					}
				}
			}
		});
		removeCloseButton();
	}

	else if (result == 2) {

		dialogTemplate("Document Reference", "Reference links are invalid");
		$("#newDocCrtStatusDiv").html("Reference links are invalid");
		// return false;
	}
	
	else if(result == 3) {
		dialogTemplate("Upload Document Result", "The user does not have the security group mapped");
		
	}

	else {
		// alert("Failed to upload document,please check your reference links");
		dialogTemplate("Upload Document Result",
				"This file failed to upload due to a large file size.");
		// $("#newDocCrtStatusDiv").html(
		// "Failed to upload document");
	}
}

// Commented on 17.01.13 due to enhance this fn. above
// function uploadNewDocCompleted(result) {
// $("#uploading").remove();
//
// if (result == 1) {
// alert("Document successfully uploaded");
// parent.$("#dialog-confirm").dialog('close');
//
// $("#newDocCrtStatusDiv").html("Success");
// refreshTray();
// loadAllDocuments();
// }
//
// else if (result == 2) {
//
// alert("Reference links are invalid");
// $("#newDocCrtStatusDiv").html("Reference links are invalid");
// // return false;
// }
//
// else {
// // alert("Failed to upload document,please check your reference links");
// $("#newDocCrtStatusDiv").html(
// "Failed to upload document,either document already exists or check your
// reference links");
// }
// }

function drawDatePicker() {
	$('input:[dtpk=true]').datepicker({
		dateFormat : 'dd-mm-yy'
	});
}

function adminLogoutClicked() {
	$("#adminLogoutFrm").attr("action", "logout");
	$("#adminLogoutFrm").submit();

}

function logoutClicked() {
	$("#logoutFrm").attr("action", "logout");
	$("#logoutFrm").submit();
}

function refreshTray() {
	// console.log("refreshtray");
	$.ajax({
		type : "GET",
		url : "getTray",
		cache : false,
		success : function(data) {
			// alert("In Refresh Data");
			$("#trayDiv").html(data);
		}
	});
}

function refreshLastDownloadedDocument(){
	$.ajax({
		type : "GET",
		url : "getDownloadedDocument",
		cache : false,
		success : function(data) {
			// alert("In Refresh Data");
			$("#lastDownloadedDiv").html(data);
		}
	});
}

function refreshLastActionedDocument(){
	$.ajax({
		type : "GET",
		url : "getActionedDocument",
		cache : false,
		success : function(data) {
			// alert("In Refresh Data");
			$("#lastActionedDiv").html(data);
		}
	});
}

function refreshTrayForTemplate() {
	$.ajax({
		type : "GET",
		url : "getTray",
		cache : false,
		success : function(data) {
			$("#trayDiv").html(data);
			loadAllDocumentsForTemplate();
		}
	});
}

function editClicked() {

	// if($('#comments').is(':disabled')){
	// alert("Diasbled");
	// return false;
	// }

	// var isDisabled = $('#comments').prop('disabled');

	var comments = document.getElementById("comments").value;
	// alert("Comments: "+comments);
	if (comments != "") {
		// alert("Do you want to post comments before going to update
		// properties?");
		// return false;

		// alert("In Dialog Outer");

		$("#postConfirmation").dialog({
			resizable : false,
			width : 450,
			height : 150,
			modal : true,
			buttons : {
				"Yes" : {
					text : 'Yes',

					click :

					function() {
						$(this).dialog("close");
						return false;

					}
				},
				"No" : {
					text : "No",

					click : function() {
						$(this).dialog("close");

						$('#comments').val('');
						$('#review').hide();
						$('#docReview').hide();

						$('#docProperties').show();

						$('#editAttrDiv').show();

						$('#properties').show();

						$('#updateLinksProp').show();

						$('#documentProperties').dataTable({
							"bJQueryUI" : true,
							"sPaginationType" : "full_numbers",
							"iDisplayLength" : 50,
							"bFilter" : true,
							"sScrollX" : "100%",
							"aaSorting" : [],
							"bDestroy" : true,
							"aoColumns" : [ {
								"bSortable" : false
							}, {
								"bSortable" : false
							}, {
								"bSortable" : false
							} ]
						});

						// $('#editAttrDiv').onLoad(drawDatePicker());

					}
				}

			}

		});
	}

	else {
		$('#comments').val('');
		$('#review').hide();
		$('#docReview').hide();
		$('#docProperties').show();
		$('#editAttrDiv').show();
		$('#properties').show();
		$('#documentProperties').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"iDisplayLength" : 50,
			"bFilter" : true,
			"sScrollX" : "100%",
			"aaSorting" : [],
			"bDestroy" : true,
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			} ]
		});
		// $('#editAttrDiv').onLoad(drawDatePicker());
	}
}

function saveAttrClicked(adminMeta) {
	var keywords = $('#it_Keywords').val();

	if ($.trim(keywords) == "") {

		dialogTemplate("Update Properties", "Please enter keywords");
		return false;
	}

	if ($.trim($('#it_Keywords').val()).length > 200) {
		dialogTemplate("Update Properties",
				"Please enter keywords less than 200 characters");
		$('#it_Keywords').focus();
		return false;
	}

	// patt=/^[a-zA-Z0-9\s\[\]]*$/g;
	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
	if (!patt.test(keywords) == true) {

		dialogTemplate("Update Properties",
				"Special characters not allowed in keywords");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "dash/saveAttr?adminMeta="+adminMeta,
		cache : false,
		data : $('#frmAttr').serialize(),
		success : function(data) {
			// $("#dynContent").html(data);
			$("#dynContentMetadata").html(data);
			$("#dynamicContent").html(data);
			$('#dynContentMetadata').show();

			$('#postConfirm-dialog').hide();
			// $('#reviewComments').dataTable({ "bJQueryUI" : true,
			// "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
			// "sScrollY" : "400px",
			// "sScrollX" : "100%",
			// "sDom": 'T<"clear">lfrtip',
			// "oTableTools": {
			// "aButtons": [
			// {
			// "sExtends": "print",
			// "sButtonText": "",
			// "sMessage": "Generated by Document Portal"
			// }
			// ]
			// },
			//				     
			// "bFilter" : true });

			$('#actionReason').hide();
			$("#docProperties").hide();
			$("#fileCheckin").hide();
			$('#editAttrDiv').hide();
			$("#uploadNew").hide();
			// $("#properties").hide();
			propCommon();

		}
	});
}

/*function saveAttrAdminClicked() {
	var keywords = $('#it_Keywords').val();

	if ($.trim(keywords) == "") {

		dialogTemplate("Update Properties", "Please enter keywords");
		return false;
	}

	if ($.trim($('#it_Keywords').val()).length > 200) {
		dialogTemplate("Update Properties",
				"Please enter keywords less than 200 characters");
		$('#it_Keywords').focus();
		return false;
	}

	// patt=/^[a-zA-Z0-9\s\[\]]*$/g;
	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
	if (!patt.test(keywords) == true) {

		dialogTemplate("Update Properties",
				"Special characters not allowed in keywords");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "adminTemplate/saveAttr",
		cache : false,
		data : $('#frmAttr').serialize(),
		success : function(data) {
			// $("#dynContent").html(data);
			$("#dynamicContent").html(data);
			$('#dynamicContent').show();

			$('#postConfirm-dialog').hide();
			// $('#reviewComments').dataTable({ "bJQueryUI" : true,
			// "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
			// "sScrollY" : "400px",
			// "sScrollX" : "100%",
			// "sDom": 'T<"clear">lfrtip',
			// "oTableTools": {
			// "aButtons": [
			// {
			// "sExtends": "print",
			// "sButtonText": "",
			// "sMessage": "Generated by Document Portal"
			// }
			// ]
			// },
			//				     
			// "bFilter" : true });

			$('#actionReason').hide();
			$("#docProperties").hide();
			$("#fileCheckin").hide();
			$('#editAttrDiv').hide();
			$("#uploadNew").hide();
			// $("#properties").hide();
			propCommon();

		}
	});
}*/

function goActionValidations(modelName, action) {

	$('#actionReason').show();

	if ($.trim(modelName) == 'Fast Track'
			|| $.trim(modelName) == 'Fast Track ITPS') {
		if (action == 'Rejected') {
			dialogTemplate(modelName, "Enter the reason for rejection");
			document.getElementById("actionReason").focus();
			return false;
		}

		else if (action == 'Prepared') {
			// dialogTemplate(modelName, "Please enter reason for selecting a
			// Fast Track workflow");
			$('#actionReason').focus();
			dialogTemplate(modelName,
					"Please enter reason for selecting a Fast Track workflow");
			// document.getElementById("actionReason").focus();
			return false;
		}

		else if (action == 'Published') {

			dialogTemplate(modelName,
					"Have you uploaded this document onto Eb? Please enter the Transmittal Number?");
			document.getElementById("actionReason").focus();
			return false;
		}
	} else {

		if (action == 'Published') {

			dialogTemplate(modelName,
					"Have you uploaded this document onto Eb? Please enter the Transmittal Number?");
			document.getElementById("actionReason").focus();
			return false;
		}

		else {
			dialogTemplate(modelName, "Enter the reason");
			document.getElementById("actionReason").focus();
			return false;
		}

	}
	goActionCommon();
}

function goActionCommonWithComments(comments, path, docName) {

	// alert("In ActionCommonWithComments : "+comments+" "+path+" "+docName);
	$('#actionComments').val(comments);
	$('#actionDocPath').val(path);
	$('#actionDocName').val(docName);

	$("#goAction").hide();
	loadProgress();
	$.ajax({
		type : "POST",
		url : "dash/goAction",
		data : $('#goActionForm').serialize(),
		success : function(data) {
			removeProgress();
		},
		error : function(data) {
			refreshToolbar();
			removeProgress();
		}
	});
	
}

function postConfirmationDialogWithoutReason(comments, path, docName) {
	$("#postConfirmation").dialog({
		resizable : false,
		width : 450,
		height : 150,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				click : function() {
					$(this).dialog("close");
					goActionCommonWithComments(comments, path, docName);
					// return false;

				}
			},
			"No" : {
				text : "No",
				click : function() {
					$(this).dialog("close");
					// $("#comments").val('');
					goActionCommon();
				}
			},
			"Cancel" : {
				text : "Cancel",
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}

	});

}

function actionSelected() {
	var actionValue = $("#actions").val();
	if (actionValue != -1) {
		var valText = document.getElementById("jsonvals").value;
		var data = eval("(" + valText + ")");
		var index;
		for (index = 0; index < data.vals.length; index++) {
			if (data.vals[index].n == actionValue) {
				break;
			}
		}
		if (index < data.vals.length) {
			var reqd = data.vals[index].r;
			if (reqd == 'y') {

				if (actionValue == 'Published') {

					dialogTemplate("Transmittal Number",
							"Have you uploaded this document onto Eb? Please enter the Transmittal Number");
					$('#actionReason').show();
				}

				else {
					$('#actionReason').show();
					$('#actionReason').focus();
				}
			} else {
				$('#actionReason').hide();

			}
		}
	} else {
		$('#actionReason').hide();
	}
}

function goActionClicked(modelName, path, docName, popUpNeeded) {
	// alert("Pop Up:" +popUpNeeded);
	// alert("inside");
	var defaultValue = $("#actions").val();
	if (defaultValue == -1) {
		dialogTemplate("Alert",
				"Please select a valid action before selecting Next");
		return false;
	}

	var isVisible = $('#btnCheckin').is(':visible');
	if (isVisible) {
		dialogTemplate("Alert",
				"Please check in your file before selecting Next");
		return false;

	}
	var valText = document.getElementById("jsonvals").value;
	var data = eval("(" + valText + ")");
	var action = document.getElementById("actions").value;
	var index;
	for (index = 0; index < data.vals.length; index++) {
		if (data.vals[index].n == action) {
			break;
		}
	}

	if (index < data.vals.length) {
		var reqd = data.vals[index].r;
		// alert(regd);
		var rsn = document.getElementById("actionReason").value.trim();
		if (!$("#comments")) {

			return false;
		}
		var comments = $("#comments").val();
		if (comments == null)
			comments = "";

		if (reqd == 'y' && rsn.length == 0) {
			if (comments == "") {
				var isVisible = $('#saveComment').is(':visible');

				if (isVisible) {
					if (popUpNeeded == 'true') {
						// alert("If");
						// return false;
						var NewDialog = $("<div id='MenuDialog' title='Document Comments'>\<p>\<span class='ui-icon ui-icon-info' "
								+ "style='float: left; margin: 0 7px 20px 0;'> "
								+ "</span><b>Would you like to enter a comment?</b></p>\</div>");
						NewDialog.dialog({
							resizable : false,
							width : 300,
							height : 150,
							modal : true,
							buttons : {
								"Yes" : {
									text : 'Yes',
									click : function() {
										$(this).dialog("close");
										return false;
									}
								},
								"No" : {
									text : 'No',
									click : function() {
										$(this).dialog("close");
										goActionValidations(modelName, action);
									}
								}
							}
						});
					} else {
						// alert("Else in reason empty");
						goActionValidations(modelName, action);

						// return false;
					}

				} else {
					goActionValidations(modelName, action);
				}
			} else {
				goActionValidations(modelName, action);
			}
			//

			// goActionValidations(modelName,action);
			// }
		}

		else {

			// alert("Inside else");
			if (!$("#comments")) {
				// alert("Inside if");
				goActionCommon();

			} else {
				// alert("Inside else 1");
				if (comments != "") {
					// alert(comments);
					// alert("Not Null");
					// alert("Inside if");
					if (comments.trim() == '<br>'
							|| comments.trim() == '<p>&nbsp;</p>') {
						// alert("Inside");
						$("#comments").val('');
						goActionCommon();
						// return false;
					} else {
						// alert("Inside else 2");
						postConfirmationDialogWithoutReason(comments, path,
								docName);
					}

					// postConfirmationDialogWithoutReason();
					// postConfirmationDialogWithoutReason(comments,path,docName);
				} else {
					// alert(comments);
					var isVisible = $('#saveComment').is(':visible');
					if (isVisible) {

						if (popUpNeeded == 'true') {
							// alert("If");
							var NewDialog = $("<div id='MenuDialog' title='Document Comments'>\<p>\<span class='ui-icon ui-icon-info' "
									+ "style='float: left; margin: 0 7px 20px 0;'> "
									+ "</span><b>Would you like to enter a comment?</b></p>\</div>");
							NewDialog.dialog({
								resizable : false,
								width : 280,
								height : 150,
								modal : true,
								buttons : {
									"Yes" : {
										text : 'Yes',
										click : function() {
											$(this).dialog("close");
											return false;
										}
									},
									"No" : {
										text : 'No',
										click : function() {
											$(this).dialog("close");
											goActionCommonWithComments(
													'No Comments', path,
													docName);
											// goActionCommon();
										}
									}
								}
							});
						} else {
							// alert("Else in second");
							goActionCommon();
						}

					}

					else {
						// alert("Third");
						goActionCommon();

					}

					// goActionCommon();

				}
				// goActionCommon();
			}
		}
	}
}

function goActionCommon() {
	// alert("inside");
	$("#goAction").hide();
	loadProgress();
	$.ajax({
		type : "POST",
		url : "dash/goAction",
		data : $('#goActionForm').serialize(),
		success : function(data) {
			removeProgress();
		},
		error : function(data) {
			// $("#dynContentMetadata").hide();
			// $("#dynContent").show();
			// $('#myIntrays').show();
			// $('#leftlinks').show();
			// refreshTray();
			// loadAllDocuments();
			refreshToolbar();
			removeProgress();
		}
	});
	

}

function listTemplatesClicked() {
	var modelId = $("#modelForCase").val();
	$.ajax({
		type : "GET",
		url : "case/listTemplates" + "?modelId=" + modelId,
		cache : false,
		success : function(data) {
			$("#listTemplatesDiv").html(data);
		}
	});
}

function downloadTemplateClicked(docType, modelId) {

	// alert("Inside");

	// dialogTemplate("Download Template","1. Click on Open File.2. Enable
	// Editing.3. Save the File to a memorable location.");

	$('#csvDownloadSearch').hide();
	$("#modelId").val(modelId);
	$("#docType").val(docType);
	$("#downloadTemplateForm").attr("action", "case/downloadTemplate");
	$("#downloadTemplateForm").submit();
}

function uploadDocumentClicked() {
	$("#uploadDocNew").show();
}

function showTableForCase(caseId) {
	$.ajax({
		type : "GET",
		url : "case/showSteps" + "?caseId=" + caseId,
		cache : false,
		success : function(data) {
			$("#dynContent").html(data);
			$("#stepTable").dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"bFilter" : false
			});
		}
	});
}

// function csvDownloadSearchClicked() {
// var csv_value = $('#example').Datatable2CSV(
// {
//
// delivery : 'value',
// header : [ 'Name', 'Status-Action', 'Submission Date',
// 'Document Owner', 'Workflow Process', 'Document Type',
// 'Eb No', 'Author' ]
//
// });
// // alert(csv_value);
//
// $("#csv_text").val(csv_value);
// $("#csvForm1").attr("action", "dash/csv1");
// $("#csvForm1").submit();
//
// }

function csvDownloadSearchClicked() {

	var oTable = $('#example').dataTable();
	var csv_value = oTable.fnGetData();
	$("#csv_text").val(csv_value);
	$("#csvForm1").attr("action", "dash/csv1");
	$("#csvForm1").submit();

}

/*CSV DOWNLOAD ONCLICK METHOD FOR FORM
 * function csvDownloadFormClicked(){
	
var oTable = $('#formTableVal').dataTable();
var csv_value = oTable.fnGetData();
alert(csv_value);
$("#csv1_text").val(csv_value);
$("#csvDownloadForm").attr("action", "csvForm");
$("#csvDownloadForm").submit();
}*/

function downloadTemplate() {
	$.ajax({
		type : "GET",
		url : "template/attr1",
		cache : false,
		success : function(data) {
			$('#search').hide();
			$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$('#dynContent').show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();
			$("#dynContent").html(data);
			$("#attr2Div").hide();
			$("#header2title").hide();
			$("#header3title").hide();
			$("#at1Disp").load(attr1Displayed());
		}
	});
}

function attr1Displayed() {
	var attr1Value = $("#attr1Disp").attr('name');
	if (attr1Value != "-1") {
		// alert(attr1Value);
		if (attr1Value != undefined) {
			$.ajax({
				type : "GET",
				url : "template/attr2?attr1Value=" + attr1Value,
				cache : false,
				success : function(data) {
					$("#attr2Div").show();
					$("#attr3Div").hide();
					$("#attr2Div").html(data);
					$("#at2Disp").load(attr2Displayed());
				}
			});
		}

	}

	else {
		$("#attr2Div").hide();
	}
}

function attr1Selected() {
	var attr1Value = $("#attr1").val();
	if (attr1Value != "-1") {
		// alert(attr1Value);
		$.ajax({
			type : "GET",
			url : "template/attr2?attr1Value=" + attr1Value,
			cache : false,
			success : function(data) {
				$("#attr2Div").show();
				$("#attr3Div").hide();
				$("#attr2Div").html(data);
				$("#at2Disp").load(attr2Displayed());
			}
		});
	} else {
		$("#attr2Div").hide();
	}
}

function attr2Displayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr2Value = $("#attr2Disp").attr('name');
	if (attr2Value != "-1") {
		// alert(attr2Value);
		if (attr2Value != undefined) {
			$.ajax({
				type : "GET",
				url : "template/attr3?attr2Value=" + attr2Value,
				cache : false,
				success : function(data) {
					$("#attr3Div").show();
					$("#attr4Div").hide();
					$("#attr3Div").html(data);
					$("#at3Disp").load(attr3Displayed());
				}
			});
		}
	} else

	{
		$("#attr3Div").hide();

	}
}

function attr2Selected() {
	var attr2Value = $("#attr2").val();
	if (attr2Value != "-1") {

		$.ajax({
			type : "GET",
			url : "template/attr3?attr2Value=" + attr2Value,
			cache : false,
			success : function(data) {
				$("#attr3Div").show();
				$("#attr4Div").hide();
				$("#attr3Div").html(data);
				$("#at3Disp").load(attr3Displayed());
			}
		});
	} else {
		$("#attr3Div").hide();
	}
}

function attr3Displayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr3Value = $("#attr3Disp").attr('name');
	if (attr3Value != "-1") {

		// alert(attr3Value);
		if (attr3Value != undefined) {
			/*$.ajax({
				type : "GET",
				url : "template/attr4?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					$("#attr4Div").show();
					$("#attr4Div").html(data);
					$("#at4Disp").load(attr4Displayed());
				}
			});*/
			
			$.ajax({
				type : "GET",
				url : "template/getDoctypes?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					$("#attr4Div").show();
					$("#attr4Div").html(data);
					$("#at4Disp").load(doctypeDisplayed());
				}
			});
		}
	} else {
		$("#attr4Div").hide();
	}
}

function attr3Selected() {
	var attr3Value = $("#attr3").val();
	if (attr3Value != "-1") {
		/*$.ajax({
			type : "GET",
			url : "template/attr4?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				$("#attr4Div").show();
				$("#attr4Div").html(data);
				$("#at4Disp").load(attr4Displayed());
				$("#btnDwnldTemp").hide();
			}
		});*/
		
		$.ajax({
			type : "GET",
			url : "template/getDoctypes?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				$("#attr4Div").show();
				$("#attr4Div").html(data);
				$("#at4Disp").load(doctypeDisplayed());
			}
		});
		
	}

	else {
		$("#attr4Div").hide();
	}
}

function attr4Displayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr4Value = $("#attr4Disp").attr('name');
	if (attr4Value != "-1") {

		// alert(attr3Value);
		if (attr4Value != undefined) {
			$.ajax({
				type : "GET",
				url : "template/attr5?attr4Value=" + attr4Value,
				cache : false,
				success : function(data) {
					$("#attr5Div").show();
					$("#attr5Div").html(data);
					$("#at5Disp").load(attr5Displayed());
				}
			});
		}
	} else {
		$("#attr5Div").hide();
	}
}

function attr4Selected() {
	var attr4Value = $("#attr4").val();
	
	if (attr4Value != "-1") {
		$.ajax({
			type : "GET",
			url : "template/attr5?attr4Value=" + attr4Value,
			cache : false,
			success : function(data) {
				$("#attr5Div").show();
				$("#attr5Div").html(data);
				$("#at5Disp").load(attr5Displayed());
				$("#btnDwnldTemp").hide();
			}
		});
	}

	else {
		$("#attr5Div").hide();
	}
}

function attr5Displayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr5Value = $("#attr5Disp").attr('name');
	if (attr5Value != "-1") {
		// alert(attr4Value);
		if (attr5Value != undefined) {
			$.ajax({
				type : "GET",
				url : "template/getDoctypes?attr5Value=" + attr5Value,
				cache : false,
				success : function(data) {
					$("#doctypeDiv").show();
					$("#doctypeDiv").html(data);
					$("#doctypeDisp").load(doctypeDisplayed());
				}
			});
		}
	} else {
		$("#doctypeDiv").hide();
	}
}

function attr5Selected() {
	var attr5Value = $("#attr5").val();
	if (attr5Value != "-1") {
		$.ajax({
			type : "GET",
			url : "template/getDoctypes?attr5Value=" + attr5Value,
			cache : false,
			success : function(data) {
				$("#doctypeDiv").show();
				$("#doctypeDiv").html(data);
				$("#doctypeDisp").load(doctypeDisplayed());
			}
		});
	} else {

		$("#doctypeDiv").hide();
	}
}

function doctypeDisplayed() {
	var doctype = $("#dtypeText").attr('name');

	if (doctype != "-1") {
		// alert(doctype);
		if (doctype != undefined) {
			$.ajax({
				type : "GET",
				url : "template/selectTemplates?doctypeId=" + doctype,
				cache : false,
				success : function(data) {
					$("#btnDwnldTemp").show();
					$("#btnDwnldTemp").html(data);
				}
			});
		}
	} else {
		$("#btnDwnldTemp").hide();

	}
}

function doctypeSelected() {
	var doctype = $("#dtype").val();
	if (doctype != "-1") {
		$.ajax({
			type : "GET",
			url : "template/selectTemplates?doctypeId=" + doctype,
			cache : false,
			success : function(data) {
				$("#btnDwnldTemp").show();
				$("#btnDwnldTemp").html(data);
			}
		});
	} else {
		$("#btnDwnldTemp").show();

	}
}

function templateClicked(url) {
	// alert(url);
	// dialogTemplate("Download Template","1.Click on Open File.2.Enable
	// Editing.3.Save the File to a memorable location.");
	var TemplateDialog = $("<div id='MenuDialog' title='Download Template'>\<p>\<span class='ui-icon ui-icon-check' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>1.Click on Open File.</b><br>\<span class='ui-icon ui-icon-check' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>2.Enable Editing.</b><br>\<span class='ui-icon ui-icon-check' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>3.Save the File to a memorable location.</b>"
			+ "</p>\</div>");
	TemplateDialog.dialog({
		resizable : false,
		width : 350,
		height : 185,
		modal : true,
		closeOnEscape : false,
		buttons : {
			"Ok" : {
				text : 'Ok',
				click : function() {
					$(this).dialog("close");
					$("#templateUrl").val(url);
					refreshTrayForTemplate();
					// $("#downloadTemplateForm").attr("action",
					// "template/downloadTemplate");
					// $("#downloadTemplateForm").submit();
					// refreshTray();
					// loadAllDocuments();
				}
			}
		}
	});
	// refreshTray();
	// loadAllDocuments();
	// $("#templateUrl").val(url);
	// $("#downloadTemplateForm").attr("action", "template/downloadTemplate");
	// $("#downloadTemplateForm").submit();
}

/*
 * * * * * For Quick Upload
 */

function quickUploadProcess() {
	$.ajax({
		type : "GET",
		url : "quickupload/attr1",
		cache : false,
		success : function(data) {
			$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$('#dynContent').show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();

			$("#dynContent").html(data);

			$("#attr2qUplDiv").hide();
			$("#header1title").hide();
			$("#header2title").hide();
			$('#csvDownloadSearch').hide();
			$("#header3title").show();

			$("#at1qUplDisp").load(attr1qUplDisplayed());
		}
	});
}

function attr1qUplDisplayed() {
	var attr1Value = $("#attr1qUplDisp").attr('name');
	// alert(attr1Value);
	if (attr1Value != undefined) {
		$.ajax({
			type : "GET",
			url : "quickupload/attr2?attr1Value=" + attr1Value,
			cache : false,
			success : function(data) {
				$("#attr2qUplDiv").show();
				$("#attr3qUplDiv").hide();
				$("#attr2qUplDiv").html(data);
				$("#at2qUplDisp").load(attr2qUplDisplayed());
			}
		});
	}
}

function attr1qUplSelected() {
	var attr1Value = $("#attr1qUpl").val();

	if (attr1Value != "-1") {
		$.ajax({
			type : "GET",
			url : "quickupload/attr2?attr1Value=" + attr1Value,
			cache : false,
			success : function(data) {
				$("#attr2qUplDiv").show();
				$("#attr3qUplDiv").hide();
				$("#attr2qUplDiv").html(data);
				$("#at2qUplDisp").load(attr2qUplDisplayed());
			}
		});
	}

	else {
		$("#attr2qUplDiv").hide();
	}

	// alert(attr1Value);

}

function attr2qUplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
//alert("inside");
	var attr2Value = $("#attr2qUplDisp").attr('name');
	if (attr2Value != "-1") {
		if (attr2Value != undefined) {
			$.ajax({
				type : "GET",
				url : "quickupload/attr3?attr2Value=" + attr2Value,
				cache : false,
				success : function(data) {
					$("#attr3qUplDiv").show();
					$("#attr4qUplDiv").hide();
					$("#attr3qUplDiv").html(data);
					$("#at3qUplDisp").load(attr3qUplDisplayed());
					// $("#at4qUplDisp").load(attr4qUplDisplayed());
				}
			});
		}

	}

	else {
		$("#attr3qUplDiv").hide();

	}
	// alert(attr2Value);

}
function attr2qUplSelected() {
	var attr2Value = $("#attr2qUpl").val();
	if (attr2Value != "-1") {
		$.ajax({
			type : "GET",
			url : "quickupload/attr3?attr2Value=" + attr2Value,
			cache : false,
			success : function(data) {
				$("#attr3qUplDiv").show();
				$("#attr4qUplDiv").hide();
				$("#attr3qUplDiv").html(data);
				$("#at3qUplDisp").load(attr3qUplDisplayed());
			}
		});
	} else {
		$("#attr3qUplDiv").hide();
	}
}

function attr3qUplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr3Value = $("#attr3qUplDisp").attr('name');
	if (attr3Value != "-1") {
		// alert(attr3Value);
		if (attr3Value != undefined) {
			/*$.ajax({
				type : "GET",
				url : "quickupload/attr4?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					$("#attr4qUplDiv").show();
					$("#attr4qUplDiv").html(data);
					$("#at4qUplDisp").load(attr4qUplDisplayed());
				}
			});*/
			$.ajax({
				type : "GET",
				url : "quickupload/qupload?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					// alert(data);

					$("#attr4qUplDiv").show();
					$("#attr4qUplDiv").html(data);
					$("#dialog-confirm-quick").hide();
					$("#quickUploading").hide();
				}
			});
			
		}
	} else {
		$("#attr4qUplDiv").hide();
	}
}

function attr3qUplSelected() {
	var attr3Value = $("#attr3qUpl").val();
	if (attr3Value != "-1") {
		// alert(attr3Value);
		/*$.ajax({
			type : "GET",
			url : "quickupload/attr4?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				$("#attr4qUplDiv").show();
				$("#attr4qUplDiv").html(data);
				$("#doctypeqUplDiv").hide();

				// For Attr4 Displayed load of doctype
				$("#at4qUplDisp").load(attr4qUplDisplayed());

			}
		});*/
		
		$.ajax({
			type : "GET",
			url : "quickupload/qupload?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				$("#attr4qUplDiv").show();
				$("#attr4qUplDiv").html(data);
				$("#dialog-confirm-quick").hide();
				$("#quickUploading").hide();
			}
		});
		
		
	}

	else {
		$("#attr4qUplDiv").hide();
	}

}

function attr4qUplDisplayed() {
	var attr4Value = $("#attr4qUplDisp").attr('name');

	// alert(attr4Value);
	if (attr4Value != "-1") {
		// alert("attr4"+attr4Value);
		if (attr4Value != undefined) {
			$.ajax({
				type : "GET",
				url : "quickupload/qupload?attr4Value=" + attr4Value,
				cache : false,
				success : function(data) {
					// alert(data);

					$("#doctypeqUplDiv").show();
					$("#doctypeqUplDiv").html(data);
					$("#dialog-confirm-quick").hide();
					$("#quickUploading").hide();
				}
			});
		}
	}

	else {
		$("#doctypeqUplDiv").hide();
	}
}

function attr4qUplSelected() {
	var attr4Value = $("#attr4qUpl").val();
	if (attr4Value != "-1") {
		$.ajax({
			type : "GET",
			url : "quickupload/qupload?attr4Value=" + attr4Value,
			cache : false,
			success : function(data) {
				$("#doctypeqUplDiv").show();
				$("#doctypeqUplDiv").html(data);
				$("#dialog-confirm-quick").hide();
				$("#quickUploading").hide();
			}
		});
	}

	else {
		$("#doctypeqUplDiv").hide();
	}
}



/*
 * * * * *End of Quick Upload
 */

/*
 * * * * * For Upload
 */

function uploadDocument() {
	$.ajax({
		type : "GET",
		url : "docupload/attr1",
		cache : false,
		success : function(data) {
			$('#search').hide();
			$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$('#dynContent').show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();

			$("#dynContent").html(data);
			$("#attr2UplDiv").hide();
			$("#header1title").hide();
			$("#header3title").hide();
			$('#csvDownloadSearch').hide();
			$("#header2title").show();

			$("#at1UplDisp").load(attr1UplDisplayed());
		}
	});
}



function attr1UplDisplayed() {
	var attr1Value = $("#attr1UplDisp").attr('name');
	// alert(attr1Value);
	if (attr1Value != undefined) {
		$.ajax({
			type : "GET",
			url : "docupload/attr2?attr1Value=" + attr1Value,
			cache : false,
			success : function(data) {
				$("#attr2UplDiv").show();
				$("#attr3UplDiv").hide();
				$("#attr2UplDiv").html(data);
				$("#at2UplDisp").load(attr2UplDisplayed());
			}
		});
	}
}

function attr1UplSelected() {
	var attr1Value = $("#attr1Upl").val();
	if (attr1Value != "-1") {
		$.ajax({
			type : "GET",
			url : "docupload/attr2?attr1Value=" + attr1Value,
			cache : false,
			success : function(data) {
				$("#attr2UplDiv").show();
				$("#attr3UplDiv").hide();
				$("#attr2UplDiv").html(data);
				// Added on 14.10.13 For Drill down issue
				$("#at2UplDisp").load(attr2UplDisplayed());
			}
		});
	} else {
		$("#attr2UplDiv").hide();
	}
}

function attr2UplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	// alert("test1234");
	var attr2Value = $("#attr2UplDisp").attr('name');
	if (attr2Value != "-1") {
		if (attr2Value != undefined) {
			$.ajax({
				type : "GET",
				url : "docupload/attr3?attr2Value=" + attr2Value,
				cache : false,
				success : function(data) {

					$("#attr3UplDiv").show();
					$("#attr4UplDiv").hide();
					$("#attr3UplDiv").html(data);
					$("#at3UplDisp").load(attr3UplDisplayed());
					// $("#at4UplDisp").load(attr4UplDisplayed()); changes on
					// 23.01.13
				}
			});
		}

	}

	else {
		$("#attr3UplDiv").hide();

	}
	// alert(attr2Value);

}
function attr2UplSelected() {
	// alert("test1234");
	var attr2Value = $("#attr2Upl").val();
	if (attr2Value != "-1") {
		$.ajax({
			type : "GET",
			url : "docupload/attr3?attr2Value=" + attr2Value,
			cache : false,
			success : function(data) {
				$("#attr3UplDiv").show();
				$("#attr4UplDiv").hide();
				$("#attr3UplDiv").html(data);
				$("#at3UplDisp").load(attr3UplDisplayed());
			}
		});
	}

	else {

		$("#attr3UplDiv").hide();
	}
}

function attr3UplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	// alert(attr3Value);

	var attr3Value = $("#attr3UplDisp").attr('name');
	if (attr3Value != "-1") {

		// alert(attr3Value);
		if (attr3Value != undefined) {
			/*$.ajax({
				type : "GET",
				url : "docupload/attr4?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					$("#attr4UplDiv").show();
					$("#attr4UplDiv").html(data);
					// alert("test1");
					$("#at4UplDisp").load(attr4UplDisplayed());
				}
			});*/
			$.ajax({
				type : "GET",
				url : "docupload/getDoctypes?attr3Value=" + attr3Value,
				cache : false,
				success : function(data) {
					$("#attr4UplDiv").show();
					$("#attr4UplDiv").html(data);
					$("#at4UplDisp").load(doctypeUplDisplayed());
				}
			});
			
			
		}
	}

	else {
		$("#attr4UplDiv").hide();

	}
}

function attr3UplSelected() {
	// alert("test");
	var attr3Value = $("#attr3Upl").val();
	if (attr3Value != "-1") {
		// alert(attr3Value);
		/*$.ajax({
			type : "GET",
			url : "docupload/attr4?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				// alert(data);
				$("#attr4UplDiv").show();
				$("#attr4UplDiv").html(data);
				$("#doctypeUplDiv").hide();

				// For site load issue
				$("#at4UplDisp").load(attr4UplDisplayed());
			}
		});*/
		$.ajax({
			type : "GET",
			url : "docupload/getDoctypes?attr3Value=" + attr3Value,
			cache : false,
			success : function(data) {
				$("#attr4UplDiv").show();
				$("#attr4UplDiv").html(data);
				$("#at4UplDisp").load(doctypeUplDisplayed());
			}
		});
		
	} else {
		$("#attr4UplDiv").hide();
	}
}

function attr4UplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	// alert("inside");
	var attr4Value = $("#attr4UplDisp").attr('name');
	if (attr4Value != "-1") {
		// alert("attr4"+attr4Value);
		if (attr4Value != undefined) {
			$.ajax({
				type : "GET",
				url : "docupload/attr5?attr4Value=" + attr4Value,
				cache : false,
				success : function(data) {
					$("#attr5UplDiv").show();
					$("#attr5UplDiv").html(data);
					// alert("test1");
					$("#at5UplDisp").load(attr5UplDisplayed());
				}
			});
		}
	}

	else {
		$("#doctypeUplDiv").hide();
	}
}

function attr4UplSelected() {
	// alert("data");
	var attr4Value = $("#attr4Upl").val();
	if (attr4Value != "-1") {
		$.ajax({
			type : "GET",
			url : "docupload/attr5?attr4Value=" + attr4Value,
			cache : false,
			success : function(data) {
				$("#attr5UplDiv").show();
				$("#attr5UplDiv").html(data);
				$("#doctypeUplDiv").hide();

				// For site load issue
				$("#at5UplDisp").load(attr5UplDisplayed());
			}
		});
	}

	else {
		$("#doctypeUplDiv").hide();
	}
}


function attr5UplDisplayed() {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	// alert("inside");
	var attr4Value = $("#attr5UplDisp").attr('name');
	if (attr4Value != "-1") {
		// alert("attr4"+attr4Value);
		if (attr4Value != undefined) {
			$.ajax({
				type : "GET",
				url : "docupload/getDoctypes?attr5Value=" + attr4Value,
				cache : false,
				success : function(data) {
					$("#doctypeUplDiv").show();
					$("#doctypeUplDiv").html(data);
					$("#doctypeUplDisp").load(doctypeUplDisplayed());
				}
			});
		}
	}

	else {
		$("#doctypeUplDiv").hide();
	}
}

function attr5UplSelected() {
	// alert("data");
	var attr4Value = $("#attr5Upl").val();
	if (attr4Value != "-1") {
		$.ajax({
			type : "GET",
			url : "docupload/getDoctypes?attr5Value=" + attr4Value,
			cache : false,
			success : function(data) {
				$("#doctypeUplDiv").show();
				$("#doctypeUplDiv").html(data);
				$("#doctypeUplDisp").load(doctypeUplDisplayed());
			}
		});
	}

	else {
		$("#doctypeUplDiv").hide();
	}
}

function doctypeUplDisplayed() {
	var doctype = $("#dtypeUplText").attr('name');
	if (doctype != "-1") {
		// alert("doctype "+doctype);
		if (doctype != undefined) {
			$.ajax({
				type : "GET",
				url : "docupload/selectModels?doctypeId=" + doctype,
				cache : false,
				success : function(data) {
					$("#modelSelectDiv").show();
					$("#modelSelectDiv").html(data);
					$("#modelDispDiv").load(modelNameDisplayed());

				}
			});
		}

	} else {
		$("#modelSelectDiv").hide();
	}

}

function doctypeUplSelected() {

	var doctype = $("#dtypeUpl").val();

	if (doctype != "-1") {
		$.ajax({
			type : "GET",
			url : "docupload/selectModels?doctypeId=" + doctype,
			cache : false,
			success : function(data) {
				$("#modelSelectDiv").show();
				$("#modelSelectDiv").html(data);
				$("#modelDispDiv").load(modelNameDisplayed());

			}
		});
	} else {
		$("#modelSelectDiv").hide();

	}
}

function destroySecurityDialog() {
	$('#dialog-confirm').dialog('destroy').remove();
	$('#securitySettingDialog').dialog('destroy').remove();
}

function modelNameDisplayed() {

	var modelName = $("#modelDisp").val();
	if (modelName != "-1") {
		// alert("modelName dis-> "+modelName);
		if (modelName != undefined) {
			$.ajax({
				type : "GET",
				url : "docupload/editAttr?modelName=" + modelName,
				cache : false,
				success : function(data) {
					destroySecurityDialog();
					$("#uploadDocument").show();
					$("#uploadDocument").html(data);
					$("#dialog-confirm").hide();
					$("#uploading").hide();
					$("#editLinks").hide();
					$("#securitySettingDialog").hide();

				}
			});
		}
	} else

	{
		$("#uploadDocument").hide();

	}
}
function modelNameSelected() {

	var modelName = $("#modelname").val();
	// alert(modelName);
	if (modelName != "-1") {
		// alert("modelName selo-> "+modelName);
		$.ajax({
			type : "GET",
			url : "docupload/editAttr?modelName=" + modelName,
			cache : false,
			success : function(data) {
				destroySecurityDialog();
				$("#uploadDocument").show();
				$("#uploadDocument").html(data);
				$("#dialog-confirm").hide();
				$("#uploading").hide();
				$("#editLinks").hide();
				$("#securitySettingDialog").hide();
			}
		});
	}

	else {
		$("#uploadDocument").hide();

	}
}
/*
 * End for Upload
 */

/*
 * Start Quick Upload
 */

function quickUploadClicked() {
	// alert("In Quick Upload");

	$.ajax({
		type : "GET",
		url : "docupload/docwithoutworkflow",
		cache : false,
		success : function(data) {

			$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$('#dynContent').show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();
			$("#dynContent").html(data);
			$("#header3title").show();
		}
	});

}

// For Naming Formation Start

function getMetaDataForWorkflow(keywords, fileName) {

	var project, discipline,  docType;
	var isVisibleAttr1 = $('#attr1UplDisp').is(':visible');
	if (isVisibleAttr1) {
		// project=$('#attr1UplDisp').val();
		project = $('#attr1UplDisp').attr('title');
	}

	else {
		// project=$('#attr2Upl option:selected').text();
		project = $('#attr1Upl option:selected').attr('title');
		// alert("project:" +project);

	}
	var isVisibleAttr3 = $('#attr3UplDisp').is(':visible');

	if (isVisibleAttr3) {
		// discipline=$('#attr3UplDisp').val();
		discipline = $('#attr3UplDisp').attr('title');
	} else {
		// discipline=$('#attr3Upl option:selected').text();
		discipline = $('#attr3Upl option:selected').attr('title');
	}

	/*var isVisibleAttr4 = $('#attr4UplDisp').is(':visible');
	if (isVisibleAttr4) {
		// site=$('#attr4UplDisp').val();
		site = $('#attr4UplDisp').attr('title');

	} else {
		// site=$('#attr4Upl option:selected').text();
		site = $('#attr4Upl option:selected').attr('title');
	}*/
	var isVisibleAttr5 = $('#dtypeUplText').is(':visible');
	if (isVisibleAttr5) {
		// docType=$('#dtypeUplText').val();
		docType = $('#dtypeUplText').attr('title');
		// alert(docType);
	} else {
		docType = $('#dtypeUpl option:selected').attr('title');
	}
	// $("#documentTypeWof option:selected").attr('title');
	getThreeCharacters(project, docType, keywords,  discipline, fileName,
			1);
	// alert(project.toUpperCase()
	// +"-"+docType+"-"+keywords+"-"+site+"-"+discipline.toUpperCase());
}

function getMetaData(keywords, fileName) {
	var project, discipline, docType;
	// ,category

	var isVisibleAttr1 = $('#attr1qUplDisp').is(':visible');
	if (isVisibleAttr1) {
		// project=$('#attr1qUplDisp').val();
		project = $('#attr1qUplDisp').attr('title');
	}

	else {
		// project=$('#attr2qUpl option:selected').text();
		project = $('#attr1qUpl option:selected').attr('title');
	}
	var isVisibleAttr3 = $('#attr3qUplDisp').is(':visible');

	if (isVisibleAttr3) {
		// discipline=$('#attr3qUplDisp').val();
		discipline = $('#attr3qUplDisp').attr('title');
	} else {
		// discipline=$('#attr3qUpl option:selected').text();
		discipline = $('#attr3qUpl option:selected').attr('title');
	}

	//alert("----->"+discipline);
	/*var isVisibleAttr4 = $('#attr4qUplDisp').is(':visible');
	if (isVisibleAttr4) {
		// site=$('#attr4qUplDisp').val();
		site = $('#attr4qUplDisp').attr('title');
	} else {
		// site=$('#attr4qUpl option:selected').text();
		site = $('#attr4qUpl option:selected').attr('title');
	}*/
	docType = $("#documentTypeWof option:selected").attr('title');
	getThreeCharacters(project, docType, keywords,  discipline, fileName,
			2);
	return 1;

}

function getThreeCharacters(project, docType, keywords,  discipline,
		fileName, value) {
	// alert(project.toUpperCase()
	// +"-"+docType+"-"+keywords+"-"+site+"-"+discipline.toUpperCase());
//	var site1 = site;
	var discipline1 = discipline;

	/*if (site.length >= 3) {

		site1 = site.substring(0, 3);
	}*/
	if (discipline.length >= 3) {

		discipline1 = discipline.substring(0, 3);
	}

	var ext = fileName.split('.').pop();
	getFullName(project, docType, keywords,  discipline1, ext, value);
}

function StringBuilder() {
	var strings = [];

	this.append = function(string) {
		string = verify(string);
		if (string.length > 0)
			strings[strings.length] = string;
	};

	this.appendLine = function(string) {
		string = verify(string);
		if (this.isEmpty()) {
			if (string.length > 0)
				strings[strings.length] = string;
			else
				return;
		} else
			strings[strings.length] = string.length > 0 ? "\r\n" + string
					: "\r\n";
	};

	this.clear = function() {
		strings = [];
	};

	this.isEmpty = function() {
		return strings.length == 0;
	};

	this.toString = function() {
		return strings.join("");
	};

	var verify = function(string) {
		if (!defined(string))
			return "";
		if (getType(string) != getType(new String()))
			return String(string);
		return string;
	};

	var defined = function(el) {
		return el != null && typeof (el) != "undefined";
	};

	var getType = function(instance) {
		if (!defined(instance.constructor))
			throw Error("Unexpected object type");
		var type = String(instance.constructor).match(/function\s+(\w+)/);

		return defined(type) ? type[1] : "undefined";
	};

};

// function restrictedClickedQupl(defaultSg){
//	
// $("input[name='sgrpQupl'][value='"+defaultSg+"']").attr("checked",
// "checked");
// $("#listSecGroupsQupl").show();
// }

function restrictedClicked(defaultSg) {
	// alert("restricted Clicked" + defaultSg);
	// console.log($("input[name='sgrp'][value='"+defaultSg+"']"));
	$("input[name='sgrp'][value='" + defaultSg + "']").attr("checked",
			"checked");
	$("#listSecGroups").show();
	$("#listSecGroupsQupl").show();
	// $("#listSecGroupsQupl").show();
}

// function openClickedQupl(){
//	
// $("input[name='sgrpQupl']").attr('checked', false);
// $("#listSecGroupsQupl").hide();
// }

function openClicked() {

	// alert("in open");
	// console.log("open Clicked");
	// console.log($("input[name='sgrp']").attr('checked'));
	$("input[name='sgrp']").attr('checked', false);
	$("#listSecGroups").hide();
	$("#listSecGroupsQupl").hide();
}

function getFullName(project, docType, keywords,  discipline1, ext, value) {
	var concatChar = "-";
	var sb = new StringBuilder();
	sb.append(project.toUpperCase());
	sb.append(concatChar);
	sb.append(docType.toUpperCase());
	sb.append(concatChar);
	sb.append(keywords.toUpperCase());
	sb.append(concatChar);
	//sb.append(site1.toUpperCase());
	//sb.append(concatChar);
	sb.append(discipline1.toUpperCase());
	sb.append(concatChar);
	sb.append("XX");
	if (value != 2) {
		sb.append(concatChar);
		sb.append("REV1");
	}
	sb.append(".");
	sb.append(ext);

	if (value == 2) {

		var documentTypeWof = $('#documentTypeWof').val();
		// alert(documentTypeWof);
		var NewDialog = $("<div id='MenuDialog' title='Quick Upload'>\<p>\<span class='ui-icon ui-icon-info' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>Please Confirm Filename:<br><b>"
				+ sb.toString()
				+ "</b></p>\</div>");
		NewDialog
				.dialog({
					resizable : false,
					width : 540,
					height : 170,
					modal : true,
					buttons : {
						"Yes" : {
							text : 'Yes',
							click : function() {

								$(this).dialog("close");
								$('#securitySettingDialogQupl').dialog(
										'destroy').remove();
								// $('#secDialog').html('');

								$
										.ajax({
											type : "GET",
											url : "quickupload/getSecurityGroups?documentTypeWof="
													+ documentTypeWof,
											cache : false,
											success : function(data) {
												// alert(data);

												$('#secDialog').html(data);
												$("#securitySettingDialogQupl")
														.dialog(
																{
																	resizable : false,
																	width : 400,
																	height : 300,
																	modal : true,
																	buttons : {
																		"Set Security" : {
																			text : 'Set Security',
																			click : function() {
																				var sectype = $(
																						"input:radio[name='secTypeQupl']:checked")
																						.val();
																				
																				
																				
																				if (sectype == 'open') {
																					var sgo=$("#secOpen").val();
																					// alert(sectype);
																					// $("#listSecGroupsQupl").hide();

																					$(
																							"#securitySettingQupl")
																							.val(
																									sgo);
																					$(
																							"#quickUploading")
																							.dialog(
																									{
																										height : 125,
																										width : 254,
																										modal : true,
																										closeOnEscape : false,
																										resizable : false,
																										draggable : false
																									});

																					$(
																							"#quickUpload")
																							.attr(
																									"action",
																									"quickupload/upload");
																					$(
																							"#quickUpload")
																							.submit();
																					$(
																							this)
																							.dialog(
																									"close");
																					$(
																							this)
																							.dialog(
																									'destroy')
																							.remove();

																				} else if (sectype == 'restricted') {
																					// alert(sectype);

																					var sg = $(
																							'input[name=sgrpQupl]:checked')
																							.val();
																					$(
																							"#securitySettingQupl")
																							.val(
																									sg);

																					$(
																							"#quickUploading")
																							.dialog(
																									{
																										height : 125,
																										width : 254,
																										modal : true,
																										closeOnEscape : false,
																										resizable : false,
																										draggable : false
																									});
																					$(
																							"#quickUpload")
																							.attr(
																									"action",
																									"quickupload/upload");
																					$(
																							"#quickUpload")
																							.submit();
																					$(
																							this)
																							.dialog(
																									"close");
																					$(
																							this)
																							.dialog(
																									'destroy')
																							.remove();
																				}
																			}
																		}
																	}
																});
											}
										});
							}
						},
						"No" : {
							text : 'No',
							click : function() {

								$(this).dialog("close");
								return false;
							}
						}
					}
				});
	} else {

		var NewDialog = $("<div id='MenuDialog' title='Controlled Upload'>\<p>\<span class='ui-icon ui-icon-info' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>Please Confirm Filename:<br><b>"
				+ sb.toString()
				+ "</b></p>\</div>");
		NewDialog
				.dialog({
					resizable : false,
					width : 540,
					height : 170,
					modal : true,
					buttons : {
						"Yes" : {
							text : 'Yes',
							click : function() {
								$(this).dialog("close");
								/*
								 * When Clicking Securitysettings go controller
								 * get the SG for the 4 attrs and Doctype Paint
								 * the dialog in success accordingly
								 */
								// console.log("yES cLICKECD");
								$(this).dialog("close");
								// $("#securitySettingDialog").show();
								$("#securitySettingDialog")
										.dialog(
												{
													resizable : false,
													width : 400,
													height : 300,
													modal : true,
													/*
													 * close : function(ev, ui) {
													 * $(this).close(); },
													 */
													buttons : {
														"Set Security" : {
															text : 'Set Security',
															click : function() {
																var sectype = $(
																		"input:radio[name='secType']:checked")
																		.val();
																// alert(sectype);
																if (sectype == 'open') {
																	// console.log("Entering
																	// in
																	// open");
																	var sgo=$("#secOpen").val();
																	/*$(
																			"#securitySetting")
																			.val(
																					"N");*/
																	$(
																	"#securitySetting")
																	.val(
																			sgo);
																	$(
																			"#uploading")
																			.dialog(
																					{
																						height : 125,
																						width : 254,
																						modal : true,
																						closeOnEscape : false,
																						resizable : false,
																						draggable : false
																					});
																	// $('a.ui-dialog-titlebar-close').remove();
																	// console.log("In
																	// open
																	// ->-"+$("#securitySetting").val());
																	$(
																			"#submitAttrSaveForm")
																			.attr(
																					"action",
																					"docupload/saveCaseUploadDoc");
																	$(
																			"#submitAttrSaveForm")
																			.submit();
																	$(this)
																			.dialog(
																					"close");
																	$(this)
																			.dialog(
																					'destroy')
																			.remove();

																} else if (sectype == 'restricted') {
																	// console.log("Entering
																	// in
																	// Restricted");
																	// console.log($("#securitySetting"));
																	var sg = $(
																			'input[name=sgrp]:checked')
																			.val();
																	 //alert(sg);
																	$(
																			"#securitySetting")
																			.val(
																					sg);
																	$(
																			"#uploading")
																			.dialog(
																					{
																						height : 125,
																						width : 254,
																						modal : true,
																						closeOnEscape : false,
																						resizable : false,
																						draggable : false
																					});
																	// $('a.ui-dialog-titlebar-close'
																	// ).remove();
																	// console.log("In
																	// Restricted->
																	// "+$("#securitySetting").val());
																	$(
																			"#submitAttrSaveForm")
																			.attr(
																					"action",
																					"docupload/saveCaseUploadDoc");
																	$(
																			"#submitAttrSaveForm")
																			.submit();
																	$(this)
																			.dialog(
																					"close");
																	$(this)
																			.dialog(
																					'destroy')
																			.remove();
																}
															}
														}
													}
												});

							}
						},
						"No" : {
							text : 'No',
							click : function() {
								$("#securitySetting").val("N");
								$(this).dialog("close");
								return false;
							}
						}

					}
				});
	}
}

function uploadCorrectFile(title, valueTxt, keywords, filename, checkValue) {
	var UploadCorrectFileDialog = $("<div id='MenuDialog' title='" + title
			+ "'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> " + "</span>"
			+ valueTxt + "<br></p>\</div>");
	UploadCorrectFileDialog.dialog({
		resizable : false,
		width : 300,
		height : 150,
		modal : true,
		closeOnEscape : false,
		buttons : {
			"Ok" : {
				text : 'Ok',
				click : function() {
					$(this).dialog("close");
					if (checkValue == 1) {
						// alert("In 1");
						getMetaDataForWorkflow(keywords, filename);
					} else {
						// alert("In 2");
						getMetaData(keywords, filename);
					}

				}
			}
		}
	});
	// removeCloseButton();
}

// End Naming Formation
function quickUpload() {
	var value = $('#quickUpl').val();
	var keywords = $('#keywordsWof').val();
	if ($.trim(keywords) == "")

	{
		dialogTemplate("Quick Upload", "Please Enter Keywords Only");
		$('#keywordsWof').focus();
		return false;
	}

	if ($.trim($('#keywordsWof').val()).length > 100) {
		dialogTemplate("Quick Upload",
				"Please enter keywords less than 100 characters");
		$('#keywordsWof').focus();
		return false;
	}

	// patt=/^[a-zA-Z0-9\s\[\]]*$/g;

	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt.test(keywords) == true) {

		dialogTemplate("Quick Upload",
				"Special characters not allowed in title/keywords");
		return false;
	}
	if (value == "")

	{
		dialogTemplate("Quick Upload", "Please select a file to upload");
		// alert("Please select a file to upload");
		$('#quickUpl').focus();
		return false;
	}

	uploadCorrectFile("Quick Upload",
			"Please remember to upload the correct file", keywords, value, 2);
	// var s=getMetaData(keywords,value);

}

/*
 * End Quick Upload
 */

function onPickClicked(path, docName, caseId, stepId,adminMeta) {
	// alert(docName+" - "+stepId);

	docName = regexSplit(docName);
	// alert("Doc" +docName);

	$.ajax({
		type : "GET",
		url : "dash/pick?path=" + path + "&docName=" + docName + "&caseId="
				+ caseId + "&stepId=" + stepId + "&adminMeta="+adminMeta,
		cache : false,
		success : function(data) {
			$("#dynContentMetadata").html(data);
			$("#dynamicContent").html(data);
			$("#dynContentMetadata").show();
			$('#csvDownloadSearch').hide();
			$('#review').hide();
			$('#docReview').hide();

			$('#postConfirm-dialog').hide();
			$('#documentProperties').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true,
				/*
				 * "sDom" : 'T<"clear">lfrtip', "oTableTools" : { "aButtons" : [ {
				 * "sExtends" : "print", "sButtonText" : "", "sMessage" :
				 * "Generated by Document Portal" } ] },
				 */
				"sScrollX" : "100%",
				"aaSorting" : [],
				"bDestroy" : true,
				"aoColumns" : [ {
					"bSortable" : false
				}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ]
			});

			// For Claim

			enableButton();

			// $("#properties").hide();
			$("#checkInProgress").hide();

			$("#fileCheckin").hide();
			// $('#docProperties').hide();
			$('#editAttrDiv').hide();
			$('#uploadNew').hide();
			$('#actionReason').hide();

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#downloadedList').hide();
			$('#actionedList').hide();
			$('#header2').hide(); // For Hide Action Trays
			$('#dynContent').hide();

		},
		error : function(data) {
			alert("Error: " + data);
		}
	});
}

function loadProgress() {
	//alert("comes in load progress");
	var ProgressDialog = $("<div id='ProgressBarDialog' title='Please wait...' align='center' style='padding-top: 6px'><img align='middle' src='resources/images/progress_bar.gif' /></div>");
	ProgressDialog.dialog({
		height : 125,
		width : 254,
		modal : true,
		closeOnEscape : false,
		resizable : false,
		draggable : false
	});
}

function removeProgress() {
	//alert("comes in");
	$('#ProgressBarDialog').dialog('destroy').remove();
}

// Cloning : Document Revisioning
function submitform(modelName, date, docId) {
	alert(modelName+" "+date+" "+docId);
	loadProgress();
	$.ajax({
		type : "GET",
		url : "docupload/clonedoc?modelName=" + modelName + "&date=" + date
				+ "&docId=" + docId,
		cache : false,
		success : function(data) {
			tb_remove();

			$("#testdiv").html(data);

			// homeClicked();

			// loadAllDocuments();

		},
		error : function(data) {
			// alert('failure');
			dialogTemplate("Revision Document", "Failure to revise document");
		}
	});

}

function submitformRevision(userFormId,revisionDate) {
	//loadProgress();
	var date=trim(revisionDate);
	$.ajax({
		type : "GET",
		url : "forms/revisonForm?userFormId=" + userFormId + "&formTargetDate=" + date,
		cache : false,
		success : function(data) {
			tb_remove();

			$("#testdiv").html(data);

			// homeClicked();

			// loadAllDocuments();

		},
		error : function(data) {
			// alert('failure');
			dialogTemplate("Revision Document", "Failure to revise document");
		}
	});

}

function revisionbtnclicked(docId) {

	var url = "docupload/revision?docId=" + docId
			+ "&TB_iframe=true&height=195&width=380";

	tb_show('Revision Document', url, null);

}

function cloneBtnClicked(userFormsId){
//alert("UserFormsId:"+userFormsId);
	$.ajax({
		type : "GET",
		url : "forms/clone?userFormId=" + userFormsId,
		cache : false,
		success : function(data) {
       //  alert("S");
         $("#testdiv").html(data);
		},
		error : function(data) {
         //  alert("F");
		}
	});
}

/*function popupFormRevisionSelected(userFormsId) {
	//alert("UserFormsId:"+userFormsId);
	var url = "forms/revFormPop?userFormId=" + userFormsId
	+ "&TB_iframe=true&height=195&width=380";
	//alert(url);
	tb_show('Revision Form', url, null);
}*/

/*function revisionFormBtnClicked(userFormsId){
	alert("UserFormsId:"+userFormsId);
	
		$.ajax({
			type : "GET",
			url : "forms/revisionForm?userFormId=" + userFormsId,
			cache : false,
			success : function(data) {
	       //  alert("S");
	         $("#testdiv").html(data);
			},
			error : function(data) {
	         //  alert("F");
			}
		});
	}*/

/*function modelNameRevisionSelected(modelName) {
	$.ajax({
		type : "GET",
		url : "docupload/editattrcloned?modelName=" + modelName,
		cache : false,
		success : function(data) {

			$("#modelselrevision").html(data);

		}
	});
}*/

function refreshToolbar() {
	$("#comments").val('');
	$("#dynContentMetadata").hide();
	$('#downloadTemplateImg').show();
	$('#quickUploadImg').show();
	$('#uploadDocumentImg').show();
	$('#formsImg').show();
	$("#dynContent").show();
	$('#myIntrays').show();
	$('#header2').show(); //Visible Action Trays
	$('#leftlinks').show();
	$('#downloadedList').show();
	$('#actionedList').show();
	$('#search').hide();
	refreshTray();
	refreshLastDownloadedDocument();
	refreshLastActionedDocument();
	loadAllDocuments();

}

function refreshToolbarAdmin() {
	showAllWipDocs();
}

// Abandon Documents

function abandonClicked(stepId,adminMeta) {
	//alert(adminMeta);
	if(adminMeta == "false"){
	var NewDialog = $("<div id='MenuDialog' title='Abandon Document'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to Abandon/Cancel this document/form?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 150,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "dash/abandon?stepId=" + stepId,
						cache : false,
						success : function(data) {

							refreshToolbar();
							// console.log('Abandoned');
							// alert(data);
							// $("#dynContentMetadata").hide();
							// $('#downloadTemplateImg').show();
							// $('#quickUploadImg').show();
							// $('#uploadDocumentImg').show();
							//							
							// $("#dynContent").show();
							// $('#myIntrays').show();
							// $('#leftlinks').show();
							// refreshTray();
							// loadAllDocuments();
						},
						error : function() {
							// console.log('Failed');
							dialogTemplate("Abandon Document",
									"Failure to abandon document");
						}
					});
				}
			},
			"No" : {
				text : 'No',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
	}else if(adminMeta == "true"){
		
		//aaalert("false");
		var NewDialog = $("<div id='MenuDialog' title='Abandon Document'>\<p>\<span class='ui-icon ui-icon-info' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span><b>Are you sure you want to Abandon/Cancel this document/form?</b></p>\</div>");
		NewDialog.dialog({
			resizable : false,
			width : 370,
			height : 150,
			modal : true,
			buttons : {
				"Yes" : {
					text : 'Yes',
					click : function() {
						$(this).dialog("close");
						$.ajax({
							type : "GET",
							url : "dash/abandon?stepId=" + stepId,
							cache : false,
							success : function(data) {

								refreshToolbarAdmin();
								// console.log('Abandoned');
								// alert(data);
								// $("#dynContentMetadata").hide();
								// $('#downloadTemplateImg').show();
								// $('#quickUploadImg').show();
								// $('#uploadDocumentImg').show();
								//							
								// $("#dynContent").show();
								// $('#myIntrays').show();
								// $('#leftlinks').show();
								// refreshTray();
								// loadAllDocuments();
							},
							error : function() {
								// console.log('Failed');
								dialogTemplate("Abandon Document",
										"Failure to abandon document");
							}
						});
					}
				},
				"No" : {
					text : 'No',
					click : function() {
						$(this).dialog("close");
						return false;
					}
				}
			}
		});
	}
}

function cancelcheckout(docId, path, stepId, docName) {

	var NewDialog = $("<div id='MenuDialog' title='"
			+ docName
			+ "'>\<p>\<span class='ui-icon ui-icon-alert'"
			+ " style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span>"
			+ "<b>Are you sure you want to undo check out document?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 500,
		height : 170,
		modal : true,
		position : [ "center", 280 ],
		buttons : {
			"Yes" : {
				text : 'Yes',
				click : function() {
					$(this).dialog("close");
					docName = regexSplit(docName);

					// alert(docName);

					var url = "docs/cancelcheckout" + "?documentId=" + docId
							+ "&path=" + path + "&stepId=" + stepId
							+ "&documentName=" + docName;
					$.ajax({
						type : "GET",
						url : url,
						cache : false,
						success : function(data) {
							$("#checkinDiv").hide();
							$("#checkoutDiv").show();
						},
						error : function(data) {
							dialogTemplate("Undo Check out",
									"Failure to undo check out document");
						}
					});
				}
			},

			"No" : {

				text : 'No',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

}

// For Printing Review Comments,etc

// function printClicked(documentId){
function printClicked(caseId) {
	var url = "dash/print?caseId=" + caseId;
	var newwindow = window
			.open(
					url,
					'Print',
					'height=600,width=800,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');

	if (window.focus) {
		newwindow.focus();
	}
	return false;
}

function printFormClicked(userFormId) {
	
	var url = "printFormTable?userFormId="+ userFormId;
	
	var newwindow = window
			.open(
					url,
					'Print',
					'height=600,width=800,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');

	if (window.focus) {
		newwindow.focus();
		
	}
	return false;
}

//For Claim Disable Button

function disableButton(){
	 $("#goAction").attr("disabled", true); 
     $("#btnDownload").attr("disabled", true); 
     $("#btnVersion").attr("disabled", true);
     $("#saveComment").attr("disabled", true); 
     $("#btnhistory").attr("disabled", true); 
     $("#btnComment").attr("disabled", true); 
     $("#btnProperties").attr("disabled", true); 
	 $("#btnForm").attr("disabled", true);
     $("#updateProperties").attr("disabled", true); 
     $("#actions").attr("disabled", true); 
     
     $("#updateRefLinks").attr("disabled",true);
     $("#abandon").attr("disabled",true);
     
     //For Check In Check Out Visibility
	 var isChkout = $("#isCheckedout").val();
	 if (isChkout == "true") {
			$("#checkoutDiv").hide();
			$("#checkinDiv").attr("disabled",true);
			$("#checkinDiv").css("opacity",".4");
	 }
     else {
			$("#checkinDiv").hide();
			$("#checkoutDiv").attr("disabled",true);
			$("#checkoutDiv").css("opacity",".4");
			
		}
	 
     $("#goAction").css("opacity", ".4"); 
     $("#btnDownload").css("opacity", ".4");
     $("#btnVersion").css("opacity", ".4");
     $("#saveComment").css("opacity", ".4"); 
     $("#btnhistory").css("opacity", ".4"); 
     $("#btnComment").css("opacity", ".4"); 
     $("#updateProperties").css("opacity", ".4"); 
     $("#btnProperties").css("opacity", ".4");   
	 $("#btnForm").css("opacity", ".4");
     $("#actions").css("opacity", ".4"); 
     $("#updateRefLinks").css("opacity", ".4"); 
     $("#abandon").css("opacity",".4");
}

// For Claim Enable Button

function enableButton() {

	$("#btnDownload").removeAttr("disabled", "disabled");
	$("#btnVersion").removeAttr("disabled", "disabled");
	$("#goAction").removeAttr("disabled", "disabled");
	$("#saveComment").removeAttr("disabled", "disabled");
	$("#btnhistory").removeAttr("disabled", "disabled");
	$("#btnComment").removeAttr("disabled", "disabled");
	$("#updateProperties").removeAttr("disabled", "disabled");
	$("#btnForm").removeAttr("disabled", "disabled");
	$("#btnProperties").removeAttr("disabled", "disabled");
	$("#actions").removeAttr("disabled", "disabled");
	  //Added by Karthik on 17.12.13
	   $("#updateRefLinks").removeAttr("disabled","disabled");
	   $("#abandon").removeAttr("disabled","disabled");

	    $("#goAction").css("opacity", ""); 
	    $("#btnDownload").css("opacity", "");
	    $("#btnVersion").css("opacity", "");
	    $("#saveComment").css("opacity", ""); 
	    $("#btnhistory").css("opacity", ""); 
	    $("#btnComment").css("opacity", ""); 
	    $("#updateProperties").css("opacity", ""); 
	    $("#btnProperties").css("opacity", "");   
		$("#btnForm").css("opacity", "");
	    $("#actions").css("opacity", ""); 
	    $("#updateRefLinks").css("opacity", ""); 
	    $("#abandon").css("opacity","");
}

// Change Paswword
function changePasswordClicked() {
	$("#changePassword").attr("action", "changepwd");
	$("#changePassword").submit();
}


//Help Icon Fucntionality
function helpIconClicked(){
		$.ajax({
			type : "GET",
			url : "dash/getHelpIconContent",
			cache : false,
			success : function(data) {
				$("#wrapper").html(data);
			}
		});		
}


// JS changed done for forms document upload


function quickUploadCompletedForm(result, name,id,documentName) {
	$("#quickUploading").remove();
	if (result == 1) {
		var QuickUploadDialog = $("<div id='MenuDialog' title='Quick Upload Result'>\<p>\<span class='ui-icon ui-icon-check' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>File uploaded successfully with name: <b>"
				+ name
				+ "</b></p>\</div>");
		QuickUploadDialog.dialog({
			resizable : false,
			width : 540,
			height : 170,
			modal : true,
			closeOnEscape : false,
			buttons : {
				"Ok" : {
					text : 'Ok',
					click : function() {
						$(this).dialog("close");
						$.ajax({
							type : "GET",
							url : "removeTempDoc?docName=" + documentName,
							cache : false,
							success : function(data) {
								$("#quickUploadResult").html("Success");
								//alert(name);
								window.opener.setValue(id,name);
								window.close();
							}
						});
						

					}
				}
			}
		});
		removeCloseButton();
	} else {
		// alert("Failed to upload a file");
		dialogTemplate("Quick Upload",
				"This file failed to upload due to a large file size.");
		// $("#quickUploadResult").html("Failed to upload a file");
	}
}


function quickUploadFormProcess(id,docName) {
	$.ajax({
		type : "GET",
		url : "attr1?itemId="+id+"&docName="+docName,
		cache : false,
		success : function(data) {
			/*$('#dynContentMetadata').hide();
			$('#csvDownloadSearch').hide();
			$('#dynContent').show();
			$('#myIntrays').show();
			$('#downloadedList').show();
			$('#actionedList').show();
			$('#header2').show(); //Visible Action Trays
			$('#leftlinks').show();*/

			$("#dynContentForm").html(data);

			$("#attr2qUplDiv").hide();
			$("#header1title").hide();
			$("#header2title").hide();
			$('#csvDownloadSearch').hide();
			$("#header3title").show();

			$("#at1qUplDisp").load(attr1qUplFormDisplayed(id,docName));
		}
	});
}

function attr1qUplFormDisplayed(id,docName) {
	var attr1Value = $("#attr1qUplDisp").attr('name');
	//alert(attr1Value);
	if (attr1Value != undefined) {
		$.ajax({
			type : "GET",
			url : "attr2?attr1Value=" + attr1Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#attr2qUplDiv").show();
				$("#attr3qUplDiv").hide();
				$("#attr2qUplDiv").html(data);
				$("#at2qUplDisp").load(attr2qUplFormDisplayed(id,docName));
			}
		});
	}
}

function attr1qUplFormSelected(id,docName) {
	var attr1Value = $("#attr1qUpl").val();
//alert(id);
	if (attr1Value != "-1") {
		$.ajax({
			type : "GET",
			url : "attr2?attr1Value=" + attr1Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#attr2qUplDiv").show();
				$("#attr3qUplDiv").hide();
				$("#attr2qUplDiv").html(data);
				$("#at2qUplDisp").load(attr2qUplFormDisplayed(id,docName));
			}
		});
	}

	else {
		$("#attr2qUplDiv").hide();
	}

	// alert(attr1Value);

}

function attr2qUplFormDisplayed(id,docName) {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
//alert("inside attr2form");
	var attr2Value = $("#attr2qUplDisp").attr('name');
	if (attr2Value != "-1") {
		if (attr2Value != undefined) {
			$.ajax({
				type : "GET",
				url : "attr3?attr2Value=" + attr2Value+"&itemId="+id+"&docName="+docName,
				cache : false,
				success : function(data) {
					$("#attr3qUplDiv").show();
					$("#attr4qUplDiv").hide();
					$("#attr3qUplDiv").html(data);
					$("#at3qUplDisp").load(attr3qUplFormDisplayed(id,docName));
					// $("#at4qUplDisp").load(attr4qUplFormDisplayed());
				}
			});
		}

	}

	else {
		$("#attr3qUplDiv").hide();

	}
	// alert(attr2Value);

}
function attr2qUplFormSelected(id,docName) {
	var attr2Value = $("#attr2qUpl").val();
	if (attr2Value != "-1") {
		$.ajax({
			type : "GET",
			url : "attr3?attr2Value=" + attr2Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#attr3qUplDiv").show();
				$("#attr4qUplDiv").hide();
				$("#attr3qUplDiv").html(data);
				$("#at3qUplDisp").load(attr3qUplFormDisplayed(id,docName));
			}
		});
	} else {
		$("#attr3qUplDiv").hide();
	}
}

function attr3qUplFormDisplayed(id,docName) {
	// var attr4Value = $("#attr4Disp").find(":selected").val();
	var attr3Value = $("#attr3qUplDisp").attr('name');
	if (attr3Value != "-1") {
		//alert(attr3Value);
		if (attr3Value != undefined) {
			$.ajax({
				type : "GET",
				url : "qupload?attr3Value=" + attr3Value+"&itemId="+id+"&docName="+docName,
				cache : false,
				success : function(data) {
					$("#attr4qUplDiv").show();
					$("#attr4qUplDiv").html(data);
					//$("#at4qUplDisp").load(attr4qUplFormDisplayed(id,docName));
				}
			});
		}
	} else {
		$("#attr4qUplDiv").hide();
	}
}

function attr3qUplFormSelected(id,docName) {
	var attr3Value = $("#attr3qUpl").val();
	if (attr3Value != "-1") {
		// alert(attr3Value);
		$.ajax({
			type : "GET",
			url : "qupload?attr3Value=" + attr3Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#attr4qUplDiv").show();
				$("#attr4qUplDiv").html(data);
				$("#doctypeqUplDiv").hide();

				// For Attr4 Displayed load of doctype
				//$("#at4qUplDisp").load(attr4qUplFormDisplayed(id,docName));

			}
		});
	}

	else {
		$("#attr4qUplDiv").hide();
	}

}

function attr4qUplFormDisplayed(id,docName) {
	var attr4Value = $("#attr4qUplDisp").attr('name');
	if (attr4Value != "-1") {
		//alert(attr3Value);
		if (attr4Value != undefined) {
			$.ajax({
				type : "GET",
				url : "attr5?attr4Value=" + attr4Value+"&itemId="+id+"&docName="+docName,
				cache : false,
				success : function(data) {
					$("#attr5qUplDiv").show();
					$("#attr5qUplDiv").html(data);
					$("#at5qUplDisp").load(attr5qUplFormDisplayed(id,docName));
				}
			});
		}
	} else {
		$("#attr4qUplDiv").hide();
	}
}

function attr4qUplFormSelected(id,docName) {
	var attr4Value = $("#attr4qUpl").val();
	if (attr4Value != "-1") {
		// alert(attr3Value);
		$.ajax({
			type : "GET",
			url : "attr5?attr4Value=" + attr4Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#attr5qUplDiv").show();
				$("#attr5qUplDiv").html(data);
				$("#doctypeqUplDiv").hide();

				// For Attr4 Displayed load of doctype
				$("#at5qUplDisp").load(attr5qUplFormDisplayed(id,docName));

			}
		});
	}

	else {
		$("#attr5qUplDiv").hide();
	}
}


function attr5qUplFormDisplayed(id,docName) {
	var attr5Value = $("#attr5qUplDisp").attr('name');

	//alert(attr5Value);
	if (attr5Value != "-1") {
		// alert("attr4"+attr4Value);
		if (attr5Value != undefined) {
			$.ajax({
				type : "GET",
				url : "qupload?attr5Value=" + attr5Value+"&itemId="+id+"&docName="+docName,
				cache : false,
				success : function(data) {
					// alert(data);

					$("#doctypeqUplDiv").show();
					$("#doctypeqUplDiv").html(data);
					$("#dialog-confirm-quick").hide();
					$("#quickUploading").hide();
				}
			});
		}
	}

	else {
		$("#doctypeqUplDiv").hide();
	}
}

function attr5qUplFormSelected(id,docName) {
	var attr5Value = $("#attr5qUpl").val();
	//alert("quickupload/qupload?attr4Value=" + attr5Value);
	if (attr5Value != "-1") {
		$.ajax({
			type : "GET",
			url : "qupload?attr5Value=" + attr5Value+"&itemId="+id+"&docName="+docName,
			cache : false,
			success : function(data) {
				$("#doctypeqUplDiv").show();
				$("#doctypeqUplDiv").html(data);
				$("#dialog-confirm-quick").hide();
				$("#quickUploading").hide();
			}
		});
	}

	else {
		$("#doctypeqUplDiv").hide();
	}
}

function getFullNameForm(project, docType, keywords, site1, discipline1, ext, value,id,docName) {
	//alert(id);
	var concatChar = "-";
	var sb = new StringBuilder();
	sb.append(project.toUpperCase());
	sb.append(concatChar);
	sb.append(docType.toUpperCase());
	sb.append(concatChar);
	sb.append(keywords.toUpperCase());
	sb.append(concatChar);
	/*sb.append(site1.toUpperCase());
	sb.append(concatChar);*/
	sb.append(discipline1.toUpperCase());
	sb.append(concatChar);
	sb.append("XX");
	if (value != 2) {
		sb.append(concatChar);
		sb.append("REV1");
	}
	sb.append(".");
	sb.append(ext);

	if (value == 2) {

		var documentTypeWof = $('#documentTypeWof').val();
		// alert(documentTypeWof);
		var NewDialog = $("<div id='MenuDialog' title='Quick Upload'>\<p>\<span class='ui-icon ui-icon-info' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>Please Confirm Filename:<br><b>"
				+ sb.toString()
				+ "</b></p>\</div>");
		NewDialog
				.dialog({
					resizable : false,
					width : 540,
					height : 170,
					modal : true,
					buttons : {
						"Yes" : {
							text : 'Yes',
							click : function() {

								$(this).dialog("close");
								$('#securitySettingDialogQupl').dialog(
										'destroy').remove();
								// $('#secDialog').html('');

								$
										.ajax({
											type : "GET",
											url : "getSecurityGroups?documentTypeWof="
													+ documentTypeWof+"&itemId="+id+"&docName="+docName,
											cache : false,
											success : function(data) {
												// alert(data);

												$('#secDialog').html(data);
												$("#securitySettingDialogQupl")
														.dialog(
																{
																	resizable : false,
																	width : 400,
																	height : 300,
																	modal : true,
																	buttons : {
																		"Set Security" : {
																			text : 'Set Security',
																			click : function() {
																				var sectype = $(
																						"input:radio[name='secTypeQupl']:checked")
																						.val();
																				if (sectype == 'open') {

																					// alert(sectype);
																					// $("#listSecGroupsQupl").hide();
																					var sgo=$("#secOpen").val();
																					//alert(sgo);
																					/*$(
																							"#securitySettingQupl")
																							.val(
																									"N");*/
																					$(
																					"#securitySettingQupl")
																					.val(
																							sgo);
																					$(
																							"#quickUploading")
																							.dialog(
																									{
																										height : 125,
																										width : 254,
																										modal : true,
																										closeOnEscape : false,
																										resizable : false,
																										draggable : false
																									});

																					$(
																							"#quickUpload")
																							.attr(
																									"action",
																									"upload");
																					$(
																							"#quickUpload")
																							.submit();
																					$(
																							this)
																							.dialog(
																									"close");
																					$(
																							this)
																							.dialog(
																									'destroy')
																							.remove();

																				} else if (sectype == 'restricted') {
																					// alert(sectype);

																					var sg = $(
																							'input[name=sgrpQupl]:checked')
																							.val();
																					$(
																							"#securitySettingQupl")
																							.val(
																									sg);

																					$(
																							"#quickUploading")
																							.dialog(
																									{
																										height : 125,
																										width : 254,
																										modal : true,
																										closeOnEscape : false,
																										resizable : false,
																										draggable : false
																									});
																					$(
																							"#quickUpload")
																							.attr(
																									"action",
																									"upload");
																					$(
																							"#quickUpload")
																							.submit();
																					$(
																							this)
																							.dialog(
																									"close");
																					$(
																							this)
																							.dialog(
																									'destroy')
																							.remove();
																				}
																			}
																		}
																	}
																});
											}
										});
							}
						},
						"No" : {
							text : 'No',
							click : function() {

								$(this).dialog("close");
								return false;
							}
						}
					}
				});
	} 
}


function uploadCorrectFileForm(title, valueTxt, keywords, filename, checkValue,id,docName) {
	var UploadCorrectFileDialog = $("<div id='MenuDialog' title='" + title
			+ "'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> " + "</span>"
			+ valueTxt + "<br></p>\</div>");
	UploadCorrectFileDialog.dialog({
		resizable : false,
		width : 300,
		height : 150,
		modal : true,
		closeOnEscape : false,
		buttons : {
			"Ok" : {
				text : 'Ok',
				click : function() {
					$(this).dialog("close");
					if (checkValue == 1) {
						// alert("In 1");
						getMetaDataForWorkflowForm(keywords, filename);
					} else {
						// alert("In 2");
						getMetaDataForm(keywords, filename,id,docName);
					}

				}
			}
		}
	});
	// removeCloseButton();
}


function quickUploadForm(id,docName) {
	var value = $('#quickUpl').val();
	var keywords = $('#keywordsWof').val();
	if ($.trim(keywords) == "")

	{
		dialogTemplate("Quick Upload", "Please Enter Keywords Only");
		$('#keywordsWof').focus();
		return false;
	}

	if ($.trim($('#keywordsWof').val()).length > 100) {
		dialogTemplate("Quick Upload",
				"Please enter keywords less than 100 characters");
		$('#keywordsWof').focus();
		return false;
	}

	// patt=/^[a-zA-Z0-9\s\[\]]*$/g;

	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt.test(keywords) == true) {

		dialogTemplate("Quick Upload",
				"Special characters not allowed in title/keywords");
		return false;
	}
	if (value == "")

	{
		dialogTemplate("Quick Upload", "Please select a file to upload");
		// alert("Please select a file to upload");
		$('#quickUpl').focus();
		return false;
	}

	uploadCorrectFileForm("Quick Upload",
			"Please remember to upload the correct file", keywords, value, 2,id,docName);
	// var s=getMetaData(keywords,value);

}

function getMetaDataForm(keywords, fileName,id,docName) {
	var project, discipline, site, docType;
	// ,category

	var isVisibleAttr1 = $('#attr1qUplDisp').is(':visible');
	if (isVisibleAttr1) {
		// project=$('#attr1qUplDisp').val();
		project = $('#attr1qUplDisp').attr('title');
	}

	else {
		// project=$('#attr2qUpl option:selected').text();
		project = $('#attr1qUpl option:selected').attr('title');
	}
	var isVisibleAttr3 = $('#attr3qUplDisp').is(':visible');

	if (isVisibleAttr3) {
		// discipline=$('#attr3qUplDisp').val();
		discipline = $('#attr3qUplDisp').attr('title');
	} else {
		// discipline=$('#attr3qUpl option:selected').text();
		discipline = $('#attr3qUpl option:selected').attr('title');
	}

	/*var isVisibleAttr4 = $('#attr4qUplDisp').is(':visible');
	if (isVisibleAttr4) {
		// site=$('#attr4qUplDisp').val();
		site = $('#attr4qUplDisp').attr('title');
	} else {
		// site=$('#attr4qUpl option:selected').text();
		site = $('#attr4qUpl option:selected').attr('title');
	}*/
	docType = $("#documentTypeWof option:selected").attr('title');
	getThreeCharactersForm(project, docType, keywords, site, discipline, fileName,
			2,id,docName);
	return 1;

}

function getThreeCharactersForm(project, docType, keywords, site, discipline,
		fileName, value,id,docName) {
	// alert(project.toUpperCase()
	// +"-"+docType+"-"+keywords+"-"+site+"-"+discipline.toUpperCase());
	var site1 = site;
	var discipline1 = discipline;

	/*if (site.length >= 3) {

		site1 = site.substring(0, 3);
	}*/
	if (discipline.length >= 3) {

		discipline1 = discipline.substring(0, 3);
	}

	var ext = fileName.split('.').pop();
	getFullNameForm(project, docType, keywords, site1, discipline1, ext, value,id,docName);
}

// Forms Selection For Abbottslangley



function propertyFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllProperties",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null, null, null, null, null,null ],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}


function inventoryFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllInventory",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [null, null, null, null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function builderDetailsFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllBuilderDetails",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null, null ],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function potentialTenantFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllPotentialTenant",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null, null,null ],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function utilitiesCompanyFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllUtilitiesCompanyDetails",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null, null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function checkingOutFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllCheckingOut",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null, null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function tenancyFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllTenancy",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null,null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function givingNoticeFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getGivingNotice",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [null,null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}





function rentReceiptFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllRentReceipt",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null,null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}

function newJobFormSelected(){
	$.ajax({
		type : "GET",
		url : "forms/getAllJobs",
		cache : false,
		success : function(data) {

			$('#csvDownloadSearch').hide();
			// alert(data);
			$("#dynContent").show();
			$("#dynContent").html(data);
			$('#formsList').dataTable( {
		        "bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"aoColumns" : [ 
								null,null,null,null,null],
				"bDestroy" : true,
				"bFilter" : true

		    });

		}
	});
}



//new script


function deletePhoto(folderName,fileName){
	$.ajax({
		type : "GET",
		url : "forms/deletePhoto?folderName="+ folderName+"&fileName="+fileName,
		cache : false,
		success : function(data){
		}
	});
}

