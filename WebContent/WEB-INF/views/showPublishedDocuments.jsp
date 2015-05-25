<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
<table id='documentListReassignOwnerDtbl' class='documents-table display'>
		<thead>
			<tr>
			    <th>Name</th>
			    <th>Submission Date</th>
			    <th>Document Owner</th>
			    <th>Document Type</th>
			    <th>Eb No</th>
			    <!-- <th>Raised Date & Time</th> -->
			    <th>Reassign Owner</th>
			    <th>Reassign</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${publishedDocs}" varStatus="status">
					<tr>
					    <td>${i.name}</td>
					    <td><fmt:formatDate type="both" pattern="dd-MM-yyyy"
												value="${i.targetDate}" /></td>
						<td>${i.author}</td>
						<td>${i.doctype.doctypeName}</td>
						<td>${i.ebNo}</td>
					<%-- 	<td>${i.dateCreated}</td> --%>
						<td>${i.reassignOwner}</td>
						<td><a href="#" onclick="reassignDocumentOwner('${i.id}');">Reassign</a></td>
					</tr>
					</c:forEach>
		</tbody>
	</table>