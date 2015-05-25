<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New Workflow Model" 
				onclick="createNewWorkflowModelMap();"  >+ Add New</a>	</span>	
	
</div>

<table id='modelComboLists' class='documents-table display'>
	<thead>
		<tr>
			<th>Workflow Model Name</th>
			<th>Doc Type</th>
			<th>Assignment</th>
			<th></th>
			<th>Deactive/Activate</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${modelComboLists}" varStatus="status">
			<c:set var="isActive" scope="page">${i.isActive}</c:set>
			<tr>
				<td id="name">${i.modelName}</td>
				<td>${i.doctype.doctypeName}</td>

				<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td id="editAssignment"><a href="#"
							onclick="modelAssignmentClicked('${i.id}');">Assignment</a></td>
						<td><h1 style='display: none;'>2</h1>
							<img src='resources/images/green_ball-v2.png'
							style='width: 15px;' /></td>
						<td id="deleteModelCombo"><a href="#"
							onclick="deleteModeModelComboClicked('${i.id}');">Deactive</a></td>
					</c:when>
					<c:otherwise>
						<td id="editAssignment">Assignment</td>
						<td><h1 style='display: none;'>1</h1>
							<img src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteModelCombo"><a href="#"
							onclick="unDeleteModeModelComboClicked('${i.id}');">Activate</a></td>
					</c:otherwise>
				</c:choose>

			</tr>
		</c:forEach>
	</tbody>
</table>