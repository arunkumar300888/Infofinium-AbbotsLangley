<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
<table id='showUserAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Name</th>
			    <th>Role</th>
			    <th>Group Role</th>
			    <th>Security Group</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody><tr>
						<td id="name"></td>
						<td id="role">${project}</td>
						<td id="groupRole">${category}</td>
						<td id="secGroup">${discipline}</td>
						<td id="deleteUserAssignment"><a href="#" onclick="deleteModeUserAssignmentClicked();">Delete</a></td>
					</tr>
		</tbody>
	</table>