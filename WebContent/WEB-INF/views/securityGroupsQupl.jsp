 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <script>
	$("#listSecGroupsQupl").hide();
 </script>

	<!-- Added For Security group Below this -->
	<div id="securitySettingDialogQupl"  title="Security Setting" class="main">
			<div align="center"  class="value controlspan">	
			<input type="hidden" value="${open }" id="secOpen" name="secOpen"/>	
				<c:choose>
						<c:when test="${secGroups.size()==0}">
							<input type="radio" style="display: inline;" name="secTypeQupl" id="secTypeQupl" value="open" onClick="openClicked();" checked="checked"> 
								<span>Open</span>
							<input type="radio" style="display: inline;" name="secTypeQupl" id="secTypeQupl" value="restricted" disabled="disabled">
								<span>Restricted</span>
						</c:when>
						<c:otherwise>
							<input type="radio" style="display: inline;" name="secTypeQupl" id="secTypeQupl" value="open" onClick="openClicked();" checked="checked"> 
									<span>Open</span>
							<input type="radio" style="display: inline;" name="secTypeQupl" id="secTypeQupl" value="restricted"  onclick="restrictedClicked(${defaultSG.id});" >
									<span>Restricted</span>
			
							<div id="listSecGroupsQupl" align="left"  class="value controlspan"  hidden="true"> 
								<c:forEach var="sg" items="${secGroups}"> 
								<c:choose>
									<c:when test="${(sg.id)==(defaultSG.id)}">
										<input type="radio" style="display: inline;" name="sgrpQupl" id="sgrpQupl" value="${sg.id}" checked="checked">
											<span>${sg.name}</span><br>
									</c:when>
									<c:otherwise>
										<input type="radio" style="display: inline;" name="sgrpQupl" id="sgrpQupl" value="${sg.id}"> <span>${sg.name}</span><br>
									</c:otherwise>
								</c:choose>
								</c:forEach>
							</div>		
						</c:otherwise>
				</c:choose>
			</div>
	</div>
	
	
	
	
	