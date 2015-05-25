<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New Security Group" onclick="createNewSecurityGroup();"  >+ Add New</a>	</span>	
	
</div>

<table id='secGroupLists' class='documents-table display'>
	<thead>
		<tr>
			<th>Security Group Name</th>
			<th>Assignment</th>
			<th>Edit</th>
			<th></th>
			<th>Deactive/Activate</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${secGroupLists}" varStatus="status">
			<c:set var="isActive" scope="page">${i.isActive}</c:set>
			<tr>
				<td id="name">${i.name}</td>
				<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td id="editAssignment"><a href="#"
							onclick="editSecGroupAssignmentClicked('${i.id}');">Assignment</a></td>
						<td id="editSecGroup"><a href="#"
							onclick="editModeSecGroupClicked('${i.id}');">Edit</a></td>
						<td><h1 style='display: none;'>2</h1> <img
							src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
						<td id="deleteSecGroup"><a href="#"
							onclick="deleteModeSecGroupClicked('${i.id}');">Deactive</a></td>
					</c:when>
					<c:otherwise>
						<td id="editAssignment">Assignment</td>
						<td id="editSecGroup">Edit</td>
						<td><h1 style='display: none;'>1</h1> <img
							src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteSecGroup"><a href="#"
							onclick="unDeleteModeSecGroupClicked('${i.id}');">Activate</a></td>
					</c:otherwise>
				</c:choose>
				<%-- <td id="deleteSecGroup"><a href="#" onclick="deleteModeSecGroupClicked('${i.id}');">Delete</a></td> --%>
			</tr>
		</c:forEach>
	</tbody>
</table>