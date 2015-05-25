<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='userSecGroupAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Name</th>
			    <th>Security Group</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${userSecGroupLists}" varStatus="status">
					<tr>
						<td id="name">${userName}</td>
				        <td>${i.name}</td>
				        <td><a href="#" onclick="deleteSecGroupFromUserClicked('${i.id}','${userId}');">Delete</a></td> 
				     <!--    <td id="userRoleRemove">Delete</td> -->
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='secGroupLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Security Group</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sg" items="${secGroupLists}" varStatus="status">
					<tr>
				        <td>${sg.name}</td>
				        <td><a href="#" onclick="addSecGroupToUserClicked('${sg.id}','${userId}');">Add</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	</tr>
	</table>
	<div id="goBackToUserSecGroups" align="right">
									<input type="button" class="download"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showUsers();" />
										</div>
	
	
	