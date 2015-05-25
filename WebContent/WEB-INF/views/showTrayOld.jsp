<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table cellspacing="10">
	<tr>
		<c:if test="${empty tasks}">
			<td class="traygray">
				<div class="top"></div>
				<div class="bot">0</div>
			</td>
		</c:if>
		
		<c:forEach var="entry" items="${draftTray}">
		<td class="trayorange" onclick="trayDraftClicked();" >
						<div class="top">
							<span class="point">${entry.value}</span>
						</div>
						<div class="bot">${entry.key}</div>
					</td>
		</c:forEach>

		<c:forEach var="i" items="${tasks}">
			<c:choose>
				<c:when test="${i.taskName=='My Documents'}">

					<td class="trayblue" onclick="trayClicked('${i.taskName}');">
						<div class="top">
							<span class="point">${i.taskCount}</span>
						</div>
						<div class="bot">${i.taskName}</div>
					</td>

				</c:when>
				<c:otherwise>
					<td class="trayred" onclick="trayClicked('${i.taskName}');">
						<div class="top">
							<span class="point">${i.taskCount}</span>
						</div>
						<div class="bot">${i.taskName}</div>
					</td>

				</c:otherwise>
			</c:choose>
		</c:forEach>
		
	
	</tr>
</table>


<%-- <c:choose>
	<c:when test="${empty tasks}">
		<table cellspacing="10">
			<tr>
				<td class="traygray"><div class="top"></div>
					<div class="bot">0</div></td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>

		<ul id="mycarousel" class="jcarousel-skin-tango">
			<c:forEach var="i" items="${tasks}">
				<c:choose>
					<c:when
						test="${i.taskName=='Approved' || i.taskName=='Checked' || i.taskName=='Reviewed' || i.taskName=='Rejected'}">
				<li>
					<table>
						<tr>
							<td class="trayred" onclick="trayClicked('${i.taskName}');"><div
									class="top">
									<span class="point">${i.taskCount}</span>
								</div>
								<div class="bot">${i.taskName}</div></td>
						</tr>
					</table>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	$(document).ready(function() {

		$('#mycarousel').jcarousel();
	});
</script> --%>



