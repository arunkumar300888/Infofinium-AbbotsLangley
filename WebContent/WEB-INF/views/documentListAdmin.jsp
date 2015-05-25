<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="bg_title">
<span class="grid-box-header">My Documents</span>	
</div>
<table id='docLists' class='documents-table display tdhover'>
	<thead>
		<tr>
			<th>Name</th>
			<th>Status-Action</th>
			<th>Submission Date</th>
			<th>Document Owner</th>
			<th>Workflow Process</th>
			<th>Document Type</th>
			<th>Raised Date & Time</th>
			<th>Model Category</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${docList}" varStatus="status">
			<c:choose>
			<c:when test="${i.key[4] == true}">
			<tr onclick="loadMetadata('${i.key[0]}','${i.key[9]}', '${i.key[8]}','${i.key[10]}','true');">
			
				<td>${i.key[0]}</td>
						<td>${i.key[6]}</td>
						<td><fmt:formatDate type="both" pattern="dd-MM-yyyy"
												value="${i.key[1]}" /></td>
						<td>${i.key[2]}</td>
						<td>${i.value}</td>
						<td>${i.key[3]}</td>
						<td>${i.key[5]}</td>
						<td>${i.key[7]}</td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr onclick="loadFormProperties('${i.key[0]}','${i.key[9]}', '${i.key[8]}','${i.key[10]}','true');">
				<td>${i.key[0]}</td>
						<td>${i.key[6]}</td>
						<td><fmt:formatDate type="both" pattern="dd-MM-yyyy"
												value="${i.key[1]}" /></td>
						<td>${i.key[2]}</td>
						<td>${i.value}</td>
						<td>${i.key[3]}</td>
						<td>${i.key[5]}</td>
						<td>${i.key[7]}</td>
			</tr>
			</c:otherwise>
			</c:choose>
		</c:forEach>
	</tbody>
</table>