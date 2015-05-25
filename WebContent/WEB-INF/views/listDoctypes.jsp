<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="select1">
<div>
<c:choose>
<c:when test="${DT == true }">
<div id="ldt">
	
			<label title="Document Type" for="">Select Doc Type</label>
			
			<c:choose>
					<c:when test="${doctypes.size() == 1}">
						<c:forEach var="i" items="${doctypes}">
							<span id="doctypeDisp">
								<input title="${i.abbreviation}" type="text" disabled="disabled"  id="dtypeText" name="${i.id}" value="${i.doctypeName}" class="text">
							</span>
						</c:forEach>
						<%-- <select name="attr2" id="attr2" style="width: 190px;" onchange="attr2Selected();">
							<c:forEach var="i" items="${attr2Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:when>
					<c:otherwise>					
						<select name="dtype" id="dtype" class="text" onchange="doctypeSelected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${doctypes}">
								<option title="${i.abbreviation}" value="${i.id}">${i.doctypeName}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="btnDwnldTemp"></div>

</c:when>
<c:otherwise>
<div id="ldtUpl">
	
			<label title="Document Type" for="">Select Doc Type</label>
			
			<c:choose>
					<c:when test="${doctypes.size() == 1}">
						<c:forEach var="i" items="${doctypes}">
							<span id="doctypeUplDisp">
								<input title="${i.abbreviation}" type="text" disabled="disabled"  id="dtypeUplText" name="${i.id}" value="${i.doctypeName}" class="text">
							</span>
						</c:forEach>
						<%-- <select name="attr2" id="attr2" style="width: 190px;" onchange="attr2Selected();">
							<c:forEach var="i" items="${attr2Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:when>
					<c:otherwise>					
						<select name="dtypeUpl" id="dtypeUpl" class="text" onchange="doctypeUplSelected();">
						<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${doctypes}">
								<option title="${i.abbreviation}" value="${i.id}">${i.doctypeName}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="modelSelectDiv"></div>
</c:otherwise>
</c:choose>
</div>
</div>