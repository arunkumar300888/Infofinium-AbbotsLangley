<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
/* 	$('#btnSendEmail').click(function(){	
		alert("inside send");
		loadProgress();
	});	 */
	
	function roleCompleted(result) {
		//alert("Email Completed");
		
		if (result == 1) {
			//alert("In Change PWD");
			
			dialogTemplate("Role Assignment Result", "Third Party cannot be assigned any role ");
		}
	
		else if(result == 2){
			dialogTemplate("Role Assignment Result", "Third Party role cannot be assigned to this user ");
		}
		else{
			dialogTemplate("Role Assignment Result", "Role added successfully ");
		}
	}  
	</script>

<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
    <%-- 	<ul>
			<c:forEach var="ur" items="${userRoleLists}">
				<li>
					<div>${userName}</div>
				    <div>${ur.roleName}</div>
				    <div><a href="#" onclick="deleteRoleFromUserClicked('${ur.id}','${userId}');">Delete</a></div>
				</li>
			</c:forEach>
		</ul> --%>
	
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='userRoleAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Name</th>
			    <th>Role</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="ur" items="${userRoleLists}" varStatus="status">
					<tr>
						<td id="name">${userName}</td>
				        <td id="assignedRole">${ur.roleName}</td>
				        <td id="userRoleRemove"><a href="#" onclick="deleteRoleFromUserClicked('${ur.id}','${userId}');">Delete</a></td> 
				     <!--    <td id="userRoleRemove">Delete</td> -->
					</tr>
					</c:forEach>
	<c:if test="${result==1}">
	<div >
	<script type="text/javascript">
	dialogTemplate("Role Assignment Result", "Third Party cannot be assigned any role ");</script>
	</div>
	</c:if>
	
	<c:if test="${result==2}">
	<div>
	<script type="text/javascript">
	dialogTemplate("Role Assignment Result", "Third Party Role cannot be assigned to this User ");</script>
	</div>
	</c:if>
	
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
				        <td id="addToUser"><a href="#" onclick="addRoleToUserClicked('${r.id}','${userId}');">Add</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	</tr>
	</table>
	
	<div id="goBackToUserRoles" align="right">
									<input type="button" class="download"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showUsers();" />
										</div>	
	
	
	