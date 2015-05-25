<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />



<title>infoFINIUM</title>
<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="resources/style/wizart/jscrollpane.css" />
<link rel="stylesheet" type="text/css" href="resources/style/wizart/style.css" />


<link rel="stylesheet" type="text/css" href="resources/style/jquery.treeview.css" />
<link rel="stylesheet" type="text/css" href="resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="resources/style/demo_table_jui.css" />
<link rel="stylesheet" type="text/css" href="resources/style/TableTools_JUI.css" />
<link rel="stylesheet" type="text/css" href="resources/style/TableTools.css" />
<link rel="stylesheet" type="text/css" href="resources/cleditor/jquery.cleditor.css" />
<link rel="stylesheet" type="text/css" href="resources/style/thickbox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="resources/style/form-styles.css" />
<link rel="stylesheet" type="text/css" href="resources/style/lightbox.css" />

<script src="resources/js/unused/jquery-1.7.2.min.js"></script>
<script src="resources/js/lightbox.min.js"></script>
<script type="text/javascript" src='resources/JScript/jquery.mousewheel.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.jscrollpane.min.js'></script>
<!-- <script type="text/javascript" src='resources/JScript/jquery.collision.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.coords.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.draggable.js'></script>
<script type="text/javascript" src='resources/JScript/utils.js'></script>
<script type="text/javascript" src='resources/JScript/jquery.gridster.js'></script>
<script type="text/javascript" src='resources/JScript/scripts.js'></script>
<script type="text/javascript" src="resources/JScript/jquery.resizableColumns.js"></script>
<script type="text/javascript" src="resources/JScript/store.min.js"></script> -->


<script src='resources/js/jquery.dataTables.min.js' type="text/javascript"></script>
<script src='resources/js/shCore.js' type="text/javascript"></script>
<script src='resources/js/JmrSdp.js' type="text/javascript"></script>
<script type="text/javascript" src="resources/js/thickbox.js"></script>
<script src="resources/js/Datatable2CSV.js" type="text/javascript"></script>
<script src="resources/cleditor/jquery.cleditor.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/cleditor/jquery.cleditor.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<script src='resources/js/TableTools.js' type="text/javascript"></script>
<script src="resources/js/TableTools.min.js" type="text/javascript"></script>
<script src="resources/js/ZeroClipboard.js" type="text/javascript"></script>
<script src='resources/js/popupDialog.js' type="text/javascript"></script>
<script src='resources/js/common.js' type="text/javascript"></script>
<script src='resources/js/ajax.js' type="text/javascript"></script>
<script src="resources/js/jquery-ui-1.8.20.custom.min.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script> -->
<!-- <script type="text/javascript" src="resources/js/angular.js"></script> -->

<script type="text/javascript">
/* $(function()
		{
			$('.scroll-pane, .box-content').jScrollPane(
				{
					hijackInternalLinks: true
				}
			);
		}); */
	
		/*  $(document).keypress(
			    function(event){
			     if (event.which == '13') {
			        event.preventDefault();
			       }


			});  */
			
var builder= "${isBuilder}";			
//alert("builder"+builder);			
			 $('input[type=button],input[type=submit],input[type=reset]').keypress(
				    function(event){
				     if (event.which == '13') {
				        event.preventDefault();
				      }
				}); 
			
		 
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
<div id="wrapper" >
		<%--    <%@include file="include/header.jsp" %> --%>
		<jsp:include page="include/header.jsp" />
		<ul class='gridster'>

			<!----------SEARCH START---------->
			<li class='grid-box view-search' data-rowId="view-search"
				data-row="2" data-col="1" data-sizex="2" data-sizey="2">
				<div id="search">

					<div class='inner-box' id="left-side">
					<div  style="min-height: 230px;">
						<span class="grid-box-header">SEARCH

							<div id="nav-search">
								<a href="#" class="advanced-search">Show Advanced Search</a> <a
									href="#" class="simple-search">Show Simple Search</a>

							</div> <a href='#' class='close-widget-box'> <img
								src="resources/style/wizart/images/x.png" class="float-right" />

						</a>

						</span>


						<div>

							<div class="searchbox simple">
								<form id="frmSimpleSearch" method="post"  class="float-right"
									style="height: 28px">
									<input type="hidden" name="userId" value="${userId}"
										id="userId" />	
										
									<input type="text" onkeypress="simpleSearchKeyPress(event);" class="text" name="simple" size="10" id="simple"
										maxlength="120" value="" placeholder="Search"
										style="vertical-align: top; margin-top: 7px;" /> <input
										type="button" class="button" id="btnSimple"  onclick="simpleSearchClicked();"/>
								</form>
							</div>
							<div class="searchbox advanced">
								<div align="center">
									<table cellspacing="6">
										<tr id="searchoptions">
											<td>
											<input type="radio" name="all" id="all1" value="M" checked="checked"
												onclick="allSearchClicked();"/>
												<label class="radio" for="all1">All</label>
                                            </td>
											<td><input type="radio" name="all" id="all2" value="D"
												onclick="allDocumentsClicked();"/>
												<label class="radio" for="all2">Documents</label>
											</td>
											<td><input type="radio" name="all" id="all3" value="F"
												onclick="allFormsClicked();"/> <label class="radio" for="all3">Forms</label></td>
										</tr>
									</table>
								</div>
								<form method="post" id="frmSearch" class="float-right"
									style="height: 28px">
									<input type="hidden" name="discriminator" value=""
										id="discriminator" /> 
									<input type="hidden" name="userId" value="${userId}"
										id="userId" />	
									<input id="documentName"
										onkeypress="searchKeyPress(event);" type="text" class="text"
										name="documentName" size="10" maxlength="120" value=""
										style="vertical-align: top; margin-top: 7px;" title="Document Name" placeholder="Document Title" />
									<!-- <img title="Search for Documents by searching the document title/file name" src="resources/images/comment_question.png" /> -->
									<input id="keywords" onkeypress="searchKeyPress(event);"
										type="text" class="text" name="keywords" size="10"
										maxlength="120" value="" placeholder="Keywords" title="Keywords"
										style="vertical-align: top; margin-top: 7px;" />
									<!-- <img title="Search for documents by searching the activities/keywords.( You can use space ' ' to search multiple keywords )" src="resources/images/comment_question.png"  /> -->
									<input id="owner" onkeypress="searchKeyPress(event);"
										type="text" class="text" name="owner" size="10"
										maxlength="120" value="" placeholder="Owner / Author"
										style="vertical-align: top; margin-top: 7px;" title="Owner/Author"/> 
										<input
										id="refNo" onkeypress="searchKeyPress(event);" type="text"
										class="text" name="refNo" size="10" maxlength="120" value=""
										placeholder="Unique Doc ID/Ref No"
										style="vertical-align: top; margin-top: 7px;" title="Unique Doc ID/Ref No" /> 
										<input
										id="datepicker1" readonly="readonly" name="dateCreatedFrom"
										type="text" class="text datepicker"
										onkeypress="searchKeyPress(event);"  style="background-color: white;"
										onchange="colorChangedateCreatedFrom();"
										placeholder="From Date" title="From Date"/> 
										<input id="datepicker2"
										readonly="readonly" name="dateCreatedTo"
										onkeypress="searchKeyPress(event);" style="background-color: white;"
										onchange="colorChangedateCreatedTo();" type="text"
										class="text datepicker" placeholder="To Date" title="To Date"/> 
										<span id="dynamicMainFormsDocuments"></span>
									<c:if test="${canShowAdminAll}">
										<span> 
										    <input type="button" id="btnSearch"
											class="butn" value="Find" onClick="searchBtnClicked();" /> 
											<input
											type="reset" name="reset" id="btnClearAll" class="butn" 
											value="Clear All" />
										</span>
									</c:if>
									<c:if test="${canShowAdminSearch}">

										<span>
										
										
										<input type="button" id="btnSearch"
											class="butn" value="Find" onClick="searchBtnClicked();" /> 
											<input type="reset" name="reset" id="btnClearAll" class="butn"
											value="Clear All" />
										</span>
									</c:if>
								</form>
								
							</div>
						</div>
					</div>
					</div>
				</div>
				<div class="clear"></div>	
			</li>
			<!----------SEARCH END---------->
		
			
			<!---------TRAY START-------->
	<!-- 		<li class='grid-box view-action-trays' data-rowId="view-action-trays"
				data-row="1" data-col="1" data-sizex="4" data-sizey="1">
				<div class='inner-box' id="header2">
					<span class="grid-box-header">ACTION TRAYS <a href='#'
						class='close-widget-box'> <img
							src="resources/style/wizart/images/x.png" class="float-right" />
					</a></span>
					<div id="trayDiv"></div>
				</div>
			</li> -->
			<!---------TRAY END------ -->
	
	<!---------LAST DOWNLOADED DOCUMENT START------ -->
	<%-- <c:if test="${canShowLastDA ==true}">
	<li id="downloadedList" class='grid-box view-last-downloads float-left'data-rowId="view-last-downloads" data-row="2" data-col="1" data-sizex="2" data-sizey="2" style="width: 49%;">
	<div class='inner-box'  id="left-side">
	<div id="lastDownloadedDiv"></div>
	</div>
	</li>
	</c:if> --%>
	<!---------LAST DOWNLOADED DOCUMENT END------ -->
	
	<!---------LAST ACTIONED DOCUMENT START------ -->
	<%-- <c:if test="${canShowLastDA ==true}">
	<li id="actionedList" class='grid-box view-last-actioned float-right' data-rowId="view-last-actioned" data-row="2" data-col="3" data-sizex="2" data-sizey="2" style="width: 49%;">
	<div class='inner-box'  id="right-side">
	<div id="lastActionedDiv"></div>
	</div>
	</li>
	</c:if> --%>
	<!---------LAST ACTIONED DOCUMENT END------ -->
			<li class='grid-box view-my-documents'>
				<!---------MYDOCUMENT TABLE START--------->
				
					<div  id="dynContent" class='inner-box'></div>
					<div class="clear"></div>
	    <!---------MYDOCUMENT TABLE END--------->
			

		<!--------- Document Properties START--------->
		
		
		<div class="inner-box" id="dynContentMetadata"></div>
		<div class="clear"></div>
					
		</li>
		</ul>
		<!---------Document Properties END--------->
		<!--------- CSV DOWNLOAD START--------->
  <div id="csvDownloadSearch" align="center">
			<form id="csvForm1" name="csvForm1" method="post">
				<input type="hidden" name="csv_text" value="" id="csv_text" /> <input
					type="hidden" name="category" value="SC" id="category" /> <input
					type="button" class="butn"
					style="margin-bottom: 5px; width: 120px;" value="CSV Download"
					onclick="csvDownloadSearchClicked();" />
			</form>
		</div>

		<!--------- CSV DOWNLOAD END--------->

		<jsp:include page="include/footer.jsp" />
		<div class="clear"></div>
	</div>

	<script type="text/javascript">
		$(function() {
			//$( ".datepicker" ).datepicker();
			$(".datepicker").datepicker({
				dateFormat : 'dd-mm-yy'
			});

			$(".simple-search").hide();
			$(".advanced-search").click(function() {
				$(this).hide();
				$('.simple').hide();
				$(".advanced").fadeIn("slow");
				$('.simple-search').show();
			});

			$(".simple-search").click(function() {
				$(".advanced").hide();
				$(this).hide();
				$('.advanced-search').show();
				$('.simple').fadeIn("slow");
			});			
			
		});
	</script>
	
   <div id="_popupPanel" class="FullPagePopupPanel">
		<button type="button" onclick="showPopupPanel('_popupPanel', false);">Hide
			Panel</button>
	</div>
	<div id="_errorPanel" class="FullPagePopupPanel"
		style="z-index: 20000; background: rgba(200, 100, 000, 0.40);">
		<button type="button" onclick="showPopupPanel('_errorPanel', false);">Hide
			Panel</button>
	</div>
</body>
</html>
