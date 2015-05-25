<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Attribute Values List</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New" onclick="createAttributeValues();"  >+ Add New</a>	</span>	
	
</div>
	
	<table id='attrValueLists' class='documents-table display'>
		<thead>
			<tr>
				<th>Attribute Value</th>
				<th>Attribute</th>
				<th>Abbreviation</th>
				<th>Edit</th>
				<th></th>
				<th>Deactive/Activate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${attrValueLists}" varStatus="status">
				<c:set var="isActive" scope="page">${i.isActive}</c:set>
				<tr>
					<td id="attrValue">${i.value}</td>
					<td id="attrName">${i.attr.name}</td>
					<td id="abbreviation">${i.abbreviation}</td>

					<c:choose>
						<c:when test="${isActive eq 'Y'}">
							<td id="editDocType"><a href="#"
								onclick="editModeAttrValueClicked('${i.id}');">Edit</a></td>
							<td><h1 style='display: none;'>2</h1> <img
								src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
							<td id="deleteAttrValue"><a href="#"
								onclick="deleteModeAttrValueClicked('${i.id}');">Deactive</a></td>
						</c:when>
						<c:otherwise>
							<td id="editDocType">Edit</td>
							<td><h1 style='display: none;'>1</h1> <img
								src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
							<td id="unDeleteAtrrValue"><a href="#"
								onclick="undoDeleteModeAttrValueClicked('${i.id}');">Activate</a></td>
						</c:otherwise>
					</c:choose>

					<%-- <td id="deleteDocType"><a href="#" onclick="deleteModeAttrValueClicked('${i.id}');">Delete</a></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>