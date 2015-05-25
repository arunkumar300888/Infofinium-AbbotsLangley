<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>

<table id='featureLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Feature Name</th>
			    <th>Description</th>
			    <th>Access Roles</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${featureLists}" varStatus="status">
					<tr>
						<td id="name">${i.featureName}</td>
						<td>${i.description}</td> 
						<td id="accessRole"><a href="#" onclick="accessRoleClicked('${i.id}');">Access Roles</a></td>
					</tr>
					</c:forEach>
		</tbody>
	</table>