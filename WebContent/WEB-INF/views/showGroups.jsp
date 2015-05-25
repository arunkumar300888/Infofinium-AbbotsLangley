<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New Group" onclick="createNewGroup();"  >+ Add New</a>	</span>	
	
</div>

	
<table id='groupLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Group Name</th>
			    <th>Edit</th>
				<!-- <th>Delete</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${groupLists}" varStatus="status">
					<tr>
						<td id="name">${i.groupName}</td>
						<td id="editGroup"><a href="#" onclick="editModeGroupClicked('${i.id}');">Edit</a></td>
					<%-- 	<td id="deleteGroup"><a href="#" onclick="deleteModeGroupClicked('${i.id}');">Delete</a></td> --%>
					</tr>
					</c:forEach>
		</tbody>
	</table>