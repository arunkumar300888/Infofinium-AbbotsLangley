<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New Role" onclick="createNewRole();" >+ Add New</a>	</span>	
	
</div>
<table id='roleLists' class='documents-table display'>
	<thead>
		<tr>
			<th>Role Name</th>
			<th>Edit</th>
			<th></th>
			<th>Deactive/Activate</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${roleLists}" varStatus="status">
			<c:set var="isActive" scope="page">${i.isActive}</c:set>
			<tr>
				<td id="name">${i.roleName}</td>

				<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td id="editRoles"><a href="#"
							onclick="editModeRoleClicked('${i.id}');">Edit</a></td>
						<td><h1 style='display: none;'>2</h1> <img
							src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
						<td id="deleteRoles"><a href="#"
							onclick="deleteModeRoleClicked('${i.id}');">Deactive</a></td>
					</c:when>
					<c:otherwise>
						<td id="editRoles">Edit</td>
						<td><h1 style='display: none;'>1</h1> <img
							src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteRoles"><a href="#"
							onclick="unDeleteModeRoleClicked('${i.id}');">Activate</a></td>
					</c:otherwise>
				</c:choose>
				<%-- <td id="deleteRoles"><a href="#" onclick="deleteModeRoleClicked('${i.id}');">Delete</a></td> --%>
			</tr>
		</c:forEach>
	</tbody>
</table>