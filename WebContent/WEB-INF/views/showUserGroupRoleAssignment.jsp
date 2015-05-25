<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">
			<span class="grid-box-header">${title} 	
			<a href="#" class="addnew" id="createNewGroupRole"
				title="Add New Role" onclick="createNewGroupRole('${userId}');" >+ Add New</a></span>
			 
	</div>	
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='userGroupRoleAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Name</th>
			    <th>Group Role</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="ugr" items="${userGroupRoleLists}" varStatus="status">
					<tr>
						<td id="name">${userName}</td>
				        <td id="assignedRole">${ugr.group.groupName}/${ugr.role.roleName}</td>
				        <td id="userRoleRemove"><a href="#" onclick="deleteGroupRoleFromUserClicked('${ugr.id}','${userId}');">Delete</a></td> 
				     <!--    <td id="userRoleRemove">Delete</td> -->
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='groupRoleLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Group Role Name</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="gr" items="${groupRoleLists}" varStatus="status">
					<tr>
				        <td id="assignedRole">${gr.group.groupName}/${gr.role.roleName}</td>
				        <td id="addToUser"><a href="#" onclick="addGroupRoleToUserClicked('${gr.id}','${userId}');">Add</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	</tr>
	</table>
	<div id="goBackToUserGroupRole" align="right">
									<input type="button" class="butn"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showUsers();" />
										</div>
	
	
	