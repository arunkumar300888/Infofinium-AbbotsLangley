<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

			<span class="grid-box-header">${title}<c:if  test="${canShowAddNew}">	<a href="#" class="addnew"
				title="Add New Model Map Assignment" onclick="createNewModelMapAssignment('${modelComboId}');" >+ Add New</a></c:if></span>
			<c:if test="${canShowTitle1}">
			<span class="grid-box-header">${title1}<c:if  test="${canShowAddNew}">	<a href="#" class="addnew"
				title="Add New Model Map Assignment" onclick="createNewModelMapAssignment('${modelComboId}');" >+ Add New</a></c:if></span>
			</c:if>
			
	</div>
<table id='modelComboValueLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Model Name</th>
			    <th>${attr1Title}</th>
			    <th>${attr2Title}</th>
			    <th>${attr3Title}</th>
			    <%-- <th>${attr4Title}</th>
			    <th>${attr5Title}</th> --%>
			     <th>Doctype</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>				
		<tr>
						<td id="name">${modelName}</td>
						<td id="attrValue5">${company}</td>
						<td id="attrValue1">${project}</td>
						<td id="attrValue2">${category}</td>
						<%-- <td id="attrValue3">${discipline}</td>
						<td id="attrValue4">${site}</td> --%>
						<td id="doctype">${doctype}</td>
						<td id="deleteModelMap">
						<c:if test="${canShowDelete}">
						<a href="#" onclick="deleteModelMapAssignmentClicked('${modelComboId}');">Delete</a>
						</c:if>
						</td>
						
					</tr>
		</tbody>
	</table>
	<div id="goBackToWorkflow" align="right">
									<input type="button" class="butn"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showModelCombo();" />
										</div>