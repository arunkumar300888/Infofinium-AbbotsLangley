<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table id='userCompanyGroupAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			   <th>User Name</th>
			    <th>Company</th>
			    <th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${companyListUser}" varStatus="status">
					<tr>
						<td id="name">${userName}</td>
				        <td id="assignedCompany">${i.value}</td>
				        <td id="companyRemove"><a href="#" onclick="deleteCompanyForUser('${userId}','${i.id}');">Delete</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	<td>
	<table id='companyLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Company</th>
			    <th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="s" items="${companyList}" varStatus="status">
					<tr>
				        <td id="assignCompanyGrp">${s.value}</td>
				        <td id="addToUser"><a href="#" onclick="addCompanyForUserClicked('${userId}','${s.id}');">Add</a></td> 
					</tr>
					</c:forEach>
		</tbody>
	</table>
	</td>
	</tr>
	</table>
	<div id="goBackToUserList" align="right">
									<input type="button" class="butn"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showUsers();" />
										</div>
	