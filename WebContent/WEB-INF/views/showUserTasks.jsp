 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<table cellspacing="8" style="padding-left: 90px;">	
				  <tr>
				<td class="label" >Select Task</td>
				<td><select id="userTasks" name="userTasks" class="text">
				<option value="-1" selected="selected">--Select--</option>
					<c:forEach var="i" items="${tasks}">.
							<option title="${i.taskName}" value="${i.taskName}">${i.taskName}</option>
						</c:forEach>
				</select></td></tr>	
				<tr valign="top">
						<td></td>
							<td><input type="button" class="download" value="View Tasks"
								id="btnView" onclick="viewTasksClicked();"
								style="width: 137px;" />
								</td>
						</tr>
	</table>