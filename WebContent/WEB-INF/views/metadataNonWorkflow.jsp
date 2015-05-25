<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="top">
<div id="docPropertiesNonWorkflow" class="my-document complete box-content">
	
			<span class="float-left grid-box-header top3">Document Properties</span>
			<div align="right">
			<input type="button" id="btnBackToList" onclick="loadDocuments();" value="Back" class="download" />
			</div>
		
</div>
</div>
<%-- <div id="docNameheaderNonWorkflow" class="title1">${docName}</div> --%>
<div id="metaDatViewNonWorkflow">
	<div align="center" style="background-color: #EAEAEA;">
		<small id="docNameheaderNonWorkflow" class="title1">${docName}</small></div>
				<div id="metaSpaceDivNonWorkflow" class='my-document complete box-content float-left'
			style="width: 980px">
					<div id="propertiesNonWorkflow">

						<table class='display' id='documentPropertiesNonWorkflow'>

							<thead>
								<tr>
									<th>Title</th>
									<th></th>
									<th>Description</th>

								</tr>
							</thead>

							<tbody>
								<c:forEach var="entry" items="${metaDataNonWorkflow}">
									<tr>
										<c:choose>

											<c:when test="${entry.key=='Document Link'}">
												<td style="white-space: nowrap;">${entry.key}</td>
												<td>:</td>
												<td id="documentLinkProp">${entry.value}</td>
												<%-- <td><a href="#">${entry.value}</a></td> --%>

											</c:when>

											<c:otherwise>
												<td style="white-space: nowrap;">${entry.key}</td>
												<td>:</td>
												<td>${entry.value}</td>

											</c:otherwise>

										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			<div class="float-right" style="width: 213px; min-height: 350px; background-color: #ECECFB;" align="center">
					<c:if test="${(docAttached == true)&&(canShowDownload == true)}">
						<input type="button" class="butn" value="Download"
							id="btnDownloadNonWorkflow" onclick="downloadClickedNonWorkflow();"
							style="width: 142px;" />
						<br />
					</c:if>
					
						<!-- <input type="button" class="butn" value="Download"
							id="btnDownloadNonWorkflow" onclick="downloadClickedNonWorkflow();"
							style="width: 142px;" /> -->
					
				</div>


				<form id="downloadFormNonWorkflow" name="downloadFormNonWorkflow" method="post">
					<input type="hidden" name="path" value="${path}">
					<input type="hidden" name="name" value="${docName}">
				</form>
			
</div>
