<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="downloadTemplateForm" name="downloadTemplateForm" method="post">
		<input type="hidden" name="templateUrl" value="" id="templateUrl">
	</form>
<table>
			<tr valign="top">
				<td align="left"><h3 style="color: white;">Download
						Template here</h3>
						<c:forEach var="i" items="${dtTemplates}">
					<%-- <td><input style="padding-right: 15px;" class="showattribute"
						type="button" value="${i.templateName}" onclick="templateClicked('${i.templateUrl}');"></td> --%>
						<td><input style="padding-right: 15px;" class="download"
						type="button" value="Download" onclick="templateClicked('${i.templateUrl}');" ></td>
						</c:forEach>
			</tr>
</table>