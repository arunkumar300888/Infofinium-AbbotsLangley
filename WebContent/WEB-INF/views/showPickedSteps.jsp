<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
<table id='unpickSteps' class='documents-table display'>
		<thead>
			<tr>
			    <th>Document Name</th>
			    <th>Eb No</th>
				<th>Model Name</th>
				<th>Picked By</th>
				<th>Picked DateTime</th>
				<th>Task</th>
				<th>Unclaim</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${docStepMap}" varStatus="status">
					<tr>
					    <%-- <td>${i.key}</td>
						<td>${i.value.owningCase.model.name}</td>
						<td>${i.value.assignee}</td>
						<td>${i.value.datePicked}</td>
						<td>${i.value.node.trayLabel}</td> 
						<td><a href="#" onclick="unpickDocumentClicked('${i.value.id}');">Unclaim</a></td>
						--%>
						<td>${i.value[0]}</td>
						<td>${i.value[1]}</td>
						<td>${i.key.owningCase.model.name}</td>
						<td>${i.key.assignee}</td>
						<td><fmt:formatDate type="both"
												pattern="dd-MM-yyyy HH:mm:ss" value="${i.key.datePicked}" /></td>
						<%-- <td>${i.key.datePicked}</td> --%>
						<td>${i.key.node.trayLabel}</td>
						<td><a href="#" onclick="unpickDocumentClicked('${i.key.id}');">Unclaim</a></td>
						</tr>
		                    </c:forEach>
		                    
		
		</tbody>
	</table>
</body>
</html>