<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<div class="grid-box-header">LAST DOWNLOADED DOCUMENT

			<a href='#' class='close-widget-box'>

    				<img src="images/x.png" class="float-right" />

   			</a>

		</div>
		

			<table class='documents-table display' id='downloadedDocument'>

					<thead>
						<tr class='first-row'>
							<th class='col order'>Name</th>
							<th class='col order'>Submission-Date</th>
							<th class='col order'>Document-Owner</th>
							<th class='col order'>Workflow-Process</th>
							<!-- <th class='col order'>Document-Type</th>
							<th class='col order'>Ref.No</th>
							<th class='col order'>Raised Date & Time</th>
							<th class='col order'>Model-Category</th> -->
							<th class='col order'>Downloaded Date & Time</th>
						</tr>
					</thead>
					<tbody>
					<tr onclick="loadMetadata('${lastDownloadedDocument.name}', '${lastDownloadedDocument.filePath }', ${caseId },'${stepList }','false');">
					<td>${lastDownloadedDocument.name }</td>
					
					<td><fmt:formatDate
										type="both" pattern="dd-MM-yyyy"
										value="${lastDownloadedDocument.targetDate}" /></td>
						<c:choose>
   					 <c:when test="${empty lastDownloadedDocument.reassignOwner}">
					<td>${lastDownloadedDocument.author}</td>
					 </c:when>
					  <c:otherwise>
					  <td>${lastDownloadedDocument.reassignOwner }</td>
					  </c:otherwise>
					  </c:choose>				
					<td>${workflowName}</td>
					<%-- <td>${lastDownloadedDocument.doctype.doctypeName}</td>
					<td>${lastDownloadedDocument.ebNo}</td>
					<td><fmt:formatDate
										type="both" pattern="dd-MM-yyyy HH:mm:ss"
										value="${lastDownloadedDocument.dateCreated}" /></td>
					<td>${lastDownloadedDocument.discriminator}</td> --%>
					<td><fmt:formatDate type="both" pattern="dd-MM-yyyy HH:mm:ss"
										value="${lastDownloadedTime}" /></td>
					</tr>
					</tbody>
</table>
			<!-- <div class="float-left block">Showing 0 to 0 of 0 entries</div>	 -->		

