<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">
<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td align="left">${title}</td>
		</tr>
	</table>
	</div>
<table id='documentListReassignDtbl' class='documents-table display'>
		<thead>
			<tr>
			   <!--  <th>Document Name</th>
			    <th>Author</th>
			    <th>Reassign</th> -->
			    <th>Document Name</th>
			    <th>Eb No</th>
			    <th>Assignee</th>
			    <th>Status</th>
			    <th>Reassign</th>
			    
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${trayDocs}" varStatus="status">
					<tr>
					    <td>${i.key[0]}</td>
					    <td>${i.key[1]}</td>
						<td>${i.value.assignee}</td>
						<td>${i.value.status}</td>
						<td><a href="#" onclick="reassignDocumentForUser('${i.value.owningCase.id}','${i.value.id}','${userId}');">Reassign</a></td>
					</tr>
					</c:forEach>
		</tbody>
	</table>