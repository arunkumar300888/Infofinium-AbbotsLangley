<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Review Comments</title>

<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/wizart/style.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/demo_table_jui.css" />
<link rel="stylesheet" type="text/css" href="../resources/style/jquery-ui.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>
<script src='../resources/js/jquery.dataTables.min.js'
	type="text/javascript"></script>

<script>
	$(function() {
		$("#reviewCommentsPrint").dataTable({
			   "bJQueryUI" : true,
			   "bPaginate": false,
			   "bFilter": false,
			"aaSorting" : [ [ 1, "desc" ] ],
			"sScrollX": "100%",				
		});
	});
</script>
</head>
<body onload="window.print(); window.close();">
<div id="top">
		<div class="my-document complete box-content">
			<span class="float-left grid-box-header top3">Title >> ${docName}</span>
		</div>
</div>
				<div id="dt_example">
					<div id="stepList">
						<table 	class="display" id="reviewCommentsPrint">
							<thead>
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
													<td style="width: 160px; text-align: center;">
													<fmt:formatDate
												type="both" pattern="dd-MM-yyyy HH:mm:ss"
												value="${entry.reviewDate}" /></td>
										<td>${entry.comments}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
</body>
</html>