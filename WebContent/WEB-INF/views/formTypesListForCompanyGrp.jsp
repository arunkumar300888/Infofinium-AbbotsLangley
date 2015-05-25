<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg_title">

					<span class="grid-box-header">${title}	</span>	
	
</div>

<table id='formCompLists' class='documents-table display'>
	<thead>
		<tr>
			<!-- <th>Form Type</th> -->
			<th>Form Name</th>
			<th>Company Assignment</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${formTypeList}" varStatus="status">
			
			<tr>
			<%-- 	<td id="name">${i.name}</td> --%>
				
					
						<td id="description">${i.description}</td>
						<td id="assignCompanyGroup"><a href="#"
							onclick="assignCompanyGrp('${i.id}');">Assign</a></td>
						
					
				
				<%-- <td id="deleteSecGroup"><a href="#" onclick="deleteModeSecGroupClicked('${i.id}');">Delete</a></td> --%>
			</tr>
		</c:forEach>
	</tbody>
</table>