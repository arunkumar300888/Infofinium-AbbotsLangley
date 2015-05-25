<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>History</title>



	
<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.css" />

<link rel="stylesheet" type="text/css" href="../resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/demo_table_jui.css" />



<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src='../resources/js/jquery.dataTables.min.js'
	type="text/javascript"></script>

<script>
	$(document).ready(function() {

		/* 	$('#historyTable').dataTable().rowGrouping({ iGroupingColumnIndex: 2,
				sGroupBy: "letter",
				bHideGroupingColumn: true }); */

		$("#historyTable").dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bFilter" : true,
			"sScrollX": "100%",	
			/*  "sDom": 'T<"clear">lfrtip',
			 "oTableTools": {
		         "aButtons": [
		             {
		                 "sExtends": "print",
		                 "sButtonText": "",
		                 "sMessage": "Generated by Document Portal"
		             }
		         ]
		     }, */
			"iDisplayLength" : 100,
			"aaSorting" : [],
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false,
				"mRender": function() {
			          return "test";
			      }  
			}, {
				"bSortable" : false
			}]
		});

	});
	function csvDownloadFormHistoryClicked() {
		$("#csvFormHistory").attr("action", "csv");
		$("#csvFormHistory").submit();
	}
	
	function strReplace( str) {
		return str.replace("#", "\n");
	}
	
</script>
</head>
<body>
	<div class="main">
		<div class="spacer">
		<!-- <div class="logo"></div>
			<div class="h12"></div> -->
			<div class="bor" >
			<div id="top">
				<div class="my-document complete box-content">
				<span class="float-left grid-box-header top3">Title >> ${docName}
				</span>
				</div>
				</div>
				<div id="dt_example" >
					<div id="stepList" >
						<table 
							class='display' id="historyTable">
							<thead>
								<tr>
									<th>Action Date</th>
									<th>TaskName</th>
									<th>User Performed</th>
									<th>Action</th>
									<th>Details</th>
									<th>Reason</th>
								</tr>
							</thead>
							<tbody>
								<%--  <c:forEach var="i" items="${histories}">
					 	<tr> <td>${i.actionDate}</td><td>${i.taskName}</td><td>${i.performedUser}</td><td>${i.action}</td></tr>
					 </c:forEach> --%>
								<c:forEach var="i" items="${formHistory}">
									<tr>
										<td><fmt:formatDate type="both"
												pattern="dd-MM-yyyy HH:mm:ss" value="${i.performedOn}" /></td>
										<td>${ i.formStatus}</td>
										<td>${i.user.userName}</td>
										<td>${i.action}</td>
										
<%-- 										<td><fmt:${i.details}</td> --%>
										<td>
										<c:set var="data" value="${i.details}"></c:set>
																				
										${fn:replace(data,'#','<br />')} 


</td> 
										<td>${i.reason}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div align="center" class="divCenter">
					<form id="csvFormHistory" name="csvFormHistory" method="post">
						<input type="button" class="butn" value="CSV Download"
							onclick="csvDownloadFormHistoryClicked();" />
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>