	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="bg_title">

					<span class="grid-box-header">${title}</span>	
	
</div>
	<table cellspacing="8" style="padding-left: 90px;">	
				  <tr>
				<td  class="label" >Select User</td>
				<td><select id="userTray" class="text" name="userTray"  onchange="userTasksSelected();">
				<option value="-1" selected="selected">--Select--</option>
					<c:forEach var="i" items="${userLists}">.
							<option title="${i.userName}" value="${i.id}">${i.userName}</option>
						</c:forEach>
				</select></td></tr>	</table>
					<div id="trayLabelDiv"></div>