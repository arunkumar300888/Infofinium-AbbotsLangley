<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bigbg">
	<h2 style="color: white; padding: 10px;">My Steps</h2>
</div>
<div class="check" style="margin-top: 10px; height: 100%;" id="myStepsDiv">
	<div id="dt_example">
		<div id="stepList">
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="stepTable">		
				<thead>
					<tr>
						<th>Case Id</th><th>Step Id</th><th>Status</th>
					</tr>
				</thead>
				<tbody>
					 <c:forEach var="i" items="${steps}">
					 	<tr> <td>${i.owningCase.id}</td><td>${i.id}</td><td>${i.status}</td></tr>
					 </c:forEach>
				</tbody>
		
		</table>
		</div>
	</div>
</div>