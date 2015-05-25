<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New Role" onclick="createNewTemplate();" >+ Add New</a>	</span>	
	
</div>



<table id='templateLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Template Name</th>
			    <th>Doc Type</th>
			    <th>Assignment</th>
			   <!--  <th>Edit</th> -->
				<!-- <th>Delete</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${templateLists}" varStatus="status">
					<tr>
						<td id="name">${i.templateName}</td>
						<td>${i.doctype.doctypeName}</td>
						<td id="editAssignment"><a href="#" onclick="templateAssignmentClicked('${i.id}');">Assignment</a></td> 
					<%-- 	<td id="editTemplates"><a href="#" onclick="editModeTemplateClicked('${i.id}');">Edit</a></td> --%>
						<%-- <td id="deleteTemplates"><a href="#" onclick="deleteModeTemplateClicked('${i.id}');">Delete</a></td> --%>
					</tr>
					</c:forEach>
		</tbody>
	</table>