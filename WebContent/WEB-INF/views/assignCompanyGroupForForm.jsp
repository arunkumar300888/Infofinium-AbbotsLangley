<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='companyGrpAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>Form Name</th>
			    <th>Company</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${companyGrpListsForm}" varStatus="status">
					<tr>
						<td id="name">${formName}</td>
				        <td id="assignedcompanyGrp">${i.attributeValue.value}</td>
				        <td id="companyGrpRemove"><a href="#" onclick="deleteCompanyGrpForForm('${formTypeId}','${i.attributeValue.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='companyGrpLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Company</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="s" items="${companyGrpLists}" varStatus="status">
					<tr>
				        <td id="assignedSecGrp">${s.value}</td>
				        <td id="addToForm"><a href="#" onclick="addCompanyGrpForFormClicked('${formTypeId}','${s.id}');">Add</a></td> 
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
										onclick="showFormsForCompanyGrp();" />
										</div>
	