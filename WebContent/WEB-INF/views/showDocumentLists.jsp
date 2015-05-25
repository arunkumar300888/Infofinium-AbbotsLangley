<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
	
<table id='documentListDtbl' class='documents-table display'>
		<thead>
			<tr>
			    <th>Document Name</th>
			    <th>Author</th>
			    <th>Raised Date & Time</th>
			    <th>Eb No</th>
			    <th>Status - Action</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="i" items="${docStatusActionMap}" varStatus="status">
					<tr>
						<td>${i.key.name}</td>
						<td>${i.key.author}</td>
						<td><fmt:formatDate type="both"
												pattern="dd-MM-yyyy HH:mm:ss" value="${i.key.dateCreated}" /></td>
                        <td>${i.key.ebNo}</td>
						<td>${i.value}</td>
					    <td><a href="#" onclick="deleteDocumentFromAlfresco('${i.key.id}');">Delete</a></td>
						</tr>
		                    </c:forEach>
		</tbody>
	</table>
	
	<div id="docDeletionDialog"></div>