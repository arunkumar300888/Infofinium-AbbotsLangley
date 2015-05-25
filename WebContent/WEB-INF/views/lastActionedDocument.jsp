
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	

		<span class="grid-box-header">LAST ACTIONED DOCUMENT

			<a href='#' class='close-widget-box'>

    				<img src="images/x.png" class="float-right" />

   			</a>

		</span>					

			<table class='documents-table display' id='actionedDocument'>

					<thead>
						<tr class='first-row'>
							<th class='col order'>Name</th>
							<th class='col order'>Workflow-Process</th>
							<!-- <th class='col order'>Document / Form Type</th> -->
							<th class='col order'>Action-Taken</th>
							<th class='col order'>Actioned-Date & Time</th>
							<!-- <th class='col order'>Model-Category</th> -->
							<th class='col order'>Actioned-By</th>
						</tr>
					</thead>
					<tbody>
					<!-- <tr onclick="loadMetadata('ABL-COSHH-11-ACC-IFC-170.jpg', '/User Homes/infoFinium/Quick Upload', 0,'Nil','false');"> -->
					<tr>
					<td>${lastActionedDocumentObj.name}</td>
					<td>${workflowName}</td>
					<%-- <td>${docType}</td> --%>
					<td>${actionApplied}</td>
					<td><fmt:formatDate
										type="both" pattern="dd-MM-yyyy HH:mm:ss"
										value="${lastActionedDocumentStep.dateCompleted}" /></td>
					<%-- <td>${lastActionedDocumentObj.discriminator}</td> --%>
					<td>${actionedBy}</td>
					</tr>
					</tbody>
</table>

			<!-- <div class="float-left block">Showing 0 to 0 of 0 entries</div>	 -->				

		

		<div class="clear"></div>	

		



