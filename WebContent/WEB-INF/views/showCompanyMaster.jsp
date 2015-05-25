<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="bg_title">

					<span class="grid-box-header">${title} <a href="#" class="addnew"
				title="Add New"  onclick="showCreateCompany();"  >+ Add New</a>	</span>	
	
</div>
	
<table id='companyList' class='documents-table display'>
		<thead>
			<tr>
			    <th>Company Name</th>
			    <th>Edit</th>
			    <th></th>
			    <th>Active/Deactive</th>
			    
			</tr>
		</thead>
		<tbody>
		<c:forEach var="i" items="${company}" varStatus="status">
		<c:set var="isActive" scope="page">${i.isActive}</c:set>
		
					<tr>
						<td>${i.value}</td>
						<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td><a href="#" onclick="editCompanyClicked(${i.id});">Edit</a> </td>
						<td><h1 style='display: none;'>2</h1> <img
							src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
						<td id="deleteCompany"><a href="#"
							onclick="deleteModeCompanyClicked('${i.id}');">Deactive</a></td>
						<!-- <td><a href="#" onclick="assignProjectToCompanyClicked();">Assign</a></td> -->
						
						
						</c:when>
						<c:otherwise>
						
						<td>Edit </td>
						<td><h1 style='display: none;'>1</h1> 
						<img src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteCompany"><a href="#"
							onclick="unDeleteModeCompanyClicked('${i.id}');">Activate</a></td>
						</c:otherwise>
						</c:choose>
						</tr>
		                    </c:forEach>
		</tbody>
	</table>
	
	