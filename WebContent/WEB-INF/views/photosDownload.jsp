<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div id="photoDownload" class="title">
	<table width="100%">
		<tr>
			<td align="left">Download Photos<img alt="" src="resources/images/forward_disabled.png" />${folderName}</td>
			<!-- <td align="right"><a href="javascript:loadDocuments();"
				id="btnBackToList">Back</a></td> -->
		</tr>
	</table>
</div>


<div id="photosDownloadView">
	<table style="width: 100%;">
		<tr valign="top">
			<td>
				<div id="metaSpaceDiv" style="width: 690px;overflow: scroll;">
					<div id="photoProperties">

						<table id='photosDownload' class='display'>

							<thead>
								<tr>
									<th>Name</th>
									<th>Download Url</th>

								</tr>
							</thead>

							<tbody>
								<c:forEach var="i" items="${imageList}">
									<tr>
										<td>${i.name}</td>
										<td><a href="#" onclick="downloadPhotos('${i.path}','${i.name}');">${i.path}/cm:${i.name}</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				
			</td>
			<td><div class="butn_right"></div></td>
		</tr>
	</table>
	
	<div id="error">${ photoserror}</div>
	
	<form id="downloadPhotosForm" name="downloadPhotosForm" method="post">
		<input type="hidden" name="path" value="" id="path">
		<input type="hidden" name="name" value="" id="name">
	
	</form>	
	
</div>
