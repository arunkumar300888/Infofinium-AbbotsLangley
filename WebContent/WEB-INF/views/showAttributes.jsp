<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="bg_title">

			<span class="grid-box-header">${title}</span>
			
			
			<c:if test="${canShowAddNewAttr}">
			 <input type="button" class="butn_link" title="Add New Attribute" value="Add New" onclick="createNewAttributeClicked();"/></c:if>
			<!-- <img id="createNewDocType" onclick="createNewAttributeClicked();" src="resources/images/5.png" title="Add New Attributes" class="pointer" /> --></td>
			<!-- <input type="button" value="Add New" onclick="createNewDocTypeClicked();" class="butn" style="float: right;"></td> -->
		
	</div>
<table id='attributeLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Order</th>
				<th>Name</th>
				<th>Edit</th>
				<!-- <th>Delete</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${attrLists}" varStatus="status">
					<tr>
					    <td id="order">${i.order}</td>
						<td id="name">${i.name}</td>
						<td id="editDocType"><a href="#" onclick="editModeAttributeClicked('${i.id}');">Edit</a></td>
				<%-- 		<td id="deleteDocType"><a href="#" onclick="deleteModeAttributeClicked('${i.id}');">Delete</a></td> --%>
					</tr>
					</c:forEach>
		</tbody>
	</table>