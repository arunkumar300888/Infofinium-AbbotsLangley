<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>



<script type="text/javascript">

function GenericRentReceiptDetailsClick(tenancy) {
	var url = "forms/generateRentReceiptbyUserID?tenancy="+tenancy+"&TB_iframe=true&height=585&width=900";
	tb_show('Receipt', url, null);
}

function showInWindow(value1)
{
	/* alert("value1"+value1);
	alert("value2"+ value2); */
	 //alert("value1"+value1);
	var url = value1+"&TB_iframe=true&height=585&width=900";
		       /*  window.open(url, "pop", "width=400,height=800"); */
	tb_show('Details', url, null);
}

function giveNoticeMetaData(property,tenancy){
	$.ajax({
		type : "GET",
		url : "forms/showGivingNotice?propertyId="+property+"&tenancyId="+tenancy ,
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
			
		}
	}); 
}

function rentAccountsMetaData(propertyId,tenancyId) {
	$.ajax({
		type : "GET",
		url : "forms/showRentAccountsForm?propertyId="+propertyId+"&tenancyId="+tenancyId,
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

		}
	});
}

function checkingOutMetaData(id){
	
	
	
	if(checkClosedDate()){
		$.ajax({
			type : "GET",
			url : "forms/showCheckingOut" + "?checkingoutId=" + id,
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

			}
		});
	}
	}

	function checkingOut() {
		/* var url = "forms/showCheckingOut" + "?checkingoutId="+0;
		window.open(url,"Check Out",'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes'); */
		var url = "forms/showCheckOutForTenancy" + "?checkingoutId=" + 0;

		window
				.open(
						url,
						"Check Out",
						'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');

	}

	$(document).ready(function() {

		
		
		$( "#tenantFirstName" ).change(function() {
			  document.getElementById("tenantUserName").value=$("#tenantFirstName").val();
			  document.getElementById("tenantPassword").value=$("#tenantFirstName").val();
			});
		
		
		var isOccupied="${isOccupied}";
		
		/* if(isOccupied=="Y"){
		$("#submitFormAttr").hide();
				$("#saveFormAttr").hide();
		
		} */
		
		var status = "${status}";

		if (status != "Tenancy Created" && status!="") {
			$(".text").prop( "onclick", null );
			
			$( ".text" ).removeClass( "datepicker" ); 
			$( "#tenancyClosedDate" ).addClass( "datepicker" );
			 $('#formDatas input').attr('readonly', 'readonly');
			$('#formDatas select').attr('disabled', 'disabled');
			$('#formDatas textarea').attr('readonly', 'readonly'); 
		}
		
		if(status="Checked Out"){
			$("submitFormAttr").hide();
		}
		/*  $(function(){
		 	   $("#roomNumber").multiselect(); 
			}); */

		// 	$("#tenantDateOfBirth").prop("readonly", false);
		$("#tenancyFinishDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});

		if (!$("#tenantNationalInsuranceNumber").val()) {
			$("#tenantNationalInsuranceNumber").val("NA");
		}

		/* var roomNumber="${typeOfRental}";
		if(roomNumber=="perRoom"){
			$("#roomNo").show();
		}else{
			$("#roomNo").hide();
		}  */

	});

	$(document).ready(
			function() {
				$("h4").next("div").not(".hideFalse").prev().addClass(
						"accordBack");
				$("h4").click(
						function() {

							if ($(this).find("span").hasClass(
									"glyphicon-triangle-bottom")) {
								$(this).addClass('accordBack');
								$(this).find("span.glyphicon-triangle-bottom")
										.addClass("glyphicon-triangle-right")
										.removeClass(
												"glyphicon-triangle-bottom");
							} else if ($(this).find("span").hasClass(
									"glyphicon-triangle-right")) {
								$(this).removeClass('accordBack');
								$(this).find("span.glyphicon-triangle-right")
										.toggleClass(
												"glyphicon-triangle-bottom");
							}

						});

			});

	function openUploadForm(id) {

		var docName = document.getElementById(id).value;

		var url = "forms/openForUpload?itemId=" + id + "&docName=" + docName;
		//alert(url);
		window
				.open(
						url,
						"Document Upload",
						'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');
		//tb_show('Update Links', url, null); 

	}

	function setValue(id, val) {
		//alert(val+""+id);
		document.getElementById(id).value = val;

	}

	function openDoc(id) {
		//alert(id);
		var docName = document.getElementById(id).value;
		//alert(docName);
		if (docName != "") {

			var url = "forms/openDoc?docName=" + docName;

			var win = window.open(url, "Document Upload", '');
			setTimeout(function() {
				win.close()
			}, 3000);
		} else {

			dialogTemplate("Document", "No item");
		}

	}

	function removeElement(id) {
		var documentName = document.getElementById(id).value;

		//alert(documentName);
		$.ajax({
			type : "GET",
			url : "forms/removeTempDoc?docName=" + documentName,
			cache : false,
			success : function(data) {
				document.getElementById(id).value = "";
			}
		});

	}

	function checkStartedDate() {
		if ($("#tenancyStartedDate").val() && $("#tenancyFinishDate").val()) {
			dialogTemplate("Check In","Click Submit to Check In");
			return 1;
			
		} else {
			$("#tenancyStartedDate").focus();
			dialogTemplate('Required Fields',
					'Please enter tenancy started date to check in');
			return 0;
		}
	}
	function checkClosedDate() {
		if ($("#tenancyClosedDate").val()) {
			return 1;
		} else {
			$("#tenancyClosedDate").focus();
			dialogTemplate('Required Fields',
					'Please enter tenancy closed date to check out');
			return 0;
		}
	}

	function checkFinishedDate() {
		if ($("#tenancyFinishDate").val()) {
			return 1;
		} else {
			$("#tenancyFinishDate").focus();
			dialogTemplate('Required Fields',
					'Please enter tenancy Finish date to check out');
			return 0;
		}
	}

	function propertySelected(id) {

		var propertyName = document.getElementById(id).value;

		if (propertyName == "0") {
			$('#roomNumber').find('option').remove();
			document.getElementById("propertyAddress").value = "";
			$("#typeOfRental option[value='nil']").attr('selected', 'selected');
			$('#roomNo').hide();
		} else {

			$('#roomNumber').find('option').remove();

			$.ajax({
				type : "GET",
				url : "forms/tenancyRooms?propertyId=" + propertyName,
				cache : false,
				success : function(data) {
					//document.getElementById(id).value="";
					//alert("success "+data);
					var str_array = data.split('/');

					propertySelectCompleted(str_array[0].trim(), str_array[1]
							.trim(), str_array[2].trim());
				}
			});
		}
	}

	function propertySelectCompleted(roomNames, typeOfRental, propertyAddress) {
		//alert(typeOfRental);
		/* $('#roomNumber').multiselect("destroy"); */
		$("#typeOfRental option[value=" + typeOfRental + "]").attr('selected',
				'selected');
		document.getElementById("propertyAddress").value = propertyAddress;
		if (roomNames != "") {
			var str_array = roomNames.split(',');
			$('#roomNo').show();
			//$('#roomNumber').append(new Option("--Select--", "-1", true, true));
			//alert(str_array.length+" "+str_array);
			for ( var j = 0; j <= str_array.length - 1; j++) {
				//alert(str_array[j]);

				$('#roomNumber').append(
						new Option(str_array[j], str_array[j], false, false));
			}
			/*  $('#roomNumber').multiselect(); */
		}

		if (typeOfRental == "wholeHouse") {
			$('#roomNo').hide();
		}

	}

	$(function() {
		var alternate = false;
		$('.form2 h4').click(function() {
			if (alternate == true) {
				$('.form2 > div').hide();
				$(this).next('div').show(300);
			} else
				$(this).next('div').toggle(300);
		});

	});

	$('#typeOfRental').change(function(e) {

		var type = document.getElementById("typeOfRental").value;
		var linktoproperty = document.getElementById("linkToProperty").value;
		//alert(linktoproperty);
		if (linktoproperty == "0") {
			//alert("inside if");
			document.getElementById("propertyAddress").value = "";
			if (type == "perBedroom") {
				/* $('#roomNumber').multiselect("destroy"); */
				$("#roomNo").show();
				/* 	$('#roomNumber').multiselect(); */

			}

			if (type == "wholeHouse" || type == "nil") {
				$('#roomNumber').multiselect("destroy");
				$("#roomNo").hide();
				document.getElementById("propertyAddress").value = "";
				document.getElementById("roomNumber").value = "";
			}
		} else {
			if (type == "perBedroom") {

				$("#roomNo").show();
				/* $('#roomNumber').append(new Option("--Select--", "-1", true, true)).multiselect(); */

			}

			if (type == "wholeHouse" || type == "nil") {
				$("#roomNo").hide();
				document.getElementById("roomNumber").value = "";
			}
		}

	});

	$(document).ready(
			function() {
				$("#submissionDate").val(
						$.datepicker.formatDate("dd-mm-yy", new Date()));
				$("#typeOfRental option[value='${typeOfRental}']").attr(
						'selected', 'selected');

				if ("${typeOfRental}" == "wholeHouse") {
					$('#roomNo').hide();
				}

				var linktoprop = "${linkToProperty}";

				//alert(linktoprop);

				/* if(linktoprop!="-1"){
					propertySelected("linkToProperty", "${userFormId}"); */
				/* $('#roomNumber').multiselect("destroy"); */
				//}
				var roomNames = "${roomnames}";
				if (roomNames != "") {
					var str_array = roomNames.split(',');
					$('#roomNo').show();
					//$('#roomNumber').append(new Option("--Select--", "-1", true, true));
					//alert(str_array.length+" "+str_array);
					for ( var j = 0; j <= str_array.length - 1; j++) {
						//alert(str_array[j]);

						$('#roomNumber').append(
								new Option(str_array[j], str_array[j], false,
										false));
					}
					/*  $('#roomNumber').multiselect(); */
				}

				var roomNumber = "${roomNumber}";
				//alert(roomNumber);
				if (roomNumber != null) {
					//alert(roomNumber);

					/* $("#roomNumber option:selected").prop("selected", false) */
					if (roomNumber.indexOf(",") === -1) {
						//	 alert("index -1");
						/*  $('#roomNumber').multiselect("destroy"); */
						$("#roomNumber option[value='${roomNumber}']").attr(
								'selected', 'selected');
						/*  $('#roomNumber').multiselect(); */
					} else {
						/*  $('#roomNumber').multiselect("destroy"); */
						var splitRoomNumber = roomNumber.split(",");
						//alert("else "+splitRoomNumber+" length "+splitRoomNumber.length );
						for ( var j = 0; j <= splitRoomNumber.length - 1; j++) {
							//alert(splitRoomNumber[j]);
							$(
									"#roomNumber option[value='"
											+ splitRoomNumber[j] + "']").attr(
									'selected', 'selected');
						}
						/*  $('#roomNumber').multiselect(); */
					}

				}
				$("#rentDueDate option[value='${rentDueDate}']").attr(
						'selected', 'selected');
				$("#linkToProperty option[value='${linkToProperty}']").attr(
						'selected', 'selected');
			});

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

	//validation start

	function saveCheck() {
		/* var dob=$("#tenantDateOfBirth").val();
		
		var ndob = dob.split("-"); */

		if ($("#linkToProperty").val() != 0) {
			return 1;
		} else {
			dialogTemplate('Required Fields',
					'Please select the property field');
			return 0;
		}
	}

	function mandatoryCheck() {

		var count = 0;
		$("input[required], textarea[required]").each(function() {
			if (!$(this).val()) {
				count++;
			}

		});
		if (count) {
			dialogTemplate('Mandatory Fields',
					'Please fill all the mandatory fields');
			return 0;
		} else {
			if ($("#tenantMobileNumber").val()
					|| $("#tenantLandlineNumber").val()) {
				if ($("#tenantPassport").val()
						|| $("#tenantDriverLicense").val()) {
					return 1;
				} else {
					dialogTemplate('Upload',
							'Please upload either your passport or driving license');
					return 0;
				}
			} else {
				dialogTemplate('Contact Details',
						'Enter your mobile or landline number');
				return 0;
			}

		}
	}

	//end of validation

	function saveForm() {
		var check = saveCheck();
		//alert(check);
		if (check) {
			loadProgress();
			$("#formDatas").attr("action", "forms/saveTenancyFormForDraft");
			$("#formDatas").submit();
		}
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();

	}

	function submitForm() {

		var check = mandatoryCheck();
		if (check && checkStartedDate()) {
			loadProgress();
			$("#formDatas").attr("action", "forms/createTenancy");
			$("#formDatas").submit();
		}
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();

	}

	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy',
			changeYear : true,
			changeMonth : true,
			yearRange : "-100:+0"
		});

		// 	$("#tenancyClosedDate").datepicker({
		// 		dateFormat : 'dd-mm-yy',
		// 		changeYear : true
		// 	});

		// 	$("#tenancyStartedDate").datepicker({
		// 		dateFormat : 'dd-mm-yy',
		// 		changeYear : true,
		// 		maxDate : 0
		// 	});

		$("#tenantDateOfBirth").click(function() {

		});
	});

	// var jsonString=${jsonStringValue};
</script>
 

<span class="grid-box-header" id="header2title">Tenancy Details<font class="mand">&nbsp;&nbsp;&nbsp;&nbsp;* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>




<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${tenancyId }" id="tenancyId" name="tenancyId"/>
		
		
		
		<!-- <p align="center">
				<b><font size="3" >Tenancy Form</font></b>
			</p> -->
		
		<h4><span class="glyphicon glyphicon-triangle-bottom"></span>  Property Details</h4>
			<div class="hideFalse">
		<section>
		
		<p>
				<label class="FormFieldLabelRequired" for="linkToProperty" title="Property">Property<em class="mand">* </em>:</label>
				<select class="text" id="linkToProperty" name="linkToProperty" onchange="propertySelected('linkToProperty');">
				<option selected="selected" value="0">--Select--</option>
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select>
				<%-- <input class="text" id="linkToProperty" name="linkToProperty" value="${linkToProperty  }" required readonly="readonly" type="text" onclick="openUploadForm('linkToProperty');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('linkToProperty');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToProperty');"> --%>
			</p>
		
			<p>
				<label class="FormFieldLabelRequired" for="propertyAddress" title="Property Address">Property Address<em class="mand">* </em>:</label>
			<textarea class="text" cols="43" id="propertyAddress"  name="propertyAddress" rows="6" maxlength="500">${propertyAddress } </textarea>
			</p>
			
			</section>
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="typeOfRental" title="Type of Rental">Type of Rental<em class="mand">* </em>:</label>
				<select  class="text" id="typeOfRental" name="typeOfRental">
				<option value="nil">Select</option> 
				<option value="wholeHouse">Whole Property</option>
				<option value="perBedroom">Per Room</option>
				
				</select><br/>
				</p>
			<p id="roomNo">
				<label class="FormFieldLabelRequired" for="roomNumber" title="Room Number">Room Number:</label>
				<select class="text" id="roomNumber" name="roomNumber" multiple="multiple">
			</select>		
			</p>
			<p id="rentDueDate">
				<label class="FormFieldLabelRequired" for="rentDueDate" title="Rent Due Date">Rent Due Date:</label>
				<select class="text" id="rentDueDate" name="rentDueDate">
						<c:forEach var="i" begin="1" end="28">
							<option value="${i}">${i}</option>
						</c:forEach>
					</select>
				</p>
			</section>
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Tenant Details</h4>
			<div>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="tenantTitle" title="Title">Title<em class="mand">* </em>:</label>
				<input class="text" id="tenantTitle" name="tenantTitle" required value="${tenantTitle  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantFirstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="tenantFirstName" name="tenantFirstName" required value="${tenantFirstName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantLastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="tenantLastName" name="tenantLastName" required value="${tenantLastName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantDateOfBirth" title="Date Of Birth">Date Of Birth<em class="mand">* </em>:</label>
				<input class="text  datepicker"  id="tenantDateOfBirth" value="${tenantDateOfBirth  }" required name="tenantDateOfBirth" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantLandlineNumber" title="Landline Number">Landline Number:</label>
				<input class="text" id="tenantLandlineNumber" value="${tenantLandlineNumber  }" name="tenantLandlineNumber" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantMobileNumber" title="Mobile Number">Mobile Number:</label>
				<input class="text" id="tenantMobileNumber" value="${tenantMobileNumber  }" name="tenantMobileNumber" type="text"/>
			</p>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantEmailAddress" title="Email Address">Email Address<em class="mand">* </em>:</label>
				<input class="text" id="tenantEmailAddress" name="tenantEmailAddress"  value="${tenantEmailAddress  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantNationalInsuranceNumber" title="National Insurance Number">National Insurance Number:</label>
				<input class="text" id="tenantNationalInsuranceNumber" name="tenantNationalInsuranceNumber" value="${tenantNationalInsuranceNumber  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantCurrentAddress" title="Current Address">Current Address<em class="mand">* </em>:</label>				
				<textarea class="text" cols="43" id="tenantCurrentAddress" required name="tenantCurrentAddress"  rows="6"> ${tenantCurrentAddress  }</textarea>
			</p>
			</section>
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantPassport" title="Passport">Passport:</label>
				<input class="text" id="tenantPassport" name="tenantPassport" value="${tenantPassport  }" readonly="readonly" type="text" onclick="openUploadForm('tenantPassport');"/>
				<input class="openFile" type="button" value="" onclick="openDoc('tenantPassport');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenantPassport');">
			</p>
			
			<p>
				<label class="FormFieldLabel" for="tenantDriverLicense" title="Driver's License">Driver's License:</label>
				<input class="text" id="tenantDriverLicense" name="tenantDriverLicense" value="${tenantDriverLicense}" readonly="readonly" type="text" onclick="openUploadForm('tenantDriverLicense');" />
			<input class="openFile" type="button" value="" onclick="openDoc('tenantDriverLicense');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenantDriverLicense');">
			</p>
			
			<p>
				<label class="FormFieldLabel" for="tenantCreditCheck" title="Credit Check">Credit Check<em class="mand">* </em>:</label>
				<input class="text" id="tenantCreditCheck" name="tenantCreditCheck" required value="${tenantCreditCheck  }" readonly="readonly" type="text" onclick="openUploadForm('tenantCreditCheck');" />
				<input class="openFile" type="button" value="" onclick="openDoc('tenantCreditCheck');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenantCreditCheck');">
			</p>
			<p>
				<label class="FormFieldLabel" for="tenantReferenceCheck" title="Reference Check">Reference Check<em class="mand">* </em>:</label>
				<input class="text" id="tenantReferenceCheck" required value="${tenantReferenceCheck  }" name="tenantReferenceCheck" readonly="readonly" type="text" onclick="openUploadForm('tenantReferenceCheck');" />
			<input class="openFile" type="button" value="" onclick="openDoc('tenantReferenceCheck');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenantReferenceCheck');">
			</p>
			
			
			<p>
				<label class="FormFieldLabel" for="tenantAdditionalRemark" title="Addition Remark">Additional Remarks:</label>
				<textarea class="text" cols="43" id="tenantAdditionalRemark"  name="tenantAdditionalRemark" rows="6" maxlength="500">${tenantAdditionalRemark  } </textarea>
			</p>
			</section>
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Previous Landlord Details</h4>
			<div>
			<section>
				
			
			
			<!-- <p align="center">
				<input class="download" id="landLordFirstName" name="landLordFirstName" type="text" maxlength="50"/>
			</p> -->
			
			
			<p>
				<label class="FormFieldLabelRequired" for="landLordFirstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="landLordFirstName" name="landLordFirstName" required value="${landLordFirstName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="landLordLastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="landLordLastName" name="landLordLastName" required value="${landLordLastName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="landLordAddress" title="Address">Address<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="landLordAddress" name="landLordAddress" required rows="6" maxlength="500">${landLordAddress  } </textarea>
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="landLordEmailAddress" title="Email Address">Email Address<em class="mand">* </em>:</label>
				<input class="text" id="landLordEmailAddress" name="landLordEmailAddress" required value="${landLordEmailAddress  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="landLordContactNumber" title="Contact Number">Contact Number<em class="mand">* </em>:</label>
				<input class="text" id="landLordContactNumber" name="landLordContactNumber" required value="${landLordContactNumber  }" type="text"/>
			</p>
			
			</section>
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Employer Details</h4>
			<div>
			<section>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="employerCompany" title="employerCompany">Company:</label>
				<input class="text" id="employerCompany" name="employerCompany"  value="${employerCompany  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="employerFirstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="employerFirstName" name="employerFirstName" required value="${employerFirstName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="employerLastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="employerLastName" name="employerLastName" required value="${employerLastName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="employerAddress" title="Address">Address<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="employerAddress" name="employerAddress" required rows="6" maxlength="500">${employerAddress  } </textarea>
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="employerEmailAddress" title="Email Address">Email Address:</label>
				<input class="text" id="employerEmailAddress" name="employerEmailAddress" value="${employerEmailAddress  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="employerContactNumber" title="Contact Number">Contact Number:</label>
				<input class="text" id="employerContactNumber" name="employerContactNumber" value="${employerContactNumber  }" type="text"/>
			</p>
			</section>
			</div>
			
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Guarantor Details</h4>
			<div>
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="guarantorCompany" title="guarantorCompany">Company:</label>
				<input class="text" id="guarantorCompany" name="guarantorCompany"  value="${guarantorCompany  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="guarantorFirstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="guarantorFirstName" required name="guarantorFirstName" value="${guarantorFirstName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="guarantorLastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="guarantorLastName" required name="guarantorLastName" value="${guarantorLastName  }" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="guarantorAddress" title="Address">Address<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="guarantorAddress" required name="guarantorAddress" rows="6" maxlength="500">${guarantorAddress  }  </textarea>
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="guarantorEmailAddress" title="Email Address">Email Address:</label>
				<input class="text" id="guarantorEmailAddress" name="guarantorEmailAddress" value="${guarantorEmailAddress  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="guarantorContactNumber" title="Contact Number">Contact Number<em class="mand">* </em>:</label>
				<input class="text" id="guarantorContactNumber" name="guarantorContactNumber" required value="${guarantorContactNumber  }" type="text"/>
			</p>
			</section>
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Next Of Kin</h4>
			<div>
			<section>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="kinFirstName" title="First Name">First Name<em class="mand">* </em>:</label>
				<input class="text" id="kinFirstName" name="kinFirstName" type="text" value="${kinFirstName  }" required maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="kinLastName" title="Last Name">Last Name<em class="mand">* </em>:</label>
				<input class="text" id="kinLastName" name="kinLastName" type="text" value="${kinLastName  }" required maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="kinAddress" title="Address">Address<em class="mand">* </em>:</label>
				<textarea class="text" cols="43" id="kinAddress" name="kinAddress" rows="6"  maxlength="500">${kinAddress  } </textarea>
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="kinEmailAddress" title="Email Address">Email Address<em class="mand">* </em>:</label>
				<input class="text" id="kinEmailAddress" name="kinEmailAddress" required value="${kinEmailAddress  }" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="kinContactNumber" title="Contact Number">Contact Number<em class="mand">* </em>:</label>
				<input class="text" id="kinContactNumber" name="kinContactNumber" required value="${kinContactNumber  }" type="text"/>
			</p>
			
			</section>
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>  Tenancy Details</h4>
			<div>
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyOpenedBy" title="Who Opened the Tenancy">Who Opened the Tenancy<em class="mand">* </em>:</label>
				<input class="text" id="tenancyOpenedBy" name="tenancyOpenedBy" value="${tenancyOpenedBy  }" type="text" required maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyStartedDate" title="Date Tenancy Started">Date Tenancy Started<em class="mand">* </em>:</label>
				<input class="text  datepicker" id="tenancyStartedDate" value="${tenancyStartedDate  }" name="tenancyStartedDate" required type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyFinishDate" title="Tenancy Finish Date">Tenancy Finish Date:<em class="mand">* </em>:</label>
				<input class="text  datepicker" id="tenancyFinishDate" value="${tenancyFinishDate  }" name="tenancyFinishDate" required type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyClosedBy" title="Who Closed the Tenancy">Who Closed the Tenancy:</label>
				<input class="text" id="tenancyClosedBy" name="tenancyClosedBy" value="${tenancyClosedBy  }" type="text" maxlength="50"/>
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="tenancyClosedDate" title="Date Tenancy Closed">Date Tenancy Closed:</label>
				<input class="text  datepicker" id="tenancyClosedDate" value="${tenancyClosedDate  }" name="tenancyClosedDate" type="text"/>
			</p>
			
			<%-- <p>
				<label class="FormFieldLabelRequired" for="checkingOutForm" title="Checking Out Form">Checking Out Form:</label>
				<input class="text" id="checkingOutForm" name="checkingOutForm" value="${checkingOutForm  }" readonly="readonly" type="text" onclick="openUploadForm('checkingOutForm');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('checkingOutForm');">
				<input class="clearFile" type="button" value="" onclick="removeElement('checkingOutForm');">
			</p> --%>
			
			<p>
				<label class="FormFieldLabelRequired" for="checkingOutForm" title="Checking Out Form">Checking Out Form:</label>
			<%-- 	<input type="file" style="width: 310px;" name="checkingOutForm" value="${checkingOutForm}" id="checkingOutForm"/> --%>
			<input class="text" id="checkingOutForm" name="checkingOutForm" value="${checkingOutForm  }" readonly="readonly" type="text" onclick="openUploadForm('checkingOutForm');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('checkingOutForm');">
				<input class="clearFile" type="button" value="" onclick="removeElement('checkingOutForm');">
			</p>
			<p>
				<label class="FormFieldLabelRequired" for="checkingInForm" title="Checking In Form">Checking In Form:</label>
				<%-- <input type="file" style="width: 310px;" name="checkingInForm" value="${checkingInForm}" id="checkingInForm"/> --%>
				<input class="text" id="checkingInForm" name="checkingInForm" value="${checkingInForm  }" readonly="readonly" type="text" onclick="openUploadForm('checkingInForm');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('checkingInForm');">
				<input class="clearFile" type="button" value="" onclick="removeElement('checkingInForm');">
			</p>
			
			
			
			<%  Object status = request.getAttribute("status");
			System.out.println(status);
			if(status.toString().equalsIgnoreCase("Tenancy Created")){ %>
			<p align="center" id="stButton">
				<input class="download" type="button" value="Check In" onclick="checkStartedDate()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
			</p>
			
			<%}%>
			
			<%  Object occupied = request.getAttribute("isOccupied");
			
			if(occupied.toString().equalsIgnoreCase("Y")){ %>
			<p align="center" id="stButton">
				<!-- <a href="#" onclick="checkingOutMetaData('0');" >Check Out</a> -->
				<input class="download" type="button" value="Check Out" onclick="checkingOutMetaData('0');">
				<% if(status.toString().equalsIgnoreCase("Given Notice")){ %>
				<input class="download" type="button" value="View Notice" onclick="showInWindow('forms/viewGivingNoticeForm?tenancyId=${tenancyId}');">
<!-- 				<a href="#" onclick="showInWindow('forms/viewGivingNoticeForm');" >View Notice</a> -->
				<%} %>
				<input class="download" type="button" value="Pay Rent" onclick="rentAccountsMetaData('${linkToProperty}','${tenancyId }');" /> 
			</p>
			
			<p align="center" id="stButton">
			<input
		class="download" type="button" id="genericRent"
		value="Generate Rent Receipt"
		onclick="GenericRentReceiptDetailsClick('${tenancyId}')" />
			</p>
			
			<%}%>
			
<%-- 			<%   --%>
			
<%-- 			if(status.toString().equalsIgnoreCase("Given Notice")){ %> --%>
			
<!-- 			<p align="center"> -->
				
			
<!-- 				<a href="#" onclick="showInWindow('forms/viewGivingNoticeForm');" >View Notice</a> -->
				
<!-- 			</p> -->
			
<%-- 			<%} %> --%>
			
<!-- 			<p align="center"> -->
<!-- 			<input type="button" -->
<!-- 		class="download float-right" style="margin-right: 5%" value="Pay Rent" -->
<%-- 		onclick="rentAccountsMetaData('${linkToProperty}','${tenancyId }');" />  --%>
<!-- 			</p> -->
			<%-- <p>
				<label class="FormFieldLabelRequired" for="checkingOutForm" title="Checking In Form">Checking In Form:</label>
				<input class="text" id="checkingOutForm" name="checkingOutForm" value="${checkingOutForm  }" readonly="readonly" type="text" onclick="openUploadForm('checkingOutForm');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('checkingOutForm');">
				<input class="clearFile" type="button" value="" onclick="removeElement('checkingOutForm');">
			</p> --%>


				
			
			
			</section>
			
			<p align="center" id="statusButton">
			
			</p>
			
			</div>
			
			<h4><span class="glyphicon glyphicon-triangle-right"></span>Reference Documents</h4>
			<div>
			<section>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToTenancyAgreement" title="First Name">Link to Tenancy Agreement<em class="mand">* </em>:</label>
				<input class="text" id="linkToTenancyAgreement" name="linkToTenancyAgreement" required value="${linkToTenancyAgreement  }" readonly="readonly" type="text" onclick="openUploadForm('linkToTenancyAgreement');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('linkToTenancyAgreement');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToTenancyAgreement');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToRentAccounts" title="First Name">Link to Rent Accounts<em class="mand">* </em>:</label>
				<input class="text" id="linkToRentAccounts" name="linkToRentAccounts" required value="${linkToRentAccounts  }" readonly="readonly" type="text" onclick="openUploadForm('linkToRentAccounts');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('linkToRentAccounts');">
				<input class="clearFile" type="button" value="" onclick="removeElement('linkToRentAccounts');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="legalNotification" title="First Name">Legal Notifications:</label>
				<input class="text" id="legalNotification" name="legalNotification" value="${legalNotification  }" readonly="readonly" type="text" onclick="openUploadForm('legalNotification');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('legalNotification');">
				<input class="clearFile" type="button" value="" onclick="removeElement('legalNotification');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenancyDepositCertificate" title="First Name">Tenancy Deposit Certificate<em class="mand">* </em>:</label>
				<input class="text" id="tenancyDepositCertificate" required name="tenancyDepositCertificate" value="${tenancyDepositCertificate  }" readonly="readonly" type="text" onclick="openUploadForm('tenancyDepositCertificate');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('tenancyDepositCertificate');">
				<input class="clearFile" type="button" value="" onclick="removeElement('tenancyDepositCertificate');">
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="councilTaxRegistration" title="First Name">Council Tax Registration<em class="mand">* </em>:</label>
				<input class="text" id="councilTaxRegistration" required name="councilTaxRegistration" value="${councilTaxRegistration  }" readonly="readonly" type="text" onclick="openUploadForm('councilTaxRegistration');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('councilTaxRegistration');">
				<input class="clearFile" type="button" value="" onclick="removeElement('councilTaxRegistration');">
			</p>
			</section>
			<section>
			<p>
				<label class="FormFieldLabelRequired" for="additionalLinks" title="First Name">Additional Links:</label>
				<input class="text" id="additionalLinks" name="additionalLinks"  value="${additionalLinks  }" readonly="readonly" type="text" onclick="openUploadForm('additionalLinks');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('additionalLinks');">
				<input class="clearFile" type="button" value="" onclick="removeElement('additionalLinks');">
			</p>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="correspondenceWithTenants" title="Email Address">Correspondence with Tenant/s:</label>
				<textarea class="text" cols="43" id="correspondenceWithTenants"  name="correspondenceWithTenants" rows="6" maxlength="500"> ${correspondenceWithTenants  }</textarea>
			
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="correspondenceLinks" title="First Name">Correspondence Link:</label>
				<input class="text" id="correspondenceLinks" name="correspondenceLinks" value="${correspondenceLinks  }" readonly="readonly" type="text" onclick="openUploadForm('correspondenceLinks');"/>
			<input class="openFile" type="button" value="" onclick="openDoc('correspondenceLinks');">
				<input class="clearFile" type="button" value="" onclick="removeElement('correspondenceLinks');">
			</p>
			
			<p>
			<label class="FormFieldLabelRequired" for="tenantUserName" title="User Name">User Name For Tenant:</label>
			<input class="text" id="tenantUserName" name="tenantUserName" value="${tenantUserName  }" readonly="readonly" type="text" />
			</p>
			
			<p>
			<label class="FormFieldLabelRequired" for="tenantPassword" title="Password">Password For Tenant:</label>
			<input class="text" id="tenantPassword" name="tenantPassword" value="${tenantPassword  }" readonly="readonly" type="text" />
			</p>
			
			</section>
			</div>
			
		</div>
</form>
		<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="homeClicked();"/></span>
					<% 	if(status.toString().equalsIgnoreCase("Tenancy Created") || status.equals("")){ %>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"/></span>
					<%  }%>
					<span><input
					type="submit" class="download" id="submitFormAttr"
					value="Submit" onclick="submitForm();" /></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

