function searchBtnLinksClicked() {

	// var fromDate = $("#datepicker1Links").val();
	// var toDate = $("#datepicker2Links").val();
	var relevantdatefrom = $("#datepicker3Links").val();
	var relevantdateto = $("#datepicker4Links").val();
	var documentName = $("#documentNameLinks").val();
	var discipline = $("#disciplineLinks").val();
	var sites = $("#sitesLinks").val();
	var documentType = $("#documentTypeLinks").val();

	var owner = $("#ownerLinks").val();
	var ebNo = $("#ebNoLinks").val();
	var keywords = $("#keywordsLinks").val();

	// var nFromDate = fromDate.split("-");
	//
	// var nToDate = toDate.split("-");
	//
	// var n1FromDate = new Date(nFromDate[2], nFromDate[1] - 1, nFromDate[0]);
	//
	// var n1ToDate = new Date(nToDate[2], nToDate[1] - 1, nToDate[0]);

	var nRelevantDateFrom = relevantdatefrom.split("-");
	var nRelevantDateTo = relevantdateto.split("-");

	var n1RelevantDateFrom = new Date(nRelevantDateFrom[2],
			nRelevantDateFrom[1] - 1, nRelevantDateFrom[0]);
	var n1RelavantDateTo = new Date(nRelevantDateTo[2], nRelevantDateTo[1] - 1,
			nRelevantDateTo[0]);

	if (documentName == "" && owner == "" && ebNo == "" && keywords == ""
			&& discipline == "-1" && documentType == "-1" && sites == "-1"
			&& relevantdatefrom == "" && relevantdateto == "") {
		// fromDate == ""
		// && toDate == "" &&
		// alert("Inside");

		dialogTemplateLinks("Search Links",
				"Please enter a value for Search Links");
		// alert("Please enter a value for Quick Search");
		// return false;
		$('a.ui-dialog-titlebar-close').remove();
		return false;

	}

	else if (documentName != "" || owner != "" || ebNo != "" || keywords != ""
			|| discipline != "-1" || documentType != "-1" || sites != "-1"
			|| relevantdatefrom != "" || relevantdateto != "") {

		// fromDate != "" || toDate != "" ||
		// alert("*** In Case 2");

		if (relevantdatefrom == "" && relevantdateto != ""
				|| relevantdatefrom != "" && relevantdateto == "") {

			if (relevantdatefrom == "") {
				var NewDialog = $("<div id='MenuDialog' title='Search Links'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select start date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 140,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							class : 'butn',
							width : '40px',
							click : function() {

								$(this).dialog("close");
								$('#datepicker3Links').focus();
								$('#datepicker3Links').css({
									'background-color' : 'Red'
								});

							}
						}
					}
				});
				$('a.ui-dialog-titlebar-close').remove();
				return false;
			}

			else if (relevantdateto == "") {
				// alert("Please select end date");
				var NewDialog = $("<div id='MenuDialog' title='Search Links'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select end date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 140,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							class : 'butn',
							width : '40px',
							click : function() {
								$(this).dialog("close");
								$('#datepicker4Links').focus();
								$('#datepicker4Links').css({
									'background-color' : 'Red'
								});
							}
						}
					}
				});

				$('a.ui-dialog-titlebar-close').remove();

				return false;
			}
		}

		else if (relevantdatefrom != "" && relevantdateto != "") {

			// alert("From:" +relevantdatefrom);
			// alert("From:" +relevantdateto);

			if (n1RelavantDateTo < n1RelevantDateFrom) {

				var NewDialog = $("<div id='MenuDialog' title='Search Links'>\<p>\<span class='ui-icon ui-icon-alert'"
						+ " style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span>"
						+ "<b>Please select a valid date range. End date must be greater than start date</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 140,
					modal : true,
					buttons : {
						"Ok" : {
							text : 'Ok',
							class : 'butn',
							width : '40px',
							click : function() {
								$(this).dialog("close");
								$('#datepicker4').focus();
								$('#datepicker4').css({
									'background-color' : 'Red'
								});

								// return false;

							}
						}
					}
				});
				$('a.ui-dialog-titlebar-close').remove();
				return false;
			}

			$('#datepicker3Links').css({
				'background-color' : 'White'
			});
			$('#datepicker4Links').css({
				'background-color' : 'White'
			});
		}
		// }

		// if (fromDate == "" && toDate != "" || fromDate != "" && toDate == ""
		// ) {
		//
		// if (fromDate == "") {
		// //alert("Please select start date");
		// var NewDialog = $("<div id='MenuDialog' title='Search
		// Links'>\<p>\<span class='ui-icon ui-icon-alert'" +
		// " style='float: left; margin: 0 7px 20px 0;'> " +
		// "</span>" +
		// "<b>Please select raised start date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 140,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		// class : 'butn',
		// width : '40px',
		// click : function() {
		//
		// $(this).dialog("close");
		// $('#datepicker1Links').focus();
		// $('#datepicker1Links').css({
		// 'background-color' : 'Red'
		// });
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close')
		// .remove();
		// return false;
		// }
		//
		// else if (toDate == "") {
		// //alert("Please select end date");
		// var NewDialog = $("<div id='MenuDialog' title='Search
		// Links'>\<p>\<span class='ui-icon ui-icon-alert'" +
		// " style='float: left; margin: 0 7px 20px 0;'> " +
		// "</span>" +
		// "<b>Please select raised end date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 140,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		// class : 'butn',
		// width : '40px',
		// click : function() {
		// $(this).dialog("close");
		// $('#datepicker2Links').focus();
		// $('#datepicker2Links').css({
		// 'background-color' : 'Red'
		// });
		//
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close')
		// .remove();
		//
		// return false;
		// }
		//
		// }
		//
		// else if (fromDate != "" && toDate != "") {
		//
		// if (n1ToDate < n1FromDate) {
		//
		// var NewDialog = $("<div id='MenuDialog' title='Search
		// Links'>\<p>\<span class='ui-icon ui-icon-alert'" +
		// " style='float: left; margin: 0 7px 20px 0;'> " +
		// "</span>" +
		// "<b>Please select a valid raised date range. End date must be greater
		// than start date</b></p>\</div>");
		// NewDialog.dialog({
		// resizable : false,
		// width : 300,
		// height : 140,
		// modal : true,
		// buttons : {
		// "Ok" : {
		// text : 'Ok',
		// class : 'butn',
		// width : '40px',
		// click : function() {
		// $(this).dialog("close");
		// $('#datepicker2Links').focus();
		// $('#datepicker2Links').css({
		// 'background-color' : 'Red'
		// });
		//
		// // return false;
		//
		// }
		// }
		// }
		// });
		// $('a.ui-dialog-titlebar-close')
		// .remove();
		// return false;
		// }
		//
		// $('#datepicker1Links').css({
		// 'background-color' : 'White'
		// });
		// $('#datepicker2Links').css({
		// 'background-color' : 'White'
		// });
		//
		// }

		// alert(fromDate+toDate+relevantdatefrom+relevantdateto+documentName+discipline+sites+documentType+owner+ebNo+keywords);
		// return false;

		// alert("Before");
		$.ajax({
			type : "POST",
			url : "searchLinks",
			cache : false,
			data : $('#frmSearchLinks').serialize(),
			success : function(data) {

				// alert(data);

				$("#documentLinks").html(data);
				$('#documentLinksLists').dataTable({
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
}

function userEditClicked(userId) {
	
	$.ajax({
		type : "GET",
		url : "user/editUser" + "?userId=" + userId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function dialogTemplateLinks(title, value) {

	var NewDialog = $("<div id='MenuDialog' title='" + title
			+ "'>\<p>\<span class='ui-icon ui-icon-alert'"
			+ " style='float: left; margin: 0 7px 20px 0;'> " + "</span>"
			+ "<b>" + value + "</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 300,
		height : 140,
		modal : true,
		buttons : {
			"Ok" : {
				text : 'Ok',
				class : 'butn',
				width : '40px',
				click : function() {

					$(this).dialog("close");
					// return false;
				}
			}
		}
	});

}

function downloadReferenceLinks(path, name, url) {
	$('#path').val(path);
	$('#name').val(name);
	$("#downloadReferenceForm").attr("action",
			url + "/template/downloadReferenceLinks");
	$("#downloadReferenceForm").submit();

}

$("#selectall").click(function() {
	$('.case').attr('checked', this.checked);
});

$(".case").click(function() {

	if ($(".case").length == $(".case:checked").length) {
		$("#selectall").attr("checked", "checked");
	} else {
		$("#selectall").removeAttr("checked");
	}

});

function searchKeyPressLinks(e) {
	if (typeof e == 'undefined' && window.event) {
		e = window.event;
	}

	if (e.keyCode == 13) {
		document.getElementById('btnSearchLinks').click();
	}
}

function testout() {

	if ($("#editLinks") != "undefined") {

		$.ajax({
			type : "GET",
			url : "docupload/validatereference",
			cache : false,
			success : function(data) {

				var htmldata = data.trim();

				if (htmldata == 1) {

					$("#attachLinks").hide();
					$("#editLinks").show();

				} else {

					$("#editLinks").hide();
					$("#attachLinks").show();
				}
			}
		});
	}

	/*
	 * if ($("#dynContentMetadata") != "undefined") { var path =
	 * $('#pathId').val(); var name = $('#documentNameForRefresh').val(); var
	 * caseid = $('#caseIdForRefresh').val(); loadMetadata(name, path, caseid);
	 *  }
	 */
}

// Admin Module Start

function adminTemplate() {
	$.ajax({
		type : "GET",
		url : "adminTemplate/goAdminTemplate",
		cache : false,
		success : function(data) {
			$("#mainHtml").html(data);
			// alert(data);
		}
	});
}

// For Doc Type Start
function createDocTypes() {
	$.ajax({
		type : "GET",
		url : "docTypes/goCreateDoctype",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#doctypeLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});

			// alert(data);
		}
	});
	// var url = "docTypes/goCreateDoctype?TB_iframe=true&height=190&width=400";
	// tb_show('Document Types', url, null);

}

function createNewDocTypeClicked() {
	// alert("Inside");
	var id = -1;
	$.ajax({
		type : "GET",
		url : "docTypes/goCreateNewDocTypes" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function editModeClicked(docTypeId) {
	// alert(docTypeId);
	var id = docTypeId;
	$.ajax({
		type : "GET",
		url : "docTypes/goCreateNewDocTypes" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function deleteModeClicked(docTypeId) {
	var NewDialog = $("<div id='MenuDialog' title='Document Type'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "docTypes/deleteDocType" + "?id=" + docTypeId,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#doctypeLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "docTypes/deleteDocType"+ "?id=" + docTypeId,
	// cache : false,
	// success : function(data) {
	// $("#dynamicContent").html(data);
	// $('#doctypeLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	//		
	// }
	// });

}

function unDeleteModeDocTypeClicked(docTypeId) {
	var NewDialog = $("<div id='MenuDialog' title='Document Type'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "docTypes/unDeleteDocType" + "?id=" + docTypeId,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#doctypeLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

function docTypesCreation() {
	// alert("inside");

	var docType = $("#docType").val();
	var description = $("#description").val();
	var abbreviation = $("#abbreviation").val();

	if (docType == "" || description == "" || abbreviation == "") {
		dialogTemplateLinks("Document types", "All fields are mandatory");
		return false;
	}

	// patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
	patt1 = /^[&()a-zA-Z0-9_\s\[\]]*$/g;
	patt2 = /^[&a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt1.test(docType) == true) {
		dialogTemplate("Doctype Creation",
				"Special characters not allowed in doctype name");
		return false;
	}

	if (!patt2.test(abbreviation) == true) {
		dialogTemplate("Doctype Creation",
				"Special characters not allowed in doctype abbreviation");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "docTypes/createDocType",
		cache : false,
		data : $('#createDocTypesFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			$('#doctypeLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
			// alert(data);
		}
	});

}

// End Doc Type

// Start Attributes
function attributesCreation() {
	var attr1 = $('#attr1').val();
	var attr2 = $('#attr2').val();
	var attr3 = $('#attr3').val();
	var attr4 = $('#attr4').val();
	if (attr1 == "" || attr2 == "" || attr3 == "" || attr4 == "") {
		// alert("All fields are mandatory");
		dialogTemplateLinks("Attributes", "All fields are mandatory");
		return false;
	}
	// alert("inside");
	$.ajax({
		type : "POST",
		url : "attributes/createAttributes",
		cache : false,
		data : $('#attributesFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			// alert(data);
		}
	});
}

function createNewAttributeClicked() {
	// alert("Attributes already found please edit that");
	$.ajax({
		type : "GET",
		url : "attributes/goShowNewAttribute",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		},
		error : function(data) {
			// alert('failure' +data);
			dialogTemplateLinks("Attributes Found",
					"Please edit that attributes");
			return false;
		}
	});
}

function createAttributes() {
	$.ajax({
		type : "GET",
		// url : "attributes/goCreateAttribute",
		url : "attributes/goShowAttribute",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#attributeLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 10,
				 */
				"sScrollY" : "390px",
				"bFilter" : false,
				"bDestroy" : true
			});
			// alert(data);
		}
	});

}

function editModeAttributeClicked(attributeId) {

	$.ajax({
		type : "GET",
		url : "attributes/goCreateAttribute" + "?id=" + attributeId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});

}

function attributeUpdate() {
	$.ajax({
		type : "POST",
		url : "attributes/saveAttribute",
		cache : false,
		data : $('#editAttributesFrm').serialize(),
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#attributeLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 10,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
		}
	});

}

// End Attributes

// Start Attribute Values

function createAttributeValues() {
	// alert("ins");
	var id = "-1";
	$.ajax({
		type : "GET",
		url : "attributeValues/goCreateAttributeValues?" + "id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			// alert(data);
		}
	});
	// var url =
	// "attributeValues/goCreateAttributeValues?TB_iframe=true&height=190&width=400";
	// tb_show('Attribute Values', url, null);
}

function attributeValuesCreation() {
	var attr = $("#attr").val();
	var attrValue = $("#attrValue").val();
	var abbreviation = $("#abbreviation").val();

	if (attr == "" || attrValue == "" || abbreviation == "" || attr == "-1") {
		dialogTemplateLinks("Attribute Values", "All fields are mandatory");
		return false;
	}
	// specialCharacterValidation(attrValue,abbreviation);
	patt1 = /^[&()a-zA-Z0-9_\s\[\]]*$/g;
	patt2 = /^[&a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt1.test(attrValue) == true) {
		dialogTemplate("Attribute Values",
				"Special characters are not allowed in attribute values");
		return false;
	}

	if (!patt2.test(abbreviation) == true) {
		dialogTemplate("Attribute Values",
				"Special characters are not allowed in abbreviation");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "attributeValues/createAttributeValues",
		cache : false,
		data : $('#attributeValuesFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			// alert(data);
		}
	});
}

function showAttributeValues() {

	// alert("inside");
	$.ajax({
		type : "GET",
		url : "attributeValues/goShowAttributeValues",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#attrValueLists').dataTable({
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

function editModeAttrValueClicked(attrValueId) {
	// alert(attrValueId);

	// var id=attrValuesId;

	$.ajax({
		type : "GET",
		url : "attributeValues/goEditAttribute" + "?id=" + attrValueId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function deleteModeAttrValueClicked(attrValueId) {
	// alert(attrValueId);
	var NewDialog = $("<div id='MenuDialog' title='Attribute Value'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "attributeValues/deleteAttributeValue" + "?id="
								+ attrValueId,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#attrValueLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "attributeValues/deleteAttributeValue"+ "?id=" + attrValueId,
	// cache : false,
	// success : function(data) {
	// $("#dynamicContent").html(data);
	// $('#attrValueLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });

}

function undoDeleteModeAttrValueClicked(attrValueId) {
	// alert(attrValueId);
	var NewDialog = $("<div id='MenuDialog' title='Attribute Value'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "attributeValues/unDeleteModeAttrValueClicked"
								+ "?id=" + attrValueId,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#attrValueLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

function editAndSaveAttributeValues() {
	var attrName = $('#attrName').val();
	var attrValue = $('#attrValue').val();
	var abbreviation = $('#abbreviation').val();

	if (attrName == "" || attrValue == "" || abbreviation == "") {
		dialogTemplateLinks("Edit Attribute Values", "All fields are mandatory");
		return false;
	}
	// specialCharacterValidation(attrValue, abbreviation);
	patt1 = /^[&()a-zA-Z0-9_\s\[\]]*$/g;
	patt2 = /^[&a-zA-Z0-9_\s\[\]]*$/g;
	if (!patt1.test(attrValue) == true) {
		dialogTemplate("Attribute Values",
				"Special characters are not allowed in attribute values");
		return false;
	}
	if (!patt2.test(abbreviation) == true) {
		dialogTemplate("Attribute Values",
				"Special characters are not allowed in abbreviation");
		return false;
	}
	// specialCharValidation(attrValue,abbreviation, "Edit Attributes", "Special
	// characters not allowed");
	// alert("ins");
	$.ajax({
		type : "POST",
		url : "attributeValues/editSaveAttributeValue",
		cache : false,
		data : $('#editAttributeValuesFrm').serialize(),
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#attrValueLists').dataTable({
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

// End Attribute Values

// For Grp
function showGroups() {

	$.ajax({
		type : "GET",
		url : "groups/goShowGroups",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#groupLists').dataTable({
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

function createNewGroup() {
	var id = -1;
	$.ajax({
		type : "GET",
		url : "groups/goCreateNewGroup" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function groupCreation() {
	// alert("inside");
	var group = $("#groupName").val();
	if (group == "") {
		dialogTemplateLinks("Group Creation", "Group field is mandatory");
		return false;
	}
	patt = /^[&a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt.test(group) == true) {
		dialogTemplate("Group Creation",
				"Special characters not allowed in group");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "groups/createGroup",
		cache : false,
		data : $('#createGroupFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			$('#groupLists').dataTable({
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

function editModeGroupClicked(groupId) {
	var id = groupId;
	$.ajax({
		type : "GET",
		url : "groups/goCreateNewGroup" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

// End of Group

// Start Roles

function showRoles() {
	$.ajax({
		type : "GET",
		url : "roles/goShowRoles",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#roleLists').dataTable({
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

function createNewRole() {
	var id = -1;
	$.ajax({
		type : "GET",
		url : "roles/goCreateNewRole" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});

}

function roleCreation() {
	// alert("inside");
	var role = $("#roleName").val();
	if (role == "") {
		dialogTemplateLinks("Role Creation", "Role field is mandatory");
		return false;
	}
	patt = /^[\/a-zA-Z0-9_\s\[\]]*$/g;

	if (!patt.test(role) == true) {
		dialogTemplate("Role Creation",
				"Special characters not allowed in Role");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "roles/createRole",
		cache : false,
		data : $('#createRoleFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			$('#roleLists').dataTable({
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

function editModeRoleClicked(roleId) {
	var id = roleId;
	$.ajax({
		type : "GET",
		url : "roles/goCreateNewRole" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function deleteModeRoleClicked(roleId) {
	// alert("inside delete");
	var id = roleId;
	var NewDialog = $("<div id='MenuDialog' title='Role'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "roles/goDeleteRole" + "?id=" + id,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#roleLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// var id=roleId;
	// $.ajax({
	// type : "GET",
	// url : "roles/goDeleteRole"+ "?id=" + id,
	// cache : false,
	// success : function(data) {
	// $("#dynamicContent").html(data);
	// $('#roleLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });

}

function unDeleteModeRoleClicked(roleId) {
	// alert("inside delete");
	var id = roleId;
	var NewDialog = $("<div id='MenuDialog' title='Role'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "roles/goUnDeleteRole" + "?id=" + id,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#roleLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

// End Roles

// Start Sec Groups
function showSecurityGroups() {
	$.ajax({
		type : "GET",
		url : "secGroup/goShowSecGroups",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#secGroupLists').dataTable({
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

function createNewSecurityGroup() {
	var id = -1;
	$.ajax({
		type : "GET",
		url : "secGroup/goCreateNewSecGroup" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});

}

function securityGroupAssignmentCreation() {
	// alert("inside");
	$("#result").html("");
	var project = $("#projectSec").val();
	var category = $("#categorySec").val();
	var discipline = $("#disciplineSec").val();
	var site = $("#siteSec").val();
	var doctype = $("#doctypeSec").val();
	var secGroupName = $("#secGroupName").val();

	if (project == "-1" || category == "-1" || discipline == "-1"
			|| site == "-1" || doctype == "-1" || secGroupName == "") {
		dialogTemplateLinks("Security Group", "All fields are mandatory");
		return false;
	}

	// patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
	// if (!patt.test(secGroupName) == true) {
	// alert("inside");
	// dialogTemplateLinks("Security Group",
	// "Special characters not allowed in security group");
	// return false;
	// }

	$.ajax({
		type : "POST",
		url : "secGroup/createSecGroupAssignment",
		cache : false,
		data : $('#createNewSecGroupAssignmentFrm').serialize(),
		success : function(data) {
			// $("#dynamicContent").html(data);
			$("#showDynamicContent").html(data);
			$('#secGroupAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
			$("#title").hide();

		}
	});
}

function securityGroupCreation() {
	// alert("data");
	$("#result").html("");
	var secGroupName = $("#secGroupName").val();
	if ($.trim(secGroupName) == "") {
		dialogTemplateLinks("Security Group", "Field is mandatory");
		return false;
	}

	patt = /^[a-zA-Z0-9_\s\[\]]*$/g;
	if (!patt.test(secGroupName) == true) {
		// alert("inside");
		dialogTemplateLinks("Security Group",
				"Special characters not allowed in security group");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "secGroup/createSecGroup",
		cache : false,
		data : $('#createNewSecGroupFrm').serialize(),
		success : function(data) {
			$("#dynamicContent").html(data);
			$('#secGroupLists').dataTable({
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

function editSecGroupAssignmentClicked(id) {
	// alert("edit assignment:" +id );
	$.ajax({
		type : "GET",
		url : "secGroup/goShowSecGroupAssignmentList" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#secGroupAssignmentLists').dataTable({
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

function createNewSecurityGroupAssignment(secGroupId) {
	// alert("create assignment:" +secGroupId);
	$.ajax({
		type : "GET",
		url : "secGroup/goCreateNewSecGroupAssignment" + "?id=" + secGroupId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

// function securityGroupCreation(){
// var project=$('#projectSec').val();
// var category=$('#categorySec').val();
// var discipline=$('#disciplineSec').val();
// var site=$('#siteSec').val();
// var doctype=$('#doctypeSec').val();
// var secGroupName=$('#secGroupName').val();
//	
// if(project=="-1" || category=="-1" || discipline=="-1" || site=="-1" ||
// doctype=="-1" || secGroupName==""){
// dialogTemplateLinks("Security Group", "All fields are mandatory");
// return false;
// }
// patt = /^[a-zA-Z0-9_-\s\[\]]*$/g;
// if (!patt.test(secGroupName) == true) {
// dialogTemplateLinks("Security Group",
// "Special characters not allowed in security group");
// return false;
// }
// $.ajax({
// type : "POST",
// url : "secGroup/createSecGroup",
// cache : false,
// data : $('#createNewSecGroupFrm').serialize(),
// success : function(data) {
// $("#dynamicContent").html(data);
// }
// });
//	
// }

function editModeSecGroupClicked(secGroupId) {
	var id = secGroupId;
	$.ajax({
		type : "GET",
		url : "secGroup/goCreateNewSecGroup" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function deleteModeSecGroupClicked(secGroupId) {
	// alert("inside delete");
	var id = secGroupId;
	var NewDialog = $("<div id='MenuDialog' title='Security Group'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "secGroup/goDeleteSecGroup" + "?id=" + id,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#secGroupLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "secGroup/goDeleteSecGroup"+ "?id=" + id,
	// cache : false,
	// success : function(data) {
	// $("#dynamicContent").html(data);
	// $('#secGroupLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });
}

function unDeleteModeSecGroupClicked(secGroupId) {
	// alert("inside delete");
	var id = secGroupId;
	var NewDialog = $("<div id='MenuDialog' title='Security Group'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "secGroup/goUnDeleteSecGroup" + "?id=" + id,
						cache : false,
						success : function(data) {
							$("#dynamicContent").html(data);
							$('#secGroupLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

}

function deleteModeSecGroupAssignmentClicked(secGroupAssignmentId, secGroupId) {
	// alert(secGroupAssignmentId);

	var NewDialog = $("<div id='MenuDialog' title='Attribute Value'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "secGroup/goDeleteSecGroupAssignment" + "?id="
								+ secGroupAssignmentId + "&secGroupId="
								+ secGroupId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#secGroupAssignmentLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "secGroup/goDeleteSecGroupAssignment"+ "?id=" +
	// secGroupAssignmentId+"&secGroupId="+secGroupId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#secGroupAssignmentLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });

}

// End Sec Groups

// Template Management Start
function showTemplates() {
	$.ajax({
		type : "GET",
		url : "template/goShowTemplates",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#templateLists').dataTable({
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

function createNewTemplate() {
	// alert("create");
	var id = -1;
	// $("#uploading").hide();
	$.ajax({
		type : "GET",
		url : "template/goCreateNewTemplate" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			$("#templateUploading").hide();
		}
	});

}

function uploadTemplateCompleted(result, fileName) {
	// alert("In complete");
	$("#templateUploading").remove();
	if (result == 1) {
		var TemplateUploadDialog = $("<div id='MenuDialog' title='Template Upload Result'>\<p>\<span class='ui-icon ui-icon-check' "
				+ "style='float: left; margin: 0 7px 20px 0;'> "
				+ "</span>Template uploaded successfully with name: <b>"
				+ fileName + "</b></p>\</div>");
		TemplateUploadDialog.dialog({
			resizable : false,
			width : 540,
			height : 170,
			modal : true,
			closeOnEscape : false,
			buttons : {
				"Ok" : {
					text : 'Ok',
					class : 'butn',
					width : '40px',
					click : function() {
						$(this).dialog("close");
						showTemplates();
					}
				}
			}
		});
		$('a.ui-dialog-titlebar-close').remove();
	} else {
		dialogTemplate("Template Upload",
				"Template failed to upload due to a large file size.");
	}
}

function uploadTemplateClicked() {
	// alert("inside");
	var project = $("#projectTemplate").val();
	var category = $("#categoryTemplate").val();
	var discipline = $("#disciplineTemplate").val();
	var site = $("#siteTemplate").val();
	var doctype = $("#doctypeTemplate").val();
	var templateFile = $("#templateFile").val();

	if (project == "-1" || category == "-1" || discipline == "-1"
			|| site == "-1" || doctype == "-1") {
		dialogTemplateLinks("Upload Template", "All fields are mandatory");
		return false;
	}

	if (templateFile == "") {
		dialogTemplateLinks("Upload Template",
				"Please select a template to upload");
		$('#templateFile').focus();
		return false;
	}
	$("#templateUploading").dialog({
		height : 73,
		width : 224,
		modal : true,
		closeOnEscape : false,
		resizable : false,
		draggable : false
	});

	$("#createNewTemplateFrm").attr("action", "template/uploadNewTemplate");
	$("#createNewTemplateFrm").submit();
	$(this).dialog("close");
	$(this).dialog('destroy').remove();
	// $.ajax({
	// type : "POST",
	// url : "template/uploadNewTemplate",
	// cache : false,
	// data : $('#createNewTemplateFrm').serialize(),
	// success : function(data) {
	// $("#dynamicContent").html(data);
	// }
	// });
}

function templateAssignmentClicked(templateId) {
	// alert(templateId);
	$.ajax({
		type : "GET",
		url : "template/goShowTemplateAssignment" + "?id=" + templateId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#showTemplateAssignmentLists').dataTable({
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

function deleteModeTemplateAssignmentClicked(templateId) {
	// alert(templateId);
	var NewDialog = $("<div id='MenuDialog' title='Template'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "template/goDeleteTemplateAssignment" + "?id="
								+ templateId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#templateLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "template/goDeleteTemplateAssignment"+"?id=" +templateId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#templateLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });

}

// Template Management End

// Start Model Combo
function showModelCombo() {
	$.ajax({
		type : "GET",
		url : "workflowMap/goShowModels",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#modelComboLists').dataTable({
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

function createNewWorkflowModelMap() {
	var id = -1;
	$.ajax({
		type : "GET",
		url : "workflowMap/goCreateNewWorkflowModelMap" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});

}

function workflowModelMapCreation() {

	var modelName = $('#modelName').val();
	var description = $('#description').val();
	var doctype = $('#doctype').val();
	if (modelName == "-1" || doctype == "-1" || description == "") {
		dialogTemplateLinks("Workflow Management", "All fields are mandatory");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "workflowMap/createWorkflowModelMap",
		cache : false,
		data : $('#createNewWorkflowModelFrm').serialize(),
		success : function(data) {
			$("#dynamicContent").html(data);
			// dialogTemplateLinks("Workflow Model Map","Model Mapped
			// Successfully");
			$('#modelComboLists').dataTable({
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

function modelAssignmentClicked(id) {
	// alert(id);
	$.ajax({
		type : "GET",
		url : "workflowMap/goShowModelMapAssignmentList" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#modelComboValueLists').dataTable({
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

function modelAssignementCreation() {
	var project = $("#projectModel").val();
	var category = $("#categoryModel").val();
	var discipline = $("#disciplineModel").val();
	var site = $("#siteModel").val();
	var modelName = $('#modelName').val();
	var doctype = $('#doctypeName').val();

	if (project == "-1" || category == "-1" || discipline == "-1"
			|| site == "-1" || doctype == "" || modelName == "") {
		dialogTemplateLinks("Workflow Management", "All fields are mandatory");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "workflowMap/createModelMapAssignment",
		cache : false,
		data : $('#createNewModelAssignmentFrm').serialize(),
		success : function(data) {
			// $("#dynamicContent").html(data);
			$("#showDynamicContent").html(data);
			$('#modelComboValueLists').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
			// $("#title").hide();

		}
	});

}

function createNewModelMapAssignment(modelComboId) {
	// alert("inside");
	$.ajax({
		type : "GET",
		url : "workflowMap/goCreateNewModelMapAssignment" + "?id="
				+ modelComboId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
		}
	});
}

function deleteModeModelComboClicked(modelComboId) {
	// alert(secGroupAssignmentId);
	var NewDialog = $("<div id='MenuDialog' title='Workflow'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "workflowMap/goDeleteModelMap" + "?id="
								+ modelComboId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#modelComboLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

function unDeleteModeModelComboClicked(modelComboId) {
	// alert(secGroupAssignmentId);
	var NewDialog = $("<div id='MenuDialog' title='Workflow'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "workflowMap/goUnDeleteModelMap" + "?id="
								+ modelComboId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#modelComboLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

function deleteModelMapAssignmentClicked(modelComboId) {
	// alert(secGroupAssignmentId);
	var NewDialog = $("<div id='MenuDialog' title='Workflow'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "workflowMap/goDeleteModelMapAssignment" + "?id="
								+ modelComboId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#modelComboLists').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "workflowMap/goDeleteModelMapAssignment"+ "?id=" + modelComboId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#modelComboLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });
}

// End Model Combo

// User Management Start
function showUsers() {
	$.ajax({
		type : "GET",
		url : "user/goShowUsers",
		cache : false,
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

function userEditClicked(userId) {

	$.ajax({
		type : "GET",
		url : "user/editUser" + "?userId=" + userId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function userUpdation(userId) {
	var updateEmail = $('#updateEmail').val();
	var mobileNo = $('#updatemobileNo').val();
	if (updateEmail == "" || mobileNo == "") {
		dialogTemplateLinks("User Updation", "All fields are mandatory");
		return false;
	}

	if ($.trim(updateEmail).length == 0) {
		dialogTemplateLinks("User Updation", "Please enter valid email address");
		return false;
	}

	if (!validateEmail(updateEmail)) {
		dialogTemplateLinks("User Updation", "Invalid email address");
		return false;
	}

	if (!validatePhone(mobileNo)) {
		dialogTemplateLinks("User Updation", "Invalid mobile no. Please do not enter any special characters use only numbers");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "user/updateUser",
		cache : false,
		data : $('#updateUserFrm').serialize(),
		success : function(data) {
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

function createNewUser() {
	// alert("Inside");
	$.ajax({
		type : "GET",
		url : "user/createUser",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
			// alert(data);
		}
	});
}

function createUser() {
	// $("#result").html("");
	$.ajax({
		type : "GET",
		url : "user/goCreate",
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}
function validateEmail(sEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}



function validatePhone(mobileNo) {
	
	var filter = /^[0-9]+$/;
	if (filter.test(mobileNo)) {
		return true;
	} else {
		return false;
	}
}



function userCreation() {
	$("#result").html("");
	var userName = $('#createNewUser').val();
	var passWord = $('#createNewPassword').val();
	var createNewEmail = $('#createNewEmail').val();
	var mobileNo = $('#mobileNo').val();

	if (userName == "" || passWord == "" || createNewEmail == ""
			|| mobileNo == "") {
		dialogTemplateLinks("User Creation", "All fields are mandatory");
		return false;
	}

	if ($.trim(createNewEmail).length == 0) {
		dialogTemplateLinks("User Creation", "Please enter valid email address");
		return false;
	}
	passwordRegex = /^[a-zA-Z0-9_-]{6,18}$/g;
	if(!passwordRegex.test(passWord)==true){
		dialogTemplateLinks("User Creation", "Password should range from 6-18 and no special characters are allowed");
		$('#createNewPassword').val();
		return false;
	}
	
	if (!validateEmail(createNewEmail)) {
		dialogTemplateLinks("User Creation", "Invalid email address");
		return false;
	}

	if (!validatePhone(mobileNo)) {
		dialogTemplateLinks("User Creation", "Invalid mobile no.Please do not use any special characters.");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "user/createUser",
		cache : false,
		data : $('#createUserFrm').serialize(),
		success : function(data) {
			// alert(data);
			$("#dynamicContent").html(data);
			// alert(data);
		}
	});
}

function userAssignmentClicked(userId) {
	$.ajax({
		type : "GET",
		url : "user/goShowUserAssignment" + "?id=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#showUserAssignmentLists').dataTable({
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

function userSecGroupClicked(userId) {
	$.ajax({
		type : "GET",
		url : "user/goShowUserSecGroupAssignment" + "?id=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userSecGroupAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#secGroupLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});
}

function userGroupRoleClicked(userId) {
	$.ajax({
		type : "GET",
		url : "user/goShowUserGroupRoleAssignment" + "?id=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userGroupRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#groupRoleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});
}

function deleteModeUserClicked(userId) {
	// alert(usedId);
	var NewDialog = $("<div id='MenuDialog' title='User'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to deactivate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "user/goDeleteUser" + "?id=" + userId,
						cache : false,
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "user/goDeleteUser"+"?id=" +userId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#userLists').dataTable({
	// "bJQueryUI" : true,
	// "sPaginationType" : "full_numbers",
	// "iDisplayLength" : 25,
	// "sScrollY" : "390px",
	// "bFilter" : true,
	// "bDestroy" : true
	// });
	// }
	// });
}

function unDeleteModeUserClicked(userId) {
	// alert(usedId);
	var NewDialog = $("<div id='MenuDialog' title='User'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Do you want to activate?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "user/goUnDeleteUser" + "?id=" + userId,
						cache : false,
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

function deleteSecGroupFromUserClicked(secGroupId, userId) {

	var NewDialog = $("<div id='MenuDialog' title='User Security Group'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "user/deleteSecGroupFromUser" + "?secGroupId="
								+ secGroupId + "&userId=" + userId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#userSecGroupAssignmentLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
							$('#secGroupLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
						}
					});

				}
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "user/deleteSecGroupFromUser"+"?secGroupId=" +secGroupId
	// +"&userId=" +userId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#userSecGroupAssignmentLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// $('#secGroupLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// }
	// });

}

function addSecGroupToUserClicked(secGroupId, userId) {
	$.ajax({
		type : "GET",
		url : "user/addSecGroupToUser" + "?secGroupId=" + secGroupId
				+ "&userId=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userSecGroupAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#secGroupLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});

}

function createNewGroupRole(userId) {
	$.ajax({
		type : "GET",
		url : "user/showCreateGroupRole" + "?userId=" + userId,
		cache : false,
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function groupRoleCreation() {
	$("#result").html(" ");
	var group = $("#group").val();
	var role = $("#role").val();
	if (group == "-1" || role == "-1") {
		dialogTemplateLinks("Group Role Creation", "All fields are mandatory");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "user/createGroupRole",
		cache : false,
		data : $('#createGroupRoleFrm').serialize(),
		success : function(data) {
			$("#dynamicContent").html(data);
		}
	});
}

function addGroupRoleToUserClicked(grpRoleId, userId) {
	// alert(grpRoleId);
	$.ajax({
		type : "GET",
		url : "user/addGroupRoleToUser" + "?grpRoleId=" + grpRoleId
				+ "&userId=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userGroupRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#groupRoleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});
}

function deleteGroupRoleFromUserClicked(grpRoleId, userId) {

	var NewDialog = $("<div id='MenuDialog' title='User Group Role'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "user/deleteGroupRoleFromUser" + "?grpRoleId="
								+ grpRoleId + "&userId=" + userId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#userGroupRoleAssignmentLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
							$('#groupRoleLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
						}
					});

				}
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "user/deleteGroupRoleFromUser"+"?grpRoleId=" +grpRoleId +"&userId="
	// +userId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#userGroupRoleAssignmentLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// $('#groupRoleLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// }
	// });

}

function userRoleClicked(userId) {
	// alert("userRoleAssignment");
	$.ajax({
		type : "GET",
		url : "user/goShowUserRoleAssignment" + "?id=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#roleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});

}

function addRoleToUserClicked(roleId, userId) {
	$.ajax({
		type : "GET",
		url : "user/addRoleToUser" + "?roleId=" + roleId + "&userId=" + userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#userRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#roleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});
}

function deleteRoleFromUserClicked(roleId, userId) {
	var NewDialog = $("<div id='MenuDialog' title='User Role'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "user/deleteRoleFromUser" + "?roleId=" + roleId
								+ "&userId=" + userId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#userRoleAssignmentLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								/*
								 * "sPaginationType" : "full_numbers",
								 * "iDisplayLength" : 25,
								 */
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
							$('#roleLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								/* "sPaginationType" : "full_numbers", */
								/* "iDisplayLength" : 25, */
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
						}
					});

				}
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "user/deleteRoleFromUser"+"?roleId=" +roleId +"&userId=" +userId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#userRoleAssignmentLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// $('#roleLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// }
	// });

}

function isValidEmailAddress(emailAddress) {
	var pattern = new RegExp(
			/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	return pattern.test(emailAddress);
};

// User Management End

// Feature Management Start

function showFeatures() {
	$.ajax({
		type : "GET",
		url : "features/goShowFeatures",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#featureLists').dataTable({
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

function accessRoleClicked(featureId) {
	var id = featureId;
	$.ajax({
		type : "GET",
		url : "features/showAddNewRoleToFeature" + "?id=" + id,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#featureRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#roleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});

		}
	});
}

function addRoleToFeatureClicked(featureId, roleId) {
	$.ajax({
		type : "GET",
		url : "features/goAddRoleToFeature" + "?featureId=" + featureId
				+ "&roleId=" + roleId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#featureRoleAssignmentLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
			$('#roleLists').dataTable({
				"bJQueryUI" : true,
				"bPaginate" : false,
				/*
				 * "sPaginationType" : "full_numbers", "iDisplayLength" : 25,
				 */
				"sScrollY" : "410px",
				"sScrollX" : "300px",
				"bFilter" : false,
				"bDestroy" : true
			});
		}
	});

}

function deleteRoleFromFeatureClicked(featureId, roleId) {

	var NewDialog = $("<div id='MenuDialog' title='Feature'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "features/goDeleteRoleToFeature" + "?featureId="
								+ featureId + "&roleId=" + roleId,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#featureRoleAssignmentLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
							$('#roleLists').dataTable({
								"bJQueryUI" : true,
								"bPaginate" : false,
								"sScrollY" : "410px",
								"sScrollX" : "300px",
								"bFilter" : false,
								"bDestroy" : true
							});
						}
					});

				}
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});

	// $.ajax({
	// type : "GET",
	// url : "features/goDeleteRoleToFeature"+ "?featureId=" +
	// featureId+"&roleId=" +roleId,
	// cache : false,
	// success : function(data) {
	// $("#showDynamicContent").html("");
	// $("#dynamicContent").html(data);
	// $('#featureRoleAssignmentLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// $('#roleLists').dataTable({
	// "bJQueryUI" : true,
	// "bPaginate": false,
	// "sScrollY" : "410px",
	// "sScrollX" : "300px",
	// "bFilter" : false,
	// "bDestroy" : true
	// });
	// }
	// });

}

// Feature Management End

// Delete Document Start
function removeProgress() {
	$('#ProgressBarDialog').dialog('destroy').remove();
}

function showDocumentsForDeletion() {
	// alert("Inside");
	loadProgress();
	$.ajax({
		type : "GET",
		url : "docupload/goOpenCasesWithDocs",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#documentListDtbl').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});

			// $('#ProgressBarDialog').dialog('destroy').remove();
			removeProgress();
		}
	});
}

function deleteDocumentFromAlfresco(documentId) {
	var NewDialog = $("<div id='MenuDialog' title='Document Deletion'>\<p>\<span class='ui-icon ui-icon-alert' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to delete it?</b></p>\</div>");
	NewDialog
			.dialog({
				resizable : false,
				width : 370,
				height : 140,
				modal : true,
				buttons : {
					"Yes" : {
						text : 'Yes',
						class : 'butn',
						width : '40px',
						click : function() {
							$(this).dialog("close");
							$('#enterReasonDialog').dialog('destroy').remove();
							$
									.ajax({
										type : "GET",
										url : "docupload/goReasonForDeletion"
												+ "?documentId=" + documentId,
										cache : false,
										success : function(data) {
											$("#docDeletionDialog").html(data);
											$("#enterReasonDialog")
													.dialog(
															{
																resizable : false,
																width : 320,
																height : 260,
																modal : true,
																buttons : {
																	"Save" : {
																		text : 'Save',
																		class : 'butn',
																		width : '40px',
																		click : function() {
																			var reasonValue = $(
																					"#reason")
																					.val();
																			if ($
																					.trim(reasonValue) == "") {
																				dialogTemplateLinks(
																						"Document Deletion",
																						"Please enter the reason for deletion");
																				return false;
																			}
																			$(
																					this)
																					.dialog(
																							"close");
																			loadProgress();
																			$
																					.ajax({
																						type : "POST",
																						url : "docupload/deleteDocument",
																						cache : false,
																						data : $(
																								'#deleteReasonFrm')
																								.serialize(),
																						success : function(
																								data) {
																							$(
																									"#showDynamicContent")
																									.html(
																											"");
																							$(
																									"#dynamicContent")
																									.html(
																											data);
																							$(
																									'#documentListDtbl')
																									.dataTable(
																											{
																												"bJQueryUI" : true,
																												"sPaginationType" : "full_numbers",
																												"iDisplayLength" : 25,
																												"sScrollY" : "390px",
																												"bFilter" : true,
																												"bDestroy" : true
																											});
																							removeProgress();
																							dialogTemplateLinks(
																									"Document Deletion",
																									"This document has been successfully deleted");

																						}
																					});
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
						class : 'butn',
						width : '40px',
						click : function() {
							$(this).dialog("close");
							return false;
						}
					}
				}
			});
}

// Delete Document End

// Admin Module Workflow Adminstration

// Unpick Document Start
function showPickedSteps() {
	// alert("inside");
	loadProgress();
	$.ajax({
		type : "GET",
		url : "adminTemplate/goPickedSteps",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#unpickSteps').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
			removeProgress();
		}
	});
}

function unpickDocumentClicked(id) {
	// alert(id);
	// return false;
	var NewDialog = $("<div id='MenuDialog' title='Unclaim Document'>\<p>\<span class='ui-icon ui-icon-info' "
			+ "style='float: left; margin: 0 7px 20px 0;'> "
			+ "</span><b>Are you sure you want to unclaim document?</b></p>\</div>");
	NewDialog.dialog({
		resizable : false,
		width : 370,
		height : 140,
		modal : true,
		buttons : {
			"Yes" : {
				text : 'Yes',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					$.ajax({
						type : "GET",
						url : "adminTemplate/unpickStep" + "?stepId=" + id,
						cache : false,
						success : function(data) {
							$("#showDynamicContent").html("");
							$("#dynamicContent").html(data);
							$('#unpickSteps').dataTable({
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
			},
			"No" : {
				text : 'No',
				class : 'butn',
				width : '40px',
				click : function() {
					$(this).dialog("close");
					return false;
				}
			}
		}
	});
}

// Unpick Document End

// Reassign User Start
function showAssignedUserTasks() {
	// alert("Inside Reassign");
	$.ajax({
		type : "GET",
		url : "adminTemplate/getUsers",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$("#trayLabelDiv").hide();
		}
	});

}

function userTasksSelected() {
	var user = $("#userTray").val();
	// alert(user);
	if (user != "-1") {
		$.ajax({
			type : "GET",
			url : "adminTemplate/getUserTray?user=" + user,
			cache : false,
			success : function(data) {
				$("#showDynamicContent").html("");
				$("#trayLabelDiv").show();
				$("#trayLabelDiv").html(data);
				// $("#dynamicContent").html(data);
			}
		});

	}

	else {
		$("#trayLabelDiv").hide();
	}
}

function viewTasksClicked() {
	var userId = $("#userTray").val();
	var trayLabel = $("#userTasks").val();
	$.ajax({
		type : "GET",
		url : "adminTemplate/trayDocs" + "?trayLabel=" + trayLabel + "&userId="
				+ userId,
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html(data);
			$('#documentListReassignDtbl').dataTable({
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

function reassignDocumentForUser(caseId, stepId, userId) {
	// alert("In Reassign: "+caseId+ ":" +stepId+":" +userId);
	var url = "adminTemplate/reassignUser" + "?caseId=" + caseId + "&stepId="
			+ stepId + "&userId=" + userId
			+ "&TB_iframe=true&height=100&width=300";
	tb_show('Reassign Task', url, null);
}

// Reassign User End

// Reassign Owner Start Region

function showReassignOwnerDocs() {
	loadProgress();
	$.ajax({
		type : "GET",
		url : "adminTemplate/showPublishedDocs",
		cache : false,
		success : function(data) {
			$("#showDynamicContent").html("");
			$("#dynamicContent").html(data);
			$('#documentListReassignOwnerDtbl').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"sScrollY" : "390px",
				"bFilter" : true,
				"bDestroy" : true
			});
			removeProgress();
		}
	});
}

function reassignDocumentOwner(documentId) {
	// alert("inside");
	var url = "adminTemplate/reassignOwner" + "?documentId=" + documentId
			+ "&TB_iframe=true&height=100&width=300";
	tb_show('Reassign Document Owner', url, null);

}

// Reassign Owner End Region

// Admin Module End

// Forms Module Start
function formDefinitionClicked(formDefinitionId) {

	$
			.ajax({
				type : "GET",
				url : "forms/formsDefinition" + "?formDefinitionId="
						+ formDefinitionId,
				cache : false,
				success : function(data) {
					$("#dynContent").html(data);
					$('#csvDownloadSearch').hide();
					$("#header3").show();
				}
			});
}

function loadDraftForm(userFormId) {

	$.ajax({
		type : "GET",
		url : "forms/showDraftForm" + "?userFormId=" + userFormId,
		cache : false,
		success : function(data) {
			$("#dynContent").html(data);
			$('#csvDownloadSearch').hide();
			$("#header3").show();
		}
	});
}

function formDataSubmit(frmData) {

	$.ajax({
		type : "POST",
		url : "forms/create",
		cache : false,
		data : frmData,
		success : function(data) {
			// $("#dynContent").html(data);
			$("#dynContent").html('');
			refreshTray();
			loadAllDocuments();
		}
	});
}

function formDataSave(frmData) {

	$.ajax({
		type : "POST",
		url : "forms/saveForDraft",
		cache : false,
		data : frmData,
		success : function(data) {
			// alert("inside");
			$("#trayDiv").html(data);
			loadAllDocuments();
		}
	});
}

function formDataCancel() {
	backBtnClicked();
}

function formDataUpdate(frmData) {

	$.ajax({
		type : "POST",
		url : "forms/saveForUpdate",
		cache : false,
		data : frmData,
		success : function(data) {
			eval(data);
		}
	});
}

function trayDraftClicked() {

	$.ajax({
		type : "GET",
		url : "forms/listDraftForms",
		cache : false,
		success : function(data) {
			$('#csvDownloadSearch').hide();
			$("#dynContent").html(data);
			$('#example').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bDestroy" : true,
				"bFilter" : true
			});

			// $('#csvDownloadSearch').show();
		}
	});
}

function onPickFormClicked(path, formName, caseId, stepId) {
	$.ajax({
		type : "GET",
		url : "dash/pickForm?path=" + path + "&formName=" + formName
				+ "&caseId=" + caseId + "&stepId=" + stepId,
		cache : false,
		success : function(data) {
			$("#dynContentMetadata").html(data);
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
			$('#dynContent').hide();

		},
		error : function(data) {
			alert("Error: " + data);
		}
	});
}

function loadFormProperties(formName, path, caseId, stepList) {
	$.ajax({
		type : "GET",
		url : "dash/metaDataForForm" + "?path=" + path + "&formName="
				+ formName + "&caseId=" + caseId + "&stepList=" + stepList,
		cache : false,
		success : function(data) {
			$("#dynContentMetadata").html(data);
			$("#dynContentMetadata").show();
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

			$('#leftlinks').hide();
			$('#myIntrays').hide();
			$('#dynContent').hide();

			// For Header visibility disable in properties page

			$('#downloadTemplateImg').hide();
			$('#quickUploadImg').hide();
			$('#uploadDocumentImg').hide();
			$('#formsImg').hide();

			// For Document Properties Page
			$('#documentProperties').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers",
				"iDisplayLength" : 25,
				"bFilter" : true,
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

function showFormBtnClicked(userFormId) {

	window.open("forms/showStaticForm?userFormId=" + userFormId, "userform_"
			+ userFormId, "menubar=1,titlebar=1,toolbar=1,status=yes", true);
}

function formBtnClicked(userFormId) {
	$.ajax({
		type : "GET",
		url : "forms/showForm" + "?userFormId=" + userFormId,
		cache : false,
		success : function(data) {
			$('#metaSpaceDiv').html(data);
		}
	});
}

// For Forms Workflow Actions Trigger
function goActionFormsClickedA(modelName, path, docName, popUpNeeded) {
	var defaultValue = $("#actions").val();
	if (defaultValue == -1) {
		dialogTemplate("Alert",
				"Please select a valid action before selecting Next");
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
		if (reqd == 'y' && rsn.length == 0) {
			goActionValidations(modelName, action);
		} else {
			goActionCommon();
		}
	}
}

// Forms Module End
