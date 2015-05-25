<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='modelFormAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>Form Name</th>
			    <th>WorkFlow</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${modelListsForm}" varStatus="status">
					<tr>
						<td id="name">${formName}</td>
				        <td id="assignedSecGrp">${i.name}</td>
				        <td id="secGrpRemove"><a href="#" onclick="deleteModelForForm('${formTypeId}','${i.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='modelLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>WorkFlow</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="s" items="${modelLists}" varStatus="status">
					<tr>
				        <td id="assignedSecGrp">${s.name}</td>
				        <td id="addToForm"><a href="#" onclick="addModelForFormClicked('${formTypeId}','${s.id}');">Add</a></td> 
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
										onclick="showFormsForModelMap();" />
										</div>
	