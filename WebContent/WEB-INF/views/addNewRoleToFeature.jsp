<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='featureRoleAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>Feature Name</th>
			    <th>Role</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${featureRoleLists}" varStatus="status">
					<tr>
						<td id="name">${featureName}</td>
				        <td id="assignedRole">${i.roleName}</td>
				        <td id="featureRoleRemove"><a href="#" onclick="deleteRoleFromFeatureClicked('${featureId}','${i.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='roleLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Role Name</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="r" items="${roleLists}" varStatus="status">
					<tr>
				        <td id="assignedRole">${r.roleName}</td>
				        <td id="addToFeature"><a href="#" onclick="addRoleToFeatureClicked('${featureId}','${r.id}');">Add</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	</tr>
	</table>
	<div id="goBackToFeature" align="right">
									<input type="button" class="butn"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showFeatures();" />
										</div>
	