<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='secGrpAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>Form Name</th>
			    <th>Security Group</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${secGrpListsForm}" varStatus="status">
					<tr>
						<td id="name">${formName}</td>
				        <td id="assignedSecGrp">${i.securityGroup.name}</td>
				        <td id="secGrpRemove"><a href="#" onclick="deleteSecGrpForForm('${formTypeId}','${i.securityGroup.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='secGrpLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Security Group</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="s" items="${secGrpLists}" varStatus="status">
					<tr>
				        <td id="assignedSecGrp">${s.name}</td>
				        <td id="addToForm"><a href="#" onclick="addSecGrpForFormClicked('${formTypeId}','${s.id}');">Add</a></td> 
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
										onclick="showFormsForSecGrp();" />
										</div>
	