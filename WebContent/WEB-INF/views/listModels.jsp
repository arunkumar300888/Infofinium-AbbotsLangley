<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="select1">
<div>
		<div style="display: none;" id="modelsel">
			
					<label title="Choice of Workflow" >Select Workflow</label>
					<c:choose>
							<c:when test="${modelsSet.size() == 1}">
								<c:forEach var="i" items="${modelsSet}">
									<span id="modelDispDiv">
										<input  title="${i.description}" type="text" disabled="disabled" id="modelDisp" name="${i.id}" value="${i.modelName}" class="text">
									</span>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<select name="modelname" id="modelname" class="text"	onchange="modelNameSelected();">
								<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${modelsSet}">
										<option title="${i.description}" value="${i.modelName}">${i.modelName}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
		</div>

		<div id="uploadDocument"></div>
		</div>
		</div>