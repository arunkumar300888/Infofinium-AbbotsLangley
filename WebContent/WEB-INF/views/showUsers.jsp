<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<script type="text/javascript">
/* 	$('#btnSendEmail').click(function(){	
		alert("inside send");
		loadProgress();
	});	 */
	
	function emailCompleted(result) {
		//alert("Email Completed");
		removeProgress();
		if (result == 1) {
			//alert("In Change PWD");
			var emailUserDetailsDialog = $("<div id='MenuDialog' title='User Details Result'>\<p>\<span class='ui-icon ui-icon-check' "
					+ "style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>Details sent successfully<b></div>");

			emailUserDetailsDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				closeOnEscape : false,
				buttons : {
					"Ok" : {
						text : 'Ok',
						/* class : 'butn',
						width : '40px', */
						click : function() {
							$(this).dialog("close");
						}
					}
				}
			});
			
		}
		else {
			dialogTemplate("User Details Result", "Details not Sent");
		}
	}  
	
	
	function holidayCompleted(){
		if (result == 1) {
			//alert("In Change PWD");
			var holidayDialog = $("<div id='MenuDialog' title='Holiday Result'>\<p>\<span class='ui-icon ui-icon-check' "
					+ "style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>He is the one<b></div>");

			holidayDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				closeOnEscape : false,
				buttons : {
					"Ok" : {
						text : 'Ok',
						/* class : 'butn',
						width : '40px', */
						click : function() {
							$(this).dialog("close");
						}
					}
				}
			});
			
		}
		
	}
	</script>
	
	
	
<div class="bg_title">

					<span class="grid-box-header">${title}	<a href="#" class="addnew"
				title="Add New User" onclick="createUser();"  >+ Add New</a>	</span>	
	
</div>

<table id='userLists' class='documents-table display'>
	<thead>
		<tr>
			<th>Name</th>
			<th>Role</th>
			<th>Group Role</th>
			<th>Security Group</th>
			<th>Company</th>
			<th>Edit</th>
			<th>Mail User Details</th>
			<th>Holiday</th>
			<th></th>
			<th>Deactive/Activate</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="i" items="${userLists}" varStatus="status">
			<c:set var="isActive" scope="page">${i.isActive}</c:set>
			<tr>
				<td id="name">${i.userName}</td>
				<c:choose>
					<c:when test="${isActive eq 'Y'}">
						<td id="userRoleAssignment"><a href="#"
							onclick="userRoleClicked('${i.id}');">Role</a></td>
						<td id="userGroupRoleAssignment"><a href="#"
							onclick="userGroupRoleClicked('${i.id}');">Group Role</a></td>
						<td id="userSecGroupAssignment"><a href="#"
							onclick="userSecGroupClicked('${i.id}');">Security Group</a></td>
							<td id="userCompanyGroupAssignment"><a href="#"
							onclick="userCompanyGroupClicked('${i.id}');">Company Group</a></td>
						<td id="userEdit"><a href="#"
							onclick="userEditClicked('${i.id}');">Edit</a></td>
						<td id="userEmail" align="center">
							<form method="post" id="frmUserEmail" target="hiddenNewFrame">
								<input type="hidden" name="userId" value="" id="userId">
								<input type="button" class="butn" id="btnSendEmail" value="Send"
									onclick="mailUserDetailsClicked('${i.id}');">
							</form>
						</td>		
						<td id="userHoliday"><a href="#"
							onclick="selectHolidayClicked('${i.id}');">Select Date</a></td>
						<td><h1 style='display: none;'>2</h1> <img
							src='resources/images/green_ball-v2.png' style='width: 15px;' /></td>
						<td id="deleteUser"><a href="#"
							onclick="deleteModeUserClicked('${i.id}');">Deactive</a></td>
					</c:when>
					<c:otherwise>
						<td id="userRoleAssignment">Role</td>
						<td id="userGroupRoleAssignment">Group Role</td>
						<td id="userSecGroupAssignment">Security Group</td>
						<td id="userCompanyGroupAssignment">Company Group</td>
						<td id="userEdit">Edit</td>
						<td id="userEmail" align="center"><button class="butnSend">Send</button></td>
						<td></td>
						<td><h1 style='display: none;'>1</h1> 
						<img src='resources/images/red_ball-v2.png' style='width: 15px;' /></td>
						<td id="unDeleteUser"><a href="#"
							onclick="unDeleteModeUserClicked('${i.id}');">Activate</a></td>
					</c:otherwise>
				</c:choose>
				<%-- <td id="deleteUser"><a href="#" onclick="deleteModeUserClicked('${i.id}');">Delete</a></td> --%>
				<%-- <td id="editAssignment"><a href="#" onclick="userAssignmentClicked('${i.id}');">Assignment</a></td>  --%>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="hiddenDivSendUserDetails">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame" style="display: none">
	</iframe>
</div>