<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">
			<span class="grid-box-header" id="title">${title}<c:if  test="${canShowAddNew}">	<a href="#" class="addnew"
				title="Add New Security Group Assignment" onclick="createNewSecurityGroupAssignment('${secGroupId}');" >+ Add New</a></c:if></span>
			<c:if test="${canShowTitle1}">
			<span class="grid-box-header">${title1}<c:if  test="${canShowAddNew}">	<a href="#" class="addnew"
				title="Add New Security Group Assignment" onclick="createNewSecurityGroupAssignment('${secGroupId}');" >+ Add New</a></c:if></span>
			</c:if>
			
	</div>
<%-- <div class="bg_title">
<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td id="title" align="left">${title}</td>
			<c:if test="${canShowTitle1}">
			<td align="left">${title1}</td>
			</c:if>
			<c:if  test="${canShowAddNew}">
			<td align="right">
			 <input type="button" class="download" title="Add New Security Group Assignment" value="Add New" onclick="createNewSecurityGroupAssignment('${secGroupId}');"/>
			 </td>
			 </c:if>
		</tr>
	</table>
	</div> --%>

<table id='secGroupAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Security Group</th>
			    <th>${attr1Title}</th>
			    <th>${attr2Title}</th>
			    <th>${attr3Title}</th>
			    <%-- <th>${attr4Title}</th> --%>
			     <th>Doctype</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${secGroupAssignmentLists}" varStatus="status">
			<%-- <c:out value="${secGroupAssignmentLists}"/> --%>
					<tr>
						<td id="name">${i.securityGroup.name}</td>
						<td id="attrValue1">${i.attributeValue1.value}</td>
						<td id="attrValue2">${i.attributeValue2.value}</td>
						<td id="attrValue3">${i.attributeValue3.value}</td>
						<%-- <td id="attrValue4">${i.attributeValue4.value}</td> --%>
						<td id="doctype">${i.doctype.doctypeName}</td>
						<td id="deleteSecGroup"><a href="#" onclick="deleteModeSecGroupAssignmentClicked('${i.id}','${secGroupId}');">Delete</a></td>
					</tr>
					</c:forEach>
		</tbody>
	</table>
	
		<div id="goBackToSecGroup" align="right">
									<input type="button" class="download"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showSecurityGroups();" />
										</div>	