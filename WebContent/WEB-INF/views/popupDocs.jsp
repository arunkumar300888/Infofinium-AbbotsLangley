<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p id="popDocs">
	<select size="5" class="m_select_text" style="width: 100%;" id="_pd_data_listbox" name="_pd_data_listbox">
		<c:forEach var="i" items="${docs}">
		<c:set var="discriminator" scope="page">${i.discriminator}</c:set>
		<c:choose>
					<c:when test="${discriminator eq 'D'}">
		
			<option value="${i.name}" docDow="${i.filePath},${i.name},${i.id},0">${i.name}</option>
			
			</c:when>
			<c:otherwise>
			<%-- <form method="post" id="pdfDownloadForm" name="pdfDownloadFrm">
			<input type="hidden" name="userFormId" value="${i.userFormId }" id="userFormId" />
			</form> --%>
			<option value="${i.name}" pdfDow="${i.userFormId }">${i.name} </option>
					
			</c:otherwise>
			</c:choose>
		</c:forEach>		
	</select>
	<input type="button" class="butn" onclick="docDownloadClicked();" value="Open">
</p>