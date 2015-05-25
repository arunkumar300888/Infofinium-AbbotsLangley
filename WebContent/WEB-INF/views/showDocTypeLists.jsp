<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <div class="bg_title">${title}</div> --%>
<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New"  onclick="createNewDocTypeClicked();"  >+ Add New</a>	</span>	
	
</div>

<table id='doctypeLists' class='documents-table display'>
	<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>isWorkflow</th>
			<th>Abbreviation</th>
			<th>Edit</th>
			<th></th>
			<th>Deactive/Activate</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${doctypes}" varStatus="status">
			<c:set var="isActive" scope="page">${i.isActive}</c:set>
			<tr>
				<td id="name">${i.doctypeName}</td>
				<td id="description">${i.description}</td>
				<c:choose>
					<c:when test="${i.workflow==true}">
						<td id="workflow">Controlled</td>
					</c:when>
					<c:otherwise>
						<td id="workflow">Quick</td>
					</c:otherwise>
				</c:choose>
				<%-- <td id="workflow">${i.workflow}</td> --%>
				<td id="abbreviation">${i.abbreviation}</td>
				<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td id="editDocType"><a href="#"
							onclick="editModeClicked('${i.id}');">Edit</a></td>
						<td><h1 style='display: none;'>2</h1> <img
							src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
						<td id="deleteDocType"><a href="#"
							onclick="deleteModeClicked('${i.id}');">Deactive</a></td>
					</c:when>
					<c:otherwise>
						<td id="editDocType">Edit</td>
						<td><h1 style='display: none;'>1</h1> <img
							src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteDoctype"><a href="#"
							onclick="unDeleteModeDocTypeClicked('${i.id}');">Activate</a></td>
					</c:otherwise>
				</c:choose>
				<%--    <td id="deleteDocType"><a href="#" onclick="deleteModeClicked('${i.id}');">Delete</a></td> --%>
				<!-- <td>Edit<td> -->
			</tr>
		</c:forEach>
	</tbody>
</table>