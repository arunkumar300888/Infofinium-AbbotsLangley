<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>Dashboard level I</title>

<link rel="stylesheet" type="text/css"
	href="resources/Extn/styleDSJV.css" />
<link rel="stylesheet" type="text/css"
	href="resources/style/jquery.treeview.css" />
<link rel="stylesheet" type="text/css" href="resources/style/style.css">

<link rel="stylesheet" type="text/css"
	href="resources/style/demo_page.css">
<link rel="stylesheet" type="text/css"
	href="resources/style/demo_table_jui.css">
<link rel="stylesheet" type="text/css"
	href="resources/style/jquery-ui-1.8.4.custom.css">
<link rel="stylesheet" type="text/css"
	href="resources/style/TableTools_JUI.css">
<link rel="stylesheet" type="text/css"
	href="resources/style/TableTools.css">
<link rel="stylesheet" type="text/css"
	href="resources/cleditor/jquery.cleditor.css" />

<!-- <link rel="stylesheet" type="text/css" href="resources/style/skin.css" /> -->
<link rel="stylesheet" href="resources/style/thickbox.css"
	type="text/css" media="screen" />
<!-- <link href="resources/Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css"> -->

<link rel="stylesheet" type="text/css"
	href="resources/style/form-styles.css">


<script src="resources/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src='resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>
<script src="resources/js/jquery.treeview.js" type="text/javascript"></script>
<script src="resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src='resources/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
<script src='resources/js/shCore.js' type="text/javascript"></script>
<script src="resources/js/JmrSdp.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/js/thickbox.js"></script>
<!-- <script src="resources/js/jquery.form.js" type="text/javascript"></script> -->
<script src="resources/js/Datatable2CSV.js" type="text/javascript"></script>

<script src="resources/cleditor/jquery.cleditor.js"
	type="text/javascript" charset="utf-8"></script>

<script src="resources/cleditor/jquery.cleditor.min.js"
	type="text/javascript" charset="utf-8"></script>

<!-- 	<script src="resources/js/jquery.jcarousel.min.js" type="text/javascript"></script> -->
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<script src='resources/js/TableTools.js' type="text/javascript"></script>
<script src="resources/js/TableTools.min.js" type="text/javascript"></script>
<script src="resources/js/ZeroClipboard.js" type="text/javascript"></script>

<script src='resources/js/popupDialog.js' type="text/javascript"></script>
<script src='resources/js/common.js' type="text/javascript"></script>
<script src='resources/js/ajax.js' type="text/javascript"></script>

<script type="text/javascript">
	$(function() {

		$(".hed img").mouseover(
				function() {
					$(".submenu1").hide();
					var offset = $(this).offset();
					$(this).next(".submenu1").css("top", offset.top + 18).css(
							"left", offset.left);
					$(this).next(".submenu1").show();
				});
		$(".hed img").mouseleave(function() {
			$(this).next(".submenu1").hide();
			$(this).next(".submenu1").mouseover(function() {
				$(this).show();
			});
			$(".submenu1").mouseleave(function() {
				$(this).hide();
			});
		});

	});

	/*  function toggleControl()
	 {
		  $(".popup_ad").slideToggle();
	 } */

	function showPopupPanel(panel, show) {
		// we have to bodge the client height, width at 100% is ok.
		try {
			var elem = document.getElementById(panel);
			if (show) {
				var height = (document.body.clientHeight > window.innerHeight) ? document.body.clientHeight
						: window.innerHeight;
				elem.style.height = height + "px";
				elem.style.visibility = "visible";
			} else {
				elem.style.visibility = "hidden";
				elem.style.height = 0 + "px";
			}
		} catch (e) {
		}
	}
</script>
</head>
<body>
	<div id="_popupPanel" class="FullPagePopupPanel">
		<button type="button" onclick="showPopupPanel('_popupPanel', false);">Hide
			Panel</button>
	</div>
	<div id="_errorPanel" class="FullPagePopupPanel"
		style="z-index: 20000; background: rgba(200, 100, 000, 0.40);">
		<button type="button" onclick="showPopupPanel('_errorPanel', false);">Hide
			Panel</button>
	</div>

	<div class="main" id="mainHtml">
		<div class="spacer">
			<div class="logo"></div>
			<!-- <div class="h12"></div> -->
			<div class="hed">

				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="left" style="padding-left: 4px;"><img border="0"
                                                        title="Home" alt="Home" src="resources/images/home.jpg"
                                                        class="pointer" onclick="homeClicked();" /> <c:if
                                                                test="${canShow ==true}">
                                                                  <img id="downloadTemplateImg" border="0" title="Download Template"
                                                                        alt="Download Template"
                                                                        src="resources/images/downloadtemplate.jpg" class="pointer"
                                                                        onclick="downloadTemplate();" />


                                                                <!--
                                                                        <img id="quickUploadImg" border="0" title="Select Quick Upload if you want to upload a document without a workflow process" alt="Select Quick Upload if you want to upload a document without a workflow process"
									src="resources/images/quickupload.jpg" class="pointer"
									onclick="quickUploadProcess();" />
									-->

								<!-- <img id="uploadDocumentImg" class="pointer" border="0"
                                                                        src="resources/images/UploadWorkflow.jpg" alt="Select Controlled Upload if you want to upload a document requiring a controlled workflow process"
                                                                        title="Select Controlled Upload if you want to upload a document requiring a controlled workflow process" onclick="uploadDocument();" /> -->


                                                                   <img id="uploadDocumentImg" class="pointer" border="0"
                                                                        src="resources/images/upload_menu.png" alt="Upload Process"
                                                                        title="Upload Process" onclick="uploadDocument();" />


                                                                <img id="formsImg" class="pointer" border="0" title="Forms Menu"
                                                                        alt="Forms Menu" src="resources/images/forms_menu.png" />
								<div class="submenu1">
									<ul>
										<c:forEach var="i" items="${formDefinitions}"
											varStatus="status">
											<li>
												<form method="get">
													<a href="#" onclick="formDefinitionClicked('${i.id}');">${i.name}
														- ${i.description}</a>
												</form>
											</li>
										</c:forEach>
									</ul>
								</div>

								<%-- <div class="popup_ad">
				<c:forEach var="i" items="${formDefinitions}"
										varStatus="status">
				<form  method="get"> <a href="#" onclick="formDefinitionClicked('${i.id}');">${i.name}</a></form>
				</c:forEach>
				</div>	 --%>
							</c:if> <c:if test="${canShowAdminMain ==true}">
								<img id="userConsole" class="pointer" border="0"
									src="resources/images/addConsole.jpg" title="Admin Console"
									alt="Admin Console" onclick="adminTemplate();" />
							</c:if></td>

						<td align="right" style="font-weight: bold; font-size: 14px;"><span>Welcome</span>
							&nbsp;<span style="text-transform: uppercase">${username}</span>
						</td>
						<!-- <td align="right" style="width: 110px; padding-left: 4px;">
						<form id="changePassword" method="get">
                                                        <a href="javascript:changePasswordClicked();"><u>Change
                                                                        Password</u></a>
                                                                        <img  class="pointer" border="0"
                                                
                        src="resources/images/ch_password.jpg" onclick="changePasswordClicked();" />
                                                                                </form></td> -->
                                                <td class="sidesplit">&nbsp;</td>
						<td style="width: 67px;"><form id="logoutFrm" method="get">
								<a href="#" onclick="logoutClicked();" class="butnlogout"
									id="btnLogout">Logout</a>
							</form></td>
					</tr>
				</table>
			</div>
			<div class="h12"></div>
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr valign="top">
					<td class="equalier"></td>
					<td id="leftlinks" class="left">

						<div class="bor">
							<div class="title">Search</div>
							<div align="center">
									<table cellspacing="6"><tr>
									<td>
									<input type="radio" name="all" id="all" value="M" checked="checked" onClick="allSearchClicked('M');" >
									<span>All</span></td>
							       <td> <input type="radio" name="all" id="all" value="D" onClick="allDocumentsClicked('D');">
									<span>Documents</span></td>
									<td><input type="radio" name="all" id="all" value="F"  onClick="allFormsClicked('F');">
									<span>Forms</span></td>
										</tr></table></div>
							<form method="post" id="frmSearch">
							<input type="hidden" name="discriminator" value="" id="discriminator"/>
								     <div align="center">
                                    <c:if test="${canShowAdminAll}">  
									<table id="searchAbove">
												<tr>
													<td><input type="button" id="btnSearch"
														class="butn113" value="Search"
														onClick="searchBtnClicked();" /></td>
													<td style="padding-left: 4px;"><input type="reset"
														name="reset" id="btnClearAll" class="butn113"
														value="Clear All" /></td>
												</tr>
											</table></c:if>
									 <c:if test="${canShowAdminSearch}"> 
									
										<table id="searchAbove">
												<tr>
													<td><input type="button" id="btnSearch"
														class="butn113" value="Search"
														onClick="searchBtnClicked();" /></td>
													<td style="padding-left: 4px;"><input type="reset"
														name="reset" id="btnClearAll" class="butn113"
														value="Clear All" /></td>
												</tr>
											</table>
									  </c:if>
									  </div>
									<table cellspacing="8" width="100%" class="searchpan">
									  	<tr valign="top">
										<td>Title</td>
									<tr>
										<td ><input type="text" name="documentName" style="width: 230px;"
											id="documentName" onkeypress="searchKeyPress(event);" />
											<img title="Search for Documents by searching the document title/file name" src="resources/images/comment_question.png" />
											</td>
									</tr>
									
									<tr valign="top">
										<td>Keywords</td>
									</tr>
									<tr>
										<td><input type="text" style="width: 230px;" name="keywords" id="keywords"
											onkeypress="searchKeyPress(event);"  />
												<img title="Search for documents by searching the activities/keywords.( You can use space ' ' to search multiple keywords )" src="resources/images/comment_question.png"  />
											</td>
									</tr>
									
									<tr valign="top">
										<td>Owner/Author</td>
									</tr>
									<tr>
										<td><input name="owner" id="owner"
											onkeypress="searchKeyPress(event);" /></td>
									</tr>
									
									<tr valign="top">
										<td>Unique Doc ID/Reference Number</td>
									</tr>
									<tr>
										<td><input type="text" name="refNo" id="refNo"
											onkeypress="searchKeyPress(event);" /></td>
									</tr>
									
									  	<tr valign="top">
										<td>Raised Date</td>
									</tr>

									<tr valign="top">
										<td>
											<table>

												<tr>
													<td>From</td>
													<td style="padding-left: 25px;">To</td>
												</tr>
												<tr>
													<td><input type="text" id="datepicker1"  style="width: 90px;" 
														name="dateCreatedFrom" readonly="readonly" onkeypress="searchKeyPress(event);" onchange="colorChangedateCreatedFrom();" /></td>
													<td style="padding-left: 25px;"><input type="text"
														id="datepicker2"  style="width: 90px;"  readonly="readonly" name="dateCreatedTo"
														onkeypress="searchKeyPress(event);" onchange="colorChangedateCreatedTo();" /></td>
												</tr>

											</table>
										</td>
									</tr></table>
									
									<div id="dynamicMainFormsDocuments"></div>
									
									<%-- <tr valign="top">
										<td>Form Title</td>
									</tr>
									<tr>
										<td><input type="text" name="documentName"
											style="width: 230px;" id="documentName"
											onkeypress="searchKeyPress(event);" /> <img
											title="Search for Documents by searching the document title/file name"
											src="resources/images/comment_question.png" /></td>
									</tr>
									<tr valign="top">
										<td>Form Type</td>
									</tr>
									<tr>
										<td><select name="documentType" id="documentType"
											onkeypress="searchKeyPress(event);">
												<option value="-1" selected="selected">--Select--</option>
												<c:forEach var="i" items="${formDefinitions}"
													varStatus="status">
													<option value="${i.id}">${i.name} -
														${i.description}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr valign="top">
										<td>Date Range</td>
									</tr>
									<tr valign="top">
										<td>
											<table>

												<tr>
													<td>From</td>
													<td style="padding-left: 25px;">To</td>
												</tr>
												<tr>
													<td><input type="text" id="datepicker3"
														style="width: 90px;" readonly="readonly"
														name="relevantdatefrom"
														onkeypress="searchKeyPress(event);"
														onchange="colorChangerelevantdatefrom();" /></td>
													<td style="padding-left: 25px;"><input type="text"
														id="datepicker4" style="width: 90px;" readonly="readonly"
														name="relevantdateto" onkeypress="searchKeyPress(event);"
														onchange="colorChangerelevantdateto();" /></td>
												</tr>

											</table>
										</td>
									</tr>

									<tr valign="top">
										<td>Form Owner</td>
									</tr>
									<tr>
										<td><input name="author" id="owner"
											onkeypress="searchKeyPress(event);" /></td>
									</tr>

									<tr valign="top">
										<td>Ref. Number</td>
									</tr>
									<tr>
										<td><input type="text" name="ebNo" id="ebNo"
											onkeypress="searchKeyPress(event);" /></td>
									</tr> --%>
									<div align="center">
                                      <c:if test="${canShowAdminAll}"> 
									<table>
												<tr>
													<td><input type="button" id="btnSearch"
														class="butn113" value="Search"
														onClick="searchBtnClicked();" /></td>
													<td style="padding-left: 4px;"><input type="reset"
														name="reset" id="btnClearAll" class="butn113"
														value="Clear All" /></td>
												</tr>
											</table></c:if>
									
									 <c:if test="${canShowAdminSearch}"> 
									<table>
												<tr>
													<td><input type="button" id="btnSearch"
														class="butn113" value="Search"
														onClick="searchBtnClicked();" /></td>
													<td style="padding-left: 4px;"><input type="reset"
														name="reset" id="btnClearAll" class="butn113"
														value="Clear All" /></td>
												</tr>
											</table>
									</c:if>
								</div>
							</form>

						</div>
						<div class="h12"></div>
						<div id="treeFilter" class="bor">
							<div class="title">Filters</div>
							<div style="width: 280px; height: 376px; overflow: auto;">
								<ul id="browser" class="filetree">${treeContent}
								</ul>
							</div>
						</div>

					</td>
					<td class="mid"></td>
					<td>
						<div id="myIntrays" class="bor">
							<div class="title">My Action Trays</div>
							<div id="trayDiv"></div>
						</div>

						<div class="h12"></div>
						<div class="bor">
							<div id="dynContent" style="min-height: 483px;"></div>
							<div id="dynContentMetadata" style="min-height: 648px;"></div>
							<div id="csvDownloadSearch" style="padding-left: 47em;">
								<form id="csvForm1" name="csvForm1" method="post">
									<input type="hidden" name="csv_text" value="" id="csv_text">
									<input type="hidden" name="category" value="SC" id="category">
									<input type="button" class="butn"
										style="margin-bottom: 5px; width: 120px;" value="CSV Download"
										onclick="csvDownloadSearchClicked();" />
								</form>
							</div>
						</div>

					</td>
					<td class="equalier"></td>
				</tr>
			</table>
		</div>
		<div align="center" class="footer">Copyright &copy; Blue Phoenix
			by JMR Consulting UK Ltd</div>
	</div>
	<script>
		$(function() {
			$("#datepicker1").datepicker({
				dateFormat : 'dd-mm-yy',
				showOn : "button",
				buttonImage : "resources/images/calendar.gif",
				buttonImageOnly : true
			/* 				 onClose : function() {

			 this.focus();
			 }  */
			});

			$('#datepicker1').click(function() {
				$('#datepicker1').datepicker('show');
			});

			$("#datepicker2").datepicker({
				dateFormat : 'dd-mm-yy',
				showOn : "button",
				buttonImage : "resources/images/calendar.gif",
				buttonImageOnly : true

			/* onClose : function() {

				this.focus();
			} */
			});

			$('#datepicker2').click(function() {
				$('#datepicker2').datepicker('show');
			});

			$("#datepicker3").datepicker({
				dateFormat : 'dd-mm-yy',
				showOn : "button",
				buttonImage : "resources/images/calendar.gif",
				buttonImageOnly : true

			/* onClose : function() {

				this.focus();
			} */
			});

			$('#datepicker3').click(function() {
				$('#datepicker3').datepicker('show');
			});

			$("#datepicker4").datepicker({
				dateFormat : 'dd-mm-yy',
				showOn : "button",
				buttonImage : "resources/images/calendar.gif",
				buttonImageOnly : true

			/* onClose : function() {

				this.focus();
			} */
			});

			$('#datepicker4').click(function() {
				$('#datepicker4').datepicker('show');
			});

		});
	</script>
</body>
</html>
