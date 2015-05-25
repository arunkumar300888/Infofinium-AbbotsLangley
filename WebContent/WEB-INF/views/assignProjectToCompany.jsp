<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='compAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>Company Name</th>
			    <th>Project</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${projComp}" varStatus="status">
					<tr>
						<td id="name">${companyName}</td>
				        <td id="assignedSecGrp">${i.securityGroup.name}</td>
				        <td id="secGrpRemove"><a href="#" onclick="deleteSecGrpForForm('${formTypeId}','${i.securityGroup.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='projectLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Project Name</th>
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
	